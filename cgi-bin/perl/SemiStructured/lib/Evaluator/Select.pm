package Evaluator::Select;

  use strict;

  my %Tuple = ();
  my %FullTuple = ();
  my @AnswerSet = ();
  my @Aggregates = ();
  my $NullPath;
  my $RootNode;

  my $Globals;
  my $SSGraph;
  my $Roots;
  my $CodeHash;
  my $From;
  my $HeaderDone = 0;
  my $Where;
  my $Select;
  my %State;

=head1 NAME Evaluator::Select

This is the code for the query evaluation of the SELECT statement.

=head1 DESCRIPTION

The SELECT statement evaluation proceeds by first determining a 
tuple from the variable assignments in the From clause.
Then the WHERE clause predicate is applied, and if the tuple
passes, the SELECT clause attributes are projected.

=head1 METHODS

=cut

#---------------------------------------------------------------------
# Initialize the global state
#---------------------------------------------------------------------
  sub _initialize {
    $CodeHash = shift;
    $From = $$CodeHash{'From'};
    $Where = $$CodeHash{'Where'};
    $Select = $$CodeHash{'Select'};
    $Globals = new SemiStructuredDB::Globals();
    $SSGraph = $Globals->{'SSGraph'};
    #my ($nullNode) = new String('&null');
    my ($nullNode) = new Null();
    my %h = ();
    $NullPath = new Path(
                  new Edge( 
                    $nullNode, 
                    new Label([new Region(\%h)]), 
                    $nullNode
                    )
                  );
    $RootNode = new String('&root');
    $Roots = $SSGraph->roots();
    %State = ();
    $State{'variables'} = \%Tuple;
    $State{'fullVariables'} = \%FullTuple;
    $State{'graph'} = $SSGraph;
    }


=head2 evaluate(HashTable codeHash)

=over 4

=item * codeHash

- A hash table of the clauses.

=back

Evaluate the select clause.

=cut

#---------------------------------------------------------------------
# Start the evaluation
#---------------------------------------------------------------------
sub evaluate {
  &_initialize(shift);

  #
  # Start from each root, and compute tuples
  #
  my ($edgeList) = $Roots->enumerateEdges();
  $FullTuple{'&root'} = $edgeList;
  foreach my $rootEdge (@$edgeList) {
    $Tuple{'&root'} = new Path($rootEdge);
    &_constructTuples(0);
    }

  # Dump the end of the answer table if needed
  &_answerFooter() if $HeaderDone;
  }

#---------------------------------------------------------------------
# Dump the header for the table to start answers
#---------------------------------------------------------------------
sub _answerHeader {
  print "<BLOCKQUOTE><table border=1 cellpadding=9 cellspacing=0>\n";
  print "<tr>\n";
  foreach my $column (@$Select) {
    print "<th>@$column[0]</th>\n";
    }
  print "</tr>\n";
  $HeaderDone = 1;
  }

#---------------------------------------------------------------------
# Print out an answer
#---------------------------------------------------------------------
sub _gotAnswer {
  &_answerHeader() unless $HeaderDone;
  print "<tr>\n";
  foreach my $column (@$Select) {
    my ($op) = @$column[1];
    print "<td>\n";
    print $op->evaluate(\%State)->presentation();
    print "</td>\n";
    }
  print "</tr>\n";
  }

#---------------------------------------------------------------------
# Print out an answer
#---------------------------------------------------------------------
sub _answerFooter {
  print "</table></blockquote>\n";
  }

#--------------------------------------------------------------------
# Build the Cartesian product set
#--------------------------------------------------------------------
sub _constructTuples {
  my ($index) = @_;

  # We have got all the variables we need, now construct a tuple
  if ($index >= scalar(@$From)) {

    # We have constructed a tuple!

    # Is there a where clause?
    if (defined $Where) {

      # evaluate the where clause
      my $result = $Where->evaluate(\%State);

      # make sure result type is boolean
      # Move this check to the compiler!
      #&_runTimeError("Non-boolean result from Where clause expression")
      #  unless ($result->resultType() eq 'boolean');

      # keep tuple if true
      if ($result->isTrue()) {
        &_gotAnswer();
        }
      }

    else {
      # Assume that a missing where clause is true
      &_gotAnswer();
      }
    return;
    }

  # Where are we currently located?
  my ($assignment) = @$From[$index];
  my ($variable, $operation) = @$assignment;

  # Get all the values to assign
  my ($values) = $operation->evaluate(\%State);
  $FullTuple{$variable} = $values;
  if (scalar(@$values)) {
    # Assign each value in turn
    foreach my $value (@$values) {
      $Tuple{$variable} = $value;
      &_constructTuples($index + 1);
      }
    }
  else {
    $Tuple{$variable} = $NullPath;
    &_constructTuples($index + 1);
    }
  }

sub _runTimeError {
  my ($msg) = @_;
  &ErrorHandler::ErrorReport(
        "SEMANTIC ERROR DETECTED.\n" .
        "------------------------\n" .
        "<----- $msg\n\nExiting...\n".
        "------------------------\n" 
  );
  exit(1);
  }

1;
