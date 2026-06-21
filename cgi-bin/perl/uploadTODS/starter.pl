#!/net/local/bin/perl -I/net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/upload/lib













































# Copyright (c) 1996. Curtis E. Dyreson. All rights reserved.  This 
# software is covered under the general license and lack of 
# warranty as stated in the installation kit. WARNING! DANGER WILL 
# ROBINSON! USE AT YOUR OWN RISK!

 use strict;
 use CGI_Lite;

# common routine to clean junk
 require "cleanWS.pm";

# EXIT THE FORM
print "Content-type: text/html", "\r\n\r\n";
print <<'ACMCENTRAL';
<html>
<title>Submit a paper to TODS redirection</title>
<body>
To electronically submit a paper for review
by ACM <i>Transactions on Database Systems</i> please go to 
<a href="http://acm.manuscriptcentral.com">acm.manuscriptcentral.com</a>.
</body>
</html>
ACMCENTRAL
exit 0;

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
                 'address' => 1, 
                 'phone' => 0, 'fax' => 0,
                 'name2' => 0, 'email2' => 0,
                 'name3' => 0, 'email3' => 0,
                 'name4' => 0, 'email4' => 0,
                 'name5' => 0, 'email5' => 0,
                 'name6' => 0, 'email6' => 0,
                 'name7' => 0, 'email7' => 0,
                 'name8' => 0, 'email8' => 0,
                 'name9' => 0, 'email9' => 0,
                 'name10' => 0, 'email10' => 0,
                 'comment' => 0, 
                 'title' => 1, 
                 'abstract' => 1, 
                 'file' => 1);
 my %FieldName = ('name1' => 'Name', 
                 'email1' => 'E-mail', 
                 'address' => 'Postal Address', 
                 'phone' => 'Phone', 'fax' => 'Fax',
                 'name2' => 'Name', 'email2' => 'E-mail',
                 'name3' => 'Name', 'email3' => 'E-mail',
                 'name4' => 'Name', 'email4' => 'E-mail',
                 'name5' => 'Name', 'email5' => 'E-mail',
                 'name6' => 'Name', 'email6' => 'E-mail',
                 'name7' => 'Name', 'email7' => 'E-mail',
                 'name8' => 'Name', 'email8' => 'E-mail',
                 'name9' => 'Name', 'email9' => 'E-mail',
                 'name10' => 'Name', 'email10' => 'E-mail',
                 'comment' => 'Comment', 
                 'title' => 'Title', 
                 'abstract' => 'Abstract', 
                 'file' => 'File to upload (PDF or PostScript)');
 my %FieldText = ();
 my $EntryTitle = 'File Upload Submission';
 my $Title = $EntryTitle;
 my $EntryMessage = <<'ENTRYMESSAGE';
To electronically submit a paper for review
by ACM <i>Transactions on Database Systems</i> please fill in the following
form (you may also submit
by <a href="http://www.acm.org/tods/Authors.html#HowtoSubmit-Initial">postal mail</a> or
by <a href="http://www.acm.org/tods/SubmitByCoRR.html">CoRR</a>).
All fields are required, except those marked optional.  LaTeX or
other document formatting commands can be included in a field
(you may cut and paste from your document source)
as long as the text can be read and understood by a human.
You will be asked to review and confirm the information you
entered before it is actually submitted.
The information that you provide is much appreciated.
ENTRYMESSAGE
 my $ErrorTitle = 'File Upload Incomplete';
 my $ConfirmationTitle = 'File Upload Confirmation';
 my $ErrorMessage = <<'ERRORMESSAGE';
Please complete the form by filling in the missing required fields,
indicated in red below.  
You will be asked to review and confirm the information you
entered before it is actually submitted.
The information that you provide is much appreciated.
ERRORMESSAGE

 my $cgi = new CGI_Lite;

 my $ROOT = 'http://www.eecs.wsu.edu/cgi-bin/cgiwrap/~cdyreson/cgi-bin/perl/upload';

