#set xdata time
#set timefmt "%y"
#set xrange [ "1976":"2001" ]
set xrange [ 1977:2003 ]
set yrange [ 0:45 ]
#set format x "%y"
set key left
set function style lines
#set terminal x11
set data style lines
set xlabel "Year"
set ylabel "Average End-to-End Time in Months per Volume"
set terminal postscript eps
plot 'etoe.dat' using 1:2 t '' with lines lw 3

