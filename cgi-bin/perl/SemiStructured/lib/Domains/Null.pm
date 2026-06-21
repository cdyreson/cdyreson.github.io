package Null;

  use strict;

=head1 NAME Null

=head1 DESCRIPTION

This class represents a Null value constant.

=head1 METHODS

=head2 new()

Create a new Null value.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 isNull()

Is this a null value?

=cut

#---------------------------------------------------------------------
  sub isNull {
    my ($self) = @_;
    return new Boolean('true');
    }
#---------------------------------------------------------------------

=head2 match()

Match this one against another.

=cut

#---------------------------------------------------------------------
  sub match {
    my ($self, $other) = @_;
    return new Boolean('maybe');
    }
#---------------------------------------------------------------------

=head2 collapse()

Collapse this one with another.

=cut

#---------------------------------------------------------------------
  sub collapse {
    my ($self, $other) = @_;
    return $self;
    }
#---------------------------------------------------------------------

=head2 coalesce()

Coalesce this one with another.

=cut

#---------------------------------------------------------------------
  sub coalesce {
    my ($self, $other) = @_;
    return $self;
    }
#---------------------------------------------------------------------

=head2 slice()

Slice this one using another.

=cut

#---------------------------------------------------------------------
  sub slice {
    my ($self, $other) = @_;
    return $self;
    }
#---------------------------------------------------------------------

=head2 presentation()

Present this string value.

=cut

#---------------------------------------------------------------------
  sub presentation {
    my ($self) = @_;
    return '@';
    }
#---------------------------------------------------------------------

=head2 getValue()

Present this value, it is undefined.

=cut

#---------------------------------------------------------------------
  sub getValue {
    my ($self) = @_;
    return undef;
    }
#---------------------------------------------------------------------

=head2 value()

Present this string value.

=cut

#---------------------------------------------------------------------
  sub value {
    my ($self) = @_;
    return '@';
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
