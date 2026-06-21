#!/usr/bin/perl -I/home/cdyreson/public_html/cgi-bin/perl/upload/lib -I.

 use strict;
 use CGI_Lite;

# common routine to clean junk
 require "cleanWS.pm";
# require "tueSponsors.pm";
# require "wedSponsors.pm";
# require "thuSponsors.pm";

# EXIT THE FORM
#print "Content-type: text/html", "\r\n\r\n";
#print <<'ACMCENTRAL';
#<html>
#<title>Submit a CV to TODS redirection</title>
#<body>
#...
#</body>
#</html>
#ACMCENTRAL
#exit 0;

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
 my $Directory = "/home/cdyreson/prweb/cvs";
 my $Archive = "$Directory/archive";
 my $DBFile = "$Directory/sponsors.txt";
 my $PaperInfo = '';
 my @Sponsors = (
'Goldman Sachs',
'Oracle',
'Microsoft',
'AT&T',
'Baidu',
'Google',
'Facebook',
'IBM Research',
'Pivotal',
'SAP',
'Tableau Software',
'Walmart Labs',
'Intel',
'NEC',
'Yahoo Labs'
);

  @Sponsors = sort @Sponsors;


# 1 means required, 0 means optional
 my %Field   = ( 'name' => 1, 
                 'email' => 1, 
#                 'email2' => 1, 
                 'lunch1' => 0, 
                 'lunch2' => 0, 
                 'lunch3' => 0, 
                 'cv' => 0);
 my %FieldName = ('name' => 'Name', 
                 'email' => 'E-mail', 
#                 'email2' => 'Confirm E-mail', 
                 'lunch1' => 'Lunch Tuesday', 
                 'lunch2' => 'Lunch Wednesday', 
                 'lunch3' => 'Lunch Friday', 
                 'file' => 'CV or bio to upload (PDF)');
 my %FieldText = ();
 my $EntryTitle = 'PDF Upload Submission';
 my $Title = $EntryTitle;
 my $EntryMessage = <<'ENTRYMESSAGE';
<h2>ACM SIGMOD/PODS 2014 Student/Sponsor Lunches</h2>
SIGMOD/PODS 2014 provides an opportunity for you to eat
lunch with leaders in industry.
You can learn about life and working in industry, 
how to prepare for an interview, what skills or techniques are 
desired by industry, and about interesting trends and novel research
opportunities.
<p>
To connect you with our industry partners we'd
like to learn a bit more about you and what companies
are of interest to you.
To sign up for the student/sponsor face-to-face 
lunch meetings, please fill in the following form by
uploading in PDF something about you, like your CV, 
resume, bio, or print out of a home page. Just one file
please.  
Also, 
select one company for each lunch meeting from the drop-down 
menu of sponsors.  
The lunch meetings will in the Event Tent
where everyone will eat lunch.  Just look for the
sign with your chosen company on the table.
Your PDF will be provided to the company in advance
of the lunch to help them get to know you a bit better.
If you'd like to skip a
lunch meeting on a particular day, please select 'None' for that
day.  The selection is
on a first-come, first-serve basis and seating is limited
to eight students per sponsor table.
<p>
Finally, if you forget to fill out this form, no worries,
you can choose to sit at a unreserved seat at a sponsor's 
table if you wish to.

ENTRYMESSAGE
 my $ErrorTitle = 'PDF Upload Incomplete';
 my $ConfirmationTitle = 'PDF Upload Confirmation';
 my $ErrorMessage = <<'ERRORMESSAGE';
Please complete the form by filling in the missing required fields,
indicated in red below.  
You will be asked to review and confirm the information you
entered before it is actually submitted.
The information that you provide is much appreciated.
ERRORMESSAGE

 my $cgi = new CGI_Lite;

 my $ROOT = 'http://digital.cs.usu.edu/cgi-bin/cgiwrap/~cdyreson/perl/upload';

 my @tueSpo = ();
 my %tueStu = ();
 &grabSponsors("tuesday",\@tueSpo,\%tueStu);
 my @wedSpo = ();
 my %wedStu = ();
 &grabSponsors("wednesday",\@wedSpo,\%wedStu);
 my @thuSpo = ();
 my %thuStu = ();
 &grabSponsors("thursday",\@thuSpo,\%thuStu);

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

