# 
# This is the TextDB version of the database.
#

use TextDB;

sub noopen {
  my ($text) = @_;
  print "<HTML><HEAD><TITLE>Error opening Files</TITLE></HEAD><BODY><\n";
  print "Detected the following error: " . $text;
  print "<P> Please make sure that you file permissions are set to allow access by user nobody.";
  print $query->end_html;
  die;
}

sub openReadOnlydb {
  my ($hashref, $file) = @_;
  &opendb($hashref, $file);
  }

sub opendb {
  my ($hashref, $file) = @_;
  tie %$hashref, TextDB, '';
  if (-e $file) {
    &noopen("dying, could not open the file `$file'.\n") unless
      (tied %$hashref)->load($file);
    }
  }
 
sub closedb {
  my ($hashref, $file) = @_;
  &noopen("dying, could not save the file `$file'.\n") unless
      (tied %$hashref)->save($file);
  untie %$hashref;
  }
 
sub openAndCleardb {
  my ($hashref, $file) = @_;
  tie %$hashref, TextDB, '';
  #&noopen("dying, could not open the file `$file'.\n") unless
  #  (tied %$hashref)->load("$file");
  }

1;
