#!/usr/bin/perl -I/home/cdyreson/public_html/cgi-bin/perl/upload/lib -I.
 
#***************************************************************************
# Copyright (c) 1997 Curtis Dyreson. All rights reserved.  This software is 
# covered under the general license and lack of warranty as stated in the
# installation kit. WARNING! DANGER WILL ROBINSON! USE AT YOUR OWN RISK!
#****************************************************************************
 
 #use strict;
 use CGI_Lite;
# common routine to clean junk
 require "cleanWS.pm";

 my $cgi = new CGI_Lite;
 #my $Archive = q{/home/cdyreson/prweb/groups};

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 #$cgi->set_directory ($Archive) || die "Directory $Archive doesn't exist.\n";
 #$cgi->set_file_type ("handle");

 my $data = $cgi->parse_form_data();

 my $hidden = $$data{'hidden'};
 my $SEPARATOR = ':.:';
 my $Course = 'CS5800';

# set up the global state
 my $file = q{/home/cdyreson/prweb/groups/groups};
 my $alphaName = $$data{'alphaName'} || &leave("Name for ALPHA is required.");
 my $betaName = $$data{'betaName'} || &leave("Name for BETA is required.");
 my $alphaEmail = $$data{'alphaEmail'} || &leave("E-mail for ALPHA is required.");
 &leave("E-mail for ALPHA must be complete, e.g., joe.doe\@usu.edu.")
   unless $alphaEmail =~ /\@/;
 my $betaEmail = $$data{'betaEmail'} || &leave("E-mail for BETA is required.");
 &leave("E-mail for BETA must be complete, e.g., joe.doe\@usu.edu.")
   unless $betaEmail =~ /\@/;
 my $gammaName = '';
 $gammaName = $$data{'gammaName'}; # || &leave("Name for GAMMA is required.");
 my $gammaEmail = '';
 $gammaEmail = $$data{'gammaEmail'}; # || &leave("E-mail for GAMMA is required.");
 #&leave("E-mail for GAMMA must be complete, e.g., joe.doe\@usu.edu.")
 #  unless $gammaEmail =~ /\@/;

 if ($gammaName) {
   print '<title>OK</title><body>Your information is being processed.  ' .
         'Thank you for your cooperation' . " $alphaName, $betaName, and $gammaName.</body>";
    }
 else {
   print '<title>OK</title><body>Your information is being processed.  ' .
         'Thank you for your cooperation' . " $alphaName and $betaName.</body>";
  }

# open the file for writing
 open (GROUPS, ">>$file") || &leave("Could not open $file for appending");
 print GROUPS "PENDING$SEPARATOR$alphaName$SEPARATOR$alphaEmail$SEPARATOR" . &generateRandom() . $SEPARATOR . 
              "$betaName$SEPARATOR$betaEmail$SEPARATOR" . &generateRandom() . $SEPARATOR . 
              "$gammaName$SEPARATOR$gammaEmail$SEPARATOR" . &generateRandom() . "\n";
 close GROUPS;
 print "<BR>An e-mail message will be sent to ask you to confirm the group.  Please follow the instructions in the message.";

 my ($random) = 0;

 my $text = "\nhttp://digital.cs.usu.edu/cgi-bin/cgiwrap/~cdyreson/perl/groups/myconfirm.pl\n";
 sendAMailMessage('Curtis.Dyreson@usu.edu', " $Course Group Confirmation", $text, 'no');

sub sendAMailMessage {
  my ($to, $subject, $text, $attachment) = @_;
  #print "$ENV{'PATH'}";
  #open(LS, "ls /net/local/bin | ") || die "could not open ls for reading";
  #while (<LS>) {
  # print "<br>ls: $_\n";
  # }
  #close LS;
  
  my $pid = time;
  if ($attachment eq 'yes') {
    open(F1, "> /tmp/groups$pid.htm") || die "could not open tmp groups for write";
    print F1 $text;
    close F1;
    open(MAIL, "| /usr/bin/mutt -s \"$subject\" -a /tmp/groups$pid.htm $to") || die "could not open mail for writing";
    print MAIL "Please use the attached page to confirm your group membership.\nSave the page and view it in your web browser.\n";
    close MAIL;
    unlink "/tmp/groups$pid.htm";
    }
  else {
    open(MAIL, "| /usr/bin/mutt -s \"$subject\" $to") || die "could not open mail for writing";
    print MAIL $text;
    close MAIL;
    }
  }

sub generateRandom {
  # Use pid version of password, token that will identify user for stats 
  my ($time) = time;
  $random++;
  return &cleanWS("$$.$time$random");

  # generate a random seed
  #my $seed = time;
  #srand($seed);
}

sub leave {
  my ($problem) = @_;
  print '<title>Error occured in Processing</title><body>Please use the back button to fix the following problem: ' . 
        $problem .
        '</body>';
  exit 0;
  }

#========================================================================
# Hey, we found an error!!@!!!
#========================================================================
sub internalError {
  my ($text) = @_;
  print "An internal error was detected.<P>";
  print "$text";
  exit 0;
}
