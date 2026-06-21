# Configure only for making the example ============= start
# This is the directory where the databases and pages built by QuizMaker
# will live (must be a hard link!!!)
EXAMPLEDIR = examples
PAGESDIR = pages
#EXAMPLEDIR = /net/zeus/facstaff/cdyreson/public_html/pub/eduSoft/quiz/myinstall
# name of the example entry page
# where does QuizMaker live?
QUIZMAKER = ~cdyreson/bin/perl/QuizMaker
# name of the example quiz entry page
ENTRYPAGE = $(PAGESDIR)/example.html
# name of the example grader package entry page
STATSPAGE = $(PAGESDIR)/example-stats.html
# name of the file for the passwords generated for the example quiz
PASSWORDS = $(EXAMPLEDIR)/passwords
# what chmod to use so the WWW can read the pages/databases
CHMOD = chmod 
# Configure only for making the example ============= end

install:
	perl -w QuizMakerinstall

example:	
		-mkdir $(EXAMPLEDIR)/example.dbs
	#	$(CHMOD) 770 $(EXAMPLEDIR)/example.dbs
		$(QUIZMAKER) $(EXAMPLEDIR)/example.dbs $(ENTRYPAGE) $(STATSPAGE) $(PASSWORDS) < example.tex
		$(CHMOD) 644 $(TUTEPAGE) $(STATSPAGE)
	#	$(CHMOD) 660 $(EXAMPLEDIR)/example.dbs/*

clean:
	rm -rf poster reviewer marker starter stats QuizMaker $(TUTEPAGE) $(STATSPAGE) example.dbs

