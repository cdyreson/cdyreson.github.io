#!/usr/bin/perl

use strict;

# slurp input
my $maxNumberOfPapersInMonth = 0;
my $numberOfDeskRejections = 0;
my $numberOfNonDeskRejections = 0;
my $totalDaysDeskRejections = 0;
my $totalDaysNonDeskRejections = 0;
my $longestInMonth = 0;
my $output = "";

my @lines = <>;
my %calendar = (
'January' => 1, 
'February' => 2, 
'March' => 3,
'April' => 4, 
'May' => 5,
'June' => 6,
'July' => 7,
'August' => 8,
'September' => 9,
'October' => 10,
'November' => 11,
'December' => 12);

foreach my $line (@lines) {
  # skip blank lines
  next unless $line =~ /:/;

  # Do we have a month?
  if ($line =~ /\[/) {
     my $before = $`;
     my $after = $';
     $before =~ s/^\s+//;
     $before =~ s/://;
     $before =~ s/\s+$//;
     $after =~ s/^\s+//;
     $after =~ s/://;
     $after =~ s/\s+$//;

     my ($x, $y) = split(" ", $before);
     # is it in the calendar?
     die "undefined month $x\n" unless defined $calendar{$x};

     if ($output ne "") { print "$output, $numberOfDeskRejections, $totalDaysDeskRejections, $numberOfNonDeskRejections, $totalDaysNonDeskRejections, $longestInMonth\n"; }

     $output = "$y, $calendar{$x}";

     # we have a new month
     $after =~ /(\d+)/;
     die "bad number of papers $after\n" unless defined $1;
     $maxNumberOfPapersInMonth = $1;
     $numberOfDeskRejections = 0;
     $numberOfNonDeskRejections = 0;
     $totalDaysDeskRejections = 0;
     $totalDaysNonDeskRejections = 0;
     $longestInMonth = 0;


     }
  else {
     # split on colon
     my ($before, $after) = split(/:/, $line);
     $before =~ s/^\s+//;
     $before =~ s/\s+$//;
     $after =~ s/^\s+//;
     $after =~ s/\s+$//;

     # split further
     my ($x, $y) = split(" ", $after);
     #print  ":$x:$y:\n";
     $x =~ /(\d+)/;
     die "bad number of days $x\n" unless defined $1;
     my $num = $1;
    
     if ($num > $longestInMonth) { $longestInMonth = $num; }
     if ($y eq 'r' || $y eq 'x') {
       # reject
       $numberOfNonDeskRejections += 1;
       $totalDaysNonDeskRejections += $num;
       }
     elsif ($y eq 'a') {
       # accept
       $numberOfNonDeskRejections += 1;
       $totalDaysNonDeskRejections += $num;
       }
     elsif ($y eq '#') {
       # desk reject 
       $numberOfDeskRejections += 1;
       $totalDaysDeskRejections += $num;
       }
     else {
       die "unknown code $y\n";
       }

     }
}  
if ($output ne "") { print "$output, $numberOfDeskRejections, $totalDaysDeskRejections, $numberOfNonDeskRejections, $totalDaysNonDeskRejections, $longestInMonth\n"; }

