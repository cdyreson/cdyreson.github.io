package JumpingSpider::TitleTable;

=head1 NAME

TitleTable - A class encapsulating the title table actions

=head1 DESCRIPTION

Output the title of the corresponding page.

=head1 PUBLIC METHODS

=head2 addTitle(string $url, $structure)

=over 4

=item * string $url

- The URL of the page.

=back

Extract the title from the parsed page, and output 
the combination of URL and title.  

Barf if this is an 'Index of' page since we don't want to traverse
the silly file directory structures.  How can we detect these otherwise?

=cut

#-----------------------------------------------------------------------
sub addTitle {
  my ($page, $parse) = @_;

  # extract the title from the page
  my ($title) = $$parse{'title'};
  $title =~ s/\n/ /;
  return 0 if $title =~ /^\s*Index of \//;

  print "TITLE $page $title\n";
  #foreach (@{ $$parse{'links'} }) {
  #  my ($link, $text) = @$_;
  #  print "LINKTEXT $page $link $text\n";
  #  }
  foreach (@{ $$parse{'headers'} }) {
    print "HEADER $page $_\n";
    }
  return 1;
}
#-----------------------------------------------------------------------

1;
