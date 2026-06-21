package Tuple;

=head1 NAME Tuple

This package is part of L<DBMDatabase>.
For more information on the database see the L<DBMDatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<DBMDatabase::Licence> and L<DBMDatabase::Version>.

=head1 DESCRIPTION

A tuple is a pairing of a key and a value.  

=head1 METHODS

=head2 new(Id or IdList $key)

=over 4

=item * Id or IdList $key

- The key for the tuple.

=back

Create a tuple with the given key.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    my $key = shift @_;
    my $value;
    #print "key is $key ". $key->image();
    my $valueExists = 0;
    if (scalar(@_) > 0) {
      $value = shift @_;
      #print "value is $value " . $value->image();
      $valueExists = 1;
      }
    #print "\n";
    $self->{'class'} = $type;
    $self->{'key'} = $key->toBytes();
    if ($valueExists) {$self->{'value'} = $value->toBytes();}
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 getKeyAsBytes

Convert the key to a byte array.

=cut

#---------------------------------------------------------------------
  sub getKeyAsBytes { my ($self) = @_; return $self->{'key'}; }
#---------------------------------------------------------------------

=head2 getKeyAsString

Convert the key to a String.

=cut

#---------------------------------------------------------------------
  sub getKeyAsString { my ($self) = @_; return $self->{'key'}; }
#---------------------------------------------------------------------

=head2 getKeyAsId.

Convert the key to an L<DBMDatabase::Id>.

=cut

#---------------------------------------------------------------------
  sub getKeyAsId { my ($self) = @_; 
                   return Id::fromBytes($self->{'key'}); }
#---------------------------------------------------------------------

=head2 getKeyAsIdSet

Convert the key to an L<DBMDatabase::IdSet>.

=cut

#---------------------------------------------------------------------
  sub getKeyAsIdSet { my ($self) = @_; 
                   return IdSet::fromBytes($self->{'key'}); }
#---------------------------------------------------------------------

=head2 getKeyAsIdList

Convert the key to an L<DBMDatabase::IdList>.

=cut

#---------------------------------------------------------------------
  sub getKeyAsIdList { my ($self) = @_; 
                   return IdList::fromBytes($self->{'key'}); }
#---------------------------------------------------------------------

=head2 getKeyAsStringList

Convert the key to a StringList.

=cut

#---------------------------------------------------------------------
  sub getKeyAsStringList { my ($self) = @_; 
                   return StringList::fromBytes($self->{'key'}); }
#---------------------------------------------------------------------

=head2 getValueAsBytes

Convert the value to a byte image.

=cut

#---------------------------------------------------------------------
  sub getValueAsBytes { 
    my ($self) = @_; 
    return $self->{'value'}; 
    #if ($value == 0) { return "0"; }
    #return $value;
    }
#---------------------------------------------------------------------

=head2 getValueAsBytes

Convert the value to a byte image.

=cut

#---------------------------------------------------------------------
  sub getValueAsString { my ($self) = @_; return $self->{'value'}; }
#---------------------------------------------------------------------

=head2 getValueAsId

Convert the value to a L<DBMDatabase::Id>.

=cut

#---------------------------------------------------------------------
  sub getValueAsId { my ($self) = @_; 
                   return Id::fromBytes($self->{'value'}); }
#---------------------------------------------------------------------

=head2 getValueAsIdSet

Convert the value to a L<DBMDatabase::IdSet>.

=cut

#---------------------------------------------------------------------
  sub getValueAsIdSet { my ($self) = @_; 
                   return IdSet::fromBytes($self->{'value'}); }
#---------------------------------------------------------------------

=head2 getValueAsIdSetWithCount

Convert the value to a L<DBMDatabase::IdSetWithCount>.

=cut

#---------------------------------------------------------------------
  sub getValueAsIdSetWithCount { my ($self) = @_; 
                   return IdSetWithCount::fromBytes($self->{'value'}); }
#---------------------------------------------------------------------

=head2 getValueAsBytes

Convert the value to a L<DBMDatabase::IdList>.

=cut

#---------------------------------------------------------------------
  sub getValueAsIdList { my ($self) = @_; 
                   return IdList::fromBytes($self->{'value'}); }
#---------------------------------------------------------------------

=head2 getValueAsStringList

Convert the value to a L<DBMDatabase::StringList>.

=cut

#---------------------------------------------------------------------
  sub getValueAsStringList { my ($self) = @_; 
                   return StringList::fromBytes($self->{'value'}); }
#---------------------------------------------------------------------

=head2 import

Is this needed?

=cut

#---------------------------------------------------------------------
  sub import {
    1;
    }
#---------------------------------------------------------------------
1;
