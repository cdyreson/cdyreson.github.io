TESTS	= survey

newcode:		
		rm -f Surveycode.tar.gz
		tar cf Surveycode.tar Surveycode
		gzip Surveycode.tar
		chmod a+r Surveycode.tar.gz

default:	$(TESTS)

.PRECIOUS:	%.d

%.html:		
		-mkdir %.dbs
		chmod 770 %.dbs
		SurveyMaker `pwd`/%.dbs $@ `pwd`/%-stats.html `pwd`/%-passwords < %.tex
		chmod 644 $@ %-stats.html
		chmod 660 %.dbs/*
		chgrp -R cgiperl %.dbs
		rm %.dbs/enterdb

%.clean:
		rm -rf %.d %.html %-stats.html %.dbs