# Build the header
 print "Content-type: text/html", "\r\n\r\n";
 $cgi->set_directory ("/net/zeus/facstaff/cdyreson/tods/archive") || die "Directory doesn't exist.\n";

 $cgi->set_file_type ("handle");
 my $data = $cgi->parse_form_data();

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
<title>ACM Transactions on Database Systems On-line - $Title</title>
</head>
<script language="JavaScript">
<!--
  forwardArrow=new Image
  backArrow=new Image
  backLinkArrow=new Image
  forwardLinkArrow=new Image
  forwardArrow.src="http://www.acm.org/tods/across.gif"
  backArrow.src="http://www.acm.org/tods/b.gif"
  backLinkArrow.src="http://www.acm.org/tods/back.gif"
  forwardLinkArrow.src="http://www.acm.org/tods/backBlue.gif"
// -->
</script>
<body bgcolor="#ffffff">
<TABLE SUMMARY="frame" BORDER=0 CELLPADDING="0" CELLSPACING="0">
  <tr><td align=left bgcolor="#818181" valign=top><IMG SRC="http://www.acm.org/tods/logotods7.gif" 
  ALT="ACM logo" BORDER="0"></TD><TD 
   bgcolor="#818181" 
   valign=bottom
   ><img
   SRC="http://www.acm.org/tods/logotods8.gif" 
   ALT="TODS" 
   BORDER="0"></td><td bgcolor="#818181" align=right>
