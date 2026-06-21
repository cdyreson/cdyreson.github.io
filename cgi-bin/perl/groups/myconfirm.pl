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
 my $CODE = 'CS6890';
 my $BASEURL = '"http://digital.cs.usu.edu/cgi-bin/cgiwrap/~cdyreson/perl/groups';
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
   $alphaString = "<LI> ALPHA $an" if $an;
   my $betaString = '';
   $betaString = "<LI> BETA $bn" if $bn;
   my $gammaString = '';
   $gammaString = "<LI> GAMMA $gn" if $gn;

   my $text = <<"MSG";
<html><head><title>$CODE Group Confirmation</title></head>
<body>
Hello $an,
<br>
(If this page does not open correctly in your mailer, please
save the attachement as a .htm file and open it in your
brwoser to confirm the group.)

Thanks for signing up for a group for the assignment in $CODE.  
It looks like you are in the following group.
<ul> 
     $alphaString
     $betaString
     $gammaString
</ul>
If you agree, then please simply activate the following button 
(only once is necessary) to confirm your group membership.
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
Hello $bn,
<br>
(If this page does not open correctly in your mailer, please
save the attachement as a .htm file and open it in your
brwoser to confirm the group.)

Thanks for signing up for a group for the assignment in $CODE.  
It looks like you are in the following group.
<ul>
     $alphaString
     $betaString
     $gammaString
</ul>
If you agree, then please simply activate the following button 
(only once is necessary) to confirm your group membership.
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
Hello $gn,
<br>
(If this page does not open correctly in your mailer, please
save the attachement as a .htm file and open it in your
brwoser to confirm the group.)

Thanks for signing up for a group for the assignment in $CODE.  
It looks like you are in the following group.
<ul>
     $alphaString
     $betaString
     $gammaString
</ul>
If you agree, then please simply activate the following button 
(only once is necessary) to confirm your group membership.
<form METHOD="POST" ACTION="$BASEURL/groups/confirm.pl">
  <input TYPE="submit" NAME="group" VALUE="$gn$SEPARATOR$gu"> 
</form>
Thank you for your cooperation.
</body>
</html>
MSG3

   sendAMailMessage($ae, $CODE . ' Group Confirmation', $text, 'yes');
   sendAMailMessage($be, $CODE . ' Group Confirmation', $text2, 'yes') if $be;
   sendAMailMessage($ge, $CODE . ' Group Confirmation', $text3, 'yes') if $ge;
   open (GROUPS, ">>$file") || &leave("Could not open $file for writing");
   print GROUPS "SENT$SEPARATOR" . $$data{'send'} . "\n";
   close GROUPS;
   delete $$data{'send'};
   }

