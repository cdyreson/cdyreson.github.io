package ExpressionTree::RegExp::KleeneClosure;

  use strict;
  use Carp;

=head1 NAME ExpressionTree::RegExp::KleeneClosure

=head1 DESCRIPTION

This class represents the KleeneClosure (zero to infinity) matching
of a single regular expression.

=head1 METHODS

=head2 new(Region token)

=over 4

=item * token

- The L<Region> to match.

=back

Create a new KleeneClosure operation.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    croak "No token in Token." unless scalar(@_);
    $self->{'value'} = shift;
    $self->{'resultType'} = 'Path';
    bless $self, $type;
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

- A L<SemiStructuredDB::Path>, the path to this point in the graph.

=back

Compare the token against all the edges out of this node.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $graph, $pathIn) = @_;
    my $exp = $self->{'value'};

    # path contains nothing, zero matches are possible
    my @constructed = ($pathIn);

    # Do a single matching of this regExp
    my $extendedPathList = $exp->evaluate($graph, $pathIn);

    # Exit is nothing new is found
    return \@constructed unless scalar(@$extendedPathList);

    # Extend each path further (the next N rounds of Kleene closure)
    foreach my $extendedPath (@$extendedPathList) {

      # Go off and figure out all the next rounds
      my $paths = $self->evaluate($graph, $extendedPath);
      @constructed = (@constructed, @$paths) if scalar(@$paths);
      }

    return \@constructed;
    }
#---------------------------------------------------------------------

1;
