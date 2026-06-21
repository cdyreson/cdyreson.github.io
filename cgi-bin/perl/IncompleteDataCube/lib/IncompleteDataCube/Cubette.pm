package IncompleteDataCube::Cubette;

=head1 NAME IncompleteDataCube::Cubette.pm

This class implements a complete sub-cube within the cube.

=head1 DESCRIPTION

It has several parts 

=over 4

=item * 

L<Cubette::Cubette> 
- The top-level interface to a complete sub-cube within the 
incomplete data cube.

=item * 

L<Cubette::CubetteSpecification>
- A Cubette Specification is a list of units and measure.

=item * 

L<Cubette::LogRecord>
- A class to iterate over a log file and count instances of particular
records that are filtered through the cubette specifications.

=item * 

L<Cubette::QueryCondition>
- A class that represents the degree of satisfaction for a query.

=back 

For more information on the cube see the 
<a href="IncompleteDataCube/Overview.html">Overview</a>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the 
<a href="IncompleteDataCube/License.html">license</a>
and 
<a href="IncompleteDataCube/License.html">version</a>.

=cut

require IncompleteDataCube::Cubette::Cubette;
require IncompleteDataCube::Cubette::CubetteSpecification;
require IncompleteDataCube::Cubette::LogRecord;
require IncompleteDataCube::Cubette::QueryCondition;

1;
