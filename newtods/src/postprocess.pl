#!/usr/bin/perl -I/home/cdyreson/public_html/cgi-bin/perl/pm -I/home/cdyreson/bin/perl -w

#-----------------------------------------------------------------------

=head1 NAME

postprocess.pl - a program to post process a page and turn the box bgcolor on

=head1 SYNOPSIS

 postprocess.pl \
   -fontcolor color \
   -color color \
   -directory dir \
   -page page.html 

=head1 DESCRIPTION

B<postprocess> is a program to postprocess a page and turn the box bgcolor on

=head1 EXAMPLE USAGE

Make the index.html page blue

 postprocess.pl \
   -fontcolor #ffffff \
   -color #0000ff \
   -directory webpages \
   -page index.html 

=cut

#-----------------------------------------------------------------------
  use strict;
  #require 5.002;
 
  use Getopt::Long;
  use English;

  require "cleanWS.pm";

# Global variables
  my $ProgramName = 'SAMaker';
  my $Page = '';
  my $SHOW_HELP = 0;
  my $Color = '';
  my $FontColor = '';
  my $Directory = '';

  &ParseCommandLine();

# Default parsing pattern 
  my $Pattern = "bgcolor=[.]*HREF=\"$Page\"";
  my $bg = "bgcolor=\"#";
  my $pg = "HREF=\"$Page\"";

  open (FILE, "<$Directory/$Page") || die "could not open $Page";
  my @bottom = <FILE>;
  close FILE;
  open (OUT, ">$Directory/$Page") || die "could not open $Page";
  my @top = ();
  my @middle = ();
  my $s = '';
  while ($s = shift @bottom) {
    push @top, $s;
    if ($s =~ /$pg/) {
      # throw out the image
      shift @bottom;
      unshift @bottom, '&nbsp;&nbsp;&nbsp;<!-- ';
      while ($s = pop @top) {
        if ($s =~ /$bg/) {
          print OUT join("", @top);
          print OUT $`;
          print OUT $&;
          print OUT $Color;
          print OUT '">' . "\n";
          #print OUT "\nzzzzz\n";
          print OUT join("", @middle);
          #print OUT "\nyyyyy\n";
          print OUT join("", @bottom);
          close OUT;
          exit 0;
          }
        if ($s =~ /font/) {
          $s =~ s/font size=/font color="#$FontColor" size=/;
          unshift @middle, $s unless $s =~ /onMouseO/;
          }
        }
      }
    }
  print OUT join("", @top);
  close OUT;
        

#------------------------------------------------------------------------
# ParseCommandLine() - This function parses the command line.
#------------------------------------------------------------------------
sub ParseCommandLine {
  my @switches = (
    'page=s',     \$Page,
    'color=s',       \$Color,
    'directory=s',       \$Directory,
    'fontcolor=s',       \$FontColor,
    'help',         \$SHOW_HELP,
    );
 
  &GetOptions(@switches) || die "use -help switch to display brief help\n";
 
  if ($SHOW_HELP || ($Page eq '') || ($FontColor eq '') || ($Color eq '')) {
    print <<EofHelp;
    $ProgramName - Post process a page.

    Usage: 

      $ProgramName \
         [-help] \
         -fontcolor color \
         -directory directory \
         -color color \
         -page page.html
 
    Options: 
 
        -fontcolor  : rgb of font, e.g., ffffff
        -directory  : directory where page lives
        -color      : rgb of background, e.g., 3333ff
        -page       : the name of the page to process
EofHelp
    exit 0;
    }
 
}
