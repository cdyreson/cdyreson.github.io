package Convert;

use Carp;

=head1 NAME Convert

This package is part of L<DBMDatabase>.
For more information on the database see the L<DBMDatabase::Overview>.

Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
Please be aware of the L<DBMDatabase::Licence> and L<DBMDatabase::Version>.

=head1 DESCRIPTION

This class provides conversion routines, for use in the
L<DBMDatabase::Database>.  For efficiency, strings are mapped to
L<DBMDatabase::Id>s.  That mapping is done by the conversion routines
in this class.

=head1 PUBLIC METHODS

=cut

#---------------------------------------------------------------------
# Private Instance Variables
 my $initialized = 0;
 my $StringToIdTable = "";
 my $IdToStringTable = "";
 my $tokenCount = new Id(0);
#---------------------------------------------------------------------

=head2 new(string $databaseName)

=over 4

=item * string $databaseName

- The name of the database that stores the tools database.  It is
probably not really needed since the database name is a global
L<globals::Constants>.

=back

Initialize the conversion tools

=cut

#---------------------------------------------------------------------
  sub initialize {
    my ($idTable, $stringTable) = @_;
    $initialized = 1;
    $StringToIdTable = $idTable;
    $IdToStringTable = $stringTable;
    my $r = new StringCol("_tokenCount");
    my $t = $StringToIdTable->retrieveTuple($r);
    if (!$t) {$tokenCount = &_stringToIdMapping("_tokenCount");}
    else {$tokenCount = $t->getValueAsId();}
    }
#---------------------------------------------------------------------

=head2 toString(Id $key)

=over 4

=item * Id $key

- The L<DBMDatabase::Id> to convert.

=back

Convert an L<DBMDatabase::Id> to a String by doing a table lookup.
This returns the string.

=cut

#---------------------------------------------------------------------
  sub toString {
    my ($key) = @_;
    croak 'Convert not initialized!' unless $initialized;
    my $t = $IdToStringTable->retrieveTuple($key);
    return '' unless $t;
    return $t->getValueAsString();
    }
#---------------------------------------------------------------------

=head2 toId(string $key)

=over 4

=item * string $key

- The string to convert.

=back

Convert a string to a L<DBMDatabase::Id> by doing a table lookup.
This returns the L<DBMDatabase::Id>.

=cut

#---------------------------------------------------------------------
  sub toId {
    my ($key) = @_;
    croak 'Convert not initialized!' unless $initialized;
    my $r = new StringCol($key);
    my $t = $StringToIdTable->retrieveTuple($r);
    return &_stringToIdMapping($key) unless $t;
    return $t->getValueAsId();
    }
#---------------------------------------------------------------------

=head2 save()

Close the mapping tables (is this needed?) Deprecated...

=cut

#---------------------------------------------------------------------
  sub save {
    if ($StringToIdTable) {$StringToIdTable->save();}
    if ($IdToStringTable) {$IdToStringTable->save();}
    }
#---------------------------------------------------------------------

=head1 PRIVATE METHODS

=head2 _stringToIdMapping(string $s)

=over 4

=item * string $s

- The string to convert.

=back

Add a new mapping to the string to Id table.
This returns an L<DBMDatabase::Id>.

=cut

#---------------------------------------------------------------------
  sub _stringToIdMapping {
    my ($key) = @_;
    croak 'Convert not initialized!' unless $initialized;
    my ($t1) = new Tuple(new StringCol($key), $tokenCount);
    $StringToIdTable->insertTuple($t1);
    my ($t2) = new Tuple($tokenCount, new StringCol($key));
    $IdToStringTable->insertTuple($t2);
    $tokenCount->increment();
    my ($t3) = new Tuple(new StringCol('_tokenCount'), $tokenCount);
    $StringToIdTable->insertTuple($t3);
    return $t1->getValueAsId();
    }
#---------------------------------------------------------------------

1;
