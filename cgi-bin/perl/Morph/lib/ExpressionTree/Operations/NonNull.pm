package ExpressionTree::Operations::NonNull;

  use strict;

=head1 NAME ExpressionTree::Operations::NonNull;

=head1 DESCRIPTION

This class represents a NONNULL operation.

=head1 METHODS

=head2 new(ExpressionTree::Variable var)

=over 4

=item * var

The variable to test.

=back

Create a new NONNULL operation

=cut

#---------------------------------------------------------------------
  sub new {
    my $type = shift;
    my $self = {}; 
    $self->{'var'} = shift;
    $self->{'resultType'} = 'Boolean';
    bless $self, $type;
    }
#---------------------------------------------------------------------

=head2 validate(var)

#---------------------------------------------------------------------
  sub validate {
    my $type = shift;

    return "No variable for NONNULL." unless scalar(@_);
    return undef; # successful validation!
    }
#---------------------------------------------------------------------

=head2 resultType()

Return the type of result.

=cut

#---------------------------------------------------------------------
  sub resultType {
    my ($self) = @_;
    return $self->{'resultType'};
    }
#---------------------------------------------------------------------

=head2 dump($indent)

Dump this node.

=cut

#---------------------------------------------------------------------
  sub dump {
    my ($self, $indent) = @_;
    $indent = "  " unless defined $indent;
    my ($extra) = '  ';
    my ($op) = 'NONNULL';

    return "$indent $op\n   " . $self->{'var'}->dump("$indent$extra") . "\n";
    }
#---------------------------------------------------------------------

=head2 evaluate(State state)

Evaluate the NONNULL operation returning a boolean result.

=cut

my $True = new Boolean('true');
my $False = new Boolean('false');

#---------------------------------------------------------------------
  sub evaluate {
    my ($self, $state) = @_;

    my $value = $self->{'var'}->evaluate($state);
    return $False unless defined $value;
    return $False if ref($value) eq 'Null';
    return $True;
    }
#---------------------------------------------------------------------

1;
