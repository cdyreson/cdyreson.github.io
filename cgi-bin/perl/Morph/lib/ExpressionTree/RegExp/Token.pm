package ExpressionTree::RegExp::Token;

  use strict;

=head1 NAME ExpressionTree::RegExp::Token

=head1 DESCRIPTION

This class represents matching of a single 'token' in a
regular expression, to the current place in a path.

=head1 METHODS

=head2 new(Region token)

=over 4

=item * token

- The L<Region> to match.

=back

Create a new Token operation.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    my $region = shift; 
    $self->{'value'} = $region;
    $self->{'resultType'} = 'Edges';
    bless $self, $type;
    }
#---------------------------------------------------------------------
#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No token in Token." unless scalar(@_);
    my $region = shift; 
    return "Not passed a region in Token." unless ref($region) eq 'Region';
    return undef; #passed validation
    }
#---------------------------------------------------------------------

=head2 resultType()

Return the type of result.

=cut

#---------------------------------------------------------------------
  sub resultType {
    my ($self) = @_;
    return $self->{'resultType'};
    }
#---------------------------------------------------------------------

=head2 dump($indent)

Dump this node.

=cut

#---------------------------------------------------------------------
  sub dump {
    my ($self, $indent) = @_;
    my ($extra) = '  ';
    my ($s) = "$indent Token: " . $self->{'value'}->dump() . "\n";
    return $s;
    }
#---------------------------------------------------------------------

=head2 evaluate(LabelledGraph graph, Path path)

=over 4

=item * graph

- A L<SemiStructuredDB::LabelledGraph> that stores the semistructure.

=item * path

- A L<SemiStructuredDB::PPath>, the current path in the graph.

=back

Compare the token against all the edges out of the terminal node
in the current path.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $graph, $path) = @_;

    # Figure out which edges out of the terminal node match the descriptor
    my $edgeList = $graph->match($path->terminalNode(), $self->{'value'});

    my @constructed = ();
    my $edges = $edgeList->enumerateEdges();

    # does any edge match?
    return \@constructed unless scalar(@$edges);

    # Extend the path with each found edge
    foreach my $edge (@{$edgeList->enumerateEdges()}) {
      my $newPath = $path->clone();

      # Ignore invalid paths
      next unless $newPath->append($edge);

      # Keep valid paths
      push @constructed, $newPath;
      }

    # Return the new set of paths
    return \@constructed;
    }
#---------------------------------------------------------------------

1;
