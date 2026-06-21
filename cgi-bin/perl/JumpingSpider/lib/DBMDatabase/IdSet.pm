package IdSet;

=head1 NAME IdSet

Class encapsulating a set of L<DBMDatabase::Id>.

This package is part of L<DBMDatabase>.
For more information on the database see the L<DBMDatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<DBMDatabase::Licence> and L<DBMDatabase::Version>.

=head1 DESCRIPTION

An IdSet is a data type for columns in a 
database L<DBMDatabase::Table>.  An IdSet cannot be used as a key.

=cut

#---------------------------------------------------------------------
# private instance variables
 my $setSeparator = ":";
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
      $h{$item->toBytes()} = $item;
      }
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 fromBytes(string $b)

=over 4

=item * $b

- The string to convert from (a byte image of an IdSet 
or L<DBMDatabase::IdList>).

=back

Construct a new IdSet from a byte image of an L<DBMDatabase::IdList>
or IdSet.

=cut

#---------------------------------------------------------------------
  sub fromBytes {
    my ($b) = @_;
    my (@items) = split(/$setSeparator/, $b);
    my $idSet = new IdSet();
    my $item;
    foreach $item (@items) {
      $idSet->insert(Id::fromBytes($item));
      }
    return $idSet;
    }
#---------------------------------------------------------------------
 
=head2 fromIdList(IdList $ls)

=over 4

=item * $ls

- An L<DBMDatabase::IdList> 

=back

Construct a new IdSet from an L<DBMDatabase::IdList> (basically, 
convert the list to a set).

=cut

#---------------------------------------------------------------------
  sub fromIdList {
    my ($b) = @_;
    return new IdSet $b->toIdArray();
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
    $$href{$b} = $id;
    }
#---------------------------------------------------------------------

=head2 enumerate()

Return an enumeration of the IdSet.  OK, we have to fake it in Perl
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

=head2 union(IdSet $other)

=over 4

=item * $other

- The other L<DBMDatabase::IdSet>.

=back

Union this set with the other set, and update this set with the
result.

=cut

#---------------------------------------------------------------------
  sub union {
    my ($self, $other) = @_;
    my ($result) = new IdSet();
    $result->unionSelf($other);
    $result->unionSelf($self);
    return $result;
    }
#---------------------------------------------------------------------

=head2 unionSelf(IdSet $other)

=over 4

=item * $other

- The other L<DBMDatabase::IdSet>.

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
      $$href{$key} = $$otherhref{$key};
      }
    }
#---------------------------------------------------------------------

=head2 intersect(IdSet $other)

=over 4

=item * $other

- The other L<DBMDatabase::IdSet>.

=back

Intersect this set with the other set, and return the result.

=cut

#---------------------------------------------------------------------
  sub intersect {
    my ($self, $other) = @_;
    my ($result) = new IdSet();
    $result->unionSelf($other);
    $result->intersectSelf($self);
    return $result;
    }
#---------------------------------------------------------------------

=head2 intersectSelf(IdSet $other)

=over 4

=item * $other

- The other L<DBMDatabase::IdSet>.

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
      if (!defined $$otherhref{$key}) {delete $$href{$key}};
      }
    }
#---------------------------------------------------------------------

=head2 toIdArrayReference()

Returns a reference to an array of Ids from the Set.

=cut

#---------------------------------------------------------------------
  sub toIdArrayReference {
    my ($self) = @_;
    my @result = ();
    my $href = $self->{'href'};
    my $key;
    foreach $key (keys %$href) {
      push @result, $$href{$key};
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
    my ($result) = "IdSet elements are:\n";
    my $href = $self->{'href'};
    foreach $key (keys %$href) {
      my $id = $$href{$key};
      $result .= $id->toString() . " ";
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
    $result = join($setSeparator, keys %$href);
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
