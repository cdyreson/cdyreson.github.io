#!/usr/local/bin/perl5

##++
##     CGI Lite v1.4
##     Last modified: October 14, 1995
##
##     Copyright (c) 1995 by Shishir Gundavaram
##     All Rights Reserved
##
##     E-Mail: shishir@bu.edu
##
##     Permission to use, copy, and distribute is hereby granted,
##     providing that the above copyright notice and this permission
##     appear in all copies and in supporting documentation.
##--

###############################################################################

=head1 NAME

CGI Lite v1.4 - Perl 5.0 module to process and decode WWW form
information

=head1 SYNOPSIS

    use CGI_Lite;

    $form = new CGI_Lite ();
    $status = $form->set_file_type ("handle" or "file");

    $status = $form->set_directory ("/some/dir");
    $form->set_directory ("/some/dir") || die "Directory doesn't exist.\n";

    $reference_to_hash = $form->parse_form_data ();
    %hash = $form->parse_form_data ();

    $form->print_form_data ();

=head1 DESCRIPTION

The module can be used to handle and decode WWW form
information. Both GET and POST requests can be processed. In
the case of POST requests, the information can be one of two
possible MIME types:

    application/x-www-form-urlencoded
    multipart/form-data

This module is very light-weight, and can be thought of
as an enhanced version of the old cgi-lib.pl library for
Perl 4.0 by Steven Brenner I<(S.E.Brenner@bioc.cam.ac.uk)>.

=head2 Form Creation

Here is an example of a simple form that uses the I<file> attribute
in the I<input> statement to present the user with the capability to
upload a file:

    <TITLE>CGI Lite Test</TITLE>
    <H1>CGI Lite Test</H1>
    <HR>
    <FORM ACTION="/cgi-bin/test.pl"
	  ENCTYPE="multipart/form-data"
	  METHOD="POST">
 
          What is your name? <INPUT TYPE="text" NAME="username">
          <P>     
          Select a file to send: <INPUT TYPE="file" NAME="input_file">
          <P>
          <INPUT TYPE="submit" VALUE="Send the Multipart Form">
          <INPUT TYPE="reset"  VALUE="Clear the Information">

    </FORM>
    <HR>  

=head2 multipart/form-data MIME header

Here is what a multipart/form-data header looks like (as of Netscape 2.0b1):

    -----------------------------239891195122666
    Content-disposition: form-data; name="full_name"

    Foo Bar
    -----------------------------239891195122666
    Content-disposition: form-data; name="picture"; filename="/bar.gif"

    ... GIF Data ...
    -----------------------------239891195122666
    Content-disposition: form-data; name="readme"; filename="/bar.txt"

    ... Text Data ...
    -----------------------------239891195122666--

=head1 METHODS

Here are the methods you can use to process your forms:

=over 5

=item B<parse_form_data>

This will handle all types of requests: GET, HEAD and
POST (for both encoding methods). For multipart/form-data,
uploaded files are stored in the user selected directory
(see B<set_directory>). The files are named in the following format:

    /some/dir/filename.timestamp

where the filename is specified in the "Content-disposition"
header.

I<Return Value>

Returns either a reference to the hash, or the hash itself, that contains
all of the key/value pairs. For fields that contain file information,
the value contains either the path to the file, or the filehandle (see the
B<set_file_type> method).

I<Restrictions>

This module cannot handle multiple files within I<one> field.
No need to worry, Netscape 2.0 does not support this anyway.

=item B<set_directory>

Used to set the directory where the uploaded files
will be stored (only applies to the I<multipart/form-data> encoding
scheme).

This function should be called I<before> you call
B<parse_form_data>, or else the directory defaults to "/tmp".
If the application cannot write to the directory for whatever
reason, an error status is returned.

I<Return Value>

    0  Failure
    1  Success

=item B<set_file_type>

By default, the uploaded filename is stored in the hash
that is returned by the B<parse_form_data> method. However,
if you pass the string "handle" to this subroutine, the filehandles
to the newly created files are returned.