&nbsp;
      </td></tr><tr><td height="35" bgcolor="#818181">&nbsp;</td><td 
        bgcolor="#D0D0D0" colspan=2
        align="left"><font SIZE="6" 
         face="arial">&nbsp;&nbsp;<i>$Title</i></font></td></tr><tr 
        valign="top"><td width="80" align="center" valign="top" bgcolor="#818181">
     <table summary="frame" cellspacing="2" border="0" cellpadding="2">
       <tr>
         <td bgcolor="#818181">
           <font size="2" face="arial">&nbsp;</font>
         </td>
         <td bgcolor="#818181">
           <font size="2" face="arial">&nbsp;</font>
         </td>
       </tr>  
       <tr><td colspan=2 bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Home.src=backArrow.src"
          onMouseOut="Home.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://www.acm.org/tods/index.html">
          <img alt="TODS Home" border=0 name="Home" SRC="http://www.acm.org/tods/across.gif"><!--
          -->&nbsp;Home&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</A>
         </font>
       </td></tr>
       <tr><td colspan=2 bgcolor="#818181" align=top>
         <font size="2" face="arial" color="#FFFFFF"
           >Articles</font>
       </td></tr>
       <tr><td bgcolor="#818181">
         <font size="2" face="arial">&nbsp;
         </font>
         </td><td bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Current.src=backArrow.src"
          onMouseOut="Current.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://portal.acm.org/tods/current">
          <img alt="Current issue" name="Current" border=0 SRC="http://www.acm.org/tods/across.gif"><!--
          -->&nbsp;Current&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</A>
         </font>
       </td></tr>
       <tr><td bgcolor="#818181">
         <font size="2" face="arial">&nbsp;
         </font>
         </td><td bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Upcoming.src=backArrow.src"
          onMouseOut="Upcoming.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://www.acm.org/tods/Upcoming.html">
          <img name="Upcoming" alt="Future issues" border=0 SRC="http://www.acm.org/tods/across.gif"><!-- 
          -->&nbsp;Upcoming&nbsp;</A>
         </font>
       </td></tr>
       <tr><td bgcolor="#818181">
         <font size="2" face="arial">&nbsp;
         </font>
         </td><td bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Past.src=backArrow.src"
          onMouseOut="Past.src=forwardArrow.src"
          style="text-decoration:none"
         HREF="http://portal.acm.org/tods">
          <img border=0 name="Past" alt="Past issues" SRC="http://www.acm.org/tods/across.gif"><!-- 
          -->&nbsp;Past&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</A>
         </font>
       </td></tr>
       <tr><td colspan=2 bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Charter.src=backArrow.src"
          onMouseOut="Charter.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://www.acm.org/tods/Charter.html">
          <img border=0 alt="Charter" name="Charter" SRC="http://www.acm.org/tods/across.gif"><!-- 
         -->&nbsp;Charter&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</A>
         </font>
       </td></tr>
       <tr><td colspan=2 bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Editors.src=backArrow.src"
          onMouseOut="Editors.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://www.acm.org/tods/Editors.html">
          <img border=0 alt="Editors" name="Editors" SRC="http://www.acm.org/tods/across.gif"><!-- 
         -->&nbsp;Editorial&nbsp;Board</A>
         </font>
       </td></tr>
       <tr><td colspan=2 bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Authors.src=backArrow.src"
          onMouseOut="Authors.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://www.acm.org/tods/Authors.html">
          <img border=0 name="Authors" alt="Author instructions" SRC="http://www.acm.org/tods/across.gif"><!-- 
          -->&nbsp;Author&nbsp;info&nbsp;&nbsp;&nbsp;&nbsp;</A>
         </font>
       </td></tr>
       <tr><td colspan=2 bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Referees.src=backArrow.src"
          onMouseOut="Referees.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://www.acm.org/tods/Referees.html">
          <img border=0 name="Referees" alt="Referee instructions" SRC="http://www.acm.org/tods/across.gif"><!-- 
          -->&nbsp;Referee&nbsp;info&nbsp;&nbsp;&nbsp;</A>
         </font>
       </td></tr>
       <tr><td colspan=2 bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Editorials.src=backArrow.src"
          onMouseOut="Editorials.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://www.acm.org/tods/Editorials.html">
          <img border=0 alt="TODS charter" name="Editorials" SRC="http://www.acm.org/tods/across.gif"><!-- 
          -->&nbsp;Editorials&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</A>
         </font>
       </td></tr>
       <tr><td colspan=2 bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="Subscribe.src=backArrow.src"
          onMouseOut="Subscribe.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://www.acm.org/tods/Subscription.html">
          <img border=0 alt="Subscribe to TODS" name="Subscribe" SRC="http://www.acm.org/tods/across.gif"><!-- 
          -->&nbsp;Subscribe&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</A>
         </font>
       </td></tr>
       <tr><td colspan=2 bgcolor="#FFFFFF">
         <font size="2" face="arial">
         <A
          onMouseOver="EditorsOnly.src=backArrow.src"
          onMouseOut="EditorsOnly.src=forwardArrow.src"
          style="text-decoration:none"
          HREF="http://www.acm.org/tods/editors/index.html">
          <img border=0 alt="Editors only" name="EditorsOnly" SRC="http://www.acm.org/tods/across.gif"><!-- 
          -->&nbsp;Editors only&nbsp;</A>
         </font>
       </td></tr>
     </table>
    </td>
    <td valign="top" colspan=2>
      <table summary="frame">
        <tr><td> 
          &nbsp;&nbsp;
        </td><td> 
          <font face="arial">
          
  $GUI

          </font>
        </td></tr> 
      </table>
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
    <td bgcolor="#818181">&nbsp;</td><td colspan=2><hr noshade size=1></td>
  </tr>
  <tr>
    <td height=20 bgcolor="#818181">&nbsp;</td><td 
    >&nbsp;&nbsp;<a
        href="javascript:history.back()"
        onMouseOver="backLink.src=forwardLinkArrow.src"
        onMouseOut="backLink.src=backLinkArrow.src"
        style="text-decoration:none"
      ><img 
       alt="Return to previous page" 
       border=0
       name="backLink" 
       valign=center SRC="http://www.acm.org/tods/back.gif">&nbsp;
    <font size="-1" face="arial" "arial">back</font></a>
    </td><td align=right><font size="2" 
    face="arial"><i>E-mail questions or comments to </i><A HREF="mailto:tods\@acm.org">tods\@acm.org</A>.</font></td>
  </tr>
</table>
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
<FORM action="$ROOT/starter.pl" method=post encType=multipart/form-data>
<h2>Contact Author</h2>
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
<td align=right>
  $FieldText{'phone'}
</td>
<td>
  <INPUT name=phone value="$$data{'phone'}"> (optional)
</td>
</tr>
<tr>
<td align=right>
  $FieldText{'fax'}
</td>
<td>
  <INPUT name=fax value="$$data{'fax'}"> (optional)
</td>
</tr>
<tr>
<td align=right valign=top>
  $FieldText{'address'}
</td>
<td>
  <TEXTAREA name=address rows=5 cols=50>$$data{'address'}</TEXTAREA> 
</td>
</tr>
</table>
<h2>Additional Authors (optional)</h2>
Please enter any additional authors that you would like to include on 
e-mail correspondence about the paper.</h2>
  $FieldText{'name2'}
  <INPUT name=name2 size=25 value="$$data{'name2'}">
  $FieldText{'email2'}
  <INPUT name=email2 size=30 value="$$data{'email2'}">
