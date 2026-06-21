JAVA_FLAGS=-classpath ../..
code: CubeGUI.class MessageLayout.class MessageHandler.class 

CubeGUI.class: CubeGUI.java
	javac $(JAVA_FLAGS) CubeGUI.java

MessageLayout.class: MessageLayout.java
	javac $(JAVA_FLAGS) MessageLayout.java

MessageHandler.class: MessageHandler.java
	javac $(JAVA_FLAGS) MessageHandler.java

clean: 
	rm -f *.class
