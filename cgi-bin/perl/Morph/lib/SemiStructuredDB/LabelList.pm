package LabelList;

=head1 NAME LabelList

Class encapsulating an L<SemiStructuredDB::Label> list in a Database.

=head1 DESCRIPTION

An LabelList is a list of L<Label>s.

=cut

#---------------------------------------------------------------------

=head1 METHODS

=head2 new(Label[] \@labels)

Construct a list from an array of Labels

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {};
    $self->{'class'} = $type;
    my ($labels) = @_;
    $self->{'value'} = $labels;
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 image()

Convert to a nice String image for dumping

=cut

#---------------------------------------------------------------------
  sub image {
    my ($self) = @_;
    my ($s) = "";
    my ($toadd) = ""; 
    my ($arrayref) = $self->{'value'};
    foreach $label (@$arrayref) {
      $s .= $toadd;
      $s .= $label->toString();
      $toadd = ", "; 
      }
    return $s;
    }
#---------------------------------------------------------------------

=head2 toBytes()

Returns a byte image of LabelList.

=cut

#---------------------------------------------------------------------
  sub toBytes {
    my ($self) = @_;
    return Swizzle::_flatten($self->{'value'});
    }
#---------------------------------------------------------------------

=head2 fromBytes(Byte[] $b)

=over 4

=item * Byte[] $b 

- The sequence of bytes

=back

Build a new LabelList from a byte image.

=cut

#---------------------------------------------------------------------
  sub fromBytes {
    my ($b) = @_;
    return new LabelList(Swizzle::_rebuild($b)); 
    }
#---------------------------------------------------------------------

=head2 toLabelArrayRef()

Build an array of Labels that is the sequence.

=cut

#---------------------------------------------------------------------
  sub toLabelArrayRef {
    my ($self) = @_;
    my $arrayref = $self->{'value'};
    return $arrayref;
    }
#---------------------------------------------------------------------

=head2 insert()

Add a label to the end of the list

=cut

#---------------------------------------------------------------------
  sub insert {
    my ($self, $label) = @_;
    my $arrayref = $self->{'value'};
    push @$arrayref, $label;
    }
#---------------------------------------------------------------------

1;
