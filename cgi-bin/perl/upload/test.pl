#!/net/local/bin/perl -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/pm
# /usr/local/bin/perl -Tw -I.
 
#***************************************************************************
# Copyright (c) 2001 Curtis Dyreson. All rights reserved.  This software is 
# covered under the general license and lack of warranty as stated in the
# installation kit. WARNING! DANGER WILL ROBINSON! USE AT YOUR OWN RISK!
#****************************************************************************
 
 use strict;
 use CGI_Lite;

# common routine to clean junk
 require "cleanWS.pm";

MAIN: 
{
 my $cgi = new CGI_Lite ();
# Build the header
 print "Content-type: text/html", "\r\n\r\n";

 #$cgi->set_directory ("/usr/shishir") || die "Directory doesn't exist.\n";
 $cgi->set_directory ("/net/zeus/facstaff/cdyreson/tmp") || die "Directory doesn't exist.\n";

 $cgi->set_file_type ("handle");
 my %data = $cgi->parse_form_data ();

 my @required = ('author', 'name1', 'email1', 'address', 'title', 'abstract', 'file');
 foreach my $field (@required) {
   next if defined $data{$field} && $data{$field};
   print "Died because $field is required\n";
   exit;
   }

 my $user = $data{'note'};
 my $filename = $data{'upfile'};
 print "Welcome $user, let's see what file you uploaded...", "\n";
 print "=" x 80, "\n";

#  if (-T $filename) {
  open(FIN, "/net/zeus/facstaff/cdyreson/tmp/$filename") || die "could not open $filename";
      while (<FIN>) {
          print;
      } 
      close FIN;
#  } else {
#    print "Sorry! you did not upload a text file.", "\n";
#  }
}
