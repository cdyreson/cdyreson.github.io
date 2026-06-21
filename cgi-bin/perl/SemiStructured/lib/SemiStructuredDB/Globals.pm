package SemiStructuredDB::Globals;

=head1 NAME SemiStructuredDB::Globals

The global state in the semistructured db.  

=head1 DESCRIPTION

Below is the list of instance variables in the global object.

=over 4

=item * SSGraph

- A L<LabelledGraph>.  
The SSGraph is the raw semistructured graph.

=back

=head1 METHODS

=head2 new()

Allocate and intialize the necessary variables.

=cut

#------------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    my ($s) = @_;
    my $myDatabase = new Database($SemiStructuredDB::Constants::databaseName,
                              $SemiStructuredDB::Constants::databaseMode);
    $self->{'myDatabase'} = $myDatabase;
    $self->{'SSGraph'} = new LabelledGraph($myDatabase, 
                                 $SemiStructuredDB::Constants::SSGraphName);
    bless $self, $type;
    }
#------------------------------------------------------------------------

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
