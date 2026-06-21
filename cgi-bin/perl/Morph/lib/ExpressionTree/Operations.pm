package ExpressionTree::Operations;

=head1 NAME ExpressionTree::Operations

All the operations in an expression tree.

=head1 DESCRIPTION

Below are listed the supported operations.

=over 4

=item * 

L<Operations::Comparison> 
- comparison operations

=item * 

L<Operations::Arithmetic> 
- arithemetic operations

=item * 

L<Operations::Logical> 
- logical (boolean) operations

=item * 

L<Operations::Aggregate> 
- aggregate operations

=item * 

L<Operations::Match> 
- a MATCH operation

=item * 

L<Operations::Slice> 
- a SLICE operation

=item * 

L<Operations::Coalesce> 
- a COALESCE operation

=item * 

L<Operations::Collapse> 
- a COLLAPSE operation

=item * 

L<Operations::Nodes> 
- a NODES operation

=item * 

L<Operations::Dimension> 
- a DIMENSION operation

=item * 

L<Operations::Null> 
- a NULL operation

=item * 

L<Operations::NonNull> 
- a NONNULL operation

=back 

Copyright &copy 1998 Curtis E. Dyreson. All rights reserved.

=cut

use ExpressionTree::Operations::Comparison;
use ExpressionTree::Operations::Arithmetic;
use ExpressionTree::Operations::Logical;
use ExpressionTree::Operations::Aggregate;
use ExpressionTree::Operations::Match;
use ExpressionTree::Operations::Slice;
use ExpressionTree::Operations::Coalesce;
use ExpressionTree::Operations::Collapse;
use ExpressionTree::Operations::Nodes;
use ExpressionTree::Operations::Dimension;
use ExpressionTree::Operations::Null;
use ExpressionTree::Operations::NonNull;

1;
