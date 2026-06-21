#!/net/local/bin/perl -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/pm -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/JumpingSpider/lib
###/usr/local/bin/perl -I../../pm
 
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

  print "<HTML>";
  print  <<"HEAD";
  <HEAD>
  <TITLE>
  Jumping Spider Demonstration - Washington State University
  </TITLE>
  </HEAD>
  <BODY>
  <INPUT TYPE="hidden" NAME="hidden" VALUE="">
  <FORM METHOD="POST" ACTION="$ROOT/starter">
  Enter the next search string in the text box below.<BR>
HEAD

  print "<INPUT TYPE=\"text\" NAME=\"query\" VALUE=\"";
    print $$data{"query"};
    print "\" SIZE=60><BR>\n";

  print  <<'FORM';
  <INPUT TYPE="submit" NAME="Search" VALUE="Search">
  </FORM>
FORM

  my $result = &JumpingSpider::Query::query($keywords, 
                 $global->{'indexTable'}, 
                 $global->{'WWWReachableTable'});

  #
  # print out the graphic user interface
  #

  print "Results of search for <UL><LI>" . 
        join ("<LI>", @$keywords) . "</UL>\n";
  my $id;
  my $titleTable = $global->{'titleTable'};
  print "<OL>\n" if (scalar @$result);
  foreach $id (@$result) {
    my $url = $id->toString();
    my $t = $titleTable->retrieveTuple($id);
    my $title = "title unknown?";
    $title = $t->getValueAsString() if ($t);
    print "<li><A HREF=\"$url\">$title</A><BR> (<i>$url</i>)<BR>\n" if ($t);
    }
  print "</OL>\n" if (scalar @$result);

  print  <<'REST';
  <hr size=3 noshade>
  [<a href="http://www.cs.jcu.edu.au/~curtis/htmls/MiniWorldWeb/publications.html">Papers</a>]
  [<a href="http://www.cs.jcu.edu.au/~curtis/htmls/MiniWorldWeb/demo.html">Interactive demo</a>]
  [<a href="http://www.cs.jcu.edu.au/~curtis/htmls/MiniWorldWeb/code.html">Implementation</a>]
  [<A HREF="mailto:curtis@cs.jcu.edu.au">join mailing list</A>]
  <P>
  Curtis Dyreson &copy; 1997. All rights reserved. 
  </BODY>
  </HTML>
REST

}
