code: sym.class scanner.class parser.class lexer.class Main.class LexFileWriter.class CupFileWriter.class

scanner.class: measures.lex.java
	javac measures.lex.java

measures.lex.java: measures.lex
	java JavaLex.Main measures.lex

sym.class: measures.cup
	java java_cup.Main < measures.cup
	javac sym.java

parser.class: parser.java
	javac parser.java

lexer.class: lexer.java
	javac lexer.java

Main.class: Main.java
	javac Main.java

LexFileWriter.class: LexFileWriter.java
	javac LexFileWriter.java

CupFileWriter.class: CupFileWriter.java
	javac CupFileWriter.java

dimensions: 
	#creating the dimension database
	java Main >ooj

clean: 
	#cleaning up the non-JavaCup classes
	rm -f Main.class CupFileWriter.class LexFileWriter.class lexer.class

reallyclean: 
	#cleaning up everything
	rm *.class
