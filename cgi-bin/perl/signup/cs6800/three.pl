#!/usr/bin/perl -I/home/cdyreson/public_html/cgi-bin/perl/upload/lib -I.

























# Copyright (c) 1996. Curtis E. Dyreson. All rights reserved.  This 
# software is covered under the general license and lack of 
# warranty as stated in the installation kit. WARNING! DANGER WILL 
# ROBINSON! USE AT YOUR OWN RISK!

 use strict;
 use CGI_Lite;

# common routine to clean junk
 require "cleanWS.pm";
 require "classlist.pm";

 my $ASSIGNMENT = 'three';
 my $Subject = 'CS 6800';
 my $Course = 'cs6800';
 my $Directory = "/home/cdyreson/$Course/$ASSIGNMENT";
 my $Archive = "$Directory/archive";
 my %Emails = ();
 &buildClassList(\%Emails);

 my %Directories = ();
 foreach my $name (keys %Emails) {
   my $s = $name; 
   $s =~ s/ //g;
   $s =~ s/-//g;
   $s =~ s/\.//g;
   $Directories{$name} = $s;
   }

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
 my $ErrorTitle = $Subject . ' Turnin Incomplete of Assignment ' . $ASSIGNMENT;
 my $ConfirmationTitle = $Subject . ' Turnin Confirmation of Assignment ' . $ASSIGNMENT;
 my $ErrorMessage = <<'ERRORMESSAGE';
Please complete the form by filling in the missing required fields, 
indicated in red below.  You will be asked to review and confirm the 
information you entered before it is actually submitted.  The information 
that you provide is much appreciated.
ERRORMESSAGE

 my $cgi = new CGI_Lite;

 my $ROOT = 'http://digital.cs.usu.edu/cgi-bin/cgiwrap/~cdyreson/perl/homeworksubmit' . "/$Course";

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
<title>CS 6800 Advanced Database Management Systems $Title</title>
</head>
<body>

<table summary="frame" border="0" cellPadding="0" cellSpacing="0">
  <tr>
    <td bgColor="#EEDDCC" align=right valign=top>
      <table summary="hard to see" border="0" cellSpacing="1">
        <tr>
          <td><a href="http://cs.usu.edu/~cdyreson/teaching/databases/141images/logo.jpg"
                 sytle="text-decoration:none"
             ><img src="http://cs.usu.edu/~cdyreson/teaching/databases/141/images/logo.jpg" 
                  alt="logo" 
                  width="90" 
                  height="68"
                  border="0"></a
          ></td>
        </tr>
      </table>
    </td>
    <td bgColor="#EEDDCC">
      &nbsp;
    </td>
    <td align=left valign=top bgColor="#EEDDCC">
        <table summary="hard to see" border="0" cellSpacing="1">
          <tr>
            <td bgcolor="#EEDDCC"> 
              <big>$Title</big>
              <br>
              <font color="#800000" size="2">
              CS 6800 - Advanced Database Management Systems<br>
              Utah State University 
              </font>
            </td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td align=center valign="top" bgcolor="#EEDDCC">
      <table summary="frame" cellpadding="2" border="0" cellspacing="2">
        <tr><td colspan=2 bgcolor="#FFFFFF">
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://cs.usu.edu/~cdyreson/teaching/databases/141/index.htm">
            Home&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://cs.usu.edu/~cdyreson/teaching/databases/141/calendar.htm">
            Calendar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://cs.usu.edu/~cdyreson/teaching/databases/141/homework.htm">
            Homework&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://cs.usu.edu/~cdyreson/teaching/databases/141/syllabus.htm">
            Syllabus&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://cs.usu.edu/~cdyreson/teaching/databases/141/resources.htm">
            Resources&nbsp;&nbsp;
            </a>
        </td></tr>
        <tr><td colspan=2 bgcolor="#FFFFFF">
            <a style="text-decoration:none" 
               href="http://cs.usu.edu/~cdyreson/teaching/databases/141/people.htm">
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
    <td height="20" bgColor="#EEDDCC">&nbsp;</td>
    <td colspan=2 bgColor="#EEDDCC" align="right">
      <small><i>E-mail questions or comments to <A HREF="mailto:Curtis.Dyreson\@usu.edu">Curtis Dyreson</A></i></small>
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
<FORM action="$ROOT/$ASSIGNMENT.pl" method=post encType=multipart/form-data>
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
<INPUT type=submit name="press" value="press"> this button to submit your assignment.
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
                           . ' needs a domain (e.g. Curtis.Dyreson@usu.edu) </font>';        }
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
                           . ' needs a domain (e.g. Curtis.Dyreson@usu.edu) </font>';        }
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
    if (defined($$data{'file'})) {
      if (-e "$Archive/" . $$data{'file'}) {
        my $target = "$Directory/" . $Directories{$$data{'student'}};
        mkdir $target, 0700 unless -e $target;
        link "$Archive/$$data{'file'}", "$target/$$data{'file'}"
        #system("/bin/mv $Archive/$$data{'file'} $target/$$data{'file'}")
          unless -e "$target/$$data{'file'}";
        unlink "$Archive/$$data{'file'}";
        }
      }
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
  my $recepients = $Emails{$$data{'student'}} . ' -c Curtis.Dyreson\@usu.edu ';
  my $text = &buildText();
  $text =~ s/<\/?pre>//mg;
  $text =~ s/<\/?h2>//mg;
  $text =~ s/<\/?i>//mg;

  my $message = <<"MESSAGE";
Dear $$data{'student'},

Thank you for the turnin of your assignment.  This message is 
to confirm that we have received the information listed below 
about your turnin.  If you have any questions, please 
e-mail Curtis.Dyreson\@usu.edu.

Sincerely,
Curtis Dyreson

-------------------------------------------------------------------
Synopsis
$PaperBlurb

$text

MESSAGE

  &sendAnAttachment($recepients, "Confirmation: $$data{'student'} $Subject Turnin", $message, '');

  $Title = $ConfirmationTitle;
  return <<"FINALFORM";
Thank you, $$data{'student'}, for the turnin of your assignment.
A confirmation e-mail has been sent to you at $Emails{$$data{'student'}}.
The mail server may be slow, so the e-mail may not reach you for several
hours.  If there are any problems, or if you do not receive the confirmation
e-mail within a day, please e-mail <A HREF="mailto:Curtis.Dyreson\@usu.edu">Curtis.Dyreson\@usu.edu</A> with your concerns.

FINALFORM
}

sub bugAction {
  return <<"INCOMPLETEACTION";
Don't know how you ended up here.  
Please e-mail a bug report to <A HREF="mailto:Curtis.Dyreson\@usu.edu">Curtis.Dyreson\@usu.edu</A>.

INCOMPLETEACTION
}

sub buildText{

  my $i;
  my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time);
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

<FORM action="$ROOT/$ASSIGNMENT.pl" method=post encType=multipart/form-data>
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

  #$to = 'Curtis.Dyreson\@usu.edu';
  if ($attachment ne '') {
    open(MAIL,
          "| /usr/bin/mutt -s \"$subject\" -a \"$Archive/$attachment\" $to"
        ) || die "could not open mail for writing";
    print MAIL $text;
    close MAIL;
    }
  else {
    open(MAIL, "| /usr/bin/mutt -s \"$subject\" $to") 
       || die "could not open mail for writing";
    print MAIL $text;
    close MAIL;
    }
  }
