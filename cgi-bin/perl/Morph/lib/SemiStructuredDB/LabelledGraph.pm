package LabelledGraph;

=head1 NAME LabelledGraph 

Class encapsulating a persistent labelled graph.

=head1 DESCRIPTION

A persistent labelled graph is a L<OODatabase::Table> mapping from nodes to 
to nodes.

=head1 METHODS

=head2 new($db, $name)

=over 4

=item * $db

- A L<OODatabase::Database>, the database where the graph will live

-item * $name

- A string, the name of the graph

=back

Create a new persistent graph.

=cut

use Carp;

#---------------------------------------------------------------------
  sub new {
    my($type, $db, $name) = @_;
    my $self = {};
    $self->{'edges'} = $db->createTable("$name.edges");
    #DIM#$self->{'dimensionTable'} = $db->createTable("$name.dimensions");
    #DIM## OK, now swizzle the dimensions
    #DIM#my ($t) = $self->{'dimensionTable'}->retrieveTuple(new String('0'));
    #DIM#if ($t) {
    #DIM#  $self->{'dimensions'} = Swizzle::_rebuild($t->getValue());
    #DIM#  }
    #DIM#else {
      #DIM#my %h = ();
      #DIM#$self->{'dimensions'} = \%h;
      #DIM#}
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 addDimension(name, type)

Add a dimension

=cut

#---------------------------------------------------------------------
  sub addDimension {
    my ($self, $name, $type) = @_;
    #DIM#my ($dimensionHash) = $self->{'dimensions'};
    #DIM#my ($t) = $self->{'dimensionTable'};

    #DIM#$$dimensionHash{$name} = $type;
    #DIM#$t->insertTuple(new Tuple(new String('0'), 
                    #DIM#new StringCol(Swizzle::_flatten($dimensionHash))));
    }
#---------------------------------------------------------------------

=head2 KeysImage()

Return an image of all the keys in the graph.

=cut

#---------------------------------------------------------------------
  sub KeysImage {
    my ($self) = @_;
    my ($edges) = $self->{'edges'};
    return $edges->KeysImage();
    }
#---------------------------------------------------------------------

=head2 addEdge(Edge edge)

=over 4

=item * edge

- A labelled edge.

=back

Add an edge. 

=cut

#---------------------------------------------------------------------
  sub addEdge {
    my ($self, $edge) = @_;
    my ($edgeTable) = $self->{'edges'};
    my ($edges);

    # retrieve existing edges from this node from the edge table
    my ($from) = $edge->from();
    my ($t) = $edgeTable->retrieveTuple($from);
    if ($t) { $edges = EdgeList->fromBytes($t->getValue()) if $t; }
    else { $edges = new EdgeList($from, []); }

    # Insert the new edge into the edge list
    $edges->insert($edge);

    # Update the edge table
    $edgeTable->insertTuple(new Tuple($from, $edges));
    return 1;
    }
#---------------------------------------------------------------------

=head2 nodesFromMatch(fromNode, testRegion)
 
=over 4
 
=item * fromNode
 
- A L<Domains::Node>, the node to start from
 
=item * testRegion
 
- The L<Region> to check
 
=back
 
Get the set of nodes that match the testRegion from the fromNode.
Returns an L<Domains::NodeSet>, the set of nodes.
 
=cut
 
#---------------------------------------------------------------------
  sub nodesFromMatch {
    my ($self, $fromNode, $testRegion) = @_;

    # retrieve the set of edges from fromNode out of the edge table
    my ($edges);
    my ($edgeTable) = $self->{'edges'};
    my ($t) = $edgeTable->retrieveTuple($fromNode);
    if ($t) { $edges = EdgeList->fromBytes($t->getValue()); }
    else { $edges = new EdgeList($fromNode, []); }
    my ($toSet) = new NodeSet();

    # Test each edge out of this node
    my $edgeList = $edges->enumerateEdges();
    foreach my $edge (@{$edgeList}) {

      # if it matches...
      if ($edge->match($fromNode, $testRegion)) {

        # add the to node to the result set
        $toSet->insert($edge->toNode());
        }
      }

    return $toSet;
    }
#---------------------------------------------------------------------

=head2 match(fromNode, testRegion)

=over 4

=item * fromNode

- An L<Domains::Node>, the node to start from

=item * testRegion

- The L<Region> to check

=back

Matches the testRegion against the regions in the labels on edges out 
of fromNode.  Returns a list of edges!

=cut

#---------------------------------------------------------------------
  sub match {
    my ($self, $fromNode, $testRegion) = @_;

    # retrieve the set of edges from fromNode out of the edge table
    my ($edges);
    my ($edgeTable) = $self->{'edges'};
    my ($t) = $edgeTable->retrieveTuple($fromNode);
    if ($t) { $edges = EdgeList->fromBytes($t->getValue()); }
    else { $edges = new EdgeList($fromNode, []); }
    my ($result) = new EdgeList($fromNode, []);

    # Test each edge out of this node
    my $edgeList = $edges->enumerateEdges();
    foreach my $edge (@{$edgeList}) {

      # if it matches...
      if ($edge->match($testRegion)) {

        # add the edge to the result set
        $result->insert($edge);
        }
      }

    return $result;
    }
#---------------------------------------------------------------------

=head2 roots()

Return the EdgeList of root nodes.

=cut

#---------------------------------------------------------------------
  sub roots {
    my ($self) = @_;

    # retrieve the set of edges from fromNode out of the edge table
    my ($edges);
    my ($edgeTable) = $self->{'edges'};
    my ($t) = $edgeTable->retrieveTuple(new Node('&root'));
    if ($t) { $edges = EdgeList->fromBytes($t->getValue()); }
    else { $edges = new EdgeList(new Node('&root'), []); }

    return $edges;
    }
#---------------------------------------------------------------------

=head2 enumerate()

Return a reference to the dbm file.

=cut

#---------------------------------------------------------------------
  sub enumerate {
    my ($self) = @_;
    my ($edgeTable) = $self->{'edges'};
    return $edgeTable->enumerate();
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

1;
