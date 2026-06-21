package Dollar;

  use strict;
  @Dollar::ISA = qw( Required Persistent Integer );

=head1 NAME Dollar

=head1 DESCRIPTION

This class represents a Persistent Dollar amount.

=head1 METHODS

=head2 new($value)

=over 4

=item *

value - A string (a dollar value).

=back

Create a new type from the passed string value.

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
    $self->{'original'} = $s;
    $s =~ s/\$//;
    $self->{'value'} = $s + 0;
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 validate()

Make sure Dollar amount is legal.

=cut

#---------------------------------------------------------------------
  sub validate {
    my ($s) = @_;
    # is it a number
    if ($s =~ /^\d+$/) { return undef; }
    return "Bad Dollar Value: '$s'";
    }
#---------------------------------------------------------------------

=head2 match()

Match this one against another.

=cut

#---------------------------------------------------------------------
  sub match {
    my ($self, $other) = @_;
    return $self->lessThanOrEqualTo($other);
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
