# sh pdf2ps.sh j.pdf j.ps
acroread -toPostScript <$1 >$2 
#cat asample1.pdf | acroread -toPostscript > sample.ps 
