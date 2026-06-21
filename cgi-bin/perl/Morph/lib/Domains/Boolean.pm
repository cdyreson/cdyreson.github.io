package Boolean;

  use strict;

=head1 NAME Boolean

=head1 DESCRIPTION

This class represents a Boolean constant (in three-valued logic).

=head1 METHODS

=head2 new(String value)

=over 4

=item *

value - A boolean value ('true', 'false', or 'maybe').

=back

Create a new Boolean from the passed string value, if no string assume false.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    my $s = shift;
    $s =~ s/\s*$//;
    $s =~ s/^\s*//;
    $s = lc $s;
    $self->{'value'} = $s;
    bless $self, $type;
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

=head2 isMaybe()

Is this a maybe value?

=cut

#---------------------------------------------------------------------
  sub isMaybe {
    my ($self) = @_;
    return $self->{'value'} eq 'maybe';
    }
#---------------------------------------------------------------------

=head2 isTrue()

Is this a true value?

=cut

#---------------------------------------------------------------------
  sub isTrue{
    my ($self) = @_;
    return $self->{'value'} eq 'true';
    }
#---------------------------------------------------------------------

=head2 isFalse()

Is this a false value?

=cut

#---------------------------------------------------------------------
  sub isFalse {
    my ($self) = @_;
    return $self->{'value'} eq 'false';
    }
#---------------------------------------------------------------------

=head2 not()

Logical NOT operation

=cut

#---------------------------------------------------------------------
  sub not {
    my ($self) = @_;
    return $self if $self->isMaybe();
    return new Boolean('false') if $self->isTrue;
    return new Boolean('true');
    }
#---------------------------------------------------------------------

=head2 and(other)

Logical AND operation

=cut

#---------------------------------------------------------------------
  sub and {
    my ($self, $other) = @_;
    return $self if $self->isFalse();
    return $other if $self->isTrue();
    return $self if $other->isTrue();
    return $other;
    }
#---------------------------------------------------------------------

=head2 or(other)

Logical OR operation

=cut

#---------------------------------------------------------------------
  sub or {
    my ($self, $other) = @_;
    return $self if $self->isTrue();
    return $other if $self->isFalse();
    return $self if $other->isFalse();
    return $other;
    }
#---------------------------------------------------------------------

=head2 validate()

Make sure Boolean is legal.

=cut

#---------------------------------------------------------------------
  sub validate {
    my ($s) = @_;
    $s =~ s/\s*$//;
    $s =~ s/^\s*//;
    $s = lc $s;
    return undef if ($s eq 'true');
    return undef if ($s eq 'false');
    return undef if ($s eq 'maybe');
    return "Bad Boolean Value: '$s'";
    }
#---------------------------------------------------------------------

=head2 match()

Match this one against another.

=cut

#---------------------------------------------------------------------
  sub match {
    my ($self, $other) = @_;
    return $self->and($other);
    }
#---------------------------------------------------------------------

=head2 collapse()

Collapse this one with another.

=cut

#---------------------------------------------------------------------
  sub collapse {
    my ($self, $other) = @_;
    return $self->and($other);
    }
#---------------------------------------------------------------------

=head2 coalesce()

Coalesce this one with another.

=cut

#---------------------------------------------------------------------
  sub coalesce {
    my ($self, $other) = @_;
    return $self->or($other);
    }
#---------------------------------------------------------------------

=head2 slice()

Slice this one using another.

=cut

#---------------------------------------------------------------------
  sub slice {
    my ($self, $other) = @_;
    return $self->and($other);
    }
#---------------------------------------------------------------------

1;
