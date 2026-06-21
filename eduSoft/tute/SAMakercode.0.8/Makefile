#=============begin configuration=============
# Configuration is ONLY FOR MAKING THE EXAMPLE!!!  You do not need to change
# the Makefile to install SAMaker

# This is the directory where the databases and pages built by SAMaker 
# will live (must be a hard link!!!)
EXAMPLEDIR = /world/www/ftp/users/CDyreson/web/htmls/software/tute/myinstall

# where does SAMaker live (here we assume it is not in our command path)?
SAMAKER = SAMaker

# name of the created entry page to example 
TUTEPAGE = $(EXAMPLEDIR)/example.html

# name of the created example statistcal package entry page
STATSPAGE = $(EXAMPLEDIR)/example-stats.html

# what chmod to use so the WWW can read the pages/databases
CHMOD = chmod 

# what chgrp to use so the WWW can read the pages/databases
CHGRP = chgrp -R cgiperl
#=============end configuration=============

install:
	SAMakerinstall

example:	
		-mkdir $(EXAMPLEDIR)/example.dbs
		$(CHMOD) 770 $(EXAMPLEDIR)/example.dbs
		$(SAMAKER) $(EXAMPLEDIR)/example.dbs $(TUTEPAGE) $(STATSPAGE) < example.tex
		$(CHMOD) 644 $(TUTEPAGE) $(STATSPAGE)
		$(CHGRP) $(EXAMPLEDIR)/example.dbs
		$(CHMOD) 660 $(EXAMPLEDIR)/example.dbs/*

clean:
	rm -rf marker starter stats SAMaker next $(TUTEPAGE) $(STATSPAGE) example.dbs 
