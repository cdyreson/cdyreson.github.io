
=head1 NAME IncompleteDataCube.pm

This class is the configuration for the entire incomplete data cube.

=head1 DESCRIPTION

This package needs two other pieces.

=over 4

=item * 

L<DBMDatabase>
- A DBM based database.  Used to store the cube information.

=item * 

L<Persistent::Graph>
- A persistent graph class.  The graph maintains information about the
hierarchy of units and measures in the cube.

=back 

This package has several parts.

=over 4

=item * 

L<IncompleteDataCube::Cubette> 
- A cubette is a complete sub-cube within the incomplete data cube.

=item * 

L<IncompleteDataCube::Globals>
- Global constants to configure the cube.

=item * 

L<IncompleteDataCube::Constants>
- The global constants, EDIT THIS TO CONFIGURE THE CUBE.

=item * 

L<IncompleteDataCube::Dimension>
- A class to create the global variables for a new dimension, 
a helper class for L<IncompleteDataCube::Globals>.

=back 

For more information on the cube see the 
L<IncompleteDataCube::Overview>.
Please be aware of the 
L<IncompleteDataCube::Licence>
and 
L<IncompleteDataCube::Version>.

=for html
<br>
Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.

=cut

require DBMDatabase;
require Persistent::Graph;
require IncompleteDataCube::Constants;
require IncompleteDataCube::Dimension;
require IncompleteDataCube::Globals;
require IncompleteDataCube::Cubette;

1;
