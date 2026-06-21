package QueryCondition;

=head1 NAME QueryCondition

This class represents the satisfaction condition for a query.
A query can be partially satisfied.

This package is part of L<cube::cubette>.
For more information on the cube see the L<cube::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<cube::Licence> and L<cube::Version>.

=head1 DESCRIPTION

When a query is evaluated, the cubettes are tested to determine
if they satisfy the query.  This class records how fully the
query is satisfied since it may be partially satisfied.

=head1 METHODS

=head2 new($dimensions, $unitsMatched, $measuresMatched)

=over 4

=item * $dimensions

- The number of dimensions (yeah, sure it is a global constant too!)

=item * $unitsMatched

- How many units were matched.

=item * $measuresMatched

- How many measures where matched.

=back

Create a query satisfacation object.

History

=over 4

=item * Nov 14, 1997

- Born.

=back

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    my ($dimensions, $unitsMatched, $measuresMatched) = @_;
    $self->{'dimensions'} = $dimensions;
    $self->{'unitsMatched'} = $unitsMatched;
    $self->{'measuresMatched'} = $measuresMatched;
    bless $self, $type;
   }
#---------------------------------------------------------------------

=head2 fullySatisfied()

Was the query fully satisfied by this cubette?

=cut

#---------------------------------------------------------------------
  sub fullySatisfied {
    my ($self) = @_;

    return 0 unless $self->{'dimensions'} == $self->{'unitsMatched'};
    return 0 unless $self->{'dimensions'} == $self->{'measuresMatched'};
    return 1;
    }
#---------------------------------------------------------------------

=head2 partiallySatisfied()

Was the query partially satisfied by this cubette?

=cut

#---------------------------------------------------------------------
  sub partiallySatisfied {
    my ($self) = @_;

    return 0 unless $self->{'dimensions'} == $self->{'unitsMatched'};
    return 1 if $self->{'measuresMatched'} > 0;
    return 0;
    }
#---------------------------------------------------------------------


=head2 rank()

Rank how well the query is partially satisfied.

=cut

#---------------------------------------------------------------------
  sub rank {
    my ($self) = @_;

    return ($self->{'unitsMatched'} * $self->{'dimensions'}) + 
           $self->{'measuresMatched'};
    }
#---------------------------------------------------------------------

1;