This function should be called I<before> you call
B<parse_form_data>, or else filenames are returned.

I<Return Value>

    1  Success (always)

=item B<print_form_data>

Used to display all of the key/value pairs.

I<Return Value>

None

=head1 EXAMPLES

Here are four examples that illustrate some of the functions of this module.
You can use these directly in your form processing programs:

=head2 Example 1

    #!/usr/local/bin/perl5

    # Prints out the key/value pairs using the print_form_data
    # method.

    use CGI_Lite;

    $cgi = new CGI_Lite ()

    # The return value from the method is ignored.

    $cgi->parse_form_data ();

    print "Content-type: text/plain", "\n\n";
    $cgi->print_form_data ();

    exit (0);

=head2 Example 2

    #!/usr/local/bin/perl5

    # Simple example that performs the same function as the
    # print_form_data method.

    use CGI_Lite;

    $cgi = new CGI_Lite ();

    # The return value is stored in $data, which contains a
    # reference to the hash. In order to access an element, you have
    # to dereference it (i.e: $$data{'readme'} or %$data).

    $data = $cgi->parse_form_data ();

    print "Content-type: text/plain", "\n\n";

    foreach $key (keys %$data) {
        print $key, " = ", $$data{$key}, "\n";
    }

    exit (0);

=head2 Example 3

    #!/usr/local/bin/perl5

    # Very much like the previous example, except for the fact that
    # the context of the parse_form_data method is an associative
    # array (no need to dereference!)

    use CGI_Lite;

    $cgi = new CGI_Lite ();
    %data = $cgi->parse_form_data ();

    print "Content-type: text/plain", "\n\n";

    foreach $key (keys %data) {
        print $key, " = ", $data{$key}, "\n";
    }

    exit (0);

=head2 Example 4

    #!/usr/local/bin/perl5

    # Simple example that displays the filename associated with
    # the "readme" field in a multiform/form-data request.

    use CGI_Lite;

    $cgi = new CGI_Lite ();
   
    # Die if the directory is invalid (i.e doesn't exist, can't
    # read or write to it, or is not a directory)

    $cgi->set_directory ("/usr/shishir")
        || die "Directory doesn't exist.\n";

    # Tell the module to return filehandles

    $cgi->set_file_type ("handle");
    $data = $cgi->parse_form_data ();

    print "Content-type: text/plain", "\n\n";

    # Dereferences the variable to get a filehandle. Then,
    # iterates through the file, displaying each line to STDOUT.

    $filename = $$data{'readme'};
    while (<$filename>) {
        print;
    }

    # Make sure to close filehandle.

    close ($filename);
    exit (0);

=head2 Example 5

    #!/usr/local/bin/perl5

    # Simply displays the key/value pairs. Here is how the output
    # would look like for multipart/form-data requests:
    #
    #    Content-type: text/plain
    #
    #    full_name = Foo Bar
    #    picture = /usr/shishir/bar.gif_812186386
    #    readme = /usr/shishir/bar.txt_812186386

    use CGI_Lite;

    $cgi = new CGI_Lite ();
    $cgi->set_directory ("/usr/shishir")
        || die "Directory doesn't exist.\n";

    $data = $cgi->parse_form_data ();
    print "Content-type: text/plain", "\n\n";
    $cgi->print_form_data ();

    exit (0);

=head1 SEE ALSO

You should look at the various other Perl 5.0 CGI modules,
and use the one that best suites you. For more information, you
can subscribe to the CGI Module Development List at:

I<CGI-perl@webstorm.com>

Contact: Marc Hedlund I<(hedlund@best.com)> for more information.
This list is B<not> for general CGI support. It only
deals with Perl 5.0 CGI module development.

The CGI modules are maintained by Lincoln Stein
I<(lstein@genome.wi.mit.edu)> and can be found on his WWW site:

I<http://www-genome.wi.mit.edu/WWW/tools/scripting>

=head1 REVISION HISTORY

