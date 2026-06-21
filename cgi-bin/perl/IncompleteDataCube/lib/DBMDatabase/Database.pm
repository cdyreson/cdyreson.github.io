package Database;

use Carp;

=head1 NAME Database

This class encapsulates a Database.

This package is part of L<DBMDatabase>.
For more information on the database see the L<DBMDatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<DBMDatabase::Licence> and L<DBMDatabase::Version>.

=head1 DESCRIPTION

The database supports only tables of persistent associative arrays,
so it is a really simple database! 
In our implementation the database itself is a directory, and the tables
within it are jdbm files. The name of the 
directory is passed to the Database constructor.  Individual tables in
the database are dbm files.
See also L<DBMDatabase::Table> and L<DBMDatabase::Tuple>.

=head1 METHODS

=head2 new(string $databaseName)

=over 4

=item * string $databaseName

- The name of the database (a directory name).

=back

This constructor just stores the name of the database as 
the directory.  It does not open any files,
nor check to make sure the directory exists.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    my ($name) = shift || croak 'Database needs a name!';
    my ($mode) = shift || croak 'Database needs a mode!';
    if ($mode ne 'GDBM') {
      if ($mode ne 'DBM') {
        if ($mode ne 'BSD') { 
          if ($mode ne 'SMALLDBM') { 
            croak "Database $mode not a supported mode!";
            }
          }
        }
      }
    my ($idTableName) = shift || 'ids';
    my ($stringTableName) = shift || 'strings';
    $self->{'class'} = $type;

    # die if the database does not exist
    croak "Warning, the database directory '$name' cannot be found.\n"
      unless -e $name;

    # store the name and mode of the database
    $self->{'name'} = $name;
    $self->{'mode'} = $mode;
    my @temp = ();
    $self->{'openTables'} = \@temp;
    &createTable($self, $idTableName);
    &createTable($self, $stringTableName);
    my ($idTable, $stringTable) = @temp;
    &Convert::initialize($idTable, $stringTable);
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 createTable(string $tableName)

=over 4

=item * string $tableName

- The name of the table.

=back 

Creates a L<DBMDatabase::Table>.  
A table is a database relation.

=cut

#---------------------------------------------------------------------
  sub createTable {
    my ($self, $tableName) = @_;
    my $table = new Table($self->{'name'} . "/$tableName", 
                          $self->{'name'},
                          $self->{'mode'});
    my $tables = $self->{'openTables'};
    push @$tables, $table;
    $self->{'openTables'} = $tables;
    return $table;
    }
#---------------------------------------------------------------------

=head2 createNewTable(string $tableName)

=over 4

=item * string $tableName

- The name of the table.

=back 

Creates a new L<DBMDatabase::Table> (will delete all entries from an 
existing table).
A table is a database relation.

=cut

#---------------------------------------------------------------------
  sub createNewTable {
    my ($self, $tableName) = @_;
    my ($table) = $self->createTable($tableName);
    $table->clear();
    my $tables = $self->{'openTables'};
    push @$tables, $table;
    $self->{'openTables'} = $tables;
    return $table;
    }
#---------------------------------------------------------------------

=head2 close()

Close the database making sure that the open tables have been flushed.

=cut

#---------------------------------------------------------------------
  sub close {
    my ($self) = @_;
    my $tables = $self->{'openTables'};
    my ($table);
    foreach $table (@$tables) {
      $table->save();
      }
    $self->{'openTables'} = [];
    }
#---------------------------------------------------------------------

1;
