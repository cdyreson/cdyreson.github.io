package Dimension;

=head1 NAME Dimension

Build the global variables for a single dimension.

=head1 DESCRIPTION

This is a helper class for L<IncompleteDataCube::Globals>.  It encapsulates
the creation of global variables for each dimension since all dimensions
look the same, they just have different names.

=cut

=head1 METHODS

=head2 new($dimensionName)

=over 4

=item * $dimensionName

- The name of the dimension.  Needed since the persistent graphs and 
tables for this dimension will be distinguished on disk by name.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    my ($s) = @_;
    my $myDatabase = new Database($IncompleteDataCube::Constants::databaseName,
                              $IncompleteDataCube::Constants::databaseMode,
                              $IncompleteDataCube::Constants::idTableName,
                              $IncompleteDataCube::Constants::stringTableName);

    $self->{'myDatabase'} = $myDatabase;
    $self->{'finerUnitGraph'} = 
      new Persistent::Graph($myDatabase, 
             $IncompleteDataCube::Constants::finerUnitGraphName . $s);
    $self->{'coarserUnitGraph'} = 
      new Persistent::Graph(myDatabase, 
             $IncompleteDataCube::Constants::coarserUnitGraphName . $s);
    $self->{'finerMeasureGraph'} = 
      new Persistent::Graph(myDatabase, 
             $IncompleteDataCube::Constants::finerMeasureGraphName . $s);
    $self->{'coarserMeasureGraph'} = new Persistent::Graph(myDatabase, 
             $IncompleteDataCube::Constants::coarserMeasureGraphName . $s);
    $self->{'unitToMeasureTable'} = $myDatabase->createTable(
             $IncompleteDataCube::Constants::unitToMeasureTableName . $s);
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 close()

Close and write all the persistent variables are written to disk.  This
should be called prior to exiting, but even if
it not called, in theory, all the persistent variables should be
maintained since Perl will clean them up nicely for us by doing
the appropriate unties.

=cut

#------------------------------------------------------------------------
  sub close {
    my $self = shift;
    my $myDatabase = $self->{'myDatabase'};
    $myDatabase->close();
    }
#------------------------------------------------------------------------


=head2 save()

Write all the persistent variables are written to disk.

=cut

#------------------------------------------------------------------------
  sub save {
    my $self = shift;
    my $myDatabase = $self->{'myDatabase'};
    $myDatabase->save();
    }
#------------------------------------------------------------------------

1;
