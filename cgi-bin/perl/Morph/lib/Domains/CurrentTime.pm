package CurrentTime;

  use strict;
  use Carp;

  my $Time;

=head1 NAME CurrentTime

=head1 DESCRIPTION

This class represents the Current Time as of the start of a query.

=head1 METHODS

=head2 _currentTime()

Compute the Current Time;

=cut

#---------------------------------------------------------------------
  sub time {
    $Time = &_currentTime() unless defined $Time;
    return $Time;
    }
#---------------------------------------------------------------------

#---------------------------------------------------------------------
  sub _currentTime {
    my $now = localtime;  # e.g., "Thu Oct 13 04:54:34 1994"
    #$now =~ /^\w+\s(\w+)\s(\d+)\s\d+\Q:\E\d+\Q:\E\d+\s(\d+)/;
    #print join("z", split(" ", $now));
    #$now =~ /\w+ (\w+ 4)/;
    # print "now is $1" . "z<br>";
     #print "now is $2/$1/$3<br>";
    #return TimePoint->new("$2/$1/$3")->{'time'};
     my @temp = split(" ", $now);
    my $s = $temp[2] . '/' . $temp[1] . '/' . $temp[4];
    return TimePoint->new($s)->{'time'};
    }
#---------------------------------------------------------------------

1;
