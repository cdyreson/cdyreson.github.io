#!/usr/bin/perl -I/home/cdyreson/public_html/cgi-bin/perl/talkUpload/lib

# Copyright (c) 1996. Curtis E. Dyreson. All rights reserved.  This 
# software is covered under the general license and lack of 
# warranty as stated in the installation kit. WARNING! DANGER WILL 
# ROBINSON! USE AT YOUR OWN RISK!

 use strict;
 use CGI_Lite;
 use HTML::Entities;

# common routine to clean junk
 require "cleanWS.pm";

# What is cgi root
 my $ROOT = 'http://www.cs.usu.edu/cgi-bin/cgiwrap/~cdyreson/perl/talkUpload';
 my $EDITORNAME= 'Curtis Dyreson';
 my $EDITORAFFILIATION = 'ACM SIGMOD Anthology Editor';
 my $EDITOREMAIL = 'Curtis.Dyreson@usu.edu';
 my $EDITORMAILTO = "<A HREF=\"mailto:$EDITOREMAIL\">$EDITOREMAIL</A>";
 my $ARCHIVEDIR = "/home/cdyreson/sigmod/archive";
 my $LOGOSURL = "http://www.cs.usu.edu/~cdyreson/anthology/images";

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
 my %Field   = ( 'name1' => 1, 
                 'email1' => 1, 
                 'file' => 1);
 my %FieldName = ('name1' => 'Name', 
                 'email1' => 'E-mail', 
                 'file' => 'File to upload (PDF, PPT, or PPS)');
 my %FieldText = ();
 my $EntryTitle = 'Conference Talk Upload';
 my $Title = $EntryTitle;

 my $cgi = new CGI_Lite;

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 $cgi->set_directory ($ARCHIVEDIR) || die "Directory $ARCHIVEDIR doesn't exist.\n";

 $cgi->set_file_type ("handle");
 my $data = $cgi->parse_form_data();

 my $authors = HTML::Entities::decode($$data{'authors'});
 my $title = HTML::Entities::decode($$data{'title'});
 my $EntryMessage = <<"ENTRYMESSAGE";
To upload a conference talk 
for 
<blockquote>
$authors
<br/>
$title
$$data{'pagesFirst'}-$$data{'pagesLast'}
<br/>
$$data{'conference'} $$data{'year'}
</blockquote>
please fill in the following
form.
ENTRYMESSAGE

 my $ErrorTitle = 'Conference Talk Upload Incomplete';
 my $ConfirmationTitle = 'Conference Talk Upload Confirmation';
 my $ErrorMessage = <<"ERRORMESSAGE";
Uploading in process for
<blockquote>
$authors
<br/>
$title
$$data{'pagesFirst'}-$$data{'pagesLast'}
<br/>
$$data{'conference'} $$data{'year'}
</blockquote>
Please complete the form by filling in the missing required fields,
indicated in red below.  
The information that you provide is much appreciated.
ERRORMESSAGE

# patch the file upload
 my $temp = $$data{'file'};
 if ($temp ne '') {
   $temp =~ s/^\d+__//;
   if ($temp eq '') {$$data{'file'} = '';}
   }

# Patch the input if bad
  foreach my $key (keys %$data) {
    next if $key eq 'file';
    $$data{$key} = &myURLencode($$data{$key});
    }

# figure out which action to apply
 my $GUI = &whichAction();

 print <<"PAGE";
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>ACM SIGMOD On-line - $Title</title>
</head>
<body>
          
  $GUI

</body>
</html>

PAGE

sub printGUI {
  my $message = shift;
  my $fileText = '<INPUT type="file" name="file" device="files" accept="*.pdf, *.ps, *.ps.gz, *.eps">';
  if ($$data{'file'} ne '') {
    $fileText = $$data{'file'};
    $fileText =~ s/^\d+__//;
    $fileText .= '<INPUT type="hidden" name="file" value="' 
                 . $$data{'file'} . '">';
    }
  my $result =<<"INITIALFORM";
$message
<FORM action="$ROOT/starter.pl" method="post" encType="multipart/form-data">
  <INPUT TYPE="hidden" name="title" value="$$data{'title'}">
  <INPUT TYPE="hidden" name="authors" value="$$data{'authors'}">
  <INPUT TYPE="hidden" name="conference" value="$$data{'conference'}">
  <INPUT TYPE="hidden" name="year" value="$$data{'year'}">
  <INPUT TYPE="hidden" name="pagesFirst" value="$$data{'pagesFirst'}">
  <INPUT TYPE="hidden" name="pagesLast" value="$$data{'pagesLast'}">
<table>
<tr>
<td align="right">
  $FieldText{'name1'}
</td>
<td>
  <INPUT name=name1 size="25" value="$$data{'name1'}"> 
</td>
</tr>
<tr>
<td align=right>
  $FieldText{'email1'}
</td>
<td>
  <INPUT name=email1 size=30 value="$$data{'email1'}">
</td>
</tr>
<tr>
<td colspan=2>
  $FieldText{'file'}
  $fileText
</td>
</tr>
</table>
<P/>
When the form is complete
<INPUT type=submit name="press" value="press this button"> to submit your talk.
You will be asked to review and confirm the information you
entered next.
</FORM>

INITIALFORM
  return $result;
}

