package Dimensions;

=head1 NAME Dimensions

This class encapsulates the conversions from literals to dimension values.

=head1 DESCRIPTION

The semistructure has various associated property space dimensions,
which are defined over different domains.
This package configures those domains by providing a method to
convert constants in that domain to the appropriate type, e.g., 
L<Domains::Integer>.
See L<Domains> for the supported types.

=head1 METHODS

=head2 convert(string dimension, string value)

=over 4

=item * string dimension

- The name of the dimension.

=item * string value

- The string value to convert.

=back

Creates a new value of the appropriate domain type from the passed string.
Dies if there is no such dimension!
This should be patched to an appropriate validation check.

=cut

#---------------------------------------------------------------------
  sub convert {
    my ($dimension, $value) = @_;

    return new String($value) if ($dimension eq 'String');
    return new Integer($value) if ($dimension eq 'Integer');
    return new TimeInterval($value) if ($dimension eq 'TimeInterval');
    return new String($value) if ($dimension eq 'name');
    return new TimeInterval($value) if ($dimension eq 'transaction_time');
    return new TimeInterval($value) if ($dimension eq 'valid_time');
    return new CNF($value) if ($dimension eq 'security');
    return new Quality($value) if ($dimension eq 'quality');
    return new Dollar($value) if ($dimension eq 'price');
    die "tried to convert unknown dimension $dimension with value $value\n";
    }
#---------------------------------------------------------------------

1;
