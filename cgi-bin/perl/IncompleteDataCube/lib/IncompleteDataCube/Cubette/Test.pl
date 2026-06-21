#!/usr/local/bin/perl -w -I..:../..
 
# A test program 
use IncompleteDataCube;

$global = new IncompleteDataCube::Globals();
@u = ();
@m = ();
@queryUnitsAbove = ();
@queryMeasuresBelow = ();

$u[0] = Id::fromString("World");
$u[1] = Id::fromString("All of Time");
$u[2] = Id::fromString("SAMaker total");
$m[0] = Id::fromString("world");
$m[1] = Id::fromString("months");
$m[2] = Id::fromString("cgi-bin scripts");
#$u[0] = Id::fromString("Australia");
#$u[1] = Id::fromString("All of Time");
#$u[2] = Id::fromString("SAMaker total");
#$m[0] = Id::fromString("countries");
#$m[1] = Id::fromString("months");
#$m[2] = Id::fromString("cgi-bin scripts");
for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
  my $temp = $global->{'coarserUnitGraphs'};
  my $g = @$temp[$i];
  $queryUnitsAbove[$i] = $g->reachableSet($u[$i]);
  #print "Units above for $i\n";
  #print $queryUnitsAbove[$i]->image();
  $temp = $global->{'finerMeasureGraphs'};
  $g = @$temp[$i];
  $queryMeasuresBelow[$i] = $g->reachableSet($m[$i]);
  #print "Measures below for $i\n";
  #print $queryMeasuresBelow[$i]->image();
  }

my $querySpec = new CubetteSpecification(\@u, \@m);
my $table = $global->{'filterUnitTable'};
foreach $keyAsBytes ($table->enumerateKeys) {
  my $key = Id::fromBytes($keyAsBytes);
  my $testSpec = new CubetteSpecification($key, 
        $global->{'filterUnitTable'}, 
        $global->{'filterMeasureTable'});
  my $condition = Cubette::query(
        $testSpec,
        \@queryUnitsAbove,
        \@queryMeasuresBelow);
  if ($condition->fullySatisfied()) {
    my $answer = 
           Cubette::sum(
             $testSpec, 
             $querySpec, 
             $global->{'finerUnitGraphs'}, 
             $global->{'unitToMeasureTables'}, 
             $global->{'countTable'});
        #//System.out.println(answer.length);
        print " And the answer is :\n";
        for ($k = 0; $k < scalar(@$answer); $k++) {
          my $ref = @$answer[$k];
          print $ref->image() . "\n";
          }
        exit(0);
        }
      }

