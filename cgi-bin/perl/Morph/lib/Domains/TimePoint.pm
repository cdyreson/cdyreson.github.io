package TimePoint;

  use strict;
  @TimePoint::ISA = qw( Required Persistent );

  my %MonthToInteger = (
       'jan' => 1,
       'feb' => 2,
       'mar' => 3,
       'apr' => 4,
       'may' => 5,
       'jun' => 6,
       'jul' => 7,
       'aug' => 8,
       'sep' => 9,
       'oct' => 10,
       'nov' => 11,
       'dec' => 12
       );

  my %IntegerToMonth = (
       1 => 'jan',
       2 => 'feb',
       3 => 'mar',
       4 => 'apr',
       5 => 'may',
       6 => 'jun',
       7 => 'jul',
       8 => 'aug',
       9 => 'sep',
       10 => 'oct',
       11 => 'nov',
       12 => 'dec'
       );

=head1 NAME TimePoint

=head1 DESCRIPTION

This class represents a Persistent TimePoint constant.

=head1 METHODS

=head2 new($value)

=over 4

=item *

value - A string (a date really).

=back

Create a new type from the passed string value, if no string assume 'now'.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    my ($s);
    if (scalar(@_)) { $s = shift; }
    else { $s = 'now'; }
    &_initialize($self, $s);
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 validate(string s)

=cut

#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    my $self = {}; 
    return undef unless scalar(@_);
    my $s = shift;
    my $check = &_initialize($self, $s);
    return $check if defined $check;
    return undef;
    }
#---------------------------------------------------------------------

=head2 equalTo(other)

TimePoint equalTo operation

=cut

#---------------------------------------------------------------------
  sub equalTo {
    my ($self, $other) = @_;
    if (defined $self->{'time'}) {
      return $self->{'time'} == $other->{'time'} if defined $other->{'time'};
      return 0;
      }
    if (defined $self->{'beginning'}) {
      return defined $other->{'beginning'};
      }
    elsif (defined $self->{'forever'}) {
      return defined $other->{'forever'};
      }
    # must be now
    return defined $other->{'now'};
    }
#---------------------------------------------------------------------

=head2 greaterThan(other)

TimePoint greaterThan operation

=cut

#---------------------------------------------------------------------
  sub greaterThan {
    my ($self, $other) = @_;
    if (defined $self->{'time'}) {
      return $self->{'time'} > $other->{'time'} if defined $other->{'time'};
      return 1 if defined $other->{'beginning'};
      return 0 if defined $other->{'forever'};
      return $self->{'time'} > CurrentTime->time();
      }
    elsif (defined $self->{'forever'}) {
      return !defined $other->{'forever'};
      }
    elsif (defined $self->{'beginning'}) {
      return 0;
      }
    # must be now
    return 1 if defined $other->{'beginning'};
    return 0 if defined $other->{'forever'};
    return 0 if defined $other->{'now'};
    return CurrentTime->time() > $other->{'time'};
    }
#---------------------------------------------------------------------

=head2 lessThan(other)

TimePoint lessThan operation

=cut

#---------------------------------------------------------------------
  sub lessThan {
    my ($self, $other) = @_;
    if (defined $self->{'time'}) {
      return $self->{'time'} < $other->{'time'} if defined $other->{'time'};
      return 0 if defined $other->{'beginning'};
      return 1 if defined $other->{'forever'};
      return $self->{'time'} < CurrentTime->time();
      }
    elsif (defined $self->{'forever'}) {
      return 0;
      }
    elsif (defined $self->{'beginning'}) {
      return !defined $other->{'beginning'};
      }
    # must be now
    return 0 if defined $other->{'beginning'};
    return 1 if defined $other->{'forever'};
    return 0 if defined $other->{'now'};
    return CurrentTime->time() < $other->{'time'};
    }
#---------------------------------------------------------------------

=head2 lessThanOrEqualTo(other)

TimePoint lessThanOrEqualTo operation

=cut

#---------------------------------------------------------------------
  sub lessThanOrEqualTo {
    my ($self, $other) = @_;
    if (defined $self->{'time'}) {
      return $self->{'time'} <= $other->{'time'} if defined $other->{'time'};
      return 0 if defined $other->{'beginning'};
      return 1 if defined $other->{'forever'};
      return $self->{'time'} <= CurrentTime->time();
      }
    elsif (defined $self->{'forever'}) {
      return defined $other->{'forever'};
      }
    elsif (defined $self->{'beginning'}) {
      return 1;
      }
    # must be now
    return 0 if defined $other->{'beginning'};
    return 1 if defined $other->{'forever'};
    return 1 if defined $other->{'now'};
    return CurrentTime->time() <= $other->{'time'};
    }
