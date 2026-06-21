#!/usr/local/bin/perl -w -I../..:/pack/perl-5.004/src/perl5.004_01/ext/GDBM_File

use database::DBMConfig;

#$me = new Id(23, 24, 25);
#print $me->toBytes() . "\n";
#$me = new IdList(new Id(23), new Id(24), new Id(25));
#print $me->toBytes() . "\n";
$me = new IdSet(new Id(0));
print $me->toBytes() . "\n";
$db = new Database(".");
$table = $db->createTable("temp");
$table->insertTuple(new Tuple(new Id(1), $me));
$tuple = $table->retrieveTuple(new Id(1));
print $tuple->getValueAsBytes();
$me->insert(new Id(1));
print $me->toBytes() . "\n";
