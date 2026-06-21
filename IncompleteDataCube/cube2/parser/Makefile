code: scanner.class CountIt.class lexer.class Main.class Time Page Machine

Page: Page.flex
	flex Page.flex
	gcc lex.yy.c -L/pack/flex/lib -lfl -o Page

Time: Time.flex
	flex Time.flex
	gcc lex.yy.c  -L/pack/flex/lib -lfl -o Time

Machine: Machine.flex
	flex Machine.flex
	gcc lex.yy.c  -L/pack/flex/lib -lfl -o Machine

temp: code
	perl splitter <../logfiles/templog 
	perl combiner | java Main

count: code
	perl splitter <../logfiles/access_log 
	perl combiner | java Main

CountIt.class: CountIt.java
	javac CountIt.java

scanner.class: parser.lex.java
	javac parser.lex.java

parser.lex.java: parser.lex
	java JavaLex.Main parser.lex

lexer.class: lexer.java
	javac lexer.java

Main.class: Main.java
	javac Main.java

clean: 
	rm -f Main.class CountIt.class lexer.class

reallyclean: 
	rm -f *.class *.o lex.yy.c
