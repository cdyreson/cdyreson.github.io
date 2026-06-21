package IdSetWithCount;

=head1 NAME IdSetWithCount

Class encapsulating a set of L<DBMDatabase::Id> and an
integer count for the number of such things within the set.

This package is part of L<DBMDatabase>.
For more information on the database see the L<DBMDatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<DBMDatabase::Licence> and L<DBMDatabase::Version>.

=head1 DESCRIPTION

An IdSetWithCount is a data type for columns in a 
database L<DBMDatabase::Table>.  An IdSetWithCount cannot be used as a key.

=cut

#---------------------------------------------------------------------
# private instance variables
 my $setSeparator = ":";
 my $countSeparator = ",";
#---------------------------------------------------------------------

=head1 METHODS

=head2 new(Id[] \@ids)

=over 4

=item * \@ids 

- A reference to a list of Ids.

=back

Construct a Set from a list of Ids

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $itemsref = \@_;
    my $self = {};
    my %h = ();
    $self->{'class'} = $type;
    $self->{'href'} = \%h;
    my $item;
    foreach $item (@$itemsref) {
      my ($id) = $item->toBytes();
      $h{$id} = [$item, 0] unless defined $h{$id};
      # increment the count
      my ($ref) = $h{$id};
      my ($count) = @$ref[1];
      $count++;
      @$ref[1] = $count;
      }
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 fromBytes(string $b)

=over 4

=item * $b

- The string to convert from (a byte image of an IdSetWithCount 
or L<DBMDatabase::IdList>).

=back

Construct a new IdSetWithCount from a byte image of an L<DBMDatabase::IdList>
or IdSetWithCount.

=cut

#---------------------------------------------------------------------
  sub fromBytes {
    my ($b) = @_;
    my (@items) = split(/$setSeparator/, $b);
    my $idSet = new IdSetWithCount();
    my $item;
    foreach $item (@items) {
      my ($idString, $count) = split(/$countSeparator/, $item);
      $idSet->insertWithCount(Id::fromBytes($idString),$count);
      }
    return $idSet;
    }
#---------------------------------------------------------------------
 
=head2 fromIdList(IdList $ls)

=over 4

=item * $ls

- An L<DBMDatabase::IdList> 

=back

Construct a new IdSetWithCount from an L<DBMDatabase::IdList> (basically, 
convert the list to a set).

=cut

#---------------------------------------------------------------------
  sub fromIdList {
    my ($b) = @_;
    return new IdSetWithCount $b->toIdArray();
    }
#---------------------------------------------------------------------

=head2 numberOfElements()

Return the number of Ids in the set.

=cut

#---------------------------------------------------------------------
  sub numberOfElements {
    my ($self) = @_;
    my $href = $self->{'href'};
    my ($count) = 0;
    my $key;
    foreach $key (keys %$href) {
      $count++;
      }
    return $count;
    }
#---------------------------------------------------------------------

=head2 memberOf(Id $id)

=over 4

=item * $id

- Check if this L<DBMDatabase::Id> is in the set.

=back

Returns true if it is, false otherwise.

=cut

#---------------------------------------------------------------------
  sub memberOf {
    my ($self, $id) = @_;
    my $href = $self->{'href'};
    my $b = $id->toBytes();
    if (defined $$href{$b}) { return 1; } 
    return 0;
    }
#---------------------------------------------------------------------

=head2 delete(Id $id)

=over 4

=item * $id

- The L<DBMDatabase::Id> to remove from the set.

=back

Delete an Id from the set.

=cut

#---------------------------------------------------------------------
  sub delete {
    my ($self, $id) = @_;
    my $href = $self->{'href'};
    my $b = $id->toBytes();
    if (defined $$href{$b}) { 
      delete $$href{$b};
      return 1; 
      } 
    return 0;
    }
#---------------------------------------------------------------------

=head2 insert(Id $id)

=over 4

=item * $id

- The L<DBMDatabase::Id> to insert.

=back

Inserts an L<DBMDatabase::Id> into this set.

=cut

#---------------------------------------------------------------------
  sub insert {
    my ($self, $id) = @_;
    my $href = $self->{'href'};
    my $b = $id->toBytes();
    $$href{$b} = [$id, 0] unless defined $$href{$b};
    my $ref = $$href{$b};
    @$ref[1]++;
    }
#---------------------------------------------------------------------

=head2 insertWithCount(Id $id, int $count)

=over 4

=item * $id

- The L<DBMDatabase::Id> to insert.

=item * $count

- The count for this item.

=back

Inserts an L<DBMDatabase::Id> into this set with the indicated count.
Careful, it overwrites existing count for element!

=cut

#---------------------------------------------------------------------
  sub insertWithCount {
    my ($self, $id, $count) = @_;
    my $href = $self->{'href'};
    my $b = $id->toBytes();
    $$href{$b} = [$id, $count];
    }
#---------------------------------------------------------------------

=head2 enumerate()

Return an enumeration of the IdSetWithCount.  OK, we have to fake it in Perl
since this is a Java concept (although now that I understand hash
tables I think I can do it with each).  So we will return the
list of values in the table.  Currently, only small sets should
be enumerated.

