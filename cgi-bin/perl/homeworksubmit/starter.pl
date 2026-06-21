#!/net/local/bin/perl -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/upload/lib

























# Copyright (c) 1996. Curtis E. Dyreson. All rights reserved.  This 
# software is covered under the general license and lack of 
# warranty as stated in the installation kit. WARNING! DANGER WILL 
# ROBINSON! USE AT YOUR OWN RISK!

 use strict;
 use CGI_Lite;

# common routine to clean junk
 require "cleanWS.pm";

 my $Directory = "/net/zeus/facstaff/cdyreson/cpts551";
 my $Archive = "$Directory/archive";
 my $Subject = 'CptS 551';
 my %Emails = (
"Andy O'Fallon" => 'aofallon@eecs.wsu.edu',
'Ching-Guo Wu' => 'cwu@wsu.edu',
'Chris Baker' => 'cbaker3@eecs.wsu.edu',
'Damodar Nagapuram' => 'dnagapur@eecs.wsu.edu',
'Hongxun Liu' => 'hliu2@eecs.wsu.edu',
'Jin Liu' => 'jinliu@eecs.wsu.edu',
'Ramkumar Rajendran' => 'ramkumar@eecs.wsu.edu',
'Richard Griswold' => 'rgriswol@eecs.wsu.edu',
'Rongsen He' => 'rhe@eecs.wsu.edu',
'Ryan Phelps' => 'ryan@joescan.com',
'Stig Owre' => 'sowre@hotmail.com',
'Sunitha Shambulingappa' => 'sunniks@yahoo.com',
'Vaishali Chattopadhyay' => 'vaishali@eecs.wsu.edu',
'Wei Fu' => 'wfu1@eecs.wsu.edu',
'Yongchun Wu' => 'ycwu@mail.wsu.edu',
'test' => 'cdyreson\@eecs.wsu.edu',
);

 my %Directories = (
"Andy O'Fallon" => 'Andy',
'Ching-Guo Wu' => 'ChingGuo',
'Chris Baker' => 'Chris',
'Damodar Nagapuram' => 'Damodar',
'Hongxun Liu' => 'Hongxun',
'Jin Liu' => 'JinLiu',
'Ramkumar Rajendran' => 'Ramkumar',
'Richard Griswold' => 'Rich',
'Rongsen He' => 'Rongsen',
'Ryan Phelps' => 'Ryan',
'Stig Owre' => 'Stig',
'Sunitha Shambulingappa' => 'Sunitha',
'Vaishali Chattopadhyay' => 'Vaishali',
'Wei Fu' => 'Wei',
'Yongchun Wu' => 'Yongchun',
'test' => 'cdyreson',
);

# Define possible actions
 my $submitQueryAction = "press";
 my $editQueryAction = "Edit";
 my $confirmQueryAction = "Confirm";
 my $resubmitQueryAction = "resubmit";
 my $initialAction = "initial";
 my $okAction = "cancel";
 my $okAction = "finish";
 my $MaxAuthors = 6;
 my $PaperBlurb = '';
# 1 means required, 0 means optional
 my %Field   = ( 'student' => 1, 
                 'file' => 1,
                 'comment' => 0, 
               );
 my %FieldName = ('student' => 'name', 
                 'file' => 'file to upload',
                 'comment' => 'comment',
                 );
 my %FieldText = ();
 my $EntryTitle = 'Turnin by File Upload';
 my $Title = $EntryTitle;
 my $EntryMessage = <<'ENTRYMESSAGE';
To electronically submit an assignment please use the 
following form.
ENTRYMESSAGE
 my $ErrorTitle = 'Turnin Incomplete';
 my $ConfirmationTitle = 'Turnin Confirmation';
 my $ErrorMessage = <<'ERRORMESSAGE';