#---------------------------------------------------------------------

=head2 greaterThanOrEqualTo(other)

TimePoint greaterThanOrEqualTo operation

=cut

#---------------------------------------------------------------------
  sub greaterThanOrEqualTo {
    my ($self, $other) = @_;
    if (defined $self->{'time'}) {
      return $self->{'time'} >= $other->{'time'} if defined $other->{'time'};
      return 1 if defined $other->{'beginning'};
      return 0 if defined $other->{'forever'};
      return $self->{'time'} >= CurrentTime->time();
      }
    elsif (defined $self->{'forever'}) {
      return 1;
      }
    elsif (defined $self->{'beginning'}) {
      return defined $other->{'beginning'};
      }
    # must be now
    return 1 if defined $other->{'beginning'};
    return 0 if defined $other->{'forever'};
    return 1 if defined $other->{'now'};
    return CurrentTime->time() >= $other->{'time'};
    }
#---------------------------------------------------------------------

=head2 notEqualTo(other)

TimePoint notEqualTo operation

=cut

#---------------------------------------------------------------------
  sub notEqualTo {
    my ($self, $other) = @_;
    if (defined $self->{'time'}) {
      return $self->{'time'} != $other->{'time'} if defined $other->{'time'};
      return 1;
      }
    if (defined $self->{'beginning'}) {
      return !defined $other->{'beginning'};
      }
    elsif (defined $self->{'forever'}) {
      return !defined $other->{'forever'};
      }
    # must be now for self
    return !defined $other->{'now'};
    }
#---------------------------------------------------------------------

=head2 min(other)

TimePoint min operation

=cut

#---------------------------------------------------------------------
  sub min {
    my ($self, $other) = @_;
    return $self if $self->lessThan($other);
    return $other;
    }
#---------------------------------------------------------------------

=head2 max(other)

TimePoint max operation

=cut

#---------------------------------------------------------------------
  sub max {
    my ($self, $other) = @_;
    return $other if $self->lessThan($other);
    return $self;
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
    return $self if $self->lessThan($other);
    return $other
    }
#---------------------------------------------------------------------

=head2 coalesce()

Coalesce this one with another.

=cut

#---------------------------------------------------------------------
  sub coalesce {
    my ($self, $other) = @_;
    return $self if $self->greaterThan($other);
    return $other
    }
#---------------------------------------------------------------------

=head2 slice()

Slice this one using another.

=cut

#---------------------------------------------------------------------
  sub slice {
    my ($self, $other) = @_;
    return $self if $self->lessThan($other);
    return $other
    }
#---------------------------------------------------------------------

=head2 dump()

Return a text image of the TimePoint.

=cut

#---------------------------------------------------------------------
  sub dump {
    my ($self) = @_;
    return "forever\n" if defined $self->{'forever'};
    return "now\n" if defined $self->{'now'};
    return "beginning\n" if defined $self->{'beginning'};
    return "time is " . $self->{'time'} . "\n" if defined $self->{'time'};
    return "no time!!!";
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


=head2 _initialize()

Make sure time value is legal.

=cut

#---------------------------------------------------------------------
  sub _initialize {
    my ($self, $s) = @_;
    $s =~ s/\s*$//;
    $s =~ s/^\s*//;
    # is it a number
    $self->{'value'} = $s;
    $s = lc $s;
    if ($s =~ /^beginning$/) { $self->{'beginning'} = 1; return undef; }
    if ($s =~ /^forever$/) { $self->{'forever'} = 1; return undef; }
    if ($s =~ /^now$/) { $self->{'now'} = 1; return undef; }
    if ($s =~ /^uc$/) { $self->{'now'} = 1; return undef; }
    if ($s =~ /^(\d+)\/(\w+)\/(\d+)$/) { 
      # compute an ordering on the dates for quick comparison
      $self->{'time'} = ($3 * 1024) + ($MonthToInteger{$2} * 32) + $1;
      return undef; 
      }
    return "Bad Time Point Constant: '$s'";
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
