package ExpressionTree::Variable;

  @ExpressionTree::Variable::ISA = qw( ExpressionTree::Operand );

=head1 NAME ExpressionTree::Operands::Variable;

=head1 DESCRIPTION

This class represents a variable.

=head1 METHODS

=head2 new($variable)

=over 4

=item *

variable - name of variable

=back

Create a variable node.

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

=head2 variableName ()

Fetch the name of the variable

=cut

#---------------------------------------------------------------------
  sub variableName {
    my ($self) = @_;
    return $self->{'value'};
    }
#---------------------------------------------------------------------

=head2 evaluate()

Fetch the value of the variable.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    my $variable = $self->{'value'};
    my $variables = $$state{'variables'};
    die "undefined variable $variable" unless defined $$variables{$variable};
    my $value = $$variables{$variable};
    return $value;
    }
#---------------------------------------------------------------------

=head2 fullEvaluate()

Fetch the value of the variable.

=cut

#---------------------------------------------------------------------
  sub fullEvaluate {
    my ($self, $state) = @_;

    my $variable = $self->{'value'};
    my $variables = $$state{'fullVariables'};
    die "undefined variable $variable" unless defined $$variables{$variable};
    my $value = $$variables{$variable};
    return $value;
    }
#---------------------------------------------------------------------

1;
