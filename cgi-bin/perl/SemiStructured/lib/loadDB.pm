#!/coll/local/bin/perl -Ilib -w

#-----------------------------------------------------------------------

=head1 NAME

B<loadDB> - Create the SemiS database from a text file
specification.

=head1 SYNOPSIS

  loadDB
    [-help] |
    [-version] |
    [-verbose] 
    [-databaseName databaseName] 
    [-databaseMode GDBM | DBM | BSD] 
    < CompanyDB

=head1 DESCRIPTION

B<loadDB> reads the text file representation of a 
semi-structured DB and creates the necessary DB.

=head1 EXAMPLE USAGE

Create the specifications.

 perl -I../lib loadDB -verbose CompanyDB 

See also L<SemiStructuredDB>.

=cut

#-----------------------------------------------------------------------

use strict;
require 5.002;

use Getopt::Long;
use English;
use Carp;
use SemiStructuredDB;
use LoadDB::Global;
use Parser::LoadDB;

#-----------------------------------------------------------------------

=head1 OPTIONS

=over 4

=item -help

Display a short help message with a reminder of supported
command-line options.

=item -version

Display the version of B<loadDB>.

=item -verbose

Enable verbose reporting.

=item -databaseName databaseName

The name of the database, overides the default name 
in L<SemiStructuredDB::Constants>.

=item -databaseMode databaseMode

The mode of the database, overides the default mode
in L<SemiStructuredDB::Constants>.

=back

=cut

#-----------------------------------------------------------------------

my $VERSION       = '1.00';
my $SHOW_VERSION  = 0;
my $VERBOSE       = 0;
my $TRACE         = 0;
my $HELP          = 0;
my $COMMAND_NAME  = 'loadDB';
my $INPUT_FILE = '';

#---------------------------------------------------------------------

=head1 NAME Main
 
Parse the text file and create the SemiS DB.
 
=head1 DESCRIPTION
 
This is the parser for the specification file.  It is a top-down
recursive descent parser.
 
=cut
 
#-----------------------------------------------------------------------
# Parse the command line
#-----------------------------------------------------------------------
  &ParseCommandLine();
  my $globals = new SemiStructuredDB::Globals();
  $Global::SSGraph = $globals->{'SSGraph'};
  my $parser = new Parser::LoadDB();
  $parser->Run();
  #&dump('&root', '', '');
  
  print "alldone!\n" if $VERBOSE;
  #foreach (keys %Global::Paths) { print "$_\n"; }

#-----------------------------------------------------------------------
# Dump the paths
#   No longer works, must fix the Globals
#-----------------------------------------------------------------------
sub dump {
  my ($current,$prefix,$cleanPrefix) = @_;
  die "Undefined object $current" unless defined $Global::Values{$current};
  die "Undefined object $current" unless defined $Global::Objects{$current};
  my $values = $Global::Values{$current};
  my $objects = $Global::Objects{$current};
  foreach (@$objects) {
    my ($edge, $rest) = @$_;
    #print "$prefix.$current.$edge\n";
    if ($current eq '&root') {
      &dump ($rest, "$edge", "$edge");
      }
    else {
      $Global::Paths{"$cleanPrefix.$edge"} = 1;
      &dump ($rest, "$prefix.$current.$edge", "$cleanPrefix.$edge");
      }
    }
  foreach (@$values) {
    my ($edge, $rest) = @$_;
    $Global::Paths{"$cleanPrefix.$edge"} = 1;
    print "$prefix.$current.$edge.$rest\n";
    }

  }

#------------------------------------------------------------------------
# ParseCommandLine() - handle command line
#------------------------------------------------------------------------
sub ParseCommandLine {
  my @switches = (
    'databaseMode=s', \$SemiStructuredDB::Constants::databaseMode,
    'databaseName=s', \$SemiStructuredDB::Constants::databaseName,
    'help',           \$HELP,
    'verbose',        \$VERBOSE,
    'version',        \$SHOW_VERSION,
    );

  &GetOptions(@switches) || die "use -help switch to display brief help\n";

  if ($SHOW_VERSION) {
    print "This is $COMMAND_NAME, version $VERSION\n";
    exit 0;
    }

  if ($HELP) {
    print <<HelpEnd;
    $COMMAND_NAME, v$VERSION - parse the Incomplete Data Cube specifications

    Usage: $COMMAND_NAME 
                         [-help] |
                         [-version] |
                         [-verbose]
                         [-databaseName databaseName]
                         [-databaseMode GDBM | DBM | BSD]
                         < inputFile

        -help            : display this message
        -verbose         : display verbose information as running
        -databaseName name : name of the database
        -databaseMode mode : mode for the database 

HelpEnd
    exit 0;
    }

}

