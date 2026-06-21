
=head1 NAME BigDB_File

A class that ties a hash table to two DBM files so that there
is no length problem.

=head1 SYNOPSIS

    use BigDB_File;
    tie %dbm, BigDB_File, $fileName, $mode;
    # Use the %dbm array.
    untie %dbm;

=head1 DESCRIPTION

B<BigDB_File> is a module which allows a dbm database to have no
length limitations.

=cut

package BigDB_File;

 use strict;
 use Carp;
 use Fcntl;

=head1 TIEHASH

Ties the big dbm file.

=cut

my $DEBUG = 0;
sub new { &TIEHASH(@_); }

sub TIEHASH {
  my $type = shift;
  my ($fileName, $mode) = @_;
  my $self = {};
  my (%tempdbcntrl, %tempdb);

  print "open $fileName\n" if $DEBUG;
  dbmopen(%tempdb, $fileName. 'data', $mode) || 
    croak "could not open $fileName" ;
  dbmopen(%tempdbcntrl, $fileName. 'cntrl', $mode) || 
                               croak "could not open $fileName cntrl" ;
  $self->{'fileName'} = $fileName;
  $self->{'dbm'} = \%tempdb;
  $self->{'cntrl'} = \%tempdbcntrl;
  bless $self, $type;
}
 
sub CLEAR {
  my ($self) = @_;
  my ($dbm) = $self->{'dbm'};
  my ($dbmcntrl) = $self->{'cntrl'};
  %$dbm->CLEAR;
  %$dbmcntrl->CLEAR;
}
 
# Return a list of all the keys
sub KEYS {
  my ($self) = @_;
  my ($dbmcntrl) = $self->{'cntrl'};
  return keys %$dbmcntrl;
}
 
# Store a value for a particular key
sub STORE {
  my ($self, $dbkey, $dbtext) = @_;
  my ($dbm) = $self->{'dbm'};
  my ($dbmcntrl) = $self->{'cntrl'};
  my ($i, $increment, $next, $previous);
  my ($fileName) = $self->{'fileName'} if $DEBUG;

  print "storing $fileName $dbkey $dbtext\n" if $DEBUG;
  # 1014 is a magic number, but it is the PROCBLKSIZE with room to spare.
  # I'd use 1024, which is what the constant is defined, as but it gives
  # me an error when I do so. What should magic number be?
  # Don't write more than 40,000 characters!
  if (length($dbtext) > 40000) {
    $dbtext = substr($dbtext, 1, 300) . 
              "\n\n\n...\n\n\n" . 
              substr($dbtext, -300, 300);
    }
  $increment = 1014 - length($dbkey);
  $i = 1;
  $previous = 0;
  while ($previous <= length($dbtext)) {
    $next = $i * $increment;
    $$dbm{"$dbkey.$i"} = substr($dbtext,$previous,$increment);
    $previous = $next;
    $i++;
    }
  $i--;
  $$dbmcntrl{$dbkey} = join("\f", (1..$i));
}
 
sub NEXTKEY { 
  my ($self) = @_;
  my ($dbmcntrl) = $self->{'cntrl'};
  return each %$dbmcntrl;
}
 
sub FIRSTKEY { 
  my ($self) = @_;
  my ($dbmcntrl) = $self->{'cntrl'};
  return each %$dbmcntrl;
}
 
sub FETCH {
  my ($self, $dbkey) = @_;
  my ($dbm) = $self->{'dbm'};
  my ($dbmcntrl) = $self->{'cntrl'};
  my ($msg, @segments, $seg);
  my ($fileName) = $self->{'fileName'} if $DEBUG;

  print "fetch $fileName $dbkey\n" if $DEBUG;
  return $$dbmcntrl{$dbkey} unless defined $$dbmcntrl{$dbkey};
  @segments = split("\f",$$dbmcntrl{$dbkey});
  $msg = '';
  foreach $seg (@segments) {
    print "fetching $dbkey.$seg\n" if $DEBUG;
    $msg .= $$dbm{"$dbkey.$seg"};
    }
  return $msg;
}
 
sub DELETE { 
  my ($self, $dbkey) = @_;
  my ($dbm) = $self->{'dbm'};
  my ($dbmcntrl) = $self->{'cntrl'};
  my (@segments, $seg);

  @segments = split("\f",$$dbmcntrl{$dbkey});
  foreach $seg (@segments) {
    delete $$dbm{"$dbkey.$seg"};
    }
  delete $$dbmcntrl{"$dbkey"};
  }
 
sub EXISTS { 
  my ($self, $key) = @_;
  my ($dbmcntrl) = $self->{'cntrl'};
  my ($fileName) = $self->{'fileName'} if $DEBUG;

  print "exists $fileName $key\n" if $DEBUG;
  return defined $$dbmcntrl{$key};
  }
 
sub DESTROY {
  my ($self) = @_;
  my ($dbm) = $self->{'dbm'};
  my ($dbmcntrl) = $self->{'cntrl'};
  my ($fileName) = $self->{'fileName'} if $DEBUG;

  print "destroying $fileName $dbm $dbmcntrl\n" if $DEBUG;
  $dbm->DESTROY;
  $dbmcntrl->DESTROY;
  #untie %$dbm;
  #untie %$dbmcntrl;
  #dbmclose(%$dbm) || die 'could not close dbm';
  #dbmclose(%$dbmcntrl) || die 'could not close dbmcntrl';
  print "done destroyig\n" if $DEBUG;

  undef %{ $self->{'dbm'} };
  undef %{ $self->{'dbmcntrl'} };
}
 
1;
