package ExpressionTree::Operations::Collapse;

  use strict;

=head1 NAME ExpressionTree::Operations::Collapse;

=head1 DESCRIPTION

This class represents the Path Collapse operation.

=head1 METHODS

=head2 new(variable)

=over 4

=item * variable

The name of a variable, which is the path to collapse, 
the constructors are with the dimension specification.

=back

Create a new Collapse operation

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'variable'} = shift;
    $self->{'resultType'} = undef;
    bless $self, $type;
    }
#---------------------------------------------------------------------
#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No path to Collapse." unless scalar(@_);
    return undef; # passed validation!
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
    my ($op) = $self->{'op'};
    my ($s) = "$indent $op\n   " . $self->{'variable'}->dump("$indent$extra");
    return $s;
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

=over 4

=item * state

- The state of the system.

=back

Evaluate the Collapse operation.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    # Do a symbol table lookup or operation evaluation 
    my $startPaths = $self->{'variable'}->evaluate($state);

    # make it into a list if it is a path
    $startPaths = [$startPaths] if ref($startPaths) eq 'Path';

    my @constructed = ();
    foreach my $startPath (@$startPaths) {

      # We know it is a good path, just fetch the collapsed path!
      push @constructed, $startPath->collapse();
      }
    return \@constructed;
    }
#---------------------------------------------------------------------

1;