=over 5

=item v1.4 - October 15, 1995

Added pod style documentation. Now you can see this manual page by doing
the following:

    pod2man CGI_Lite.pm | nroff -man | more

Also, modified the B<parse_form_data> method so that it can return
the actual associative array (if called within an array context).

=item v1.3 - October 12, 1995

Completely modified the B<parse_multipart_data> method. It no longer
reads the multipart message line by line, but rather in small size
blocks (or "chunks"). This also eliminated a B<major> bug that caused
Netscape to hang.

Since some browsers do not send a "\r\n" character string at the end
of header lines, the B<parse_multipart_data> method conditionally checks
for and removes them. This also allows you to emulate a multipart/form-data
request by storing a sample request in a file and piping it to your program:

    cat multipart.txt | test.pl

=item v1.2 - October 12, 1995

Added the B<set_file_type> method to return filehandles for the stored
files.

=item v1.1 - October 10, 1995

The environment variable CONTENT_TYPE is used to determine the type of
encoding scheme. In v1.0, the body of the POST request was parsed.

This module no longer outputs an error message if an invalid directory
is passed to the B<set_directory> method. Instead, it returns a status
of 0 to indicate failure.

=item v1.0 - September 26, 1995

Initial Release

=back

=head1 COPYRIGHT INFORMATION

           Copyright (c) 1995 by Shishir Gundavaram
                    All Rights Reserved

 Permission to use, copy, and  distribute  is  hereby granted,
 providing that the above copyright notice and this permission
 appear in all copies and in supporting documentation.

=cut

###############################################################################

package CGI_Lite;

sub new
{
	my $self = {};

	bless $self;
	$self->initialize ();
	return $self;
}

sub initialize
{
	my ($self) = @_;

	$self{'multipart_directory'} = undef;
	$self{'default_directory'} = "/tmp";
	$self{'file_type'} = "name";

	$self{'form_data'} = {};
}

sub set_directory
{
	my ($self, $directory) = @_;

	stat ($directory);

	if ( (-d _) && (-e _) && (-r _) && (-w _) ) {
		$self{'multipart_directory'} = $directory;
		return (1);

	} else {
		return (0);
	}
}

sub set_file_type
{
	my ($self, $type) = @_;

	if ($type =~ /^handle$/i) {
		$self{'file_type'} = $type;
	} else {
		$self{'file_type'} = 'name';
	}

	return (1);
}

sub parse_form_data
{
	my ($self) = @_;
	my ($request_method, $content_length, $content_type, $query_string,
	    $first_line, $multipart_boundary, $post_data);
        $post_data = '';

	$request_method = $ENV{'REQUEST_METHOD'};
	$content_length = $ENV{'CONTENT_LENGTH'};
	$content_type   = $ENV{'CONTENT_TYPE'};

	if ($request_method =~ /^(get|head)$/i) {

		$query_string = $ENV{'QUERY_STRING'};
		$self->decode_url_encoded_data (\$query_string);

		return wantarray ? 
		      (%{$$self{'form_data'}}) : ($$self{'form_data'});

	} elsif ($request_method =~ /^post$/i) {

		if ($content_type eq "application/x-www-form-urlencoded") {
			read (STDIN, $post_data, $content_length);
			$self->decode_url_encoded_data (\$post_data);

			return wantarray ? 
			      (%{$$self{'form_data'}}) : ($$self{'form_data'});

		} elsif ($content_type =~ /multipart\/form-data/) {
			($multipart_boundary) =
				$content_type =~ /boundary=(\S+)$/;
			$self->parse_multipart_data ($content_length, 
						     $multipart_boundary);

			return wantarray ? 
			      (%{$$self{'form_data'}}) : ($$self{'form_data'});
		} else {
			$self->return_error (500, "Server Error",
				"Server uses unsupported MIME type for POST.");
		}

	} else {
		$self->return_error (500, "Server Error",
			 	          "Server uses unsupported method.");
	}
}

