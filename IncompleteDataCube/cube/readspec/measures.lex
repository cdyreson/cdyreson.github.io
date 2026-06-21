import java.lang.System;
import java_cup.runtime.*;

%%

%cup
%class scanner
%function next_token
%type token

WHITE_SPACE_CHAR=[\n\ \t\b\012]
ALPHA=[A-Za-z]
DIGIT=[0-9]

%% 

"FILTERS:"  
  { System.out.print(yytext()); return(new token(sym.FILTERS)); }
"UNIT:"  
  { System.out.print(yytext()); return(new token(sym.UNIT)); }
"MEASURE:"  
  { System.out.print(yytext()); return(new token(sym.MEASURE)); }
({ALPHA}|{DIGIT}|_)+ 
  { String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
\{[^}]*\}    
  { System.out.print(yytext()); 
    String str = yytext().substring(1,yytext().length() - 1);
    return(new str_token(sym.REGULAR_EXPRESSION, str)); 
  }
\([^)]*\)    
  { System.out.print(yytext()); 
    String str = yytext().substring(1,yytext().length() - 1);
    return(new str_token(sym.REGULAR_EXPRESSION, str)); 
  }
\"[^\"]*\" 
  { String str =  yytext().substring(1,yytext().length()-1);
    System.out.print(yytext());  
    return(new str_token(sym.IDENTIFIER, str)); 
  }
'[^']*' 
  { String str =  yytext().substring(1,yytext().length()-1);
    System.out.print(yytext());  
    return(new str_token(sym.IDENTIFIER, str)); 
  }
";"
  { System.out.print(yytext()); return(new token(sym.SEMICOLON)); }
"."
  { System.out.print(yytext()); return(new token(sym.PERIOD)); }
"="                         
  { System.out.print(yytext()); return(new token(sym.EQUALS)); }
"@"                         
  { System.out.print(yytext()); return(new token(sym.AT)); }
{WHITE_SPACE_CHAR}+ 
  { System.out.print(yytext()); }