<br>
  $FieldText{'name3'}
  <INPUT name=name3 size=25 value="$$data{'name3'}">
  $FieldText{'email3'}
  <INPUT name=email3 size=30 value="$$data{'email3'}">
<br>
  $FieldText{'name4'}
  <INPUT name=name4 size=25 value="$$data{'name4'}">
  $FieldText{'email4'}
  <INPUT name=email4 size=30 value="$$data{'email4'}">
<br>
  $FieldText{'name5'}
  <INPUT name=name5 size=25 value="$$data{'name5'}">
  $FieldText{'email5'}
  <INPUT name=email5 size=30 value="$$data{'email5'}">
<br>
  $FieldText{'name6'}
  <INPUT name=name6 size=25 value="$$data{'name6'}">
  $FieldText{'email6'}
  <INPUT name=email6 size=30 value="$$data{'email6'}">
<h2>Paper</h2>
<table>
<tr>
<td align=right>
  $FieldText{'title'}
</td>
<td>
  <INPUT name=title size="60" value="$$data{'title'}">
</td>
</tr>
<tr>
<td align=right valign=top>
  $FieldText{'abstract'}
</td>
<td>
  <TEXTAREA name=abstract rows=5 cols=50>$$data{'abstract'}</TEXTAREA>
</td>
</tr>
<tr>
<td colspan=2>
  $FieldText{'file'}
  $fileText
</td>
</tr>
</table>
<h2>Additional Information (optional)</h2>
Is there any additional, special information on the submission of your paper
(this information is provided to the editor(s))?
<br>
  <TEXTAREA name="comment" rows=5 cols=60>$$data{'comment'}</TEXTAREA>
<P>
When the form is complete
<INPUT type=submit name="press" value="press"> this button to submit your paper.
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
  my $recepients = $$data{'email1'} . ' -c tods\@acm.org ';
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

Thank you for submitting a paper to ACM Transactions on Database Systems.  
A TODS editor will contact you soon with more information
on the processing of your submission.  This message is 
to confirm that we have received the information listed 
below about your paper.  If you have any questions, 
please e-mail mailto:tods\@acm.org.

Sincerely,
Curtis Dyreson
TODS Information Director
tods\@acm.org

-------------------------------------------------------------------
Synopsis
  $PaperBlurb

$text

MESSAGE

  my $toRick = <<"RICKMESSAGE";
$PaperBlurb
-------------------------------------------------------------------
$text
RICKMESSAGE

  &sendAnAttachment('rts\@cs.arizona.edu', "TODS Submission by $$data{'name1'}", $toRick, $$data{'file'});
#  &sendAnAttachment('mcarey\@bea.com -c rts\@cs.arizona.edu tods\@acm.org', "TODS Submission by $$data{'name1'}", $toRick, $$data{'file'});
  &sendAnAttachment($recepients, 'Confirmation: TODS Paper Submission', $message, '');

  $Title = $ConfirmationTitle;
  return <<"FINALFORM";
Thank you for submitting a paper for review by ACM <i>Transactions on Database Systems</i>.
A confirmation e-mail has been sent to all the authors.
You will be contacted soon by a <i>TODS</i> editor who will give you more
information on the processing of your paper.
If there are any problems, or if you do not receive the confirmation 
e-mail within a week, please e-mail <A HREF="mailto:tods\@acm.org">tods\@acm.org</A> with your concerns.

FINALFORM
}

