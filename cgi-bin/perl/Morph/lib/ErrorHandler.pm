package ErrorHandler;

# class variable
my $ProgText = '';

=head1 NAME ErrorHandler

This class handles errors, primarily by reporting and dying!
Only one ErrorHandler is permitted since this class contains
class-dependent variables (i.e., it should not be instantiated).

=head1 DESCRIPTION

Various kinds of errors are detected during query evaluation,
methods in this class support processing of those errors.
In general, all errors should end up here rather than croaking
or dying in the underlying methods.

=head1 METHODS

=head2 clearText()

Clear the accumulated text from the class.

=cut

#-----------------------------------------------------------------------
sub clearText {
  $ProgText = '';
  }
#-----------------------------------------------------------------------

=head2 appendText(string s)

=over 4

=item * s

- The string to append to the accumulated text.

=back

Adds text to the accumulated text, used to remember where in the
parsed text the program currently is.

=cut

#-----------------------------------------------------------------------
sub appendText {
  my ($type, $s) = @_;
  $ProgText .= $s;
  }
#-----------------------------------------------------------------------

=head2 internalError(string msg, string rest)

=over 4

=item * msg

- The message to print.

=item * rest

- The rest of the program.

=back

Some internal error was detected due to a bug in the compiler,
print something appropriate and die.

=cut

#-----------------------------------------------------------------------
sub internalError {
  my ($type, $msg, $rest) = @_;
  print "<PRE>\n";
  print $ProgText;
  print " <--- Internal database error.\n";
  print "------------------------------------------------------\n";
  print " Perhaps the problem is: $msg.\n";
  print "------------------------------------------------------\n";
  print "$rest\n" if defined $rest;
  print "</PRE>\n";
  exit(1);
}
#-----------------------------------------------------------------------

=head2 syntaxError(string msg, string current, string rest)

=over 4

=item * msg

- The message to print.

=item * current

- The current token.

=item * rest

- The rest of the program.

=back

A syntax error was detected.  Print something appropriate and die.

=cut

#-----------------------------------------------------------------------
sub syntaxError {
  my ($type, $msg, $current, $rest) = @_;
  print "<PRE>\n";
  print $ProgText;
  print " <--- Syntax error with $current.\n";
  print "------------------------------------------------------\n";
  print " Perhaps the problem is: $msg.\n";
  print "------------------------------------------------------\n";
  print "$rest\n" if defined $rest;
  print "</PRE>\n";
  exit(1);
}  
#-----------------------------------------------------------------------

=head2 semanticError(string msg, string current, string rest)

=over 4

=item * msg

- The message to print.

=item * current

- The current token.

=item * rest

- The rest of the program.

=back

A semantic error was detected.  Print something appropriate and die.

=cut

#-----------------------------------------------------------------------
sub semanticError {
  my ($type, $msg, $current, $rest) = @_;
  print "<PRE>\n";
  print $ProgText;
  print " <--- Semantic error with $current.\n";
  print "------------------------------------------------------\n";
  print " Perhaps the problem is: $msg.\n";
  print "------------------------------------------------------\n";
  print "$rest\n" if defined $rest;
  print "</PRE>\n";
  exit(1);
}  
#-----------------------------------------------------------------------

1;
