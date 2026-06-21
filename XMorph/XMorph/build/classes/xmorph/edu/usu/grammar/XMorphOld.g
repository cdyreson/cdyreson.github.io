grammar XMorph;	

options {
  k=2;
  }

@header {
package xmorph.grammar;
import xmorph.algebra.*;
import java.lang.UnsupportedOperationException;
  }

@lexer::header {
package xmorph.grammar;
import xmorph.algebra.*;
import java.lang.UnsupportedOperationException;
  }

@members {

protected static String errorMessage = "";
protected boolean hasError = false;

public boolean hasError() {
   return hasError;
}

public String getErrorMessage() {
   return errorMessage;
}

protected void mismatch (
  IntStream input, 
  int ttype, 
  BitSet follow) throws RecognitionException {
            MismatchedTokenException e = new MismatchedTokenException(ttype, input);       
            errorMessage = buildErrorMessage(e);
        hasError = true;
    throw new XMorphParseException(errorMessage);
  }

public String recoverFromMismatchedSet (
  IntStream input,
  RecognitionException e,
  BitSet follow) throws RecognitionException {
  //BitSet follow) throws XMorphParseException {
          errorMessage = buildErrorMessage(e);
        hasError = true;
    throw e;
    //throw new XMorphParseException(buildErrorMessage(e));
  }

public Object recoverFromMisMatchedToken(
  IntStream input,
  RecognitionException e,
  BitSet follow) throws RecognitionException {
    //BitSet follow) throws XMorphParseException {
            errorMessage = buildErrorMessage(e);
        hasError = true;
    throw e;
    //throw new XMorphParseException(buildErrorMessage(e));
  }
  

       private String buildErrorMessage(
                                   RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        // Now do something with hdr and msg...
        String lines[] = input.toString().split("\n");
        int line = e.line;
        Token token = e.token;
        int position = token.getCharPositionInLine(); //e.charPositionInLine;
        int length = e.charPositionInLine - position;
        length = (length <= 0)? 1 : length;
        String output = "";
        for (String s : lines) {
          output += s + "\n";
          if (line-- == 1) {
             for (int i = position; i > 0; i--) {
                output += " ";
             }
             for (int i = length; i > 0; i--) {
                output += "^";
             }
             output += "Parse Error: \"" + token.getText() + "\" " + msg + "\n"; 
          }
        }
        return output;
    }
    
    
      public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        // Now do something with hdr and msg...
        
        errorMessage = buildErrorMessage(e);
        hasError = true;
        //throw new XMorphParseException(errorMessage);
        
    }
    
    

}

@lexer::members {
    
      public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
   
        //errorMessage = buildErrorMessage(e);
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, XMorphParser.tokenNames);
        // Now do something with hdr and msg...
        XMorphParser.errorMessage = "^Lexical Error in query at " + hdr + ": " + msg + "\n"; 
        
    }
}   

@rulecatch {
  catch (RecognitionException e) {
    throw e;
  }
  }
  

  
program returns [Query query] :
  pipe EOF {
    $query = new Query($pipe.exp);
    if (hasError) { throw new XMorphParseException(errorMessage); }
    };
    
    
pipe returns [Operator exp] :
    function ( PIPE p=pipe )? {
    if ($p.exp == null) {
       $exp = $function.exp;
      }
    else {
       //$exp = new Pipe($function.exp, $p.exp);
       // Java/antlr is not smart enough to do polymorphic call, 
       // so have to hard code it.
       try {
          //System.out.println("Children");
          if ($function.exp.getClass().equals(Class.forName("xmorph.algebra.Translate"))) {
            if ($p.exp.getClass().equals(Class.forName("xmorph.algebra.Translate"))) {
              $exp = new Pipe((Translate)$function.exp, (Translate)$p.exp);
              }
            else {
              $exp = new Pipe((Translate)$function.exp, $p.exp);
              }
          } else {
            if ($p.exp.getClass().equals(Class.forName("xmorph.algebra.Translate"))) {
              $exp = new Pipe($function.exp, (Translate)$p.exp);
            } else {
              $exp = new Pipe($function.exp, $p.exp);
            }
          }
       } catch (ClassNotFoundException e) {
           System.out.println("No class found");
           System.exit(-1);
       };
      }
    }
    ;

function returns [Operator exp] :	 
  | MORPH shape {
      $exp = $shape.exp;
    } 
  | MUTATE shape {
      $exp = new Mutate($shape.exp);
    } 
  | COMPOSE  f1=function  WITH f2=function {
      $exp = new Pipe($f1.exp, $f2.exp);
    }
  | DATA data {
      $exp = $data.exp;
    }
  | GROUP f3=shape BY f4=shape {
        $exp = new DynamicGroup($f3.exp, $f4.exp);
    }
  | RESTRICT shape {
        $exp = new Hide($shape.exp);
    }  
  | TRANSLATE {Dictionary dict = new Dictionary();}
      dictionary[dict] {
      $exp = new Translate(dict);
    }
  ;
  
shape returns [Operator exp] :
  parent ('[' children[$parent.exp] ']') ? {
    if ($children.exp == null) {
       $exp = $parent.exp;
      }
    else {
      $exp = $children.exp;
      }
    }
  ;