Please complete the form by filling in the missing required fields,
indicated in red below.  
You will be asked to review and confirm the information you
entered before it is actually submitted.
The information that you provide is much appreciated.
ERRORMESSAGE

 my $cgi = new CGI_Lite;

 my $ROOT = 'http://www.eecs.wsu.edu/cgi-bin/cgiwrap/~cdyreson/cgi-bin/perl/homeworksubmit';

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 $cgi->set_directory ($Archive) || die "Directory $Archive doesn't exist.\n";

 $cgi->set_file_type ("handle");
 my $data = $cgi->parse_form_data();

# patch the file upload
 my $temp = $$data{'file'};
 if ($temp ne '') {
   $temp =~ s/^\d+__//;
   if ($temp eq '') {$$data{'file'} = '';}
   }

# figure out which action to apply
 my $GUI = &whichAction();

 print <<"PAGE";














<html>
<head>
<title>CptS 551 Database Systems $Title</title>
</head>
<body>

<table summary="frame" border="0" cellPadding="0" cellSpacing="0">
  <tr>
    <td bgColor="#AFA58F" align=right valign=top>
      <table summary="hard to see" border="0" cellSpacing="1">
        <tr>
          <td><a href="http://content.wsulibs.wsu.edu/cgi-bin/pview.exe?CISOROOT=/maps&CISOPTR=564&CISORESTMP=/qbuild/buildplate11.html&CISOVIEWTMP=/qbuild/buildplate12.html&CISOCLICK=title:subjec:creato:date:type:covera"
                 style="text-decoration:none"
             ><img src="http://www.eecs.wsu.edu/~cdyreson/teaching/database/031/images/icon304.jpg" 
                  alt="logo" 
                  width="90" 
                  height="68"
                  border="0"></a
          ></td>
        </tr>
      </table>
    </td>
    <td bgColor="#AFA58F">
      &nbsp;
    </td>
    <td align=left valign=top bgColor="#AFA58F">
        <table summary="hard to see" border="0" cellSpacing="1">
          <tr>
            <td bgcolor="#AFA58F"> 
              <big>$Title</big>
              <br>
              <font color="#800000" size="2">
              CptS 551 - Database Systems<br>
              Washington State University 
              </font>
            </td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td align=center valign="top" bgcolor="#AFA58F">
      <table summary="frame" cellpadding="2" border="0" cellspacing="2">
        <tr><td colspan=2 bgcolor="#FFFFFF">
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://www.eecs.wsu.edu/~cdyreson/teaching/database/031/index.htm">
            Home&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://www.eecs.wsu.edu/~cdyreson/teaching/database/031/lectures.htm">
            Lectures&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://www.eecs.wsu.edu/~cdyreson/teaching/database/031/homework.htm">
            Homework&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://www.eecs.wsu.edu/~cdyreson/teaching/database/031/lessons.htm">
            Lessons&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://www.eecs.wsu.edu/~cdyreson/teaching/database/031/syllabus.htm">
            Syllabus&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://www.eecs.wsu.edu/~cdyreson/teaching/database/031/resources.htm">
            Resources&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://www.eecs.wsu.edu/~cdyreson/teaching/database/031/people.htm">
            People&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
      </table>
    </td>
    <td>
    </td>
    <td vAlign="top">
      <br>

      
  $GUI


      <br>
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    </td>
  </tr>
  <tr>
    <td height="20" bgColor="#AFA58F">&nbsp;</td>
    <td colspan=2 bgColor="#AFA58F" align="right">
      <small><i>E-mail questions or comments to <A HREF="mailto:cdyreson\@eecs.wsu.edu">Curtis Dyreson</A></i></small>
    </td>
  </tr>
</table>

</body>
</html>

PAGE

sub printGUI {
  my $message = shift;
  my @optionList = sort keys %Emails;
  my $size = scalar(@optionList);
  my $options = join("<option>", @optionList);
  $options = "<option>$options";
  my $fileText = '<INPUT type="file" name="file" device="files" accept="*.gz, *.tar, *.tar.gz">';
  if ($$data{'file'} ne '') {
    $fileText = $$data{'file'};
    $fileText =~ s/^\d+__//;
    $fileText .= '<INPUT type="hidden" name="file" value="' 
                 . $$data{'file'} . '">';
    }
  my $result =<<"INITIALFORM";
$message
<FORM action="$ROOT/starter.pl" method=post encType=multipart/form-data>
Choose your $FieldText{'student'}.
<P>
<select name="student" size=$size>$options</select>
<P>
Enter the $FieldText{'file'}.
  $fileText
<P>
(Optional) Is there any additional, special information on your submission?
<br>
  <TEXTAREA name="comment" rows=5 cols=60>$$data{'comment'}</TEXTAREA>
<P>
When the form is complete
<INPUT type=submit name="press" value="press"> this button to submit your paper.
</FORM>

INITIALFORM
  return $result;
}

