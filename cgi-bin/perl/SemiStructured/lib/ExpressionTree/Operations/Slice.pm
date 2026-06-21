package ExpressionTree::Operations::Slice;
 
   use strict;

=head1 NAME ExpressionTree::Operations::Slice;

=head1 DESCRIPTION

This class represents the Path Slice operation.

=head1 METHODS

=head2 new(descriptor, variable)

=over 4

=item * descriptor

The descriptor to slice with.

=item * variable 

The name of the variable representing the paths to slice.

=back

Create a new Slice operation

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'descriptor'} = shift;
    $self->{'variable'} = shift;
    $self->{'resultType'} = undef;
    bless $self, $type;
    }
#---------------------------------------------------------------------
#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No descriptor in Slice." unless scalar(@_);
    shift;
    return "No paths in Slice." unless scalar(@_);
    return undef;
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
    my ($s) = "$indent Slice\n   ".$self->{'descriptor'}->dump("$indent$extra");
    $s .= "$indent   \n   " . $self->{'variable'}->dump("$indent$extra") . "\n";
    return $s;
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

=over 4

=item * state

- The state of the system.

=back

Evaluate the Slice operation.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    # Do a symbol table lookup to find the value for this variable
    my $startPaths = $self->{'variable'}->evaluate($state);

    # If it is a path, turn it into a list of paths
    $startPaths = [$startPaths] if ref($startPaths) eq 'Path';

    my $descriptor = $self->{'descriptor'};
    my @constructed = ();

    foreach my $startPath (@$startPaths) {

      # Evaluate the slice, creating a new path 
      my ($newPath) = $startPath->slice($descriptor);

      # If a legal path results add it to the list of paths
      if ($newPath) {
        push @constructed, $newPath if defined $newPath;
        }
      }

    return \@constructed;
    }
#---------------------------------------------------------------------

1;
