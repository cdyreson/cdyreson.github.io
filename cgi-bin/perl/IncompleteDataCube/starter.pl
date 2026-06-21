#!/net/local/bin/perl -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/pm -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/IncompleteDataCube/lib

# Copyright (c) 1996. Curtis E. Dyreson. All rights reserved.  This 
# software is covered under the general license and lack of 
# warranty as stated in the installation kit. WARNING! DANGER WILL 
# ROBINSON! USE AT YOUR OWN RISK!

use strict;
use CGI_Lite;
# use DBMDatabase;
# use Persistent::Graph;
use IncompleteDataCube;

# What is the separator character to use?
my $SEPARATOR = "\f";
my $INNERSEPARATOR = "\e";

# Define possible actions
my $submitQueryAction = "submit query";
my $readjustAction = "adjust units";
my $drillDownAction = "drill down";
my $reloadMeasuresAction = "reload measures";
my $okAction = "units OK";

# Is is possible to query from this configuration?
my $canQuery = 1;

# Open databases
my $global = new IncompleteDataCube::Globals();

my $LISTSIZE = 5;
my $cgi = new CGI_Lite;

my $ROOT = 'http://www.eecs.wsu.edu/cgi-bin/cgiwrap/~cdyreson/cgi-bin/perl/IncompleteDataCube';

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 my $data = $cgi->parse_form_data();

# common routine to clean junk
 require "cleanWS.pm";

# Make security checks - none really
#require "security.pm";

# figure out which action to apply
my $GUI = &whichAction();

print <<"PAGE";





























<html>
<head>
<title>An Incomplete Data Cube - Perl Demonstration</title>
</head>
<body>
<table summary="table" border="0" cellPadding="0" cellSpacing="0">
  <tr>
    <td bgColor="#DEFEDE" align=left valign=top>
      <table summary="table" border="0" cellSpacing="1">
        <tr>
          <td >
             <img src="https://cdyreson.github.io/IncompleteDataCube/cube.gif"
                  alt="cube logo" 
                  width="90" 
                  height="68"
                  border="2">
          </td>
        </tr>
      </table>
    </td>
    <td bgColor="#DEFEDE">
      &nbsp;
    </td>
    <td align=left valign=top bgColor="#DEFEDE">
        <table summary="table" border="0" cellSpacing="1">
          <tr>
            <td bgcolor="#DEFEDE"> 
              <big>An Incomplete Data Cube - Perl Demonstration</big>
              <br>
              <font color="#800000" size="2">
              A Data Cube Tool for Missing Data
              </font>
            </td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td align=center vAlign="top" bgcolor="#DEFEDE">
      <table summary="table" border="0" cellpadding="2" cellspacing="2">
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/IncompleteDataCube/index.htm">
              Cube Home&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/IncompleteDataCube/overview.htm">
              Overview&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/IncompleteDataCube/publications.htm">
              Publications
             </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/IncompleteDataCube/javademo.htm">
              Java Demo&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/IncompleteDataCube/perldemo.htm">
              Perl Demo&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/IncompleteDataCube/code.htm">
              Code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#DEFEDE">&nbsp;
        </td></tr>
        <tr><td colspan=2 bgcolor="#DEFEDE">
            Curtis Dyreson
        </td></tr>
        <tr><td bgcolor="#DEFEDE">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://digital.cs.usu.edu/~cdyreson/">
              Home
              </a>
        </td></tr>
        <tr><td bgcolor="#DEFEDE">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/publications.htm">
              Publications
            </a>
        </td></tr>
        <tr><td bgcolor="#DEFEDE">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/projects.htm">
              Projects
             </a>
        </td></tr>
        <tr><td bgcolor="#DEFEDE">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/code.htm">Software</a>
        </td></tr>
        <tr><td bgcolor="#DEFEDE">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/demos.htm">Demos</a>
        </td></tr>
        <tr><td bgcolor="#DEFEDE">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/teaching.htm">Teaching</a>
        </td></tr>
        <tr><td bgcolor="#DEFEDE">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/contact.htm">Contact me</a>
        </td></tr>
      </table>
    </td>
    <td>
    </td>
    <td vAlign="top">
      <br>
      
  <a href="https://cdyreson.github.io//IncompleteDataCube/demoinstructions.htm">Instructions on use</a>
  <P>
  $GUI

      <br>
     &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      <hr noshade size="1">
     <a style="text-decoration:none" 
               href="http://digital.cs.usu.edu/~cdyreson">Curtis E. Dyreson</a>
     &copy; 1995-2001. All rights reserved.
    </td>
  </tr>
  <tr>
    <td height="20" bgColor="#DEFEDE">&nbsp;</td>
    <td colspan=2 bgColor="#DEFEDE" align="right">
      <small><i>E-mail questions or comments to Curtis.Dyreson at usu.edu</i></small>
    </td>
  </tr>
