#!/usr/local/bin/perl -I/world/httpd/cgi-bin/perl/pm 

my $SEPARATOR = "\f";
my $INNERSEPARATOR = "\e";
 
sub encrypt {
  local ($text) = @_;

  #$text =~ tr/a-z/!o-za-m/;
  #$text =~ tr/\/\-\e\f\./n:;*\?/;
  return $text;
  }

sub decrypt {
  local ($text) = @_;

  #$text =~ tr/n:;*\?/\/\-\e\f\./;
  #$text =~ tr/!o-za-m/a-z/;
  return $text;
  }

sub encrypt2 {
  local ($text) = @_;
  local (@hidden,$hidden);
  my ($increment, $i, $previous, $next);
  my ($msg);

  # do forty characters at a time
  $increment = 40;
  $i = 1;
  $previous = 0;
  $msg = "";
  while ($previous < length($text)) {
    $next = $i * $increment;

    # do the decryption on this part
    open (JOE, "| des -e -k alpha > /tmp/Tmp$$");
    print JOE substr($text,$previous,$increment);
    close JOE;

    open (JOE, "</tmp/Tmp$$");
    @hidden = <JOE>;
    close JOE;
    $hidden = join("\n", @hidden);
    #unlink "/tmp/Tmp$$";
    $msg .= $hidden;

    $previous = $next;
    $i++;
    }

  return $msg;
}

sub decrypt2 {
  my ($text) = @_;
  my (@hidden, $hidden);
  my ($increment, $i, $previous, $next);
  my ($msg);

  # do forty characters at a time
  $increment = 40;
  $i = 1;
  $previous = 0;
  $msg = "";
  while ($previous < length($text)) {
    $next = $i * $increment;

    # do the decryption on this part 
    open (JOE, "| des -d -k alpha > /tmp/Tmp$$");
    print JOE substr($text,$previous,$increment);
    close JOE;

    open (JOE, "</tmp/Tmp$$"); 
    @hidden = <JOE>;
    close JOE;
    $hidden = join("\n", @hidden);
    #unlink "/tmp/Tmp$$";
    $msg .= $hidden;

    $previous = $next;
    $i++;
    }

  return $msg;
}

sub decryptHidden {
  my ($input) = @_;
  my (@vars, $var, $param, $message);
  %hidden = ();
  
  $message = &decrypt($input);
  @vars = split($SEPARATOR, $message);
  foreach $var (@vars) {
    ($param, $value) = split($INNERSEPARATOR, $var);
    $hidden{$param} = $value;  
    }
}

sub hidden {
  my ($name, $value) = @_;
  $query->param($name, $value);
  return $query->hidden($name, $value);
}

sub encryptHidden {
  my (@vars, $key);
  
  @vars = ();
  foreach $key (keys %hidden) {
    #print "$key is " . $hidden{$key};
    push (@vars, "$key$INNERSEPARATOR" . $hidden{$key});
    }
  $tmp = join("$SEPARATOR",@vars);
  #print "\nzzzzzzz\n" . &decrypt(&encrypt($tmp));
  #print @vars;
  return &encrypt(join($SEPARATOR, @vars));
}

1;
