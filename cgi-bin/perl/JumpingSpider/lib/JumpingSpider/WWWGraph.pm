package JumpingSpider::WWWGraph;

#-----------------------------------------------------------------------

=head1 NAME

WWWGraph - A class encapsulating the WWW graph model

=head1 DESCRIPTION

This class encapsulates the WWW graph.  The WWW graph actually
has several `helper' graphs that implement the cost model.

=over 4

=item * DownGraph

- a graph for downward edges

=item * SideGraph

- a graph for side edges

=item * SideTargetsTable

- a table of targets for side edges that come from outside

=item * ReachableTable

- a reachability table

=back

We ignore back edges.

=head1 PUBLIC METHODS

=cut

#-----------------------------------------------------------------------
# We need the persistent graph class 

# Instance variables
# A list of Unknowns
 my @Unknowns = ();
# The graph representing downward edges
 my $DownGraph;
# The graph representing side edges
 my $SideGraph;
# The WWW reachable table
 my $WWWTable;
# The reachable table
 my $ReachableTable;
# The table of outside side edges
 my $SideTargetsTable;
#-----------------------------------------------------------------------

=head2 done()

Finished, save the the graph objects.

=cut

#-----------------------------------------------------------------------
sub done {
  $DownGraph->save();
  $SideGraph->save();
  $WWWTable->save();
  $ReachableTable->save();
  $SideTargetsTable->save();
}
#-----------------------------------------------------------------------

=head2 init()

Cache the graph object references.

=cut

#-----------------------------------------------------------------------
sub init {
  my ($g) = @_;
  $DownGraph = $g->{'downGraph'};
  $SideGraph = $g->{'sideGraph'};
  $WWWTable = $g->{'WWWReachableTable'};
  $ReachableTable = $g->{'reachableTable'};
  $SideTargetsTable = $g->{'sideTargetsTable'};
}
#-----------------------------------------------------------------------

=head2 addEdge(string $fromURL, string $toURL)

Add an edge to the WWW graph from one URL to another URL.
This function determines which subgraph to add it to internally.

=cut

#-----------------------------------------------------------------------
sub addEdge {
  my ($fromName, $toName) = @_;

  #
  # Ignore recursive edges 
  #
  if ($fromName eq $toName) { return; }

  #
  # Figure out the directories
  #
  my ($fromDirectory, $toDirectory) = ($fromName, $toName);
  $fromDirectory =~ s/[^\/]*$//;
  $toDirectory =~ s/[^\/]*$//;

  #print "ALL $toName\n   -> $fromName\n";
  #print "DIR $toDirectory\n    -> $fromDirectory\n";

  #
  # Are these in the same directory?
  #
  if ($fromDirectory eq $toDirectory) {
    if ($toDirectory eq $toName) { 
      # Edge is back to an index.html page
      return; 
      }

    if ($fromDirectory eq $fromName) {
      # Edge is from an index.html page, so it must be downward!
      &_addDownEdge($fromName, $toName);
      return;
      }
        
    if ($fromName =~ /index\.html$/i) {
      # from an index.html page
      &_addDownEdge($fromName, $toName);
      return;
      }

    if ($toName =~ /index\.html$/i) {
      # to an index.html
      &_addSideEdge($fromName, $toName);
      return;
      }

    #
    # Unclear if this is a down or side edge
    #
    &_addUnknownEdge($fromName, $toName);
    return;
    }

  #
  # Is this edge from a parent to a child directory?
  #  At this point we know the directories can't be the same.
  #
  if ($toDirectory =~ /^\Q$fromDirectory\E/) {
    &_addDownEdge($fromName, $toName);
    #
    # Update SideTarget table since this edge comes from outside
    #
    my ($toId) = Id::fromString($toName);
    $SideTargetsTable->insertTuple(new Tuple($toId, new IdSet()));
    return;
    }

  #
  # is it an back edge?
  #
  if ($fromDirectory =~ /\Q$toDirectory\E/) { 
    return; 
    }

  #
  # It is not down or back so it has to be a side edge.
  # In fact it is a side edge from outside!!
  #
  &_addSideEdge($fromName, $toName);
  #
  # Update SideTarget table since this edge comes from outside
  #
  my ($toId) = Id::fromString($toName);
  $SideTargetsTable->insertTuple(new Tuple($toId, new IdSet()));

}

#-----------------------------------------------------------------------
sub handleUnknowns {
  #
  # Cycle through the list of unknowns
  #
  foreach (@Unknowns) {
    my ($from, $to) = @$_;
    #
    # Determine if an edge comes here from outside
    #
    my $t = $SideTargetsTable->retrieveTuple(Id::fromString($from));
    if ($t) { _addDownEdge($from, $to); }
    else { _addSideEdge($from, $to); }
    #if ($t) { print "Down $from $to\n"; }
    #else { print "Side $from $to\n"; }
    }
}

#-----------------------------------------------------------------------
sub _addSideEdge {
  my ($from, $to) = @_;
  $SideGraph->addEdge(Id::fromString($from), Id::fromString($to));
}
#-----------------------------------------------------------------------

#-----------------------------------------------------------------------
sub _addDownEdge {
  my ($from, $to) = @_;
  #
  # To more evenly distribute the reachability table
  # down edges go the other way!!!!
  #$DownGraph->addEdge(Id::fromString($from),Id::fromString($to));
  #
  $DownGraph->addEdge(Id::fromString($to),Id::fromString($from));
}
#-----------------------------------------------------------------------


#-----------------------------------------------------------------------
sub _addUnknownEdge {
  my ($from, $to) = @_;
  #
  # Is there a side edge into this edge?  If so it is a down edge...
  #
  my $t = $SideTargetsTable->retrieveTuple(Id::fromString($from));
  if ($t) {
    _addDownEdge($from, $to);
    }
  
  # 
  # Otherwise, we are not yet sure yet.
  #
  push @Unknowns, [$from, $to];
}
#-----------------------------------------------------------------------

=head2 createReachable()

Create the reachable graph from the side and downward graphs.

=cut

#-----------------------------------------------------------------------
sub createReachable {

  # local variables
  my ($key, $id);

  #
  # assume all the graphs are open
  #

  #
  # Create the Down graph by determining all the reachable nodes
  # downward from a given node
  #
  foreach $key (keys %{$DownGraph->enumerate()}) {
    $id = Id::fromBytes($key);
    $DownGraph->reachableSetWithCaching($id,$ReachableTable);
    }

  #
  # Add all the down edges to the final table
  #
  foreach $key (keys %{$ReachableTable->enumerate()}) {
    $id = Id::fromBytes($key);
    my ($t) = $ReachableTable->retrieveTuple($id);
    $WWWTable->insertTuple($t);
    }

  #
  # Extend the final table with single side edges
  #
    #
    # The following code is commented out since it is used for 
    # paths that actually go `down' followed by one side edge.
    # We have paths that do 'up', and we should preface those paths
    # with the requisite number of side edges.
    #
    # my ($thing);
    #foreach $thing ($idSet->enumerate()) {
    #  my $sideSet = $SideGraph->toNodeSet($thing);
    #  my $sideId;
    #  foreach $sideId ($sideSet->enumerate()) {
    #    $t = $ReachableTable->retrieveTuple($sideId);
    #    if ($t) {
    #      $result->unionSelf($t->getValueAsIdSet());
    #      }
    #    }
    #  }
    #$WWWTable->insertTuple(new Tuple($id, $result));
    #}
  foreach $key (keys %{$SideGraph->enumerate()}) {
    $id = Id::fromBytes($key);
    my $result = $SideGraph->toNodeSet($id);
    my $toSet = new IdSet();
    my $t = $ReachableTable->retrieveTuple($id);
    if ($t) { $toSet = $t->getValueAsIdSet(); }
    my $fromId;
    foreach $fromId ($result->enumerate()) {
      my $fromSet = new IdSet();
      my $r = $WWWTable->retrieveTuple($fromId);
      if ($r) { $fromSet = $r->getValueAsIdSet(); }
      $fromSet->unionSelf($toSet);
      $WWWTable->insertTuple(new Tuple($fromId, $fromSet));
      }
    }
}
#-----------------------------------------------------------------------

1;
