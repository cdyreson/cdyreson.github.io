TESTS	= quiz

newcode :	
		rm -f Quizcode.tar.gz
		tar cf Quizcode.tar Quizcode
		gzip Quizcode.tar
		chmod a+r Quizcode.tar.gz

default:	$(TESTS)

.PRECIOUS:	%.d

%.html:		%.d
		-mkdir %.dbs
		chmod 770 %.dbs
		QuizMaker `pwd`/%.dbs $@ `pwd`/%-stats.html q.pass.html < %.d/%.html
		chmod 644 $@ %-stats.html
		chgrp -R cgiperl %.dbs
		chmod 660 %.dbs/*
		rm %.dbs/passworddb

%.d: 		%.tex
		latex2html -split 0 -info 0 -address '' -dir $@ %.tex
		chmod -R go+rX $@

%.clean:
		rm -rf %.d %.html %-stats.html %.dbs
