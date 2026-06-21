JAVA_FLAGS=-classpath ../..:.

all: Canned.class
	java $(JAVA_FLAGS) cube.canned.Canned >InitTables.java

code: Canned.class

Canned.class: Canned.java 
	javac $(JAVA_FLAGS) Canned.java

clean: 
	rm -f *.class
