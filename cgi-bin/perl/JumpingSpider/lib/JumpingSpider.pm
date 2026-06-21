package JumpingSpider;

=head1 NAME JumpingSpider.pm

This package includes all the things required for the Jumping Spider.
For more see L<JumpingSpider::Overview> and L<JumpingSpider::Licence>.

=head1 DESCRIPTION

Included are several parts of the Jumping Spider.

=over 4

=item * 

L<JumpingSpider::Query> 
- Utilities useful in Querying.

=item * 

L<JumpingSpider::WWWGraph> 
- Utilities useful in building the WWWGraph.

=item * 

L<JumpingSpider::Globals> 
- All the database tables needed.

=item * 

L<DBMDatabase> 
- The DBMDatabase, a simple kind of database based on DBM files.

=item * 

L<Persistent::PersistentGraph> 
- A persistent graph.

=item * 

L<JumpingSpider::Constants> 
- All the constants needed.

=item * 

L<JumpingSpider::TitleTable> 
- Useful title table utilities.

=back 

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.

=cut

use DBMDatabase;
use Persistent::Graph;
use JumpingSpider::Version;
use JumpingSpider::Overview;
use JumpingSpider::Licence;
use JumpingSpider::Constants;
use JumpingSpider::Globals;
use JumpingSpider::WWWGraph;
use JumpingSpider::TitleTable;
use JumpingSpider::Query;

1;
