my $title = "";
my @authors = ();

open(my $fh,"<" , "papers.dat") || die "could not open papers.dat\n";

while (<$fh>) {
 if (/\s*Title:\s+/) {
   $title = $';
   #print "$title\n";
  }
 if (/\s*Authors:\s+/) {
   @authors = split(";",$');
   print '<ul>' . "\n";
   my @foo = ();
   foreach my $a (@authors) {
     my ($last, $first) = split(',',$a);
     my $last = &cleanWS($last);
     my $first = &cleanWS($first);
     my $let = lc substr($last,0,1);
     push @foo, '<a href=":dblp[]/' . $let . '/' . $last 
                . ':colon[]' . $first . '.html">' . "$first $last" . '</a>';
   } 
   print join(",\n", @foo);
   print '<br/>' . "\n";
   print '<i>' . $title .  '</i>' . "\n";
   print '</ul>' . "\n\n";
  }
}

close $fh;

#<ul>
#<a href=":dblp[]/x/XXX:colon[]XXX.html">XXX</a>,
#<a href=":dblp[]/x/XXX:colon[]XXX.html">XXX</a>
#<br/>
#<i>
#</i>
#</ul>

sub cleanWS {
  my ($value) = @_;
  $value = '' if !defined $value;
  #chew up white space at both ends (parsing of fields depends on this!)
  $value =~ s/^[\s\n]*//;
  #$value  =~ s/[\s\n]*$//;
  $value =~ s/(<P>|\s|\n)+$//;
  return $value;
}