=cut

#---------------------------------------------------------------------
  sub enumerate {
    my ($self) = @_;
    my $href = $self->{'href'};
    return values %$href;
    }
#---------------------------------------------------------------------

=head2 enumerateInSortedOrder()

Return an enumeration of the IdSetWithCount.  OK, we have to fake it in Perl
since this is a Java concept (although now that I understand hash
tables I think I can do it with each).  So we will return the
list of values in the table.  Currently, only small sets should
be enumerated.  We do this by sorting the list prior to enumerating
it.

=cut

#---------------------------------------------------------------------
  sub enumerateInSortedOrder {
    my ($self) = @_;
    my $href = $self->{'href'};
    return sort {@$b[1] <=> @$a[1]} values %$href;
    }
#---------------------------------------------------------------------

=head2 union(IdSetWithCount $other)

=over 4

=item * $other

- The other L<DBMDatabase::IdSetWithCount>.

=back

Union this set with the other set, and update this set with the
result.

=cut

#---------------------------------------------------------------------
  sub union {
    my ($self, $other) = @_;
    my ($result) = new IdSetWithCount();
    $result->unionSelf($other);
    $result->unionSelf($self);
    return $result;
    }
#---------------------------------------------------------------------

=head2 unionSelf(IdSetWithCount $other)

=over 4

=item * $other

- The other L<DBMDatabase::IdSetWithCount>.

=back

Union this set with the other set, and update this set with the
result.

=cut

#---------------------------------------------------------------------
  sub unionSelf {
    my ($self, $other) = @_;
    my $href = $self->{'href'};
    my $otherhref = $other->{'href'};
    my $key;
    foreach $key (keys %$otherhref) {
      if (defined $$href{$key}) {
        #keep maximum count
        my ($oref) = $$otherhref{$key};
        my ($sref) = $$href{$key};
        if (@$oref[1] > @$sref[1]) {
          $$href{$key} = $$otherhref{$key};
          }
        }
      else {$$href{$key} = $$otherhref{$key};}
      }
    }
#---------------------------------------------------------------------

=head2 intersect(IdSetWithCount $other)

=over 4

=item * $other

- The other L<DBMDatabase::IdSetWithCount>.

=back

Intersect this set with the other set, and return the result.

=cut

#---------------------------------------------------------------------
  sub intersect {
    my ($self, $other) = @_;
    my ($result) = new IdSetWithCount();
    $result->unionSelf($self);
    $result->intersectSelf($other);
    return $result;
    }
#---------------------------------------------------------------------

=head2 intersectSelf(IdSetWithCount $other)

=over 4

=item * $other

- The other L<DBMDatabase::IdSetWithCount>.

=back

Intersect this set with the other set, and update this set with the
result.

=cut

#---------------------------------------------------------------------
  sub intersectSelf {
    my ($self, $other) = @_;
    my $href = $self->{'href'};
    my $otherhref = $other->{'href'};
    my $key;
    foreach $key (keys %$href) {
      if (defined $$otherhref{$key}) {
        #only keep minimum count
        my ($oref) = $$otherhref{$key};
        my ($sref) = $$href{$key};
        if (@$oref[1] < @$sref[1]) {
          $$href{$key} = $$otherhref{$key};
          }
        }
      else {delete $$href{$key};}
      }
    }
#---------------------------------------------------------------------

=head2 toIdArrayReference()

Returns a reference to an array of Ids from the Set.
Replicates an id for each count.

=cut

#---------------------------------------------------------------------
  sub toIdArrayReference {
    my ($self) = @_;
    my @result = ();
    my $href = $self->{'href'};
    my $key;
    foreach $key (keys %$href) {
      my ($ref) =  $$href{$key};
      my ($id, $count) =  @$ref;
      # replicate the id by the count
      while ($count--) {
        push @result, $id;
        }
      }
    return \@result;
    }
#---------------------------------------------------------------------

=head2 image()

Create a formatted string of all the elements in the set

=cut

#---------------------------------------------------------------------
  sub image {
    my ($self) = @_;
    my ($result) = "IdSetWithCount elements are:\n";
    my $href = $self->{'href'};
    foreach $key (keys %$href) {
      my ($ref) =  $$href{$key};
      my ($id, $count) =  @$ref;
      $result .= $id->toString() . ", $count ";
      }
    return $result . "\nno more!\n";
    }
#---------------------------------------------------------------------

=head2 toBytes()

Convert to a byte array

=cut

#---------------------------------------------------------------------
  sub toBytes {
    my ($self) = @_;
    my ($result) = "";
    my $href = $self->{'href'};
    my ($key);
    my @partial = ();
    foreach $key (keys %$href) {
      my ($ref) =  $$href{$key};
      my ($id, $count) =  @$ref;
      #print "zzzz $count\n";
      unshift @partial, $id->toBytes() . "$countSeparator$count";
      }
    $result = join($setSeparator, @partial);
    return $result;
    }
#---------------------------------------------------------------------

=head2 import()

Is this still needed?  Just for requires.

=cut

#---------------------------------------------------------------------
  sub import {
    1;
    }
#---------------------------------------------------------------------

1;
