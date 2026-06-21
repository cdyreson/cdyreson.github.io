package Cubette;

=head1 NAME Cubette

The top-level interface to a complete sub-cube within the incomplete
data cube.

This package is part of L<IncompleteDataCube::Cubette>.
For more information on the cube see the 
<a href="IncompleteDataCube/Overview.html">Overview</a>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the 
<a href="IncompleteDataCube/License.html">license</a>
and 
<a href="IncompleteDataCube/License.html">version</a>.

=head1 DESCRIPTION

A Cubette is a complete sub-cube.  This class advertises operations that
are available on Cubettes.

=head1 METHODS

=head2 query($cubetteSpec, $queryUnitsAbove, $queryMeasuresBelow)

=over 4

=item * $cubetteSpec

- An L<IncompleteDataCube::Cubette::CubetteSpecification> 
that specifies the units and measures in the query.

=item * $queryUnitsAbove

- An array of L<DBMDatabase::IdSet>.  Each L<DBMDatabase::IdSet> is the set of 
units above the query's unit in the respective domain in the query.

=item * $cubetteMeasuresBelow

- An array of L<DBMDatabase::IdSet>.  Each L<DBMDatabase::IdSet> is the set of 
measures below the query's measure in the respective domain in the query.

=back

Determine if a query is satisfied by the specified Cubette.
Return true if the query can be satisfied, false otherwise.  For more
on satisfaction, see the relevant papers (e.g., the VLDB paper).

History

=over 4

=item * Nov 14, 1997

- Added count of unit and measure matching.  It now returns the
number of units and measures matched as a list.

=back

=cut

#---------------------------------------------------------------------
  sub query {
    my ($cubetteSpec,
        $queryUnitsAbove,
        $queryMeasuresBelow) = @_;

    my $dimensions = scalar(@$queryMeasuresBelow);
    my $unitsMatched = $dimensions;
    my $measuresMatched = $dimensions;

    #//cycle through the query units and measures making sure they meet criteria
    my ($i);
    my ($measures) = $cubetteSpec->getMeasures();
    my ($units) = $cubetteSpec->getUnits();
    for ($i = 0; $i < $dimensions; $i++) {
      my ($measure) = @$measures[$i];
      my ($unit) = @$units[$i];
      my ($measureSet) = @$queryMeasuresBelow[$i];
      my ($unitSet) = @$queryUnitsAbove[$i];
      #NOV 14,97#return 0 unless $measureSet->memberOf($measure);
      #NOV 14,97#return 0 unless $unitSet->memberOf($unit);
      $measuresMatched-- unless $measureSet->memberOf($measure);
      $unitsMatched-- unless $unitSet->memberOf($unit);
      }  

    #// We have a satisfying cubette!  
    return new QueryCondition($dimensions, $unitsMatched, $measuresMatched);
    }

#---------------------------------------------------------------------

=head2 relaxQuery($cubetteSpec, $queryUnitsAbove, $queryMeasuresBelow)

=over 4

=item * $cubetteSpec

- An L<IncompleteDataCube::Cubette::CubetteSpecification> that specifies 
the units and measures in the query.

=item * $queryUnitsAbove

- An array of L<DBMDatabase::IdSet>.  Each L<DBMDatabase::IdSet> is the set of 
units above the query's unit in the respective domain in the query.

=item * $cubetteMeasuresBelow

- An array of L<DBMDatabase::IdSet>.  Each L<DBMDatabase::IdSet> is the set of 
measures below the query's measure in the respective domain in the query.

=back

Determine if a query can be relaxed to be satisfied by the specified Cubette.
Return true if the query can be relaxed, false otherwise.  For more
on query relaxation or generalization, see the relevant papers 
(e.g., the VLDB paper).

=cut

#---------------------------------------------------------------------
# CURRENTLY NOT USED!!!!!!!!!!!

  sub relaxQuery {
    my ($cubetteSpec, $queryUnitsAbove, $queryMeasuresAbove) = @_;

    #//cycle through the query units and measures to determine how closely
    # they come to meeting the criteria
    # First try to just relax the measures
    my ($i);
    }
#---------------------------------------------------------------------


