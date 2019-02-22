all: build

build: tema3.jar

tema3.jar:
	./build
	javac ./bin/*.java
	rm ./bin/*.java
	jar cvf tema3.jar -C ./bin/ .

run:
	java -Xmx1G -cp tema3.jar MainClass $(ARGS)

clean: 
	rm -fr ./bin/* tema3.jar

.PHONY: build clean run