# figure out which action to apply
sub whichAction {
  my ($i);
  my $result = '';
  if (defined $$data{$initialAction}) { 
    delete $$data{$initialAction};
    foreach my $key (keys %FieldName) {
      $FieldText{$key} = $FieldName{$key};
      }
    $result .= &printGUI($EntryMessage);
    return $result; 
    }
  if (defined $$data{$editQueryAction}) { 
    delete $$data{$editQueryAction};
    # Test all the required fields to determine that data is present
    my $error = 0;  # assume no errors
    foreach my $key (keys %Field) {
      $FieldText{$key} = $FieldName{$key};
      # Is this field an e-mail address?
      if ($key =~ /^email/
          && &cleanWS($$data{$key}) ne ''
          && (!($$data{$key} =~ /\@/))) {
        $error = 1;
        $FieldText{$key} = '<font color="red">'
                           . $FieldName{$key}
                           . ' needs a domain (e.g. cdyreson@wsu.edu) </font>';        }
      # Is this field required?
      if ($Field{$key} == 1) {
        # Is required data missing?
        if (!(defined $$data{$key} && $$data{$key} ne '')) {
          # A required field is missing
          $error = 1;
          $FieldText{$key} = '<font color="red">' 
                             . $FieldName{$key} 
                             . ' is required</font>';
          }
        }
      }
    if ($error != 0) {
      $Title = $ErrorTitle;
      return &printGUI($ErrorMessage);
      }
    return &printGUI($EntryMessage);
    }
  if (defined $$data{$submitQueryAction}) { 
    delete $$data{$submitQueryAction};
    # Test all the required fields to determine that data is present
    my $error = 0;  # assume no errors
    foreach my $key (keys %Field) {
      $FieldText{$key} = $FieldName{$key};
      # Is this field an e-mail address?
      if ($key =~ /^email/
          && &cleanWS($$data{$key}) ne ''
          && (!($$data{$key} =~ /\@/))) {
        $error = 1;
        $FieldText{$key} = '<font color="red">'
                           . $FieldName{$key}
                           . ' needs a domain (e.g. cdyreson@wsu.edu) </font>';        }
      # Is this field required?
      if ($Field{$key} == 1) {
        # Is required data missing?
        if (!(defined $$data{$key} && $$data{$key} ne '')) {
          # A required field is missing
          $error = 1;
          $FieldText{$key} = '<font color="red">' 
                             . $FieldName{$key} 
                             . ' is required</font>';
          }
        }
      }
    if ($error != 0) {
      $Title = $ErrorTitle;
      return &printGUI($ErrorMessage);
      }
    # Submission was clean
    return &formComplete(); 
    }
  if (defined $$data{$confirmQueryAction}) { 
    delete $$data{$confirmQueryAction};
    return &formFinal();
    }
  return &bugAction();
}    

