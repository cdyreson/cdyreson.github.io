#!/usr/bin/perl -I/home/cdyreson/public_html/cgi-bin/perl/pm -I/home/cdyreson/bin/perl -w

#-----------------------------------------------------------------------
#
#=head1 NAME
#
#updateHomework.pl - a program to update homework on Canvas, use postHomwework.pl to post initially
#
#=head1 SYNOPSIS
#
# updateHomework.pl \
#   -help \
#   -directory dir \
#   -page page.html 
#
#=head1 EXAMPLE USAGE
#
#Update the hw1.htm page 
#
# postprocess.pl \
#   -page hw21
#
#=cut
#
#-----------------------------------------------------------------------

  use strict;
  use warnings;
  use Getopt::Long;


# Global vars
  my $Rows = '';
  my $SHOW_HELP = 0;
  my $ProgramName = 'dbGen';
  my $highCardFactor = 3;
  my $lowCardFactor = 100; 
  my $TIMELINE = 256*256*8;
  my $TIMELINESIZE = 8+8+3;
  my $LENGTH = int($TIMELINE/(256*8));
  my $TIMEID = 1;
  my $EMPTID = 1;
  my @Names = ();
  my @Depts = ();
  my $t = "";

  &ParseCommandLine();

  for (my $i = 0; $i < $Rows + 0; $i++) {
    my $high = int($i/$highCardFactor);
    my $low = $i % $lowCardFactor; 
#    print "INSERT INTO emp(id, name, dept)  VALUES ($i, 'foobar $high', 'simba joe $low');\n"; 
#    print "INSERT INTO empn(id, name, dept)  VALUES ($i, 'foobar $high', 'simba joe $low');\n"; 
    my @total = &generateMyTimes(); 
    my $a = pop @total;
    my ($totalStart, $totalStop) = @$a;
    my %ha = (); # &generateTimes($totalStart, $totalStop);
    my @pa = (); # &generateStartPatternTimes($totalStart);
    my @psa = (); # &generateStopPatternTimes($totalStop);
    my $otherKeys = "";
    my $otherValues = "";
	$EMPTID = $EMPTID + 1;
    foreach my $key (keys %ha) {
      my $times = $ha{$key};
      my $sig = "";
      while (scalar @$times) { 
        my $a = shift @$times;
        my ($start, $stop)  = @$a;
        my $keyString = "s$key$sig";	
	$otherKeys .= ", $keyString";
	$otherValues .= ", $start";
        $sig = "x";
#        $t = $t . "INSERT INTO time(id, start, stop)  VALUES ($i, $start, $stop);\n"; 
      }
    }
    my $patternKeys = "";
    my $patternValues = "";
    while (scalar @pa) {
      my $p = pop @pa;
      my ($count, $value) = @$p;
      $patternKeys .= ", p$count";
      $patternValues .= ", $value";
    }
    my $spatternKeys = "";
    my $spatternValues = "";
    while (scalar @psa) {
      my $p = pop @psa;
      my ($count, $value) = @$p;
      $spatternKeys .= ", p" . $count . "x";
      $spatternValues .= ", $value";
    }
    $TIMEID = $TIMEID + 1;
    print "INSERT INTO emp(id, name, dept, start, stop$otherKeys$patternKeys$spatternKeys)  " .
          "VALUES ($EMPTID, 'foobar $high', 'simba joe $low', $totalStart, $totalStop$otherValues$patternValues$spatternValues);\n"; 
    #print scalar @times . "\n";
  }
  #print $t;

sub generateMyTimes {
   my @result = ();
   my @numberResult = ();
   my $start = int(rand($TIMELINE)); 
   my $len = int(rand($LENGTH)) + 1; 
   push @numberResult, [$start, $start+$len];
   return @numberResult;
}

sub generateTimes { 
   my ($current, $stop) = @_;
   my @result = ();
   my %numberResult = ();
   my $len = $stop - $current;
   #print "Length is $len\n";
   my $s = '1';
   my $segmentLength = 1;
   my $size = $TIMELINE;

   my $diff = 0;
   while ($len > 0) {
     $s = "1";
     my $exit = 0;
     $size = $TIMELINE;
     my $start = $current + $diff;
     $current = $start;
     while ($size > 0) {
       #print "size is $start $size\n";
       if ($start == $size || $start == 0) {
         if ($len >= $size) {
           $len = $len - $size; 
           $diff = $size;
           last;
           $exit = 1;
         }
       }
       $size = int($size/2);
       if ($start >= $size) {
          # go right
          if ($size > 0) {
            $start = $start % $size;
          }
          $s .= "1";
       } else {
          # go left
          $s .= "0";
       }
       if ($exit) {last;}
     }
     if (!defined $numberResult{$size}) {
       $numberResult{$size} = ();
     }
     push @{$numberResult{$size}}, [$current, ($current+$size)-1];
     #print "$start $current $size\n";
     push @result, $s; # "$s\n";
   }
   return %numberResult;
}

sub generateStartPatternTimes { 
   my ($start) = @_;
   my $count = $TIMELINE/2;
   my $value = 2;
   my @times;
   my $current = 0;
   # Evenly divisible by a power of 2?
   while ($current < $start && $count >= 1) {
     if ($current + $count >= $start) {
       push @times, [$count, $current];
     } else {
       $current = $current + $count;
       push @times, [$count, $current];
     }
     #print "start $start count $count current $current\n";
     $count = $count / 2;
   }
   # print "times are " . join(",",@times) . "\n";
   return @times;
}

sub generateStopPatternTimes { 
   my ($stop) = @_;
   my $count = $TIMELINE/2;
   my $value = 2;
   my @times;
   my $current = $TIMELINE;
   # Evenly divisible by a power of 2?
   return @times;
   while ($current > $stop && $count >= 1) {
     if ($current - $count <= $stop) {
       push @times, [$count, $current];
     } else {
       $current = $current - $count;
       push @times, [$count, $current];
     }
     #print "stop $stop count $count current $current\n";
     $count = $count / 2;
   }
   # print "times are " . join(",",@times) . "\n";
   return @times;
}


# cleans the white space from around a string
# Note we are consider <P> as whitespace!
sub cleanWS {
  my ($value) = @_;
  $value = '' if !defined $value;
  #chew up white space at both ends (parsing of fields depends on this!)
  $value =~ s/^[\s\n]*//;
  #$value  =~ s/[\s\n]*$//;
  $value =~ s/(<P>|\s|\n)+$//;
  return $value;
}

#------------------------------------------------------------------------
# ParseCommandLine() - This function parses the command line.
#------------------------------------------------------------------------
sub ParseCommandLine {
  my @switches = (
    'rows=s',       \$Rows,
    'help',         \$SHOW_HELP,
    );
 
  &GetOptions(@switches) || die "use -help switch to display brief help\n";
 
  if ($SHOW_HELP || ($Rows eq '')) {
    print <<EofHelp;
    $ProgramName - Post a page for canas.

    Usage: 

      $ProgramName \
         [-directory directory] \
         -rows 23
 
    Options: 
 
        -rows : number of rows to output
EofHelp
    exit 0;
    }
}

