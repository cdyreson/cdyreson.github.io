package Persistent;

  use Carp;

=head1 NAME Persistent

=head1 DESCRIPTION

Routines to make an object persistent.  All that has to happen is
to keep the 'local' instance values in a 'value' field.  Then
this class will make the resulting object persistent.

=head1 METHODS

=head2 value()

Return the value.

=cut

#---------------------------------------------------------------------
  sub value {
    my ($self) = @_;
    return $self->{'value'};
    }
#---------------------------------------------------------------------

=head2 swizzle(string b, integer index)

=over 4

=item * b

- The string to convert from

=item * index

- The swizzle index

=back

Swizzle a stored value

=cut

#---------------------------------------------------------------------
  sub swizzle {
    my ($b, $index) = @_;
    my ($s) = $self->new();
    $s->{'value'} = _rebuild($b, $index);
    return $s;
    }
#---------------------------------------------------------------------

=head2 fromBytes(string b)

=over 4

=item * b

- The string to convert from.

=back

Construct a new object from a byte image.

=cut

#---------------------------------------------------------------------
  sub fromBytes {
    my ($self, $b) = @_;
    return _rebuild($b);
    }
#---------------------------------------------------------------------

=head2 toBytes()

Generate a byte image of this object

=cut

#---------------------------------------------------------------------
  sub toBytes {
    my ($self) = @_;
    return _flatten($self);
    }
#---------------------------------------------------------------------

#my $SEPARATOR = '#!_';
#my $REPLACEMENT = '\#\!\_';
#my $SEPARATOR = "\n\e\n";
#my $REPLACEMENT = '\n\e\n';
my $SEPARATOR_START = ":";
my $SEPARATOR_END = ";";
my $REPLACEMENT_START = '(';
my $REPLACEMENT_END = ')';

sub _flatten {
  my ($r, $index) = @_;
  $index = 1 unless defined $index;
  my $separator = "$SEPARATOR_START$index$SEPARATOR_END";
  my $innerSeparator = "$SEPARATOR_START" . ($index + 1) . "$SEPARATOR_END";

  # exit if nothing to flatten
  return if !defined $r;

  if (!ref($r)) {
    # do not allow the separator to be in the string!!!!
    $r =~ s/\Q$SEPARATOR_START\E(\d+)\Q$SEPARATOR_END\E/$REPLACEMENT_START$1$REPLACEMENT_END/g;
    return "VALUE$separator$r";
    }

  if (ref($r) eq "HASH") {
    my $thing;
    my @fields = ();
    foreach $thing (keys %$r) {
      push @fields, &_flatten($thing, $index + 2);
      push @fields, &_flatten($$r{$thing}, $index + 2);
      }
    $r = join($innerSeparator, @fields);
    return "HASH$separator$r";
    }

  if (ref($r) eq "ARRAY") {
    my $thing;
    my @result = ();
    foreach $thing (@{ $r }) {
      my ($flat) = &_flatten($thing, $index + 2);
      push @result, $flat;
      }
    $r = join($innerSeparator, @result);
    return "ARRAY$separator$r";
    }

  # has to be an object!
  # Do the body of the object as a hash table
  my @fields = ();
  foreach my $thing (keys %$r) {
    push @fields, &_flatten($thing, $index + 2);
    push @fields, &_flatten($r->{$thing}, $index + 2);
    }
  return ref($r) . "$separator" . join($innerSeparator, @fields);
}

sub _rebuild {
  my ($text, $index) = @_;
  $index = 1 unless defined $index;

  my $separator = "$SEPARATOR_START$index$SEPARATOR_END";
  return if !defined $text;
  my ($kind, $rest) = split($separator, $text); 
  my $innerSeparator = "$SEPARATOR_START" . ($index + 1) . "$SEPARATOR_END";

  if ($kind eq "VALUE") {
    return '' unless defined $rest;
    $rest =~ s/\Q$REPLACEMENT_START\E(\d+)\Q$REPLACEMENT_END\E/$SEPARATOR_START$1$SEPARATOR_END/g;
    return $rest;
    }

  if ($kind eq "ARRAY") {
    my @array = split($innerSeparator, $rest);
    my $i;
    for ($i = 0; $i < scalar @array; $i++) {
      $array[$i] = &_rebuild($array[$i], $index + 2);
      }
    return \@array; 
    }

  if ($kind eq "HASH") {
    my %hash = ();
    my @values = ();
    @values = split($innerSeparator, $rest);
    while (scalar @values) {
      my $key = &_rebuild(shift @values, $index + 2);
      my $value = &_rebuild(shift @values, $index + 2);
      #$hash{$key} = $value if defined $value;
      $hash{$key} = $value;
      }
    return \%hash;
    }

  # has to be an object, assume it can be swizzled!!!!
  my $self = _rebuild("HASH$separator$rest", $index);
  return bless $self, $kind;
}

1;
