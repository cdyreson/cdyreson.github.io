#!/usr/local/bin/perl -IC:\Inetpub\wwwroot\CDyreson\cgi-bin\perl\pm

#***************************************************************************
# For more info on this software surf to
#       http://www.cs.jcu.edu.au/~curtis/software.html
# You are welcome to send bug reports, fixes, comments, questions or
# suggestions to Curtis Dyreson at curtis@cs.jcu.edu.au.
#
# Copyright (c) 1997 Curtis Dyreson. All rights reserved.  This software is 
# covered under the general license and lack of warranty as stated in the
# installation kit. WARNING! DANGER WILL ROBINSON! USE AT YOUR OWN RISK!
#****************************************************************************

# use strict;
 use CGI_Lite;
 use TextDB;
 my $cgi = new CGI_Lite;

# What is the separator character to use in the criteriadb?
 my $QUESTIONSEPARATOR = ".";
 
# What is the name of the stats database?
 my $statsDbName = "statistics";

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 my $data = $cgi->parse_form_data();

# common routine to clean junk
 require "cleanWS.pm";

 my $hidden = new TextDB($$data{'hidden'});
 
# set up the global state
 my $collectStatistics = $hidden->FETCH('collectStatistics');
 my $path = $hidden->FETCH('path');
 my ($junk, $rest) = split('/', $path);
 $path = q{C:\Inetpub\wwwroot\CDyreson\prweb\125\lessons\ };
 $path = &cleanWS($path) . $rest;
 my $login = $hidden->FETCH('login');
 my %globalState = (
   answerText => ' ',
   explanation => ' ',
   hintText => ' ',
   questionsText => ' ',
   questionNumber => 'zzzz',
   pagesRemaining => 'zzzz',
   pageNumber => 'zzzz',
   questionId => 'zzzz',
   );
 
# Open databases
 my $randomdb = new TextDB;
 $randomdb->load("$path" . '\randomdb');
 my $statsQuestiondb = new TextDB;
 $statsQuestiondb->load("$path" . '\statsQuestiondb');


# figure out which action to apply
 &starter();

