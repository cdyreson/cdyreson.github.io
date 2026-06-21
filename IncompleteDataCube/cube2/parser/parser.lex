import java.lang.System;
import java_cup.runtime.*;

%%

%cup
%class scanner
%function next_token
%type Integer

WHITE_SPACE_CHAR=[\n\ \t\b\012]
ALPHA=[A-Za-z]
DIGIT=[0-9]
NONZERODIGIT=[1-9]
ZERODIGIT=[0]

%% 

{NONZERODIGIT}{DIGIT}*
  { String str =  yytext().substring(0,yytext().length());
    //System.out.print(yytext());     
    Integer i = new Integer(str);
    return i;
  }

n
  { 
    //System.out.print(yytext());     
    return new Integer(0); 
  }

t
  { 
    //System.out.print(yytext());     
    return new Integer(-1); 
  }

{WHITE_SPACE_CHAR}+ 
  {
  }
