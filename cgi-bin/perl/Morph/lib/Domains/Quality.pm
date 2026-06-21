package Quality;

  use strict;
  @Quality::ISA = qw( Required Persistent Integer );

# We have room to add more qualities
my %Values = 
  ( low => 1,
    medium => 500,
    high => 1000,
  );

=head1 NAME Quality

=head1 DESCRIPTION

This class represents a Persistent Quality constant.

=head1 METHODS

=head2 new($value)

=over 4

=item *

value - A string (a quality value).

=back

Create a new quality from the passed string value

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    my ($s) = shift;
    $s =~ s/\s*$//;
    $s =~ s/^\s*//;
    $self->{'original'} = $s;
    $self->{'value'} = &_initialize(lc $s);
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 validate()

Make sure value is legal, map to appropriate scale.

=cut

#---------------------------------------------------------------------
  sub validate {
    my ($s) = @_;
    # is it a quality
    return "undefined quality $s" unless defined $Values{lc $s};
    return undef;
    }
#---------------------------------------------------------------------

=head2 _initialize()

Make sure value is legal, map to appropriate scale.

=cut

#---------------------------------------------------------------------
  sub _initialize {
    my ($s) = @_;
    # is it a quality
    return $Values{$s};
    }
#---------------------------------------------------------------------

=head2 match()

Match this one against another.

=cut

#---------------------------------------------------------------------
  sub match {
    my ($self, $other) = @_;
    return $self->greaterThanOrEqualTo($other);
    }
#---------------------------------------------------------------------

=head2 collapse()

Collapse this one with another.

=cut

#---------------------------------------------------------------------
  sub collapse {
    my ($self, $other) = @_;
    return $self->max($other);
    }
#---------------------------------------------------------------------

=head2 coalesce()

Coalesce this one with another.

=cut

#---------------------------------------------------------------------
  sub coalesce {
    my ($self, $other) = @_;
    return $self->min($other);
    }
#---------------------------------------------------------------------

=head2 slice()

Slice this one using another.

=cut

#---------------------------------------------------------------------
  sub slice {
    my ($self, $other) = @_;
    return $self->max($other);
    }
#---------------------------------------------------------------------

=head2 presentation()

Present this string value.

=cut

#---------------------------------------------------------------------
  sub presentation {
    my ($self) = @_;
    return $self->{'original'};
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
