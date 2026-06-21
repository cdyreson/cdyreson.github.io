
=head1 NAME TextDB

A class that ties a hash table to a text database.

=head1 SYNOPSIS

    use TextDB;
    tie %textdb, TextDB, $text;
    # Use the %textdb array.
    untie %textdb;

=head1 DESCRIPTION

B<TextDB> is a module which allows a Text database to be tied to 
a hash table.

=cut

package TextDB;

 use strict;
 use Carp;

=head1 TIEHASH

Ties the textdb.

=cut

#my $SEPARATOR = "\n\e\n_";
my $SEPARATOR = '#!_';
my $REPLACEMENT = '\#\!\_';

sub new { &TIEHASH(@_); }

sub TIEHASH {
  my $type = shift;
  my ($text) = @_;
  my $self = {};
  my %h = ();
  if ($text) { %h = %{ _rebuild($text, 1) } };
  $self->{'href'} = \%h;
  bless $self, $type;
}
 
sub load {
  my ($self, $fileName) = @_;
  croak "must give a fileName\n" unless $fileName;
  open (FILE, "<$fileName") || croak "could not open '$fileName' for reading\n";
  # slurp file
  my @lines = <FILE>;
  close FILE;
  $self->{'href'} = _rebuild(join('',@lines), 1);
}

sub save {
  my ($self, $fileName, $fileMode) = @_;
  croak "must give a fileName\n" unless $fileName;
  open (FILE, ">$fileName") || croak "could not open '$fileName' for writing\n";
  print FILE _flatten($self->{'href'}, 1);
  close FILE;
  chmod $fileMode, $fileName;
}
 
sub toString {
  my ($self) = @_;
  my $href = $self->{'href'};
  return _flatten($href, 1);
}

sub CLEAR {
  my ($self) = @_;
  my %h = {};
  $self->{'href'} = \%h;
}
 
sub KEYS {
  my ($self) = @_;
  my $href = $self->{'href'};
  return keys %$href;
}
 
sub STORE {
  my ($self, $key, $value) = @_;
  my $href = $self->{'href'};
  $$href{$key} = $value;
}
 
sub NEXTKEY { 
  my ($self) = @_;
  my $href = $self->{'href'};
  each %$href;
}
 
sub FIRSTKEY { 
  my ($self) = @_;
  my $href = $self->{'href'};
  my ($a) = scalar keys %$href;
  each %$href;
}
 
sub FETCH {
  my ($self, $key) = @_;
  my $href = $self->{'href'};
  return $$href{$key};
}
 
sub _flatten {
  my ($r, $index) = @_;
  my $separator = "$SEPARATOR" . ($index);
  my $innerSeparator = "$SEPARATOR" . ($index + 1);

  # exit if nothing to flatten
  return if !defined $r;

  if (!ref($r)) {
    # do not allow the separator to be in the string!!!!
    $r =~ s/$SEPARATOR/$REPLACEMENT/g;
    return "VALUE$separator$r";
    }

  if (ref($r) eq "HASH") {
    my $thing;
    my @fields = ();
    foreach $thing (keys %$r) {
      push @fields, _flatten($thing, $index + 2);
      push @fields, _flatten($$r{$thing}, $index + 2);
      }
    $r = join($innerSeparator, @fields);
    return "HASH$separator$r";
    }

  if (ref($r) eq "ARRAY") {
    my $thing;
    my @result = ();
    foreach $thing (@{ $r }) {
      push @result, _flatten($thing, $index + 2);
      }
    $r = join($innerSeparator, @result);
    return "ARRAY$separator$r";
    }

  # should never get here
  croak "unknown type when flattening!!!!";

}

sub _rebuild {
  my ($text, $index) = @_;

  my $separator = "$SEPARATOR$index";
  my ($kind, $rest) = split($separator, $text); 
  my $innerSeparator = "$SEPARATOR" . ($index + 1);

  if ($kind eq "VALUE") {
    return $rest;
    }

  if ($kind eq "ARRAY") {
    my @array = split($innerSeparator, $rest);
    my $i;
    for ($i = 0; $i < scalar @array; $i++) {
      $array[$i] = _rebuild($array[$i], $index + 2);
      }
    return \@array; 
    }

  if ($kind eq "HASH") {
    my %hash = ();
    my @values = ();
    @values = split($innerSeparator, $rest);
    while (scalar @values) {
      my $key = _rebuild(shift @values, $index + 2);
      my $value = _rebuild(shift @values, $index + 2);
      $hash{$key} = $value;
      }
    return \%hash;
    }

  else { croak "unknown type when rebuilding!!!!"; }

}

sub DELETE { 
  my ($self, $key) = @_;
  my $href = $self->{'href'};
  delete $$href{$key};
  }
 
sub EXISTS { 
  my ($self, $key) = @_;
  my $href = $self->{'href'};
  return defined $$href{$key};
  }
 
sub DESTROY {
  my ($self) = @_;
  undef %{ $self->{'href'} };
}
 
1;