#========================================================================
# Build the stats display 
#========================================================================
sub starter {
  my %stats;
  tie %stats, TextDB;

  # load the stats db if it exists
  if (-e "$path\\$statsDbName") { (tied %stats)->load("$path\\$statsDbName"); }
  else {
    # initialise the stats db
    # get how many question sets are on each page
    my @questionSetsPerPage = @{ $randomdb->FETCH('values') };
 
    my $pageCounter;
    my $questionSetCounter;
    my $questionName;
    my @remaining = ();
 
    # iterate through the pages
    for ($pageCounter = 1;
         $pageCounter <= scalar @questionSetsPerPage;
         $pageCounter++) {
      # get how many alternatives of each question exist
      my $questionSets = @questionSetsPerPage[$pageCounter-1];
      my $questionSetCounter = 0;
 
      # iterate through the question sets on a page
      for ($questionSetCounter = 1;
           $questionSetCounter <= scalar @$questionSets;
           $questionSetCounter++) {
 
        my $questionCounter = 0;
        for ($questionCounter = 1;
             $questionCounter <= scalar @$questionSets[$questionSetCounter-1];
             $questionCounter++) {

        # Construct a question file name
          $questionName = "$pageCounter$QUESTIONSEPARATOR" .
                          "$questionSetCounter$QUESTIONSEPARATOR" .
                          "$questionCounter";
          $stats{$questionName} = {};
          $stats{$questionName}->{'HINT'} = 0;
          $stats{$questionName}->{'EXPLANATION'} = 0;
          $stats{$questionName}->{'WRONG'} = {};
          $stats{$questionName}->{'RIGHT'} = {};
          $stats{$questionName}->{'TOTALRIGHT'} = 0;
          $stats{$questionName}->{'TOTAL'} = 0;
          }
        }
      }
    }

  # run through all the stats and collect them
  while (<$path\\log*>) {
    my $fileName = $_;
    # peel off the actual file name
    #$fileName =~ m!\/([^\/]*)$!;
    #$fileName = $1;
    # open the student log
    open (LOG, "<$fileName") || &internalError("could not open $fileName\n");
    $fileName =~ /log\d+\.\d+$/;
    my $newFileName = "$`o$&";
    open (PROCLOG, ">$newFileName") || &internalError("could not open $newFileName\n");
    # iterate through the log
    while (<LOG>) {
      my $line = $_; 
      print PROCLOG $line;
      chomp $line;
      if ($line !~ /\d+([^\d])/) {next;}
      my $sep = $1;
      my @items = split(/\Q$sep\E/, $line);
      my $time = shift @items;
      my $action = shift @items;
      my $questionId = shift @items;
      my $tried = shift @items;
      my $correct = shift @items;
      $stats{$questionId} = {} unless defined $stats{$questionId};
      my $questionref = $stats{$questionId};
      $$questionref{'TOTALRIGHT'}= 0 if !defined $$questionref{'TOTALRIGHT'};
      $$questionref{'TOTAL'} = 0 if !defined $$questionref{'TOTAL'};
      $$questionref{$action} = 0 if !defined $$questionref{$action};
      if ($action eq 'INCORRECT') { 
        $$questionref{'TOTAL'} = $$questionref{'TOTAL'} + 1;
        $$questionref{'WRONG'} = {} if !defined $$questionref{'WRONG'};
        my $answer = join("\n",@items);
        $$questionref{'WRONG'}->{$answer} = 0 
          if !defined $$questionref{'WRONG'}->{$answer};
        $$questionref{'WRONG'}->{$answer} =  
          $$questionref{'WRONG'}->{$answer} + 1;
        }
      elsif ($action eq 'CORRECT') { 
        $$questionref{'TOTAL'} = $$questionref{'TOTAL'} + 1;
        $$questionref{'RIGHT'} = {} if !defined $$questionref{'RIGHT'};
        my $answer = join("\n",@items);
        $$questionref{'RIGHT'}->{$answer} = 0 
          if !defined $$questionref{'RIGHT'}->{$answer};
        $$questionref{'RIGHT'}->{$answer} =  
          $$questionref{'RIGHT'}->{$answer} + 1;
        $$questionref{'TOTALRIGHT'} = $$questionref{'TOTALRIGHT'} + 1;
        }
      #elsif ($action eq 'HINT' or $action eq 'EXPLANATION') { 
      #  $$questionref{$action} = 0 if !defined $$questionref{$action};
      #  $$questionref{$action} =  $$questionref{$action} + 1;
      #  }
      }
    close LOG;
    close PROCLOG;
    unlink $fileName;
    }

  # sort the questions, oh the humanity!!  Schwarztian xform, make it so #1
  my @questions = map { $_->[0] }
                  sort { $a->[1] <=> $b->[1]
                                  ||
                         $a->[2] <=> $b->[2]
                                  ||
                         $a->[3] <=> $b->[3]
                                  ||
                         $a->[4] cmp $b->[4]
                  } map { [$_, /(\d+)\.(\d+)\.(\d+)/, uc($_)] } keys %stats;
 
  # figure out percentages

  my $statsText = '';
  foreach ( @questions ) {
    $statsText .= "<tr><td><A HREF=\"#$_\">$_</A></td><td>";
    if ($stats{$_}->{'TOTAL'}) {
      $stats{$_}->{'PERCENTAGE'} = sprintf "%3d\%", 
        ((100.0 * $stats{$_}->{'TOTALRIGHT'})/ $stats{$_}->{'TOTAL'});
      $statsText .= $stats{$_}->{'PERCENTAGE'};
      $statsText .= "</td><td>";
      $statsText .= " (" . $stats{$_}->{'TOTALRIGHT'} . 
                    "/". $stats{$_}->{'TOTAL'} .")";
      $statsText .= "</td></tr>";
      }
    else {
      $statsText .= "</td><td></td>";
      $stats{$_}->{'PERCENTAGE'} = '    ';
      }
    }

  $statsText .= "</table></UL>\n";
  foreach (@questions) {
    #print "<HR>\n";
    $statsText .= &buildAQuestion($_,  $stats{$_} );
    }
  (tied %stats)->save("$path\\$statsDbName",0640);

  # for backwards compatibility check to see if format is there
  if ($statsQuestiondb->FETCH('statsPresentationFormat')) {
    $globalState{'statsText'} = $statsText;
    print &cleanWS(&runTimeFormat($statsQuestiondb->FETCH('statsPresentationFormat')));
    }
  else {
    print '<H1><A NAME="top">Overall Statistics</A></H1>';
    print '<left><table border=3 cellpadding=5>';
    print $statsText;
    print '</table></left>';
    } 

}

