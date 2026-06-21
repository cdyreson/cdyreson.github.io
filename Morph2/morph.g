grammar morph;	
options {
  k=2;
  }

@header {
  package grammar;
  import algebra.*;
  import java.lang.UnsupportedOperationException;
  }

@lexer::header {
  package grammar;
  import java.lang.UnsupportedOperationException;
  }

@members {

protected void mismatch (
  IntStream input, 
  int ttype, 
  BitSet follow) throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);
  }

public String recoverFromMismatchedSet (
  IntStream input,
  RecognitionException e,
  BitSet follow) throws RecognitionException {
    throw e;
  }
}

@rulecatch {
  catch (RecognitionException e) {
    throw e;
  }
}
  
program returns [Query query] :
    function EOF {
    $query = new Query($function.exp);
    }
  ;
  /*
  catch[RecognitionException re] {
    //String msg = $program.text;
    String msg = "";
    int ind = msg.lastIndexOf('\n');
    String msgsub = msg.substring(ind+1);
    int len = msgsub.length();
    String emstr = "\n";
    for (int i=0; i <= len; i++) emstr=emstr+" ";
    String synstr = emstr;
    synstr = synstr + "Syntax Error";
    emstr = emstr + "^";
    msg = msg + emstr + synstr + "\n There is a syntax error at the end of the above pattern\n";                        
    System.out.println(msg);
    }*/

function returns [Operator exp] :	 
  | MORPH pattern {
      $exp = $pattern.exp;
    } 
  | MUTATE pattern {
      $exp = $pattern.exp;
    } 
  | DATA data {
      $exp = $data.exp;
    }
  | TRANSLATE {Dictionary dict = new Dictionary();}
      dictionary[dict]
  ;
  
pattern returns [Operator exp] :
  parent ('[' children[$parent.exp] ']') ? {
    if ($children.exp == null) {
       $exp = $parent.exp;
      }
    else {
      $exp = $children.exp;
      }
    }
  ;

dynamicGrouping returns [Operator exp]:	
  GROUP '(' pattern ')' {
    $exp = new Group($pattern.exp);
    }
  ;

parent returns [Operator exp]:
  repr {
    $exp = $repr.exp;
    }
  ;
	
repr returns [Operator exp] :
  label ( 
    m=modifier[new Type($label.s)] { 
      //System.out.println("Type is " + $label.s);
      $exp = $m.exp; 
      }
    | ',' NEW  { 
      $exp = new NewLabel($label.s); 
      }
    )
  ;
                                           
children[Operator parent] returns [Operator exp] :
  pattern {
   // Java/antlr is not smart enough to do polymorphic call, 
   // so have to hard code it.
   try {
     //System.out.println("Children");
     if ($parent.getClass().equals(Class.forName("algebra.NewLabel"))) {
       $parent = new WrapWithNewLabel((NewLabel)$parent, $pattern.exp);
       }
     else {
       if ($pattern.exp.getClass().equals(Class.forName("algebra.NewLabel"))) {
         $parent = new JoinNewLabel($parent, (NewLabel)$pattern.exp);
         }
       else {
         //System.out.println("New LCAJoin");
         $parent = new LCAJoin($parent, $pattern.exp);
         }
       }
   } catch (ClassNotFoundException e) {
     System.out.println("No class found");
     System.exit(-1);
     };
   //System.out.println(" p " + $parent);
   }
  m=children[$parent] {
      if ($m.exp != null) {
        //System.out.println("Not empty children" + $m.exp);
        $exp = $m.exp;
        }
      else {
        //System.out.println("Empty children");
        $exp = $parent;
        }
    }
  | dynamicGrouping {
      $exp = new LCAJoin($parent, new Group($dynamicGrouping.exp)); 
    }
  | {
      $exp = $parent;
    }
  ;

child returns [Operator exp]:
  repr {
      $exp = $repr.exp;
      //System.out.println("doing repr" + $exp);
    }
  ;

modifier[Type in] returns [Operator exp] :
  ',' ( HIDE {
            $in = new Hide($in);
          }
        | CLONE {
            $in = new Clone($in);
          }
        | WHERE condition {
            $in = new Where($in, $condition.exp);
          } 
        | GROUP {
            $in = new Group($in);
          }
      ) m=modifier[$in] {
          $exp = $m.exp;
        }
  | {
      $exp = $in;
    }
  ;

label returns [String s] :
  ID t=labelContinues[$ID.text] { 
      $s = $t.s1;
    } 
  ;

labelContinues[String s] returns [String s1] : 
  '.' ID t=labelContinues["." + $ID.text] {
      $s1 = $s + $t.s1;
    }
  | {
      $s1 = $s;
    }
  //  '[' INT ']' | 
  ;

