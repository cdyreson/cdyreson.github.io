JAVA_FLAGS=-classpath ../..

code: CubetteSpecification.class LogRecord.class Cubette.class Federation.class

Federation.class: Federation.java
	javac $(JAVA_FLAGS) Federation.java

LogRecord.class: LogRecord.java
	javac $(JAVA_FLAGS) LogRecord.java

Cubette.class: Cubette.java
	javac $(JAVA_FLAGS) Cubette.java

CubetteSpecification.class: CubetteSpecification.java
	javac $(JAVA_FLAGS) CubetteSpecification.java

clean: 
	rm -f *.class
