#########################################################################
#
#      This file was generated using Parse::Yapp version 0.15.
#
#          Don't edit this file, use source file instead.
#
#               ANY CHANGE MADE HERE WILL BE LOST !
#
#########################################################################
package Parser::Select;
use vars qw ( @ISA );
use strict;

@ISA= qw ( Parse::Yapp::Driver );

use Parse::Yapp::Driver;


require 5.004;

use Carp;
use ExpressionTree;
use ErrorHandler;
my %Variables;
my %TypeOfVariable;
my $FullDescriptor;
my @From = ();
my @SelectPairs = ();
my @OrderVars = ();
my $PassOne;
my $PassTwo;
my %DefaultProperties = ();


sub new {
        my($class)=shift;
        ref($class)
    and $class=ref($class);

    my($self)=$class->SUPER::new( yyversion => '0.15',
                                  yystates =>
[
	{#State 0
		DEFAULT => -2,
		GOTOS => {
			'start' => 2,
			'@1-0' => 1,
			'passes' => 3
		}
	},
	{#State 1
		ACTIONS => {
			"set" => 5
		},
		DEFAULT => -5,
		GOTOS => {
			'defaults' => 4
		}
	},
	{#State 2
		ACTIONS => {
			'' => 6
		}
	},
	{#State 3
		DEFAULT => -1
	},
	{#State 4
		DEFAULT => -12,
		GOTOS => {
			'select' => 7,
			'@8-0' => 8
		}
	},
	{#State 5
		DEFAULT => -6,
		GOTOS => {
			'@3-1' => 9
		}
	},
	{#State 6
		DEFAULT => -0
	},
	{#State 7
		ACTIONS => {
			"eof" => 10
		}
	},
	{#State 8
		ACTIONS => {
			"select" => 12
		},
		GOTOS => {
			'selectClause' => 11
		}
	},
	{#State 9
		ACTIONS => {
			"default" => 13
		}
	},
	{#State 10
		DEFAULT => -3,
		GOTOS => {
			'@2-4' => 14
		}
	},
	{#State 11
		DEFAULT => -13,
		GOTOS => {
			'@9-2' => 15
		}
	},
	{#State 12
		DEFAULT => -42,
		GOTOS => {
			'@23-1' => 16
		}
	},
	{#State 13
		DEFAULT => -7,
		GOTOS => {
			'@4-3' => 17
		}
	},
	{#State 14
		ACTIONS => {
			"set" => 5
		},
		DEFAULT => -5,
		GOTOS => {
			'defaults' => 18
		}
	},
	{#State 15
		ACTIONS => {
			"from" => -58
		},
		DEFAULT => -56,
		GOTOS => {
			'@29-0' => 19,
			'fromClause' => 20,
			'@28-0' => 21
		}
	},
	{#State 16
		ACTIONS => {
			"*" => 22,
			"aggregate" => 27
		},
		DEFAULT => -87,
		GOTOS => {
			'generalizedAttributeList' => 24,
			'fullDescriptorExpression' => 23,
			'attribute' => 26,
			'@43-0' => 25,
			'generalizedAttribute' => 28,
			'aggregate' => 29
		}
	},
	{#State 17
		ACTIONS => {
			"property" => 30
		}
	},
	{#State 18
		DEFAULT => -12,
		GOTOS => {
			'select' => 31,
			'@8-0' => 8
		}
	},
	{#State 19
		ACTIONS => {
			"from" => 32
		}
	},
	{#State 20
		DEFAULT => -14,
		GOTOS => {
			'@10-4' => 33
		}
	},
	{#State 21
		DEFAULT => -57
	},
	{#State 22
		DEFAULT => -44
	},
	{#State 23
		DEFAULT => -55
	},
	{#State 24
		DEFAULT => -43
	},
	{#State 25
		ACTIONS => {
			"identifier" => 35,
			"left brace" => 36,
			"left parenthesis" => 37
		},
		GOTOS => {
			'propertyList' => 34,
			'descriptorExpression' => 39,
			'descriptor' => 38
		}
	},
	{#State 26
		DEFAULT => -49
	},
	{#State 27
		DEFAULT => -51,
		GOTOS => {
			'@25-1' => 40
		}
	},
	{#State 28
		ACTIONS => {
			"comma" => 42
		},
		DEFAULT => -46,
		GOTOS => {
			'attributeList' => 41
		}
	},
	{#State 29
		DEFAULT => -50
	},
	{#State 30
		DEFAULT => -8,
		GOTOS => {
			'@5-5' => 43
		}
	},
	{#State 31
		DEFAULT => -4
	},
	{#State 32
		DEFAULT => -59,
		GOTOS => {
			'@30-2' => 44
		}
	},
	{#State 33
		ACTIONS => {
			"where" => 46
		},
		DEFAULT => -121,
		GOTOS => {
			'whereClause' => 45
		}
	},
	{#State 34
		DEFAULT => -120
	},
	{#State 35
		DEFAULT => -119
	},
	{#State 36
		ACTIONS => {
			"identifier" => -19
		},
		DEFAULT => -17,
		GOTOS => {
			'@12-1' => 47,
			'@13-1' => 48
		}
	},
	{#State 37
		DEFAULT => -110,
		GOTOS => {
			'@60-1' => 49
		}
	},
	{#State 38
		DEFAULT => -109
	},
	{#State 39
		ACTIONS => {
			"period" => 53,
			"question mark" => 52,
			"*" => 50,
			"+" => 51
		},
		DEFAULT => -88
	},
	{#State 40
		ACTIONS => {
			"left parenthesis" => 54
		}
	},
	{#State 41
		DEFAULT => -45
	},
	{#State 42
		DEFAULT => -47,
		GOTOS => {
			'@24-1' => 55
		}
	},
	{#State 43
		ACTIONS => {
			"left brace" => 36
		},
		GOTOS => {
			'propertyList' => 56
		}
	},
	{#State 44
		ACTIONS => {
			"collapse" => 58,
			"property" => 70,
			"roots" => 64,
			"nodes" => 66,
			"match" => 71,
			"coalesce" => 69,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'coalescedValue' => 57,
			'slicedPath' => 59,
			'propertyValue' => 61,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'node' => 65,
			'table' => 67,
			'path' => 68,
			'valueSpecification' => 72
		}
	},
	{#State 45
		DEFAULT => -15,
		GOTOS => {
			'@11-6' => 74
		}
	},
	{#State 46
		DEFAULT => -122,
		GOTOS => {
			'@62-1' => 75
		}
	},
	{#State 47
		ACTIONS => {
			"right brace" => 76
		}
	},
	{#State 48
		ACTIONS => {
			"identifier" => 78
		},
		GOTOS => {
			'property' => 77
		}
	},
	{#State 49
		ACTIONS => {
			"identifier" => 35,
			"left brace" => 36,
			"left parenthesis" => 37
		},
		GOTOS => {
			'propertyList' => 34,
			'descriptor' => 38,
			'descriptorExpression' => 79
		}
	},
	{#State 50
		DEFAULT => -114
	},
	{#State 51
		DEFAULT => -116
	},
	{#State 52
		DEFAULT => -115
	},
	{#State 53
		ACTIONS => {
			"identifier" => 35,
			"left brace" => 36,
			"left parenthesis" => 37
		},
		GOTOS => {
			'propertyList' => 34,
			'descriptor' => 38,
			'descriptorExpression' => 80
		}
	},
	{#State 54
		DEFAULT => -52,
		GOTOS => {
			'@26-3' => 81
		}
	},
	{#State 55
		ACTIONS => {
			"aggregate" => 27
		},
		DEFAULT => -87,
		GOTOS => {
			'fullDescriptorExpression' => 23,
			'attribute' => 26,
			'@43-0' => 25,
			'generalizedAttribute' => 82,
			'aggregate' => 29
		}
	},
	{#State 56
		DEFAULT => -9,
		GOTOS => {
			'@6-7' => 83
		}
	},
	{#State 57
		DEFAULT => -67
	},
	{#State 58
		DEFAULT => -89,
		GOTOS => {
			'@44-1' => 84
		}
	},
	{#State 59
		DEFAULT => -84
	},
	{#State 60
		DEFAULT => -82
	},
	{#State 61
		DEFAULT => -66
	},
	{#State 62
		DEFAULT => -85
	},
	{#State 63
		DEFAULT => -83
	},
	{#State 64
		DEFAULT => -86
	},
	{#State 65
		DEFAULT => -68
	},
	{#State 66
		DEFAULT => -105,
		GOTOS => {
			'@57-1' => 85
		}
	},
	{#State 67
		ACTIONS => {
			"comma" => 87
		},
		DEFAULT => -61,
		GOTOS => {
			'tableList' => 86
		}
	},
	{#State 68
		DEFAULT => -69
	},
	{#State 69
		DEFAULT => -76,
		GOTOS => {
			'@38-1' => 88
		}
	},
	{#State 70
		DEFAULT => -70,
		GOTOS => {
			'@33-1' => 89
		}
	},
	{#State 71
		DEFAULT => -99,
		GOTOS => {
			'@52-1' => 90
		}
	},
	{#State 72
		DEFAULT => -64,
		GOTOS => {
			'@32-1' => 91
		}
	},
	{#State 73
		DEFAULT => -93,
		GOTOS => {
			'@47-1' => 92
		}
	},
	{#State 74
		ACTIONS => {
			"semi-colon" => 93
		}
	},
	{#State 75
		ACTIONS => {
			"timeInterval" => 95,
			"nonnull" => 100,
			"string" => 96,
			"constant" => 101,
			"null" => 97,
			"-" => 102,
			"integer" => 103,
			"not" => 105,
			"left parenthesis" => 104
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 98,
			'predicate' => 99,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 76
		DEFAULT => -18
	},
	{#State 77
		ACTIONS => {
			"comma" => 107
		},
		DEFAULT => -22,
		GOTOS => {
			'propertyListContinues' => 106
		}
	},
	{#State 78
		DEFAULT => -24,
		GOTOS => {
			'@15-1' => 108
		}
	},
	{#State 79
		ACTIONS => {
			"*" => 50,
			"+" => 51,
			"period" => 53,
			"question mark" => 52
		},
		DEFAULT => -111,
		GOTOS => {
			'@61-3' => 109
		}
	},
	{#State 80
		ACTIONS => {
			"period" => 53,
			"question mark" => 52,
			"*" => 50,
			"+" => 51
		},
		DEFAULT => -113
	},
	{#State 81
		DEFAULT => -87,
		GOTOS => {
			'fullDescriptorExpression' => 23,
			'attribute' => 110,
			'@43-0' => 25
		}
	},
	{#State 82
		ACTIONS => {
			"comma" => 42
		},
		DEFAULT => -46,
		GOTOS => {
			'attributeList' => 111
		}
	},
	{#State 83
		ACTIONS => {
			"semi-colon" => 112
		}
	},
	{#State 84
		ACTIONS => {
			"left parenthesis" => 113
		}
	},
	{#State 85
		ACTIONS => {
			"left parenthesis" => 114
		}
	},
	{#State 86
		DEFAULT => -60
	},
	{#State 87
		DEFAULT => -62,
		GOTOS => {
			'@31-1' => 115
		}
	},
	{#State 88
		ACTIONS => {
			"left parenthesis" => 116
		}
	},
	{#State 89
		ACTIONS => {
			"left parenthesis" => 117
		}
	},
	{#State 90
		ACTIONS => {
			"left parenthesis" => 118
		}
	},
	{#State 91
		ACTIONS => {
			"identifier" => 119
		}
	},
	{#State 92
		ACTIONS => {
			"left parenthesis" => 120
		}
	},
	{#State 93
		DEFAULT => -16
	},
	{#State 94
		DEFAULT => -146
	},
	{#State 95
		DEFAULT => -139
	},
	{#State 96
		DEFAULT => -138
	},
	{#State 97
		DEFAULT => -128,
		GOTOS => {
			'@63-1' => 121
		}
	},
	{#State 98
		ACTIONS => {
			"*" => 123,
			"+" => 124,
			"comparison" => 122,
			"-" => 125,
			"/" => 126
		}
	},
	{#State 99
		ACTIONS => {
			"and" => 127,
			"or" => 128
		},
		DEFAULT => -123
	},
	{#State 100
		DEFAULT => -132,
		GOTOS => {
			'@66-1' => 129
		}
	},
	{#State 101
		DEFAULT => -140,
		GOTOS => {
			'@69-1' => 130
		}
	},
	{#State 102
		ACTIONS => {
			"timeInterval" => 95,
			"string" => 96,
			"constant" => 101,
			"-" => 102,
			"integer" => 103
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 131,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 103
		DEFAULT => -137
	},
	{#State 104
		ACTIONS => {
			"timeInterval" => 95,
			"nonnull" => 100,
			"string" => 96,
			"constant" => 101,
			"null" => 97,
			"-" => 102,
			"integer" => 103,
			"not" => 105,
			"left parenthesis" => 104
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 98,
			'predicate' => 132,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 105
		ACTIONS => {
			"timeInterval" => 95,
			"nonnull" => 100,
			"string" => 96,
			"constant" => 101,
			"null" => 97,
			"-" => 102,
			"integer" => 103,
			"not" => 105,
			"left parenthesis" => 104
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 98,
			'predicate' => 133,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 106
		DEFAULT => -20,
		GOTOS => {
			'@14-4' => 134
		}
	},
	{#State 107
		ACTIONS => {
			"identifier" => 78
		},
		GOTOS => {
			'property' => 135
		}
	},
	{#State 108
		ACTIONS => {
			"colon" => 137,
			"exclamation mark" => 139
		},
		GOTOS => {
			'optionalProperty' => 138,
			'propertyContinues' => 140,
			'requiredProperty' => 136
		}
	},
	{#State 109
		ACTIONS => {
			"right parenthesis" => 141
		}
	},
	{#State 110
		DEFAULT => -53,
		GOTOS => {
			'@27-5' => 142
		}
	},
	{#State 111
		DEFAULT => -48
	},
	{#State 112
		DEFAULT => -10,
		GOTOS => {
			'@7-9' => 143
		}
	},
	{#State 113
		DEFAULT => -90,
		GOTOS => {
			'@45-3' => 144
		}
	},
	{#State 114
		DEFAULT => -106,
		GOTOS => {
			'@58-3' => 145
		}
	},
	{#State 115
		ACTIONS => {
			"collapse" => 58,
			"property" => 70,
			"roots" => 64,
			"nodes" => 66,
			"match" => 71,
			"coalesce" => 69,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'coalescedValue' => 57,
			'slicedPath' => 59,
			'propertyValue' => 61,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'node' => 65,
			'table' => 146,
			'path' => 68,
			'valueSpecification' => 72
		}
	},
	{#State 116
		DEFAULT => -77,
		GOTOS => {
			'@39-3' => 147
		}
	},
	{#State 117
		DEFAULT => -71,
		GOTOS => {
			'@34-3' => 148
		}
	},
	{#State 118
		DEFAULT => -100,
		GOTOS => {
			'@53-3' => 149
		}
	},
	{#State 119
		DEFAULT => -65
	},
	{#State 120
		DEFAULT => -94,
		GOTOS => {
			'@48-3' => 150
		}
	},
	{#State 121
		ACTIONS => {
			"left parenthesis" => 151
		}
	},
	{#State 122
		ACTIONS => {
			"timeInterval" => 95,
			"string" => 96,
			"constant" => 101,
			"-" => 102,
			"integer" => 103
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 152,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 123
		ACTIONS => {
			"timeInterval" => 95,
			"string" => 96,
			"constant" => 101,
			"-" => 102,
			"integer" => 103
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 153,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 124
		ACTIONS => {
			"timeInterval" => 95,
			"string" => 96,
			"constant" => 101,
			"-" => 102,
			"integer" => 103
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 154,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 125
		ACTIONS => {
			"timeInterval" => 95,
			"string" => 96,
			"constant" => 101,
			"-" => 102,
			"integer" => 103
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 155,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 126
		ACTIONS => {
			"timeInterval" => 95,
			"string" => 96,
			"constant" => 101,
			"-" => 102,
			"integer" => 103
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 156,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 127
		ACTIONS => {
			"timeInterval" => 95,
			"nonnull" => 100,
			"string" => 96,
			"constant" => 101,
			"null" => 97,
			"-" => 102,
			"integer" => 103,
			"not" => 105,
			"left parenthesis" => 104
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 98,
			'predicate' => 157,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 128
		ACTIONS => {
			"timeInterval" => 95,
			"nonnull" => 100,
			"string" => 96,
			"constant" => 101,
			"null" => 97,
			"-" => 102,
			"integer" => 103,
			"not" => 105,
			"left parenthesis" => 104
		},
		DEFAULT => -87,
		GOTOS => {
			'expression' => 98,
			'predicate' => 158,
			'fullDescriptorExpression' => 94,
			'@43-0' => 25
		}
	},
	{#State 129
		ACTIONS => {
			"left parenthesis" => 159
		}
	},
	{#State 130
		ACTIONS => {
			"left parenthesis" => 160
		}
	},
	{#State 131
		DEFAULT => -151
	},
	{#State 132
		ACTIONS => {
			"and" => 127,
			"right parenthesis" => 161,
			"or" => 128
		}
	},
	{#State 133
		DEFAULT => -124
	},
	{#State 134
		ACTIONS => {
			"right brace" => 162
		}
	},
	{#State 135
		ACTIONS => {
			"comma" => 107
		},
		DEFAULT => -22,
		GOTOS => {
			'propertyListContinues' => 163
		}
	},
	{#State 136
		DEFAULT => -26
	},
	{#State 137
		DEFAULT => -30,
		GOTOS => {
			'@17-1' => 164
		}
	},
	{#State 138
		DEFAULT => -27
	},
	{#State 139
		DEFAULT => -28,
		GOTOS => {
			'@16-1' => 165
		}
	},
	{#State 140
		DEFAULT => -25
	},
	{#State 141
		DEFAULT => -112
	},
	{#State 142
		ACTIONS => {
			"right parenthesis" => 166
		}
	},
	{#State 143
		ACTIONS => {
			"set" => 5
		},
		DEFAULT => -5,
		GOTOS => {
			'defaults' => 167
		}
	},
	{#State 144
		ACTIONS => {
			"collapse" => 58,
			"roots" => 64,
			"match" => 71,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'slicedPath' => 59,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'path' => 168
		}
	},
	{#State 145
		ACTIONS => {
			"collapse" => 58,
			"roots" => 64,
			"match" => 71,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'slicedPath' => 59,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'path' => 169
		}
	},
	{#State 146
		ACTIONS => {
			"comma" => 87
		},
		DEFAULT => -61,
		GOTOS => {
			'tableList' => 170
		}
	},
	{#State 147
		ACTIONS => {
			"identifier" => 171
		}
	},
	{#State 148
		ACTIONS => {
			"identifier" => 172
		}
	},
	{#State 149
		ACTIONS => {
			"collapse" => 58,
			"roots" => 64,
			"match" => 71,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'slicedPath' => 59,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'path' => 173
		}
	},
	{#State 150
		ACTIONS => {
			"identifier" => 176,
			"left brace" => 36
		},
		GOTOS => {
			'propertyList' => 174,
			'regionSpecification' => 175
		}
	},
	{#State 151
		DEFAULT => -129,
		GOTOS => {
			'@64-3' => 177
		}
	},
	{#State 152
		ACTIONS => {
			"*" => 123,
			"+" => 124,
			"-" => 125,
			"/" => 126
		},
		DEFAULT => -136
	},
	{#State 153
		DEFAULT => -149
	},
	{#State 154
		ACTIONS => {
			"*" => 123,
			"/" => 126
		},
		DEFAULT => -147
	},
	{#State 155
		ACTIONS => {
			"*" => 123,
			"/" => 126
		},
		DEFAULT => -148
	},
	{#State 156
		DEFAULT => -150
	},
	{#State 157
		DEFAULT => -126
	},
	{#State 158
		ACTIONS => {
			"and" => 127
		},
		DEFAULT => -127
	},
	{#State 159
		DEFAULT => -133,
		GOTOS => {
			'@67-3' => 178
		}
	},
	{#State 160
		DEFAULT => -141,
		GOTOS => {
			'@70-3' => 179
		}
	},
	{#State 161
		DEFAULT => -125
	},
	{#State 162
		DEFAULT => -21
	},
	{#State 163
		DEFAULT => -23
	},
	{#State 164
		ACTIONS => {
			"constant" => 184,
			"identifier" => 183,
			"timeInterval" => 180,
			"integer" => 185,
			"string" => 181
		},
		GOTOS => {
			'literal' => 182
		}
	},
	{#State 165
		ACTIONS => {
			"constant" => 184,
			"identifier" => 183,
			"timeInterval" => 180,
			"integer" => 185,
			"string" => 181
		},
		GOTOS => {
			'literal' => 186
		}
	},
	{#State 166
		DEFAULT => -54
	},
	{#State 167
		DEFAULT => -11
	},
	{#State 168
		DEFAULT => -91,
		GOTOS => {
			'@46-5' => 187
		}
	},
	{#State 169
		DEFAULT => -107,
		GOTOS => {
			'@59-5' => 188
		}
	},
	{#State 170
		DEFAULT => -63
	},
	{#State 171
		DEFAULT => -78,
		GOTOS => {
			'@40-5' => 189
		}
	},
	{#State 172
		DEFAULT => -72,
		GOTOS => {
			'@35-5' => 190
		}
	},
	{#State 173
		DEFAULT => -101,
		GOTOS => {
			'@54-5' => 191
		}
	},
	{#State 174
		DEFAULT => -118
	},
	{#State 175
		DEFAULT => -95,
		GOTOS => {
			'@49-5' => 192
		}
	},
	{#State 176
		DEFAULT => -117
	},
	{#State 177
		ACTIONS => {
			"collapse" => 58,
			"roots" => 64,
			"match" => 71,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'slicedPath' => 59,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'path' => 193
		}
	},
	{#State 178
		ACTIONS => {
			"collapse" => 58,
			"roots" => 64,
			"match" => 71,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'slicedPath' => 59,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'path' => 194
		}
	},
	{#State 179
		ACTIONS => {
			"identifier" => 195
		}
	},
	{#State 180
		DEFAULT => -33
	},
	{#State 181
		DEFAULT => -35
	},
	{#State 182
		DEFAULT => -31
	},
	{#State 183
		DEFAULT => -32
	},
	{#State 184
		DEFAULT => -36,
		GOTOS => {
			'@18-1' => 196
		}
	},
	{#State 185
		DEFAULT => -34
	},
	{#State 186
		DEFAULT => -29
	},
	{#State 187
		ACTIONS => {
			"right parenthesis" => 197
		}
	},
	{#State 188
		ACTIONS => {
			"right parenthesis" => 198
		}
	},
	{#State 189
		ACTIONS => {
			"comma" => 199
		}
	},
	{#State 190
		ACTIONS => {
			"comma" => 200
		}
	},
	{#State 191
		ACTIONS => {
			"comma" => 201
		}
	},
	{#State 192
		ACTIONS => {
			"comma" => 202
		}
	},
	{#State 193
		DEFAULT => -130,
		GOTOS => {
			'@65-5' => 203
		}
	},
	{#State 194
		DEFAULT => -134,
		GOTOS => {
			'@68-5' => 204
		}
	},
	{#State 195
		DEFAULT => -142,
		GOTOS => {
			'@71-5' => 205
		}
	},
	{#State 196
		ACTIONS => {
			"left parenthesis" => 206
		}
	},
	{#State 197
		DEFAULT => -92
	},
	{#State 198
		DEFAULT => -108
	},
	{#State 199
		DEFAULT => -79,
		GOTOS => {
			'@41-7' => 207
		}
	},
	{#State 200
		DEFAULT => -73,
		GOTOS => {
			'@36-7' => 208
		}
	},
	{#State 201
		DEFAULT => -102,
		GOTOS => {
			'@55-7' => 209
		}
	},
	{#State 202
		DEFAULT => -96,
		GOTOS => {
			'@50-7' => 210
		}
	},
	{#State 203
		ACTIONS => {
			"right parenthesis" => 211
		}
	},
	{#State 204
		ACTIONS => {
			"right parenthesis" => 212
		}
	},
	{#State 205
		ACTIONS => {
			"comma" => 213
		}
	},
	{#State 206
		DEFAULT => -37,
		GOTOS => {
			'@19-3' => 214
		}
	},
	{#State 207
		ACTIONS => {
			"collapse" => 58,
			"roots" => 64,
			"match" => 71,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'slicedPath' => 59,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'path' => 215
		}
	},
	{#State 208
		ACTIONS => {
			"collapse" => 58,
			"roots" => 64,
			"match" => 71,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'slicedPath' => 59,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'path' => 216
		}
	},
	{#State 209
		ACTIONS => {
			"identifier" => 35,
			"left brace" => 36,
			"left parenthesis" => 37
		},
		GOTOS => {
			'propertyList' => 34,
			'descriptor' => 38,
			'descriptorExpression' => 217
		}
	},
	{#State 210
		ACTIONS => {
			"collapse" => 58,
			"roots" => 64,
			"match" => 71,
			"slice" => 73
		},
		DEFAULT => -87,
		GOTOS => {
			'slicedPath' => 59,
			'fullDescriptorExpression' => 60,
			'matchedPath' => 62,
			'collapsedPath' => 63,
			'@43-0' => 25,
			'path' => 218
		}
	},
	{#State 211
		DEFAULT => -131
	},
	{#State 212
		DEFAULT => -135
	},
	{#State 213
		DEFAULT => -143,
		GOTOS => {
			'@72-7' => 219
		}
	},
	{#State 214
		ACTIONS => {
			"identifier" => 220
		}
	},
	{#State 215
		DEFAULT => -80,
		GOTOS => {
			'@42-9' => 221
		}
	},
	{#State 216
		DEFAULT => -74,
		GOTOS => {
			'@37-9' => 222
		}
	},
	{#State 217
		ACTIONS => {
			"*" => 50,
			"+" => 51,
			"period" => 53,
			"question mark" => 52
		},
		DEFAULT => -103,
		GOTOS => {
			'@56-9' => 223
		}
	},
	{#State 218
		DEFAULT => -97,
		GOTOS => {
			'@51-9' => 224
		}
	},
	{#State 219
		ACTIONS => {
			"constant" => 184,
			"identifier" => 183,
			"timeInterval" => 180,
			"integer" => 185,
			"string" => 181
		},
		GOTOS => {
			'literal' => 225
		}
	},
	{#State 220
		DEFAULT => -38,
		GOTOS => {
			'@20-5' => 226
		}
	},
	{#State 221
		ACTIONS => {
			"right parenthesis" => 227
		}
	},
	{#State 222
		ACTIONS => {
			"right parenthesis" => 228
		}
	},
	{#State 223
		ACTIONS => {
			"right parenthesis" => 229
		}
	},
	{#State 224
		ACTIONS => {
			"right parenthesis" => 230
		}
	},
	{#State 225
		DEFAULT => -144,
		GOTOS => {
			'@73-9' => 231
		}
	},
	{#State 226
		ACTIONS => {
			"comma" => 232
		}
	},
	{#State 227
		DEFAULT => -81
	},
	{#State 228
		DEFAULT => -75
	},
	{#State 229
		DEFAULT => -104
	},
	{#State 230
		DEFAULT => -98
	},
	{#State 231
		ACTIONS => {
			"right parenthesis" => 233
		}
	},
	{#State 232
		DEFAULT => -39,
		GOTOS => {
			'@21-7' => 234
		}
	},
	{#State 233
		DEFAULT => -145
	},
	{#State 234
		ACTIONS => {
			"constant" => 184,
			"identifier" => 183,
			"timeInterval" => 180,
			"integer" => 185,
			"string" => 181
		},
		GOTOS => {
			'literal' => 235
		}
	},
	{#State 235
		DEFAULT => -40,
		GOTOS => {
			'@22-9' => 236
		}
	},
	{#State 236
		ACTIONS => {
			"right parenthesis" => 237
		}
	},
	{#State 237
		DEFAULT => -41
	}
],
                                  yyrules  =>
[
	[#Rule 0
		 '$start', 2, undef
	],
	[#Rule 1
		 'start', 1,
sub {
 return $_[1]; 
}
	],
	[#Rule 2
		 '@1-0', 0,
sub {
 $PassOne = 1; 
      $PassTwo = 0;
      
}
	],
	[#Rule 3
		 '@2-4', 0,
sub {
 $PassOne = 0; 
      $PassTwo = 1;
      @From = ();
      ErrorHandler->clearText();
      
}
	],
	[#Rule 4
		 'passes', 7,
sub {
 return $_[7]; 
}
	],
	[#Rule 5
		 'defaults', 0, undef
	],
	[#Rule 6
		 '@3-1', 0,
sub {
 &_expecting("keyword default") if $PassOne; 
}
	],
	[#Rule 7
		 '@4-3', 0,
sub {
 &_expecting("keyword property") if $PassOne; 
}
	],
	[#Rule 8
		 '@5-5', 0,
sub {
 &_expecting("property list") if $PassOne; 
}
	],
	[#Rule 9
		 '@6-7', 0,
sub {
 &_expecting("semi-colon") if $PassOne; 
      &_establishDefaults($_[7]) if $PassTwo; 
}
	],
	[#Rule 10
		 '@7-9', 0,
sub {
 &_expecting("set or select") if $PassOne; 
}
	],
	[#Rule 11
		 'defaults', 11, undef
	],
	[#Rule 12
		 '@8-0', 0,
sub {
 &_expecting("SELECT clause") if $PassOne; 
}
	],
	[#Rule 13
		 '@9-2', 0,
sub {
 &_expecting("FROM clause") if $PassOne; 
      if ($PassTwo) { 
        while (@From) {push @SelectPairs, pop @From;}
        }
      
}
	],
	[#Rule 14
		 '@10-4', 0,
sub {
 &_expecting("WHERE clause") if $PassOne; 
      if ($PassTwo) { 
        while (@SelectPairs) {push @From, pop @SelectPairs;}
        }
      
}
	],
	[#Rule 15
		 '@11-6', 0,
sub {
 &_expecting("semi-colon") if $PassOne; 
}
	],
	[#Rule 16
		 'select', 8,
sub {
 return {'Select' => $_[2],
              'From' => \@From,
              'Where' => $_[6]
             } if $PassTwo;
    
}
	],
	[#Rule 17
		 '@12-1', 0,
sub {
 &_expecting("right parenthesis that terminates a property") if $PassOne; 
}
	],
	[#Rule 18
		 'propertyList', 3,
sub {
 if ($PassTwo) {my %h = (); return \%h;}
}
	],
	[#Rule 19
		 '@13-1', 0,
sub {
 &_expecting("a property") if $PassOne; 
}
	],
	[#Rule 20
		 '@14-4', 0,
sub {
 &_expecting("right parenthesis that terminates a property") if $PassOne; 
}
	],
	[#Rule 21
		 'propertyList', 6,
sub {
 if ($PassTwo) {
        my $h = $_[4]; 
        my ($d, $pair) = @{$_[3]}; 
        my ($v, $w) = @$pair;
        my ($value) = Dimensions::convert($d,$v); 
        $value->required() if $w eq 'required';
        $$h{$d} = $value;
        return $h; 
        }
      
}
	],
	[#Rule 22
		 'propertyListContinues', 0,
sub {
 if ($PassTwo) {my %h = (); return \%h;} 
}
	],
	[#Rule 23
		 'propertyListContinues', 3,
sub {
 if ($PassTwo) {
        my $h = $_[3]; 
        my ($d, $pair) = @{$_[2]}; 
        my ($v, $w) = @$pair;
        my ($value) = Dimensions::convert($d,$v); 
        $value->required() if $w eq 'required';
        $$h{$d} = $value;
        return $h; 
        }
      
}
	],
	[#Rule 24
		 '@15-1', 0,
sub {
 &_expecting("a property name") if $PassOne; 
}
	],
	[#Rule 25
		 'property', 3,
sub {
 return [lc $_[1], $_[3]] if $PassTwo; 
}
	],
	[#Rule 26
		 'propertyContinues', 1,
sub {
 return $_[1] if $PassTwo; 
}
	],
	[#Rule 27
		 'propertyContinues', 1,
sub {
 return $_[1] if $PassTwo; 
}
	],
	[#Rule 28
		 '@16-1', 0,
sub {
 &_expecting("an exclamation mark") if $PassOne; 
}
	],
	[#Rule 29
		 'requiredProperty', 3,
sub {
 &_expecting("a property value") if $PassOne; 
      return [$_[3], 'required'] if $PassTwo; 
}
	],
	[#Rule 30
		 '@17-1', 0,
sub {
 &_expecting("a colon") if $PassOne; 
}
	],
	[#Rule 31
		 'optionalProperty', 3,
sub {
 &_expecting("a property value") if $PassOne; 
      return [$_[3], 'optional'] if $PassTwo; 
}
	],
	[#Rule 32
		 'literal', 1,
sub {
 return $_[1] if $PassTwo; 
}
	],
	[#Rule 33
		 'literal', 1,
sub {
 return $_[1] if $PassTwo; 
}
	],
	[#Rule 34
		 'literal', 1,
sub {
 return $_[1] if $PassTwo; 
}
	],
	[#Rule 35
		 'literal', 1,
sub {
 return $_[1] if $PassTwo; 
}
	],
	[#Rule 36
		 '@18-1', 0,
sub {
 &_expecting("a left parenthesis for constant operation") if $PassOne; 
}
	],
	[#Rule 37
		 '@19-3', 0,
sub {
 &_expecting("a property name in constant operation") if $PassOne; 
}
	],
	[#Rule 38
		 '@20-5', 0,
sub {
 &_expecting("a comma in constant operation") if $PassOne; 
}
	],
	[#Rule 39
		 '@21-7', 0,
sub {
 &_expecting("a constant in constant operation") if $PassOne; 
}
	],
	[#Rule 40
		 '@22-9', 0,
sub {
 &_expecting("a right parenthesis for constant operation") if $PassOne; 
}
	],
	[#Rule 41
		 'literal', 11,
sub {
 return lc $_[5] if $PassOne; 
      return Dimensions::convert($_[5],$_[9]); 
      
}
	],
	[#Rule 42
		 '@23-1', 0,
sub {
 &_expecting("generalized attribute list in SELECT clause") if $PassOne; 
      &_captureTokenText() if $PassTwo; 
}
	],
	[#Rule 43
		 'selectClause', 3,
sub {
 return $_[3] if $PassTwo; 
}
	],
	[#Rule 44
		 'generalizedAttributeList', 1,
sub {
 if ($PassTwo) { 
        my @temp = ();
        foreach my $key (@OrderVars) {
          my ($var) = new ExpressionTree::Variable($key, $TypeOfVariable{$key});
          push @temp, [$key, $var];
          }
        # Turn off token capturing
        &_fetchTokenText();
        return \@temp;
        }
      
}
	],
	[#Rule 45
		 'generalizedAttributeList', 2,
sub {
 if ($PassTwo) {my $a = $_[2]; unshift @$a, $_[1]; return $a;} 
}
	],
	[#Rule 46
		 'attributeList', 0,
sub {
 return [] if $PassTwo; 
}
	],
	[#Rule 47
		 '@24-1', 0,
sub {
 &_expecting("generalized attribute after the comma") if $PassOne; 
      &_captureTokenText() if $PassTwo; 
}
	],
	[#Rule 48
		 'attributeList', 4,
sub {
 if ($PassTwo) {my $a = $_[4]; unshift @$a, $_[3]; return $a;} 
}
	],
	[#Rule 49
		 'generalizedAttribute', 1,
sub {
 return $_[1] if $PassTwo; 
}
	],
	[#Rule 50
		 'generalizedAttribute', 1,
sub {
 return $_[1] if $PassTwo; 
}
	],
	[#Rule 51
		 '@25-1', 0,
sub {
 &_expecting("left parenthesis in aggregate operation") if $PassOne; 
}
	],
	[#Rule 52
		 '@26-3', 0,
sub {
 &_expecting("attribute in aggregate operation") if $PassOne; 
}
	],
	[#Rule 53
		 '@27-5', 0,
sub {
 &_expecting("right parenthesis in aggregate operation") if $PassOne; 
}
	],
	[#Rule 54
		 'aggregate', 7,
sub {
 if ($PassTwo) {
        my ($attributeName) = @$_[5];
        my ($anyError) = 
          ExpressionTree::Operations::Aggregate->validate($_[1],$attributeName);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return [$_[1] . "(" . $attributeName . ")",
                new ExpressionTree::Operations::Aggregate($_[1], $attributeName)
               ];
        }
      
}
	],
	[#Rule 55
		 'attribute', 1,
sub {
 return [&_fetchTokenText, $_[1]] if $PassTwo; 
}
	],
	[#Rule 56
		 '@28-0', 0,
sub {
 %TypeOfVariable = () if $PassOne; 
      %Variables = () if $PassTwo; 
      
}
	],
	[#Rule 57
		 'fromClause', 1,
sub {
 return [] if $PassTwo; 
}
	],
	[#Rule 58
		 '@29-0', 0,
sub {
 %TypeOfVariable = () if $PassOne; 
      %Variables = () if $PassTwo; 
      
}
	],
	[#Rule 59
		 '@30-2', 0,
sub {
 &_expecting("a `table' or list of `tables'") if $PassOne; 
}
	],
	[#Rule 60
		 'fromClause', 5, undef
	],
	[#Rule 61
		 'tableList', 0,
sub {
 return [] if $PassTwo; 
}
	],
	[#Rule 62
		 '@31-1', 0,
sub {
 &_expecting("a `table' or list of `tables'") if $PassOne; 
}
	],
	[#Rule 63
		 'tableList', 4, undef
	],
	[#Rule 64
		 '@32-1', 0,
sub {
 &_expecting("a variable (which is an identifier)") if $PassOne; 
}
	],
	[#Rule 65
		 'table', 3,
sub {
 if ($PassOne) {
        # Check to see if variable has been used before
        $_[0]->_semanticError("Variable $_[3] redefined.") 
          if defined $Variables{$_[3]};
        $TypeOfVariable{$_[3]} = $_[1];
        push @OrderVars, $_[3];
        }
      if ($PassTwo) {
        $Variables{$_[3]} = 1;

        # Optimize away variable assignment
        if (ref($_[1]) eq 'ExpressionTree::Variable') {
          foreach my $pair (@From) {
            my ($var, $exp) = @$pair;
            if ($var eq $_[1]->variableName) { 
              @$pair[0] = $_[3]; 
              }
            } 
          }
        else {
          push @From, [$_[3], $_[1]];
          }
        }
      
}
	],
	[#Rule 66
		 'valueSpecification', 1, undef
	],
	[#Rule 67
		 'valueSpecification', 1, undef
	],
	[#Rule 68
		 'valueSpecification', 1, undef
	],
	[#Rule 69
		 'valueSpecification', 1, undef
	],
	[#Rule 70
		 '@33-1', 0,
sub {
 &_expecting("left parenthesis for PROPERTY EXTRACTION operation") 
        if $PassOne; 
}
	],
	[#Rule 71
		 '@34-3', 0,
sub {
 &_expecting("property name for PROPERTY EXTRACTION operation") 
        if $PassOne; 
}
	],
	[#Rule 72
		 '@35-5', 0,
sub {
 &_expecting("comma for PROPERTY EXTRACTION operation") if $PassOne; 
}
	],
	[#Rule 73
		 '@36-7', 0,
sub {
 &_expecting("path expression in PROPERTY EXTRACTION operation") 
        if $PassOne; 
}
	],
	[#Rule 74
		 '@37-9', 0,
sub {
 &_expecting("right parenthesis for PROPERTY EXTRACTION operation") 
        if $PassOne; 
}
	],
	[#Rule 75
		 'propertyValue', 11,
sub {
 return lc $_[5] if $PassOne; 
      # It must be a variable!
      my ($var) = $_[9]; 
      if (ref($var) ne 'ExpressionTree::Variable') {
        # Create a new variable
        my ($newVar) = &_newVariable();
        my ($anyError) =
        my ($assign) = [$newVar, $var];
        push @From, $assign;
        $TypeOfVariable{$newVar} = lc $_[5];
        $var = new ExpressionTree::Variable($newVar, $TypeOfVariable{$newVar});
        }
      my ($anyError) = 
        ExpressionTree::Operations::Dimension->validate(lc $_[5], $var);
      $_[0]->_semanticError($anyError) if defined $anyError;
      return new ExpressionTree::Operations::Dimension(lc $_[5], $var);
      
}
	],
	[#Rule 76
		 '@38-1', 0,
sub {
 &_expecting("left parenthesis in COALESCE operation") if $PassOne; 
}
	],
	[#Rule 77
		 '@39-3', 0,
sub {
 &_expecting("property in COALESCE operation") if $PassOne; 
}
	],
	[#Rule 78
		 '@40-5', 0,
sub {
 &_expecting("comma in COALESCE operation") if $PassOne; 
}
	],
	[#Rule 79
		 '@41-7', 0,
sub {
 &_expecting("path expression in COALESCE operation") if $PassOne; 
}
	],
	[#Rule 80
		 '@42-9', 0,
sub {
 &_expecting("right parenthesis in COALESCE operation") if $PassOne; 
}
	],
	[#Rule 81
		 'coalescedValue', 11,
sub {
 return lc $_[5] if $PassOne; 
      # It must be a variable!
      my ($var) = $_[9];
      if (ref($var) ne 'ExpressionTree::Variable') {
        # Create a new variable
        my ($newVar) = &_newVariable();
        my ($anyError) =
        my ($assign) = [$newVar, $var];
        push @From, $assign;
        $TypeOfVariable{$newVar} = lc $_[5];
        $var = new ExpressionTree::Variable($newVar, $TypeOfVariable{$newVar});
        }
      my ($anyError) = 
        ExpressionTree::Operations::Coalesce->validate(lc $_[5], $var);
      $_[0]->_semanticError($anyError) if defined $anyError;
      return new ExpressionTree::Operations::Coalesce(lc $_[5], $var);
      
}
	],
	[#Rule 82
		 'path', 1,
sub {
 return 'Path' if $PassOne; 
      return $_[1] if $PassTwo; 
      
}
	],
	[#Rule 83
		 'path', 1, undef
	],
	[#Rule 84
		 'path', 1, undef
	],
	[#Rule 85
		 'path', 1, undef
	],
	[#Rule 86
		 'path', 1,
sub {
 return 'Path' if $PassOne; 
      return new ExpressionTree::Variable('&root', undef) if $PassTwo; 
      
}
	],
	[#Rule 87
		 '@43-0', 0,
sub {
 $FullDescriptor = 1 if $PassTwo; 
}
	],
	[#Rule 88
		 'fullDescriptorExpression', 2,
sub {
 return 'Path' if $PassOne; 
      if ($PassTwo) {
        my ($exp) = $_[2];
        # Is it a variable?
        return $exp if ref($exp) eq 'ExpressionTree::Variable';

        # Is it a composition?
        if (ref($exp) eq 'ExpressionTree::RegExp::Composition') {
          # grab the suffix and prefix
          my ($prefix) = $exp->{'prefix'};
          my ($suffix) = $exp->{'suffix'};
  
          # Turn it into a match operation if the first guy is a variable
          if (ref($prefix) eq 'ExpressionTree::Variable') {
            my ($var) = &_newVariable();
            my ($anyError) = 
              ExpressionTree::Operations::Match->validate($prefix, $suffix);
            $_[0]->_semanticError($anyError) if defined $anyError;
            my ($assign) = [$var, 
                    new ExpressionTree::Operations::Match($prefix, $suffix)];
            push @From, $assign;
            $TypeOfVariable{$var} = undef;
            return new ExpressionTree::Variable($var, undef);
            }
          }
           
        # Match from the root
        my ($root) = new ExpressionTree::Variable('&root', undef);
        my ($var) = &_newVariable();
        $TypeOfVariable{$var} = undef;
        my ($anyError) = 
              ExpressionTree::Operations::Match->validate($root, $exp);
        $_[0]->_semanticError($anyError) if defined $anyError;
        my ($assign) = 
                [$var, new ExpressionTree::Operations::Match($root, $exp)];
        push @From, $assign;
        return new ExpressionTree::Variable($var, undef);
        } 
      
}
	],
	[#Rule 89
		 '@44-1', 0,
sub {
 &_expecting("left parenthesis in COLLAPSE operation") if $PassOne; 
}
	],
	[#Rule 90
		 '@45-3', 0,
sub {
 &_expecting("path expression in COLLAPSE operation") if $PassOne; 
}
	],
	[#Rule 91
		 '@46-5', 0,
sub {
 &_expecting("right parenthesis in COLLAPSE operation") if $PassOne; 
}
	],
	[#Rule 92
		 'collapsedPath', 7,
sub {
 return 'Path' if $PassOne; 
      if ($PassTwo) { 
        my ($anyError) = 
            ExpressionTree::Operations::Collapse->validate($_[5]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Collapse($_[5]);
        }
      
}
	],
	[#Rule 93
		 '@47-1', 0,
sub {
 &_expecting("left parenthesis in SLICE operation") if $PassOne; 
}
	],
	[#Rule 94
		 '@48-3', 0,
sub {
 &_expecting("descriptor in SLICE operation") if $PassOne; 
}
	],
	[#Rule 95
		 '@49-5', 0,
sub {
 &_expecting("comma in SLICE operation") if $PassOne; 
}
	],
	[#Rule 96
		 '@50-7', 0,
sub {
 &_expecting("path expression in SLICE operation") if $PassOne; 
}
	],
	[#Rule 97
		 '@51-9', 0,
sub {
 &_expecting("right parenthesis in SLICE operation") if $PassOne; 
}
	],
	[#Rule 98
		 'slicedPath', 11,
sub {
 return 'Path' if $PassOne; 
      if ($PassTwo) { 
        my ($anyError) = 
            ExpressionTree::Operations::Slice->validate($_[5], $_[9]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Slice($_[5], $_[9]);
        }
      
}
	],
	[#Rule 99
		 '@52-1', 0,
sub {
 &_expecting("left parenthesis for MATCH operation") if $PassOne; 
}
	],
	[#Rule 100
		 '@53-3', 0,
sub {
 &_expecting("path expression in MATCH operation") if $PassOne; 
}
	],
	[#Rule 101
		 '@54-5', 0,
sub {
 &_expecting("comma in MATCH operation") if $PassOne; 
}
	],
	[#Rule 102
		 '@55-7', 0,
sub {
 &_expecting("regular expression in MATCH operation") if $PassOne; 
}
	],
	[#Rule 103
		 '@56-9', 0,
sub {
 &_expecting("right parenthesis in MATCH operation") if $PassOne; 
}
	],
	[#Rule 104
		 'matchedPath', 11,
sub {
 return 'Path' if $PassOne; 
      if ($PassTwo) { 
        my ($anyError) = 
            ExpressionTree::Operations::Match->validate($_[5], $_[9]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Match($_[5], $_[9]);
        }
      
}
	],
	[#Rule 105
		 '@57-1', 0,
sub {
 &_expecting("left parenthesis for NODES operation") if $PassOne; 
}
	],
	[#Rule 106
		 '@58-3', 0,
sub {
 &_expecting("path expression in NODES operation") if $PassOne; 
}
	],
	[#Rule 107
		 '@59-5', 0,
sub {
 &_expecting("right parenthesis for NODES operation") if $PassOne; 
}
	],
	[#Rule 108
		 'node', 7,
sub {
 return 'Node' if $PassOne; 
      if ($PassTwo) { 
        my ($anyError) = 
            ExpressionTree::Operations::Nodes->validate($_[5]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Nodes($_[5]);
        }
      
}
	],
	[#Rule 109
		 'descriptorExpression', 1,
sub {
 return $_[1] if $PassTwo; 
}
	],
	[#Rule 110
		 '@60-1', 0,
sub {
 &_expecting("descriptor expression") if $PassOne; 
}
	],
	[#Rule 111
		 '@61-3', 0,
sub {
 &_expecting("right parenthesis for descriptor regular expression") 
        if $PassOne; 
      
}
	],
	[#Rule 112
		 'descriptorExpression', 5,
sub {
 return $_[3] if $PassTwo; 
}
	],
	[#Rule 113
		 'descriptorExpression', 3,
sub {
 return new ExpressionTree::RegExp::Composition($_[1], $_[3]) 
        if $PassTwo; 
}
	],
	[#Rule 114
		 'descriptorExpression', 2,
sub {
 return new ExpressionTree::RegExp::KleeneClosure($_[1]) if $PassTwo; 
}
	],
	[#Rule 115
		 'descriptorExpression', 2,
sub {
 return new ExpressionTree::RegExp::OptionalToken($_[1]) if $PassTwo; 
}
	],
	[#Rule 116
		 'descriptorExpression', 2,
sub {
 return new ExpressionTree::RegExp::Composition(
               $_[1],
               new ExpressionTree::RegExp::KleeneClosure($_[1])
               ) if $PassTwo; 
    
}
	],
	[#Rule 117
		 'regionSpecification', 1,
sub {
 # assume it is a REQUIRED name property value!
      if ($PassTwo) {
        my %h = ();
        my ($value) = Dimensions::convert('name',$_[1]); 
        $value->required();
        $h{'name'} = $value;
        return new Region(&_addDefaults(\%h)); 
        }
      
}
	],
	[#Rule 118
		 'regionSpecification', 1,
sub {
 return new Region(&_addDefaults($_[1])) if $PassTwo; 
}
	],
	[#Rule 119
		 'descriptor', 1,
sub {
 return $_[1] if $PassOne;
      # check to see if it is a variable that starts a full descriptor!
      if ($PassTwo) {
        if ($FullDescriptor) {
          if (defined $TypeOfVariable{$_[1]}) {
            return new ExpressionTree::Variable($_[1], $TypeOfVariable{$_[1]});
            }
          }

        # assume it is a REQUIRED name property value!
        my %h = ();
        my ($value) = Dimensions::convert('name',$_[1]); 
        $value->required();
        $h{'name'} = $value;
        return new ExpressionTree::RegExp::Token(new Region(&_addDefaults(\%h))); 
        }
      
}
	],
	[#Rule 120
		 'descriptor', 1,
sub {
 return new ExpressionTree::RegExp::Token(
               new Region(&_addDefaults($_[1]))) if $PassTwo; 
}
	],
	[#Rule 121
		 'whereClause', 0, undef
	],
	[#Rule 122
		 '@62-1', 0,
sub {
 &_expecting("WHERE clause predicate") if $PassOne; 
}
	],
	[#Rule 123
		 'whereClause', 3,
sub {
 if ($PassOne) {
        $_[0]->_typeCheck($_[3], 'Boolean');
        }
      return $_[3] if $PassTwo; 
      
}
	],
	[#Rule 124
		 'predicate', 2,
sub {
 if ($PassOne) {
        return $_[2];
        }
      if ($PassTwo) {
        my ($anyError) = 
            ExpressionTree::Operations::Logical->validate('not', $_[2]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Logical('not', $_[2]);
        }
      
}
	],
	[#Rule 125
		 'predicate', 3,
sub {
 if ($PassOne) {
        return $_[2];
        }
      return $_[2] if $PassTwo; 
      
}
	],
	[#Rule 126
		 'predicate', 3,
sub {
 if ($PassOne) {
        $_[0]->_typeCheck($_[1], $_[3]);
        return 'Boolean';
        }
      if ($PassTwo) {
        my ($anyError) = 
            ExpressionTree::Operations::Logical->validate('and', $_[1], $_[3]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Logical('and', $_[1], $_[3]);
        }
      
}
	],
	[#Rule 127
		 'predicate', 3,
sub {
 if ($PassOne) {
        $_[0]->_typeCheck($_[1], $_[3]);
        return 'Boolean';
        }
      if ($PassTwo) {
        my ($anyError) = 
            ExpressionTree::Operations::Logical->validate('or', $_[1], $_[3]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Logical('or', $_[1], $_[3]);
        }
      
}
	],
	[#Rule 128
		 '@63-1', 0,
sub {
 &_expecting("left parenthesis in NULL operation") if $PassOne; 
}
	],
	[#Rule 129
		 '@64-3', 0,
sub {
 &_expecting("variable in NULL operation") if $PassOne; 
}
	],
	[#Rule 130
		 '@65-5', 0,
sub {
 &_expecting("right parenthesis in NULL operation") if $PassOne; 
}
	],
	[#Rule 131
		 'predicate', 7,
sub {
 if ($PassOne) { return $_[5]; }
      if ($PassTwo) { 
        return new ExpressionTree::Operations::Null(
                 new ExpressionTree::Value($_[5], $_[5]->resultType())); 
        }
      
}
	],
	[#Rule 132
		 '@66-1', 0,
sub {
 &_expecting("left parenthesis in NONNULL operation") if $PassOne; 
}
	],
	[#Rule 133
		 '@67-3', 0,
sub {
 &_expecting("variable in NONNULL operation") if $PassOne; 
}
	],
	[#Rule 134
		 '@68-5', 0,
sub {
 &_expecting("right parenthesis in NONNULL operation") if $PassOne; 
}
	],
	[#Rule 135
		 'predicate', 7,
sub {
 if ($PassOne) { return $_[5]; }
      if ($PassTwo) { 
        return new ExpressionTree::Operations::NonNull(
                 new ExpressionTree::Value($_[5], $_[5]->resultType())); 
        }
      
}
	],
	[#Rule 136
		 'predicate', 3,
sub {
 if ($PassOne) {
        $_[0]->_typeCheck($_[1], $_[3]);
        return 'Boolean';
        }
      if ($PassTwo) {
        my $left = $_[1];
        my $right = $_[3];
        #my ($left, $right) = &_binaryTypeCoerce($_[2], $_[1], $_[3]);
        #$_[0]->_emanticError("unable to coerce left operand type in operation") 
        #  unless defined $left;
        #&semanticError("unable to coerce right operand type in operation") 
        #  unless defined $right;
        return new ExpressionTree::Operations::Comparison($_[2], $left, $right);
        }
      
}
	],
	[#Rule 137
		 'expression', 1,
sub {
 return 'Integer' if $PassOne;
      return new ExpressionTree::Operand(new Integer($_[1])) if $PassTwo;  
      
}
	],
	[#Rule 138
		 'expression', 1,
sub {
 return 'String' if $PassOne;
      return new ExpressionTree::Operand(new String($_[1])) if $PassTwo; 
      
}
	],
	[#Rule 139
		 'expression', 1,
sub {
 return 'TimeInterval' if $PassOne;
      return new ExpressionTree::Operand(new TimeInterval($_[1])) if $PassTwo; 
      
}
	],
	[#Rule 140
		 '@69-1', 0,
sub {
 &_expecting("a left parenthesis for constant operation") if $PassOne; 
}
	],
	[#Rule 141
		 '@70-3', 0,
sub {
 &_expecting("a property name in constant operation") if $PassOne; 
}
	],
	[#Rule 142
		 '@71-5', 0,
sub {
 &_expecting("a comma in constant operation") if $PassOne; 
}
	],
	[#Rule 143
		 '@72-7', 0,
sub {
 &_expecting("a constant in constant operation") if $PassOne; 
}
	],
	[#Rule 144
		 '@73-9', 0,
sub {
 &_expecting("a right parenthesis for constant operation") if $PassOne; 
}
	],
	[#Rule 145
		 'expression', 11,
sub {
 return lc $_[5] if $PassOne; 
      return Dimensions::convert(lc $_[5],$_[9]) if $PassTwo; 
      
}
	],
	[#Rule 146
		 'expression', 1,
sub {
 return $_[1] if $PassOne;
      return new ExpressionTree::Value($_[1], $_[1]->resultType()) if $PassTwo; 
      
}
	],
	[#Rule 147
		 'expression', 3,
sub {
 if ($PassOne) {
        $_[0]->_typeCheck($_[1], $_[3]);
        return &_typeOf($_[1], $_[3]);
        }
      if ($PassTwo) {
        my ($left) = $_[1];
        my ($right) = $_[3];
        my ($anyError) = 
         ExpressionTree::Operations::Arithmetic->validate($_[2], $left, $right);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Arithmetic($_[2], $left, $right);
        }
      
}
	],
	[#Rule 148
		 'expression', 3,
sub {
 if ($PassOne) {
        $_[0]->_typeCheck($_[1], $_[3]);
        return &_typeOf($_[1], $_[3]);
        }
      if ($PassTwo) {
        my ($anyError) = 
          ExpressionTree::Operations::Arithmetic->validate($_[2], $_[1], $_[3]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Arithmetic($_[2], $_[1], $_[3]);
        }
      
}
	],
	[#Rule 149
		 'expression', 3,
sub {
 if ($PassOne) {
        $_[0]->_typeCheck($_[1], $_[3]);
        return &_typeOf($_[1], $_[3]);
        }
      if ($PassTwo) {
        my ($anyError) = 
          ExpressionTree::Operations::Arithmetic->validate($_[2], $_[1], $_[3]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Arithmetic($_[2], $_[1], $_[3]);
        }
      
}
	],
	[#Rule 150
		 'expression', 3,
sub {
 if ($PassOne) {
        $_[0]->_typeCheck($_[1], $_[3]);
        return &_typeOf($_[1], $_[3]);
        }
      if ($PassTwo) {
        my ($anyError) = 
          ExpressionTree::Operations::Arithmetic->validate($_[2], $_[1], $_[3]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Arithmetic($_[2], $_[1], $_[3]);
        }
      
}
	],
	[#Rule 151
		 'expression', 2,
sub {
 return $_[2] if $PassOne;
      if ($PassTwo) {
        my ($anyError) = 
          ExpressionTree::Operations::Negation->validate($_[2]);
        $_[0]->_semanticError($anyError) if defined $anyError;
        return new ExpressionTree::Operations::Negation($_[2]);
        }
      
}
	]
],
                                  @_);
    bless($self,$class);
}



my $ErrorMessage = '';

sub _semanticError {
  my ($parser, $msg) = shift;
  ErrorHandler->semanticError($msg, $parser->YYCurval());
}

sub _Error {
  my ($parser) = shift;
  ErrorHandler->syntaxError($ErrorMessage, $parser->YYCurval(), $parser->_dumpRest());
}

# cleans the white space from around a string
sub _cleanWS {
  my ($value) = @_;
  #chew up white space at both ends (parsing of fields depends on this!)
  $value =~ s/^[\s\n]+//;
  $value =~ s/[\s\n]+$//;
  return $value;
}

sub _typeCheck {
  my ($self, $typeOne, $typeTwo) = @_;
  return if $typeOne eq 'Path';
  return if $typeTwo eq 'Path';
  return if $typeOne eq 'Node';
  return if $typeTwo eq 'Node';
  return if $typeOne eq $typeTwo;
  $self->_semanticError("Illegal types in operation: $typeOne, $typeTwo");
}

sub _typeOf {
  my ($typeOne, $typeTwo) = @_;
  return $typeOne if $typeTwo eq 'Path';
  return $typeOne if $typeTwo eq 'Node';
  return $typeTwo;
}

sub _dumpRest {
  my($parser)=shift;
  my $s = '';
  foreach my $thing (@{$parser->{'tokens'}}) {
    return $s if @$thing[0] eq 'eof';
    $s .= @$thing[2] if defined @$thing[1];
    }
  return ''; 
}


my $TokenText = '';
my $previousTokenText = '';
my $CapturingTokenText = 0;

sub _captureTokenText {
  $TokenText = '';
  $previousTokenText = '';
  $CapturingTokenText = 1;
  }

sub _fetchTokenText {
  $CapturingTokenText = 0;
  return $previousTokenText;
  }

sub _Lexer {
    my ($parser) = shift;

    $FullDescriptor = 0;
    
    return ['', undef, undef] unless scalar @{$parser->{'tokens'}};
    my $token = shift @{$parser->{'tokens'}};
    ErrorHandler->appendText(@$token[2]) if defined @$token[2];
    $previousTokenText = $TokenText if $CapturingTokenText;
    $TokenText .= @$token[2] if $CapturingTokenText;
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
     'set' => 'set', 
     'default' => 'default', 
     'property' => 'property', 
     'path' => 'path', 
     'select' => 'select', 
     'coalesce' => 'coalesce',
     'nodes' => 'nodes',
     'property' => 'property',
     'dimension' => 'property',
     'collapse' => 'collapse',
     'match' => 'match',
     'slice' => 'slice',
     'roots' => 'roots',
     'any' => 'any',
     'from' => 'from',
     'where' => 'where',
     'max' => 'aggregate',
     'min' => 'aggregate',
     'count' => 'aggregate',
     'avg' => 'aggregate',
     'sum' => 'aggregate',
     'overlaps' => 'comparison',
     'contains' => 'comparison',
     'meets' => 'comparison',
     'precedes' => 'comparison',
     'or' => 'or',
     'and' => 'and',
     'not' => 'not',
     'nonnull' => 'nonnull',
     'null' => 'null',
     'constant' => 'constant',
     );

  # gobble white space
  $s =~ s/^\s*//;
  my $originalText = $&;
  while ($s) {
    my %token = ();
    $token{'kind'} =  'ignore';
    if ($s =~ s/^(\Q->\E)//) {
      $token{'kind'} =  'edge';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^'([^']*)'//) {
      $token{'kind'} =  'string';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^\[(\w+(\/\w+\/\d+)?\s*-\s*\w+(\/\w+\/\d+)?)\s*\]//) {
      $token{'kind'} =  'timeInterval';
      $token{'text'} =  _cleanWS($1);
      }
    #elsif ($s =~ s/^(\d+[\.]?[\d]*)\s*//) {
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
    elsif ($s =~ s/^(\?)//) {
      $token{'kind'} =  'question mark';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q!\E)//) {
      $token{'kind'} =  'exclamation mark';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q:\E)//) {
      $token{'kind'} =  'colon';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q;\E)//) {
      $token{'kind'} =  'semi-colon';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/(^\])//) {
      $token{'kind'} =  'right bracket';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\[)//) {
      $token{'kind'} =  'left bracket';
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
    elsif ($s =~ s/^([A-Za-z][A-Za-z0-9_]*)//) {
      $token{'text'} =  _cleanWS($1);
      if (defined $keywords{lc $1}) {
        $token{'kind'} =  $keywords{lc $1};
        }
      else {
        $token{'kind'} =  'identifier';
        }
      }
    elsif ($s =~ s/^(\Q:\E)//) {
      $token{'kind'} =  'colon';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q+\E)//) {
      $token{'kind'} =  '+';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q*\E)//) {
      $token{'kind'} =  '*';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\/)//) {
      $token{'kind'} =  '/';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/^(\Q-\E)//) {
      $token{'kind'} =  '-';
      $token{'text'} =  $1;
      }
    elsif ($s =~ s/(.)//) {
      $token{'kind'} =  'ignore';
      $token{'text'} =  $1;
      }
    else {
      $_[0]->_semanticError("can't possibly reach here");
      }
    $token{'originalText'} = "$originalText$&";
    push @tokens, [$token{'kind'}, $token{'text'}, $token{'originalText'}];
    # gobble white space
    $s =~ s/^\s*//;
    $originalText = $&;
    }

  # Modify the token stream to handle property lists.
  my $left;
  my $doRight = 0;
  my $veryNextOne = 0;
  foreach my $token (@tokens) {

    # This check replaces () pairs
    if ($veryNextOne == 1) {
      $veryNextOne = 0;
      if (@$token[0] eq 'right parenthesis') {
        @$left[0] = 'left brace';
        @$token[0] = 'right brace';
        }
      }
    $veryNextOne = 0;
    if (@$token[0] eq 'left parenthesis') {
      if ($doRight == 0) { 
        $veryNextOne = 1;
        $left = $token;
        }
      else {
        $doRight += 1;
        }
      }
    elsif (@$token[0] eq 'colon' || @$token[0] eq 'exclamation mark') {
      @$left[0] = 'left brace' if defined $left;
      $doRight += 1 unless $doRight > 0;
      }
    elsif (@$token[0] eq 'right parenthesis') {
      if ($doRight == 1) { 
        $doRight = 0;
        @$token[0] = 'right brace';
        }
      elsif ($doRight > 0) {
        $doRight -= 1;
        }
      }
    }

  # Set up the two passes
  my @allTokens = ();
  push @tokens, ['eof', 'eof', ''];
  foreach my $token (@tokens) {push @allTokens, $token;}
  foreach my $token (@tokens) {push @allTokens, $token;}
  # get rid of EOF
  pop @allTokens;
  push @allTokens, ['', undef, undef];
  $self->{'tokens'} = \@allTokens;
  #push @tokens, ['', undef, undef];
  #$self->{'tokens'} = \@tokens;
}

my $labelPrefix = "_gen";
my $labelCount= "0";
sub _newVariable {
  my ($var) = "$labelPrefix" . $labelCount++;
  $Variables{$var} = 1;
  return $var;
  }

sub _expecting {
  my ($s) = @_;
  $ErrorMessage = "Expecting $s";
  }

sub _addDefaults {
  my ($h) = @_;
  foreach my $key (keys %DefaultProperties) { 
    $$h{$key} = $DefaultProperties{$key} unless defined $$h{$key};
    }
  return $h;
  }

sub _establishDefaults {
  my ($h) = @_;
  foreach my $key (keys %$h) { $DefaultProperties{$key} = $$h{$key}; }
  }

#my %DefaultCoercions = (
#my %GoodCoercions = (
#  '=' => {
#    'Integer' => { 
#      'Integer' => 1,
#      }
#    'String' => { 
#      'String' => 1,
#      }
#    'TimeInterval' => { 
#      'TimeInterval' => 1,
#      }
#    'Default' => { 
#      'TimeInterval' => 1,
#  '<>' => {

1;
