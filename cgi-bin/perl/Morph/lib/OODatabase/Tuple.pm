package Tuple;

=head1 NAME Tuple

This package is part of L<OODatabase>.
For more information on the database see the L<OODatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<OODatabase::Licence> and L<OODatabase::Version>.

=head1 DESCRIPTION

A tuple is a pairing of a key and a value.  

=head1 METHODS

=head2 new(Object key, Object value)

=over 4

=item * key

- The key for the tuple, a L<OODatabase::Persistent> object.

=item * value

- The value for the tuple, a L<OODatabase::Persistent> object.

=back

Create a tuple with the given key, value pair.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $key = shift;
    my $value = shift;
    my $self = {};
    $self->{'key'} = $key->toBytes();
    $self->{'value'} = $value->toBytes() if defined $value;
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 newAlreadySwizzled(ByteString key, ByteString value)

=over 4

=item * key

- The key for the tuple, an already swizzled L<OODatabase::Persistent> object.

=item * value

- The value for the tuple, an already swizzled L<OODatabase::Persistent> object.

=back

Create a tuple with the given key, value pair.

=cut

#---------------------------------------------------------------------
  sub newAlreadySwizzled {
    my $type = shift;
    my $self = {};
    $self->{'key'} = shift;
    $self->{'value'} = shift if scalar(@_);
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 getKey

Retrieve the key.

=cut

#---------------------------------------------------------------------
  sub getKey { my ($self) = @_; return $self->{'key'}; }
#---------------------------------------------------------------------

=head2 getValue

Retrieve the value.

=cut

#---------------------------------------------------------------------
  sub getValue { 
    my ($self) = @_; 
    return $self->{'value'}; 
    }
#---------------------------------------------------------------------

1;
