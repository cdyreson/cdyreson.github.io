#! /net/local/bin/perl -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/pm -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/JumpingSpider/lib

# Copyright (c) 1996. Curtis E. Dyreson. All rights reserved.  This 
# software is covered under the general license and lack of 
# warranty as stated in the installation kit. WARNING! DANGER WILL 
# ROBINSON! USE AT YOUR OWN RISK!

use strict;
use CGI_Lite;
use DBMDatabase;
use Persistent::Graph;
use JumpingSpider;
use Template;

my $cgi = new CGI_Lite;

my $ROOT = 'http://www.eecs.edu.au/cgi-bin/cgiwrapd/~cdyreson/cgi-bin/perl/JumpingSpider/starter.pl';

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 my $data = $cgi->parse_form_data();

# common routine to clean junk
 require "cleanWS.pm";

# Make security checks - none really
#require "security.pm";

# figure out which action to apply

 my $i = 0;
 my @keywords = ();
 @keywords = split("->", $$data{"query"});

 &starter(\@keywords);

#========================================================================
# Process the field entries
#========================================================================
sub starter {

  my ($keywords) = @_;
  my $global = new JumpingSpider::Globals();

  my $query = $$data{"query"};
  my $result = &JumpingSpider::Query::query($keywords, 
                 $global->{'indexTable'}, 
                 $global->{'WWWReachableTable'});

  #
  # capture the query results
  #
  my $queryResult = "Results of search for <UL><LI>" . 
                     join ("<LI>", @$keywords) . "</UL>\n";
  my $id;
  my $titleTable = $global->{'titleTable'};
  $queryResult .= "<OL>\n" if (scalar @$result);
  foreach $id (@$result) {
    my $url = $id->toString();
    my $t = $titleTable->retrieveTuple($id);
    my $title = "title unknown?";
    $title = $t->getValueAsString() if ($t);
    $queryResult .= "<li><A HREF=\"$url\">$title</A><BR> (<i>$url</i>)<BR>\n" if ($t);
    }
  $queryResult .= "</OL>\n" if (scalar @$result);
print <<"PAGE";













<html>
<head>
<title>Jumping Spider On-line Demonstration</title>
</head>
<body>
<table border="0" cellPadding="0" cellSpacing="0">
  <tr>
    <td bgColor="#F0E0F0">
      <table border="0" cellSpacing="1">
        <tr>
          <td >
             <img src="js.gif" 
                  alt="mug shot" 
                  width="80" 
                  height="60"
                  border="2">
          </td>
        </tr>
      </table>
    </td>
    <td bgColor="#F0E0F0">
      &nbsp;
    </td>
    <td align=left valign=top bgColor="#F0E0F0">
        <table border="0" cellSpacing="1">
          <tr>
            <td> 
              <big>Jumping Spider On-line Demonstration</big>
              <br>
              <font color="#800000" size="2">
              A web page indexer and search engine
              </font>
            </td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td align=center vAlign="top" bgcolor="#F0E0F0">
      <table border="0" cellspacing="1">
        <tr>
          <td>
            <a href="http://www.eecs.wsu.edu/~cdyreson/pub/JumpingSpider/">Home</a><br>
            <a href="http://www.eecs.wsu.edu/~cdyreson/pub/JumpingSpider/overview.htm">Overview</a><br>
            <a href="http://www.eecs.wsu.edu/~cdyreson/pub/JumpingSpider/publications.htm">Publications</a><br>
            <a href="http://www.eecs.wsu.edu/~cdyreson/pub/JumpingSpider/demo.htm">Demo</a><br>
            <a href="http://www.eecs.wsu.edu/~cdyreson/pub/JumpingSpider/code.htm">Code</a><br><br>
            <a href="http://www.eecs.wsu.edu/~cdyreson">C. Dyreson</a><br>
          </td>
        </tr>
      </table>
    </td>
    <td>
    </td>
    <td vAlign="top">
      <br>
      
  <INPUT TYPE="hidden" NAME="hidden" VALUE="">
  <FORM METHOD="POST" ACTION="$ROOT/starter.pl">
  Enter the next search string in the text box below.<BR>
  <INPUT TYPE="text" NAME="query" VALUE="$query" SIZE=60><BR>
  <INPUT TYPE="submit" NAME="Search" VALUE="Search">
  </FORM>
  $queryResult

      <br>
      <hr noshade>
      Curtis Dyreson &copy; 1997-2000. All rights reserved.
    </td>
  </tr>
</table>
</body>
</html>

PAGE

  }
