package JumpingSpider::Globals;

=head1 NAME JumpingSpider::Globals

The global variables in the MWW, maintained in a global object.
See also L<JumpingSpider::Constants>.

For more information on the MWW see the L<JumpingSpider::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<JumpingSpider::Licence> and L<JumpingSpider::Version>.

=head1 DESCRIPTION

Below is the list of instance variables in the global object.

=over 4

=item * sideGraph

- A L<Persistent::Graph>.  
The sideGraph is the graph of side edges in the MWW.

=item * downGraph

- A L<Persistent::Graph>.  
The downGraph is the graph of down edges in the MWW.

=item * WWWreachableGraph

- A L<Persistent::Graph>.  
The WWWReachableGraph is the computed reachable graph for the MWW.

=item * indexTable

- A L<DBMDatabase::Table>.  
The index table is the mapping from content descriptions to Ids.

=item * titleTable

- A L<DBMDatabase::Table>.  
The title table is the mapping from Ids to title strings.

=item * ids

- A L<DBMDatabase::Table>.  
A table that maps Ids to strings.

=item * strings

- A L<DBMDatabase::Table>.  
A table that maps strings to Ids.

=back

=head1 METHODS

=head2 new()

Allocate and intialize the global vars.

=cut

#------------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    # open the database so conversions can proceed
    my $myDatabase = new Database($JumpingSpider::Constants::databaseName,
                                $JumpingSpider::Constants::databaseMode,
                                $JumpingSpider::Constants::idTableName,
                                $JumpingSpider::Constants::stringTableName);

    $self->{'myDatabase'} = $myDatabase;

    $self->{'titleTable'} = 
      $myDatabase->createTable($JumpingSpider::Constants::titleTableName);
    $self->{'indexTable'} = 
      $myDatabase->createTable($JumpingSpider::Constants::indexTableName);
    $self->{'sideTargetsTable'} = 
      $myDatabase->createTable($JumpingSpider::Constants::sideTargetsTableName);
    $self->{'sideGraph'} = 
      new Persistent::Graph($myDatabase, $JumpingSpider::Constants::sideGraphName);
    $self->{'downGraph'} = 
      new Persistent::Graph($myDatabase, $JumpingSpider::Constants::downGraphName);
    $self->{'WWWReachableTable'} = 
      $myDatabase->createTable($JumpingSpider::Constants::WWWReachableTableName);
    $self->{'reachableTable'} = 
      $myDatabase->createTable($JumpingSpider::Constants::reachableTableName);

    bless $self, $type;
    }
#------------------------------------------------------------------------

=head2 close()

Close and write all the persistent variables are written to disk.  This 
should be
called prior to exiting the MWW (when creating it), but even if 
it not called, in theory, all the persistent variables should be 
maintained since Perl will clean them up nicely for us by doing 
the appropriate unties.

=cut

#------------------------------------------------------------------------
  sub close {
    my $self = shift;
    my $myDatabase = $self->{'myDatabase'};
    $myDatabase->close();
    }
#------------------------------------------------------------------------


=head2 save()

Write all the persistent variables are written to disk.  

=cut

#------------------------------------------------------------------------
  sub save {
    my $self = shift;
    my $myDatabase = $self->{'myDatabase'};
    $myDatabase->save();
    }
#------------------------------------------------------------------------

1;
