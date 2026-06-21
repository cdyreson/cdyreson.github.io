#=============begin configuration=============
# Configuration is ONLY FOR MAKING THE EXAMPLE!!!  You do not need to change
# the Makefile to install SAMaker

# This is the directory where the databases and pages built by SAMaker 
# will live (must be a hard link!!!)
EXAMPLEDIR = /world/www/ftp/users/OldStaff/CDyreson/web/htmls/software/tute/myinstall

# where does SAMaker live (here we assume it is not in our command path)?
SAMAKER = /home/curtis/bin/perl/SAMaker

# where does LessonMaker live (here we assume it is not in our command path)?
LESSONMAKER = /home/curtis/bin/perl/LessonMaker

# name of the created entry page to example 
TUTEPAGE = $(EXAMPLEDIR)/example.html

# name of the created example statistcal package entry page
STATSPAGE = $(EXAMPLEDIR)/example-stats.html

# what chmod to use so the WWW can read the pages/databases
CHMOD = chmod -f

# what chgrp to use so the WWW can read the pages/databases
CHGRP = chgrp -fR cgiperl
#=============end configuration=============

install:
	SAMakerinstall

example:	
		-mkdir $(EXAMPLEDIR)/example.dbs
		$(CHMOD) 770 $(EXAMPLEDIR)/example.dbs
		$(SAMAKER) \
			-database $(EXAMPLEDIR)/example.dbs \
			-entry $(TUTEPAGE) \
			-stats $(STATSPAGE) \
			< example.tex
		$(CHMOD) 644 $(TUTEPAGE) $(STATSPAGE)
		$(CHGRP) $(EXAMPLEDIR)/example.dbs
		$(CHMOD) 660 $(EXAMPLEDIR)/example.dbs/*

lesson:		
		-mkdir $(EXAMPLEDIR)/lesson.dbs
		$(LESSONMAKER) \
           		-embed \
           		-database $(EXAMPLEDIR)/lesson.dbs \
           	-lesson lesson.html < lesson.tex
		$(CHGRP) $(EXAMPLEDIR)/lesson.dbs
		chmod -fR 770 $(EXAMPLEDIR)/lesson.dbs

germanexample:	
		-mkdir $(EXAMPLEDIR)/germanexample.dbs
		$(CHMOD) 770 $(EXAMPLEDIR)/germanexample.dbs
		$(SAMAKER) \
			-database $(EXAMPLEDIR)/germanexample.dbs \
			-entry $(EXAMPLEDIR)/germanexample.html \
			-stats $(EXAMPLEDIR)/germanexample-stats.html \
			< germanexample.tex
		$(CHMOD) 644 $(TUTEPAGE) $(STATSPAGE)
		$(CHGRP) $(EXAMPLEDIR)/germanexample.dbs
		$(CHMOD) 660 $(EXAMPLEDIR)/germanexample.dbs/*

cleanexample:
	rm -rf $(TUTEPAGE) $(STATSPAGE) $(EXAMPLEDIR)/example.dbs 

