QUIZZES = /world/www/ftp/web/teaching/Subjects/cp2001/1996/quizzes

.PRECIOUS:	%.d

%.html:		%.d
		-mkdir $(QUIZZES)/%.dbs
		chmod 770 $(QUIZZES)/%.dbs
		quizmaker $(QUIZZES)/%.dbs $(QUIZZES)/$@ %-stats.html %-passwords < $(QUIZZES)/%.d/$@
		chmod 644 $(QUIZZES)/$@ 
		chgrp -R cgiperl $(QUIZZES)/%.dbs
		chmod 660 $(QUIZZES)/%.dbs/*

%.d: 		%.tex
		latex2html -split 0 -info 0 -address '' -dir $(QUIZZES)/$@ $?
		chmod -R go+rX $(QUIZZES)/$@

%.clean:
		rm -rf $(QUIZZES)/%.d $(QUIZZES)/%.html $(QUIZZES)/%-stats.html $(QUIZZES)/%.dbs
