#########################################################################
#
#      This file was generated using Parse::Yapp version 0.15.
#
#          Don't edit this file, use source file instead.
#
#               ANY CHANGE MADE HERE WILL BE LOST !
#
#########################################################################
package Parser::LoadDB;
use vars qw ( @ISA );
use strict;

@ISA= qw ( Parse::Yapp::Driver );

use Parse::Yapp::Driver;


require 5.004;

use Carp;
use OODatabase;



sub new {
        my($class)=shift;
        ref($class)
    and $class=ref($class);

    my($self)=$class->SUPER::new( yyversion => '0.15',
                                  yystates =>
[
	{#State 0
		ACTIONS => {
			"insert" => 2
		},
		DEFAULT => -2,
		GOTOS => {
			'statementList' => 1,
			'statement' => 3,
			'start' => 4
		}
	},
	{#State 1
		DEFAULT => -1
	},
	{#State 2
		ACTIONS => {
			"edge" => 6
		},
		GOTOS => {
			'insertStatement' => 5
		}
	},
	{#State 3
		ACTIONS => {
			"insert" => 2
		},
		DEFAULT => -2,
		GOTOS => {
			'statementList' => 7,
			'statement' => 3
		}
	},
	{#State 4
		ACTIONS => {
			'' => 8
		}
	},
	{#State 5
		ACTIONS => {
			"semi-colon" => 9
		}
	},
	{#State 6
		DEFAULT => -5,
		GOTOS => {
			'@1-1' => 10
		}
	},
	{#State 7
		DEFAULT => -3
	},
	{#State 8
		DEFAULT => -0
	},
	{#State 9
		DEFAULT => -4
	},
	{#State 10
		ACTIONS => {
			"identifier" => 11,
			"timeInterval" => 12,
			"integer" => 13,
			"object" => 16,
			"string" => 14
		},
		GOTOS => {
			'literal' => 15,
			'node' => 17
		}
	},
	{#State 11
		DEFAULT => -24
	},
	{#State 12
		DEFAULT => -25
	},
	{#State 13
		DEFAULT => -26
	},
	{#State 14
		DEFAULT => -27
	},
	{#State 15
		DEFAULT => -23
	},
	{#State 16
		DEFAULT => -22
	},
	{#State 17
		ACTIONS => {
			"comma" => 18
		}
	},
	{#State 18
		DEFAULT => -6,
		GOTOS => {
			'@2-4' => 19
		}
	},
	{#State 19
		ACTIONS => {
			"identifier" => 11,
			"timeInterval" => 12,
			"integer" => 13,
			"object" => 16,
			"string" => 14
		},
		GOTOS => {
			'literal' => 15,
			'node' => 20
		}
	},
	{#State 20
		DEFAULT => -7,
		GOTOS => {
			'@3-6' => 21
		}
	},
	{#State 21
		ACTIONS => {
			"left parenthesis" => 24
		},
		DEFAULT => -9,
		GOTOS => {
			'propertyList' => 22,
			'properties' => 23
		}
	},
	{#State 22
		DEFAULT => -10
	},
	{#State 23
		DEFAULT => -8
	},
	{#State 24
		ACTIONS => {
			"identifier" => 25
		},
		GOTOS => {
			'property' => 26
		}
	},
	{#State 25
		DEFAULT => -14,
		GOTOS => {
			'@4-1' => 27
		}
	},
	{#State 26
		ACTIONS => {
			"comma" => 29
		},
		DEFAULT => -12,
		GOTOS => {
			'propertyListContinues' => 28
		}
	},
	{#State 27
		ACTIONS => {
			"colon" => 30,
			"exclamation mark" => 33
		},
		GOTOS => {
			'optionalProperty' => 31,
			'propertyContinues' => 32,
			'requiredProperty' => 34
		}
	},
	{#State 28
		ACTIONS => {
			"right parenthesis" => 35
		}
	},
	{#State 29
		ACTIONS => {
			"identifier" => 25
		},
		GOTOS => {
			'property' => 36
		}
	},
	{#State 30
		DEFAULT => -20,
		GOTOS => {
			'@6-1' => 37
		}
	},
	{#State 31
		DEFAULT => -17
	},
	{#State 32
		DEFAULT => -15
	},
	{#State 33
		DEFAULT => -18,
		GOTOS => {
			'@5-1' => 38
		}
	},
	{#State 34
		DEFAULT => -16
	},
	{#State 35
		DEFAULT => -11
	},
	{#State 36
		ACTIONS => {
			"comma" => 29
		},
		DEFAULT => -12,
		GOTOS => {
			'propertyListContinues' => 39
		}
	},
	{#State 37
		ACTIONS => {
			"identifier" => 11,
			"timeInterval" => 12,
			"integer" => 13,
			"string" => 14
		},
		GOTOS => {
			'literal' => 40
		}
	},
	{#State 38
		ACTIONS => {
			"identifier" => 11,
			"timeInterval" => 12,
			"integer" => 13,
			"string" => 14
		},
		GOTOS => {
			'literal' => 41
		}
	},
	{#State 39
		DEFAULT => -13
	},
	{#State 40
		DEFAULT => -21
	},
	{#State 41
		DEFAULT => -19
	}
],
                                  yyrules  =>
[
	[#Rule 0
		 '$start', 2, undef
	],
	[#Rule 1
		 'start', 1, undef
	],
	[#Rule 2
		 'statementList', 0, undef
	],
	[#Rule 3
		 'statementList', 2, undef
	],
	[#Rule 4
		 'statement', 3, undef
	],
	[#Rule 5
		 '@1-1', 0,
sub {
 &_expecting("a from object to insert"); 
}
	],
	[#Rule 6
		 '@2-4', 0,
sub {
 &_expecting("a to object to insert"); 
}
	],
	[#Rule 7
		 '@3-6', 0,
sub {
 &_expecting("a property list"); 
}
	],
	[#Rule 8
		 'insertStatement', 8,
sub {
$Global::SSGraph->addEdge(
        new Edge(new Node($_[3]), 
                new Label([new Region($_[8])]),
                new Node($_[6])
               )
        );
    print "Inserting edge from $_[3] to $_[6]\n";
    
}
	],
	[#Rule 9
		 'properties', 0,
sub {
 my %h = (); return \%h; 
}
	],
	[#Rule 10
		 'properties', 1,
sub {
 return $_[1]; 
}
	],
	[#Rule 11
		 'propertyList', 4,
sub {
 my $h = $_[3]; 
      my ($d, $pair) = @{$_[2]}; 
      my ($v, $w) = @$pair;
      print "list is $d, $w, $v\n";
      my ($value) = Dimensions::convert($d,$v); 
      $value->required() if $w eq 'required';
      $$h{$d} = $value;
      return $h; 
}
	],
	[#Rule 12
		 'propertyListContinues', 0,
sub {
 my %h = (); return \%h; 
}
	],
	[#Rule 13
		 'propertyListContinues', 3,
sub {
 my $h = $_[3]; 
      my ($d, $pair) = @{$_[2]}; 
      my ($v, $w) = @$pair;
      print "list is $d, $w, $v\n";
      my ($value) = Dimensions::convert($d,$v); 
      $value->required() if $w eq 'required';
      $$h{$d} = $value;
      return $h; 
}
	],
	[#Rule 14
		 '@4-1', 0,
sub {
 &_expecting("a property dimension name"); 
}
	],
	[#Rule 15
		 'property', 3,
sub {
 return [lc $_[1], $_[3]]; 
}
	],
	[#Rule 16
		 'propertyContinues', 1,
sub {
 return $_[1]; 
}
	],
	[#Rule 17
		 'propertyContinues', 1,
sub {
 return $_[1]; 
}
	],
	[#Rule 18
		 '@5-1', 0,
sub {
 &_expecting("an exclamation mark"); 
}
	],
	[#Rule 19
		 'requiredProperty', 3,
sub {
 &_expecting("a property dimension value"); 
      return [$_[3], 'required']; 
}
	],
	[#Rule 20
		 '@6-1', 0,
sub {
 &_expecting("a colon"); 
}
	],
	[#Rule 21
		 'optionalProperty', 3,
sub {
 &_expecting("a property dimension value"); 
      return [$_[3], 'optional']; 
}
	],
	[#Rule 22
		 'node', 1,
sub {
 return $_[1]; 
}
	],
	[#Rule 23
		 'node', 1,
sub {
 return $_[1]; 
}
	],
	[#Rule 24
		 'literal', 1,
sub {
 return $_[1]; 
}
	],
	[#Rule 25
		 'literal', 1,
sub {
 return $_[1]; 
}
	],
	[#Rule 26
		 'literal', 1,
sub {
 return $_[1]; 
}
	],
	[#Rule 27
		 'literal', 1,
sub {
 return $_[1]; 
}
	]
],
                                  @_);
    bless($self,$class);
}



