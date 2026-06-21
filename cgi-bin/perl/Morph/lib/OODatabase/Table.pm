package Table;

=head1 NAME Table

This package is part of L<OODatabase>.  

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.

=head1 DESCRIPTION

This class represents a table in a L<OODatabase::Database>.
A table is a dbm file.  Each entry in the table is
a L<OODatabase::Tuple>.  The class has been configured to use either
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
    $self->{'BerkeleyDB'} = 1 if $databaseType eq 'BerkeleyDB';
    $self->{'DBM'} = 1 if $databaseType eq 'DBM';
    $self->{'SMALLDBM'} = 1 if $databaseType eq 'SMALLDBM';
    require OODatabase::BigDB_File if defined $self->{'DBM'};
    require DB_File if defined $self->{'BSD'};
    require Fcntl if defined $self->{'BSD'};
    require GDBM_File if defined $self->{'GDBM'};
    
    $self->{'count'} = 0 if defined $self->{'GDBM'};
    my (%tempdb);

    dbmopen(%tempdb, $name, 0640) if defined $self->{'SMALLDBM'};
    tie %tempdb, 'BigDB_File', $name, 0640 if defined $self->{'DBM'};
    tie %tempdb, 'DB_File', $name 
       if defined $self->{'BSD'};
#    tie %tempdb, 'DB_File', $name, O_RDWR|O_CREAT, 0640  
#       if defined $self->{'BSD'};
    tie %tempdb, 'GDBM_File', $name, GDBM_WRCREAT, 0640 
       if defined $self->{'GDBM'};
     # GDBM_REPLACE ?
     # GDBM_FASTMODE ?
#    tie %tempdb, 'BerkeleyDB::Hash', -Filename => $name, -Flags => BerkeleyDB::DB_CREATE
    #   if defined $self->{'BerkeleyDB'};

    $self->{'dbm'} = \%tempdb;
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 insertTuple(Tuple $tuple)

=over 4

=item * 

L<OODatabase::Tuple> $tuple - The tuple to insert.

=back

Insert a L<OODatabase::Tuple> into the table.  Will replace an existing 
tuple so be careful.

=cut

#---------------------------------------------------------------------
  sub insertTuple {
    my ($self, $tuple) = @_;
    my ($key) = $tuple->getKey();
    my ($value) = $tuple->getValue();

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

- The key for the tuple to retrieve, must be a L<OODatabase::Persistent> object.

=back

Delete a tuple.  The function returns true or false depending on 
whether or not the tuple found and deleted.

=cut

#---------------------------------------------------------------------
  sub deleteTuple {
    my ($self, $key) = @_;
    $key = $key->getKey();
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

- The key for the tuple to retrieve, must be a L<OODatabase::Persistent> object.

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
    return Tuple->newAlreadySwizzled($keyAsBytes, $$dbm{$keyAsBytes});
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

1;
