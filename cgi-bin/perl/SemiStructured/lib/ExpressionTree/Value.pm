package ExpressionTree::Value;

  @ExpressionTree::Value::ISA = qw( ExpressionTree::Operand );

=head1 NAME ExpressionTree::Operands::Value;

=head1 DESCRIPTION

This class represents an operation to extract the value from a variable.

=head1 METHODS

=head2 new(variable)

=over 4

=item * variable

- variable

=back

Create a value node.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'value'} = shift;
    $self->{'resultType'} = shift;
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

Fetch the value of the variable.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    my $variable = $self->{'value'};
    my ($result) = $variable->evaluate($state);
    return undef unless defined $result;
    return $result->getValue();
    }
#---------------------------------------------------------------------

1;
