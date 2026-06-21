package Integer;

  use strict;
  @Integer::ISA = qw( Required Persistent );

=head1 NAME Integer

=head1 DESCRIPTION

This class represents a Persistent Integer constant.

=head1 METHODS

=head2 new($value)

=over 4

=item *

value - A string (an Integer really).

=back

Create a new type from the passed string value, if no string assume zero.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    my ($s);
    if (scalar(@_)) { $s = shift; }
    else { $s = 0; }
    $s =~ s/\s*$//;
    $s =~ s/^\s*//;
    $self->{'value'} = $s;
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 equalTo(other)

Integer equalTo operation

=cut

#---------------------------------------------------------------------
  sub equalTo {
    my ($self, $other) = @_;
    return 1 if $self->{'value'} == $other->{'value'};
    return 0;
    }
#---------------------------------------------------------------------

=head2 greaterThan(other)

Integer greaterThan operation

=cut

#---------------------------------------------------------------------
  sub greaterThan {
    my ($self, $other) = @_;
    return 1 if $self->{'value'} > $other->{'value'};
    return 0;
    }
#---------------------------------------------------------------------

=head2 lessThan(other)

Integer lessThan operation

=cut

#---------------------------------------------------------------------
  sub lessThan {
    my ($self, $other) = @_;
    return 1 if $self->{'value'} < $other->{'value'};
    return 0;
    }
#---------------------------------------------------------------------

=head2 lessThanOrEqualTo(other)

Integer lessThanOrEqualTo operation

=cut

#---------------------------------------------------------------------
  sub lessThanOrEqualTo {
    my ($self, $other) = @_;
    return 1 if $self->{'value'} <= $other->{'value'};
    return 0;
    }
#---------------------------------------------------------------------

=head2 greaterThanOrEqualTo(other)

Integer greaterThanOrEqualTo operation

=cut

#---------------------------------------------------------------------
  sub greaterThanOrEqualTo {
    my ($self, $other) = @_;
    return 1 if $self->{'value'} >= $other->{'value'};
    return 0;
    }
#---------------------------------------------------------------------

=head2 notEqualTo(other)

Integer notEqualTo operation

=cut

#---------------------------------------------------------------------
  sub notEqualTo {
    my ($self, $other) = @_;
    return 1 if $self->{'value'} != $other->{'value'};
    return 0;
    }
#---------------------------------------------------------------------

=head2 min(other)

Integer min operation

=cut

#---------------------------------------------------------------------
  sub min {
    my ($self, $other) = @_;
    return $self if $self->{'value'} < $other->{'value'};
    return $other;
    }
#---------------------------------------------------------------------

=head2 max(other)

Integer max operation

=cut

#---------------------------------------------------------------------
  sub max {
    my ($self, $other) = @_;
    return $other if $self->{'value'} < $other->{'value'};
    return $self;
    }
#---------------------------------------------------------------------

=head2 add(other)

Integer add operation

=cut

#---------------------------------------------------------------------
  sub add {
    my ($self, $other) = @_;
    return new Integer($self->{'value'} + $other->{'value'});
    }
#---------------------------------------------------------------------

=head2 subtract(other)

Integer subtract operation

=cut

#---------------------------------------------------------------------
  sub subtract {
    my ($self, $other) = @_;
    return new Integer($self->{'value'} - $other->{'value'});
    }
#---------------------------------------------------------------------

=head2 multiply(other)

Integer multiply operation

=cut

#---------------------------------------------------------------------
  sub multiply {
    my ($self, $other) = @_;
    return new Integer($self->{'value'} * $other->{'value'});
    }
#---------------------------------------------------------------------

=head2 divide(other)

Integer divide operation

=cut

#---------------------------------------------------------------------
  sub divide {
    my ($self, $other) = @_;
    return new Integer($self->{'value'} / $other->{'value'})
      unless $other->value() == 0;
    # division by zero
    return new Integer(0);
    }
#---------------------------------------------------------------------

=head2 validate()

Make sure Integer is legal.

=cut

#---------------------------------------------------------------------
  sub validate {
    my ($s) = @_;
    $s =~ s/\s*$//;
    $s =~ s/^\s*//;
    # is it a number
    if ($s =~ /^\d+$/) { return undef; }
    return "Bad Integer Value: '$s'";
    }
#---------------------------------------------------------------------

=head2 match()

Match this one against another.

=cut

#---------------------------------------------------------------------
  sub match {
    my ($self, $other) = @_;
    return $self->equalTo($other);
    }
#---------------------------------------------------------------------

=head2 collapse()

Collapse this one with another.

=cut

#---------------------------------------------------------------------
  sub collapse {
    my ($self, $other) = @_;
    return $self->min($other);
    }
#---------------------------------------------------------------------

=head2 coalesce()

Coalesce this one with another.

=cut

#---------------------------------------------------------------------
  sub coalesce {
    my ($self, $other) = @_;
    return $self->max($other);
    }
#---------------------------------------------------------------------

=head2 slice()

Slice this one using another.

=cut

#---------------------------------------------------------------------
  sub slice {
    my ($self, $other) = @_;
    return $self->min($other);
    }
#---------------------------------------------------------------------

=head2 presentation()

Present this string value.

=cut

#---------------------------------------------------------------------
  sub presentation {
    my ($self) = @_;
    return $self->{'value'};
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

Self evaluating

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self) = @_;
    return $self;
    }
#---------------------------------------------------------------------

1;
