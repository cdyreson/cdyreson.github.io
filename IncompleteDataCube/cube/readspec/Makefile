JAVA_FLAGS=-classpath ../..:.

code: sym.class scanner.class parser.class lexer.class Main.class LexFileWriter.class CupFileWriter.class

scanner.class: measures.lex.java
	javac $(JAVA_FLAGS) measures.lex.java

measures.lex.java: measures.lex
	java $(JAVA_FLAGS) JavaLex.Main measures.lex

sym.class: measures.cup
	java $(JAVA_FLAGS) java_cup.Main < measures.cup
	javac $(JAVA_FLAGS) sym.java

parser.class: parser.java
	javac $(JAVA_FLAGS) parser.java

lexer.class: lexer.java
	javac $(JAVA_FLAGS) lexer.java

Main.class: Main.java
	javac $(JAVA_FLAGS) Main.java

LexFileWriter.class: LexFileWriter.java
	javac $(JAVA_FLAGS) LexFileWriter.java

CupFileWriter.class: CupFileWriter.java
	javac $(JAVA_FLAGS) CupFileWriter.java

dimensions: 
	#creating the dimension database
	java $(JAVA_FLAGS) Main >ooj

clean: 
	#cleaning up the non-JavaCup classes
	rm -f Main.class CupFileWriter.class LexFileWriter.class lexer.class

reallyclean: 
	#cleaning up everything
	rm *.class