data returns [Operator exp] : 
  STRING | '{' function '}' {
    $exp = null;
    }
  ;

dictionary[Dictionary t]:
//dictionary[Dictionary $dict] returns [Operator exp]: 
  label '->' ID {
    t.add($label.text, $ID.text);
    } 
  ( dictionary[t] )?
  ;

condition returns [Operator exp]: VALUE w = whereConditionContinues {$exp = $w.exp;};

whereConditionContinues returns[Operator exp]:
  'not' a = whereConditionStillContinues
  b = whereClauseContinues [new Not($a.exp)] { 
      if (b == null) {
        $exp  = new Not($a.exp);
      } else {
        $exp = $b.exp;
      }
    }
  | a = whereConditionStillContinues b = whereClauseContinues[$a.exp] { 
      if (b == null) {
        $exp  = $a.exp;
      } else {
        $exp = $b.exp;
      }
    }
;

whereConditionStillContinues returns [Operator exp]:
  comparator  STRING {
     // Take off the " characters
     String s = $STRING.text;
     String stripped = s.substring(1, s.length() - 1);  
     $exp = new Comparator($comparator.value, stripped); 
     }
  | comparator i=integer {
     $exp = new Comparator($comparator.value, $i.value);
     }
  ;

whereClauseContinues [Operator exp1] returns [Operator exp]:
  'and' condition {
    $exp = new And($exp1, $condition.exp);
    }
  |'or' condition {
    $exp = new Or($exp1, $condition.exp);
    }
  |
  ;
  
integer	returns [ int value]:	
  INT {
    value = Integer.parseInt($INT.text);
    }
  ;

comparator returns [int value]:	 
  EQUALS {$value = Where.EQ;} 
  | LE {$value = Where.LE;} 
  | GE {$value =  Where.GE;}
  | GT {$value = Where.GT;} 
  | LT {$value = Where.LT;}
  ;

/*
condition: 'value' whereConditionContinues;

whereConditionContinues:
  'not' whereConditionStillContinues {
      // new Not();
      }
  whereClauseContinues
  | whereConditionStillContinues whereClauseContinues;

whereConditionStillContinues: 
  COMPARATOR STRING {
    }
  ;

whereClauseContinues: 
  'and' condition {
    //comp = new And(comp1, comp2);
    }
  |'or' condition {
    //comp = new Or(comp1, comp2);
    }
  |
  ;
*/
/*
MORPH 	:	('m''M')('o''O')('r''R')('p''P')('h''H');
MUTATE 	:	('m''M')('u''U')('t''T')('a''A')('t''T')('e''E');
DATA 	:	('d''D')('a''A')('t''T')('a''A');
TRANSLATE:	('t''T')('r''R')('a''A')('n''N')('s''S')('l''L')('a''A')('t''T')('e''E');
HIDE 	:	('h''H')('i''I')('d''D')('e''E');
CLONE 	:	('c''C')('l''L')('o''O')('n''N')('e''E');
WHERE 	:	('w''W')('h''H')('e''E')('r''R')('e''E');
GROUP 	:	('g''G')('r''R')('o''O')('u''U')('p''P');
NEW	:	('n''N')('e''E')('w''W');
VALUE	:	('v''V')('a''A')('l''L')('u''U')('e''E');
*/
MORPH 	:	'morph';
MUTATE 	:	'mutate';
DATA 	:	'data';
TRANSLATE:	'translate';
HIDE 	:	'hide';
CLONE 	:	'clone';
WHERE 	:	'where';
GROUP 	:	'group';
NEW	:	'new';
VALUE	:	'value';

ID :   ( LETTER | '_' | ':') (NAMECHAR)* ;

fragment NAMECHAR
    : LETTER | DIGIT | '.' | '-' | '_' | ':'
    ;

fragment DIGIT
    :    '0'..'9'
    ;

fragment LETTER
    : 'a'..'z'
    | 'A'..'Z'
    ;

WILDCARD 
	:	'*';
NOT 	:	'!';
INT	:	'0'..'9'+ ;

LBRACKET:	'[';

RBRACKET:	']';

RBRACE 	:	'}' ;

LBRACE 	:	'{' ;

/*COMPARATOR returns [int value]:	 '==' {$value = Where.EQ;} | '=' | '<=' | '>=' ;	*/ 

EQUALS	:	'==';
LE 	:	'<=';
GE 	:	'>=';
GT	: 	'>';
LT	: 	'<';

STRING 	: 	('"' (~'"')* '"') | ('\'' (~'\'')* '\'');
EOS	:	';' ;
ARROW	:       '->';
PIPE 	:	'|';
WS      :       (' '|'\r'|'\n')+ {$channel = HIDDEN;} ;

