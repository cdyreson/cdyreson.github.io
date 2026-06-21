package ExpressionTree::Operations::Match;

  use strict;

=head1 NAME ExpressionTree::Operations::Match;

=head1 DESCRIPTION

This class represents the Path Match operation.

=head1 METHODS

=head2 new(variable, descriptorExpression)

=over 4

=item * variable 

- The variable to start from, the operation to get a value from that
variable.

=item * descriptorExpression

- A descriptor regular expression.

=back

Create a new Match operation

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'variable'} = shift;
    $self->{'regExp'} = shift;
    $self->{'resultType'} = undef;
    bless $self, $type;
    }
#---------------------------------------------------------------------
#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No starting variable in Match." unless scalar(@_);
    shift;
    return "No descriptor regular expression in Match." unless scalar(@_);
    return undef;
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
    my ($s) = "$indent Match\n   " . $self->{'variable'};
    #$s .= "$indent    \n   " . $self->{'regExp'}->dump("$indent$extra") . "\n";
    return $s;
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

=over 4

=item * state

- The state of the system.

=back

Evaluate the Match operation.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    my @constructed = ();

    #
    # Do a symbol table lookup or evaluation of an operation
    #
    my $startPaths = $self->{'variable'}->evaluate($state);

    #
    # If this is a path, then turn it into an array of paths
    #
    $startPaths = [$startPaths] if ref($startPaths) eq 'Path';

    #
    # Evaluate the matching regular expression, extending each path
    #
    my $regExp = $self->{'regExp'};
    my ($graph) = $$state{'graph'};

    foreach my $startPath (@$startPaths) {
      my ($pathList) = $regExp->evaluate($graph, $startPath);

      @constructed = (@constructed, @$pathList);
      #foreach my $matchedPath (@$pathList) {
      #  push @constructed, $matchedPath;
      #  }
      }
    return \@constructed;
    }
#---------------------------------------------------------------------

#---------------------------------------------------------------------
  sub fullEvaluate {
    my ($self, $state) = @_;
    return $self->evaluate($state);
    }
#---------------------------------------------------------------------

1;
