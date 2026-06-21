#all: InitTables.class File.class jdbm.class
JAVA_FLAGS=-classpath .:../../..
all: InitTables.class jdbm.class MyByteArray.class

MyByteArray.class: MyByteArray.java 
	javac $(JAVA_FLAGS) MyByteArray.java

InitTables.class: InitTables.java 
	javac $(JAVA_FLAGS) InitTables.java

jdbm.class: jdbm.java 
	javac $(JAVA_FLAGS) jdbm.java

clean: 
	rm -f *.class
