package StringList;

=head1 NAME StringList

Class encapsulating an L<DBMDatabase::StringCol> list in a Database.

This package is part of L<DBMDatabase>.
For more information on the database see the L<DBMDatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<DBMDatabase::Licence> and L<DBMDatabase::Version>.

=head1 DESCRIPTION

An StringList is a data type for a column in a 
database L<DBMDatabase::Table>.

=cut

#---------------------------------------------------------------------
# private instance variables
my $separator = ";";
#---------------------------------------------------------------------

=head1 METHODS

=head2 new(StringCol[] @ids)

Construct a list from an array of StringCols

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    $self->{'class'} = $type;
    $self->{'string'} = @{ @_ };
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 image()

Convert to a nice String image for dumping

=cut

#---------------------------------------------------------------------
  sub image {
    my ($self) = @_;
    my ($arrayref) = $self->{'string'};
    my ($string) = "";
    my @strings = ();
    foreach $string (@$arrayref) {
      push @strings, $string->toBytes();
      }
    return join($separator, @strings);
    }
#---------------------------------------------------------------------

=head2 toBytes()

Returns a byte image of StringList.

=cut

#---------------------------------------------------------------------
  sub toBytes {
    my ($self) = @_;
    my ($arrayref) = $self->{'string'};
    my ($string) = "";
    my @strings = ();
    foreach $string (@$arrayref) {
      push @strings, $string->toBytes();
      }
    return join($separator, @strings);
    }
#---------------------------------------------------------------------

=head2 fromBytes(Byte[] $b)

=over 4

=item * Byte[] $b 

- The sequence of bytes

=back

Build a new StringList from a byte image.

=cut

#---------------------------------------------------------------------
  sub fromBytes {
    my ($b) = @_;
    my @bytes = split($separator,$b);
    my ($byte);
    my ($sl) = new StringList(); 
    foreach $byte (@bytes) {
      $sl->addElement(StringCol::fromBytes($byte));
      }
    return $sl;
    }
#---------------------------------------------------------------------

=head2 toStringColArray()

Build an array of StringCol that is the sequence.

=cut

#---------------------------------------------------------------------
  sub toStringColArray {
    my ($self) = @_;
    my ($arrayref) = $self->{'string'};
    return @$arrayref;
    }
#---------------------------------------------------------------------

=head2 addElement(StringCol $s)

=over 4

=item * StringCol $s 

- A L<DBMDatabase::StringCol>, the string to add

=back

Add an element to this list

=cut

#---------------------------------------------------------------------
  sub addElement {
    my ($self, $s) = @_;
    my $arrayref = $self->{'string'};
    push @$arrayref, $s;
    }
#---------------------------------------------------------------------

1;