# open the file for reading
 open (GROUPS, "<$file") || &leave("Could not open $file for reading");
 while (<GROUPS>) {
   my ($action, $an, $ae, $au, $bn, $be, $bu, $gn, $ge, $gu) = split("$SEPARATOR");
   #print "$action $an, $ae, $bn, $be, $gn, $ge<br>\n";
   $records{$count} = [$action, $an, $ae, $au, $bn, $be, $bu, $gn, $ge, $gu]; 
   if ($action eq 'PENDING') { 
     #print "pending $an, $bn, $gn<br>\n";
     $pending{$an} = $count unless defined $confirmed{$an};
     $pending{$bn} = $count unless defined $confirmed{$bn};
     if ($gn) {
       $pending{$gn} = $count unless defined $confirmed{$gn};
       }
     }
   elsif ($action eq 'CONFIRMED') { 
     #print "confirmed $an, $bn, $gn<br>\n";
     $confirmed{$an} = $count if defined $pending{$an};
     $confirmed{$bn} = $count if defined $pending{$bn};
     if ($gn) {
       $confirmed{$gn} = $count if defined $pending{$gn};
       }
     }
   elsif ($action eq 'SENT') { 
     #print "sent $an, $bn, $gn<br>\n";
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
 print "<html><head><title>$CODE Group List</title></head>";
  my $confirmedGroups = '<body><h2>Confirmed Groups</h2></body>';

 print '<body><h2>Pending Groups</h2></body>';
 
 foreach my $person (keys %pending) {
   my $line = $pending{$person};
   if (defined $records{$line}) {
     my ($action, $an, $ae, $au, $bn, $be, $bu, $gn, $ge, $gu) = @{ $records{$line} };
     my $a = $an;
     $a = "$an (confirmed)" if $confirmed{$an};
     my $b = $bn;
     $b = "$bn (confirmed)" if $confirmed{$bn};
     if ($gn) {
       my $g = $gn;
       $g = "$gn (confirmed)" if $confirmed{$gn};
       if (defined($confirmed{$an}) and defined($confirmed{$bn}) and defined($confirmed{$gn})) {
         $confirmedGroups .= "$an, $bn, and $gn<br>\n";
         }
       else { 
         print "$a, $b, and $g<br>\n";
         if (!(defined($sent{$an}) or defined($sent{$bn}) or defined($sent{$gn}))) {
           print <<"FORM1";
 <form METHOD="POST" ACTION="$BASEURL/groups/myconfirm.pl">
  <input TYPE="submit" NAME="send" VALUE="$an$SEPARATOR$ae$SEPARATOR$au$SEPARATOR$bn$SEPARATOR$be$SEPARATOR$bu$SEPARATOR$gn$SEPARATOR$ge$SEPARATOR$gu"> 
 </form>
FORM1
           }
         }
       }
     else { 
       if (defined($confirmed{$an}) and defined($confirmed{$bn})) {
         $confirmedGroups .= "$an and $bn<br>\n";
         }
       else { 
         print "$a and $b<br>\n";
         if (!($sent{$an} || $sent{$bn} || $sent{$gn})) {
           print <<"FORM2";
 <form METHOD="POST" ACTION="$BASEURL/myconfirm.pl">
  <input TYPE="submit" NAME="send" VALUE="$an$SEPARATOR$ae$SEPARATOR$au$SEPARATOR$bn$SEPARATOR$be$SEPARATOR$bu$SEPARATOR$empty$SEPARATOR$empty$SEPARATOR$empty"> 
 </form>
FORM2
           }
         }
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

sub sendAMailMessage {
  my ($to, $subject, $text, $attachment) = @_;
  #print "$ENV{'PATH'}";
  #open(LS, "ls /net/local/bin | ") || die "could not open ls for reading";
  #while (<LS>) {
  # print "<br>ls: $_\n";
  # }
  #close LS;
  
  my $pid = time;
  open(F1, "> /tmp/groupst$pid.htm") || die "could not open tmp groups for write";
  print F1 "Please use the attached page to confirm your group membership.\nSave the page and view it in your web browser if needed.\n";
  close F1;
  if ($attachment eq 'yes') {
    open(F1, "> /tmp/groups$pid.htm") || die "could not open tmp groups for write";
    print F1 $text;
    close F1;
    #open(MAIL, "| /usr/bin/metasend -b -s \"$subject\" -m text/plain -e \"none\" -f /tmp/groupst$pid.htm -n -m text/html -e \"base64\" -f /tmp/groups$pid.htm -t $to") || die "could not open mail for writing";
    open(MAIL, "| /usr/bin/mutt -s \"$subject\" -a /tmp/groups$pid.htm $to") || die "could not open mail for writing";
    close MAIL;
    unlink "/tmp/groupst$pid.htm";
    unlink "/tmp/groups$pid.htm";
    }
  else {
    open(MAIL, "| /usr/bin/mutt -s \"$subject\" $to") || die "could not open mail for writing";
    print MAIL $text;
    close MAIL;
    }
  }

sub WindowsSendAMailMessage {
  my ($to, $subject, $text, $attachment) = @_;
  my $path = q{C:\Inetpub\wwwroot\CDyreson\prweb\125\groups};
  my $file = &cleanWS($to);
  $file =~ /\@/;
  $file = "$path\\$`.html";
  #$file = q{C:\Inetpub\wwwroot\CDyreson\prweb\125\groups\cdyreson};
  if ($attachment eq 'yes') {
    open(MAIL, ">$file") || die "could not open $file for writing";
    print MAIL $text;
    close MAIL;
    }
  my $DevMailer = CreateObject OLE 'Geocel.Mailer';
  $DevMailer->AddServer ('mail.bond.edu.au', 25);
  $DevMailer->AddRecipient ($to," ");
  $DevMailer->{FromName}  = "$CODE Group Registration";
  $DevMailer->{FromAddress}  = "cdyreson\@bond.edu.au";
  $DevMailer->{Subject} = $subject;
  if ($attachment eq 'yes') {
    $DevMailer->{Body} = "Please use the attached page to confirm your group membership.\n";
    $DevMailer->AddAttachment("$file");
    } 
  else {
    $DevMailer->{Body} = $text;
    }
  $DevMailer->{Body} = $DevMailer->{Body} . "\r\n";
  my $success = $DevMailer->Send();
  &mailError() unless $success;
  }

sub mailError {
  ($msg) = @_;

  printf('<H1><CENTER>\n');
  printf(' Server Error</H1><HR>\n');
  printf('<BR><H2>$msg</H2>\n');
  printf('<BR><BR>We apologise that due to an error on the WWW ');
  printf('server, your feedback has not been submitted.');
  printf('<BR><BR>\n');
  printf('<h2>Please report this error by clicking ');
  printf('<A HREF=\'mailto:$ERROREMAIL\'> here</A>\n');
  exit;
  }
