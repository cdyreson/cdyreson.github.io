package ExpressionTree::Operations::Arithmetic;

  use strict;

=head1 NAME ExpressionTree::Operations::Arithmetic

=head1 DESCRIPTION

This class represents any arithmetic operation.

=head1 METHODS

=head2 new($op, $left, $right)

=over 4

=item * op

The arithmetic operation.

=item * left

L<ExpressionTree> $left - The left child

=item * right

L<ExpressionTree> $right - The right child

=back

Create a new arithmetic operation

=cut

my %Operations = ( 
  '+' => \&_add,
  '-' => \&_subtract,
  '*' => \&_multiply,
  '/' => \&_divide,
  ); # end of %Operations hash table

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'op'} = shift;
    $self->{'left'} = shift;
    $self->{'right'} = shift;
    $self->{'resultType'} = $self->{'left'}->resultType();
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 validate(op, left, right)

#---------------------------------------------------------------------
  sub validate {
    my $type = shift;

    return "No operation for arithmetic." unless scalar(@_);
    my $op = shift;
    return "No left operand in arithmetic." unless scalar(@_);
    shift;
    return "No right operand in arithmetic." unless scalar(@_);
    shift;
    return "invalid operation $op" unless defined $Operations{$op};
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
    $indent = "  " unless defined $indent;
    my ($extra) = '  ';
    my ($op) = $self->{'op'};

    return "$indent $op\n   " . $self->{'left'}->dump("$indent$extra") . 
           "$indent    \n   " . $self->{'right'}->dump("$indent$extra") . "\n";
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

Evaluate the arithmetic operation returning a boolean result.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    my $left = $self->{'left'}->evaluate($state);
    return undef unless defined $left;
    my $right = $self->{'right'}->evaluate($state);
    return undef unless defined $right;
    my ($op) = $self->{'op'};
    my ($routine) = $Operations{$op};
    return &$routine($left, $right);
    }
#---------------------------------------------------------------------

sub _add {
  my ($left, $right) = @_;
  return $left->add($right);
  }

sub _subtract {
  my ($left, $right) = @_;
  return $left->subtract($right);
  }

sub _multiply {
  my ($left, $right) = @_;
  return $left->multiply($right);
  }

sub _divide {
  my ($left, $right) = @_;
  return $left->divide($right);
  }

1;
