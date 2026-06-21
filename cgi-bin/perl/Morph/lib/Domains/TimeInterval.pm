package TimeInterval;

  use strict;
  # inherits the persistent
  @TimeInterval::ISA = qw( Required Persistent );
 
use Carp;

=head1 NAME TimeInterval

=head1 DESCRIPTION

A TimeInterval Type

=head1 METHODS

=head2 new(Constant value)

Create a new type from the passed string value, if no string
is passed create an interval over all time.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    my ($s);
    if (scalar(@_)) { $s = shift; }
    else { $s = 'beginning-forever'; }
    $s =~ s/\s*$//;
    $s =~ s/^\s*//;
    my @temp = split("-", $s);
    $self->{'lower'} = new TimePoint(shift @temp);
    $self->{'upper'} = new TimePoint(shift @temp) || $self->{'lower'};
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 validate(Constant s)

Check to see that this constant is a legal string value.
Currently only Gregorian calendar days is supported.
All times must be of the form '12/feb/1998' where
the day is between 1 and 31 (inclusive), the month must
be a legal 3 character English abbreviation of a month and
the year must be four digits.

=cut

#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    my $s = shift;
    $s =~ s/\s*$//;
    $s =~ s/^\s*//;
    my @temp = split("-", $s);
    return "bad time interval constant, no - $s" unless scalar(@temp) == 2;
    my $checkLower = TimePoint->validate($temp[0]);
    return $checkLower if defined $checkLower;
    my $checkUpper = TimePoint->validate($temp[1]);
    return $checkUpper if defined $checkUpper;
    my $lower = new TimePoint(shift @temp);
    my $upper = new TimePoint(shift @temp);
    return "Bad time interval" unless $lower->lessThanOrEqualTo($upper);
    return undef;
    }
#---------------------------------------------------------------------

=head2 overlaps(other)

Return the result of an overlaps operation.

=cut

#---------------------------------------------------------------------
  sub overlaps {
    my ($self, $other) = @_;
    return 0 if $self->{'upper'}->lessThan($other->{'lower'}); 
    return 0 if $self->{'lower'}->greaterThan($other->{'upper'}); 
    return 1;
    }
#---------------------------------------------------------------------

=head2 contains(other)

Return the result of a contains operation.

=cut

#---------------------------------------------------------------------
  sub contains {
    my ($self, $other) = @_;
    return 0 if $self->{'upper'}->lessThan($self->{'upper'}); 
    return 0 if $self->{'lower'}->greaterThan($self->{'lower'}); 
    return 1;
    }
#---------------------------------------------------------------------

=head2 intersect(other)

Return the intersection of the two intervals.

=cut

#---------------------------------------------------------------------
  sub intersect {
    my ($self, $other) = @_;

    # undefined if no overlap
    return undef unless $self->overlaps($other);

    # clone self
    my ($result) = new TimeInterval('beginning-forever');
    my ($rh) = $result->{'value'};
    $result->{'lower'} = $self->{'lower'};
    $result->{'lower'} = $other->{'lower'} 
      if $self->{'lower'}->lessThan($other->{'lower'}); 
    $result->{'upper'} = $self->{'upper'};
    $result->{'upper'} = $other->{'upper'} 
      if $self->{'upper'}->greaterThan($other->{'upper'});
    return $result;
    }
#---------------------------------------------------------------------

=head2 union(TimeInterval other)

Return the union of the two intervals, currently chooses
the maximum extent.  In future, it should return a temporal
element.

=cut

#---------------------------------------------------------------------
  sub union {
    my ($self, $other) = @_;
    # clone self
    my ($result) = new TimeInterval('beginning-forever');
    $result->{'lower'} = $self->{'lower'};
    $result->{'lower'} = $other->{'lower'} 
      if $self->{'lower'}->greaterThan($other->{'lower'}); 
    $result->{'upper'} = $self->{'upper'};
    $result->{'upper'} = $other->{'upper'} 
      if $self->{'upper'}->lessThan($other->{'upper'});
    return $result;
    }
#---------------------------------------------------------------------

=head2 match(TimeInterval other)

Match this one against another.

=cut

#---------------------------------------------------------------------
  sub match {
    my ($self, $other) = @_;
    return $self->overlaps($other);
    }
#---------------------------------------------------------------------

=head2 collapse(TimeInterval other)

Collapse this one with another using intersection.

=cut

#---------------------------------------------------------------------
  sub collapse {
    my ($self, $other) = @_;
    return $self->intersect($other);
    }
#---------------------------------------------------------------------

=head2 coalesce(TimeInterval other)

Coalesce this one with another.

=cut

#---------------------------------------------------------------------
  sub coalesce {
    my ($self, $other) = @_;
    return $self->union($other);
    }
#---------------------------------------------------------------------

=head2 slice(TimeIntveral other)

Slice this one using another.

=cut

#---------------------------------------------------------------------
  sub slice {
    my ($self, $other) = @_;
    return $self->intersect($other);
    }
#---------------------------------------------------------------------

=head2 presentation()

Present this string value.

=cut

#---------------------------------------------------------------------
  sub presentation {
    my ($self) = @_;
    return $self->{'lower'}->presentation() . 
       "-" . $self->{'upper'}->presentation();
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
