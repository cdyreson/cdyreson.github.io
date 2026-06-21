all: dimensions count

count: code
	cd parser; make count

dimensions: code
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

cleanDbs: 
	rm -f dbs/*

reallyclean: cleanDbs
	cd configure; make clean
	cd tools; make clean
	cd persistent; make clean
	cd database; make clean
	cd globals; make clean
	cd readspec; make reallyclean
	cd parser; make reallyclean
	cd cubette; make clean
	cd gui; make clean
