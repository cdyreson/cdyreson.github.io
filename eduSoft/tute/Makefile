LESSONS = /home/curtis/web/htmls/software/tute
newcode :
		rm -f SAMakercode.tar.gz
		tar cf SAMakercode.tar SAMakercode
		gzip SAMakercode.tar
		chmod a+r SAMakercode.tar.gz

.PRECIOUS:	%.d

tute.html:	tute.d
		-mkdir $(LESSONS)/tute.dbs
		chmod 770 $(LESSONS)/tute.dbs
		tutemaker $(LESSONS)/tute.dbs $(LESSONS)/$@ $(LESSONS)/tute-stats.html < $(LESSONS)/tute.d/$@
		chmod 644 $(LESSONS)/$@ $(LESSONS)/tute-stats.html
		chgrp -R cgiperl $(LESSONS)/tute.dbs
		chmod 660 $(LESSONS)/tute.dbs/*

tute.d: 		tute.tex
		latex2html -split 0 -info 0 -address '' -dir $(LESSONS)/$@ $?
		chmod -R go+rX $(LESSONS)/$@

tute.clean:
		rm -rf $(LESSONS)/tute.d $(LESSONS)/tute.html $(LESSONS)/tute-stats.html $(LESSONS)/tute.dbs
