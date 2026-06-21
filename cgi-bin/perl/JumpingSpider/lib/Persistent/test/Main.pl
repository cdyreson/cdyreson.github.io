#!/usr/local/bin/perl -w -I../..

use persistent::persistent;

    $db = new Database(".");
    $graph = new PersistentGraph($db, "edges");
    $graph->addEdge(new Id(1), new Id(2));
    $graph->addEdge(new Id(2), new Id(3));
    $graph->addEdge(new Id(1), new Id(4));
    $r = $graph->reachableSet(new Id(1));
    print $r->numberOfElements() . "\n";
    $r = $graph->reachableSet(new Id(2));
    print $r->numberOfElements() . "\n";
    $r = $graph->reachableSet(new Id(3));
    print $r->numberOfElements() . "\n";
    $r = $graph->reachableSet(new Id(4));
    print $r->numberOfElements() . "\n";
