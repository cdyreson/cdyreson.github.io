package OODatabase;

=head1 NAME OODatabase.pm

This package includes all the things required for the Configuration of
a simple (non-concurrent) database based on DBM files.  

=head1 DESCRIPTION

It has several parts 

=over 4

=item * 

L<OODatabase::Overview>, L<OODatabase::Licence>, and L<OODatabase::Version>.

=item * 

L<OODatabase::Database> 
- The database itself.

=item * 

L<OODatabase::Table> 
- A table in the database. All tables have two columns.

=item * 

L<OODatabase::Tuple> 
- A tuple in a table. All tuples have a key and a value.

=item * 

L<OODatabase::Persistent> 
- An class to implement persistence for an object. 
All objects stored in a table should inherit this class.


=back 

Copyright &copy 1998 Curtis E. Dyreson. All rights reserved.

=cut

# Uncomment the following line to use BerkeleyDB
#use BerkeleyDB;
require OODatabase::Persistent;
require OODatabase::Tuple;
require OODatabase::Table;
require OODatabase::Database;

1;
