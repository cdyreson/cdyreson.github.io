JAVA_FLAGS=-deprecation -classpath ../..
code: Cube.class MessageLayout.class MessageHandler.class 

Cube.class: Cube.java
	javac $(JAVA_FLAGS) Cube.java

MessageLayout.class: MessageLayout.java
	javac $(JAVA_FLAGS) MessageLayout.java

MessageHandler.class: MessageHandler.java
	javac $(JAVA_FLAGS) MessageHandler.java

clean: 
	rm -f *.class
