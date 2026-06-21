/*
 * A parser exception
 */

package xmorph.edu.usu.grammar;
import org.antlr.runtime.RecognitionException;

/**
 *
 * @author Curtis Dyreson
 */
public class XMorphParseException extends RecognitionException {
   public String msg = "";
   
   public XMorphParseException(String msg) {
       this.msg = msg;
   }
   
}