=head2 sum($satisfyingCubette, $query, $finerUnitGraphs, $unitToMeasureTables, $countTable)

=over 4

=item * $satisfyingCubette

- An L<IncompleteDataCube::Cubette::CubetteSpecification> of the 
cubette that we know satisfies a query.

=item * $query

- An L<IncompleteDataCube::Cubette::CubetteSpecification> of the query.

=item * $finerUnitGraphs

- An array of persistent L<Persistent::Graph>s.  Each finer unit graph is
a graph of which sub-units that are within a given unit (edges go from
coarser to finer units).

=item * $unitToMeasureTables

- An array of database L<DBMDatabase::Table>s.  Each unit to measure table
maps a unit to the corresponding measure.

=item * $countTable

- A L<DBMDatabase::Table>.  The count for each cubette.

=back

Compute a sum aggregate for the answer.
Only call this after you have assured that the cubette is satisfying!
For now we will assume a strict hierarchy.

=cut

#---------------------------------------------------------------------
  sub sum {
    my (
      $satisfyingCubette,
      $query,
      $finerUnitGraphs,
      $unitToMeasureTables,
      $countTable) = @_;

    #// Get all the cubettes within the query, the query might ask for lots
    #// of points.
    my $querySpecs = &_innerCubettes($query, 
                                    $finerUnitGraphs, 
                                    $unitToMeasureTables);

    #// for each part of the query
    my ($cubette);
    foreach $cubette (@$querySpecs) {
      #// Get all the base in the cubette for every point in the query
      my $baseSpecs = &_innerCubettes(
        new CubetteSpecification($cubette->getUnits(), 
                                 $satisfyingCubette->getMeasures()),
        $finerUnitGraphs,
        $unitToMeasureTables);

      #// Sum over each base unit to obtain the result
      my ($sum) = 0;
      my ($j);
      for ($j = 0; $j < scalar(@$baseSpecs); $j++) {
        my ($list) = new IdList(@$baseSpecs[$j]->getUnits());
        my $t = $countTable->retrieveTuple($list);
        if ($t) {
          #// found tuple, add it to running sum
          $sum += $t->getValueAsString();
          }
        }
      $cubette->{'value'} = new Id($sum);
      }
    return $querySpecs;
    } 
#---------------------------------------------------------------------

=head2 increment($cubette, $log, $finerUnitGraphs, $unitToMeasureTables, $countTable, $doneThisRound, $verbose)

=over 4

=item * $cubette

- An L<IncompleteDataCube::Cubette::CubetteSpecification> 
of the cubette to increment.

=item * $log

- An L<IncompleteDataCube::Cubette::CubetteSpecification> of the log file?

=item * $filterUnitTable

- A L<DBMDatabase::Table>.  The units for each filter.

=item * $filterMeasureTable

- A L<DBMDatabase::Table>.  The measures for each filter.

=item * $countTable

- A L<DBMDatabase::Table>.  The count for each cubette.

=item * $doneThisRound

- An L<DBMDatabase::IdSet>.  A set of filters that have been counted.

=item * $verbose

- Turn on if verbose reporting wanted.

=back

Increment the count associated with this filter.

=cut

#---------------------------------------------------------------------
  sub increment {
    my (
      $cubette,
      $log, 
      $filterUnitTable,
      $filterMeasureTable,
      $countTable,
      $doneThisRound, 
      $VERBOSE) = @_;
    my $m = $filterMeasureTable->retrieveTuple($cubette);
    die "No measures for this filter!" unless $m;
    my $measures = $m->getValueAsIdList();
    my ($count);

    #// find the units for the count
    my @countUnits = $log->unitsAtMeasures($measures);
    my $countList = new IdList(\@countUnits);
    return if (defined $$doneThisRound{$countList->toBytes()});
    my $t = $countTable->retrieveTuple($countList);
    if ($t) { $count = $t->getValueAsString(); }
    else { $count = 0;}
    $count++;
    $countTable->insertTuple(new Tuple($countList, new StringCol($count)));
    print "increment count for " . $countList->image() . "\n" if $VERBOSE;
    $$doneThisRound{$countList->toBytes()} = 1;
    }
#---------------------------------------------------------------------

=head2 quickIncrement($countList, $countTable)

