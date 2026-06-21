package ExpressionTree::Operand;

=head1 NAME ExpressionTree::Operand;

=head1 DESCRIPTION

This class represents an basic operand kind of object.
It is specialized by all the other kinds of operands.

=head1 METHODS

=head2 new(Domain object)

=over 4

=item * object

- The actual value of the operand.

=back

Creates an operand node in the tree.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'value'} = shift;
    $self->{'resultType'} = ref($self->{'value'});
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 validate(Domain object)

=over 4

=item * object

- The actual value of the operand.

=back

Validate that the arguments are OK.

=cut

#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No operand value." unless scalar(@_);
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

=head2 dump(Integer indent)

Dump this node.

=cut

#---------------------------------------------------------------------
  sub dump {
    my ($self, $indent) = @_;
    return "$indent " . $self->resultType() . " " . 
                            $self->{'value'}->value() . "\n";
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

Evaluate the operand, get the value.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;
    return $self->{'value'};
    }
#---------------------------------------------------------------------

1;
