#! /usr/bin/perl -I/home/cdyreson/public_html/cgi-bin/perl/pm -I/home/cdyreson/public_html/cgi-bin/perl/JumpingSpider/lib

# Copyright (c) 1996. Curtis E. Dyreson. All rights reserved.  This 
# software is covered under the general license and lack of 
# warranty as stated in the installation kit. WARNING! DANGER WILL 
# ROBINSON! USE AT YOUR OWN RISK!

use strict;
use CGI_Lite;
use DBMDatabase;
use Persistent::Graph;
use JumpingSpider;

my $cgi = new CGI_Lite;

my $ROOT = 'http://www.cs.usu.edu/cgi-bin/cgiwrap/~cdyreson/cgi-bin/perl/JumpingSpider';

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
  $queryResult = '' unless (scalar @$result);
print <<"PAGE";





























<html>
<head>
<title>Jumping Spider On-line Demonstration</title>
</head>
<body>
<table summary="table" border="0" cellPadding="0" cellSpacing="0">
  <tr>
    <td bgColor="#F0E0F0" align=left valign=top>
      <table summary="table" border="0" cellSpacing="1">
        <tr>
          <td >
             <img src="https://cdyreson.github.io/JumpingSpider/js.gif"
                  alt="jumping spider logo" 
                  width="90" 
                  height="68"
                  border="2">
          </td>
        </tr>
      </table>
    </td>
    <td bgColor="#F0E0F0">
      &nbsp;
    </td>
    <td align=left valign=top bgColor="#F0E0F0">
        <table summary="table" border="0" cellSpacing="1">
          <tr>
            <td bgcolor="#F0E0F0"> 
              <big>Jumping Spider On-line Demonstration</big>
              <br>
              <font color="#800000" size="2">
              An intranet index and search engine
              </font>
            </td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td align=center vAlign="top" bgcolor="#F0E0F0">
      <table summary="table" border="0" cellpadding="2" cellspacing="2">
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/JumpingSpider/index.htm">
              Home&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/JumpingSpider/overview.htm">
              Overview&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/JumpingSpider/publications.htm">
              Publications
             </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/JumpingSpider/demo.htm">
              Demo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/JumpingSpider/code.htm">
              Code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#F0E0F0">&nbsp;
        </td></tr>
        <tr><td colspan=2 bgcolor="#F0E0F0">
            Curtis Dyreson
        </td></tr>
        <tr><td bgcolor="#F0E0F0">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://digital.cs.usu.edu/~cdyreson/">
              Home
              </a>
        </td></tr>
        <tr><td bgcolor="#F0E0F0">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/publications.htm">
              Publications
            </a>
        </td></tr>
        <tr><td bgcolor="#F0E0F0">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/projects.htm">
              Projects
             </a>
        </td></tr>
        <tr><td bgcolor="#F0E0F0">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/code.htm">Software</a>
        </td></tr>
        <tr><td bgcolor="#F0E0F0">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/demos.htm">Demos</a>
        </td></tr>
        <tr><td bgcolor="#F0E0F0">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/teaching.htm">Teaching</a>
        </td></tr>
        <tr><td bgcolor="#F0E0F0">&nbsp;</td><td bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="https://cdyreson.github.io/contact.htm">Contact me</a>
        </td></tr>
      </table>
    </td>
    <td>
    </td>
    <td vAlign="top">
      <br>
      
  <INPUT TYPE="hidden" NAME="hidden" VALUE="">
  <FORM METHOD="POST" ACTION="$ROOT/starter.pl">
  Enter the search string in the text box below.<BR>
  <INPUT TYPE="text" NAME="query" VALUE="$query" SIZE=60><BR>
  <INPUT TYPE="submit" NAME="Search" VALUE="Search">
  </FORM>
  $queryResult

      <br>
     &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      <hr noshade size="1">
     <a style="text-decoration:none" 
               href="http://digital.cs.usu.edu/~cdyreson">Curtis E. Dyreson</a>
     &copy; 1997-2001. All rights reserved.
    </td>
  </tr>
  <tr>
    <td height="20" bgColor="#F0E0F0">&nbsp;</td>
    <td colspan=2 bgColor="#F0E0F0" align="right">
      <small><i>E-mail questions or comments to Curtis.Dyreson at usu.edu</i></small>
    </td>
  </tr>
</table>
</body>
</html>

PAGE

  }