dynamicGrouping[Operator in] returns [Operator exp]:	
  GROUP '(' shape ')' {
    $exp = new DynamicGroup($in, $shape.exp);
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
  shape {
   // Java/antlr is not smart enough to do polymorphic call, 
   // so have to hard code it.
   try {
     //System.out.println("Children");
     //System.out.println($parent.getClass());
     //System.out.println($shape.exp.getClass());
     if ($parent.getClass().equals(Class.forName("xmorph.algebra.NewLabel"))) {
       $parent = new WrapWithNewLabel((NewLabel)$parent, $shape.exp);
       }
     else {
       if ($shape.exp.getClass().equals(Class.forName("xmorph.algebra.NewLabel"))) {
         $parent = new JoinNewLabel($parent, (NewLabel)$shape.exp);
         }
       else {
         //System.out.println("New LCAJoin");
         $parent = new LCAJoin($parent, $shape.exp);
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

modifier[Operator in] returns [Operator exp] :
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
        | OPTIONAL {
            $in = new Optional($in);
          }
        | dynamicGrouping[$in] {
            $in = $dynamicGrouping.exp;
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
  STRING  {
      $exp = new Data($STRING.text);
    } 
    | '{' function '}'
    | ID {
      $exp = new Data($ID.text);
    }
  ;

dictionary[Dictionary t]:
  label '->' ID {
    t.put($label.text, $ID.text);
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

whereConditionStillContinues returns [Comparator exp]:
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

MORPH 	:	('m'|'M')('o'|'O')('r'|'R')('p'|'P')('h'|'H');
MUTATE 	:	('m'|'M')('u'|'U')('t'|'T')('a'|'A')('t'|'T')('e'|'E');
COMPOSE :	('c'|'C')('o'|'O')('m'|'M')('p'|'P')('o'|'O')('s'|'S')('e'|'E');
RESTRICT :	('r'|'R')('e'|'E')('s'|'S')('t'|'T')('r'|'R')('i'|'I')('c'|'C')('t'|'T');
CAST 	:	('c'|'C')('a'|'A')('s'|'S')('t'|'T');
WITH 	:	('w'|'W')('i'|'I')('t'|'T')('h'|'H');
DROP 	:	('d'|'D')('r'|'R')('o'|'O')('p'|'P');
TYPEFILL :	('t'|'T')('y'|'Y')('p'|'P')('e'|'E')'-'('f'|'F')('i'|'I')('l'|'L');
CASTWIDENING :	('c'|'C')('a'|'A')('s'|'S')('t'|'T')'-'('w'|'W')('i'|'I')('d'|'D')('e'|'E')('n'|'N')('i'|'I')('n'|'N')('g'|'G');
CASTNARROWING :	('c'|'C')('a'|'A')('s'|'S')('t'|'T')'-'('n'|'N')('a'|'A')('r'|'R')('r'|'R')('o'|'O')('w'|'W')('i'|'I')('n'|'N')('g'|'G');
CHILDREN :	('c'|'C')('h'|'H')('i'|'I')('l'|'L')('d'|'D')('r'|'R')('e'|'E')('n'|'N');
DESCENDANTS :	('d'|'D')('e'|'E')('s'|'S')('c'|'C')('e'|'E')('n'|'N')('d'|'D')('a'|'A'|'e'|'E')('n'|'N')('t'|'T')('s'|'S');
DATA 	:	('d'|'D')('a'|'A')('t'|'T')('a'|'A');
TRANSLATE:	('t'|'T')('r'|'R')('a'|'A')('n'|'N')('s'|'S')('l'|'L')('a'|'A')('t'|'T')('e'|'E');
HIDE 	:	('h'|'H')('i'|'I')('d'|'D')('e'|'E');
CLONE 	:	('c'|'C')('l'|'L')('o'|'O')('n'|'N')('e'|'E');
WHERE 	:	('w'|'W')('h'|'H')('e'|'E')('r'|'R')('e'|'E');
GROUP 	:	('g'|'G')('r'|'R')('o'|'O')('u'|'U')('p'|'P');
BY	:	('b'|'B')('y'|'Y');
NEW	:	('n'|'N')('e'|'E')('w'|'W');
VALUE	:	('v'|'V')('a'|'A')('l'|'L')('u'|'U')('e'|'E');
OPTIONAL: 	('o'|'O')('p'|'P')('t'|'T')('i'|'I')('o'|'O')('n'|'N')('a'|'A')('l'|'L');	

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

WILDCARD :	'*';
DOUBLEWILDCARD : '**';
NOT 	:	'!';
INT	:	'0'..'9'+ ;
LBRACKET:	'[';
RBRACKET:	']';
RBRACE 	:	'}' ;
LBRACE 	:	'{' ;

/*COMPARATOR returns [int value]:	 '==' {$value = Where.EQ;} | '=' | '<=' | '>=' ;	*/ 

EQUALS	:	'=''='?;
LE 	:	'<=';
GE 	:	'>=';
GT	: 	'>';
LT	: 	'<';


STRING 	: 	('"' (~'"')* '"') | ('\'' (~'\'')* '\'');
EOS	:	';' ;
ARROW	:       '->';
PIPE 	:	'|';
WS      :       (' '|'\r'|'\n')+ {$channel = HIDDEN;} ;



