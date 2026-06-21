package Domains;

=head1 NAME Domains.pm

This package includes all the basic domains.
See also L<OODatabase> and L<SemiStructuredDB>.

=head1 DESCRIPTION

The following domains are available.

=over 4

=item * 

L<Domains::Boolean> 
- A three-valued boolean.

=item * 

L<Domains::Integer> 
- Integers.

=item * 

L<Domains::String> 
- Strings.

=item * 

L<Domains::TimePoint> 
- A point in time.

=item * 

L<Domains::CurrentTime> 
- Special routines to figure out the current time.

=item * 

L<Domains::TimeInterval> 
- An interval in time.

=item * 

L<Domains::CNF> 
- Conjunctive Normal From stuff, for security domains.

=item * 

L<Domains::Quality> 
- A quality (low to high).

=item * 

L<Domains::Dollar> 
- A dallar amount.

=item * 

L<Domains::Null> 
- A null value.

=back 

Copyright &copy 1998 Curtis E. Dyreson. All rights reserved.

=cut

use Domains::Boolean;
use Domains::String;
use Domains::Integer;
use Domains::TimePoint;
use Domains::CurrentTime;
use Domains::TimeInterval;
use Domains::CNF;
use Domains::Quality;
use Domains::Dollar;
use Domains::Null;

1;
