BIN = ~cdyreson/bin/perl
WEBDIR = webpages
FINALWEBDIR = finalwebpages
CGIDIR = /net/zeus/facstaff/cdyreson/public_html/cgi-bin/perl/upload

all: top subdirectories
top: 30PercentRulePolicy.htm AECOIPolicy.htm AssociateEditorsPolicy.htm Authors.htm Charter.htm CharterPolicy.htm COI.htm DoubleBlindPolicy.htm EDBTPolicy.htm Editorials.htm Editors.htm History.htm ICDTPolicy.htm index.htm InternetAge.htm InvitedPapersPolicy.htm ManuscriptSubmissionPolicy.htm PageLengthPolicy.htm PeopleHistory.htm PODSPolicy.htm Policies.htm PreviousEditorials.htm Referees.htm reviewerguidelines.htm ReviewerRights.htm ReviewerRightsPolicy.htm ReviewFormPolicy.htm SelfPlagiarismPolicy.htm SelfPlagiarism_v1.htm SelfPlagiarism_v2.htm ShortPapersPolicy.htm SIGMODPolicy.htm SiteHistory.htm Statistics.htm SubmitByCoRR.htm Subscription.htm SuccessorPapersPolicy.htm SurveyPapersPolicy.htm TurnaroundTimePolicy.htm Upcoming.htm

final: index.fin Authors.fin COI.fin Charter.fin Editors.fin Referees.fin Subscription.fin Upcoming.fin SubmitByCorr.fin

editorialssub: TODSReviewers.htm

editorssub: index.htm SurveyPapers.htm 30PercentRule.htm ReviewForm.htm ShortPapers.htm StateOfTODS.htm SIGMOD.htm PODS.htm EDBT.htm AECOI.htm ReviewerRights.htm ICDT.htm SelfPlagiarism.htm Statistics.htm SuccessorPapers.htm

subdirectories:
	cd editors; make editorssub
	cd editorials; make editorialssub

%.pl:
	$(BIN)/htmlifyer -nop $* >$(CGIDIR)/$*.pl
	perl -w postprocess.pl -directory $(CGIDIR) -fontcolor FFFFFF -color 4444FF -page $*.pl
	chmod u+x $(CGIDIR)/$*.pl

%.fin:
	$(BIN)/htmlifyer $* >$(FINALWEBDIR)/$*.html
	#tidy -f /tmp/tidyerror.txt -wrap 0 -indent -quiet -m $(FINALWEBDIR)/$*.html
	perl -w postprocess.pl -directory $(FINALWEBDIR) -fontcolor FFFFFF -color 4444FF -page $*.html
	chmod -R a+r $(FINALWEBDIR)/$*.html

%.htm:
	$(BIN)/htmlifyer $* >$(WEBDIR)/$*.html
	#tidy -f /tmp/tidyerror.txt -wrap 0 -indent -quiet -m $(WEBDIR)/$*.html
	perl -w postprocess.pl -directory $(WEBDIR) -fontcolor FFFFFF -color 4444FF -page $*.html
	chmod -R a+r $(WEBDIR)/$*.html

%.graphics:
	$(BIN)/htmlifyer -graphics $* >$(WEBDIR)/$*.html
	chmod -R a+r $(WEBDIR)/$*.html
	chmod a+r $(WEBDIR)/images/*

%.clean:
	rm -rf $(LESSONSDIR)/$*.dbs $(WEBDIR)/$*.html

clean: 
	rm -f idraw/*.ppm idraw/*.tmp *.log *.dvi *.ps *.aux
