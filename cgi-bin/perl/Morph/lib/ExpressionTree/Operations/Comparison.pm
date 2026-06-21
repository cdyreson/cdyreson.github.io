package ExpressionTree::Operations::Comparison;

  use strict;

=head1 NAME ExpressionTree::Operations::Comparison

=head1 DESCRIPTION

This class represents any comparison operation.

=head1 METHODS

=head2 new($op, $left, $right)

=over 4

=item * op

The comparison operation.

=item * left

L<ExpressionTree> - The left child

=item * right

L<ExpressionTree> - The right child

=back

Create a new comparison operation

=cut

my $True = new Boolean('true');
my $False = new Boolean('false');
my $Maybe = new Boolean('maybe');

my %Operations = ( '=' => \&_eq, 
                   '<>' => \&_ne, 
                   '>'  => \&_gt,
                   '<'  => \&_lt,
                   '<=' => \&_le,
                   '>=' => \&_ge,
                   'meets' => \&_meets,
                   'overlaps' => \&_overlaps,
                   'contains' => \&_contains,
                   'precedes' => \&_precedes,
                 ); # end of %Operations hash table

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'op'} = lc shift;
    $self->{'left'} = shift;
    $self->{'right'} = shift;
    $self->{'coerceLeft'} = $self->{'left'}->resultType();
    $self->{'coerceRight'} = $self->{'right'}->resultType();
    $self->{'resultType'} = 'Boolean';
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 validate(op, leftType, rightType)

#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No operation for comparison." unless scalar(@_);
    my ($op) = shift;
    return "No left operand in comparison." unless scalar(@_);
    my ($leftType) = shift;
    return "No right operand in comparison." unless scalar(@_);
    my ($rightType) = shift;
    return "invalid operation $op" unless defined $Operations{$op};
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
    $indent = "  " unless defined $indent;
    my ($extra) = '  ';
    my ($op) = $self->{'op'};

    return "$indent $op\n   " . $self->{'left'}->dump("$indent$extra") . 
           "$indent    \n   " . $self->{'right'}->dump("$indent$extra") . "\n";
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

Evaluate the comparison operation returning a Boolean result.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    my $left = $self->{'left'}->evaluate($state);
    return $Maybe unless defined $left;
    my $right = $self->{'right'}->evaluate($state);
    return $Maybe unless defined $right;

    #
    # Coerce the left value if necessary
    #
    if (!defined $self->{'coerceLeft'}) { 
      if (defined $self->{'coerceRight'}) { 
        $left = Dimensions::convert($self->{'coerceRight'}, 
                                    $left->presentation());
        }
      #
      # Coerce both to strings!  
      #
      else {
        $left = $left->presentation();
        $right = $right->presentation();
        }
      }
    else {
      #
      # Coerce the right value if necessary
      #
      if (!defined $self->{'coerceRight'}) { 
        $right = Dimensions::convert($self->{'coerceLeft'}, 
                                    $right->presentation());
        }
      }

    my ($op) = $self->{'op'};
    my ($routine) = $Operations{$op};
    return &$routine($left, $right);
    }
#---------------------------------------------------------------------

sub _le {
  my ($left, $right) = @_;
  my ($result) = $left->lessThanOrEqualTo($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }
sub _ge {
  my ($left, $right) = @_;
  my ($result) = $left->greaterThanOrEqualTo($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }
sub _gt {
  my ($left, $right) = @_;
  my ($result) = $left->greaterThan($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }
sub _lt {
  my ($left, $right) = @_;
  my ($result) = $left->lessThan($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }
sub _ne {
  my ($left, $right) = @_;
  my ($result) = $left->notEqualTo($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }
sub _eq {
  my ($left, $right) = @_;
  my ($result) = $left->equalTo($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }
sub _meets {
  my ($left, $right) = @_;
  my ($result) = $left->meets($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }
sub _contains {
  my ($left, $right) = @_;
  my ($result) = $left->contains($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }
sub _overlaps {
  my ($left, $right) = @_;
  my ($result) = $left->overlaps($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }
sub _precedes {
  my ($left, $right) = @_;
  my ($result) = $left->precedes($right);
  return $Maybe unless defined $result;
  return $True if $result;
  return $False;
  }

1;
