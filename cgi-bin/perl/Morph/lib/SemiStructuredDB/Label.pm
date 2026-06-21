package Label;

  use strict;
  # inherits the persistent
  @Label::ISA = qw( Persistent );

=head1 NAME Label 

Class encapsulating an Edge Label in a SemiStructured Database

This package is part of L<SemiStructuredDB>.

=head1 DESCRIPTION

A Label is a list of property space regions (see L<Region>).
The label supports matching, slicing, coalescing, and collapsing
operations.

=cut

#---------------------------------------------------------------------

=head1 METHODS

=head2 new(List of Regions r)

=over 4

=item * RegionList r 

- The list of L<Region>s

=back

A Label is a list of property space L<Region>s.
This creates a Label from a list of L<Region>s.

=cut

#---------------------------------------------------------------------
sub new {
  my $type = shift;
  my $self = {};
  my ($a) = shift || [];
  my %h = ();
  $self->{'resultType'} = $type;
  $self->{'value'} = $a;
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

=head2 regions()

Return a reference to the list of regions in a Label.

=cut

#---------------------------------------------------------------------
sub regions {
  my ($self) = @_;
  return $self->{'value'};
  } 
#---------------------------------------------------------------------

=head2 insert(Region r)

=over 4

=item * RegionList r 

- The list of L<Region>s

=back

Insert a region into the list.

=cut

#---------------------------------------------------------------------
sub insert {
  my ($self) = @_;
  return $self->{'value'};
  } 
#---------------------------------------------------------------------

=head2 dimensionExtract(string dimension)

=over 4

=item * string dimension

- The name of the dimension to extract

=back

Extracts the dimension value from each region in the list 
building a new list.

=cut

#---------------------------------------------------------------------
sub dimensionExtract {
  my ($self, $dimension) = @_;
  my ($regions) = $self->{'value'};
  my @constructed = ();
  foreach my $region (@$regions) {
    my ($value) = $region->dimensionExtract($dimension);
    # skip if no good
    next unless defined $value;
    # good value was extracted
    push @constructed, $value;
    }
  return \@constructed;
  }
#---------------------------------------------------------------------

=head2 slice(Region S)

=over 4

=item * Region S 

- The region to slice with.

=back

Slices a portion from each region in the label, creating a new label.

=cut

#---------------------------------------------------------------------
sub slice {
  my ($self, $slicer) = @_;
  my ($regions) = $self->{'value'};
  my @constructed = ();
  foreach my $region (@$regions) {
    my ($newRegion) = $region->slice($slicer);
    # skip if no good
    next unless $newRegion;
    # good region was constructed
    push @constructed, $newRegion;
    }

  # return false if no labels matched
  return 0 unless scalar(@constructed);

  return new Label(\@constructed);
  } 
#---------------------------------------------------------------------

=head2 collapse(Label other)

=over 4

=item * Label other

- The label to collapse with.

=back

Collapses this label with another, creating a new label.
The collapsing is the pairwise collapsing of each region in
each label.  Typically, this will be one region in, one region out.

This function will fail (return value of undef) if there is
no collapsed label (the resulting label has some empty property in 
all Regions).

=cut

#---------------------------------------------------------------------
sub collapse {
  my ($self, $otherLabel) = @_;
  my ($regions) = $self->{'value'};
  my @constructed = ();
  my $otherRegions = $otherLabel->regions();
  foreach my $region (@$regions) {
    foreach my $other (@$otherRegions) {
      # do the collapsing
      my ($collapsedRegion) = $region->collapse($other);

      # is it a bad region?
      next unless defined $collapsedRegion;

      # OK it is valid, keep it
      push @constructed, $collapsedRegion;
      }
    }

  # die if the collapsing failed
  return undef unless scalar(@constructed);

  return new Label(\@constructed);
  } 
#---------------------------------------------------------------------

=head2 match(Region r)

=over 4

=item * Region r 

- The region to match against.

=back

Matches a region to every region in this label, returns a new Label
that contains all the matching regions, or 0 (false) if no region
matches.

=cut

#---------------------------------------------------------------------
sub match {
  my ($self, $otherRegion) = @_;
  my ($regions) = $self->{'value'};

  my @constructed = ();
  foreach my $region (@$regions) {
    push @constructed, $region if $otherRegion->match($region);
    }

  # return false if no labels matched
  return 0 unless scalar(@constructed);

  # return new label with the matched regions
  return new Label(\@constructed);
  } 
#---------------------------------------------------------------------

=head2 coalesce(String propertyName)

=over 4

=item * String propertyName

- The propertyName to coalesce on.

=back

Coalesce all the Regions in this Label on the indicated property.

=cut

#---------------------------------------------------------------------
sub coalesce {
  my ($self, $propertyName) = @_;
  my ($regions) = $self->{'value'};

  # create a list of the property from each region
  my @propertyValues = ();
  foreach my $region (@$regions) {
    my $p = $region->properties;
    push @propertyValues, $$p{$propertyName} if defined $$p{$propertyName};
    }

  # exit if no property
  return undef unless scalar(@propertyValues);

  # create the coalesced property value from the list
  my $result = pop @propertyValues;
  foreach my $value (@propertyValues) {
    $result = $value->coalesce($result);
    }
  return $result;
  } 
#---------------------------------------------------------------------

1;
