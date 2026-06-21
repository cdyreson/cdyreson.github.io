package StringCol;

=head1 NAME StringCol

Class encapsulating a String in a Database

This package is part of L<DBMDatabase>.
For more information on the database see the L<DBMDatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<DBMDatabase::Licence> and L<DBMDatabase::Version>.

=head1 DESCRIPTION

A String is a 'key' or 'value' data type for columns in a 
database L<DBMDatabase::Table>.

=cut

#---------------------------------------------------------------------

=head1 METHODS

=head2 new(string)

A StringCol is just a String really

=cut

#---------------------------------------------------------------------
sub new {
  my $type = shift;
  my $self = {};
  $self->{'class'} = $type;
  $self->{'id'} = shift;
  bless $self, $type;
  }
#---------------------------------------------------------------------

=head2 fromBytes(ByteArray b)

=over 4

=item * ByteArray b 

- The sequence of bytes

=back

Create a string col the byte image of a string.

=cut

#---------------------------------------------------------------------
sub fromBytes {
  my ($b) = @_;
  return new StringCol($b);
  } 
#---------------------------------------------------------------------

=head2 toBytes()

Returns a byte image of a stringcol.

=cut

#---------------------------------------------------------------------
sub toBytes {
  my ($self) = @_;
  return $self->{'id'};
  } 
#---------------------------------------------------------------------

=head2 image()

Return a string image of the StringCol.

=cut

#---------------------------------------------------------------------
sub image {
  my ($self) = @_;
  return $self->{'id'};
  } 
#---------------------------------------------------------------------

1;
