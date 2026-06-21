package Table;

=head1 NAME Table

This package is part of L<DBMDatabase>.  

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.

=head1 DESCRIPTION

This class represents a table in a L<DBMDatabase::Database>.
A table is a dbm file.  Each entry in the table is
a L<DBMDatabase::Tuple>.  The class has been configured to use either
BSD, GDBM, or BigDB files.  You must edit this package to configure 
it.

=head1 METHODS

=head2 new(string $tableName)

=over 4

=item * string $tableName 

- The name of the table (a dbm file name).

=back

Create a table with the give name (opens a dbm file).
Second line.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    my ($name) = shift;
    my ($databaseName) = shift;
    my ($databaseType) = shift;
    $self->{'class'} = $type;
    $self->{'name'} = $name;
    $self->{'databaseName'} = $databaseName;
    # Determine type
    $self->{'GDBM'} = 1 if $databaseType eq 'GDBM';
    $self->{'BSD'} = 1 if $databaseType eq 'BSD';
    $self->{'DBM'} = 1 if $databaseType eq 'DBM';
    $self->{'SMALLDBM'} = 1 if $databaseType eq 'SMALLDBM';
    require DBMDatabase::BigDB_File if defined $self->{'DBM'};
    require DB_File if defined $self->{'BSD'};
    require Fcntl if defined $self->{'BSD'};
    require GDBM_File if defined $self->{'GDBM'};
    
    $self->{'count'} = 0 if defined $self->{'GDBM'};
    my (%tempdb);

    dbmopen(%tempdb, $name, 0640) if defined $self->{'SMALLDBM'};
    tie %tempdb, 'BigDB_File', $name, 0640 if defined $self->{'DBM'};
    tie %tempdb, 'DB_File', $name 
       if defined $self->{'BSD'};
    tie %tempdb, 'GDBM_File', $name, GDBM_WRCREAT, 0640 
       if defined $self->{'GDBM'};
     # GDBM_REPLACE ?
     # GDBM_FASTMODE ?

    $self->{'dbm'} = \%tempdb;
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 insertTuple(Tuple $tuple)

=over 4

=item * 

L<DBMDatabase::Tuple> $tuple - The tuple to insert.

=back

Insert a L<DBMDatabase::Tuple> into the table.  Will replace an existing 
tuple so be careful.

=cut

#---------------------------------------------------------------------
  sub insertTuple {
    my ($self, $tuple) = @_;
    my ($key) = $tuple->getKeyAsBytes();
    my ($value) = $tuple->getValueAsBytes();

    my ($dbm) = $self->{'dbm'};

    $$dbm{$key} = $value;

    #
    # Reorganize the table if it is GDBM to conserve space.
    #
    if (defined $self->{'GDBM'}) {
      my ($count) = $self->{'count'};
      $count++;
      #reorganize every 5000th insert
      if ($count >= 5000) {
        (tied %dbm)->reorganize();
        $count = 0;
        }

      $self->{'count'} = $count;
      }

    }
#---------------------------------------------------------------------

=head2 deleteTuple(Column $key) 

=over 4

=item * Column $key 

- The key for the tuple to retrieve, can be an L<DBMDatabase::Id> or 
L<DBMDatabase::StringCol>.

=back

Delete a tuple.  The function returns true or false depending on 
whether or not the tuple found and deleted.

=cut

#---------------------------------------------------------------------
  sub deleteTuple {
    my ($self, $key) = @_;
    $key = $key->getKeyAsBytes();
    my ($dbm) = $self->{'dbm'};
    if (defined $$dbm{$key}) {
      delete $$dbm{$key};
      return 1;
      }
    return 0;
    }
#---------------------------------------------------------------------

=head2 retrieveTuple(Column $key)

=over 4

=item * Column $key 

- The key for the tuple to retrieve, can be an L<DBMDatabase::Id> or 
L<DBMDatabase::StringCol>.

=back

