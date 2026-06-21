package ExpressionTree::Operations::Coalesce;

  use strict;

=head1 NAME ExpressionTree::Operations::Coalesce;

=head1 DESCRIPTION

This class represents the Path Coalesce operation.

=head1 METHODS

=head2 new(dimension, path)

=over 4

=item * dimension

The dimension to Coalesce out

=item * path

The paths to Coalesce, the constructors are with the dimension specification.

=back

Create a new Coalesce operation

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'dimension'} = shift;
    $self->{'variable'} = shift;
    $self->{'resultType'} = $self->{'dimension'};
    bless $self, $type;
    }
#---------------------------------------------------------------------

#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No dimension to Coalesce." unless scalar(@_);
    shift;
    return "No path to Coalesce." unless scalar(@_);
    return undef; # passed validation!
    }
#---------------------------------------------------------------------

=head2 resultType()

Return the type of result.

=cut

#---------------------------------------------------------------------
  sub resultType {
    my ($self) = @_;
    return $self->{'resultType'};
    }

#---------------------------------------------------------------------

=head2 dump($indent)

Dump this node.

=cut

#---------------------------------------------------------------------
  sub dump {
    my ($self, $indent) = @_;
    my ($extra) = '  ';
    my ($op) = $self->{'op'};
    my ($s) = "$indent $op\n   " . 
       $self->{'dimension'}->dump("$indent$extra");
    $s = "$indent    " . $self->{'path'}->dump("$indent$extra");
    return $s;
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

=over 4

=item * state

- The state of the system.

=back

Evaluate the Coalesce operation.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    # Do a symbol table lookup to find the value for this variable
    my ($startExp) = $self->{'variable'};

    # Do a full variable lookup if it is a variable expression!
    my ($pathList) = $self->{'variable'}->fullEvaluate($state);
    my ($whichPath) = $self->{'variable'}->evaluate($state);
    my ($start) = $whichPath->startNode()->presentation();
    my ($finish) = $whichPath->terminalNode()->presentation();
    my ($coalescedValue);

    # Get all the paths that start and finish together
    foreach my $path (@$pathList) {

      # where does the path start?
      my ($startNode) = $path->startNode()->presentation();
      # skip it unless it is one we want
      next unless $start eq $startNode;

      # where does the path end?
      my ($endNode) = $path->terminalNode()->presentation();
      # skip it unless it is one we want
      next unless $finish eq $endNode;

      my ($newList) = $path->dimensionExtract($self->{'dimension'});

      # skip empty properties
      next unless scalar(@$newList);

      # get the property value
      my ($newValue) = @$newList[0];

      # Do we already have a value
      if (defined $coalescedValue) { 
        $coalescedValue = $coalescedValue->coalesce($newValue); 
        }
      else { 
        $coalescedValue = $newValue; 
        }
      }

    # Now, collect all the coalesced values
    my @constructed = ();
    push @constructed, $coalescedValue if defined $coalescedValue;
    return \@constructed;
    }
#---------------------------------------------------------------------

1;
