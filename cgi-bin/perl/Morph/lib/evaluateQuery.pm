#!/coll/local/bin/perl -Ilib:../lib -w

#-----------------------------------------------------------------------

=head1 NAME

B<evaluateQuery> - Do a SemiS query.

=head1 SYNOPSIS

  evaluateQuery 
    [-help] |
    [-version] |
    [-verbose] 
    [-databaseName databaseName] 
    [-databaseMode GDBM | DBM | BSD] 
    < query

=head1 DESCRIPTION

B<evaluateQuery> reads a query from standard input and evaluates it.

=head1 EXAMPLE USAGE

Do a query

 perl -I../lib evaluateQuery -verbose <specifications/query

See also L<SemiStructuredDB>.

=cut

#-----------------------------------------------------------------------

use strict;
require 5.002;

use Getopt::Long;
use English;
use SemiStructuredDB;
use ExpressionTree;
use Parser::Select;
require Evaluator::Select;
use ErrorHandler;

#-----------------------------------------------------------------------

=head1 OPTIONS

=over 4

=item -help

Display a short help message with a reminder of supported command-line options.

=item -version

Display the version of B<evaluateQuery>.

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
  my $HELP          = 0;
  my $COMMAND_NAME  = 'evaluateQuery';
  my $INPUT_FILE = '';

#-----------------------------------------------------------------------
# Parse the command line
#-----------------------------------------------------------------------
  &ParseCommandLine();

  my $parser = new Parser::Select();
  my $code = $parser->Run();
  &Evaluator::Select::evaluate($code);

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
