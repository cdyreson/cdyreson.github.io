JAVA_FLAGS=-classpath ..

all: code dimensions count query

doclasses:
	javac $(JAVA_FLAGS) tools/Internal.java
	javac $(JAVA_FLAGS) database/Dbm.java
	javac $(JAVA_FLAGS) cubette/Cubette.java
	javac $(JAVA_FLAGS) globals/Globals.java
	javac $(JAVA_FLAGS) gui/CubeGUI.java
	javac $(JAVA_FLAGS) applet/Cube.java
	javac $(JAVA_FLAGS) readspec/lexer.java

query: 
	cd test; make test

count: 
	cd parser; make count

dimensions: 
	cd readspec; make dimensions

code: 
	cd configure; make code
	cd tools; make code
	cd persistent; make code
	cd database; make code
	cd globals; make code
	cd readspec; make code
	cd parser; make code
	cd cubette; make code
	cd gui; make code
	cd applet; make code
	cd canned; make code
	cd test; make code

clean: 
	cd configure; make clean
	cd tools; make clean
	cd persistent; make clean
	cd database; make clean
	cd globals; make clean
	cd readspec; make clean
	cd parser; make clean
	cd cubette; make clean
	cd gui; make clean
	cd applet; make clean
	cd canned; make clean
	cd test; make clean

cleanDbs: 
	rm -f dbs/*

reallyclean: cleanDbs clean
	cd readspec; make reallyclean
	cd parser; make reallyclean
