package SemiStructuredDB::Constants;

=head1 NAME SemiStructuredDB::Constants

EDIT THIS TO CONFIGURE THE DB.
The global constants in the semistructured db.  

=head1 DESCRIPTION

A list of global available constants.  

=over 4

=item * databaseMode

- Underlying DBM mode

=item * databaseName

- In what directory will the database live?

=item * SSGraphName

- The semistructured graph.

=back

=cut

#---------------------------------------------------------------------
  #
  # Default configuration is for (BerkeleyDB) files.
  #
  $databaseMode = 'BerkeleyDB';
  $databaseName = "dbs";

  #
  # What is the name of the SSGraph?
  # 
  $SSGraphName = "SSGraph";
#---------------------------------------------------------------------

1;
