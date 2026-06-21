package CNF;

  use strict;
  # inherits the persistent
  @CNF::ISA = qw( Required Persistent );
 
=head1 NAME CNF 

=head1 DESCRIPTION

A formula in Conjunctive Normal Form.

=head1 METHODS

=head2 new($value)

Create a new formula from either a string or a list of conjucts, 
if no values assume false formula.

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    my $s = shift; 
    if (ref($s) eq 'ARRAY') { $self->{'conjuncts'} = $s; }
    else {
      $s =~ s/\s*$//;
      $s =~ s/^\s*//;
      $self->{'conjuncts'} = &_initialize($s);
      }
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 satisfiedBy(other)

Does the other assignment of variables satisfy this one?

=cut

#---------------------------------------------------------------------
  sub satisfiedBy {
    my ($self, $other) = @_;
    my ($thisList) = $self->{'conjuncts'};
    my ($otherList) = $other->{'conjuncts'};
    foreach my $mine (@$thisList) {
      foreach my $theirs (@$otherList) {
        return 1 if &_contains($mine, $theirs);
        }
      }
    return 0;
    }
#---------------------------------------------------------------------

# Determines if the first conjunct contains the second
#---------------------------------------------------------------------
  # A helper function
  sub _contains {
    my ($mine, $theirs) = @_;
    foreach my $key (keys %$theirs) {
      return 0 unless defined $$mine{$key};
      }
    return 1;
    }
#---------------------------------------------------------------------
  
=head2 AND(other)

Determine which conjucts are common between the two.

=cut

#---------------------------------------------------------------------
  sub AND {
    my ($self, $other) = @_;
    my ($thisList) = $self->{'conjuncts'};
    my ($otherList) = $other->{'conjuncts'};
    my @constructed = ();
    foreach my $mine (@$thisList) {
      foreach my $theirs (@$otherList) {
        if (&_contains($mine, $theirs)) { push @constructed, $theirs; }
        elsif (&_contains($theirs, $mine)) { push @constructed, $mine; }
        }
      }

    # Results in empty property if no conjuncts left
    return undef unless scalar(@constructed);
    
    # OK, build new CNF
    return new CNF(\@constructed);
    }
#---------------------------------------------------------------------

=head2 OR(other)

Put all the conjuncts together.

=cut

#---------------------------------------------------------------------
  sub OR {
    my ($self, $other) = @_;
    my ($thisList) = $self->{'conjuncts'};
    my ($otherList) = $other->{'conjuncts'};
    my @constructed = ();
    foreach my $mine (@$thisList) {
      foreach my $theirs (@$otherList) {
        my %temp = ();
        foreach my $key (keys %$mine) { $temp{$key} = 1; }
        foreach my $key (keys %$theirs) { $temp{$key} = 1; }
        push @constructed, \%temp;
        }
      }
    # Results in empty property if no conjuncts left
    return undef unless scalar(@constructed);
    
    # OK, build new CNF
    return new CNF(\@constructed);
    }
#---------------------------------------------------------------------
  
=head2 equalTo()

Does this contain the other, and does the other contain this!

=cut

#---------------------------------------------------------------------
  sub equalTo {
    my ($self, $other) = @_;
    return 0 unless $self->satisfiedBy($other);
    return 0 unless $other->satisfiedBy($self);
    return 1;
    }
#---------------------------------------------------------------------

=head2 contains()

Does this contain the other

=cut

#---------------------------------------------------------------------
  sub contains {
    my ($self, $other) = @_;
    return 0 unless $self->satisfiedBy($other);
    return 1;
    }
#---------------------------------------------------------------------

=head2 dump()

Return a formatted dump of the object.

=cut

#---------------------------------------------------------------------
  sub dump {
    my ($self) = @_;
    # must have a type
    return ref($self);
    }
#---------------------------------------------------------------------

=head2 _initialize(string s)

Initialize CNF structure with the passed string value.
We assume the formula is of the form xxx, yyy, zzz, ...

=cut

#---------------------------------------------------------------------
  sub _initialize {
    my ($s) = @_;

    # split each conjunct
    my @vars = split(',', $s);
    my %constructed = ();
    foreach my $x (@vars) {
      #strip white space
      $x =~ s/\s*$//;
      $x =~ s/^\s*//;

      # disallow the empty conjunct
      next if $x eq '';
      $constructed{$x} = 1;
      } 
    return [\%constructed];
    }
#---------------------------------------------------------------------

=head2 match()

Match this one against another.

=cut

#---------------------------------------------------------------------
  sub match {
    my ($self, $other) = @_;
    return $self->satisfiedBy($other);
    }
#---------------------------------------------------------------------

=head2 collapse()

Collapse this one with another.

=cut

#---------------------------------------------------------------------
  sub collapse {
    my ($self, $other) = @_;
    return $self->AND($other);
    }
#---------------------------------------------------------------------

=head2 coalesce()

Coalesce this one with another.

=cut

#---------------------------------------------------------------------
  sub coalesce {
    my ($self, $other) = @_;
    return $self->OR($other);
    }
#---------------------------------------------------------------------

=head2 slice()

Slice this one using another.

=cut

#---------------------------------------------------------------------
  sub slice {
    my ($self, $other) = @_;
    return $self->AND($other);
    }
#---------------------------------------------------------------------

=head2 presentation()

Present this string value.

=cut

#---------------------------------------------------------------------
  sub presentation {
    my ($self) = @_;
    my ($conjuncts) = $self->{'conjuncts'};
    my @temp = ();
    foreach my $conjunct (@$conjuncts) {
      push @temp, join(", ", keys %$conjunct);
      } 
    return join(" OR ", @temp);
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

Self evaluating

=cut

#---------------------------------------------------------------------
  sub evaluate {
    my ($self) = @_;
    return $self;
    }
#---------------------------------------------------------------------

1;
