package JumpingSpider::Query;

=head1 NAME Query

This class is for the Jumping Spider.

For more information on the Jumping Spider see the L<Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<Licence> and L<Version>.

=head1 DESCRIPTION

The query supports only one kind of query currently.

=head1 METHODS

=head2 query(@contentDecriptions, indexTable, reachableTable)

=over 4

=item * @contentDescriptions

- A list of content descriptions

=item * indexTable

- The index table

=item * reachableTable

- The reachable table

=back

Query takes a list of content descriptions and returns a list of URLs.

=cut

#---------------------------------------------------------------------
sub query {
  my ($contentDescriptions, $indexTable, $reachableTable) = @_;
  my @keywords = @$contentDescriptions;
  my @descriptions = ();

  #
  # Retrieve the nodes that match a particular keyword
  #
  #print $indexTable->KeysAsStringIds();

  my $set;
  foreach my $keyword (@keywords) {
    $keyword =~ s/^\s+//;
    $keyword =~ s/\s+$//;
    my @words = split(/\s+/, $keyword);
    $set = new IdSetWithCount();
    my $word = shift @words;
    $word =~ tr/A-Z/a-z/;
    my $t = $indexTable->retrieveTuple(new StringCol($word));
    $set = $t->getValueAsIdSetWithCount() if $t;
    foreach my $subword (@words) {
      $subword =~ tr/A-Z/a-z/;
      my $t = $indexTable->retrieveTuple(new StringCol($subword));
      $set->intersectSelf($t->getValueAsIdSetWithCount()) if $t;
      }
    unshift @descriptions, $set;
    }

# Old code to test
my $jjj;
foreach $jjj ($set->enumerateInSortedOrder()) {
  my ($id, $count) = @$jjj;
#  print "eeee" . $id->toString() . " $count\n";
  }

  #
  # Solve for each keyword
  #
  my $firstSet = shift @descriptions;
  my $firstList;
  my $previous;
  
  my $result = new IdSetWithCount();
  my @enumeration = ();
  @enumeration = $firstSet->enumerateInSortedOrder() if defined $firstSet;
  foreach my $firstList (@enumeration) {
    my ($first, $score) = @$firstList;
    my $keep = 1;
    my $i;
    my $previousSet = new IdSet();
    $previousSet->insert($first);
    my $intermediate = new IdSet();
    for ($i=1; $i < scalar @descriptions; $i++) {
      $intermediate = new IdSet();
      my $matchedSet = $descriptions[$i];
      foreach my $previous ($previousSet->enumerate()) {
        my $t = $reachableTable->retrieveTuple($previous);
        #print "$t " . $first->image . " " . $previous->image . " here\n";
        if ($t) {
          my $reachableSet = $t->getValueAsIdSet() || new IdSet();
          next unless $reachableSet->numberOfElements;
          $intermediate->unionSelf($reachableSet->intersect($matchedSet));
          }
        }
      if ($intermediate->numberOfElements == 0) { 
        $keep = 0;
        $i = scalar @descriptions; 
        }
      else {
        $previousSet = new IdSet();
        foreach my $idpair ($intermediate->enumerate()) {
          $previousSet->insert(@$idpair[0]);
          }
        }
      }
    #print "$keep, $score\n";
    if ($keep) { $result->insertWithCount($first,$score); }
    }
  
  #
  # What are the results?
  #
  my $test;
  my @results = ();
  foreach $test ($result->enumerateInSortedOrder()) {
    my ($id, $count) = @$test;
    #push @result, $id->toString() . " $count";
    push @results, $id;
    }

  return \@results;

}
#---------------------------------------------------------------------

1;