# Patch the input if bad
  foreach my $key (keys %$data) {
    next if $key eq 'file';
    $$data{$key} = &myURLencode($$data{$key});
    }

# figure out which action to apply
 my $GUI = &whichAction();

 print <<"PAGE";
<html>
<head>
<title>ACM SIGMOD/PODS 2014 Student/Sponsor Lunch - $Title</title>
</head>
</body>
          
  $GUI

<hr/>
<font size="2" 
    face="arial"><i>E-mail questions or comments to </i><A HREF="mailto:Curtis.Dyreson\@usu.edu">Curtis.Dyreson\@usu.edu</A>.</font></td>
</body>
</html>

PAGE

sub writeSponsors {
  my ($day, $spo, $name, $email, $file) = @_;
  open(OUT,">>$Directory/sponsors/$day.txt")
    || die "could not open $Directory/sponsors/$day.txt";

  print OUT "$spo<$name<$email<$file\n";
  close OUT;
}

sub grabSponsors {
  my ($day, $a, $spo) = @_;
  open(DIR,"<$Directory/sponsors/$day.txt")
    || die "could not open $Directory/sponsors/$day.txt";

  foreach my $x (@Sponsors) {
    $$spo{$x} = {};
    }

  # skip the first line
  #my $skup = <DIR>;

  while (<DIR>) {
    my ($sponsor, $name, $email, $file) =  split('<',$_); 
    #print "$sponsor, $name, $email, $file\n";
    $$spo{$sponsor} = {} unless defined $$spo{$sponsor};
    my $k = $$spo{$sponsor}; 
    $$k{$email} = $name;
    }
  close DIR;
  @$a = ();
  foreach my $sponsor (keys %$spo) {
    my $k = $$spo{$sponsor}; 
    #print "foo ". scalar (keys %$k) . "\n";
    my $count = scalar keys %$k;
    #print "$day $sponsor $count \n" if $count > 0;
    push @$a, $sponsor unless ($count > 8);
    } 
}

sub printGUI {
  my $message = shift;
  my $fileText = '<INPUT type="file" name="file" device="files" accept="*.pdf, *.ps, *.ps.gz, *.eps">';
  if ($$data{'file'} ne '') {
    $fileText = $$data{'file'};
    $fileText =~ s/^\d+__//;
    $fileText .= '<INPUT type="hidden" name="file" value="' 
                 . $$data{'file'} . '">';
    }
  my $tueSponsorList = '<option>' . join('</option><option>',sort @tueSpo) . '</option>';
  my $tueSponsorListSize = scalar @tueSpo;
  my $wedSponsorList = '<option>' . join('</option><option>',sort @wedSpo) . '</option>';
  my $wedSponsorListSize = scalar @wedSpo;
  my $thuSponsorList = '<option>' . join('</option><option>',sort @thuSpo) . '</option>';
  my $thuSponsorListSize = scalar @thuSpo;
  my $result =<<"INITIALFORM";
$message
<FORM action="$ROOT/starter.pl" method=post encType=multipart/form-data>
<table>
<tr>
<td align="right">
  $FieldText{'name'}
</td>
<td>
  <INPUT name=name size="25" value="$$data{'name'}"> 
</td>
</tr>
<tr>
<td align=right>
  $FieldText{'email'}
</td>
<td>
  <INPUT name=email size=30 value="$$data{'email'}">
</td>
</tr>
<tr>
<td>
Select a sponsor for Lunch on Tuesday
</td>
<td>
<select name="lunch1">
  <option>Any Sponsor</option>
  $tueSponsorList
  <option>None</option>
</select>
</td>
</tr>
<tr>
<td>
Select a sponsor for Lunch on Wednesday
</td>
<td>
<select name="lunch2">
  <option>Any Sponsor</option>
  $wedSponsorList
  <option>None</option>
</select>
</td>
</tr>
<tr>
<td>
Select a sponsor for Lunch on Thursday
</td>
<td>
<select name="lunch3">
  <option>Any Sponsor</option>
  $thuSponsorList
  <option>None</option>
</select>
</td>
</tr>
<tr>
<td colspan=2>
  $FieldText{'file'}
  $fileText
</td>
</tr>
</table>
<INPUT type=submit name="press" value="press"> this button to submit this form, you will be asked to 'confirm' before it is finally submitted.
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
  &writeSponsors("tuesday",$$data{'lunch1'},$$data{'name'},$$data{'email'},$$data{'file'});
  &writeSponsors("wednesday",$$data{'lunch2'},$$data{'name'},$$data{'email'},$$data{'file'});
  &writeSponsors("thursday",$$data{'lunch3'},$$data{'name'},$$data{'email'},$$data{'file'});
  my $recepients = $$data{'email1'} . ' -c Curtis.Dyreson\@usu.edu';
  my $recepients = $$data{'email1'} . ' -c Curtis.Dyreson\@usu.edu';
  $recepients .= " -c " . $$data{"email"};
  my $text = &buildText();
  $text =~ s/<\/?pre>//mg;
  $text =~ s/<\/?h2>//mg;
  $text =~ s/<\/?i>//mg;

  my $message = <<"MESSAGE";
Dear $$data{'name'},

Thank you for submitting your information for the student/sponsor lunch
meetings at SIGMOD 2014.  

If you have any questions, please e-mail mailto:Curtis.Dyreson\@usu.edu.

Sincerely,
Curtis Dyreson and Feifei Li
General Chairs, SIGMOD 2014

-------------------------------------------------------------------
Synopsis
  $PaperBlurb

$text

MESSAGE

#  my $toRick = <<"RICKMESSAGE";
#$PaperBlurb
#-------------------------------------------------------------------
#$text
#RICKMESSAGE
#
#  &sendAnAttachment('Curtis.Dyreson\@usu.edu', "PDF for $$data{'name1'}", $toRick, $$data{'file'});
  &sendAnAttachment($recepients, 'Confirmation: PDF upload for SIGMOD 2014 Student/Sponsor Lunches', $message, '');

  $Title = $ConfirmationTitle;
  return <<"FINALFORM";
Thank you for submitting your information for the student/sponsor lunch
meeting at SIGMOD 2014.
See you at Snowbird!

FINALFORM
}