sub myURLencode {
  my ($s) = @_;
  $s =~ s/</&lt;/gm;
  $s =~ s/>/&gt;/gm;
  $s =~ s/\"/&quot;/gm;
  return $s;
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
    return &AnthologyHeader('Conference Talk Upload') 
           . $result
           . &AnthologyTail(); 
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
      return &AnthologyHeader('Error in Conference Talk Upload Process') 
           . &printGUI($ErrorMessage)
           . &AnthologyTail(); 
      }
    return &AnthologyHeader('Conference Talk Upload') 
         . &printGUI($EntryMessage)
         . &AnthologyTail(); 
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
      return &AnthologyHeader('Error in Conference Talk Upload Process') 
           . &printGUI($ErrorMessage)
           . &AnthologyTail(); 
      }
    # Submission was clean
    return &AnthologyHeader('Confirmation Needed') 
         . &formComplete()
         . &AnthologyTail(); 
    }
  if (defined $$data{$confirmQueryAction}) { 
    delete $$data{$confirmQueryAction};
    return &AnthologyHeader('Submission Completed') 
         . &formFinal()
         . &AnthologyTail(); 
    }
  return &AnthologyHeader('Bug in Submission Process!!') 
         . &bugAction()
         . &AnthologyTail(); 
}    

sub formFinal {
  my $recepients = $$data{'email1'} . " -c $EDITOREMAIL";
  foreach my $i ((2..$MaxAuthors)) {
    next if &cleanWS($$data{"email$i"}) eq '';
    $recepients .= " -c " . $$data{"email$i"};
    }
  my $text = &buildText();
  $text =~ s/<\/?pre>//mg;
  $text =~ s/<\/?h2>//mg;
  $text =~ s/<\/?i>//mg;

  my $message = <<"MESSAGE";
Dear $$data{'name1'},

Thank you for submitting your conference talk to ACM SIGMOD on-line.  

Sincerely,
$EDITORNAME
$EDITORAFFILIATION

-------------------------------------------------------------------
Synopsis
  $PaperBlurb

$text

MESSAGE

  my $toTALKEDITOR = <<"EDITORMESSAGE";
$PaperBlurb
-------------------------------------------------------------------
$text
EDITORMESSAGE

  &sendAnEmail($EDITOREMAIL, "Talk Submission by $$data{'name1'}", $toTALKEDITOR);
  &sendAnEmail($recepients, 'Confirmation: Talk Submission', $message, '');

  $Title = $ConfirmationTitle;
  return <<"FINALFORM";
Thank you for submitting a conference talk to ACM SIGMOD on-line.
A confirmation e-mail has been sent to you.
You will be contacted soon by a SIGMOD editor who will give you 
information on the processing of your talk.
If there are any problems, or if you do not receive the confirmation 
e-mail within a week, please e-mail 
$EDITORMAILTO


FINALFORM
}

sub bugAction {
  return <<"INCOMPLETEACTION";
Don't know how you ended up here.  
Please e-mail a bug report to 
$EDITORMAILTO

INCOMPLETEACTION
}

sub buildText{

  my $authors = '';
  my $i;
  for my $i ((1..$MaxAuthors)) {
    next if &cleanWS($$data{"name$i"}) eq '';
    $authors .= $$data{"name$i"} . ' [' . $$data{"email$i"} . '] '  
    }
  my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = gmtime(time);
  $year += 1900;
  $mon++;
  my $submitted = "$mon/$mday/$year";
  my $received = "$mon/$mday/$year";
  my $comment = "none";
  $comment = $$data{'comment'} if $$data{'comment'} ne '';
  $comment =~ s/\n/\n             /mg;
  $PaperBlurb = <<"PAPERBLURB";
"$$data{'title'}"
  Author(s): $authors
  Submitted: $submitted 
  Received:  $received
PAPERBLURB

  my $ContactAuthor = <<"CONTACTAUTHORINFO";
$$data{'name1'}
$$data{'email1'}
CONTACTAUTHORINFO

  my $authors = HTML::Entities::decode($$data{'authors'});
  my $title = HTML::Entities::decode($$data{'title'});
  my $fileText = $$data{'file'};
  $fileText =~ s/^\d+__//;
  my $PaperInfo = "<i>" . $FieldName{'file'} . "</i>: " . $fileText . "\n";
  my $FormBody = <<"FORMBODY";
<h2>Contact Author</h2>
<pre>
$ContactAuthor
</pre>
<h2>Paper</h2>
<blockquote>
$authors
<br/>
$title
$$data{'pagesFirst'}-$$data{'pagesLast'}
<br/>
$$data{'conference'} $$data{'year'}
</blockquote>
$PaperInfo
FORMBODY
  return $FormBody;
}

