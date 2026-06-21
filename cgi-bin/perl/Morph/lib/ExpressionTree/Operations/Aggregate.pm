package ExpressionTree::Operations::Aggregate;

   use strict;

=head1 NAME ExpressionTree::Operations::Aggregate;

=head1 DESCRIPTION

This class represents an aggregate operation.

=head1 METHODS

=head2 new($op, $var)

=over 4

=item * op

The aggregate operation.

=item * var

L<ExpressionTree::Operands::Variable> $var - the column on which to compute it

=back

Create a new aggregate operation

=cut

my %Operations = ( 'sum' => \&_sum, 
                   'count' => \&_count,
                   'avg' => \&_avg,
                   'min' => \&_min,
                   'max' => \&_max,
                 );

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'op'} = shift;
    $self->{'var'} = shift;
    $self->{'resultType'} = 'integer';
    bless $self, $type;
    }
#---------------------------------------------------------------------
#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No operation for aggregation ." unless scalar(@_);
    my ($op) = shift;
    return "No variable to aggregate." unless scalar(@_);
    return "$op is not an aggregate operation." unless defined $Operations{$op};
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
    return "$indent $op\n   " . $self->{'var'}->dump("$indent$extra") . "\n";
    }

#---------------------------------------------------------------------

=head2 evaluate(State state)

Evaluate the aggregate operation returning a result.
This goes through every tuple in the current group
and computes the aggregate on the appropriate column.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    my $var = $self->{'var'}->fullEvaluate($state);
    return new Null() unless defined $var;
    my ($op) = $self->{'op'};
    my ($routine) = $Operations{$op};
    my $value;
    foreach my $answer (@$var) {
      &$routine(\$value, $answer->evaluate($state));
      }
    return $value;
    }
#---------------------------------------------------------------------

sub _count {
  my ($ref, $value) = @_;
  $$ref = 0 unless defined $$ref;
  $$ref++ unless $value eq '&null';
  }

sub _sum {
  my ($ref, $value) = @_;
  $$ref = 0 unless defined $$ref;
  $$ref += $value unless $value eq '&null';
  }

sub _min {
  my ($ref, $value) = @_;
  $$ref = $value unless defined $$ref;
  return if $value eq '&null';
  $$ref = $value if $value lt $$ref;
  }

sub _max {
  my ($ref, $value) = @_;
  $$ref = $value unless defined $$ref;
  return if $value eq '&null';
  $$ref = $value if $value gt $$ref;
  }

sub _avg {
  my ($ref, $value) = @_;
  die "avg is currently unsupported";
  }

1;
