#!/usr/bin/perl -I/home/cdyreson/public_html/cgi-bin/perl/pm
 
#***************************************************************************
# Copyright (c) 1997 Curtis Dyreson. All rights reserved.  This software is 
# covered under the general license and lack of warranty as stated in the
# installation kit. WARNING! DANGER WILL ROBINSON! USE AT YOUR OWN RISK!
#****************************************************************************
 
 #use strict;
 use CGI_Lite;
 my $cgi = new CGI_Lite;
# use OLE;

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 my $data = $cgi->parse_form_data();

# common routine to clean junk
 require "cleanWS.pm";

 my $hidden = $$data{'hidden'};
 my $SEPARATOR = ':.:';
 my $CODE = 'CS6890';
 my $BASEURL = 'http://digital.cs.usu.edu/cgi-bin/cgiwrap/~cdyreson/perl/groups';
 my $ERROREMAIL = 'Curtis.Dyreson@usu.edu';

 my $file = q{/home/cdyreson/prweb/groups/groups};
 my %records = ();
 my %pending = ();
 my %confirmed = ();
 my %sent = ();
 my $count = 1;

# process the confirmation (if any)
 if (defined $$data{'group'}) {
   my ($an, $au) = split($SEPARATOR, $$data{'group'});
   open (GROUPS, ">>$file") || &leave("Could not open $file for writing");
   my $empty = 'none';
   print GROUPS "CONFIRMED$SEPARATOR$an$SEPARATOR$au$SEPARATOR$empty$SEPARATOR$empty$SEPARATOR$empty$SEPARATOR$empty$SEPARATOR$empty$SEPARATOR$empty$SEPARATOR$empty\n";
   close GROUPS;
print <<"CMSG";
<html><head><title>$CODE Group Confirmation</title></head>
<body>
<h3> Confirmed </h3>
Thank you $an for confirming.  Please check the 
<a href=$BASEURL/listGroups.pl>official group list</a>
to verify the updated list.
</body>
</html>
CMSG
   delete $$data{'group'};
   exit 0;
   }

# process the previous send (if any)
 if (defined $$data{'send'}) {
   my ($an, $ae, $au, $bn, $be, $bu, $gn, $ge, $gu) = split($SEPARATOR, $$data{'send'});
   my $alphaString = '';
   $alphaString = "ALPHA $an" if $an;
   my $betaString = '';
   $betaString = "BETA $gn" if $gn;
   my $gammaString = '';
   $gammaString = "GAMMA $gn" if $gn;

   my $text = <<"MSG";
<html><head><title>$CODE Group Confirmation</title></head>
<body>
<h3> Hello $an</h3>
This concerns the assignment in $CODE.  Our information 
indicates that you are a group member in a group with 
     $alphaString
     $betaString
     $gammaString
Please activate the following button (only once is necessary) to confirm 
your group membership.
<form METHOD="POST" ACTION="$BASEURL/confirm.pl">
  <input TYPE="submit" NAME="group" VALUE="$an$SEPARATOR$au"> 
</form>
Thank you for your cooperation.
</body>
</html>
MSG

   my $text2 = <<"MSG2";
<html><head><title>$CODE Group Confirmation</title></head>
<body>
<h3> Hello $bn</h3>
This concerns the assignment in $CODE.  Our information 
indicates that you are a group member in a group with 
     $alphaString
     $betaString
     $gammaString
Please activate the following button (only once is necessary) to confirm 
your group membership.
<form METHOD="POST" ACTION="$BASEURL/confirm.pl">
  <input TYPE="submit" NAME="group" VALUE="$bn$SEPARATOR$bu"> 
</form>
Thank you for your cooperation.
</body>
</html>
MSG2

   my $text3 = <<"MSG3";
<html><head><title>$CODE Group Confirmation</title></head>
<body>
<h3> Hello $gn</h3>
This concerns the assignment in $CODE.  Our information 
indicates that you are a group member in a group with 
     $alphaString
     $betaString
     $gammaString
Please activate the following button (only once is necessary) to confirm 
your group membership.
<form METHOD="POST" ACTION="$BASEURL/confirm.pl">
  <input TYPE="submit" NAME="group" VALUE="$gn$SEPARATOR$gu"> 
</form>
Thank you for your cooperation.
</body>
</html>
MSG3

   sendAMailMessage($ae, "$CODE Group Confirmation", $text, 'yes');
   sendAMailMessage($be, "$CODE Group Confirmation", $text2, 'yes') if $be;
   sendAMailMessage($ge, "$CODE Group Confirmation", $text3, 'yes') if $ge;
   open (GROUPS, ">>$file") || &leave("Could not open $file for writing");
   print GROUPS "SENT$SEPARATOR" . $$data{'send'} . "\n";
   close GROUPS;
   delete $$data{'send'};
   }

# open the file for reading
 open (GROUPS, "<$file") || &leave("Could not open $file for reading");
 while (<GROUPS>) {
   my ($action, $an, $ae, $au, $bn, $be, $bu, $gn, $ge, $gu) = split("$SEPARATOR");
   $records{$count} = [$action, $an, $ae, $au, $bn, $be, $bu, $gn, $ge, $gu]; 
   if ($action eq 'PENDING') { 
     $pending{$an} = $count unless defined $confirmed{$an};
     $pending{$bn} = $count unless defined $confirmed{$bn};
     if ($gn) {
       $pending{$gn} = $count unless defined $confirmed{$gn};
       }
     }
   elsif ($action eq 'CONFIRMED') { 
     $confirmed{$an} = $count if defined $pending{$an};
     $confirmed{$bn} = $count if defined $pending{$bn};
     if ($gn) {
       $confirmed{$gn} = $count if defined $pending{$gn};
       }
     }
   elsif ($action eq 'SENT') { 
     $sent{$an} = $count unless defined $confirmed{$an};
     $sent{$bn} = $count unless defined $confirmed{$bn};
     if ($gn) {
       $sent{$gn} = $count unless defined $confirmed{$gn};
       }
     }
   $count++;
   }
 close GROUPS;

# Build title info
 print "<html><head><title>$CODE</title></head>";
  my $confirmedGroups = '<body><h2>Please check the official group list</h2></body>';
  
  print "$confirmedGroups</html>\n";
  exit 0;

sub leave {
  my ($problem) = @_;
  print '<title>Error occured in Processing</title><body>Please use the back button to fix the following problem: ' . 
        $problem .
        '</body>';
  exit 0;
  }

sub sendAMailMessage {
  my ($to, $subject, $text, $attachment) = @_;

# SEE THE OTHER PROGRAM
  }

sub WindowsSendAMailMessage {
  my ($to, $subject, $text, $attachment) = @_;

# SEE THE OTHER PROGRAM
  }

sub mailError {
  my ($msg) = @_;

# SEE THE OTHER PROGRAM
  }
