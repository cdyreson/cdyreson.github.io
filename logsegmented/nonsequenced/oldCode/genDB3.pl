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
    my @times = &generateMyTimes();
    while (scalar @times) {
        my $a = pop @times;
        my ($start, $stop)  = @$a;
        print "INSERT INTO empt(id, name, dept, start, stop)  VALUES ($EMPTID, 'foobar $high', 'simba joe $low', $start, $stop);\n"; 
	$EMPTID = $EMPTID + 1;
#        $t = $t . "INSERT INTO time(id, start, stop)  VALUES ($i, $start, $stop);\n"; 
    }
    $TIMEID = $TIMEID + 1;
    #print scalar @times . "\n";
  }
  print $t;

sub generateMyTimes {
   my @result = ();
   my @numberResult = ();
   my $start = int(rand($TIMELINE)); 
   my $len = int(rand($LENGTH)) + 1; 
   push @numberResult, [$start, $start+$len];
   return @numberResult;
}

sub generateTimes { 
   my @result = ();
   my @numberResult = ();
   my $start = int(rand($TIMELINE)); 
   my $len = int(rand($LENGTH)) + 1; 
   if ($len + $start > $TIMELINE) {
     $len = $TIMELINE - $start;
   }
   my $s = '1';
   my $segmentLength = 1;
   my $size = $TIMELINE;

   my $diff = 0;
   my $current = $start;

   while ($len > 0) {
     $s = "1";
     my $exit = 0;
     $size = $TIMELINE;
     $start = $current + $diff;
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
     push @numberResult, [$current, ($current+$size)-1];
     #print "$start $current $size\n";
     push @result, $s; # "$s\n";
   }
   return @numberResult;
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

