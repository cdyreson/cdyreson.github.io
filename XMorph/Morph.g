grammar XMorph;	

options {
  k=2;
  }

@header {
package xmorph.edu.usu.grammar;
import xmorph.edu.usu.algebra.*;
import java.lang.UnsupportedOperationException;
  }

@lexer::header {
package xmorph.edu.usu.grammar;
import xmorph.edu.usu.algebra.*;
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
    topLevelFunction {
        $query = new Query($topLevelFunction.exp);
    	if (hasError) { throw new XMorphParseException(errorMessage); }
    	}
    EOF ;  
  
topLevelFunction returns [Operator exp] :
    typeCheckFunction {$exp = $typeCheckFunction.exp; }
    |
    nonComposeTopLevelFunction {$exp = $nonComposeTopLevelFunction.exp;}
    |
    composeFunction {$exp = $composeFunction.exp;}
    ;

typeCheckFunction returns [Operator exp] :
    typeFillFunction {$exp = $typeFillFunction.exp;}
    |
    castWideningFunction {$exp = $castWideningFunction.exp;}
    |    
    castNarrowingFunction {$exp = $castNarrowingFunction.exp;}
    |
    castFunction {$exp = $castFunction.exp;}
    ; 
    
nonComposeTopLevelFunction returns [Operator exp] :
    translateFunction {$exp = $translateFunction.exp;}
    |
    morphFunction {$exp = $morphFunction.exp;}
    |
    mutateFunction {$exp = $mutateFunction.exp;}
    ;   
  
secondaryFunction returns [Operator exp] :
    newFunction {$exp = $newFunction.exp;}
    |
    translateFunction {$exp = $translateFunction.exp;}
    |
    groupFunction {$exp = $groupFunction.exp;}
    |
    restrictFunction {$exp = $restrictFunction.exp;}
    |
    cloneFunction {$exp = $cloneFunction.exp;}
    |
    asElementFunction {$exp = $asElementFunction.exp;}
    |
    orderByFunction {$exp = $orderByFunction.exp;}
    |
    dropFunction {$exp = $dropFunction.exp;}
    |
    asAttributeFunction {$exp = $asAttributeFunction.exp;}
    ;
        
composeFunction returns [Operator exp] :
/*
    nonComposeTopLevelFunction ( PIPE p=topLevelFunction )? {
    if ($p.exp == null) {
       $exp = $function.exp;
      }
    else {
       //$exp = new Pipe($function.exp, $p.exp);
       // Java/antlr is not smart enough to do polymorphic call, 
       // so have to hard code it.
       try {
          //System.out.println("Children");
          if ($function.exp.getClass().equals(Class.forName("xmorph.No class foundalgebra.Translate"))) {
            if ($p.exp.getClass().equals(Class.forName("xmorph.No class foundalgebra.Translate"))) {
              $exp = new Pipe((Translate)$function.exp, (Translate)$p.exp);
              }
            else {
              $exp = new Pipe((Translate)$function.exp, $p.exp);
              }
          } else {
            if ($p.exp.getClass().equals(Class.forName("xmorph.No class foundalgebra.Translate"))) {
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
    | 
    */
    COMPOSE f1=nonComposeTopLevelFunction  WITH f2=topLevelFunction {
      $exp = new Pipe($f1.exp, $f2.exp);
    }
    ;

castNarrowingFunction returns [Operator exp] :	 
   CASTNARROWING nonComposeTopLevelFunction {
      $exp = new TypeAllowNarrowing($nonComposeTopLevelFunction.exp);
    }
    ;
    
castWideningFunction returns [Operator exp] :	 
   CASTWIDENING nonComposeTopLevelFunction {
      $exp = new TypeAllowWidening($nonComposeTopLevelFunction.exp);
    }
    ;
   
castFunction returns [Operator exp] :	 
   CAST nonComposeTopLevelFunction {
      $exp = new TypeAllowNarrowing(new TypeAllowWidening($nonComposeTopLevelFunction.exp));
    }
    ;
    
typeFillFunction returns [Operator exp] :
    TYPEFILL nonComposeTopLevelFunction {
      $exp = new TypeFill($nonComposeTopLevelFunction.exp);
    }
    ;
        
descendantFunction  :	 
   (DESCENDANTS | DOUBLEWILDCARD) 
    ;

childrenFunction :	 
   (CHILDREN | WILDCARD)
    ;

morphFunction returns [Operator exp] :	 
   MORPH shape {
      $exp = $shape.exp;
    }
    ; 
    
cloneFunction returns [Operator exp] :	 
   CLONE shape {
      $exp = new Clone($shape.exp);
    }
    ; 

/*
newFunction  NEW  { 
           $exp = new NewLabel($label.s); 
          }
          */
                    
asAttributeFunction returns [Operator exp] :	 
   ASATTRIBUTE shape {
      $exp = new AsAttribute($shape.exp);
    }
    ; 

dropFunction returns [Operator exp] :	 
   DROP shape {
      //System.out.println("droppi in grammar " + $shape.exp);
      $exp = new Drop($shape.exp);
    }
    ; 
    
asElementFunction returns [Operator exp] :	 
   ASELEMENT shape {
      $exp = new AsElement($shape.exp);
    }
    ; 

newFunction returns [Operator exp] :	 
   NEW label shape {
      $exp = new NewTag($label.s, $shape.exp);
    }
    ; 
     
mutateFunction returns [Operator exp] :  
   MUTATE shape {
      $exp = new Mutate($shape.exp);
    }
    ; 
  
/*  
  | DATA data {
      $exp = $data.exp;
    }
*/
translateFunction returns [Operator exp] :
   TRANSLATE {Dictionary dict = new Dictionary();}
      dictionary[dict] {
      $exp = new Translate(dict);
    }
  ;

orderByFunction returns [Operator exp] :
   ORDER f3=shape BY f4=shape {
          $exp = new OrderBy($f3.exp, $f4.exp);
   }
   ;
     
groupFunction returns [Operator exp] :
   GROUP f3=shape 
     (
       BY f4=shape 
         {$exp = new DynamicGroup($f3.exp, $f4.exp);}
       | 
         {$exp = new Group($f3.exp);}
     )
   ;
   
restrictFunction returns [Operator exp] :
   RESTRICT shape {
        $exp = new Hide($shape.exp);
    }
    ;
  
shape returns [Operator exp] :
  parent ('['  
    children[$parent.exp]   
    ']') ? {
        if ($children.exp == null) {$exp = $parent.exp;}
        else {$exp = $children.exp;}
        }
  ;

dynamicGrouping[Operator in] returns [Operator exp]:	
  GROUP '(' shape ')' {
    $exp = new DynamicGroup($in, $shape.exp);
    }
  ;

parent returns [Operator exp]:
  labelAndModifiers {
    $exp = $labelAndModifiers.exp;
    }
  ;
	
labelAndModifiers returns [Operator exp] :
  LPAREN secondaryFunction RPAREN {
  	$exp = $secondaryFunction.exp;
  }
  |
  (label (m=modifier[new Type($label.s)] { 
      //System.out.println("Type is " + $label.s);
      $exp = $m.exp; 
      }
      |
     ','  NEW  { 
           $exp = new NewLabel($label.s); 
          }
          )
      )
  ;
                                           
children[Operator parent] returns [Operator exp] :
  ( descendantFunction {$exp = new Descendants($parent); }
    | childrenFunction {$exp = new Children($parent); }
    | (
  shape {
   // Java/antlr is not smart enough to do polymorphic call, 
   // so have to hard code it.
   try {
     //System.out.println("Children");
     //System.out.println("parent " + $parent.getClass());
     //System.out.println("shape " + $shape.exp.getClass());
     if ($parent.getClass().equals(Class.forName("xmorph.edu.usu.algebra.NewLabel"))) {
       $parent = new WrapWithNewLabel((NewLabel)$parent, $shape.exp);
       }
     else {
       if ($shape.exp == null) {
         $exp = $parent;
         }
       else {
         if ($shape.exp.getClass().equals(Class.forName("xmorph.edu.usu.algebra.NewLabel"))) {
           $parent = new JoinNewLabel($parent, (NewLabel)$shape.exp);
           }
         else {
         //System.out.println("New LCAJoin");
           $parent = new LCAJoin($parent, $shape.exp);
           }
         }
       }
   } catch (ClassNotFoundException e) {
     System.out.println("No class found in XMorph.g");
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
    )
  | {
      $exp = $parent;
    }
    )
  ;

child returns [Operator exp]:
  labelAndModifiers {
      $exp = $labelAndModifiers.exp;
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
          /* 
        | GROUP {
            $in = new Group($in);
          }
          */
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
    | '{' topLevelFunction '}'
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
ORDER 	:	('o'|'O')('r'|'R')('d'|'D')('e'|'E')('r'|'R');
MUTATE 	:	('m'|'M')('u'|'U')('t'|'T')('a'|'A')('t'|'T')('e'|'E');
COMPOSE :	('c'|'C')('o'|'O')('m'|'M')('p'|'P')('o'|'O')('s'|'S')('e'|'E');
RESTRICT :	('r'|'R')('e'|'E')('s'|'S')('t'|'T')('r'|'R')('i'|'I')('c'|'C')('t'|'T');
CAST 	:	('c'|'C')('a'|'A')('s'|'S')('t'|'T');
WITH 	:	('w'|'W')('i'|'I')('t'|'T')('h'|'H');
DROP 	:	('d'|'D')('r'|'R')('o'|'O')('p'|'P');
TYPEFILL :	('t'|'T')('y'|'Y')('p'|'P')('e'|'E')('-'|' ')('f'|'F')('i'|'I')('l'|'L');
CASTWIDENING :	('c'|'C')('a'|'A')('s'|'S')('t'|'T')('-'|' ')('w'|'W')('i'|'I')('d'|'D')('e'|'E')('n'|'N')('i'|'I')('n'|'N')('g'|'G');
CASTNARROWING :	('c'|'C')('a'|'A')('s'|'S')('t'|'T')('-'|' ')('n'|'N')('a'|'A')('r'|'R')('r'|'R')('o'|'O')('w'|'W')('i'|'I')('n'|'N')('g'|'G');
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
ASELEMENT:	('a'|'A')('s'|'S')('e'|'E')('l'|'L')('e'|'E')('m'|'M')('e'|'E')('n'|'N')('t'|'T');
ASATTRIBUTE:	('a'|'A')('s'|'S')('a'|'A')('t'|'T')('t'|'T')('r'|'R')('i'|'I')('b'|'B')('u'|'U')('t'|'T')('e'|'E');
OPTIONAL: 	('o'|'O')('p'|'P')('t'|'T')('i'|'I')('o'|'O')('n'|'N')('a'|'A')('l'|'L');
REQUIRED: 	('r'|'R')('e'|'E')('q'|'Q')('u'|'U')('i'|'I')('r'|'R')('e'|'E')('d'|'D');	

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
RPAREN	:	')';
LPAREN	:	'(';

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



