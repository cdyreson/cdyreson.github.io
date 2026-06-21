package Id;
 
=head1 NAME Id 

Class encapsulating an Id in a Database

This package is part of L<DBMDatabase>.
For more information on the database see the L<DBMDatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<DBMDatabase::Licence> and L<DBMDatabase::Version>.

=head1 DESCRIPTION

An Id is a compact 'key' data type for columns in a 
database L<DBMDatabase::Table>.

=cut

#---------------------------------------------------------------------
# private instance variables
 my $separator = "\e";
#---------------------------------------------------------------------

=head1 METHODS

=head2 new(list of integers)

An Id is just a list of integers

=cut

#---------------------------------------------------------------------
sub new {
  my $type = shift;
  my $self = {};
  $self->{'class'} = $type;
  $self->{'id'} = join($separator,@_);
  bless $self, $type;
  }
#---------------------------------------------------------------------

=head2 equals(Id $other)

=over 4

=item * L<DBMDatabase::Id> $other

- The other Id to compare

=back

Is this Id equal to another? Compare byte images.

=cut

#---------------------------------------------------------------------
sub equals {
  my ($self, $other) = @_;
  my $selfb = $self->toBytes();
  my $otherb = $other->toBytes();
  return ($selfb eq $otherb);
  } 
#---------------------------------------------------------------------

=head2 fromString(string $string)

=over 4

=item * string $string

- The string

=back

Create an Id from a string.

=cut

#---------------------------------------------------------------------
sub fromString {
  my ($s) = @_;
  return Convert::toId($s);
  } 
#---------------------------------------------------------------------

=head2 fromBytes(ByteArray $b)

=over 4

=item * ByteArray $b 

- The sequence of bytes

=back

Create an Id from the byte image of an Id.

=cut

#---------------------------------------------------------------------
sub fromBytes {
  my ($b) = @_;
  return new Id(split($separator, $b));
  } 
#---------------------------------------------------------------------

=head2 toString()

Returns a string image of an Id.

=cut

#---------------------------------------------------------------------
sub toString {
  my ($self) = @_;
  return Convert::toString($self);
  } 
#---------------------------------------------------------------------

=head2 toBytes()

Returns a byte image of an Id.

=cut

#---------------------------------------------------------------------
sub toBytes {
  my ($self) = @_;
  return $self->{'id'};
  } 
#---------------------------------------------------------------------

=head2 image()

Return a string image of the Id.

=cut

#---------------------------------------------------------------------
sub image {
  my ($self) = @_;
  return $self->{'id'};
  } 
#---------------------------------------------------------------------

=head2 cloneMe()

Return a duplicate of the Id.

=cut

#---------------------------------------------------------------------
sub cloneMe {
  my ($self) = @_;
  return new Id(split($separator,$self->{'id'}));
  } 
#---------------------------------------------------------------------

=head2 increment()

Increment an Id (this is for Ids that are counters)!

=cut

#---------------------------------------------------------------------
sub increment {
  my ($self) = @_;
  my @temp = split($separator,$self->{'id'});
  my ($temp) = pop(@temp);
  $temp++;
  push @temp, $temp;
  $self->{'id'} = join($separator,@temp); 
  } 
#---------------------------------------------------------------------

1;
