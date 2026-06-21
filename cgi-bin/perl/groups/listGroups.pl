#!/usr/bin/perl -I/home/cdyreson/public_html/cgi-bin/perl/pm
 
#***************************************************************************
# Copyright (c) 1997 Curtis Dyreson. All rights reserved.  This software is 
# covered under the general license and lack of warranty as stated in the
# installation kit. WARNING! DANGER WILL ROBINSON! USE AT YOUR OWN RISK!
#****************************************************************************
 
 #use strict;
 use CGI_Lite;
 my $cgi = new CGI_Lite;

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 my $data = $cgi->parse_form_data();

# common routine to clean junk
 require "cleanWS.pm";

 my $hidden = $$data{'hidden'};
 my $SEPARATOR = ':.:';
 my $CODE = 'CptS 451';

# open the file for reading
 my $file = q{/home/cdyreson/prweb/groups/groups};
 my %records = ();
 my %pending = ();
 my %confirmed = ();
 my %sent = ();
 my $count = 1;

 open (GROUPS, "<$file") || &leave("Could not open $file for reading");
 while (<GROUPS>) {
   my ($action, $an, $ae, $au, $bn, $be, $bu, $gn, $ge, $gu) = split("$SEPARATOR");
   $records{$count} = [$action, $an, $ae, $au, $bn, $be, $bu, $gn, $ge, $gu]; 
   if ($action eq 'PENDING') { 
     $pending{$an} = $count unless defined $confirmed{$an};
     $pending{$bn} = $count unless defined $confirmed{$bn};
     $pending{$gn} = $count unless defined $confirmed{$gn};
     }
   elsif ($action eq 'CONFIRMED') { 
     $confirmed{$an} = $count if defined $pending{$an};
     }
   elsif ($action eq 'SENT') { 
     $sent{$an} = $count unless defined $confirmed{$an};
     $sent{$bn} = $count unless defined $confirmed{$bn};
     $sent{$gn} = $count unless defined $confirmed{$gn};
     }
   $count++;
   }
 close GROUPS;

# Build title info
 print "<html><head><title>$CODE Group List</title></head>";
 my $confirmedGroups = '<body><h2>Confirmed Groups</h2></body>';

 print '<body><h2>Pending Groups</h2></body>';
 
 foreach my $person (keys %pending) {
   my $line = $pending{$person};
   if (defined $records{$line}) {
     my ($action, $an, $ae, $au, $bn, $be, $bu, $gn, $ge, $gu) = @{ $records{$line} };
     my $a = $an;
     my $b = $bn;
     if ($gn) {
       my $g = $gn;
       $a =  "$an (confirmed)" if $confirmed{$an};
       $b =  "$bn (confirmed)" if $confirmed{$bn};
       $g =  "$gn (confirmed)" if $confirmed{$gn};
       if ($confirmed{$an} && $confirmed{$bn} && $confirmed{$gn}) { $confirmedGroups .= "$an, $bn, and $gn<br>\n"; }
       else { print "$a, $b, and $g<br>\n"; } 
       }
     else {
       $a =  "$an (confirmed)" if $confirmed{$an};
       $b =  "$bn (confirmed)" if $confirmed{$bn};
       if ($confirmed{$an} && $confirmed{$bn}) { $confirmedGroups .= "$an and $bn<br>\n"; }
       else { print "$a and $b<br>\n"; } 
       }
     }
   delete $records{$line};
   }

print $confirmedGroups;
print '<hr></body><address>This list is automatically generated.</address></html>';

sub leave {
  my ($problem) = @_;
  print '<title>Error occured in Processing</title><body>Please use the back button to fix the following problem: ' . 
        $problem .
        '</body>';
  exit 0;
  }
