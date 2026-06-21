package Persistent::Graph;

=head1 NAME Persistent::Graph 

Class encapsulating a persistent graph.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<Licence> and L<Version>.

=head1 DESCRIPTION

A persistent graph is a L<DBMDatabase::Table> mapping from nodes to 
to nodes.

=head1 METHODS

=head2 new($db, $name)

=over 4

=item * $db

- A L<DBMDatabase::Database>, the database where the graph will live

-item * $name

- A string, the name of the graph

=back

Create a new persistent graph.

=cut

#---------------------------------------------------------------------

  sub new {
    my($type, $db, $name) = @_;
    my $self = {};
    $self->{'class'} = $type;
    $self->{'edges'} = $db->createTable($name);
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 IdIdListImage()

Return an Id to IdList image of the graph.

=cut

#---------------------------------------------------------------------
  sub IdIdListImage {
    my ($self) = @_;
    my ($edges) = $self->{'edges'};
    return $edges->IdIdListImage();
    }
#---------------------------------------------------------------------


=head2 IdKeysImage()

Return an Id to IdList image of the graph.

=cut

#---------------------------------------------------------------------
  sub IdKeysImage {
    my ($self) = @_;
    my ($edges) = $self->{'edges'};
    return $edges->IdKeysImage();
    }
#---------------------------------------------------------------------

=head2 addEdge($from, $to)

=over 4

=item * $from

- An L<DBMDatabase::Id>, the from node.

=item * $to

- An L<DBMDatabase::Id>, the to node.

=back

Add an edge. 

=cut

#---------------------------------------------------------------------
  sub addEdge {
    my ($self, $from, $to) = @_;
    my ($edges) = $self->{'edges'};
    my ($s);
    my ($t) = $edges->retrieveTuple($from);
    if ($t) { $s = $t->getValueAsIdSet(); }
    else { $s = new IdSet(); }
    $s->insert($to);
    $edges->insertTuple(new Tuple($from, $s));
    }
#---------------------------------------------------------------------

=head2 toNodeSet($from)
 
=over 4
 
=item * $from
 
- An L<DBMDatabase::Id>, the node to start from
 
=back
 
Get the set of nodes that are the 'to' set for this edge.
Returns an L<DBMDatabase::IdSet>, the set of nodes.
 
=cut
 
#---------------------------------------------------------------------
  sub toNodeSet {
    my ($self, $from) = @_;
    my ($edges) = $self->{'edges'};
    my ($t) = $edges->retrieveTuple($from);
    if ($t) { return $t->getValueAsIdSet(); }
    else { return new IdSet(); }
    }
#---------------------------------------------------------------------

=head2 reachableSetWithCaching(from, cache)

=over 4

=item * from

- An L<DBMDatabase::Id>, the node to start from

=item * cache

- A hash table reference, the cache of which nodes are reachable from 
which

=back

Get the set of nodes that are reachable from this node.
Allow cycles, and cache intermediate results to build a complete
table of reachable nodes.
Returns an L<DBMDatabase::IdSet>, the set of reachable nodes.

=cut

#---------------------------------------------------------------------
  sub reachableSetWithCaching {
    my ($self, $from, $cache) = @_;
    # the result set
    my ($result) = new IdSet();
    # can reach myself
    $result->insert($from);
    # get the nodes reachable from this node
    my ($edges) = $self->{'edges'};
    my ($t) = $edges->retrieveTuple($from);
    if ($t) {
      # for each reachable node
      my $toSet = $t->getValueAsIdSet();
      my ($id);
      foreach $id ($toSet->enumerate()) {
        # is it a member already of the result?
        if (!$result->memberOf($id)) {
          my ($cached) = $cache->retrieveTuple($id);
          if ($cached) {
            $result->unionSelf($cached->getValueAsIdSet());
            }
          else {
            # determine reachability for the node
            $self->_reachableSetInner($result,$id);
            }
          }
        }
      }
    $cache->insertTuple(new Tuple($from, $result));
    #return $result;
    }
#---------------------------------------------------------------------

=head2 reachableSet($from)
 
=over 4
 
=item * $from
 
- An L<DBMDatabase::Id>, the node to start from
 
=back
 
Get the set of nodes that are reachable from this node.
And allow cycles!
Returns an L<DBMDatabase::IdSet>, the set of reachable nodes.
 
=cut
 
#---------------------------------------------------------------------
  sub reachableSet {
    my ($self, $from) = @_;
    #// the result set
    my ($result) = new IdSet();
    #// put the current node into the result
    $result->insert($from);
    #// get the nodes reachable from this node
    my ($edges) = $self->{'edges'};
    my ($t) = $edges->retrieveTuple($from);
    if ($t) {
      #// for each reachable node
      my $toSet = $t->getValueAsIdSet();
      my ($id);
      foreach $id ($toSet->enumerate()) {
        #// is it a member already of the result?
        if (!$result->memberOf($id)) {
          #// follow the node
          $self->_reachableSetInner($result,$id);
          }
        }
      }
    return $result;
    }
#---------------------------------------------------------------------

=head2 reachableSetStopAtMeasure($from, $stopMeasure, $unitToMeasureTable)

=over 4

=item * $from 

- A L<DBMDatabase::Id>, the unit to begin searching from.

=item * $stopMeasure 

- A L<DBMDatabase::Id>, the measure at which to stop.

=item * $unitToMeasureTable 

- A L<DBMDatabase::Table>, the table that associates units with their measures.

=back

Get the set of nodes at the indicated measure 
that are reachable from this initial node.
Assume for now that the graph is a strict hierarchy,
in future we will relax this assumption.


=cut

#---------------------------------------------------------------------
  sub reachableSetStopAtMeasure {
    my ($self, $from, $stopMeasure, $unitToMeasureTable) = @_;

    #// the result set
    my ($result) = new IdSet();

    #// have we reached the stop measure?
    my ($t) = $unitToMeasureTable->retrieveTuple($from);
    die "No measure for this unit!" .  $from->toString() unless $t;
    my ($testMeasure) = $t->getValueAsId();
    if ($stopMeasure->equals($testMeasure)) {
      #// put the current node into the result
      $result->insert($from);
      return $result;
      }

    #// get the nodes reachable from this node
    my ($edges) = $self->{'edges'};
    $t = $edges->retrieveTuple($from);
    if ($t) {
      #// for each reachable node
      my ($toSet) = $t->getValueAsIdSet();
      my ($id);
      foreach $id ($toSet->enumerate()) {
        #// follow the edges from the node
        $result->unionSelf($self->reachableSetStopAtMeasure($id,$stopMeasure,$unitToMeasureTable));
        }
      }
    return $result;
    }
#---------------------------------------------------------------------

=head2 enumerate()

Return a reference to the dbm file.

=cut

#---------------------------------------------------------------------
  sub enumerate {
    my ($self) = @_;
    my ($edges) = $self->{'edges'};
    return $edges->enumerate();
    }
#---------------------------------------------------------------------

=head2 save()

Save any changes to the persistent graph to disk.  

=cut

#---------------------------------------------------------------------
  sub save {
    my ($self) = @_;
    my ($edges) = $self->{'edges'};
    $edges->save();
    }
#---------------------------------------------------------------------

=head1 PRIVATE METHODS

=head2 _reachableSetInner($result, $from)

=over 4

=item * $result

- An L<DBMDatabase::IdSet>, the set of reachable nodes.

=item * $from

- An L<DBMDatabase::Id>, the node to start from

=back

Get the set of nodes that are reachable from this node.
And allow cycles!
Build the resulting set in the $result variable.
A helper function for reachableSet.

=cut

#---------------------------------------------------------------------
  sub _reachableSetInner {
    my ($self, $result, $from) = @_;
    $result->insert($from);
    #// get the nodes reachable from this node
    my ($edges) = $self->{'edges'};
    my ($t) = $edges->retrieveTuple($from);
    if ($t) {
      #// for each reachable node
      my $toSet = $t->getValueAsIdSet();
      my ($id);
      foreach $id ($toSet->enumerate()) {
        #// is it a member already of the result?
        if (!$result->memberOf($id)) {
          #// follow the node
          $self->_reachableSetInner($result,$id);
          }
        }
      }
    return $result;
    }
#---------------------------------------------------------------------

1;
