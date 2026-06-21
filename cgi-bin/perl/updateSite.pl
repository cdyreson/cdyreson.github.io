#!/net/local/bin/perl -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/pm
 
 #use strict;
 use CGI_Lite;
 my $cgi = new CGI_Lite;

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 my $data = $cgi->parse_form_data();

# common routine to clean junk
 require "cleanWS.pm";

 my $hidden = $$data{'hidden'};
 my $SCRIPTURL = 'http://www.eecs.wsu.edu/cgi-bin/cgiwrap/~cdyreson/cgi-bin/perl/update.pl';

 my $MESSAGE = "";
 # Update the temporary site or the site?
 my $update = 'temp';
 if (defined $$data{'site'}) {
   system ("cd /net/zeus/facstaff/cdyreson/public_html/new/; make site");
   $MESSAGE = '<h3>www.eecs.wsu.edu has been updated, please check changes</h3>';
   } 
 elsif (defined $$data{'temp'}) {
   system ("cd /net/zeus/facstaff/cdyreson/public_html/new/; make temp");
   $MESSAGE = '<h3>temp site has been updated, please check changes</h3>';
   } 
print <<"PAGE";
<html>
<title>EECS site update page</title>
<body>
$MESSAGE
<form METHOD="POST" ACTION="http://www.eecs.wsu.edu/cgi-bin/cgiwrap/~cdyreson/cgi-bin/perl/updateSite.pl">
<input NAME="temp" TYPE="submit" VALUE="Update Test site"/> <br/>
<br>
<input NAME="site" TYPE="submit" VALUE="Update Live site"/> ***Will update www.eecs.wsu.edu!***
<br/>
</form>
<br>
<a href="http://www.eecs.wsu.edu/~cdyreson/publish">Test site home page</a>
<br>
<a href="http://www.eecs.wsu.edu/~cdyreson/publish">Live site home page</a>
<br>
</body>
</html>
PAGE

# sendAMailMessage('cdyreson@eecs.wsu.edu', " $Course Group Confirmation", $text, 'no');

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
