package Edge;

=head1 NAME Edge

Class encapsulating an Edge in a SemiStructured Database

This package is part of L<SemiStructuredDB>.

=head1 DESCRIPTION

A Edge is a list from, label, and to.

=cut

#---------------------------------------------------------------------

=head1 METHODS

=head2 new(Id from, Label label, Id to)

=over 4

=item * to

- The from node, a L<Node>

=item * label

- The label, a L<Label> 

=item * from

- The to node, a L<Node> 

=back

An Edge is a ordered triplet.

=cut

#---------------------------------------------------------------------
sub new {
  my $type = shift;
  my $self = {};
  my ($fromNode) = shift || die "no from node for an Edge";
  my ($label) = shift || die "no label for an Edge";
  my ($toNode) = shift || die "no to node for an Edge";
  my @a = ($fromNode, $label, $toNode);
  $self->{'value'} = \@a;
  bless $self, $type;
  }
#---------------------------------------------------------------------

=head2 toString()

Returns a string image of an Label.

=cut

#---------------------------------------------------------------------
sub toString {
  my ($self) = @_;
  return $self->toBytes();
  } 
#---------------------------------------------------------------------

=head2 image()

Return a string image of the Label.

=cut

#---------------------------------------------------------------------
sub image {
  my ($self) = @_;
  return $self->toString();
  } 
#---------------------------------------------------------------------

=head2 from()

Return the from node.

=cut

#---------------------------------------------------------------------
sub from {
  my ($self) = @_;
  my ($a) = $self->{'value'};
  return @$a[0];
  } 
#---------------------------------------------------------------------

=head2 to()

Return the to node.

=cut

#---------------------------------------------------------------------
sub to {
  my ($self) = @_;
  my ($a) = $self->{'value'};
  return @$a[2];
  } 
#---------------------------------------------------------------------

=head2 label()

Return the label.

=cut

#---------------------------------------------------------------------
sub label {
  my ($self) = @_;
  my ($a) = $self->{'value'};
  return @$a[1];
  } 
#---------------------------------------------------------------------

=head2 slice(Region region)

=over 4

=item * region

- Slice this region from each label on the edge.

=back

Returns a new edge with the regions in the label that were sliced from 
the existing edge.
Returns 0 if no label could be sliced.

=cut

#---------------------------------------------------------------------
sub slice {
  my ($self, $region) = @_;
  my ($a) = $self->{'value'};
  my ($label) = @$a[1];
  my ($newLabel) = $label->slice($region);
  return new Edge(@$a[0], $newLabel, @$a[2]) if $newLabel;
  return 0;
  } 
#---------------------------------------------------------------------

=head2 match(Region region)

=over 4

=item * region

- Match this region against the label on the edge.

=back

Returns a new edge with the regions in the label that match the edge.

=cut

#---------------------------------------------------------------------
sub match {
  my ($self, $region) = @_;
  my ($a) = $self->{'value'};
  my ($label) = @$a[1];
  my ($newLabel) = $label->match($region);
  return new Edge(@$a[0], $newLabel, @$a[1]) if $newLabel;
  return 0;
  } 
#---------------------------------------------------------------------

1;
