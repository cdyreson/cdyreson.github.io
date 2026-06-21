JAVA_FLAGS=-classpath ../..:.
code: scanner.class CountIt.class lexer.class Main.class Time Page Machine

Page: Page.flex
	flex Page.flex
	gcc lex.yy.c -lfl -o Page

Time: Time.flex
	flex Time.flex
	gcc lex.yy.c -lfl -o Time

Machine: Machine.flex
	flex Machine.flex
	gcc lex.yy.c -lfl -o Machine

temp: code
	'./splitter' <../logfiles/templog 
	'./combiner' | java $(JAVA_FLAGS) Main

count: code
	'./splitter' <../logfiles/access_log 
	'./combiner' | java $(JAVA_FLAGS) Main

CountIt.class: CountIt.java
	javac $(JAVA_FLAGS) CountIt.java

scanner.class: parser.lex.java
	javac $(JAVA_FLAGS) parser.lex.java

parser.lex.java: parser.lex
	java $(JAVA_FLAGS) JavaLex.Main parser.lex

lexer.class: lexer.java
	javac $(JAVA_FLAGS) lexer.java

Main.class: Main.java
	javac $(JAVA_FLAGS) Main.java

clean: 
	rm -f Main.class CountIt.class lexer.class

reallyclean: 
	rm -f *.class *.o lex.yy.c
