#!/usr/bin/perl -I/home/cdyreson/perl/DBI-1.14

use strict;
use DBI;

print "Content-type: text/html", "\r\n\r\n";
print <<'ACMCENTRAL';
<html>
<title>Submit a paper to TODS redirection</title>
<body>
To electronically submit a paper for review
by ACM <i>Transactions on Database Systems</i> please go to 
<a href="http://acm.manuscriptcentral.com">acm.manuscriptcentral.com</a>.
ACMCENTRAL

#my @driver_names = DBI->available_drivers;
#print "driver names\n" . join("\n", @driver_names);

print <<'ENDACM';
</body>
</html>
ENDACM

#foreach my $driver_name(@driver_names) {
#  my @data_sources = DBI->data_sources($driver_name);
#  print "data sources\n" . join("\n", @data_sources);
#  }

#my $dbh = DBI->connect( 'dbi:Oracle:orcl',
#                        'scott',
#                        'tiger',
#                      );