</table>
</body>
</html>

PAGE

sub printMeasureTables {
  my $measureTables = $global->{'measureTables'};
  my $i;
  my $result = '';
  $result .= '<tr>';
  for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
    $result .= '<td valign=top>';
    $result .= @$IncompleteDataCube::Constants::dimensionNames[$i] . ' measures<BR>';
    my $measureTable = @$measureTables[$i];
    my @keys = sort {$a <=> $b} $measureTable->enumerateKeys();
    my $key;
    my @k = ();
    foreach $key (@keys) {
      my $tempId = Id::fromBytes($key);
      push @k, $tempId->toString();
      }
    while (scalar(@k) < $LISTSIZE) { push @k, ""; }
    $result .= &printScrollingList("measure$i", \@k);
    $result .= '</td>';
  }
  $result .= '</tr>';
  return $result;
}

# First time entered? or Measures Reloaded?
sub printUnitTableWithMeasuresLoaded {
  my ($i) = @_;
  my $result = '';
  $result .= @$IncompleteDataCube::Constants::dimensionNames[$i] . " units<BR>";
  my $unitTables = $global->{'measureTables'};
  my $unitTable = @$unitTables[$i];
  my @keys = sort {$a <=> $b} $unitTable->enumerateKeys();
  my $key;
  my @k = ();
  foreach $key (@keys) {
    my $tempId = Id::fromBytes($key);
    push @k, $tempId->toString();
    }
  while (scalar(@k) < $LISTSIZE) { push @k, ""; }
  delete $$data{"unit$i"};
  $result .= &printScrollingList("unit$i", \@k);
  $result .= "<BR>";

  $canQuery = 0;
  $result .= &printUnitActions($i, $drillDownAction);
  $result .= &hidden("measuresLoaded$i","measuresLoaded$i");
  return $result;
}  

# The previous unit table is still in force
sub printPreviousUnitTable {
  my ($i) = @_;
  my $result = '';
  my @units = split($SEPARATOR, $$data{"unitOKList$i"});
  $result .= @$IncompleteDataCube::Constants::dimensionNames[$i] . " units<BR>";
  while (scalar(@units) < $LISTSIZE) { push @units, ""; }
  $result .= &printScrollingList("unit$i", \@units);
  $result .= "<BR>";
  $result .= &hidden("unitOKList$i", join($SEPARATOR,@units));
  $result .= &printUnitActions($i, $okAction);
  return $result;
}  

sub printUnitActions {
  my ($i,$option) = @_;
  my $result = '';
  my @k = ($okAction, $drillDownAction, $reloadMeasuresAction);
  $result .= &printScrollingList("unitAction$i", \@k, 1, $option);
  return $result;
}

