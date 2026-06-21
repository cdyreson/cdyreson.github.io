package Required;

=head1 NAME Required

=head1 DESCRIPTION

Class encapsulating methods on property value requirements, 
it is inherited by lots of other classes.

=head1 METHODS

=head2 isRequired()

Is this value required?

=cut

#---------------------------------------------------------------------
  sub isRequired {
    my ($self) = @_; 
    return defined $self->{'required'};
    }
#---------------------------------------------------------------------

=head2 required()

Make this value be required!

=cut

#---------------------------------------------------------------------
  sub required {
    my ($self) = @_;
    $self->{'required'} = 1;
    }
#---------------------------------------------------------------------

=head2 getValue()

The value is the object itself

=cut

#---------------------------------------------------------------------
  sub getValue {
    my ($self) = @_;
    return $self;
    }
#---------------------------------------------------------------------

1;
