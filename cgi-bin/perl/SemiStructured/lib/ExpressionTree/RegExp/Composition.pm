package ExpressionTree::RegExp::Composition;

  use strict;
  use Carp;

=head1 NAME ExpressionTree::RegExp::Composition

=head1 DESCRIPTION

This class represents the 'period' operator in a regular expression.

=head1 METHODS

=head2 new(Region token)

=over 4

=item * token

- The L<Region> to match.

=back

Create a new Composition operation.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    croak "No prefix in Composition." unless scalar(@_);
    $self->{'prefix'} = shift;
    croak "No suffix in Composition." unless scalar(@_);
    $self->{'suffix'} = shift;
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
    my ($s) = "$indent Token: " . $self->{'prefix'}->dump() . "\n";
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

Compare the token against all the edges out of this node.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $graph, $pathIn) = @_;

    my @constructed = ();

    # Figure out which paths match the first operand.
    my ($prefix) = $self->{'prefix'};
    my ($prefixPathList) = $prefix->evaluate($graph, $pathIn);
    
    # Exit if nothing matched!
    return \@constructed unless scalar(@$prefixPathList);
   
    # Extend the path 
    my ($suffix) = $self->{'suffix'};
    foreach my $prefixPath (@$prefixPathList) {
      my ($paths) = $suffix->evaluate($graph, $prefixPath);
      @constructed = (@constructed, @$paths) if scalar(@$paths);
      }
 
    return \@constructed;
    }
#---------------------------------------------------------------------

1;
