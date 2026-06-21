package Region;

  use strict;
  # inherits the persistent
  @Region::ISA = qw( Persistent );

=head1 NAME Region

Class encapsulating a property space Region in a SemiStructured Database

This package is part of L<SemiStructuredDB>.

=head1 DESCRIPTION

A Region is an object that has a set of descriptive properties.
This list is represented as a hash table internally to the Region.

=cut

#---------------------------------------------------------------------

=head1 METHODS

=head2 new(Hash table)

=over 4

=item * table

- A hash table of the mapping from property names to values

=back

A Region is a hash table of objects, that maps property names to values.

=cut

#---------------------------------------------------------------------
sub new {
  my $type = shift;
  my $self = {};
  my ($table) = @_;
  my %h = ();
  $self->{'value'} = \%h;
  $h{'table'} = $table;
  bless $self, $type;
  }
#---------------------------------------------------------------------

=head2 toString()

Returns a string image of an Region.

=cut

#---------------------------------------------------------------------
sub toString {
  my ($self) = @_;
  return $self->toBytes();
  } 
#---------------------------------------------------------------------

=head2 image()

Return a string image of the Region.

=cut

#---------------------------------------------------------------------
sub image {
  my ($self) = @_;
  return $self->toString();
  } 
#---------------------------------------------------------------------

=head2 properties()

Return the hash table of properties from the Region.

=cut

#---------------------------------------------------------------------
sub properties {
  my ($self) = @_;
  my ($h) = $self->{'value'};
  return $$h{'table'};
  } 
#---------------------------------------------------------------------

=head2 dimensionExtract(string dimension)

=over 4

=item * string dimension

- The name of the dimension to extract

=back

Extracts a value in the indicated dimension from this region.
Returns undef if the property for that dimension is missing.

=cut

#---------------------------------------------------------------------
sub dimensionExtract {
  my ($self, $dimension) = @_;
  my ($this) = $self->properties();
  return $$this{$dimension};
  }
#---------------------------------------------------------------------

=head2 slice(Region S)

=over 4

=item * Region S 

- The region to slice with.

=back

Slices a portion from the region, creating a new region.

=cut

#---------------------------------------------------------------------
sub slice {
  my ($self, $region) = @_;
  my %constructed = ();
  my ($this) = $self->properties;
  my ($other) = $region->properties;

  # cycle through all the properties in my region
  foreach my $key (keys %$this) {
    # is it in the other one?
    if (defined $$other{$key}) {
      # do the slicing
      my ($newValue) = $$this{$key}->slice($$other{$key});
      # exit if value on good
      return 0 unless defined $newValue;
      $constructed{$key} = $newValue;
      }
    else {
      # missing from the other region
      $constructed{$key} = $$this{$key};
      }
    }

  # cycle through all the properties in the slicing region
  # keeping only required properties
  foreach my $key (keys %$other) {

    # already handled it if it is in this region
    next if defined $$this{$key};

    next unless $$other{$key}->isRequired();

    # missing from this region, add it
    $constructed{$key} = $$other{$key};
    }
  return new Region(\%constructed);
  } 
#---------------------------------------------------------------------

=head2 collapse(Region S)

=over 4

=item * Region S 

- The region to collapse against.

=back

Collapses a region creating a new region.

=cut

#---------------------------------------------------------------------
sub collapse {
  my ($self, $region) = @_;
  my %constructed = ();
  my ($this) = $self->properties;
  my ($other) = $region->properties;

  # cycle through all the properties in my region
  foreach my $key (keys %$this) {

    # is it in the other one?
    if (defined $$other{$key}) {

      # Do the collapsing
      my ($collapsedValue) = $$this{$key}->collapse($$other{$key});

      # Did an empty property get generated?
      return undef unless defined $collapsedValue;

      # If either property is required, then the result is required
      $collapsedValue->required() if 
        $$this{$key}->isRequired() || $$other{$key}->isRequired();

      # Property is OK, keep going.
      $constructed{$key} = $collapsedValue;
      }
    else {

      # missing from the other region
      $constructed{$key} = $$this{$key};
      }
    }

  # cycle through all the properties in the other region!
  foreach my $key (keys %$other) {

    # skip it if it is in mine since it is already handled?
    next if defined $$this{$key};

    # missing from this region, but in other region
    $constructed{$key} = $$other{$key};
    }

  return new Region(\%constructed);
  } 
#---------------------------------------------------------------------

=head2 match(Region S)

=over 4

=item * Region S 

- The region to match against.

=back

Matches a region, returns a boolean.

=cut

#---------------------------------------------------------------------
sub match {
  my ($self, $region) = @_;
  my ($this) = $self->properties;
  my ($other) = $region->properties;

  # cycle through all the properties in my region
  foreach my $key (keys %$this) { 
    my ($myValue) = $$this{$key};

    # is it required by me?
    if ($myValue->isRequired()) {

      # its required, but the other guy is missing
      return 0 unless defined $$other{$key};

      # test against the other guy, return false if it fails
      return 0 unless $myValue->match($$other{$key});
      }

    # not required by me
    else {

      # does the other guy have it?
      if (defined $$other{$key}) { 
        return 0 unless $myValue->match($$other{$key});
        }
      }
    }

  # check to see if any required keys in the other are missing in mine
  foreach my $key (keys %$other) {
    my ($otherValue) = $$other{$key};

    # is it required by the other?  
    #If it is and missing from me, return false
    if ($otherValue->isRequired()) { return 0 unless defined $$this{$key}; }

    }

  # passed all tests, it must match
  return 1;
  } 
#---------------------------------------------------------------------

1;