# Drill down on the sucker
sub printUnitTableWithDrillDown {
  my ($i) = @_;
  my @k;
  my $result = '';
  # should we look in the measures?
  if (!$$data{"unit$i"}) {
    $result .= "cannot drill down<BR>no unit or measure<BR>selected<BR>";
    $result .= &printPreviousUnitTable($i);
    return $result;
    }

  $result .= @$IncompleteDataCube::Constants::dimensionNames[$i] . " units<BR>";
  # is the thing to drill down a measure?
  if (defined $$data{"measuresLoaded$i"}) {
    my $unitToMeasureTables = $global->{'unitToMeasureTables'};
    my $unitToMeasureTable = @$unitToMeasureTables[$i];
    my $unit;
    my $measure = Id::fromString($$data{"unit$i"});
    my @units = ();
    my $href = $unitToMeasureTable->enumerate();
    my $id;
    foreach $unit (keys %$href) {
      $id = Id::fromBytes($unit);
      my $r = $unitToMeasureTable->retrieveTuple($id);
      my $toTest = $r->getValueAsId(); 
      if ($toTest->equals($measure)) {
        push @units, $unit + 0;
        }
      }
    # sort Units by underlying token value
    my @unitsSorted = sort {$a <=> $b} @units;
    @units = ();
    foreach $id (@unitsSorted) {
      my $tempId = Id::fromBytes($id);
      push @units, $tempId->toString($tempId);
      }
    delete $$data{"unit$i"};
    $result .= &hidden("unitOKList$i", join($SEPARATOR,@units));
    $result .= &printScrollingList("unit$i", \@units);
    $result .= '<BR>';
    $result .= &printUnitActions($i, $okAction);
    $canQuery = 1;
    return $result;
    } 

  # we are drilling down on a unit  
  my $finerUnitGraphs = $global->{'finerUnitGraphs'};
  my $finerUnitGraph = @$finerUnitGraphs[$i];
  my $unitTable = $finerUnitGraph->{'edges'};
  my @units = ();
  my $t = Id::fromString($$data{"unit$i"});
  my $r = $unitTable->retrieveTuple($t);
  if ($r) {
    # tuple is found, format list of edges at this node
    my $idSet = $r->getValueAsIdSet();
    my $id;
    foreach $id ($idSet->enumerate()) {
      push @units, $id->toBytes() + 0;
      }
    # should probably sort by String Names on insertion
    my @unitsSorted = sort {$a <=> $b} @units;
    @units = ();
    foreach $id (@unitsSorted) {
      my $tempId = Id::fromBytes($id);
      push @units, $tempId->toString();
      }
    }
  else {
    # tuple is not found, so this is the last one, just show it
    @units = ($$data{"unit$i"});
    }
  delete $$data{"unit$i"};
  $result .= &hidden("unitOKList$i", join($SEPARATOR,@units));
  $result .= &printScrollingList("unit$i", \@units);
  $result .= '<BR>';
  $result .= &printUnitActions($i, $okAction);
  return $result;
}  

sub printGUI {
  my $result = '';
  $result .= "<FORM METHOD=\"POST\" ACTION=\"$ROOT/starter.pl\">";
  # $result .= &hidden('files',$files);
  $result .= '<center><table border=3 cellpadding=5>';
  $result .= '<tr></tr>';
  $result .= &printMeasureTables();
  $result .= &printUnitTables();
  $result .= '</table>';
  $result .= "\n<P>\n";
  $result .= "<INPUT TYPE=\"submit\" NAME=\"$readjustAction\" VALUE=\"$readjustAction\">";
  if ($canQuery) {$result .= "<INPUT TYPE=\"submit\" NAME=\"$submitQueryAction\" VALUE=\"$submitQueryAction\">";}
  $result .= '</FORM>';
  return $result;
}

sub printUnitTables {
  my $result = '';
  my $unitTables;
    $unitTables = $global->{'unitToMeasureTables'};
  my $i;
  my @k;
  $result .= '<tr>';
  for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
    $result .= '<td>';
    # First time entered? or Measures Reloaded?
    if ((!defined $$data{"unitAction$i"}) ||
        ($$data{"unitAction$i"} eq $reloadMeasuresAction)) {
      $result .= &printUnitTableWithMeasuresLoaded($i);
      }
    # drill down to finer units selected?
    elsif ($$data{"unitAction$i"} eq $drillDownAction) {
      $result .= &printUnitTableWithDrillDown($i);
      }
    # must be an OK action
    else { 
      $result .= &printPreviousUnitTable($i);
      }
    $result .= '</td>';
    }
  $result .= '</tr>';
  return $result;
}

