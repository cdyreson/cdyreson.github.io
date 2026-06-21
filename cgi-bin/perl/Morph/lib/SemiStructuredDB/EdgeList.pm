package EdgeList;

  use Carp;
  @EdgeList::ISA = qw( Persistent );

=head1 NAME EdgeList

Class encapsulating a set of L<SemiStructuredDB::Edge>s.

This package is part of L<SemiStructuredDB>.

=head1 DESCRIPTION

An EdgeList is a data type for columns in a 
database L<Table>.  An EdgeList cannot be used as a key.

=cut

#---------------------------------------------------------------------

=head1 METHODS

=head2 new(Node from, list a)

=over 4

=item * from

- The reference to a the from node object.

=item * a

- The reference to a list of edges to store, often it is an empty list.

=back

Construct an empty List

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $fromNode = shift;
    my $aref = shift;
    my $self = {};
    my %h = ();
    $self->{'value'} = \%h;
    $self->{'fromString'} = $fromNode->value();
    bless $self, $type;
    foreach my $edge (@$aref) { $self->insert($edge); }
    return $self;
    }
#---------------------------------------------------------------------

=head2 insert(Edge edge)

=over 4

=item * edge

- The L<SemiStructuredDB::Edge> to insert.

=back

Inserts an L<SemiStructuredDB::Edge> into this list.

=cut

#---------------------------------------------------------------------
  sub insert {
    my ($self, $edge) = @_;

    # See if the edge to insert is from this node!
    my $fromString = $edge->from->value();
    croak "bad from $fromString for node " . $self->{'fromString'}
      unless $self->{'fromString'} eq $fromString;

    # Iterate through all the edges
    my $h = $self->{'value'};
    my $node = $edge->to();
    my $nodeString = $node->value();

    # check to see if we have an entry for the to bit in this edge
    if (defined $$h{$nodeString}) {

      # cycle through all the regions, inserting each in the label
      my $edgeLabel = $edge->label();
      my $label = $$h{$nodeString};
      my $edgeRegions = $edgeLabel->regions();
      foreach my $region (@$edgeRegions) {
        $label->insert($region);
        }
      }
    else {
 
      # we have an edge to a completely new node
      $$h{$nodeString} = $edge->label();
      }
    }
#---------------------------------------------------------------------

=head2 enumerateEdges()

Return an enumeration of the L<Edge>s in the EdgeList.  
OK, we have to fake it in Perl since this is a Java concept 

=cut

#---------------------------------------------------------------------
  sub enumerateEdges {
    my ($self) = @_;
    my $h = $self->{'value'};
    my @constructed = ();
    my $fromNode = new Node($self->{'fromString'});
    foreach my $key (keys %$h) {
      my $toNode = new Node($key);
      push @constructed, new Edge($fromNode, $$h{$key}, $toNode);
      }
    return \@constructed;
    }
#---------------------------------------------------------------------

=head2 enumerateToNodes()

Return an enumeration of the 'to' nodes in the EdgeList.  
OK, we have to fake it in Perl since this is a Java concept 

=cut

#---------------------------------------------------------------------
  sub enumerateToNodes {
    my ($self) = @_;
    my $h = $self->{'value'};
    return keys %$h;
    }
#---------------------------------------------------------------------

=head2 image()

Create a formatted string of all the elements in the list

=cut

#---------------------------------------------------------------------
  sub image {
    my ($self) = @_;
    my $h = $self->{'value'};

    my $result = '';
    foreach my $toNode (keys %$h) {
      $result .= "  " . $$h{$toNode}->toString . " $toNode\n";
      }
    return $result . "\nend of list\n";
    }
#---------------------------------------------------------------------

1;
