JAVA_FLAGS=-classpath ../..:.

all: Main.class

code: Main.class

test: Main.class
	java $(JAVA_FLAGS) Main

Main.class: Main.java
	javac $(JAVA_FLAGS) Main.java

clean: 
	rm -f *.class
