// $ANTLR 3.4 C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g 2013-08-12 09:38:22

package xmorph.edu.usu.grammar;
import xmorph.edu.usu.algebra.*;
import java.lang.UnsupportedOperationException;
  

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class XMorphParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ARROW", "ASATTRIBUTE", "ASELEMENT", "BY", "CAST", "CASTNARROWING", "CASTWIDENING", "CHILDREN", "CLONE", "COMPOSE", "DATA", "DESCENDANTS", "DIGIT", "DOUBLEWILDCARD", "DROP", "EOS", "EQUALS", "GE", "GROUP", "GT", "HIDE", "ID", "INT", "LBRACE", "LBRACKET", "LE", "LETTER", "LPAREN", "LT", "MORPH", "MUTATE", "NAMECHAR", "NEW", "NOT", "OPTIONAL", "ORDER", "PIPE", "RBRACE", "RBRACKET", "REQUIRED", "RESTRICT", "RPAREN", "STRING", "TRANSLATE", "TYPEFILL", "VALUE", "WHERE", "WILDCARD", "WITH", "WS", "','", "'.'", "'and'", "'not'", "'or'"
    };

    public static final int EOF=-1;
    public static final int T__54=54;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int ARROW=4;
    public static final int ASATTRIBUTE=5;
    public static final int ASELEMENT=6;
    public static final int BY=7;
    public static final int CAST=8;
    public static final int CASTNARROWING=9;
    public static final int CASTWIDENING=10;
    public static final int CHILDREN=11;
    public static final int CLONE=12;
    public static final int COMPOSE=13;
    public static final int DATA=14;
    public static final int DESCENDANTS=15;
    public static final int DIGIT=16;
    public static final int DOUBLEWILDCARD=17;
    public static final int DROP=18;
    public static final int EOS=19;
    public static final int EQUALS=20;
    public static final int GE=21;
    public static final int GROUP=22;
    public static final int GT=23;
    public static final int HIDE=24;
    public static final int ID=25;
    public static final int INT=26;
    public static final int LBRACE=27;
    public static final int LBRACKET=28;
    public static final int LE=29;
    public static final int LETTER=30;
    public static final int LPAREN=31;
    public static final int LT=32;
    public static final int MORPH=33;
    public static final int MUTATE=34;
    public static final int NAMECHAR=35;
    public static final int NEW=36;
    public static final int NOT=37;
    public static final int OPTIONAL=38;
    public static final int ORDER=39;
    public static final int PIPE=40;
    public static final int RBRACE=41;
    public static final int RBRACKET=42;
    public static final int REQUIRED=43;
    public static final int RESTRICT=44;
    public static final int RPAREN=45;
    public static final int STRING=46;
    public static final int TRANSLATE=47;
    public static final int TYPEFILL=48;
    public static final int VALUE=49;
    public static final int WHERE=50;
    public static final int WILDCARD=51;
    public static final int WITH=52;
    public static final int WS=53;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public XMorphParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public XMorphParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return XMorphParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g"; }



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
        
        




    // $ANTLR start "program"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:129:1: program returns [Query query] : topLevelFunction EOF ;
    public final Query program() throws RecognitionException {
        Query query = null;


        Operator topLevelFunction1 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:129:31: ( topLevelFunction EOF )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:130:5: topLevelFunction EOF
            {
            pushFollow(FOLLOW_topLevelFunction_in_program72);
            topLevelFunction1=topLevelFunction();

            state._fsp--;



                    query = new Query(topLevelFunction1);
                	if (hasError) { throw new XMorphParseException(errorMessage); }
                	

            match(input,EOF,FOLLOW_EOF_in_program80); 

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return query;
    }
    // $ANTLR end "program"



    // $ANTLR start "topLevelFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:136:1: topLevelFunction returns [Operator exp] : ( typeCheckFunction | nonComposeTopLevelFunction | composeFunction );
    public final Operator topLevelFunction() throws RecognitionException {
        Operator exp = null;


        Operator typeCheckFunction2 =null;

        Operator nonComposeTopLevelFunction3 =null;

        Operator composeFunction4 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:136:41: ( typeCheckFunction | nonComposeTopLevelFunction | composeFunction )
            int alt1=3;
            switch ( input.LA(1) ) {
            case CAST:
            case CASTNARROWING:
            case CASTWIDENING:
            case TYPEFILL:
                {
                alt1=1;
                }
                break;
            case MORPH:
            case MUTATE:
            case TRANSLATE:
                {
                alt1=2;
                }
                break;
            case COMPOSE:
                {
                alt1=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }

            switch (alt1) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:137:5: typeCheckFunction
                    {
                    pushFollow(FOLLOW_typeCheckFunction_in_topLevelFunction101);
                    typeCheckFunction2=typeCheckFunction();

                    state._fsp--;


                    exp = typeCheckFunction2; 

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:139:5: nonComposeTopLevelFunction
                    {
                    pushFollow(FOLLOW_nonComposeTopLevelFunction_in_topLevelFunction115);
                    nonComposeTopLevelFunction3=nonComposeTopLevelFunction();

                    state._fsp--;


                    exp = nonComposeTopLevelFunction3;

                    }
                    break;
                case 3 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:141:5: composeFunction
                    {
                    pushFollow(FOLLOW_composeFunction_in_topLevelFunction129);
                    composeFunction4=composeFunction();

                    state._fsp--;


                    exp = composeFunction4;

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "topLevelFunction"



    // $ANTLR start "typeCheckFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:144:1: typeCheckFunction returns [Operator exp] : ( typeFillFunction | castWideningFunction | castNarrowingFunction | castFunction );
    public final Operator typeCheckFunction() throws RecognitionException {
        Operator exp = null;


        Operator typeFillFunction5 =null;

        Operator castWideningFunction6 =null;

        Operator castNarrowingFunction7 =null;

        Operator castFunction8 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:144:42: ( typeFillFunction | castWideningFunction | castNarrowingFunction | castFunction )
            int alt2=4;
            switch ( input.LA(1) ) {
            case TYPEFILL:
                {
                alt2=1;
                }
                break;
            case CASTWIDENING:
                {
                alt2=2;
                }
                break;
            case CASTNARROWING:
                {
                alt2=3;
                }
                break;
            case CAST:
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }

            switch (alt2) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:145:5: typeFillFunction
                    {
                    pushFollow(FOLLOW_typeFillFunction_in_typeCheckFunction152);
                    typeFillFunction5=typeFillFunction();

                    state._fsp--;


                    exp = typeFillFunction5;

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:147:5: castWideningFunction
                    {
                    pushFollow(FOLLOW_castWideningFunction_in_typeCheckFunction166);
                    castWideningFunction6=castWideningFunction();

                    state._fsp--;


                    exp = castWideningFunction6;

                    }
                    break;
                case 3 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:149:5: castNarrowingFunction
                    {
                    pushFollow(FOLLOW_castNarrowingFunction_in_typeCheckFunction184);
                    castNarrowingFunction7=castNarrowingFunction();

                    state._fsp--;


                    exp = castNarrowingFunction7;

                    }
                    break;
                case 4 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:151:5: castFunction
                    {
                    pushFollow(FOLLOW_castFunction_in_typeCheckFunction198);
                    castFunction8=castFunction();

                    state._fsp--;


                    exp = castFunction8;

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "typeCheckFunction"



    // $ANTLR start "nonComposeTopLevelFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:154:1: nonComposeTopLevelFunction returns [Operator exp] : ( translateFunction | morphFunction | mutateFunction );
    public final Operator nonComposeTopLevelFunction() throws RecognitionException {
        Operator exp = null;


        Operator translateFunction9 =null;

        Operator morphFunction10 =null;

        Operator mutateFunction11 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:154:51: ( translateFunction | morphFunction | mutateFunction )
            int alt3=3;
            switch ( input.LA(1) ) {
            case TRANSLATE:
                {
                alt3=1;
                }
                break;
            case MORPH:
                {
                alt3=2;
                }
                break;
            case MUTATE:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }

            switch (alt3) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:155:5: translateFunction
                    {
                    pushFollow(FOLLOW_translateFunction_in_nonComposeTopLevelFunction226);
                    translateFunction9=translateFunction();

                    state._fsp--;


                    exp = translateFunction9;

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:157:5: morphFunction
                    {
                    pushFollow(FOLLOW_morphFunction_in_nonComposeTopLevelFunction240);
                    morphFunction10=morphFunction();

                    state._fsp--;


                    exp = morphFunction10;

                    }
                    break;
                case 3 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:159:5: mutateFunction
                    {
                    pushFollow(FOLLOW_mutateFunction_in_nonComposeTopLevelFunction254);
                    mutateFunction11=mutateFunction();

                    state._fsp--;


                    exp = mutateFunction11;

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "nonComposeTopLevelFunction"



    // $ANTLR start "secondaryFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:162:1: secondaryFunction returns [Operator exp] : ( newFunction | translateFunction | groupFunction | restrictFunction | cloneFunction | asElementFunction | orderByFunction | dropFunction | asAttributeFunction );
    public final Operator secondaryFunction() throws RecognitionException {
        Operator exp = null;


        Operator newFunction12 =null;

        Operator translateFunction13 =null;

        Operator groupFunction14 =null;

        Operator restrictFunction15 =null;

        Operator cloneFunction16 =null;

        Operator asElementFunction17 =null;

        Operator orderByFunction18 =null;

        Operator dropFunction19 =null;

        Operator asAttributeFunction20 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:162:42: ( newFunction | translateFunction | groupFunction | restrictFunction | cloneFunction | asElementFunction | orderByFunction | dropFunction | asAttributeFunction )
            int alt4=9;
            switch ( input.LA(1) ) {
            case NEW:
                {
                alt4=1;
                }
                break;
            case TRANSLATE:
                {
                alt4=2;
                }
                break;
            case GROUP:
                {
                alt4=3;
                }
                break;
            case RESTRICT:
                {
                alt4=4;
                }
                break;
            case CLONE:
                {
                alt4=5;
                }
                break;
            case ASELEMENT:
                {
                alt4=6;
                }
                break;
            case ORDER:
                {
                alt4=7;
                }
                break;
            case DROP:
                {
                alt4=8;
                }
                break;
            case ASATTRIBUTE:
                {
                alt4=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }

            switch (alt4) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:163:5: newFunction
                    {
                    pushFollow(FOLLOW_newFunction_in_secondaryFunction282);
                    newFunction12=newFunction();

                    state._fsp--;


                    exp = newFunction12;

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:165:5: translateFunction
                    {
                    pushFollow(FOLLOW_translateFunction_in_secondaryFunction296);
                    translateFunction13=translateFunction();

                    state._fsp--;


                    exp = translateFunction13;

                    }
                    break;
                case 3 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:167:5: groupFunction
                    {
                    pushFollow(FOLLOW_groupFunction_in_secondaryFunction310);
                    groupFunction14=groupFunction();

                    state._fsp--;


                    exp = groupFunction14;

                    }
                    break;
                case 4 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:169:5: restrictFunction
                    {
                    pushFollow(FOLLOW_restrictFunction_in_secondaryFunction324);
                    restrictFunction15=restrictFunction();

                    state._fsp--;


                    exp = restrictFunction15;

                    }
                    break;
                case 5 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:171:5: cloneFunction
                    {
                    pushFollow(FOLLOW_cloneFunction_in_secondaryFunction338);
                    cloneFunction16=cloneFunction();

                    state._fsp--;


                    exp = cloneFunction16;

                    }
                    break;
                case 6 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:173:5: asElementFunction
                    {
                    pushFollow(FOLLOW_asElementFunction_in_secondaryFunction352);
                    asElementFunction17=asElementFunction();

                    state._fsp--;


                    exp = asElementFunction17;

                    }
                    break;
                case 7 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:175:5: orderByFunction
                    {
                    pushFollow(FOLLOW_orderByFunction_in_secondaryFunction366);
                    orderByFunction18=orderByFunction();

                    state._fsp--;


                    exp = orderByFunction18;

                    }
                    break;
                case 8 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:177:5: dropFunction
                    {
                    pushFollow(FOLLOW_dropFunction_in_secondaryFunction380);
                    dropFunction19=dropFunction();

                    state._fsp--;


                    exp = dropFunction19;

                    }
                    break;
                case 9 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:179:5: asAttributeFunction
                    {
                    pushFollow(FOLLOW_asAttributeFunction_in_secondaryFunction394);
                    asAttributeFunction20=asAttributeFunction();

                    state._fsp--;


                    exp = asAttributeFunction20;

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "secondaryFunction"



    // $ANTLR start "composeFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:182:1: composeFunction returns [Operator exp] : COMPOSE f1= nonComposeTopLevelFunction WITH f2= topLevelFunction ;
    public final Operator composeFunction() throws RecognitionException {
        Operator exp = null;


        Operator f1 =null;

        Operator f2 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:182:40: ( COMPOSE f1= nonComposeTopLevelFunction WITH f2= topLevelFunction )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:216:5: COMPOSE f1= nonComposeTopLevelFunction WITH f2= topLevelFunction
            {
            match(input,COMPOSE,FOLLOW_COMPOSE_in_composeFunction427); 

            pushFollow(FOLLOW_nonComposeTopLevelFunction_in_composeFunction431);
            f1=nonComposeTopLevelFunction();

            state._fsp--;


            match(input,WITH,FOLLOW_WITH_in_composeFunction434); 

            pushFollow(FOLLOW_topLevelFunction_in_composeFunction438);
            f2=topLevelFunction();

            state._fsp--;



                  exp = new Pipe(f1, f2);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "composeFunction"



    // $ANTLR start "castNarrowingFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:221:1: castNarrowingFunction returns [Operator exp] : CASTNARROWING nonComposeTopLevelFunction ;
    public final Operator castNarrowingFunction() throws RecognitionException {
        Operator exp = null;


        Operator nonComposeTopLevelFunction21 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:221:46: ( CASTNARROWING nonComposeTopLevelFunction )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:222:4: CASTNARROWING nonComposeTopLevelFunction
            {
            match(input,CASTNARROWING,FOLLOW_CASTNARROWING_in_castNarrowingFunction462); 

            pushFollow(FOLLOW_nonComposeTopLevelFunction_in_castNarrowingFunction464);
            nonComposeTopLevelFunction21=nonComposeTopLevelFunction();

            state._fsp--;



                  exp = new TypeAllowNarrowing(nonComposeTopLevelFunction21);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "castNarrowingFunction"



    // $ANTLR start "castWideningFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:227:1: castWideningFunction returns [Operator exp] : CASTWIDENING nonComposeTopLevelFunction ;
    public final Operator castWideningFunction() throws RecognitionException {
        Operator exp = null;


        Operator nonComposeTopLevelFunction22 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:227:45: ( CASTWIDENING nonComposeTopLevelFunction )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:228:4: CASTWIDENING nonComposeTopLevelFunction
            {
            match(input,CASTWIDENING,FOLLOW_CASTWIDENING_in_castWideningFunction492); 

            pushFollow(FOLLOW_nonComposeTopLevelFunction_in_castWideningFunction494);
            nonComposeTopLevelFunction22=nonComposeTopLevelFunction();

            state._fsp--;



                  exp = new TypeAllowWidening(nonComposeTopLevelFunction22);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "castWideningFunction"



    // $ANTLR start "castFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:233:1: castFunction returns [Operator exp] : CAST nonComposeTopLevelFunction ;
    public final Operator castFunction() throws RecognitionException {
        Operator exp = null;


        Operator nonComposeTopLevelFunction23 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:233:37: ( CAST nonComposeTopLevelFunction )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:234:4: CAST nonComposeTopLevelFunction
            {
            match(input,CAST,FOLLOW_CAST_in_castFunction521); 

            pushFollow(FOLLOW_nonComposeTopLevelFunction_in_castFunction523);
            nonComposeTopLevelFunction23=nonComposeTopLevelFunction();

            state._fsp--;



                  exp = new TypeAllowNarrowing(new TypeAllowWidening(nonComposeTopLevelFunction23));
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "castFunction"



    // $ANTLR start "typeFillFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:239:1: typeFillFunction returns [Operator exp] : TYPEFILL nonComposeTopLevelFunction ;
    public final Operator typeFillFunction() throws RecognitionException {
        Operator exp = null;


        Operator nonComposeTopLevelFunction24 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:239:41: ( TYPEFILL nonComposeTopLevelFunction )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:240:5: TYPEFILL nonComposeTopLevelFunction
            {
            match(input,TYPEFILL,FOLLOW_TYPEFILL_in_typeFillFunction550); 

            pushFollow(FOLLOW_nonComposeTopLevelFunction_in_typeFillFunction552);
            nonComposeTopLevelFunction24=nonComposeTopLevelFunction();

            state._fsp--;



                  exp = new TypeFill(nonComposeTopLevelFunction24);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "typeFillFunction"



    // $ANTLR start "descendantFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:245:1: descendantFunction : ( DESCENDANTS | DOUBLEWILDCARD ) ;
    public final void descendantFunction() throws RecognitionException {
        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:245:21: ( ( DESCENDANTS | DOUBLEWILDCARD ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
            {
            if ( input.LA(1)==DESCENDANTS||input.LA(1)==DOUBLEWILDCARD ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "descendantFunction"



    // $ANTLR start "childrenFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:249:1: childrenFunction : ( CHILDREN | WILDCARD ) ;
    public final void childrenFunction() throws RecognitionException {
        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:249:18: ( ( CHILDREN | WILDCARD ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
            {
            if ( input.LA(1)==CHILDREN||input.LA(1)==WILDCARD ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "childrenFunction"



    // $ANTLR start "morphFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:253:1: morphFunction returns [Operator exp] : MORPH shape ;
    public final Operator morphFunction() throws RecognitionException {
        Operator exp = null;


        Operator shape25 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:253:38: ( MORPH shape )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:254:4: MORPH shape
            {
            match(input,MORPH,FOLLOW_MORPH_in_morphFunction634); 

            pushFollow(FOLLOW_shape_in_morphFunction636);
            shape25=shape();

            state._fsp--;



                  exp = shape25;
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "morphFunction"



    // $ANTLR start "cloneFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:259:1: cloneFunction returns [Operator exp] : CLONE shape ;
    public final Operator cloneFunction() throws RecognitionException {
        Operator exp = null;


        Operator shape26 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:259:38: ( CLONE shape )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:260:4: CLONE shape
            {
            match(input,CLONE,FOLLOW_CLONE_in_cloneFunction665); 

            pushFollow(FOLLOW_shape_in_cloneFunction667);
            shape26=shape();

            state._fsp--;



                  exp = new Clone(shape26);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "cloneFunction"



    // $ANTLR start "asAttributeFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:271:1: asAttributeFunction returns [Operator exp] : ASATTRIBUTE shape ;
    public final Operator asAttributeFunction() throws RecognitionException {
        Operator exp = null;


        Operator shape27 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:271:44: ( ASATTRIBUTE shape )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:272:4: ASATTRIBUTE shape
            {
            match(input,ASATTRIBUTE,FOLLOW_ASATTRIBUTE_in_asAttributeFunction715); 

            pushFollow(FOLLOW_shape_in_asAttributeFunction717);
            shape27=shape();

            state._fsp--;



                  exp = new AsAttribute(shape27);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "asAttributeFunction"



    // $ANTLR start "dropFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:277:1: dropFunction returns [Operator exp] : DROP shape ;
    public final Operator dropFunction() throws RecognitionException {
        Operator exp = null;


        Operator shape28 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:277:37: ( DROP shape )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:278:4: DROP shape
            {
            match(input,DROP,FOLLOW_DROP_in_dropFunction742); 

            pushFollow(FOLLOW_shape_in_dropFunction744);
            shape28=shape();

            state._fsp--;



                  //System.out.println("droppi in grammar " + shape28);
                  exp = new Drop(shape28);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "dropFunction"



    // $ANTLR start "asElementFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:284:1: asElementFunction returns [Operator exp] : ASELEMENT shape ;
    public final Operator asElementFunction() throws RecognitionException {
        Operator exp = null;


        Operator shape29 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:284:42: ( ASELEMENT shape )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:285:4: ASELEMENT shape
            {
            match(input,ASELEMENT,FOLLOW_ASELEMENT_in_asElementFunction773); 

            pushFollow(FOLLOW_shape_in_asElementFunction775);
            shape29=shape();

            state._fsp--;



                  exp = new AsElement(shape29);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "asElementFunction"



    // $ANTLR start "newFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:290:1: newFunction returns [Operator exp] : NEW label shape ;
    public final Operator newFunction() throws RecognitionException {
        Operator exp = null;


        XMorphParser.label_return label30 =null;

        Operator shape31 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:290:36: ( NEW label shape )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:291:4: NEW label shape
            {
            match(input,NEW,FOLLOW_NEW_in_newFunction800); 

            pushFollow(FOLLOW_label_in_newFunction802);
            label30=label();

            state._fsp--;


            pushFollow(FOLLOW_shape_in_newFunction804);
            shape31=shape();

            state._fsp--;



                  exp = new NewTag((label30!=null?label30.s:null), shape31);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "newFunction"



    // $ANTLR start "mutateFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:296:1: mutateFunction returns [Operator exp] : MUTATE shape ;
    public final Operator mutateFunction() throws RecognitionException {
        Operator exp = null;


        Operator shape32 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:296:39: ( MUTATE shape )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:297:4: MUTATE shape
            {
            match(input,MUTATE,FOLLOW_MUTATE_in_mutateFunction834); 

            pushFollow(FOLLOW_shape_in_mutateFunction836);
            shape32=shape();

            state._fsp--;



                  exp = new Mutate(shape32);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "mutateFunction"



    // $ANTLR start "translateFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:307:1: translateFunction returns [Operator exp] : TRANSLATE dictionary[dict] ;
    public final Operator translateFunction() throws RecognitionException {
        Operator exp = null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:307:42: ( TRANSLATE dictionary[dict] )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:308:4: TRANSLATE dictionary[dict]
            {
            match(input,TRANSLATE,FOLLOW_TRANSLATE_in_translateFunction863); 

            Dictionary dict = new Dictionary();

            pushFollow(FOLLOW_dictionary_in_translateFunction873);
            dictionary(dict);

            state._fsp--;



                  exp = new Translate(dict);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "translateFunction"



    // $ANTLR start "orderByFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:314:1: orderByFunction returns [Operator exp] : ORDER f3= shape BY f4= shape ;
    public final Operator orderByFunction() throws RecognitionException {
        Operator exp = null;


        Operator f3 =null;

        Operator f4 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:314:40: ( ORDER f3= shape BY f4= shape )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:315:4: ORDER f3= shape BY f4= shape
            {
            match(input,ORDER,FOLLOW_ORDER_in_orderByFunction894); 

            pushFollow(FOLLOW_shape_in_orderByFunction898);
            f3=shape();

            state._fsp--;


            match(input,BY,FOLLOW_BY_in_orderByFunction900); 

            pushFollow(FOLLOW_shape_in_orderByFunction904);
            f4=shape();

            state._fsp--;



                      exp = new OrderBy(f3, f4);
               

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "orderByFunction"



    // $ANTLR start "groupFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:320:1: groupFunction returns [Operator exp] : GROUP f3= shape ( BY f4= shape |) ;
    public final Operator groupFunction() throws RecognitionException {
        Operator exp = null;


        Operator f3 =null;

        Operator f4 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:320:38: ( GROUP f3= shape ( BY f4= shape |) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:321:4: GROUP f3= shape ( BY f4= shape |)
            {
            match(input,GROUP,FOLLOW_GROUP_in_groupFunction930); 

            pushFollow(FOLLOW_shape_in_groupFunction934);
            f3=shape();

            state._fsp--;


            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:322:6: ( BY f4= shape |)
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==BY) ) {
                alt5=1;
            }
            else if ( (LA5_0==RPAREN) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:323:8: BY f4= shape
                    {
                    match(input,BY,FOLLOW_BY_in_groupFunction951); 

                    pushFollow(FOLLOW_shape_in_groupFunction955);
                    f4=shape();

                    state._fsp--;


                    exp = new DynamicGroup(f3, f4);

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:326:10: 
                    {
                    exp = new Group(f3);

                    }
                    break;

            }


            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "groupFunction"



    // $ANTLR start "restrictFunction"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:330:1: restrictFunction returns [Operator exp] : RESTRICT shape ;
    public final Operator restrictFunction() throws RecognitionException {
        Operator exp = null;


        Operator shape33 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:330:41: ( RESTRICT shape )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:331:4: RESTRICT shape
            {
            match(input,RESTRICT,FOLLOW_RESTRICT_in_restrictFunction1017); 

            pushFollow(FOLLOW_shape_in_restrictFunction1019);
            shape33=shape();

            state._fsp--;



                    exp = new Hide(shape33);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "restrictFunction"



    // $ANTLR start "shape"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:336:1: shape returns [Operator exp] : parent ( '[' children[$parent.exp] ']' )? ;
    public final Operator shape() throws RecognitionException {
        Operator exp = null;


        Operator parent34 =null;

        Operator children35 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:336:30: ( parent ( '[' children[$parent.exp] ']' )? )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:337:3: parent ( '[' children[$parent.exp] ']' )?
            {
            pushFollow(FOLLOW_parent_in_shape1042);
            parent34=parent();

            state._fsp--;


            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:337:10: ( '[' children[$parent.exp] ']' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==LBRACKET) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:337:11: '[' children[$parent.exp] ']'
                    {
                    match(input,LBRACKET,FOLLOW_LBRACKET_in_shape1045); 

                    pushFollow(FOLLOW_children_in_shape1053);
                    children35=children(parent34);

                    state._fsp--;


                    match(input,RBRACKET,FOLLOW_RBRACKET_in_shape1063); 

                    }
                    break;

            }



                    if (children35 == null) {exp = parent34;}
                    else {exp = children35;}
                    

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "shape"



    // $ANTLR start "dynamicGrouping"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:345:1: dynamicGrouping[Operator in] returns [Operator exp] : GROUP '(' shape ')' ;
    public final Operator dynamicGrouping(Operator in) throws RecognitionException {
        Operator exp = null;


        Operator shape36 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:345:52: ( GROUP '(' shape ')' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:346:3: GROUP '(' shape ')'
            {
            match(input,GROUP,FOLLOW_GROUP_in_dynamicGrouping1086); 

            match(input,LPAREN,FOLLOW_LPAREN_in_dynamicGrouping1088); 

            pushFollow(FOLLOW_shape_in_dynamicGrouping1090);
            shape36=shape();

            state._fsp--;


            match(input,RPAREN,FOLLOW_RPAREN_in_dynamicGrouping1092); 


                exp = new DynamicGroup(in, shape36);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "dynamicGrouping"



    // $ANTLR start "parent"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:351:1: parent returns [Operator exp] : labelAndModifiers ;
    public final Operator parent() throws RecognitionException {
        Operator exp = null;


        Operator labelAndModifiers37 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:351:30: ( labelAndModifiers )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:352:3: labelAndModifiers
            {
            pushFollow(FOLLOW_labelAndModifiers_in_parent1110);
            labelAndModifiers37=labelAndModifiers();

            state._fsp--;



                exp = labelAndModifiers37;
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "parent"



    // $ANTLR start "labelAndModifiers"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:357:1: labelAndModifiers returns [Operator exp] : ( LPAREN secondaryFunction RPAREN | ( label (m= modifier[new Type($label.s)] | ',' NEW ) ) );
    public final Operator labelAndModifiers() throws RecognitionException {
        Operator exp = null;


        Operator m =null;

        Operator secondaryFunction38 =null;

        XMorphParser.label_return label39 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:357:42: ( LPAREN secondaryFunction RPAREN | ( label (m= modifier[new Type($label.s)] | ',' NEW ) ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==LPAREN) ) {
                alt8=1;
            }
            else if ( (LA8_0==ID) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }
            switch (alt8) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:358:3: LPAREN secondaryFunction RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_labelAndModifiers1130); 

                    pushFollow(FOLLOW_secondaryFunction_in_labelAndModifiers1132);
                    secondaryFunction38=secondaryFunction();

                    state._fsp--;


                    match(input,RPAREN,FOLLOW_RPAREN_in_labelAndModifiers1134); 


                      	exp = secondaryFunction38;
                      

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:362:3: ( label (m= modifier[new Type($label.s)] | ',' NEW ) )
                    {
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:362:3: ( label (m= modifier[new Type($label.s)] | ',' NEW ) )
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:362:4: label (m= modifier[new Type($label.s)] | ',' NEW )
                    {
                    pushFollow(FOLLOW_label_in_labelAndModifiers1145);
                    label39=label();

                    state._fsp--;


                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:362:10: (m= modifier[new Type($label.s)] | ',' NEW )
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==54) ) {
                        int LA7_1 = input.LA(2);

                        if ( (LA7_1==NEW) ) {
                            alt7=2;
                        }
                        else if ( (LA7_1==CLONE||LA7_1==GROUP||LA7_1==HIDE||LA7_1==OPTIONAL||LA7_1==WHERE) ) {
                            alt7=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 1, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA7_0==EOF||LA7_0==BY||LA7_0==CHILDREN||LA7_0==DESCENDANTS||LA7_0==DOUBLEWILDCARD||LA7_0==ID||LA7_0==LBRACKET||LA7_0==LPAREN||(LA7_0 >= RBRACE && LA7_0 <= RBRACKET)||LA7_0==RPAREN||(LA7_0 >= WILDCARD && LA7_0 <= WITH)) ) {
                        alt7=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 0, input);

                        throw nvae;

                    }
                    switch (alt7) {
                        case 1 :
                            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:362:11: m= modifier[new Type($label.s)]
                            {
                            pushFollow(FOLLOW_modifier_in_labelAndModifiers1150);
                            m=modifier(new Type((label39!=null?label39.s:null)));

                            state._fsp--;


                             
                                  //System.out.println("Type is " + (label39!=null?label39.s:null));
                                  exp = m; 
                                  

                            }
                            break;
                        case 2 :
                            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:367:6: ',' NEW
                            {
                            match(input,54,FOLLOW_54_in_labelAndModifiers1168); 

                            match(input,NEW,FOLLOW_NEW_in_labelAndModifiers1171); 

                             
                                       exp = new NewLabel((label39!=null?label39.s:null)); 
                                      

                            }
                            break;

                    }


                    }


                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "labelAndModifiers"



    // $ANTLR start "children"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:374:1: children[Operator parent] returns [Operator exp] : ( descendantFunction | childrenFunction | ( shape m= children[$parent] ) |) ;
    public final Operator children(Operator parent) throws RecognitionException {
        Operator exp = null;


        Operator m =null;

        Operator shape40 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:374:50: ( ( descendantFunction | childrenFunction | ( shape m= children[$parent] ) |) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:375:3: ( descendantFunction | childrenFunction | ( shape m= children[$parent] ) |)
            {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:375:3: ( descendantFunction | childrenFunction | ( shape m= children[$parent] ) |)
            int alt9=4;
            switch ( input.LA(1) ) {
            case DESCENDANTS:
            case DOUBLEWILDCARD:
                {
                alt9=1;
                }
                break;
            case CHILDREN:
            case WILDCARD:
                {
                alt9=2;
                }
                break;
            case ID:
            case LPAREN:
                {
                alt9=3;
                }
                break;
            case RBRACKET:
                {
                alt9=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }

            switch (alt9) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:375:5: descendantFunction
                    {
                    pushFollow(FOLLOW_descendantFunction_in_children1257);
                    descendantFunction();

                    state._fsp--;


                    exp = new Descendants(parent); 

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:376:7: childrenFunction
                    {
                    pushFollow(FOLLOW_childrenFunction_in_children1267);
                    childrenFunction();

                    state._fsp--;


                    exp = new Children(parent); 

                    }
                    break;
                case 3 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:377:7: ( shape m= children[$parent] )
                    {
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:377:7: ( shape m= children[$parent] )
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:378:3: shape m= children[$parent]
                    {
                    pushFollow(FOLLOW_shape_in_children1281);
                    shape40=shape();

                    state._fsp--;



                       // Java/antlr is not smart enough to do polymorphic call, 
                       // so have to hard code it.
                       try {
                         //System.out.println("Children");
                         //System.out.println("parent " + parent.getClass());
                         //System.out.println("shape " + shape40.getClass());
                         if (parent.getClass().equals(Class.forName("xmorph.edu.usu.algebra.NewLabel"))) {
                           parent = new WrapWithNewLabel((NewLabel)parent, shape40);
                           }
                         else {
                           if (shape40 == null) {
                             exp = parent;
                             }
                           else {
                             if (shape40.getClass().equals(Class.forName("xmorph.edu.usu.algebra.NewLabel"))) {
                               parent = new JoinNewLabel(parent, (NewLabel)shape40);
                               }
                             else {
                             //System.out.println("New LCAJoin");
                               parent = new LCAJoin(parent, shape40);
                               }
                             }
                           }
                       } catch (ClassNotFoundException e) {
                         System.out.println("No class found in XMorph.g");
                         System.exit(-1);
                         };
                       //System.out.println(" p " + parent);
                       

                    pushFollow(FOLLOW_children_in_children1289);
                    m=children(parent);

                    state._fsp--;



                          if (m != null) {
                            //System.out.println("Not empty children" + m);
                            exp = m;
                            }
                          else {
                            //System.out.println("Empty children");
                            exp = parent;
                            }
                        

                    }


                    }
                    break;
                case 4 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:419:5: 
                    {

                          exp = parent;
                        

                    }
                    break;

            }


            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "children"



    // $ANTLR start "child"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:425:1: child returns [Operator exp] : labelAndModifiers ;
    public final Operator child() throws RecognitionException {
        Operator exp = null;


        Operator labelAndModifiers41 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:425:29: ( labelAndModifiers )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:426:3: labelAndModifiers
            {
            pushFollow(FOLLOW_labelAndModifiers_in_child1326);
            labelAndModifiers41=labelAndModifiers();

            state._fsp--;



                  exp = labelAndModifiers41;
                  //System.out.println("doing repr" + exp);
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "child"



    // $ANTLR start "modifier"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:432:1: modifier[Operator in] returns [Operator exp] : ( ',' ( HIDE | CLONE | WHERE condition | OPTIONAL | dynamicGrouping[$in] ) m= modifier[$in] |);
    public final Operator modifier(Operator in) throws RecognitionException {
        Operator exp = null;


        Operator m =null;

        Operator condition42 =null;

        Operator dynamicGrouping43 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:432:46: ( ',' ( HIDE | CLONE | WHERE condition | OPTIONAL | dynamicGrouping[$in] ) m= modifier[$in] |)
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==54) ) {
                alt11=1;
            }
            else if ( (LA11_0==EOF||LA11_0==BY||LA11_0==CHILDREN||LA11_0==DESCENDANTS||LA11_0==DOUBLEWILDCARD||LA11_0==ID||LA11_0==LBRACKET||LA11_0==LPAREN||(LA11_0 >= RBRACE && LA11_0 <= RBRACKET)||LA11_0==RPAREN||(LA11_0 >= WILDCARD && LA11_0 <= WITH)) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }
            switch (alt11) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:433:3: ',' ( HIDE | CLONE | WHERE condition | OPTIONAL | dynamicGrouping[$in] ) m= modifier[$in]
                    {
                    match(input,54,FOLLOW_54_in_modifier1346); 

                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:433:7: ( HIDE | CLONE | WHERE condition | OPTIONAL | dynamicGrouping[$in] )
                    int alt10=5;
                    switch ( input.LA(1) ) {
                    case HIDE:
                        {
                        alt10=1;
                        }
                        break;
                    case CLONE:
                        {
                        alt10=2;
                        }
                        break;
                    case WHERE:
                        {
                        alt10=3;
                        }
                        break;
                    case OPTIONAL:
                        {
                        alt10=4;
                        }
                        break;
                    case GROUP:
                        {
                        alt10=5;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 0, input);

                        throw nvae;

                    }

                    switch (alt10) {
                        case 1 :
                            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:433:9: HIDE
                            {
                            match(input,HIDE,FOLLOW_HIDE_in_modifier1350); 


                                        in = new Hide(in);
                                      

                            }
                            break;
                        case 2 :
                            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:436:11: CLONE
                            {
                            match(input,CLONE,FOLLOW_CLONE_in_modifier1364); 


                                        in = new Clone(in);
                                      

                            }
                            break;
                        case 3 :
                            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:439:11: WHERE condition
                            {
                            match(input,WHERE,FOLLOW_WHERE_in_modifier1378); 

                            pushFollow(FOLLOW_condition_in_modifier1380);
                            condition42=condition();

                            state._fsp--;



                                        in = new Where(in, condition42);
                                      

                            }
                            break;
                        case 4 :
                            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:447:11: OPTIONAL
                            {
                            match(input,OPTIONAL,FOLLOW_OPTIONAL_in_modifier1406); 


                                        in = new Optional(in);
                                      

                            }
                            break;
                        case 5 :
                            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:450:11: dynamicGrouping[$in]
                            {
                            pushFollow(FOLLOW_dynamicGrouping_in_modifier1420);
                            dynamicGrouping43=dynamicGrouping(in);

                            state._fsp--;



                                        in = dynamicGrouping43;
                                      

                            }
                            break;

                    }


                    pushFollow(FOLLOW_modifier_in_modifier1435);
                    m=modifier(in);

                    state._fsp--;



                              exp = m;
                            

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:456:5: 
                    {

                          exp = in;
                        

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "modifier"


    public static class label_return extends ParserRuleReturnScope {
        public String s;
    };


    // $ANTLR start "label"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:461:1: label returns [String s] : ID t= labelContinues[$ID.text] ;
    public final XMorphParser.label_return label() throws RecognitionException {
        XMorphParser.label_return retval = new XMorphParser.label_return();
        retval.start = input.LT(1);


        Token ID44=null;
        String t =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:461:26: ( ID t= labelContinues[$ID.text] )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:462:3: ID t= labelContinues[$ID.text]
            {
            ID44=(Token)match(input,ID,FOLLOW_ID_in_label1461); 

            pushFollow(FOLLOW_labelContinues_in_label1465);
            t=labelContinues((ID44!=null?ID44.getText():null));

            state._fsp--;


             
                  retval.s = t;
                

            }

            retval.stop = input.LT(-1);


        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "label"



    // $ANTLR start "labelContinues"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:467:1: labelContinues[String s] returns [String s1] : ( '.' ID t= labelContinues[\".\" + $ID.text] |);
    public final String labelContinues(String s) throws RecognitionException {
        String s1 = null;


        Token ID45=null;
        String t =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:467:46: ( '.' ID t= labelContinues[\".\" + $ID.text] |)
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==55) ) {
                alt12=1;
            }
            else if ( (LA12_0==EOF||LA12_0==ARROW||LA12_0==BY||LA12_0==CHILDREN||LA12_0==DESCENDANTS||LA12_0==DOUBLEWILDCARD||LA12_0==ID||LA12_0==LBRACKET||LA12_0==LPAREN||(LA12_0 >= RBRACE && LA12_0 <= RBRACKET)||LA12_0==RPAREN||(LA12_0 >= WILDCARD && LA12_0 <= WITH)||LA12_0==54) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;

            }
            switch (alt12) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:468:3: '.' ID t= labelContinues[\".\" + $ID.text]
                    {
                    match(input,55,FOLLOW_55_in_labelContinues1488); 

                    ID45=(Token)match(input,ID,FOLLOW_ID_in_labelContinues1490); 

                    pushFollow(FOLLOW_labelContinues_in_labelContinues1494);
                    t=labelContinues("." + (ID45!=null?ID45.getText():null));

                    state._fsp--;



                          s1 = s + t;
                        

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:471:5: 
                    {

                          s1 = s;
                        

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return s1;
    }
    // $ANTLR end "labelContinues"



    // $ANTLR start "data"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:477:1: data returns [Operator exp] : ( STRING | '{' topLevelFunction '}' | ID );
    public final Operator data() throws RecognitionException {
        Operator exp = null;


        Token STRING46=null;
        Token ID47=null;

        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:477:29: ( STRING | '{' topLevelFunction '}' | ID )
            int alt13=3;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt13=1;
                }
                break;
            case LBRACE:
                {
                alt13=2;
                }
                break;
            case ID:
                {
                alt13=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }

            switch (alt13) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:478:3: STRING
                    {
                    STRING46=(Token)match(input,STRING,FOLLOW_STRING_in_data1524); 


                          exp = new Data((STRING46!=null?STRING46.getText():null));
                        

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:481:7: '{' topLevelFunction '}'
                    {
                    match(input,LBRACE,FOLLOW_LBRACE_in_data1536); 

                    pushFollow(FOLLOW_topLevelFunction_in_data1538);
                    topLevelFunction();

                    state._fsp--;


                    match(input,RBRACE,FOLLOW_RBRACE_in_data1540); 

                    }
                    break;
                case 3 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:482:7: ID
                    {
                    ID47=(Token)match(input,ID,FOLLOW_ID_in_data1548); 


                          exp = new Data((ID47!=null?ID47.getText():null));
                        

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "data"



    // $ANTLR start "dictionary"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:487:1: dictionary[Dictionary t] : label '->' ID ( dictionary[t] )? ;
    public final void dictionary(Dictionary t) throws RecognitionException {
        Token ID49=null;
        XMorphParser.label_return label48 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:487:25: ( label '->' ID ( dictionary[t] )? )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:488:3: label '->' ID ( dictionary[t] )?
            {
            pushFollow(FOLLOW_label_in_dictionary1563);
            label48=label();

            state._fsp--;


            match(input,ARROW,FOLLOW_ARROW_in_dictionary1565); 

            ID49=(Token)match(input,ID,FOLLOW_ID_in_dictionary1567); 


                t.put((label48!=null?input.toString(label48.start,label48.stop):null), (ID49!=null?ID49.getText():null));
                

            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:491:3: ( dictionary[t] )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ID) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:491:5: dictionary[t]
                    {
                    pushFollow(FOLLOW_dictionary_in_dictionary1576);
                    dictionary(t);

                    state._fsp--;


                    }
                    break;

            }


            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "dictionary"



    // $ANTLR start "condition"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:494:1: condition returns [Operator exp] : VALUE w= whereConditionContinues ;
    public final Operator condition() throws RecognitionException {
        Operator exp = null;


        Operator w =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:494:33: ( VALUE w= whereConditionContinues )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:494:35: VALUE w= whereConditionContinues
            {
            match(input,VALUE,FOLLOW_VALUE_in_condition1594); 

            pushFollow(FOLLOW_whereConditionContinues_in_condition1600);
            w=whereConditionContinues();

            state._fsp--;


            exp = w;

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "condition"



    // $ANTLR start "whereConditionContinues"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:496:1: whereConditionContinues returns [Operator exp] : ( 'not' a= whereConditionStillContinues b= whereClauseContinues[new Not($a.exp)] |a= whereConditionStillContinues b= whereClauseContinues[$a.exp] );
    public final Operator whereConditionContinues() throws RecognitionException {
        Operator exp = null;


        Comparator a =null;

        Operator b =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:496:46: ( 'not' a= whereConditionStillContinues b= whereClauseContinues[new Not($a.exp)] |a= whereConditionStillContinues b= whereClauseContinues[$a.exp] )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==57) ) {
                alt15=1;
            }
            else if ( ((LA15_0 >= EQUALS && LA15_0 <= GE)||LA15_0==GT||LA15_0==LE||LA15_0==LT) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:497:3: 'not' a= whereConditionStillContinues b= whereClauseContinues[new Not($a.exp)]
                    {
                    match(input,57,FOLLOW_57_in_whereConditionContinues1614); 

                    pushFollow(FOLLOW_whereConditionStillContinues_in_whereConditionContinues1620);
                    a=whereConditionStillContinues();

                    state._fsp--;


                    pushFollow(FOLLOW_whereClauseContinues_in_whereConditionContinues1628);
                    b=whereClauseContinues(new Not(a));

                    state._fsp--;


                     
                          if (b == null) {
                            exp = new Not(a);
                          } else {
                            exp = b;
                          }
                        

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:505:5: a= whereConditionStillContinues b= whereClauseContinues[$a.exp]
                    {
                    pushFollow(FOLLOW_whereConditionStillContinues_in_whereConditionContinues1642);
                    a=whereConditionStillContinues();

                    state._fsp--;


                    pushFollow(FOLLOW_whereClauseContinues_in_whereConditionContinues1648);
                    b=whereClauseContinues(a);

                    state._fsp--;


                     
                          if (b == null) {
                            exp = a;
                          } else {
                            exp = b;
                          }
                        

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "whereConditionContinues"



    // $ANTLR start "whereConditionStillContinues"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:514:1: whereConditionStillContinues returns [Comparator exp] : ( comparator STRING | comparator i= integer );
    public final Comparator whereConditionStillContinues() throws RecognitionException {
        Comparator exp = null;


        Token STRING50=null;
        int i =0;

        int comparator51 =0;

        int comparator52 =0;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:514:54: ( comparator STRING | comparator i= integer )
            int alt16=2;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                int LA16_1 = input.LA(2);

                if ( (LA16_1==STRING) ) {
                    alt16=1;
                }
                else if ( (LA16_1==INT) ) {
                    alt16=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;

                }
                }
                break;
            case LE:
                {
                int LA16_2 = input.LA(2);

                if ( (LA16_2==STRING) ) {
                    alt16=1;
                }
                else if ( (LA16_2==INT) ) {
                    alt16=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 2, input);

                    throw nvae;

                }
                }
                break;
            case GE:
                {
                int LA16_3 = input.LA(2);

                if ( (LA16_3==STRING) ) {
                    alt16=1;
                }
                else if ( (LA16_3==INT) ) {
                    alt16=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 3, input);

                    throw nvae;

                }
                }
                break;
            case GT:
                {
                int LA16_4 = input.LA(2);

                if ( (LA16_4==STRING) ) {
                    alt16=1;
                }
                else if ( (LA16_4==INT) ) {
                    alt16=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 4, input);

                    throw nvae;

                }
                }
                break;
            case LT:
                {
                int LA16_5 = input.LA(2);

                if ( (LA16_5==STRING) ) {
                    alt16=1;
                }
                else if ( (LA16_5==INT) ) {
                    alt16=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 5, input);

                    throw nvae;

                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }

            switch (alt16) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:515:3: comparator STRING
                    {
                    pushFollow(FOLLOW_comparator_in_whereConditionStillContinues1665);
                    comparator51=comparator();

                    state._fsp--;


                    STRING50=(Token)match(input,STRING,FOLLOW_STRING_in_whereConditionStillContinues1668); 


                         // Take off the " characters
                         String s = (STRING50!=null?STRING50.getText():null);
                         String stripped = s.substring(1, s.length() - 1);  
                         exp = new Comparator(comparator51, stripped); 
                         

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:521:5: comparator i= integer
                    {
                    pushFollow(FOLLOW_comparator_in_whereConditionStillContinues1676);
                    comparator52=comparator();

                    state._fsp--;


                    pushFollow(FOLLOW_integer_in_whereConditionStillContinues1680);
                    i=integer();

                    state._fsp--;



                         exp = new Comparator(comparator52, i);
                         

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "whereConditionStillContinues"



    // $ANTLR start "whereClauseContinues"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:526:1: whereClauseContinues[Operator exp1] returns [Operator exp] : ( 'and' condition | 'or' condition |);
    public final Operator whereClauseContinues(Operator exp1) throws RecognitionException {
        Operator exp = null;


        Operator condition53 =null;

        Operator condition54 =null;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:526:60: ( 'and' condition | 'or' condition |)
            int alt17=3;
            switch ( input.LA(1) ) {
            case 56:
                {
                alt17=1;
                }
                break;
            case 58:
                {
                alt17=2;
                }
                break;
            case EOF:
            case BY:
            case CHILDREN:
            case DESCENDANTS:
            case DOUBLEWILDCARD:
            case ID:
            case LBRACKET:
            case LPAREN:
            case RBRACE:
            case RBRACKET:
            case RPAREN:
            case WILDCARD:
            case WITH:
            case 54:
                {
                alt17=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }

            switch (alt17) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:527:3: 'and' condition
                    {
                    match(input,56,FOLLOW_56_in_whereClauseContinues1700); 

                    pushFollow(FOLLOW_condition_in_whereClauseContinues1702);
                    condition53=condition();

                    state._fsp--;



                        exp = new And(exp1, condition53);
                        

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:530:4: 'or' condition
                    {
                    match(input,58,FOLLOW_58_in_whereClauseContinues1709); 

                    pushFollow(FOLLOW_condition_in_whereClauseContinues1711);
                    condition54=condition();

                    state._fsp--;



                        exp = new Or(exp1, condition54);
                        

                    }
                    break;
                case 3 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:534:3: 
                    {
                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return exp;
    }
    // $ANTLR end "whereClauseContinues"



    // $ANTLR start "integer"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:536:1: integer returns [ int value] : INT ;
    public final int integer() throws RecognitionException {
        int value = 0;


        Token INT55=null;

        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:536:29: ( INT )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:537:3: INT
            {
            INT55=(Token)match(input,INT,FOLLOW_INT_in_integer1736); 


                value = Integer.parseInt((INT55!=null?INT55.getText():null));
                

            }

        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "integer"



    // $ANTLR start "comparator"
    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:542:1: comparator returns [int value] : ( EQUALS | LE | GE | GT | LT );
    public final int comparator() throws RecognitionException {
        int value = 0;


        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:542:31: ( EQUALS | LE | GE | GT | LT )
            int alt18=5;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt18=1;
                }
                break;
            case LE:
                {
                alt18=2;
                }
                break;
            case GE:
                {
                alt18=3;
                }
                break;
            case GT:
                {
                alt18=4;
                }
                break;
            case LT:
                {
                alt18=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;

            }

            switch (alt18) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:543:3: EQUALS
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_comparator1756); 

                    value = Where.EQ;

                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:544:5: LE
                    {
                    match(input,LE,FOLLOW_LE_in_comparator1765); 

                    value = Where.LE;

                    }
                    break;
                case 3 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:545:5: GE
                    {
                    match(input,GE,FOLLOW_GE_in_comparator1774); 

                    value =  Where.GE;

                    }
                    break;
                case 4 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:546:5: GT
                    {
                    match(input,GT,FOLLOW_GT_in_comparator1782); 

                    value = Where.GT;

                    }
                    break;
                case 5 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:547:5: LT
                    {
                    match(input,LT,FOLLOW_LT_in_comparator1791); 

                    value = Where.LT;

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
            throw e;
          }
          
        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "comparator"

    // Delegated rules


 

    public static final BitSet FOLLOW_topLevelFunction_in_program72 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_program80 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeCheckFunction_in_topLevelFunction101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nonComposeTopLevelFunction_in_topLevelFunction115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_composeFunction_in_topLevelFunction129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeFillFunction_in_typeCheckFunction152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castWideningFunction_in_typeCheckFunction166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castNarrowingFunction_in_typeCheckFunction184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castFunction_in_typeCheckFunction198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_translateFunction_in_nonComposeTopLevelFunction226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_morphFunction_in_nonComposeTopLevelFunction240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mutateFunction_in_nonComposeTopLevelFunction254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_newFunction_in_secondaryFunction282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_translateFunction_in_secondaryFunction296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_groupFunction_in_secondaryFunction310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_restrictFunction_in_secondaryFunction324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cloneFunction_in_secondaryFunction338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_asElementFunction_in_secondaryFunction352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orderByFunction_in_secondaryFunction366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dropFunction_in_secondaryFunction380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_asAttributeFunction_in_secondaryFunction394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMPOSE_in_composeFunction427 = new BitSet(new long[]{0x0000800600000000L});
    public static final BitSet FOLLOW_nonComposeTopLevelFunction_in_composeFunction431 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_WITH_in_composeFunction434 = new BitSet(new long[]{0x0001800600002700L});
    public static final BitSet FOLLOW_topLevelFunction_in_composeFunction438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CASTNARROWING_in_castNarrowingFunction462 = new BitSet(new long[]{0x0000800600000000L});
    public static final BitSet FOLLOW_nonComposeTopLevelFunction_in_castNarrowingFunction464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CASTWIDENING_in_castWideningFunction492 = new BitSet(new long[]{0x0000800600000000L});
    public static final BitSet FOLLOW_nonComposeTopLevelFunction_in_castWideningFunction494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CAST_in_castFunction521 = new BitSet(new long[]{0x0000800600000000L});
    public static final BitSet FOLLOW_nonComposeTopLevelFunction_in_castFunction523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TYPEFILL_in_typeFillFunction550 = new BitSet(new long[]{0x0000800600000000L});
    public static final BitSet FOLLOW_nonComposeTopLevelFunction_in_typeFillFunction552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MORPH_in_morphFunction634 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_morphFunction636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLONE_in_cloneFunction665 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_cloneFunction667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASATTRIBUTE_in_asAttributeFunction715 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_asAttributeFunction717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DROP_in_dropFunction742 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_dropFunction744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASELEMENT_in_asElementFunction773 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_asElementFunction775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEW_in_newFunction800 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_label_in_newFunction802 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_newFunction804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MUTATE_in_mutateFunction834 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_mutateFunction836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRANSLATE_in_translateFunction863 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_dictionary_in_translateFunction873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ORDER_in_orderByFunction894 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_orderByFunction898 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_BY_in_orderByFunction900 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_orderByFunction904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GROUP_in_groupFunction930 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_groupFunction934 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_BY_in_groupFunction951 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_groupFunction955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RESTRICT_in_restrictFunction1017 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_restrictFunction1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parent_in_shape1042 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_LBRACKET_in_shape1045 = new BitSet(new long[]{0x0008040082028800L});
    public static final BitSet FOLLOW_children_in_shape1053 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_shape1063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GROUP_in_dynamicGrouping1086 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LPAREN_in_dynamicGrouping1088 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_shape_in_dynamicGrouping1090 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_dynamicGrouping1092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_labelAndModifiers_in_parent1110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_labelAndModifiers1130 = new BitSet(new long[]{0x0000909000441060L});
    public static final BitSet FOLLOW_secondaryFunction_in_labelAndModifiers1132 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_labelAndModifiers1134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_label_in_labelAndModifiers1145 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_modifier_in_labelAndModifiers1150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_labelAndModifiers1168 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_NEW_in_labelAndModifiers1171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_descendantFunction_in_children1257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_childrenFunction_in_children1267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_shape_in_children1281 = new BitSet(new long[]{0x0008000082028800L});
    public static final BitSet FOLLOW_children_in_children1289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_labelAndModifiers_in_child1326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_modifier1346 = new BitSet(new long[]{0x0004004001401000L});
    public static final BitSet FOLLOW_HIDE_in_modifier1350 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_CLONE_in_modifier1364 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_WHERE_in_modifier1378 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_condition_in_modifier1380 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_OPTIONAL_in_modifier1406 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_dynamicGrouping_in_modifier1420 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_modifier_in_modifier1435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_label1461 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_labelContinues_in_label1465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_labelContinues1488 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_labelContinues1490 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_labelContinues_in_labelContinues1494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_data1524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_data1536 = new BitSet(new long[]{0x0001800600002700L});
    public static final BitSet FOLLOW_topLevelFunction_in_data1538 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RBRACE_in_data1540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_data1548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_label_in_dictionary1563 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ARROW_in_dictionary1565 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_dictionary1567 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_dictionary_in_dictionary1576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VALUE_in_condition1594 = new BitSet(new long[]{0x0200000120B00000L});
    public static final BitSet FOLLOW_whereConditionContinues_in_condition1600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_whereConditionContinues1614 = new BitSet(new long[]{0x0000000120B00000L});
    public static final BitSet FOLLOW_whereConditionStillContinues_in_whereConditionContinues1620 = new BitSet(new long[]{0x0500000000000000L});
    public static final BitSet FOLLOW_whereClauseContinues_in_whereConditionContinues1628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whereConditionStillContinues_in_whereConditionContinues1642 = new BitSet(new long[]{0x0500000000000000L});
    public static final BitSet FOLLOW_whereClauseContinues_in_whereConditionContinues1648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comparator_in_whereConditionStillContinues1665 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_STRING_in_whereConditionStillContinues1668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comparator_in_whereConditionStillContinues1676 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_integer_in_whereConditionStillContinues1680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_whereClauseContinues1700 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_condition_in_whereClauseContinues1702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_whereClauseContinues1709 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_condition_in_whereClauseContinues1711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_integer1736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQUALS_in_comparator1756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LE_in_comparator1765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GE_in_comparator1774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GT_in_comparator1782 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LT_in_comparator1791 = new BitSet(new long[]{0x0000000000000002L});

}