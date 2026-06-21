package ExpressionTree::RegExp;

=head1 NAME ExpressionTree::RegExp

Regular expressions.

=head1 DESCRIPTION

Includes the following regular expression components.

=over 4

=item * 

L<Token>
- Match a single descriptor in a regular expression

=item * 

L<Composition>
- Composition in a regular expression

=item * 

L<KleeneClosure>
- Kleene closure in a regular expression

=item * 

L<OptionalToken>
- The question mark operator in a regular expression

=back 

Copyright &copy 1998 Curtis E. Dyreson. All rights reserved.

=cut

use ExpressionTree::RegExp::Token;
use ExpressionTree::RegExp::Composition;
use ExpressionTree::RegExp::KleeneClosure;
use ExpressionTree::RegExp::OptionalToken;

1;
