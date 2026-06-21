package ExpressionTree::RegExp::OptionalToken;

  use strict;
  use Carp;

=head1 NAME ExpressionTree::RegExp::OptionalToken

=head1 DESCRIPTION

This class represents the KleeneClosure (zero or one) matching of a 
single regular expression.

=head1 METHODS

=head2 new(RegEXp r)

=over 4

=item * r

- The regular expression to make optional.

=back

Create a new OptionalToken operation.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    croak "No token in OptionalToken." unless scalar(@_);
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

=head2 evaluate(LabelledGraph graph, Path pathIn)

=over 4

=item * graph

- A L<SemiStructuredDB::LabelledGraph> that stores the semistructure.

=item * pathIn

- A L<SemiStructuredDB::Path>, the current path in the graph.

=back

Compare the token against all the edges out of the terminal node in
the current path.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $graph, $pathIn) = @_;
    my $exp = $self->{'value'};

    # path contains nothing, zero matches are possible
    my @constructed = ($pathIn);

    # Do a single matching of this regExp
    my $paths = $exp->evaluate($graph, $pathIn);

    # Exit is nothing new is found
    return \@constructed unless scalar(@$paths);

    # Add the extended paths 
    @constructed = (@constructed, @$paths) if scalar(@$paths);
    return \@constructed;
    }
#---------------------------------------------------------------------

1;