=over 4

=item * $countList

- A L<DBMDatabase::IdList> of the units to increment the count of.

=item * $countTable

- A L<DBMDatabase::Table>.  The count for each cubette.

=back

Quickly increment the count for a particular multidimensonal unit.

=cut

#---------------------------------------------------------------------
  sub quickIncrement {
    my (
      $countList,
      $countTable,
      $verbose) = @_;
    my $t = $countTable->retrieveTuple($countList);
    if ($t) { $count = $t->getValueAsString(); }
    else { $count = 0;}
    $count++;
    $countTable->insertTuple(new Tuple($countList, new StringCol($count)));
    print "quick increment count for " . $countList->image() . "\n" if $verbose;
    }
#---------------------------------------------------------------------

=head1 PRIVATE METHODS

=head2 _innerCubettes($query, $finerUnitGraphs, $unitToMeasureTables)

=over 4

=item * $query

- An L<IncompleteDataCube::Cubette::CubetteSpecification>.

=item * $finerUnitGraphs

- An array of L<Persistent::Graph>s.  Each finer unit graph is
a graph of which sub-units that are within a given unit (edges go from 
coarser to finer units).

=item * $unitToMeasureTables

- An array of database L<DBMDatabase::Table>s.  Each unit to measure table
maps a unit to the corresponding measure.

=back

Compute the query units
Only call this after you have assured that the cubette is satisfying!
For now we will assume a strict hierarchy.

=cut

#---------------------------------------------------------------------
  sub _innerCubettes {
      my ($query,
      my  $finerUnitGraphs,
      my  $unitToMeasureTables) = @_;

    #// load cubette unit and measures off disk
    my $innerUnits = [];
    
    #// get all the units at the query measure.
    my ($i);
    my $queryUnits = $query->getUnits();
    my $queryMeasures = $query->getMeasures();
    for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
      my $g = @$finerUnitGraphs[$i];
      my $unitSet = 
        $g->reachableSetStopAtMeasure(
           @$queryUnits[$i], 
           @$queryMeasures[$i], 
           @$unitToMeasureTables[$i]);
      @$innerUnits[$i] = $unitSet;
      }  

    #// now take the crossproduct of each combination of the real query units
    return &_newCrossProduct($innerUnits, $queryMeasures);
    }
#---------------------------------------------------------------------

=head2 _newCrossProduct($unitSets, $measureList)

=over 4

=item * $unitSets

- An array of L<DBMDatabase::IdSet>s.  

=item * $measureList

- An array of L<DBMDatabase::IdSet>s.  

=back

Determine the cross product in terms of which cubettes, of a set
of units and a list of measures (so all the units at a particular measure).

=cut

#---------------------------------------------------------------------
  sub _newCrossProduct {
    my ($unitSets, $measureList) = @_;
    my $unitLists =  [];
    my $unitSet;
    foreach $unitSet (@$unitSets) {
      push @$unitLists, $unitSet->toIdArrayReference();
      }
    my $result = [];
    &_newInnerCrossProduct($result, 0, [], $unitLists, $measureList);
    return $result;
    }
#---------------------------------------------------------------------

=head2 _newInnerCrossProduct

A helper function for _newCrossProduct.

=cut

#---------------------------------------------------------------------
  sub _newInnerCrossProduct {
    my ($result, $index, $unitsSoFar, $unitLists, $measureList) = @_;
    if ($index >= $IncompleteDataCube::Constants::dimensions) {

      # clone the $units accumulated to this point
      my $temp = [];
      my $i;
      for ($i = 0; $i < scalar(@$unitsSoFar); $i++) {
        @$temp[$i] = @$unitsSoFar[$i];
        }
      #push @$result, new CubetteSpecification($unitsSoFar, $measureList);
      push @$result, new CubetteSpecification($temp, $measureList);
      return;
      }
    else {
      my $unit;
      my $unitList = @$unitLists[$index];
      foreach $unit (@$unitList) {;
        @$unitsSoFar[$index] = $unit;
        &_newInnerCrossProduct($result, $index+1, $unitsSoFar, $unitLists, $measureList);
        }
      }
    }
#---------------------------------------------------------------------

1;