sub bugAction {
  return <<"INCOMPLETEACTION";
Don't know how you ended up here.  
Please e-mail a bug report to <A HREF="mailto:tods\@acm.org">tods\@acm.org</A>.

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
  Submitted: $submitted (by file upload)
  Received:  $received
  Comment:   $comment
PAPERBLURB

  my $ContactAuthor = <<"CONTACTAUTHORINFO";
$$data{'name1'}
$$data{'address'}

$$data{'email1'}
CONTACTAUTHORINFO
  $ContactAuthor .= '<i>' . $FieldName{"phone"} . "</i>: " . $$data{"phone"} . "\n"
     if $$data{"phone"} ne '';
  $ContactAuthor .= '<i>' . $FieldName{"fax"} . "</i>: " . $$data{"fax"} . "\n"
     if $$data{"fax"} ne '';

  my $OtherAuthors = '';
  my $AdditionalAuthors = '';
  foreach my $i ((2..$MaxAuthors)) {
    next if &cleanWS($$data{"name$i"}) eq '';
    $OtherAuthors .= "<i>" . $FieldName{"name$i"} . "</i>: " . $$data{"name$i"} . "\n";
    $OtherAuthors .= "<i>" . $FieldName{"email$i"} . "</i>: " . $$data{"email$i"} . "\n";
    }
  $AdditionalAuthors = "<h2>Additional Authors</h2>\n<pre>\n$OtherAuthors</pre>\n"
    unless ($OtherAuthors eq '');
  my $PaperInfo = '';
  $PaperInfo .= "<i>" . $FieldName{'title'} . "</i>\n  " . $$data{'title'} . "\n\n\n";
  $PaperInfo .= "<i>" . $FieldName{'abstract'} . "</i>\n" . $$data{'abstract'} . "\n\n\n";
  my $fileText = $$data{'file'};
  $fileText =~ s/^\d+__//;
  $PaperInfo .= "<i>" . $FieldName{'file'} . "</i>: " . $fileText . "\n";
  my $comment = '';
  $comment .= "<h2>Comment</h2>\n" . $$data{'comment'} . "\n" if $$data{'comment'} ne '';
  my $FormBody = <<"FORMBODY";
<h2>Contact Author</h2>
<pre>
$ContactAuthor
</pre>
$AdditionalAuthors
<h2>Paper</h2>
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
press 'Confirm' to finish the submission process or 'Edit' to change 
the information.

$FormBody

<FORM action="$ROOT/starter.pl" method=post encType=multipart/form-data>
  <INPUT TYPE="hidden" name=name1 value="$$data{'name1'}"> 
  <INPUT TYPE="hidden" name=email1 value="$$data{'email1'}">
  <INPUT TYPE="hidden" name=phone value="$$data{'phone'}">
  <INPUT TYPE="hidden" name=fax value="$$data{'fax'}"> 
  <INPUT TYPE="hidden" name=address value="$$data{'address'}"> 
  <INPUT TYPE="hidden" name=name2 value="$$data{'name2'}">
  <INPUT TYPE="hidden" name=email2 value="$$data{'email2'}">
  <INPUT TYPE="hidden" name=name3 value="$$data{'name3'}">
  <INPUT TYPE="hidden" name=email3 value="$$data{'email3'}">
  <INPUT TYPE="hidden" name=name4 value="$$data{'name4'}">
  <INPUT TYPE="hidden" name=email4 value="$$data{'email4'}">
  <INPUT TYPE="hidden" name=name5 value="$$data{'name5'}">
  <INPUT TYPE="hidden" name=email5 value="$$data{'email5'}">
  <INPUT TYPE="hidden" name=name6 value="$$data{'name6'}">
  <INPUT TYPE="hidden" name=email6 value="$$data{'email6'}">
  <INPUT TYPE="hidden" name=name7 value="$$data{'name7'}">
  <INPUT TYPE="hidden" name=email7 value="$$data{'email7'}">
  <INPUT TYPE="hidden" name=name8 value="$$data{'name8'}">
  <INPUT TYPE="hidden" name=email8 value="$$data{'email8'}">
  <INPUT TYPE="hidden" name=name9 value="$$data{'name9'}">
  <INPUT TYPE="hidden" name=email9 value="$$data{'email9'}">
  <INPUT TYPE="hidden" name=name10 value="$$data{'name10'}">
  <INPUT TYPE="hidden" name=email10 value="$$data{'email10'}">
  <INPUT TYPE="hidden" name=title value="$$data{'title'}">
  <INPUT TYPE="hidden" name=abstract value="$$data{'abstract'}">
  <INPUT TYPE="hidden" name="file" value="$$data{'file'}">
  <INPUT TYPE="hidden" name="comment" value="$$data{'comment'}">
<br>
  <INPUT type=submit name="Confirm" value="Confirm"> Submit the paper
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
          "| /net/local/bin/mutt -s \"$subject\" -a \"/net/zeus/facstaff/cdyreson/tods/archive/$attachment\" $to"
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