Retrieve a tuple with the given key from the table.  This routine will 
retrieve a tuple from the table, or if the tuple is not found it will
return 0.  Use as follows.

   # we want to retrieve the tuple for the key "hi"
   $key = Id::fromString("hi");
   # try to retrieve it 
   $r = $table->retrieveTuple($key);    
   # let's test if we found it
   if ($r) { 
     # key was found in the table 
   else { 
     # key was not found
     }

=cut 

#---------------------------------------------------------------------
  sub retrieveTuple {
    my ($self, $key) = @_;
    my ($keyAsBytes) = $key->toBytes();
    my ($dbm) = $self->{'dbm'};
    #// Convert the key int to a byte array
    return 0 unless defined $$dbm{$keyAsBytes};
    return new Tuple(new StringCol($keyAsBytes), new StringCol($$dbm{$keyAsBytes}));
    }
#---------------------------------------------------------------------

=head2 IdIdImage()

Return a string of the key, value pairs (assumed to be Ids).

=cut

#---------------------------------------------------------------------
  sub IdIdImage {
    my ($self) = @_;
    my ($result) = "Key,Value Image for Table:\n";
    my ($dbm) = $self->{'dbm'};
    my ($key);
    my ($valueAsBytes);
    foreach $key (keys %$dbm) {
      $valueAsBytes = $$dbm{$key};
      $result .= $key->toString() + ",";
      $result .= $valueAsBytes->toString() + "\n";
      }
    return $result;
    }
#---------------------------------------------------------------------

=head2 IdIdListImage()

Return a string of the key, value pairs (assumed to be Id Lists).

=cut

#---------------------------------------------------------------------
  sub IdIdListImage {
    my ($self) = @_;
    my ($dbm) = $self->{'dbm'};
    my ($result) = "Key,Value List Image for Table:\n";
    my ($key);
    my ($valueAsBytes);
    my ($idList);
    foreach $key (keys %$dbm) {
      $valueAsBytes = $$dbm{$key};
      $idList = IdList::fromBytes($valueAsBytes);
      $result .= $key + "," .  $idList->image() . "\n";
      }
    return $result;
    }
#---------------------------------------------------------------------

=head2 enumerate()

Return a reference to the dbm file.

=cut

#---------------------------------------------------------------------
  sub enumerate {
    my ($self) = @_;
    my ($dbm) = $self->{'dbm'};
    return $dbm;
    }
#---------------------------------------------------------------------

=head2 enumerateKeys()

Return a list of the keys.

=cut

#---------------------------------------------------------------------
  sub enumerateKeys {
    my ($self) = @_;
    my ($dbm) = $self->{'dbm'};
    return keys %$dbm;
    }
#---------------------------------------------------------------------

=head2 enumerateKeysAsInts()

Return a list of the key values, but as integer values rather than byte
sequences.

=cut

#---------------------------------------------------------------------
  sub enumerateKeysAsInts {
    my ($self) = @_;
    my ($dbm) = $self->{'dbm'};
    my $list = [];
    my $key;
    foreach $key (keys %$dbm) {
      push @$list, $key + 0;
      }
    return $list;
    }
#---------------------------------------------------------------------

=head2 KeysAsStringIds()

Return a string of the key values (assumed to be Ids).

=cut

#---------------------------------------------------------------------
  sub KeysAsStringIds {
    my ($self) = @_;
    my ($result) = "";
    my ($dbm) = $self->{'dbm'};
    my ($key);
    my ($valueAsBytes);
    my $SEPARATOR = "";
    foreach $key (keys %$dbm) {
      my ($id) = new Id $key;
      $result .= $SEPARATOR . $id->toString();
      $SEPARATOR = "\f";
      }
    return $result;
    }
#---------------------------------------------------------------------

=head2 save()

Close the DBM file

=cut 

#---------------------------------------------------------------------
  sub save {
    my ($self) = @_;
    my ($dbm) = $self->{'dbm'};

    untie %$dbm;
    }
#---------------------------------------------------------------------

=head2 IdKeysImage()

Return a string of the key values (assumed to be Ids).

=cut

#---------------------------------------------------------------------
  sub IdKeysImage {
    my ($self) = @_;
    my ($result) = "";
    my ($dbm) = $self->{'dbm'};
    my ($key);
    my ($valueAsBytes);
    my $SEPARATOR = "";
    foreach $key (keys %$dbm) {
      my $id = new Id($key);
      $result .= $SEPARATOR . $id->image();
      $SEPARATOR = "\f";
      }
    return $result;
    }
#---------------------------------------------------------------------

1;
