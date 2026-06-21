// $ANTLR 3.4 C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g 2013-08-12 09:38:22

package xmorph.edu.usu.grammar;
import xmorph.edu.usu.algebra.*;
import java.lang.UnsupportedOperationException;
  

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class XMorphLexer extends Lexer {
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

        
          public void displayRecognitionError(String[] tokenNames,
                                            RecognitionException e) {
       
            //errorMessage = buildErrorMessage(e);
            String hdr = getErrorHeader(e);
            String msg = getErrorMessage(e, XMorphParser.tokenNames);
            // Now do something with hdr and msg...
            XMorphParser.errorMessage = "^Lexical Error in query at " + hdr + ": " + msg + "\n"; 
            
        }


    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public XMorphLexer() {} 
    public XMorphLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public XMorphLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g"; }

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:21:7: ( ',' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:21:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:22:7: ( '.' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:22:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:23:7: ( 'and' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:23:9: 'and'
            {
            match("and"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:24:7: ( 'not' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:24:9: 'not'
            {
            match("not"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:25:7: ( 'or' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:25:9: 'or'
            {
            match("or"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "MORPH"
    public final void mMORPH() throws RecognitionException {
        try {
            int _type = MORPH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:550:8: ( ( 'm' | 'M' ) ( 'o' | 'O' ) ( 'r' | 'R' ) ( 'p' | 'P' ) ( 'h' | 'H' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:550:10: ( 'm' | 'M' ) ( 'o' | 'O' ) ( 'r' | 'R' ) ( 'p' | 'P' ) ( 'h' | 'H' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MORPH"

    // $ANTLR start "ORDER"
    public final void mORDER() throws RecognitionException {
        try {
            int _type = ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:551:8: ( ( 'o' | 'O' ) ( 'r' | 'R' ) ( 'd' | 'D' ) ( 'e' | 'E' ) ( 'r' | 'R' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:551:10: ( 'o' | 'O' ) ( 'r' | 'R' ) ( 'd' | 'D' ) ( 'e' | 'E' ) ( 'r' | 'R' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ORDER"

    // $ANTLR start "MUTATE"
    public final void mMUTATE() throws RecognitionException {
        try {
            int _type = MUTATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:552:9: ( ( 'm' | 'M' ) ( 'u' | 'U' ) ( 't' | 'T' ) ( 'a' | 'A' ) ( 't' | 'T' ) ( 'e' | 'E' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:552:11: ( 'm' | 'M' ) ( 'u' | 'U' ) ( 't' | 'T' ) ( 'a' | 'A' ) ( 't' | 'T' ) ( 'e' | 'E' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MUTATE"

    // $ANTLR start "COMPOSE"
    public final void mCOMPOSE() throws RecognitionException {
        try {
            int _type = COMPOSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:553:9: ( ( 'c' | 'C' ) ( 'o' | 'O' ) ( 'm' | 'M' ) ( 'p' | 'P' ) ( 'o' | 'O' ) ( 's' | 'S' ) ( 'e' | 'E' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:553:11: ( 'c' | 'C' ) ( 'o' | 'O' ) ( 'm' | 'M' ) ( 'p' | 'P' ) ( 'o' | 'O' ) ( 's' | 'S' ) ( 'e' | 'E' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPOSE"

    // $ANTLR start "RESTRICT"
    public final void mRESTRICT() throws RecognitionException {
        try {
            int _type = RESTRICT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:554:10: ( ( 'r' | 'R' ) ( 'e' | 'E' ) ( 's' | 'S' ) ( 't' | 'T' ) ( 'r' | 'R' ) ( 'i' | 'I' ) ( 'c' | 'C' ) ( 't' | 'T' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:554:12: ( 'r' | 'R' ) ( 'e' | 'E' ) ( 's' | 'S' ) ( 't' | 'T' ) ( 'r' | 'R' ) ( 'i' | 'I' ) ( 'c' | 'C' ) ( 't' | 'T' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RESTRICT"

    // $ANTLR start "CAST"
    public final void mCAST() throws RecognitionException {
        try {
            int _type = CAST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:555:7: ( ( 'c' | 'C' ) ( 'a' | 'A' ) ( 's' | 'S' ) ( 't' | 'T' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:555:9: ( 'c' | 'C' ) ( 'a' | 'A' ) ( 's' | 'S' ) ( 't' | 'T' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CAST"

    // $ANTLR start "WITH"
    public final void mWITH() throws RecognitionException {
        try {
            int _type = WITH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:556:7: ( ( 'w' | 'W' ) ( 'i' | 'I' ) ( 't' | 'T' ) ( 'h' | 'H' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:556:9: ( 'w' | 'W' ) ( 'i' | 'I' ) ( 't' | 'T' ) ( 'h' | 'H' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WITH"

    // $ANTLR start "DROP"
    public final void mDROP() throws RecognitionException {
        try {
            int _type = DROP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:557:7: ( ( 'd' | 'D' ) ( 'r' | 'R' ) ( 'o' | 'O' ) ( 'p' | 'P' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:557:9: ( 'd' | 'D' ) ( 'r' | 'R' ) ( 'o' | 'O' ) ( 'p' | 'P' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DROP"

    // $ANTLR start "TYPEFILL"
    public final void mTYPEFILL() throws RecognitionException {
        try {
            int _type = TYPEFILL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:558:10: ( ( 't' | 'T' ) ( 'y' | 'Y' ) ( 'p' | 'P' ) ( 'e' | 'E' ) ( '-' | ' ' ) ( 'f' | 'F' ) ( 'i' | 'I' ) ( 'l' | 'L' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:558:12: ( 't' | 'T' ) ( 'y' | 'Y' ) ( 'p' | 'P' ) ( 'e' | 'E' ) ( '-' | ' ' ) ( 'f' | 'F' ) ( 'i' | 'I' ) ( 'l' | 'L' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)==' '||input.LA(1)=='-' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TYPEFILL"

    // $ANTLR start "CASTWIDENING"
    public final void mCASTWIDENING() throws RecognitionException {
        try {
            int _type = CASTWIDENING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:559:14: ( ( 'c' | 'C' ) ( 'a' | 'A' ) ( 's' | 'S' ) ( 't' | 'T' ) ( '-' | ' ' ) ( 'w' | 'W' ) ( 'i' | 'I' ) ( 'd' | 'D' ) ( 'e' | 'E' ) ( 'n' | 'N' ) ( 'i' | 'I' ) ( 'n' | 'N' ) ( 'g' | 'G' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:559:16: ( 'c' | 'C' ) ( 'a' | 'A' ) ( 's' | 'S' ) ( 't' | 'T' ) ( '-' | ' ' ) ( 'w' | 'W' ) ( 'i' | 'I' ) ( 'd' | 'D' ) ( 'e' | 'E' ) ( 'n' | 'N' ) ( 'i' | 'I' ) ( 'n' | 'N' ) ( 'g' | 'G' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)==' '||input.LA(1)=='-' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CASTWIDENING"

    // $ANTLR start "CASTNARROWING"
    public final void mCASTNARROWING() throws RecognitionException {
        try {
            int _type = CASTNARROWING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:560:15: ( ( 'c' | 'C' ) ( 'a' | 'A' ) ( 's' | 'S' ) ( 't' | 'T' ) ( '-' | ' ' ) ( 'n' | 'N' ) ( 'a' | 'A' ) ( 'r' | 'R' ) ( 'r' | 'R' ) ( 'o' | 'O' ) ( 'w' | 'W' ) ( 'i' | 'I' ) ( 'n' | 'N' ) ( 'g' | 'G' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:560:17: ( 'c' | 'C' ) ( 'a' | 'A' ) ( 's' | 'S' ) ( 't' | 'T' ) ( '-' | ' ' ) ( 'n' | 'N' ) ( 'a' | 'A' ) ( 'r' | 'R' ) ( 'r' | 'R' ) ( 'o' | 'O' ) ( 'w' | 'W' ) ( 'i' | 'I' ) ( 'n' | 'N' ) ( 'g' | 'G' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)==' '||input.LA(1)=='-' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CASTNARROWING"

    // $ANTLR start "CHILDREN"
    public final void mCHILDREN() throws RecognitionException {
        try {
            int _type = CHILDREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:561:10: ( ( 'c' | 'C' ) ( 'h' | 'H' ) ( 'i' | 'I' ) ( 'l' | 'L' ) ( 'd' | 'D' ) ( 'r' | 'R' ) ( 'e' | 'E' ) ( 'n' | 'N' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:561:12: ( 'c' | 'C' ) ( 'h' | 'H' ) ( 'i' | 'I' ) ( 'l' | 'L' ) ( 'd' | 'D' ) ( 'r' | 'R' ) ( 'e' | 'E' ) ( 'n' | 'N' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHILDREN"

    // $ANTLR start "DESCENDANTS"
    public final void mDESCENDANTS() throws RecognitionException {
        try {
            int _type = DESCENDANTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:562:13: ( ( 'd' | 'D' ) ( 'e' | 'E' ) ( 's' | 'S' ) ( 'c' | 'C' ) ( 'e' | 'E' ) ( 'n' | 'N' ) ( 'd' | 'D' ) ( 'a' | 'A' | 'e' | 'E' ) ( 'n' | 'N' ) ( 't' | 'T' ) ( 's' | 'S' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:562:15: ( 'd' | 'D' ) ( 'e' | 'E' ) ( 's' | 'S' ) ( 'c' | 'C' ) ( 'e' | 'E' ) ( 'n' | 'N' ) ( 'd' | 'D' ) ( 'a' | 'A' | 'e' | 'E' ) ( 'n' | 'N' ) ( 't' | 'T' ) ( 's' | 'S' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='E'||input.LA(1)=='a'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DESCENDANTS"

    // $ANTLR start "DATA"
    public final void mDATA() throws RecognitionException {
        try {
            int _type = DATA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:563:7: ( ( 'd' | 'D' ) ( 'a' | 'A' ) ( 't' | 'T' ) ( 'a' | 'A' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:563:9: ( 'd' | 'D' ) ( 'a' | 'A' ) ( 't' | 'T' ) ( 'a' | 'A' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DATA"

    // $ANTLR start "TRANSLATE"
    public final void mTRANSLATE() throws RecognitionException {
        try {
            int _type = TRANSLATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:564:10: ( ( 't' | 'T' ) ( 'r' | 'R' ) ( 'a' | 'A' ) ( 'n' | 'N' ) ( 's' | 'S' ) ( 'l' | 'L' ) ( 'a' | 'A' ) ( 't' | 'T' ) ( 'e' | 'E' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:564:12: ( 't' | 'T' ) ( 'r' | 'R' ) ( 'a' | 'A' ) ( 'n' | 'N' ) ( 's' | 'S' ) ( 'l' | 'L' ) ( 'a' | 'A' ) ( 't' | 'T' ) ( 'e' | 'E' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TRANSLATE"

    // $ANTLR start "HIDE"
    public final void mHIDE() throws RecognitionException {
        try {
            int _type = HIDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:565:7: ( ( 'h' | 'H' ) ( 'i' | 'I' ) ( 'd' | 'D' ) ( 'e' | 'E' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:565:9: ( 'h' | 'H' ) ( 'i' | 'I' ) ( 'd' | 'D' ) ( 'e' | 'E' )
            {
            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HIDE"

    // $ANTLR start "CLONE"
    public final void mCLONE() throws RecognitionException {
        try {
            int _type = CLONE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:566:8: ( ( 'c' | 'C' ) ( 'l' | 'L' ) ( 'o' | 'O' ) ( 'n' | 'N' ) ( 'e' | 'E' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:566:10: ( 'c' | 'C' ) ( 'l' | 'L' ) ( 'o' | 'O' ) ( 'n' | 'N' ) ( 'e' | 'E' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CLONE"

    // $ANTLR start "WHERE"
    public final void mWHERE() throws RecognitionException {
        try {
            int _type = WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:567:8: ( ( 'w' | 'W' ) ( 'h' | 'H' ) ( 'e' | 'E' ) ( 'r' | 'R' ) ( 'e' | 'E' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:567:10: ( 'w' | 'W' ) ( 'h' | 'H' ) ( 'e' | 'E' ) ( 'r' | 'R' ) ( 'e' | 'E' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHERE"

    // $ANTLR start "GROUP"
    public final void mGROUP() throws RecognitionException {
        try {
            int _type = GROUP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:568:8: ( ( 'g' | 'G' ) ( 'r' | 'R' ) ( 'o' | 'O' ) ( 'u' | 'U' ) ( 'p' | 'P' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:568:10: ( 'g' | 'G' ) ( 'r' | 'R' ) ( 'o' | 'O' ) ( 'u' | 'U' ) ( 'p' | 'P' )
            {
            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GROUP"

    // $ANTLR start "BY"
    public final void mBY() throws RecognitionException {
        try {
            int _type = BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:569:4: ( ( 'b' | 'B' ) ( 'y' | 'Y' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:569:6: ( 'b' | 'B' ) ( 'y' | 'Y' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BY"

    // $ANTLR start "NEW"
    public final void mNEW() throws RecognitionException {
        try {
            int _type = NEW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:570:5: ( ( 'n' | 'N' ) ( 'e' | 'E' ) ( 'w' | 'W' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:570:7: ( 'n' | 'N' ) ( 'e' | 'E' ) ( 'w' | 'W' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEW"

    // $ANTLR start "VALUE"
    public final void mVALUE() throws RecognitionException {
        try {
            int _type = VALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:571:7: ( ( 'v' | 'V' ) ( 'a' | 'A' ) ( 'l' | 'L' ) ( 'u' | 'U' ) ( 'e' | 'E' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:571:9: ( 'v' | 'V' ) ( 'a' | 'A' ) ( 'l' | 'L' ) ( 'u' | 'U' ) ( 'e' | 'E' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VALUE"

    // $ANTLR start "ASELEMENT"
    public final void mASELEMENT() throws RecognitionException {
        try {
            int _type = ASELEMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:572:10: ( ( 'a' | 'A' ) ( 's' | 'S' ) ( 'e' | 'E' ) ( 'l' | 'L' ) ( 'e' | 'E' ) ( 'm' | 'M' ) ( 'e' | 'E' ) ( 'n' | 'N' ) ( 't' | 'T' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:572:12: ( 'a' | 'A' ) ( 's' | 'S' ) ( 'e' | 'E' ) ( 'l' | 'L' ) ( 'e' | 'E' ) ( 'm' | 'M' ) ( 'e' | 'E' ) ( 'n' | 'N' ) ( 't' | 'T' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ASELEMENT"

    // $ANTLR start "ASATTRIBUTE"
    public final void mASATTRIBUTE() throws RecognitionException {
        try {
            int _type = ASATTRIBUTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:573:12: ( ( 'a' | 'A' ) ( 's' | 'S' ) ( 'a' | 'A' ) ( 't' | 'T' ) ( 't' | 'T' ) ( 'r' | 'R' ) ( 'i' | 'I' ) ( 'b' | 'B' ) ( 'u' | 'U' ) ( 't' | 'T' ) ( 'e' | 'E' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:573:14: ( 'a' | 'A' ) ( 's' | 'S' ) ( 'a' | 'A' ) ( 't' | 'T' ) ( 't' | 'T' ) ( 'r' | 'R' ) ( 'i' | 'I' ) ( 'b' | 'B' ) ( 'u' | 'U' ) ( 't' | 'T' ) ( 'e' | 'E' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ASATTRIBUTE"

    // $ANTLR start "OPTIONAL"
    public final void mOPTIONAL() throws RecognitionException {
        try {
            int _type = OPTIONAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:574:9: ( ( 'o' | 'O' ) ( 'p' | 'P' ) ( 't' | 'T' ) ( 'i' | 'I' ) ( 'o' | 'O' ) ( 'n' | 'N' ) ( 'a' | 'A' ) ( 'l' | 'L' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:574:12: ( 'o' | 'O' ) ( 'p' | 'P' ) ( 't' | 'T' ) ( 'i' | 'I' ) ( 'o' | 'O' ) ( 'n' | 'N' ) ( 'a' | 'A' ) ( 'l' | 'L' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPTIONAL"

    // $ANTLR start "REQUIRED"
    public final void mREQUIRED() throws RecognitionException {
        try {
            int _type = REQUIRED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:575:9: ( ( 'r' | 'R' ) ( 'e' | 'E' ) ( 'q' | 'Q' ) ( 'u' | 'U' ) ( 'i' | 'I' ) ( 'r' | 'R' ) ( 'e' | 'E' ) ( 'd' | 'D' ) )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:575:12: ( 'r' | 'R' ) ( 'e' | 'E' ) ( 'q' | 'Q' ) ( 'u' | 'U' ) ( 'i' | 'I' ) ( 'r' | 'R' ) ( 'e' | 'E' ) ( 'd' | 'D' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='Q'||input.LA(1)=='q' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "REQUIRED"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:577:4: ( ( LETTER | '_' | ':' ) ( NAMECHAR )* )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:577:8: ( LETTER | '_' | ':' ) ( NAMECHAR )*
            {
            if ( input.LA(1)==':'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:577:30: ( NAMECHAR )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '-' && LA1_0 <= '.')||(LA1_0 >= '0' && LA1_0 <= ':')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
            	    {
            	    if ( (input.LA(1) >= '-' && input.LA(1) <= '.')||(input.LA(1) >= '0' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "NAMECHAR"
    public final void mNAMECHAR() throws RecognitionException {
        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:580:5: ( LETTER | DIGIT | '.' | '-' | '_' | ':' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
            {
            if ( (input.LA(1) >= '-' && input.LA(1) <= '.')||(input.LA(1) >= '0' && input.LA(1) <= ':')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NAMECHAR"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:584:5: ( '0' .. '9' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:588:5: ( 'a' .. 'z' | 'A' .. 'Z' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "WILDCARD"
    public final void mWILDCARD() throws RecognitionException {
        try {
            int _type = WILDCARD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:592:10: ( '*' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:592:12: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WILDCARD"

    // $ANTLR start "DOUBLEWILDCARD"
    public final void mDOUBLEWILDCARD() throws RecognitionException {
        try {
            int _type = DOUBLEWILDCARD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:593:16: ( '**' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:593:18: '**'
            {
            match("**"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOUBLEWILDCARD"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:594:6: ( '!' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:594:8: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:595:5: ( ( '0' .. '9' )+ )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:595:7: ( '0' .. '9' )+
            {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:595:7: ( '0' .. '9' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "LBRACKET"
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:596:9: ( '[' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:596:11: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LBRACKET"

    // $ANTLR start "RBRACKET"
    public final void mRBRACKET() throws RecognitionException {
        try {
            int _type = RBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:597:9: ( ']' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:597:11: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RBRACKET"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:598:9: ( '}' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:598:11: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:599:9: ( '{' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:599:11: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:600:8: ( ')' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:600:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:601:8: ( '(' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:601:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:605:8: ( '=' ( '=' )? )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:605:10: '=' ( '=' )?
            {
            match('='); 

            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:605:13: ( '=' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='=') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:605:13: '='
                    {
                    match('='); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "LE"
    public final void mLE() throws RecognitionException {
        try {
            int _type = LE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:606:5: ( '<=' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:606:7: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LE"

    // $ANTLR start "GE"
    public final void mGE() throws RecognitionException {
        try {
            int _type = GE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:607:5: ( '>=' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:607:7: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GE"

    // $ANTLR start "GT"
    public final void mGT() throws RecognitionException {
        try {
            int _type = GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:608:4: ( '>' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:608:7: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GT"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:609:4: ( '<' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:609:7: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:612:9: ( ( '\"' (~ '\"' )* '\"' ) | ( '\\'' (~ '\\'' )* '\\'' ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\"') ) {
                alt6=1;
            }
            else if ( (LA6_0=='\'') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:612:12: ( '\"' (~ '\"' )* '\"' )
                    {
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:612:12: ( '\"' (~ '\"' )* '\"' )
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:612:13: '\"' (~ '\"' )* '\"'
                    {
                    match('\"'); 

                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:612:17: (~ '\"' )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0 >= '\u0000' && LA4_0 <= '!')||(LA4_0 >= '#' && LA4_0 <= '\uFFFF')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    match('\"'); 

                    }


                    }
                    break;
                case 2 :
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:612:32: ( '\\'' (~ '\\'' )* '\\'' )
                    {
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:612:32: ( '\\'' (~ '\\'' )* '\\'' )
                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:612:33: '\\'' (~ '\\'' )* '\\''
                    {
                    match('\''); 

                    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:612:38: (~ '\\'' )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0 >= '\u0000' && LA5_0 <= '&')||(LA5_0 >= '(' && LA5_0 <= '\uFFFF')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    match('\''); 

                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "EOS"
    public final void mEOS() throws RecognitionException {
        try {
            int _type = EOS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:613:5: ( ';' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:613:7: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EOS"

    // $ANTLR start "ARROW"
    public final void mARROW() throws RecognitionException {
        try {
            int _type = ARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:614:7: ( '->' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:614:15: '->'
            {
            match("->"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ARROW"

    // $ANTLR start "PIPE"
    public final void mPIPE() throws RecognitionException {
        try {
            int _type = PIPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:615:7: ( '|' )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:615:9: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PIPE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:616:9: ( ( ' ' | '\\r' | '\\n' )+ )
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:616:17: ( ' ' | '\\r' | '\\n' )+
            {
            // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:616:17: ( ' ' | '\\r' | '\\n' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='\n'||LA7_0=='\r'||LA7_0==' ') ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:
            	    {
            	    if ( input.LA(1)=='\n'||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:8: ( T__54 | T__55 | T__56 | T__57 | T__58 | MORPH | ORDER | MUTATE | COMPOSE | RESTRICT | CAST | WITH | DROP | TYPEFILL | CASTWIDENING | CASTNARROWING | CHILDREN | DESCENDANTS | DATA | TRANSLATE | HIDE | CLONE | WHERE | GROUP | BY | NEW | VALUE | ASELEMENT | ASATTRIBUTE | OPTIONAL | REQUIRED | ID | WILDCARD | DOUBLEWILDCARD | NOT | INT | LBRACKET | RBRACKET | RBRACE | LBRACE | RPAREN | LPAREN | EQUALS | LE | GE | GT | LT | STRING | EOS | ARROW | PIPE | WS )
        int alt8=52;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:10: T__54
                {
                mT__54(); 


                }
                break;
            case 2 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:16: T__55
                {
                mT__55(); 


                }
                break;
            case 3 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:22: T__56
                {
                mT__56(); 


                }
                break;
            case 4 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:28: T__57
                {
                mT__57(); 


                }
                break;
            case 5 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:34: T__58
                {
                mT__58(); 


                }
                break;
            case 6 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:40: MORPH
                {
                mMORPH(); 


                }
                break;
            case 7 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:46: ORDER
                {
                mORDER(); 


                }
                break;
            case 8 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:52: MUTATE
                {
                mMUTATE(); 


                }
                break;
            case 9 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:59: COMPOSE
                {
                mCOMPOSE(); 


                }
                break;
            case 10 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:67: RESTRICT
                {
                mRESTRICT(); 


                }
                break;
            case 11 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:76: CAST
                {
                mCAST(); 


                }
                break;
            case 12 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:81: WITH
                {
                mWITH(); 


                }
                break;
            case 13 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:86: DROP
                {
                mDROP(); 


                }
                break;
            case 14 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:91: TYPEFILL
                {
                mTYPEFILL(); 


                }
                break;
            case 15 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:100: CASTWIDENING
                {
                mCASTWIDENING(); 


                }
                break;
            case 16 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:113: CASTNARROWING
                {
                mCASTNARROWING(); 


                }
                break;
            case 17 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:127: CHILDREN
                {
                mCHILDREN(); 


                }
                break;
            case 18 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:136: DESCENDANTS
                {
                mDESCENDANTS(); 


                }
                break;
            case 19 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:148: DATA
                {
                mDATA(); 


                }
                break;
            case 20 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:153: TRANSLATE
                {
                mTRANSLATE(); 


                }
                break;
            case 21 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:163: HIDE
                {
                mHIDE(); 


                }
                break;
            case 22 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:168: CLONE
                {
                mCLONE(); 


                }
                break;
            case 23 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:174: WHERE
                {
                mWHERE(); 


                }
                break;
            case 24 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:180: GROUP
                {
                mGROUP(); 


                }
                break;
            case 25 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:186: BY
                {
                mBY(); 


                }
                break;
            case 26 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:189: NEW
                {
                mNEW(); 


                }
                break;
            case 27 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:193: VALUE
                {
                mVALUE(); 


                }
                break;
            case 28 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:199: ASELEMENT
                {
                mASELEMENT(); 


                }
                break;
            case 29 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:209: ASATTRIBUTE
                {
                mASATTRIBUTE(); 


                }
                break;
            case 30 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:221: OPTIONAL
                {
                mOPTIONAL(); 


                }
                break;
            case 31 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:230: REQUIRED
                {
                mREQUIRED(); 


                }
                break;
            case 32 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:239: ID
                {
                mID(); 


                }
                break;
            case 33 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:242: WILDCARD
                {
                mWILDCARD(); 


                }
                break;
            case 34 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:251: DOUBLEWILDCARD
                {
                mDOUBLEWILDCARD(); 


                }
                break;
            case 35 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:266: NOT
                {
                mNOT(); 


                }
                break;
            case 36 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:270: INT
                {
                mINT(); 


                }
                break;
            case 37 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:274: LBRACKET
                {
                mLBRACKET(); 


                }
                break;
            case 38 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:283: RBRACKET
                {
                mRBRACKET(); 


                }
                break;
            case 39 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:292: RBRACE
                {
                mRBRACE(); 


                }
                break;
            case 40 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:299: LBRACE
                {
                mLBRACE(); 


                }
                break;
            case 41 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:306: RPAREN
                {
                mRPAREN(); 


                }
                break;
            case 42 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:313: LPAREN
                {
                mLPAREN(); 


                }
                break;
            case 43 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:320: EQUALS
                {
                mEQUALS(); 


                }
                break;
            case 44 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:327: LE
                {
                mLE(); 


                }
                break;
            case 45 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:330: GE
                {
                mGE(); 


                }
                break;
            case 46 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:333: GT
                {
                mGT(); 


                }
                break;
            case 47 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:336: LT
                {
                mLT(); 


                }
                break;
            case 48 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:339: STRING
                {
                mSTRING(); 


                }
                break;
            case 49 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:346: EOS
                {
                mEOS(); 


                }
                break;
            case 50 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:350: ARROW
                {
                mARROW(); 


                }
                break;
            case 51 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:356: PIPE
                {
                mPIPE(); 


                }
                break;
            case 52 :
                // C:\\Users\\Curt\\Dropbox\\netbeans\\eXistVDLN\\eXistVDLN\\src\\xmorph\\edu\\usu\\grammar\\XMorph.g:1:361: WS
                {
                mWS(); 


                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\3\uffff\20\23\1\uffff\1\77\11\uffff\1\101\1\103\5\uffff\4\23\1"+
        "\112\22\23\1\135\1\23\6\uffff\1\137\2\23\1\142\1\143\1\23\1\uffff"+
        "\22\23\1\uffff\1\23\1\uffff\2\23\2\uffff\5\23\1\u0080\4\23\1\u0086"+
        "\1\23\1\u0088\1\23\1\u008a\2\23\1\u008e\4\23\1\u0093\1\23\1\u0095"+
        "\3\23\2\uffff\1\23\1\u009d\2\23\1\uffff\1\u00a0\1\uffff\1\23\1\uffff"+
        "\1\23\1\uffff\1\23\1\uffff\1\u00a4\1\u00a5\2\23\1\uffff\1\23\1\uffff"+
        "\1\u00a9\3\23\2\uffff\1\23\1\uffff\2\23\1\uffff\3\23\2\uffff\3\23"+
        "\1\uffff\1\u00b6\12\23\1\u00c1\1\uffff\2\23\1\u00c4\1\u00c5\1\u00c6"+
        "\1\23\1\u008c\1\23\1\u00c9\1\23\1\uffff\2\23\3\uffff\1\23\1\u00ce"+
        "\1\uffff\4\23\1\uffff\1\u00d3\2\23\1\u00d6\1\uffff\2\23\1\uffff"+
        "\1\u009a\1\23\1\u009b";
    static final String DFA8_eofS =
        "\u00da\uffff";
    static final String DFA8_minS =
        "\1\12\2\uffff\1\123\1\105\1\120\1\117\1\120\1\101\1\105\1\110\1"+
        "\101\1\122\1\111\1\122\1\131\1\105\1\101\1\123\1\uffff\1\52\11\uffff"+
        "\2\75\5\uffff\1\144\1\101\1\164\1\127\1\55\1\104\1\124\1\122\1\124"+
        "\1\115\1\123\1\111\1\117\1\121\1\124\1\105\1\117\1\123\1\124\1\120"+
        "\1\101\1\104\1\117\1\55\1\114\6\uffff\1\55\1\114\1\124\2\55\1\105"+
        "\1\uffff\1\111\1\120\1\101\1\120\1\124\1\114\1\116\1\124\1\125\1"+
        "\110\1\122\1\120\1\103\1\101\1\105\1\116\1\105\1\125\1\uffff\1\125"+
        "\1\uffff\1\105\1\124\2\uffff\1\122\1\117\1\110\1\124\1\117\1\40"+
        "\1\104\1\105\1\122\1\111\1\55\1\105\1\55\1\105\1\55\1\40\1\123\1"+
        "\55\1\120\1\105\1\115\1\122\1\55\1\116\1\55\1\105\1\123\1\116\1"+
        "\uffff\1\116\1\122\1\55\1\111\1\122\1\uffff\1\55\1\uffff\1\116\1"+
        "\uffff\1\106\1\uffff\1\114\1\uffff\2\55\1\105\1\111\1\uffff\1\101"+
        "\1\uffff\1\55\1\105\1\111\1\101\2\uffff\1\105\1\uffff\1\103\1\105"+
        "\1\uffff\1\104\1\111\1\101\2\uffff\1\116\1\102\1\114\1\uffff\1\55"+
        "\1\104\1\122\1\116\1\124\1\104\1\101\1\114\2\124\1\125\1\55\1\uffff"+
        "\1\105\1\122\3\55\1\116\1\55\1\105\1\55\1\124\1\uffff\1\116\1\117"+
        "\3\uffff\1\124\1\55\1\uffff\1\105\1\111\1\127\1\123\1\uffff\1\55"+
        "\1\116\1\111\1\55\1\uffff\1\107\1\116\1\uffff\1\55\1\107\1\55";
    static final String DFA8_maxS =
        "\1\175\2\uffff\1\163\1\157\1\162\1\165\1\162\1\157\1\145\1\151\1"+
        "\162\1\171\1\151\1\162\1\171\1\145\1\141\1\163\1\uffff\1\52\11\uffff"+
        "\2\75\5\uffff\1\144\1\145\1\164\1\167\1\172\1\144\1\164\1\162\1"+
        "\164\1\155\1\163\1\151\1\157\1\163\1\164\1\145\1\157\1\163\1\164"+
        "\1\160\1\141\1\144\1\157\1\172\1\154\6\uffff\1\172\1\154\1\164\2"+
        "\172\1\145\1\uffff\1\151\1\160\1\141\1\160\1\164\1\154\1\156\1\164"+
        "\1\165\1\150\1\162\1\160\1\143\1\141\1\145\1\156\1\145\1\165\1\uffff"+
        "\1\165\1\uffff\1\145\1\164\2\uffff\1\162\1\157\1\150\1\164\1\157"+
        "\1\172\1\144\1\145\1\162\1\151\1\172\1\145\1\172\1\145\1\172\1\55"+
        "\1\163\1\172\1\160\1\145\1\155\1\162\1\172\1\156\1\172\1\145\1\163"+
        "\1\167\1\uffff\1\167\1\162\1\172\1\151\1\162\1\uffff\1\172\1\uffff"+
        "\1\156\1\uffff\1\146\1\uffff\1\154\1\uffff\2\172\1\145\1\151\1\uffff"+
        "\1\141\1\uffff\1\172\1\145\1\151\1\141\2\uffff\1\145\1\uffff\1\143"+
        "\1\145\1\uffff\1\144\1\151\1\141\2\uffff\1\156\1\142\1\154\1\uffff"+
        "\1\172\1\144\1\162\1\156\1\164\1\144\1\145\1\154\2\164\1\165\1\172"+
        "\1\uffff\1\145\1\162\3\172\1\156\1\172\1\145\1\172\1\164\1\uffff"+
        "\1\156\1\157\3\uffff\1\164\1\172\1\uffff\1\145\1\151\1\167\1\163"+
        "\1\uffff\1\172\1\156\1\151\1\172\1\uffff\1\147\1\156\1\uffff\1\172"+
        "\1\147\1\172";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\1\2\20\uffff\1\40\1\uffff\1\43\1\44\1\45\1\46\1\47"+
        "\1\50\1\51\1\52\1\53\2\uffff\1\60\1\61\1\62\1\63\1\64\31\uffff\1"+
        "\42\1\41\1\54\1\57\1\55\1\56\6\uffff\1\5\22\uffff\1\31\1\uffff\1"+
        "\3\2\uffff\1\4\1\32\34\uffff\1\13\5\uffff\1\14\1\uffff\1\15\1\uffff"+
        "\1\23\1\uffff\1\16\1\uffff\1\25\4\uffff\1\7\1\uffff\1\6\4\uffff"+
        "\1\17\1\20\1\uffff\1\26\2\uffff\1\27\3\uffff\1\30\1\33\3\uffff\1"+
        "\10\14\uffff\1\11\12\uffff\1\36\2\uffff\1\21\1\12\1\37\2\uffff\1"+
        "\34\4\uffff\1\24\4\uffff\1\35\2\uffff\1\22\3\uffff";
    static final String DFA8_specialS =
        "\u00da\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\44\2\uffff\1\44\22\uffff\1\44\1\25\1\40\4\uffff\1\40\1\34"+
            "\1\33\1\24\1\uffff\1\1\1\42\1\2\1\uffff\12\26\1\23\1\41\1\36"+
            "\1\35\1\37\2\uffff\1\22\1\17\1\10\1\13\2\23\1\16\1\15\4\23\1"+
            "\6\1\20\1\7\2\23\1\11\1\23\1\14\1\23\1\21\1\12\3\23\1\27\1\uffff"+
            "\1\30\1\uffff\1\23\1\uffff\1\3\1\17\1\10\1\13\2\23\1\16\1\15"+
            "\4\23\1\6\1\4\1\5\2\23\1\11\1\23\1\14\1\23\1\21\1\12\3\23\1"+
            "\32\1\43\1\31",
            "",
            "",
            "\1\46\32\uffff\1\45\4\uffff\1\46",
            "\1\50\37\uffff\1\50\11\uffff\1\47",
            "\1\53\1\uffff\1\52\35\uffff\1\53\1\uffff\1\51",
            "\1\54\5\uffff\1\55\31\uffff\1\54\5\uffff\1\55",
            "\1\53\1\uffff\1\52\35\uffff\1\53\1\uffff\1\52",
            "\1\57\6\uffff\1\60\3\uffff\1\61\2\uffff\1\56\21\uffff\1\57"+
            "\6\uffff\1\60\3\uffff\1\61\2\uffff\1\56",
            "\1\62\37\uffff\1\62",
            "\1\64\1\63\36\uffff\1\64\1\63",
            "\1\67\3\uffff\1\66\14\uffff\1\65\16\uffff\1\67\3\uffff\1\66"+
            "\14\uffff\1\65",
            "\1\71\6\uffff\1\70\30\uffff\1\71\6\uffff\1\70",
            "\1\72\37\uffff\1\72",
            "\1\73\37\uffff\1\73",
            "\1\74\37\uffff\1\74",
            "\1\50\37\uffff\1\50",
            "\1\75\37\uffff\1\75",
            "\1\46\37\uffff\1\46",
            "",
            "\1\76",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\100",
            "\1\102",
            "",
            "",
            "",
            "",
            "",
            "\1\104",
            "\1\106\3\uffff\1\105\33\uffff\1\106\3\uffff\1\105",
            "\1\107",
            "\1\110\37\uffff\1\110",
            "\2\23\1\uffff\13\23\6\uffff\3\23\1\111\26\23\4\uffff\1\23\1"+
            "\uffff\3\23\1\111\26\23",
            "\1\111\37\uffff\1\111",
            "\1\113\37\uffff\1\113",
            "\1\114\37\uffff\1\114",
            "\1\115\37\uffff\1\115",
            "\1\116\37\uffff\1\116",
            "\1\117\37\uffff\1\117",
            "\1\120\37\uffff\1\120",
            "\1\121\37\uffff\1\121",
            "\1\123\1\uffff\1\122\35\uffff\1\123\1\uffff\1\122",
            "\1\124\37\uffff\1\124",
            "\1\125\37\uffff\1\125",
            "\1\126\37\uffff\1\126",
            "\1\127\37\uffff\1\127",
            "\1\130\37\uffff\1\130",
            "\1\131\37\uffff\1\131",
            "\1\132\37\uffff\1\132",
            "\1\133\37\uffff\1\133",
            "\1\134\37\uffff\1\134",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\136\37\uffff\1\136",
            "",
            "",
            "",
            "",
            "",
            "",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\140\37\uffff\1\140",
            "\1\141\37\uffff\1\141",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\144\37\uffff\1\144",
            "",
            "\1\145\37\uffff\1\145",
            "\1\146\37\uffff\1\146",
            "\1\147\37\uffff\1\147",
            "\1\150\37\uffff\1\150",
            "\1\151\37\uffff\1\151",
            "\1\152\37\uffff\1\152",
            "\1\153\37\uffff\1\153",
            "\1\154\37\uffff\1\154",
            "\1\155\37\uffff\1\155",
            "\1\156\37\uffff\1\156",
            "\1\157\37\uffff\1\157",
            "\1\160\37\uffff\1\160",
            "\1\161\37\uffff\1\161",
            "\1\162\37\uffff\1\162",
            "\1\163\37\uffff\1\163",
            "\1\164\37\uffff\1\164",
            "\1\165\37\uffff\1\165",
            "\1\166\37\uffff\1\166",
            "",
            "\1\167\37\uffff\1\167",
            "",
            "\1\170\37\uffff\1\170",
            "\1\171\37\uffff\1\171",
            "",
            "",
            "\1\172\37\uffff\1\172",
            "\1\173\37\uffff\1\173",
            "\1\174\37\uffff\1\174",
            "\1\175\37\uffff\1\175",
            "\1\176\37\uffff\1\176",
            "\1\u0081\14\uffff\1\177\1\23\1\uffff\13\23\6\uffff\32\23\4"+
            "\uffff\1\23\1\uffff\32\23",
            "\1\u0082\37\uffff\1\u0082",
            "\1\u0083\37\uffff\1\u0083",
            "\1\u0084\37\uffff\1\u0084",
            "\1\u0085\37\uffff\1\u0085",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u0087\37\uffff\1\u0087",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u0089\37\uffff\1\u0089",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u008c\14\uffff\1\u008b",
            "\1\u008d\37\uffff\1\u008d",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u008f\37\uffff\1\u008f",
            "\1\u0090\37\uffff\1\u0090",
            "\1\u0091\37\uffff\1\u0091",
            "\1\u0092\37\uffff\1\u0092",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u0094\37\uffff\1\u0094",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u0096\37\uffff\1\u0096",
            "\1\u0097\37\uffff\1\u0097",
            "\1\u0099\10\uffff\1\u0098\26\uffff\1\u0099\10\uffff\1\u0098",
            "",
            "\1\u009b\10\uffff\1\u009a\26\uffff\1\u009b\10\uffff\1\u009a",
            "\1\u009c\37\uffff\1\u009c",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u009e\37\uffff\1\u009e",
            "\1\u009f\37\uffff\1\u009f",
            "",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\u00a1\37\uffff\1\u00a1",
            "",
            "\1\u00a2\37\uffff\1\u00a2",
            "",
            "\1\u00a3\37\uffff\1\u00a3",
            "",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00a6\37\uffff\1\u00a6",
            "\1\u00a7\37\uffff\1\u00a7",
            "",
            "\1\u00a8\37\uffff\1\u00a8",
            "",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00aa\37\uffff\1\u00aa",
            "\1\u00ab\37\uffff\1\u00ab",
            "\1\u00ac\37\uffff\1\u00ac",
            "",
            "",
            "\1\u00ad\37\uffff\1\u00ad",
            "",
            "\1\u00ae\37\uffff\1\u00ae",
            "\1\u00af\37\uffff\1\u00af",
            "",
            "\1\u00b0\37\uffff\1\u00b0",
            "\1\u00b1\37\uffff\1\u00b1",
            "\1\u00b2\37\uffff\1\u00b2",
            "",
            "",
            "\1\u00b3\37\uffff\1\u00b3",
            "\1\u00b4\37\uffff\1\u00b4",
            "\1\u00b5\37\uffff\1\u00b5",
            "",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00b7\37\uffff\1\u00b7",
            "\1\u00b8\37\uffff\1\u00b8",
            "\1\u00b9\37\uffff\1\u00b9",
            "\1\u00ba\37\uffff\1\u00ba",
            "\1\u00bb\37\uffff\1\u00bb",
            "\1\u00bc\3\uffff\1\u00bc\33\uffff\1\u00bc\3\uffff\1\u00bc",
            "\1\u00bd\37\uffff\1\u00bd",
            "\1\u00be\37\uffff\1\u00be",
            "\1\u00bf\37\uffff\1\u00bf",
            "\1\u00c0\37\uffff\1\u00c0",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\u00c2\37\uffff\1\u00c2",
            "\1\u00c3\37\uffff\1\u00c3",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00c7\37\uffff\1\u00c7",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00c8\37\uffff\1\u00c8",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00ca\37\uffff\1\u00ca",
            "",
            "\1\u00cb\37\uffff\1\u00cb",
            "\1\u00cc\37\uffff\1\u00cc",
            "",
            "",
            "",
            "\1\u00cd\37\uffff\1\u00cd",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\u00cf\37\uffff\1\u00cf",
            "\1\u00d0\37\uffff\1\u00d0",
            "\1\u00d1\37\uffff\1\u00d1",
            "\1\u00d2\37\uffff\1\u00d2",
            "",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00d4\37\uffff\1\u00d4",
            "\1\u00d5\37\uffff\1\u00d5",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\u00d7\37\uffff\1\u00d7",
            "\1\u00d8\37\uffff\1\u00d8",
            "",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00d9\37\uffff\1\u00d9",
            "\2\23\1\uffff\13\23\6\uffff\32\23\4\uffff\1\23\1\uffff\32\23"
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__54 | T__55 | T__56 | T__57 | T__58 | MORPH | ORDER | MUTATE | COMPOSE | RESTRICT | CAST | WITH | DROP | TYPEFILL | CASTWIDENING | CASTNARROWING | CHILDREN | DESCENDANTS | DATA | TRANSLATE | HIDE | CLONE | WHERE | GROUP | BY | NEW | VALUE | ASELEMENT | ASATTRIBUTE | OPTIONAL | REQUIRED | ID | WILDCARD | DOUBLEWILDCARD | NOT | INT | LBRACKET | RBRACKET | RBRACE | LBRACE | RPAREN | LPAREN | EQUALS | LE | GE | GT | LT | STRING | EOS | ARROW | PIPE | WS );";
        }
    }
 

}