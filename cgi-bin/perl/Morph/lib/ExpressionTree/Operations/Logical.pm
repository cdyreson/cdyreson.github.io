package ExpressionTree::Operations::Logical;

  use strict;

=head1 NAME ExpressionTree::Operations::Logical;

=head1 DESCRIPTION

This class represents any logical operation.

=head1 METHODS

=head2 new($op, $left, $right)

=over 4

=item * op

The logical operation.

=item * left

L<ExpressionTree> $left - The left child

=item * right

L<ExpressionTree> $right - The right child

=back

Create a new logical operation

=cut

my %Operations = ( 'and' => \&_and, 
                   'or' => \&_or,
                   'not' => \&_not,
                 );

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'op'} = shift;
    $self->{'left'} = shift;
    $self->{'right'} = shift if scalar(@_);
    $self->{'resultType'} = 'Boolean';
    bless $self, $type;
    }
#---------------------------------------------------------------------

#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No operation for comparison." unless scalar(@_);
    my ($op) = shift;
    return "No left operand in comparison." unless scalar(@_);
    my ($left) = shift;
    my ($right) = shift if scalar(@_);
    return "Left operand must be a boolean" 
      unless $left->resultType() eq 'Boolean';
    if (defined $right) {
      return "Right operand must be a boolean "
        unless $right->resultType() eq 'Boolean';
      }
    return "$op is not a logical operation." unless defined $Operations{$op};
    return undef; # successful validation!
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
    my ($s) = "$indent $op\n   " . $self->{'left'}->dump("$indent$extra");
    return "$s\n" unless defined $self->{'right'};
    return "$s$indent    \n   ". $self->{'right'}->dump("$indent$extra") . "\n";
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

Evaluate the logical operation returning a result.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    my $left = $self->{'left'}->evaluate($state);
    my $right;
    if (defined $self->{'right'}) {
      $right = $self->{'right'}->evaluate($state);
      }
    else {
      # dummy value
      $right = new Boolean('true');
      }

    my ($op) = $self->{'op'};
    my ($routine) = $Operations{$op};
    return &$routine($left, $right);
    }
#---------------------------------------------------------------------

sub _and {
  my ($left, $right) = @_;
  return $left->and($right);
  }

sub _or {
  my ($left, $right) = @_;
  return $left->or($right);
  }

sub _not {
  my ($left) = @_;
  return $left->not();
  }

1;
