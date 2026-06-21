package IdList;

=head1 NAME IdList

Class encapsulating an L<DBMDatabase::Id> list in a Database.

This package is part of L<DBMDatabase>.
For more information on the database see the L<DBMDatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<DBMDatabase::Licence> and L<DBMDatabase::Version>.

=head1 DESCRIPTION

An IdList is a data type for a column in a 
database L<DBMDatabase::Table>.

=cut

#---------------------------------------------------------------------
# private instance variables
 my $separator = ";";
#---------------------------------------------------------------------

=head1 METHODS

=head2 new(Id[] @ids)

Construct a list from an array of Ids

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    $self->{'class'} = $type;
    my ($ids) = @_;
    $self->{'id'} = $ids;
    my $ref = $self->{'id'};
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 image()

Convert to a nice String image for dumping

=cut

#---------------------------------------------------------------------
  sub image {
    my ($self) = @_;
    my ($s) = "";
    my ($toadd) = ""; 
    my ($arrayref) = $self->{'id'};
    foreach $id (@$arrayref) {
      $s .= $toadd;
      $s .= $id->toString();
      $toadd = ", "; 
      }
    return $s;
    }
#---------------------------------------------------------------------

=head2 toBytes()

Returns a byte image of IdList.

=cut

#---------------------------------------------------------------------
  sub toBytes {
    my ($self) = @_;
    my ($arrayref) = $self->{'id'};
    #print "eeee $arrayref\n";
    my ($id) = "";
    my @ids = ();
    foreach $id (@$arrayref) {
      #print $id->toString . " \n";
      push @ids, $id->toBytes();
      }
    #print join($separator, @ids) . "\n";
    return join($separator, @ids);
    }
#---------------------------------------------------------------------

=head2 fromBytes(Byte[] $b)

=over 4

=item * Byte[] $b 

- The sequence of bytes

=back

Build a new IdList from a byte image.

=cut

#---------------------------------------------------------------------
  sub fromBytes {
    my ($b) = @_;
    my @bytes = split($separator,$b);
    #print "here $b\n";
    my $byte;
    my @ids = ();
    foreach $byte (@bytes) {
      push @ids, Id::fromBytes($byte);
      #print Id::fromBytes($byte)->toString() . "\n";
      }
    return new IdList(\@ids); 
    }
#---------------------------------------------------------------------

=head2 toIdArray()

Build an array of Ids that is the sequence.

=cut

#---------------------------------------------------------------------
  sub toIdArray {
    my ($self) = @_;
    my $arrayref = $self->{'id'};
    return @$arrayref;
    }
#---------------------------------------------------------------------

1;
