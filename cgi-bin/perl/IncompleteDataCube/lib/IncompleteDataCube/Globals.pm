package IncompleteDataCube::Globals;

=head1 NAME IncompleteDataCube::Globals

The global variables in the cube, maintained in a global object.
See also L<IncompleteDataCube::Constants>.


=head1 DESCRIPTION

Below is the list of instance variables in the global object.

=over 4

=item * dimensions

- The number of dimensions in the cube.

=item * finerUnitGraphs

- An array of L<persistent::PersistentGraph>.  
The finer unit graphs map each coarser unit to a set of finer units that
are contained by it (but not the transitive closure, just the minimal 
set of edges).
There is one graph for each dimension.

=item * coarserUnitGraphs

- An array of L<persistent::PersistentGraph>.  
The coarser unit graphs map each finer unit to a set of coarser units 
that contain it (but not the transitive closure, just the minimal set
of edges).
There is one graph for each dimension.

=item * finerMeasureGraphs

- An array of L<persistent::PersistentGraph>.  
The finer measure graphs map each coarser measure to a set of finer measures
that are below it in the measure hierarchy (but not the transitive closure, 
just the minimal set of edges).
There is one graph for each dimension.

=item * coarserMeasureGraphs

- An array of L<persistent::PersistentGraph>.  
The coarser measure graphs map each finer measure to a set of coarser measures
that are just above it in the measure hierarchy (but not the transitive 
closure, just the minimal set of edges).
There is one graph for each dimension.

=item * unitToMeasureTables

- An array of L<database::Table>.  
Each unit to measure table maps a unit to the measure to which it belongs.
There is one table for each dimension.

=item * measureTables

- An array of L<database::Table>.  
Each measure table is a list of measures for that dimension.
There is one table for each dimension.

=back

=head1 METHODS

=head2 new()

Allocate and intialize the necessary variables.

=cut

#------------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    my ($s) = @_;
    my $myDatabase = new Database($IncompleteDataCube::Constants::databaseName,
                              $IncompleteDataCube::Constants::databaseMode,
                              $IncompleteDataCube::Constants::idTableName,
                              $IncompleteDataCube::Constants::stringTableName);

    $self->{'myDatabase'} = $myDatabase;
    my $unitToMeasureTables = []; 
    my $measureTables = []; 
    my $finerUnitGraphs = []; 
    my $finerMeasureGraphs = []; 
    my $coarserUnitGraphs = []; 
    my $coarserMeasureGraphs = []; 

    $self->{'filterTable'} = 
      $myDatabase->createTable($IncompleteDataCube::Constants::filterTableName);
    $self->{'filterUnitTable'} = 
      $myDatabase->createTable($IncompleteDataCube::Constants::filterUnitTableName);
    $self->{'filterMeasureTable'} = 
      $myDatabase->createTable($IncompleteDataCube::Constants::filterMeasureTableName);
    $self->{'countTable'} = 
      $myDatabase->createTable($IncompleteDataCube::Constants::countTableName);

    my ($i);
    for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
      @$measureTables[$i] = $myDatabase->createTable(
       @$IncompleteDataCube::Constants::dimensionNames[$i] . 
       $IncompleteDataCube::Constants::measureTableName
       );
      @$unitToMeasureTables[$i] = $myDatabase->createTable(
       @$IncompleteDataCube::Constants::dimensionNames[$i] . 
       $IncompleteDataCube::Constants::unitToMeasureTableName
       );
      @$finerUnitGraphs[$i] = new Persistent::Graph($myDatabase,
       @$IncompleteDataCube::Constants::dimensionNames[$i] . 
       $IncompleteDataCube::Constants::finerUnitGraphName
       );
      @$coarserUnitGraphs[$i] = new Persistent::Graph($myDatabase,
       @$IncompleteDataCube::Constants::dimensionNames[$i] . 
       $IncompleteDataCube::Constants::coarserUnitGraphName
       );
      @$finerMeasureGraphs[$i] = new Persistent::Graph($myDatabase,
       @$IncompleteDataCube::Constants::dimensionNames[$i] . 
       $IncompleteDataCube::Constants::finerMeasureGraphName
       );
      @$coarserMeasureGraphs[$i] = new Persistent::Graph($myDatabase,
       @$IncompleteDataCube::Constants::dimensionNames[$i] . 
       $IncompleteDataCube::Constants::coarserMeasureGraphName
       );
      }

    $self->{'unitToMeasureTables'} = $unitToMeasureTables;
    $self->{'measureTables'} = $measureTables;
    $self->{'finerUnitGraphs'} = $finerUnitGraphs;
    $self->{'finerMeasureGraphs'} = $finerMeasureGraphs;
    $self->{'coarserUnitGraphs'} = $coarserUnitGraphs;
    $self->{'coarserMeasureGraphs'} = $coarserMeasureGraphs;

    bless $self, $type;
    }
#------------------------------------------------------------------------

=head2 close()

Close and write all the persistent variables are written to disk.  This
should be called prior to exiting, but even if
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

#=head2 save()
#
#
#Write all the persistent variables are written to disk.  This should be
#called prior to exiting the cube, but even if it not called, in theory,
#all the persistent variables should be maintained since Perl will clean
#them up nicely for us by doing the appropriate unties.
#
#=cut
#
##------------------------------------------------------------------------
#  sub save {
#    my $self = shift;
#    my $myDatabase = $self->{'myDatabase'};
#    my $unitToMeasureTables = $self->{'unitToMeasureTables'};
#    my $measureTables = $self->{'measureTables'};
#    my $finerUnitGraphs = $self->{'finerUnitGraphs'};
#    my $finerMeasureGraphs = $self->{'finerMeasureGraphs'};
#    my $coarserUnitGraphs = $self->{'coarserUnitGraphs'};
#    my $coarserMeasureGraphs = $self->{'coarserMeasureGraphs'};
#
#    my $filterTable = $self->{'filterTable'};
#    my $filterUnitTable = $self->{'filterUnitTable'};
#    my $filterMeasureTable = $self->{'filterMeasureTable'};
#    my $countTable = $self->{'countTable'};
#
#    my ($i);
#    for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
#      @$measureTables[$i]->save();
#      @$unitToMeasureTables[$i]->save();
#      @$finerUnitGraphs[$i]->save();
#      @$coarserUnitGraphs[$i]->save();
#      @$finerMeasureGraphs[$i]->save();
#      @$coarserMeasureGraphs[$i]->save();
#      }
#
#    $countTable->save();
#    $filterTable->save();
#    $filterUnitTable->save();
#    $filterMeasureTable->save();
#    }
##------------------------------------------------------------------------

1;