sub formComplete {
  my $FormBody = &buildText();
  my $result = <<"COMPLETEFORM";
Please review the information you provided.  At the bottom of this page,
press 'Confirm' to finish the submission process or 'Edit' to change 
the information.

$FormBody

<FORM action="$ROOT/starter.pl" method=post encType=multipart/form-data>
  <INPUT TYPE="hidden" name="name1 value="$$data{'name1'}"> 
  <INPUT TYPE="hidden" name="email1 value="$$data{'email1'}">
  <INPUT TYPE="hidden" name="title" value="$$data{'title'}">
  <INPUT TYPE="hidden" name="authors" value="$$data{'authors'}">
  <INPUT TYPE="hidden" name="conference" value="$$data{'conference'}">
  <INPUT TYPE="hidden" name="year" value="$$data{'year'}">
  <INPUT TYPE="hidden" name="pagesFirst" value="$$data{'pagesFirst'}">
  <INPUT TYPE="hidden" name="pagesLast" value="$$data{'pagesLast'}">
  <INPUT TYPE="hidden" name="file" value="$$data{'file'}">
<br>
  <INPUT type=submit name="Confirm" value="Confirm"> Submit the talk
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

  #$to = $EDITOREMAIL;
  if ($attachment ne '') {
    open(MAIL,
          "| /usr/bin/mutt -s \"$subject\" -a \"$ARCHIVEDIR/$attachment\" $to"
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

sub sendAnEmail {
  my ($to, $subject, $text) = @_;
  #print "sending $to $subject $text";

  #$to = $EDITOREMAIL;
  open(MAIL, "| /usr/bin/mutt -s \"$subject\" $to")
     || die "could not open mail for writing";
  print MAIL $text;
  close MAIL;
  }

sub AnthologyHeader {
  my ($title) = @_;
  my $s = <<HEADEREND;
<html>
<head>
<title>ACM SIGMOD Anthology On-line: Conference Talk Upload</title>
</head>
<body>
<div align="left">
  <table width="100%" cellSpacing="0" border="0" cellPadding="0">
    <tr bgColor="#000000"><td height="10"/></tr>
    <tr bgcolor="#6D9FBB">
      <td align="left"> 
        <img src="$LOGOSURL/logo.gif"/>
      </td>
    </tr>
          <tr bgColor="#000000">
            <td height="32" align="center">
               <font size="5" color="#ffffff"> <b>$title</b></font>
            </td>
          </tr>
  </table>
    <table bgColor="#6D9FBB" width="100%" height="15" cellSpacing="0" border="0" cellPadding="0">
      <tbody>
         <tr>
           <td width="100%">&nbsp;
           </td>
        </tr>
      </tbody>
    </table>
    <table width="100%" cellSpacing="0" border="0" cellPadding="0">
      <tr> 
        <td bgColor="#6D9FBB">&nbsp;</td>
        <td width="15" vAlign="top">
          <img width="15" src="$LOGOSURL/hm_corner.gif" height="15" border="0"/>
        </td>
      </tr>
      <tr>
       <td bgColor="#6D9FBB" valign="top">
         <table cellSpacing="2" border="0" cellPadding="0">
          <tr>
      <td width="10" nowrap="1"> 
      </td>
      <td bgcolor="#6D9FBB" width="110" align="left" nowrap="1">
        &nbsp;
      </td>
      <td width="2" nowrap="1"> 
      </td>
    </tr>
         </table>
       </td>
        <td>&nbsp;</td>
        <td valign="top">
          <table cellspacing="0" border="0" cellpadding="0">
            <tr><td valign="top">
HEADEREND
  return $s;
  }

sub AnthologyTail {
  my $s = <<TAILEND;
</td></tr>
         </table>
        </td>
      </tr>
      <tr>
        <td bgColor="#6D9FBB">&nbsp;</td><td valign="bottom" align="left" nowrap="1"><img width="15" src="$LOGOSURL/ll_corner.gif" border="0"/></td>
      </tr>
      <tr>
        <td bgColor="#6D9FBB" width="100%" colspan="3" height="20" align="right">
          <font color="#FFFFFF">
            <small>
              <b>
              E-mail contributions, questions, comments and corrections to
                <font color="#000000">$EDITORMAILTO</font>
              </b>
            </small>
            &nbsp;
            &nbsp;
          </font>
        </td>
      </tr>
    </table>
  </div>
</body>
</html>
TAILEND
  return $s;
  }
