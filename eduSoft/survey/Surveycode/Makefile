# Configure only for making the example ============= start
# This is the directory where the databases and pages built by SurveyMaker
# will live (must be a hard link!!!!!!)
EXAMPLEDIR = /world/www/ftp/users/CDyreson/web/htmls/software/survey/myinstall
# name of the example entry page
# where does SurveyMaker live?
SURVEYMAKER = SurveyMaker
TUTEPAGE = $(EXAMPLEDIR)/example.html
# name of the example statistcal package entry page
STATSPAGE = $(EXAMPLEDIR)/example-stats.html
# what chmod to use so the WWW can read the pages/databases
CHMOD = chmod 
# what chgrp to use so the WWW can read the pages/databases
CHGRP = chgrp -R cgiperl
# Configure only for making the example ============= end

install:
	SurveyMakerinstall

example:	
		-mkdir $(EXAMPLEDIR)/example.dbs
		$(CHMOD) 770 $(EXAMPLEDIR)/example.dbs
		$(SURVEYMAKER) $(EXAMPLEDIR)/example.dbs $(TUTEPAGE) $(STATSPAGE) < example.tex
		$(CHMOD) 644 $(TUTEPAGE) $(STATSPAGE)
		$(CHGRP) $(EXAMPLEDIR)/example.dbs
		$(CHMOD) 660 $(EXAMPLEDIR)/example.dbs/*

clean:
	rm -rf poster reviewer marker starter stats SurveyMaker $(TUTEPAGE) $(STATSPAGE) example.dbs

