package OODatabase::Overview;

=pod

This is a simple database to store objects.
Any object can be stored, as long as that object inherits the
L<OODatabase::Persistent> class.
That class provides methods to swizzle and unswizzle objects.

The OODatabase uses DBM files to store the objects.
There is one DBM file for each L<OODatabase::Table>.

=cut

1;
