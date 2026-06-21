# ****************************************************************
# This package gets around the 1024 byte field limitation in DBM without
# having to recompile with a new PROCBLKSIZE
# Idea by Curtis Dyreson
# OO design by Michael Sharpe
# OO redesign by Curtis Dyreson
# You are welcome to send bug reports, fixes, comments, questions or 
# suggestions to Curtis Dyreson at curtis@cs.jcu.edu.au, but as I hope
# to have a real life someday I can't promise a swift reply.
#
# Copyright (c) 1996. All rights reserved.  This software is covered
# under the general license and lack of warranty as stated in the 
# installation kit. WARNING! DANGER WILL ROBINSON! USE AT YOUR OWN RISK!

package Bdbm;

#### Method: new
# Creates a new Object. 
# Returns the created or opened Object
####
sub new {
  my $type = shift;
  my $self = {};
  $self->{'class'} = $type;
  bless $self, $type;
}

sub open{
  my ($self, $dbmname, $dbmmode) = @_;
  local (*tempdbcntrl, *tempdb);

  dbmopen(%tempdb, $dbmname, $dbmmode) || die "could not open $dbmname" ;
  dbmopen(%tempdbcntrl, $dbmname . "cntrl", $dbmmode) || 
                               die "could not open $dbmname cntrl" ;
  $self->{'dbm'} = *tempdb;
  $self->{'cntrl'} = *tempdbcntrl;
}

sub write {
  my ($self, $dbkey, $dbtext) = @_;
  local (*dbm) = $self->{'dbm'};
  local (*dbmcntrl) = $self->{'cntrl'};
  my ($i, $increment, $next, $previous);

  # 1014 is a magic number, but it is the PROCBLKSIZE with room to spare.
  # I'd use 1024, which is what the constant is defined, as but it gives
  # me an error when I do so. What should magic number be?
  # Don't write more than 40,000 characters!
  if (length($dbtext) > 40000) {
    $dbtext = substr($dbtext, 1, 300) . "\n\n\n...\n\n\n" . substr($dbtext, -300, 300);
    }
  $increment = 1014 - length($dbkey);
  $i = 1;
  $previous = 0;
  while ($previous < length($dbtext)) {
    $next = $i * $increment;
    $dbm{"$dbkey.$i"} = substr($dbtext,$previous,$increment);
    $previous = $next;
    $i++;
    }
  $dbmcntrl{$dbkey} = join("\f", (1..$i));
}

sub clear {
  my ($self) = @_;
  local (*dbm) = $self->{'dbm'};
  local (*dbmcntrl) = $self->{'cntrl'};
  my ($key);

  foreach $key (keys %dbmcntrl) {
    delete $dbmcntrl{$key};
    }

  foreach $key (keys %dbm) {
    delete $dbm{$key};
    }
}

sub keys {
  my ($self) = @_;
  local (*dbmcntrl) = $self->{'cntrl'};
  return keys %dbmcntrl;
  }

sub close {
  my ($self) = @_;
  local (*dbm) = $self->{'dbm'};
  local (*dbmcntrl) = $self->{'cntrl'};

  dbmclose(%dbm) || return '';
  dbmclose(%dbmcntrl) || return '';
}

sub read {
  my ($self, $dbkey) = @_;
  local (*dbm) = $self->{'dbm'};
  local (*dbmcntrl) = $self->{'cntrl'};
  local ($msg, @segments, $seg);

  @segments = split("\f",$dbmcntrl{$dbkey});
  $msg = "";
  foreach $seg (@segments) {
    $msg .= $dbm{"$dbkey.$seg"};
    }
  return $msg;
}

sub import {
  1;
}

1;
