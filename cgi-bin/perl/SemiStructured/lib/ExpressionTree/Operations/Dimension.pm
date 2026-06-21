package ExpressionTree::Operations::Dimension;

  use strict;

=head1 NAME ExpressionTree::Operations::Dimension;

=head1 DESCRIPTION

This class represents a Dimension extraction operation.

=head1 METHODS

=head2 new(variable)

=over 4

=item * variable

The name of a variable, which is the path from which to extract
the required valued.

=back

Create a new Dimension operation

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'dimension'} = shift;
    $self->{'variable'} = shift;
    $self->{'resultType'} = 'edge';
    bless $self, $type;
    }
#---------------------------------------------------------------------
#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No dimension to extract in Dimension operation." unless scalar(@_);
    shift;
    return "No path to extract a Dimension from." unless scalar(@_);
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
    my ($op) = $self->{'op'};
    my ($s) = "$indent $op\n   " . $self->{'variable'}->dump("$indent$extra");
    return $s;
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

=over 4

=item * state

- The state of the system.

=back

Evaluate the Dimension operation.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    # Do a symbol table lookup to find the value for this variable
    my $startPath = $self->{'variable'}->evaluate($state);

    # We know it is a good path, now fetch the dimension value from 
    # the collapsed area in the path
    return $startPath->dimensionExtract($self->{'dimension'});
    }
#---------------------------------------------------------------------

1;
