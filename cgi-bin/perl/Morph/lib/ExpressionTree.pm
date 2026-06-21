package ExpressionTree;

=head1 NAME ExpressionTree.pm

A class for an expression tree.

=head1 DESCRIPTION

An expression tree is an important thing.

=over 4

=item * 

L<ExpressionTree::Variable> 
- a variable node

=item * 

L<ExpressionTree::Operand> 
- an operand in an operation

=item * 

L<ExpressionTree::Value> 
- an extract value operation

=item * 

L<ExpressionTree::Operations> 
- expression tree operations

=item * 

L<ExpressionTree::RegExp> 
- regular expressions

=back 

=cut

use ExpressionTree::Operand;
use ExpressionTree::Variable;
use ExpressionTree::Value;
use ExpressionTree::Operations;
use ExpressionTree::RegExp;

1;