my $ErrorMessage = '';
my $toHere = '';
sub _Error {
 my ($parser) = shift;

 print "<PRE>\n";
 print $toHere;
 print " <--- Syntax error.\n";
 print "------------------------------------------------------\n";
 print " Perhaps the problem is: $ErrorMessage.\n";
 print "------------------------------------------------------\n";
 $parser->_dumpRest();
 print "</PRE>\n";
 exit(1);
}

# cleans the white space from around a string
sub _cleanWS {
  my ($value) = @_;
  #chew up white space at both ends (parsing of fields depends on this!)
  $value =~ s/^[\s\n]+//;
  $value =~ s/[\s\n]+$//;
  return $value;
}

sub _dumpRest {
    my($parser)=shift;

    my $thing;
    foreach $thing (@{$parser->{'tokens'}}) {
      print @$thing[2] if defined @$thing[1];
    }
}

sub _Lexer {
    my ($parser) = shift;

    #die "end of inputer" unless scalar @{$parser->{'tokens'}};
    return ['', undef, undef] unless scalar @{$parser->{'tokens'}};
    #print "_lexer " . @{@{$parser->{'tokens'}}[0]}[1] . "\n";
    my $token = shift @{$parser->{'tokens'}};
    $toHere .= @$token[2] if defined @$token[2];
    return @{ $token };
}

