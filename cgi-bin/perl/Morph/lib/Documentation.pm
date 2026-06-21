
=head1 NAME Documentation

This package includes all the documentation associated with the project.

=head1 DESCRIPTION  

It has many parts.

=over 4

=item * 

L<Documentation::Overview>
- An overview of the project.

=item * 

L<Documentation::Licence> 
- The licence for the code.

=item * 

L<Documentation::Version> 
- The version number for this release.

=item * 

The L<loadDB> command
- Evaluate a sequence of INSERT statements

=item * 

The L<evaluateQuery> command
- Evaluate a SELECT statement (and default SETs).

=item * 

L<SemiStructuredDB>
- The semistructured database classes.

=item * 

L<Dimensions>
- Which dimensions are available.

=item * 

L<OODatabase>
- A DBM based OO database.  Used to store the persistent objects.

=item * 

L<ErrorHandler>
- The error handler

=item * 

L<ExpressionTree>
- The expression tree

=item * 

L<Evaluator::Select>
- The query evaluation engine for the select statement

=item * 

L<Domains>
- The domains of labels.

=back 

Copyright &copy 1998 Curtis E. Dyreson. All rights reserved.

=cut

require Documentation::Overview;
require Documentation::Licence;
require Documentation::Version;

1;