sub decode_url_encoded_data
{
	my ($self, $form_data) = @_;
	my (@key_value_pairs, $key_value, $key, $value);

	@key_value_pairs = ();

	$$form_data =~ tr/+/ /;
	@key_value_pairs = split (/&/, $$form_data);
		
	foreach $key_value (@key_value_pairs) {
		($key, $value) = split (/=/, $key_value);

		$key   =~ s/%([0-9a-fA-F][0-9a-fA-F])/pack("C", hex($1))/eg;
		$value =~ s/%([0-9a-fA-F][0-9a-fA-F])/pack("C", hex($1))/eg;
	
		if ( defined ($$self{'form_data'}->{$key}) ) {
			$$self{'form_data'}->{$key} 
				= join ("", $$self{'form_data'}->{$key},
					    "\0", $value);
		} else {
			$$self{'form_data'}->{$key} = $value;
		}
	}
}

sub parse_multipart_data
{
	my ($self, $total_bytes, $boundary) = @_;
	my ($boundary_length, $block_size, $previous_block, $byte_count,
	    $time, @data_stream, $field_name, $file, $time, $bytes_left,
	    $combination);

	$boundary_length = length ($boundary);
	$block_size = $boundary_length * 2;
	$previous_block = undef;

	$byte_count = 0;
        $time = time;

	while (<STDIN>) {
		$byte_count += length;
		$_ = join ("", $previous_block, $_);
		undef $previous_block;

                if (/[Cc]ontent-disposition/) {
                        undef @data_stream;

                        ($field_name) = /name="([^"]+)"/;

                        if ( ($file) = /filename="(\S+)"/) {
			       $file = substr ($file, rindex ($file, "/") + 1);
                               $file = join ("_", $file, $time);
                        }

			$_ = <STDIN>;
			$byte_count += (/\r/) ? 2 : 1;
		
		        while ($byte_count < $total_bytes) {
				$bytes_left = $total_bytes - $byte_count;
				$block_size = $bytes_left if 
						($bytes_left < $block_size);

				read (STDIN, $_, $block_size);
				$combination = join ("", $previous_block, $_);
				$byte_count += $block_size;

				if ($combination =~ /\n{0,1}$boundary/o) {
					push (@data_stream, $`);
					$previous_block = $';
					last;
				} else {
					push (@data_stream, $previous_block)
					      if ( defined ($previous_block) );
					$previous_block = $_;
				}				
			}

			$data_stream[$#data_stream] =~ s/\r{0,1}\n-{1,2}//;

			if ( defined ($file) ) {
				if ($self{'multipart_directory'}) {
					$file = join ("/",
						  $self{'multipart_directory'},
						  $file);
				} else {
					$file = join ("/",
						  $self{'default_directory'},
						  $file);
				}

				open  (DATA, ">" . $file);
				print DATA @data_stream;
				close (DATA);

				if ($self{'file_type'} eq "handle") {
					open (DATA, "<" . $file);
					$$self{'form_data'}->{$field_name} = 
							\*DATA;
				} else {
					$$self{'form_data'}->{$field_name} = 
							$file;
				}
			} else {
				$$self{'form_data'}->{$field_name} = 
						join ("", @data_stream);
			}
		}
		last if ($byte_count >= $total_bytes);
	}
}

sub print_form_data
{
	my ($self) = @_;
	my ($key);

	foreach $key (keys %{$$self{'form_data'}}) {
		print $key, " = ", $$self{'form_data'}->{$key}, "\n";
	}
}

sub return_error
{
        my ($self, $status, $keyword, $message) = @_;

        print "Content-type: text/html", "\n";
        print "Status: ", $status, " ", $keyword, "\n\n";

        print "<TITLE>", "CGI Program - Unexpected Error", "</TITLE>", "\n";
	print "<H1>", $keyword, "</H1>", "\n";
	print "<HR>", $message, "<HR>", "\n";
	print "Please contact the webmaster for more information.", "\n";

        exit(1);
}

1;

