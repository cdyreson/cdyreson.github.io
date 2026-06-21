# 
# This is the BSD version of the database.
#

use DB_File;
use Fcntl;

sub noopen {
  my ($text) = @_;
  print "<HTML><HEAD><TITLE>Error opening Files</TITLE></HEAD><BODY><\n";
  print "Detected the following error: " . $text;
  print "<P> Please make sure that you file permissions are set to allow access by user nobody.";
  print $query->end_html;
  die;
}

sub opendb {
  my ($hashref, $file) = @_;
  &noopen("dying, could not open the file `$file'.\n") unless
    tie %$hashref, "DB_File", "$file", O_RDWR|O_CREAT, 0640;
  }
 
sub closedb {
  my ($hashref) = @_;
  untie %$hashref;
  }
 
sub openAndCleardb {
  my ($hashref, $file) = @_;
  &opendb($hashref, $file);
  foreach (keys %$hashref) {
    delete $$hashref{$_};
    }
  }

1;
