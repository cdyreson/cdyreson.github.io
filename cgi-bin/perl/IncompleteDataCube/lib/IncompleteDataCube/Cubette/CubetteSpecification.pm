package CubetteSpecification;

=head1 NAME CubetteSpecification

The CubetteSpecification class implements the specification of a cubette
in terms of units and measures.

This package is part of L<IncompleteDataCube::Cubette>.
For more information on the cube see the 
<a href="IncompleteDataCube/Overview.html">Overview</a>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the 
<a href="IncompleteDataCube/License.html">license</a>
and 
<a href="IncompleteDataCube/License.html">version</a>.

=head1 DESCRIPTION

The CubetteSpecification class implements the specification of a cubette
in terms of units and measures.

=head1 METHODS

=head2 new($cubette, $filterUnitTable, $filterMeasureTable)

=over 4

=item * $cubette

- A L<DBMDatabase::Id>?  The cubette.

=item * $filterUnitTable

- A L<DBMDatabase::Table>.  The units for each filter.

=item * $filterMeasureTable

- A L<DBMDatabase::Table>.  The measures for each filter.

=back

Construct a specification.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    # Are there three or two arguments?
    if (scalar(@_) == 3) {
      my ($cubette, $filterUnitTable, $filterMeasureTable) = @_;
      #// load cubette unit and measures off disk
      my $u = $filterUnitTable->retrieveTuple($cubette);
      die "No units for this filter!" unless $u;
      my $m = $filterMeasureTable->retrieveTuple($cubette);
      die "No measures for this filter!" unless $m;
      my @units = $u->getValueAsIdList()->toIdArray();
      my @measures = $m->getValueAsIdList()->toIdArray();
      $self->{'measures'} = \@measures;
      $self->{'units'} = \@units;
      $self->{'value'} = new Id(0);
      }
    else {
      my ($units, $measures) = @_;
      $self->{'measures'} = $measures;
      $self->{'units'} = $units;
      $self->{'value'} = new Id(0);
      }
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 image()

Create a nice formatted image of the Cubette.

=cut

#---------------------------------------------------------------------
  sub image {
    my ($self) = @_;
    my $s = "";
    my $value = $self->{'value'};
    my $units = $self->{'units'};
    my $measures = $self->{'measures'};
    $s .= $value->image();
    my $separator = " == ";
    my $unit;
    foreach $unit (@$units) {
      #print "In image $unit\n";
      $s .= $separator . $unit->toString();
      $separator = "|";
      }
    return $s;
    }
#---------------------------------------------------------------------


=head2 imageWithMeasures()

Create a nice formatted image of the Cubette with measures, e.g,

   World@continents, 1997@months, Incomplete Cube@Curtis Dyreson

=cut

#---------------------------------------------------------------------
  sub imageWithMeasures {
    my ($self) = @_;
    my $s = "";
    my $units = $self->{'units'};
    my $measures = $self->{'measures'};
    my $i;
    my $separator = "";
    for ($i = 0; $i < scalar(@$units); $i++) {
      $s .= $separator . @$units[$i]->toString() . 
            "@" . @$measures[$i]->toString();
      $separator = ", ";
      }
    return $s;
    }
#---------------------------------------------------------------------

=head2 getUnits()

Return an L<DBMDatabase::IdList> of the units in this cubette.

=cut

#---------------------------------------------------------------------
  sub getUnits { my ($self) = @_; return $self->{'units'}; }
#---------------------------------------------------------------------

=head2 getMeasures()

Return an L<DBMDatabase::IdList> of the measures in this cubette.

=cut

#---------------------------------------------------------------------
  sub getMeasures { my ($self) = @_; return $self->{'measures'}; }
#---------------------------------------------------------------------

1;
