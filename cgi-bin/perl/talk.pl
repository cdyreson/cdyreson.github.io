my $url = 'http://dblp.uni-trier.de/db/indices/a-tree/d/Dyreson@Curtis_E=.html';
#my $url = 'http://www.acm.org/sigmod/db/indices/a-tree/d/Dyreson@Curtis_E=.html';

use LWP::Simple;
my $content = get $url;
die "Couldn't get $url" unless defined $content;

my @lines = split("\n",$content);
foreach my $line (@lines) {
  if ($line =~ /^(<tr>)(<td.*<a name=\"p)([\d]+)(\">.*<\/td>)/) {
    my $front = $`;
    my $one = $1;
    my $two = $2;
    my $three = $3;
    my $four = $4;
    my $last = $';
    my $align = 'align="right" valign="top"';
    my $link = '<a href="' . 
                 $ENTRYURL . 
                 "?$three" .
                 '">add talk</a>';
    print "$front$one<td $align>$link<\/td>$two$three$four$last\n";
    }
  elsif ($line =~ /^(<tr>)(<th colspan=)(\d)/) {
    my $front = $`;
    my $one = $1;
    my $two = $2;
    my $last = $';
    print "$front$one$two" . 4 . "$last\n";
    }
  else {
    print "$line\n";
    }
  }
