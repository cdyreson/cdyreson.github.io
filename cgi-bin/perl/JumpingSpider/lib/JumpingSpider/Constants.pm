package JumpingSpider::Constants;

=head1 NAME

Constants - A class containing global constants to configure the 
Jumping Spider.

=head1 DESCRIPTION

This class has several constants, mostly just the names of database tables.

=over 4

=item * databaseName

- The name of the L<DBMDatabase> (a directory name).

=item * databaseMode

- The mode of the L<DBMDatabase> (GDBM, DBM, or BSD).

=item * stringTablename

- The name of the table that maps strings to ids (internal).

=item * idTablename

- The name of the table that maps ids to strings (internal).

=item * sideGraphName

- The name of the graph for side edges.

=item * downGraphName

- The name of the graph for downward edges.

=item * indexTableName

- the name of the table that maps content descriptions to ids (URLs).

=item * reachableTableName

- The name of the table that records reachability in the down graph.

=item * WWWReachableTableName

- The name of the table that records reachability for the entire MWW.

=back

=cut

#-----------------------------------------------------------------------
  $databaseName = 'dbs';
  $stringTableName = 'strings';
  $idTableName = 'ids';
  $sideGraphName = 'sideGraph';
  $sideTargetsTableName = 'sideTargets';
  $downGraphName = 'downwardGraph';
  $reachableTableName = 'reachable';
  $indexTableName = 'index';
  $titleTableName = 'titles';
  $WWWReachableTableName = 'WWWreachable';
  #
  # Default configuration is for (Big) DBM files.
  #
  $databaseMode = 'BSD';

1;
