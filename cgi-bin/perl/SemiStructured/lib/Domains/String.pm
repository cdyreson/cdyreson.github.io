package String;

  use strict;
  # inherits the persistent
  @String::ISA = qw( Required Persistent );
 
=head1 NAME String

=head1 DESCRIPTION

A String Type

=head1 METHODS

=head2 new($value)

Create a new type from the passed string value, if no string
is passed die horribly.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    if (scalar(@_)) { $self->{'String'} = shift; }
    else { $self->{'String'} = ''; }
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 lessThanOrEqualTo(other)

String <= operation

=cut

#---------------------------------------------------------------------
  sub lessThanOrEqualTo {
    my ($self, $other) = @_;
    return undef unless defined $other->{'String'};
    return 1 if $self->{'String'} le $other->{'String'}; 
    return 0;
    }
#---------------------------------------------------------------------

=head2 greaterThanOrEqualTo(other)

String greaterThanOrEqualTo operation

=cut

#---------------------------------------------------------------------
  sub greaterThanOrEqualTo {
    my ($self, $other) = @_;
    return undef unless defined $other->{'String'};
    return 1 if $self->{'String'} ge $other->{'String'}; 
    return 0;
    }
#---------------------------------------------------------------------

=head2 notEqualTo(other)

String notEqualTo operation

=cut

#---------------------------------------------------------------------
  sub notEqualTo {
    my ($self, $other) = @_;
    return undef unless defined $other->{'String'};
    return 1 if $self->{'String'} ne $other->{'String'}; 
    return 0;
    }
#---------------------------------------------------------------------

=head2 lessThan(other)

String lessThan operation

=cut

#---------------------------------------------------------------------
  sub lessThan {
    my ($self, $other) = @_;
    return undef unless defined $other->{'String'};
    return 1 if $self->{'String'} lt $other->{'String'}; 
    return 0;
    }
#---------------------------------------------------------------------

=head2 greaterThan(other)

String greaterThan operation

=cut

#---------------------------------------------------------------------
  sub greaterThan {
    my ($self, $other) = @_;
    return undef unless defined $other->{'String'};
    return 1 if $self->{'String'} gt $other->{'String'}; 
    return 0;
    }
#---------------------------------------------------------------------

=head2 equalTo(other)

String equalTo operation

=cut

#---------------------------------------------------------------------
  sub equalTo {
    my ($self, $other) = @_;
    return undef unless defined $other->{'String'};
    return 1 if $self->{'String'} eq $other->{'String'}; 
    return 0;
    }
#---------------------------------------------------------------------

=head2 add(other)

String add (concat) operation

=cut

#---------------------------------------------------------------------
  sub add {
    my ($self, $other) = @_;
    return undef unless defined $other->{'String'};
    return $self->concat($other);
    }
#---------------------------------------------------------------------

=head2 concat(other)

String concatenation operation

=cut

#---------------------------------------------------------------------
  sub concat {
    my ($self, $other) = @_;
    return new String($self->{'String'} . '.' . $other->{'String'}); 
    }
#---------------------------------------------------------------------

=head2 union(other)

String union operation

=cut

#---------------------------------------------------------------------
  sub union {
    my ($self, $other) = @_;
    return new String($self->{'String'} . '|' . $other->{'String'}); 
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
    return $self->concat($other);
    }
#---------------------------------------------------------------------

=head2 coalesce()

Coalesce this one with another.

=cut

#---------------------------------------------------------------------
  sub coalesce {
    my ($self, $other) = @_;
    return $self->union($other);
    }
#---------------------------------------------------------------------

=head2 slice()

Slice this one using another.

=cut

#---------------------------------------------------------------------
  sub slice {
    my ($self, $other) = @_;
    my ($string) = $self->{'String'};
    my ($otherValue) = $other->{'String'};
    return $& if $string =~ /$otherValue/;
    return undef;
    }
#---------------------------------------------------------------------

=head2 presentation()

Present this string value.

=cut

#---------------------------------------------------------------------
  sub presentation {
    my ($self) = @_;
    return $self->{'String'};
    }
#---------------------------------------------------------------------

=head2 value()

Present this string value.

=cut

#---------------------------------------------------------------------
  sub value {
    my ($self) = @_;
    return $self->{'String'};
    }
#---------------------------------------------------------------------

=head2 convert(string toType)

Convert this string to a known type of object.
If it is not possible return undef.

=cut

#---------------------------------------------------------------------
  sub convert {
    my ($self, $toType) = @_;
 
    my ($value);
    return $self->{$toType} if defined $self->{$toType};
    $value = Integer->newIfPossible($self->{'String'}) if $toType eq 'Integer';
    $value = TimeInterval->newIfPossible($self->{'String'}) 
      if $toType eq 'TimeInterval';
    return undef unless defined $value;
    $self->{$toType} = $value;
    return $value;
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