sub bugAction {
  return <<"INCOMPLETEACTION";
Don't know how you ended up here.  
Please e-mail a bug report to <A HREF="mailto:Curtis.Dyreson\@usu.edu">Curtis.Dyreson\@usu.edu</A>.

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
  $PaperBlurb = <<"PAPERBLURB";
  Student:    $authors
  Tuesday:    $$data{'lunch1'}
  Wednesday:  $$data{'lunch2'}
  Thursday:   $$data{'lunch3'}
  CV or Bio:  $$data{'pdf'}
PAPERBLURB

  my $ContactAuthor = <<"CONTACTAUTHORINFO";
$FieldName{'name'}: $$data{'name'}
$FieldName{'email'}: $$data{'email'}
CONTACTAUTHORINFO
  my $fileText = 'none';
  $fileText = $$data{'file'} if defined $$data{'file'};
  $fileText =~ s/^\d+__//;
  $PaperInfo .= "<i>" . $FieldName{'file'} . "</i>: " . $fileText . "\n";
  my $FormBody = <<"FORMBODY";
<h2>Student</h2>
<pre>
$ContactAuthor
</pre>
<h2>PDF</h2>
<pre>
$PaperInfo
</pre>
<h2>Lunch</h2>
<pre>
$FieldName{'lunch1'}: $$data{'lunch1'}
$FieldName{'lunch2'}: $$data{'lunch2'}
$FieldName{'lunch3'}: $$data{'lunch3'}
</pre>
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
  <INPUT TYPE="hidden" name=name value="$$data{'name'}"> 
  <INPUT TYPE="hidden" name=email value="$$data{'email'}">
  <INPUT TYPE="hidden" name=lunch1 value="$$data{'lunch1'}">
  <INPUT TYPE="hidden" name=lunch2  value="$$data{'lunch2'}">
  <INPUT TYPE="hidden" name=lunch3  value="$$data{'lunch3'}">
  <INPUT TYPE="hidden" name="file" value="$$data{'file'}">
<br>
  <INPUT type=submit name="Confirm" value="Confirm"> Submit the information
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