sub Run {
    my($self)=shift;

    $self->_tokenize();
    $self->YYParse( yylex => \&_Lexer, yyerror => \&_Error );

}

sub _tokenize {
  my ($self) = shift;

  my @tokens = ();
  my @lines = <STDIN>;
  my $s = '';
  foreach (@lines) { $s .= $_ unless /^\s*\;/; }
  my %keywords = ( 
     'insert' => 'insert', 
     'add' => 'add', 
     'edge' => 'edge', 
     'dimension' => 'dimension',
     'any' => 'any',
     'root' => 'root',
     );

  # gobble white space
  $s =~ s/^\s*//;
  my $originalText = $&;
  while ($s) {
    #print $s;
    my %token = ();
    $token{'kind'} =  'ignore';

    if ($s =~ s/^'([^']*)'//) {
      $token{'kind'} =  'string';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^\[(\w+(\/\w+\/\d+)?\s*-\s*\w+(\/\w+\/\d+)?)\s*\]//) {
      $token{'kind'} =  'timeInterval';
      $token{'text'} =  _cleanWS($1);
      }
    elsif ($s =~ s/^(\d+)\s*//) {
      $token{'kind'} =  'integer';
      $token{'text'} =  _cleanWS($1);
      }
    elsif ($s =~ s/^(\.)//) {
      $token{'kind'} =  'period';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q,\E)//) {
      $token{'kind'} =  'comma';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q:\E)//) {
      $token{'kind'} =  'colon';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q!\E)//) {
      $token{'kind'} =  'exclamation mark';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q;\E)//) {
      $token{'kind'} =  'semi-colon';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/(^\))//) {
      $token{'kind'} =  'right parenthesis';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\()//) {
      $token{'kind'} =  'left parenthesis';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q<>\E)//) {
      $token{'kind'} =  'comparison';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^([=><]+)//) {
      $token{'kind'} =  'comparison';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^\Q&\E([A-Za-z0-9_]*)//) {
      $token{'kind'} =  'object';
      $token{'text'} =  _cleanWS('&'.$1);
      }
    elsif ($s =~ s/^([A-Za-z][A-Za-z0-9_]*)//) {
      $token{'text'} =  _cleanWS($1);
      if (defined $keywords{lc $1}) {
        $token{'kind'} =  $keywords{lc $1};
        }
      else {
        $token{'kind'} =  'identifier';
        }
      }
    elsif ($s =~ s/(.)//) {
      $token{'kind'} =  'ignore';
      $token{'text'} =  $1;
      }
    else {
      die "can't possibly reach here";
      }
    $token{'originalText'} = "$originalText$&";
    push @tokens, [$token{'kind'}, $token{'text'}, $token{'originalText'}];
    #print "token $token{'kind'} $token{'text'} $token{'originalText'}\n";
    #print "token $token{'kind'} $token{'text'}\n";
    # gobble white space
    $s =~ s/^\s*//;
    $originalText = $&;
    }
  push @tokens, ['', undef, undef];
  $self->{'tokens'} = \@tokens;
}

sub _expecting {
  my ($s) = @_;
  $ErrorMessage = "Expecting $s";
  }

1;
