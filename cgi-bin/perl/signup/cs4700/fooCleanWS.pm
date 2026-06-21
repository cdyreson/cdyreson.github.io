#!/usr/local/bin/perl -I/world/httpd/cgi-bin/perl/pm 

# cleans the white space from around a string
# Note we are consider <P> as whitespace!
sub cleanWS {
  my ($value) = @_;
  $value = '' if !defined $value;
  #chew up white space at both ends (parsing of fields depends on this!)
  $value =~ s/^[\s\n]*//;
  #$value  =~ s/[\s\n]*$//;
  $value =~ s/(<P>|\s|\n)+$//;
  return $value;
}

1;
