package DBMDatabase;

=head1 NAME DBMDatabase.pm

This package includes all the things required for the Configuration of
a simple (non-concurrent) database based on DBM files.  

=head1 DESCRIPTION

It has several parts 

=over 4

=item * 

L<DBMDatabase::Database> 
- The database itself.

=item * 

L<DBMDatabase::Table> 
- A table in the database. All tables have two columns.

=item * 

L<DBMDatabase::Tuple> 
- A tuple in a table. All tuples have a key and a value.

=item * 

L<DBMDatabase::Id> 
- An allowed column data-type.  An Id is the internal representation
of a string (as a list of integers).  An Id can be a key.

=item * 

L<DBMDatabase::IdList> 
- An allowed column data-type.  An list of Ids.  An IdList can be a key.

=item * 

L<DBMDatabase::IdSet> 
- An allowed column data-type.  An set of Ids.  An IdSet cannot be a key.

=item * 

L<DBMDatabase::StringCol> 
- An allowed column data-type.  A string.  A StringCol can be a key.

=item * 

L<DBMDatabase::StringList> 
- An allowed column data-type.  A list of string.  A StringList can be a key.

=item * 

L<DBMDatabase::Convert> 
- An internal class to implement the automatic conversion between strings 
and ids.  It is effectively a private class for the database.

=back 

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.

=cut

require DBMDatabase::Id;
require DBMDatabase::Convert;
require DBMDatabase::IdList;
require DBMDatabase::IdSet;
require DBMDatabase::IdSetWithCount;
require DBMDatabase::StringCol;
require DBMDatabase::StringList;
require DBMDatabase::Tuple;
require DBMDatabase::Table;
require DBMDatabase::Database;

1;
