
=head1 NAME SemiStructedDB.pm

This class is the configuration for the SemistructuredDB.

=head1 DESCRIPTION

This package has many pieces.

=over 4

=item * 

L<SemiStructuredDB::Required>
- A Required property class.

=item * 

L<SemiStructuredDB::Region>
- A region in property space

=item * 

L<SemiStructuredDB::Label>
- An edge label

=item * 

L<SemiStructuredDB::Edge>
- An edge

=item * 

L<SemiStructuredDB::Node>
- A node

=item * 

L<SemiStructuredDB::EdgeList>
- An edge list

=item * 

L<SemiStructuredDB::Path>
- A sequence of edges

=item * 

L<SemiStructuredDB::LabelledGraph>
- A persistent graph class.  The graph is the semistructure.

=item * 

L<SemiStructuredDB::Globals>
- Global information for the semistructure.

=item * 

L<SemiStructuredDB::Constants>
- Constant information for the semistructure.

=back 

=cut

require Dimensions;
require OODatabase;
require SemiStructuredDB::Required;
require Domains;
require SemiStructuredDB::Region;
require SemiStructuredDB::Label;
require SemiStructuredDB::Edge;
require SemiStructuredDB::Node;
require SemiStructuredDB::EdgeList;
require SemiStructuredDB::Path;
require SemiStructuredDB::Constants;
require SemiStructuredDB::Globals;
require SemiStructuredDB::LabelledGraph;

1;
