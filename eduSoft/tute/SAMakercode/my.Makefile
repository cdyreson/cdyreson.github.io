LESSONS = /world/www/ftp/web/teaching/Subjects/cp2001/1996/lessons

.PRECIOUS:	%.d

%.html:		%.d
		-mkdir $(LESSONS)/%.dbs
		chmod 770 $(LESSONS)/%.dbs
		SAMaker $(LESSONS)/%.dbs $(LESSONS)/$@ $(LESSONS)/%-stats.html < $(LESSONS)/%.d/$@
		chmod 644 $(LESSONS)/$@ $(LESSONS)/%-stats.html
		chgrp -R cgiperl $(LESSONS)/%.dbs
		chmod 660 $(LESSONS)/%.dbs/*

%.d: 		%.tex
		latex2html -split 0 -info 0 -address '' -dir $(LESSONS)/$@ $?
		chmod -R go+rX $(LESSONS)/$@

%.clean:
		rm -rf $(LESSONS)/%.d $(LESSONS)/%.html $(LESSONS)/%-stats.html $(LESSONS)/%.dbs