sub buildAQuestion {
  my ($questionId, $stats) = @_;
  my $wrong = $$stats{'WRONG'};
  my $right = $$stats{'RIGHT'};
  my $total = $$stats{'TOTAL'};

  my ($pageId) = split(/\./,$questionId);
  $globalState{'pageNumber'} = $pageId;

  ## figure out the questions on this page
 
  $thisQuestion = new TextDB;
  $thisQuestion->load("$path\\question$questionId");
  $globalState{'questionNumber'} = $questionId;
  $globalState{'questionId'} = $questionId;
  $globalState{'questionText'} = 
                &cleanWS(&runTimeFormat($thisQuestion->FETCH('normalFormat')));

  my $statsText = '';
  if (keys %$right) {
    $statsText .= "<P>Correct Answers are\n";
    $statsText .= "<UL>\n";
    foreach (keys %$right) {
      $statsText .= "<LI> $_ - ";
      $statsText .= sprintf "%3d%s (%d)\n", ((100.0 * $$right{$_})/ $total), '%', $$right{$_};
      }
    $statsText.= "</UL>\n";
    } 
  else { $statsText .= "<P>No correct answers."; }
  if (keys %$wrong) {
    $statsText .= "<P>Incorrect Answers are\n";
    $statsText .= "<UL>\n";
    foreach (keys %$wrong) {
      $statsText .= "<LI> $_ - ";
      $statsText .= sprintf "%3d%s (%d)\n", ((100.0 * $$wrong{$_})/ $total), '%', $$wrong{$_};
      }
    $statsText .= "</UL>\n";
    }
  else { $statsText .= "<P>No incorrect answers."; }
  $globalState{'statsText'} = $statsText;
  return &cleanWS(&runTimeFormat($statsQuestiondb->FETCH('statsQuestionFormat')));

}

#========================================================================
# parse all the RBEGIN - REND tags, and replace if necessary
#========================================================================
sub runTimeFormat {
  my ($format) = @_;
  my ($rbegin, $rend) = ('RBEGIN', 'REND');

  my @tokens = ();
  @tokens = split("$rbegin|$rend", $format);
  my $total = "";
  $globalState{'hiddenText'} = $hidden->toString();

  while (@tokens) {
    $total .= shift @tokens;
    my ($text) = shift @tokens || '';
    $total .= &substitute($text, \%globalState);
    }
  return $total;
  }
 
#========================================================================
#========================================================================
sub substitute {
  my ($text, $sub) = @_;
 
  my $result = '';
  my $thing = '';
  my @locations = ();
  @locations = split(/\$R([A-Za-z]+)/, $text);
 
  while (@locations) {
    $result .= shift @locations;
    return $result unless @locations;
    $thing = shift @locations;
    print "Undefined $thing" unless defined $$sub{$thing};
    return "" unless defined $$sub{$thing};
    $result .= $$sub{$thing};
    }
  return $result;
} 
 
#========================================================================
#========================================================================
sub internalError {
  my ($text) = @_;
  print "An internal error was detected.<P>";
  print "$text";
  exit 0;
}

# is the log file opened?
my $logOpen = 0;

sub writeStats {
  my @elements = @_;

  return unless $hidden->FETCH('collectStatistics');
  my $userid = $hidden->FETCH('userid');
  my $path = $hidden->FETCH('userid');

  &openStats($userid) unless $logOpen;
  my $temp = join('',@elements);
  my $sep = '';
  if ($temp !~ /\|/) {$sep = '|';}
  elsif ($temp !~ /\?/) {$sep = '?';}
  elsif ($temp !~ /\!/) {$sep = '!';}
  elsif ($temp !~ /\,/) {$sep = ',';}
  elsif ($temp !~ /\#/) {$sep = '#';}
  elsif ($temp !~ /\f/) {$sep = "\f";}
  elsif ($temp !~ /\e/) {$sep = "\e";}
  else {
    # throw it out! 
    return;
    }

  $temp = join($sep, @elements);
  $temp =~ s/\n/$sep/;
  print LOG time . "$sep$temp\n";
}
