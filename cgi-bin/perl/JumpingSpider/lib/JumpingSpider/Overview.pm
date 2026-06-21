package JumpingSpider::Overview;

=pod

=head1 Overview

Several database query languages have recently been developed 
to locate and retrieve documents in the vast network of 
World-Wide Web pages.  These languages combine <i>path expressions</i>, 
which specify the structure of a path through the network to the 
desired information, with <i>content predicates</i>, which 
force the path to pass through pages with particular content.
The straightforward implementation of these languages 
is based on breadth-first search of the network, with heavy 
reliance placed on the user's understanding of network topology 
to both direct and constrain the search via the appropriate use 
of the path expressions.

In this paper we describe a system that removes the reliance on path 
expressions to safeguard the search during a query and enables the 
user to navigate by refining <i>content</i> rather than by specifying 
<i>structure</i>.  Our system uses a cost-constrained model for query 
evaluation.  Links between pages are assigned costs.  The user 
controls how far a query can navigate by specifying a permissible
cost for each path.  Only paths that are within the user-given cost 
are navigated during query evaluation.

We show how to implement content-based navigation by assigning
a low cost to links that lead from more general to more specialized 
content and high cost to links that lead from more specialized to 
more general content.  Our implementation has an efficient, scalable 
architecture which requires no changes to HTTP servers, and utilizes 
standard relational database technology to evaluate a query.  

=cut

1;
