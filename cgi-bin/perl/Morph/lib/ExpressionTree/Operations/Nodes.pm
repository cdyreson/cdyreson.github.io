package ExpressionTree::Operations::Nodes;

   use strict;

=head1 NAME ExpressionTree::Operations::Nodes;

=head1 DESCRIPTION

This class represents a Nodes operation.

=head1 METHODS

=head2 new(paths)

=over 4

=item * paths

The paths to extract nodes from

=back

Create a new Nodes operation

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'paths'} = shift;
    $self->{'resultType'} = undef;
    bless $self, $type;
    }
#---------------------------------------------------------------------
#---------------------------------------------------------------------
  sub validate {
    my $type = shift;
    return "No starting paths in Nodes." unless scalar(@_);
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
    my ($s) = "$indent Nodes\n   " . $self->{'paths'}->dump("$indent$extra");
    $s .= "\n";
    return $s;
    }
#---------------------------------------------------------------------

=head2 evaluate()

=over 4

=item * state

- The state of the system.

=back

Evaluate the Node operation returning a list of nodes.

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    my $path = $self->{'paths'}->evaluate($state);
    #print "Path is $path\n";
    my @constructed = ($path->terminalNode());
    #print "Node " . $path->terminalNode()->value() . "\n";
    return \@constructed;
    }
#---------------------------------------------------------------------

1;
