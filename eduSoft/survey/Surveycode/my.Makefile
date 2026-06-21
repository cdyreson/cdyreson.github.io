SURVEYS = /users/other/world/ftp/web/teaching/Subjects/cp2001/1996/surveys

.PRECIOUS:	%.d

%.html:		%.d
		-mkdir $(SURVEYS)/%.dbs
		chmod 770 $(SURVEYS)/%.dbs
		surveymaker $(SURVEYS)/%.dbs $(SURVEYS)/$@ %-stats.html %-passwords < $(SURVEYS)/%.d/$@
		chmod 644 $(SURVEYS)/$@ 
		chgrp -R cgiperl $(SURVEYS)/%.dbs
		chmod 660 $(SURVEYS)/%.dbs/*

%.d: 		%.tex
		latex2html -split 0 -info 0 -address '' -dir $(SURVEYS)/$@ $?
		chmod -R go+rX $(SURVEYS)/$@

random: 	
		/home/tony/bin/sun4/genrand 70 1>list 2>list.tex

%.clean:
		rm -rf $(SURVEYS)/%.d $(SURVEYS)/%.html $(SURVEYS)/%-stats.html $(SURVEYS)/%.dbs
