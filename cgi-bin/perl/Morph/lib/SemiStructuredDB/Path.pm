package Path;

  use strict;

=head1 NAME Path 

Class representing a path value.
A path is a sequence of L<SemiStructuredDB::Edge>s.

This package is part of L<SemiStructuredDB>.

=head1 DESCRIPTION

A Path is a sequence of L<Edge>s.

=cut

#---------------------------------------------------------------------

=head1 METHODS

=head2 new(Edge edge)

=over 4

=item * edge

- A L<SemiStructuredDB::Edge> to start the path with, this edge is
optional.

=back

Construct an empty path, or a path with one edge.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    my $edge = shift;
    $self->{'value'} = [$edge];
    $self->{'validity'} = $edge->label();
    $self->{'terminalNode'} = $edge->to();
    $self->{'startNode'} = $edge->from();
    my %h = ();
    $h{$edge->to()} = 1;
    $self->{'visited'} = \%h;
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 collapse()

Fetch the collapsed path.

=cut

#---------------------------------------------------------------------
  sub collapse {
    my ($self) = @_;
    
    my $toNode = $self->{'terminalNode'};
    my $fromNode = $self->{'startNode'};
    my $label = $self->{'validity'};
    return new Path(new Edge($fromNode, $label, $toNode));
    }
#---------------------------------------------------------------------

=head2 dimensionExtract(string dimension)

Fetch the dimension value from the collapsed area.
Returns a list of values.

=cut

#---------------------------------------------------------------------
  sub dimensionExtract {
    my ($self, $dimension) = @_;
    
    my $label = $self->{'validity'};
    return $label->dimensionExtract($dimension);
    }
#---------------------------------------------------------------------

=head2 clone()

Clone the path.

=cut

#---------------------------------------------------------------------
  sub clone {
    my ($self) = @_;
    my $path = {};
    $path->{'terminalNode'} = $self->{'terminalNode'};
    $path->{'startNode'} = $self->{'startNode'};
    my $sequence = $self->{'value'};
    $path->{'value'} = [@$sequence];
    my $visited = $self->{'visited'};
    my %h = %$visited;
    $path->{'visited'} = \%h;
    $path->{'validity'} = $self->{'validity'};
    bless $path, ref($self);
    }
#---------------------------------------------------------------------

=head2 append(Edge edge)

=over 4

=item * edge

- The L<SemiStructuredDB::Edge> to append.

=back

Adds an L<SemiStructuredDB::Edge> to the path.

=cut

#---------------------------------------------------------------------
  sub append {
    my ($self, $edge) = @_;

    # Check to see if the resulting path will be valid!
    my ($validLabel) = $self->{'validity'};
    $self->{'validity'} = $validLabel->collapse($edge->label());

    # Fail if it is not valid
    return 0 unless defined $self->{'validity'};

    # Fail if we have visited it previously
    my $visited = $self->{'visited'};
    return 0 if defined $$visited{$edge->to()};

    # Add the edge
    my $a = $self->{'value'};
    $self->{'terminalNode'} = $edge->to();
    push @$a, $edge;
    return 1;
    }
#---------------------------------------------------------------------

=head2 prepend(Edge edge)

=over 4

=item * edge

- The L<SemiStructuredDB::Edge> to prepend.

=back

Prepends a L<SemiStructuredDB::Edge> to the start of a path.

=cut

#---------------------------------------------------------------------
  sub prepend {
    my ($self, $edge) = @_;
    # Check to see if the resulting path will be valid!
    $self->{'validity'} = $edge->label->collapse($self->{'validity'});

    # Fail if it is not valid
    return 0 unless defined $self->{'validity'};

    # Fail if we have visited it previously
    my $visited = $self->{'visited'};
    return 0 if defined $$visited{$edge->from()};

    my $a = $self->{'value'};
    unshift @$a, $edge;
    $self->{'startNode'} = $edge->from();
    return 1;
    }
#---------------------------------------------------------------------

=head2 terminalNode()

Return the final node in the path.

=cut

#---------------------------------------------------------------------
  sub terminalNode {
    my ($self) = @_;
    return $self->{'terminalNode'};
    }
#---------------------------------------------------------------------

=head2 startNode()

Return the starting node in the path.

=cut

#---------------------------------------------------------------------
  sub startNode {
    my ($self) = @_;
    return $self->{'startNode'};
    }
#---------------------------------------------------------------------

=head2 image()

Create a formatted string of all the edges in the path

=cut

#---------------------------------------------------------------------
  sub image {
    my ($self) = @_;
    my $a = $self->{'value'};
    my $result = '';
    foreach my $edge (@$a) {
      $result .= "  " . $edge->image . "\n";
      }
    return $result . "\nend of path\n";
    }
#---------------------------------------------------------------------

=head2 slice(Region region)

=over 4

=item * region

- The L<SemiStructuredDB::Region> to slice with.

=back

Slice every edge in the path using the region.
Return 0 if no valid path results.
Return a new sliced path if a valid sliced path exists.
Note that this will leave the path unchanged, it generates
a new path (this method is not a mutator).

=cut

#---------------------------------------------------------------------
  sub slice {
    my ($self, $region) = @_;
    my $a = $self->{'value'};
    my $path;
    foreach my $edge (@$a) {
      # not the time through the loop
      if (defined $path) {
        my ($newEdge) = $edge->slice($region);
        # No path results if the edge could not be sliced
        return 0 unless $newEdge;
        # exit if not a valid path
        return 0 unless $path->append($edge);
        }
      else {
        # first time through the loop
        my ($newEdge) = $edge->slice($region);
        # No path results if the edge could not be sliced
        return 0 unless $newEdge;
        # OK, add the sliced edge to the path
        $path = new Path($edge->slice($region));
        }
      }
    return $path;
    }
#---------------------------------------------------------------------

=head2 resultType()

Result type is path.

=cut

#---------------------------------------------------------------------
  sub resultType {
    my ($self) = @_;
    return 'Path';
    }
#---------------------------------------------------------------------

=head2 getValue()

The value of the path is the value of the final node in the path.

=cut

#---------------------------------------------------------------------
  sub getValue {
    my ($self) = @_;
    return $self->{'terminalNode'}->getValue();
    }
#---------------------------------------------------------------------

=head2 presentation()

Present this path as the final node in the path.

=cut

#---------------------------------------------------------------------
  sub presentation {
    my ($self) = @_;
    return $self->{'terminalNode'}->presentation();
    }
#---------------------------------------------------------------------

1;
