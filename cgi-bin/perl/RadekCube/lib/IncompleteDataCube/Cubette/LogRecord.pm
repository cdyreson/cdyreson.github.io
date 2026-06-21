package LogRecord;

=head1 NAME LogRecord

Class encapsulating a record in a logfile.

This package is part of L<IncompleteDataCube::Cubette>.
For more information on the cube see the 
<a href="IncompleteDataCube/Overview.html">Overview</a>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the 
<a href="IncompleteDataCube/License.html">license</a>
and 
<a href="IncompleteDataCube/License.html">version</a>.

=head1 DESCRIPTION

A log record is a structure consisting of units above a unit.
It is used to update the count associated with filters for each
record read from a log file.

=head1 METHODS

=head2 new($units, $unitToMeasureTables, $coarserUnitGraphs)

=over 4

=item * $units

- An L<DBMDatabase::IdList>.  A list of units to look above in each
dimension.

=item * $unitToMeasureTables

- An array of L<DBMDatabase::Table>.  Tables that map units to particular
measures.

=item * $coarserUnitGraphs

- An array of L<persistent::PersistentGraph>.  Directed graphs that 
lead from finer to coarser units.

=back

Constuct a new log record.

=cut

#---------------------------------------------------------------------
sub new {
  my $type = shift;
  my $self = {};
  my ($units, $unitToMeasureTables, $coarserUnitGraphs) = @_;
  $self->{'units'} = $units;
  my @measures;
  my @measuresForUnitsHashtables;
  my @unitsAboveSets;
  #// Cycle through each dimension
  my $i;
  for ($i = 0; $i < scalar(@$units); $i++) {
    #// get measure for each unit
    $measures[$i] = (@$unitToMeasureTables[$i]->retrieveTuple(@$units[$i]))->getValueAsId();
    #// Allocate hash tables
    $measuresForUnitsHashtables[$i] = {};
    #// Grab the units above this one
    my $reachableSet = @$coarserUnitGraphs[$i]->reachableSet(@$units[$i]);
    $unitsAboveSets[$i] = $reachableSet;
    #// Look at each of the units above
    my $reachableUnit;
    foreach $reachableUnit ($reachableSet->enumerate()) {
      #// Hash each on the measure
      my $table = @$unitToMeasureTables[$i];
      my $t = $table->retrieveTuple($reachableUnit);
      croak("no measure for " . $reachableUnit->toString()) unless $t;
      $measure = $t->getValueAsId();
      my $h = $measuresForUnitsHashtables[$i];
      $$h{$measure->toBytes()} = $reachableUnit;
      }
    }
  $self->{'measures'} = \@measures;
  $self->{'measuresForUnitsHashtables'} = \@measuresForUnitsHashtables;
  bless $self, $type;
  }
#---------------------------------------------------------------------


=head2 unitsAtMeasures($measures)

=over 4

=item * $measures 

- A <DBMDatabase::IdList> of measures.

=back

Builds a list of the units in each dimension for a measure.  Returns the
constructed list.

=cut

#---------------------------------------------------------------------
  sub unitsAtMeasures {
    my ($self, $measures) = @_;
    my @theUnits;
    #// Cycle through each dimension
    my $i;
    my $measuresForUnitsHashtables = $self->{'measuresForUnitsHashtables'};
    my @ids = $measures->toIdArray();
    for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
      #// find corresponding unit
      my $h = @$measuresForUnitsHashtables[$i];
      my $id = $ids[$i];
      $theUnits[$i] = $$h{$id->toBytes()};
      }
    return @theUnits;
    }
#---------------------------------------------------------------------

1;
