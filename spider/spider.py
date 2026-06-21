import urllib, urlparse, string, time, htmllib, formatter, sys

class Parser(htmllib.HTMLParser):

        def __init__(self, verbose=0):
                self.anchors = {}
                f = formatter.NullFormatter()
                htmllib.HTMLParser.__init__(self, f, verbose)

        def anchor_bgn(self, href, name, type):
                self.save_bgn()
                self.anchor = href

        def anchor_end(self):
                text = string.strip(self.save_end())
                if self.anchor and text:
                        self.anchors[text] = self.anchors.get(text, []) + [self.anchor]

url = sys.argv[1]
prefix = sys.argv[2]
pageLimit = sys.argv[3]

print pageLimit
otherLinks = ""

#visit up to pagelimit urls and print the links of that page
#for i in range(0, int(pageLimit)):
for i in range(0, 3):
	print ""
	print "Visiting Page number ", i+1, " ", url
	print ""

	oldurl = url

	s = urllib.urlretrieve(url)

	file = open(s[0])
	html = file.read()
	file.close()

	p = Parser()
	p.feed(html)
	p.close()

	print "Links on the page"
	#search through the links on the page
	for k, v in p.anchors.items():
		print k, "=>", v
		for link in v:
			if link.startswith(prefix):
				if url == oldurl:
					#change the next url
					url = link
				else:
					#save links that havnt been visited
					otherLinks = link + " " + otherLinks
	#if no valid links on the page get a saved off link
	if oldurl == url:
		print "No valid links on this page"
		words = otherLinks[:-1].split()
		for word in words:
			url = word
			otherLinks.replace(word, '', 1)
			break
	if oldurl == url:
		print "Out of Valid Links"
		break
		

# w = formatter.DumbWriter()
# f = formatter.AbstractFormatter(w)
# p = htmllib.HTMLParser(f)
# v = p.feed(html).split()
# p.close()