sub doQuery {
  my @u = ();
  my @m = ();
  my @queryUnitsAbove = ();
  my @queryMeasuresBelow = ();
  my ($i, $k);
  my $result = '';

  for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
    if (!defined $$data{"unit$i"}) {
      $result .= "No ".@$IncompleteDataCube::Constants::dimensionNames[$i]." unit selected.";
      return $result;
      }
    push @u, Id::fromString($$data{"unit$i"});
    if (!defined $$data{"measure$i"}) {
      $result .= "No ".@$IncompleteDataCube::Constants::dimensionNames[$i]." measure selected.";
      return $result;
      }
    push @m, Id::fromString($$data{"measure$i"});
    }

  for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
    my $temp = $global->{'coarserUnitGraphs'};
    my $g = @$temp[$i];
    $queryUnitsAbove[$i] = new IdSet();
    $queryUnitsAbove[$i] = $g->reachableSet($u[$i]);
    $temp = $global->{'finerMeasureGraphs'};
    $g = @$temp[$i];
    $queryMeasuresBelow[$i] = new IdSet();
    $queryMeasuresBelow[$i] = $g->reachableSet($m[$i]);
    }

  my $querySpec = new CubetteSpecification(\@u, \@m);
  my $table = $global->{'filterUnitTable'};
  my %partial = {};
  foreach my $keyAsBytes ($table->enumerateKeys) {
    my $key = Id::fromBytes($keyAsBytes);
    my $testSpec = new CubetteSpecification($key, 
        $global->{'filterUnitTable'}, 
        $global->{'filterMeasureTable'});
    my $queryCondition = Cubette::query(
                                    $testSpec,
                                    \@queryUnitsAbove,
                                    \@queryMeasuresBelow);
    if ($queryCondition->fullySatisfied()) {
      # We have a satisfying cubette
      my $answer = 
           Cubette::sum(
             $testSpec, 
             $querySpec, 
             $global->{'finerUnitGraphs'}, 
             $global->{'unitToMeasureTables'}, 
             $global->{'countTable'});
      for ($k = 0; $k < scalar(@$answer); $k++) {
        $result .= @$answer[$k]->image() . "<BR>\n";
        }
      return $result;
      }
    elsif ($queryCondition->partiallySatisfied()) {
      $partial{$keyAsBytes} = $queryCondition;
      }
    }

  # no satisfying cubette
  $result .= "Sorry, not enough information in the cube to satisfy your query.";

  # are there partial queries?
  my @ranked = sort { $partial{$b}->rank() <=> $partial{$a}->rank() } keys %partial;
  $result .= "You may want to try one of the following." 
    if scalar(@ranked); 
  foreach (@ranked) {
    my $key = Id::fromBytes($_);
    my $filter = new CubetteSpecification($key, 
        $global->{'filterUnitTable'}, 
        $global->{'filterMeasureTable'});
    $result .= "<BR>" . $filter->imageWithMeasures();
    }
  return $result;
} 

# figure out which action to apply
sub whichAction {
  my ($i);
  my $result = '';
  if (defined $$data{$submitQueryAction}) { 
    $result .= &printGUI();
    $result .= &doQuery(); 
    return $result; 
    }
  if (defined $$data{$readjustAction}) { 
    for ($i = 0; $i < $IncompleteDataCube::Constants::dimensions; $i++) {
      my $choice = $$data{'unitAction$i'}; 
      if ($choice eq $drillDownAction) {
        # user wants finer units/etc
        &drillDown($i);
        }
      elsif ($choice eq $reloadMeasuresAction) {
        # user wants to reload the measures
        &reloadMeasures($i);
        }
      else {
        # user wants to keep as is
        }
      }
    $result .= &printGUI();
    return $result;
    }
  $result .= &printGUI();
  return $result;
}    

sub printScrollingList {
  my $param = shift @_;
  my $listref = shift @_;
  my $size = (shift @_) || $LISTSIZE;
  my $selected = (shift @_) || $$data{$param} || "";
  my $result = '';
  $result .= "<SELECT NAME=\"$param\" SIZE=" . $size . ">";
  if ($selected) {
    $result .= "<OPTION  VALUE=\"$selected\" SELECTED>$selected\n";
    }
  my $option;
  if ($selected) {
    foreach $option (@$listref) {
      if ($selected ne $option) {
        $result .= "<OPTION  VALUE=\"$option\">$option\n";
        }
      }
    }
  else {
    foreach $option (@$listref) {
      $result .= "<OPTION  VALUE=\"$option\">$option\n";
      }
    }
  $result .= "</SELECT>";
  return $result;
}

# Fix it so that hidden variables work correctly
sub hidden {
  my ($name, $value) = @_;
  return "<INPUT TYPE=\"HIDDEN\" NAME=\"$name\" VALUE=\"$value\">";
}