sub formFinal {
  my $file = $$data{'file'};
  my $target = "$Directory/" . $Directories{$$data{'student'}};
  mkdir $target, 0700 unless -e $target;
  system("/bin/mv $Archive/$$data{'file'} $target/$$data{'file'}")
    unless -e "$target/$$data{'file'}";
  my $recepients = $Emails{$$data{'student'}} . ' -c cdyreson\@eecs.wsu.edu ';
  my $text = &buildText();
  $text =~ s/<\/?pre>//mg;
  $text =~ s/<\/?h2>//mg;
  $text =~ s/<\/?i>//mg;

  my $message = <<"MESSAGE";
Dear $$data{'student'},

Thank you for the turnin of your assignment.
This message is to confirm that we have received the 
information listed below about your turnin.  
If you have any questions, please e-mail cdyreson\@eecs.wsu.edu.

Sincerely,
Curtis Dyreson

-------------------------------------------------------------------
Synopsis
$PaperBlurb

$text

MESSAGE

  &sendAnAttachment($recepients, 'Confirmation: $Subject Turnin', $message, '');

  $Title = $ConfirmationTitle;
  return <<"FINALFORM";
Thank you for the turnin of your assignment.  A confirmation e-mail has been 
sent to you.  If there are any problems, or if you do not receive the 
confirmation e-mail within a day, please e-mail <A HREF="mailto:cdyreson\@eecs.wsu.edu">cdyreson\@eecs.wsu.edu</A> with your 
concerns.

FINALFORM
}

sub bugAction {
  return <<"INCOMPLETEACTION";
Don't know how you ended up here.  
Please e-mail a bug report to <A HREF="mailto:cdyreson\@eecs.wsu.edu">cdyreson\@eecs.wsu.edu</A>.

INCOMPLETEACTION
}

sub buildText{

  my $i;
  my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = gmtime(time);
  $year += 1900;
  $mon++;
  my $submitted = "$mon/$mday/$year";
  my $received = "$mon/$mday/$year";
  my $comment = "none";
  $comment = $$data{'comment'} if $$data{'comment'} ne '';
  $comment =~ s/\n/\n             /mg;
  $PaperBlurb = <<"PAPERBLURB";
  Student:   $$data{'student'}
  Submitted: $submitted (by file upload)
  Received:  $received
  Comment:   $comment
PAPERBLURB

  my $PaperInfo = '';
  $PaperInfo .= "<i>" . $FieldName{'student'} . "</i>: " . $$data{'student'} . "\n";
  my $fileText = $$data{'file'};
  $fileText =~ s/^\d+__//;
  $PaperInfo .= "<i>" . $FieldName{'file'} . "</i>: " . $fileText . "\n";
  my $comment = '';
  $comment .= "<h2>Comment</h2>\n" . $$data{'comment'} . "\n" if $$data{'comment'} ne '';
  my $FormBody = <<"FORMBODY";
<pre>
$PaperInfo
</pre>
$comment
FORMBODY
  return $FormBody;
}

sub formComplete {
  my $FormBody = &buildText();
  my $result = <<"COMPLETEFORM";
Please review the information you provided.  At the bottom of this page,
press 'Confirm' to finish the turnin process or 'Edit' to change 
the information.

$FormBody

<FORM action="$ROOT/starter.pl" method=post encType=multipart/form-data>
  <INPUT TYPE="hidden" name=student value="$$data{'student'}"> 
  <INPUT TYPE="hidden" name="file" value="$$data{'file'}">
  <INPUT TYPE="hidden" name="comment" value="$$data{'comment'}">
<br>
  <INPUT type=submit name="Confirm" value="Confirm"> Turnin the assignment
<br>
<br>
  <INPUT type=submit name="Edit" value="Edit"> Edit the information (do not submit yet)
</FORM>

COMPLETEFORM
  return $result;
}

# Fix it so that hidden variables work correctly
sub hidden {
  my ($name, $value) = @_;
  return "<INPUT TYPE=\"HIDDEN\" NAME=\"$name\" VALUE=\"$value\">";
}

sub sendAnAttachment {
  my ($to, $subject, $text, $attachment) = @_;
  #print "sending $to $subject $text";

  #$to = 'cdyreson\@eecs.wsu.edu';
  if ($attachment ne '') {
    open(MAIL,
          "| /net/local/bin/mutt -s \"$subject\" -a \"$Archive/$attachment\" $to"
        ) || die "could not open mail for writing";
    print MAIL $text;
    close MAIL;
    }
  else {
    open(MAIL, "| /net/local/bin/mutt -s \"$subject\" $to") 
       || die "could not open mail for writing";
    print MAIL $text;
    close MAIL;
    }
  }
