
=head1 NAME Encryption

A class that encapsulates PGP encryption.  The encryption is a bit hokie
since it uses a temporary file, the lack of buffering in the PGP command
makes it difficult to use a pipe.

=head1 SYNOPSIS

    require Encryption.pm;
  
    $msg = "hi there";
    $password = "whatever";
    $encrypted = Encryption::encrypt($msg, $password);
    $decrypted = Encryption::decrypt($encrypted, $password);
    print "$msg should be the same as $decrypted";

=head1 DESCRIPTION

B<Encryption> is a module that encapsulates PGP encryption.

=cut

package Encryption;

 use strict;
 use Carp;

=head1 encrypt

Encrypt a message with the given password, return the encrypted message.

=cut

sub encrypt {
  my ($passwd, $text) = @_;

  # substitute double quotes so that pgp command won't barf
  $passwd =~ s/\"/\'/mg;

  # do the encryption using pgp, creating a temp file
  open (PGP, "| pgp -fat +verbose=0 -z\"$passwd\" >/tmp/Tmp$$");
  print PGP $text;
  close PGP;

  # read the encrpyted message
  open (JOE, "</tmp/Tmp$$");
  my @hidden = <JOE>;
  close JOE;

  # unlink the temp file
  unlink "/tmp/Tmp$$";
  return join("", @hidden);
}

=head1 decrypt

Decrypt an encrypted message with the given password, return the decrypted 
message.

=cut

sub decrypt {
  my ($passwd, $text) = @_;

  # substitute double quotes so that pgp command won't barf
  $passwd =~ s/\"/\'/mg;

  # write temporary file containing encrypted message
  open (JOE, ">/tmp/Tmp$$");
  print JOE $text;
  close JOE;

  # do the decryption using pgp
  open (PGP, "pgp -df +verbose=0 -z\"$passwd\" </tmp/Tmp$$ |");
  my @hidden = <PGP>;
  close PGP;

  unlink "/tmp/Tmp$$";

  return join("", @hidden);
}

1;
