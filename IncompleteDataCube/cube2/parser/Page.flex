%{
/* flex source for lexical analyzer, automatically generated */
%}
%%
^.*$ { printf("1782 "); } REJECT
"/cp1500/1995" { printf("1850 "); } REJECT
"/cp1500/1996" { printf("1851 "); } REJECT
"/cp1500/1997" { printf("1852 "); } REJECT
"/cp1500/1998" { printf("1853 "); } REJECT
"/cp2000/1995" { printf("1855 "); } REJECT
"/cp2001/1996" { printf("1856 "); } REJECT
"/cp2001/1997" { printf("1857 "); } REJECT
"/cp2001/1998" { printf("1858 "); } REJECT
"/cp2050a/1995" { printf("1860 "); } REJECT
"/cp2003/1996" { printf("1861 "); } REJECT
"/cp2003/1997" { printf("1862 "); } REJECT
"/cp2003/1998" { printf("1863 "); } REJECT
"/cp1010/1997" { printf("1864 "); } REJECT
"/cp1010/1998" { printf("1864 "); } REJECT
" / HT" { printf("1873 "); } REJECT
" /~curtis"(\/|"/index.html")?" HT" { printf("1868 "); } REJECT
"curtis/""htmls/"?"Incomplete" { printf("1867 "); } REJECT
" /~olivier"(\/|"/index.html")?" HT" { printf("1870 "); } REJECT
" /~tony"(\/|"/index.html")?" HT" { printf("1872 "); } REJECT
"/cgi-bin/icon/starter" { printf("1802 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/starter" { printf("1801 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1006/starter" { printf("1792 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1030/starter" { printf("1793 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1200/starter" { printf("1794 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1300/starter" { printf("1795 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1500/starter" { printf("1796 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2001/starter" { printf("1797 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2003/starter" { printf("1798 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2050a/starter" { printf("1799 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp3020/starter" { printf("1800 "); } REJECT
"/cgi-bin/icon/marker" { printf("1813 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/marker" { printf("1812 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1006/marker" { printf("1803 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1030/marker" { printf("1804 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1200/marker" { printf("1805 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1300/marker" { printf("1806 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1500/marker" { printf("1807 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2001/marker" { printf("1808 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2003/marker" { printf("1809 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2050a/marker" { printf("1810 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp3020/marker" { printf("1811 "); } REJECT
"/cgi-bin/icon/nextquestion" { printf("1824 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/next" { printf("1823 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1006/next" { printf("1814 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1030/next" { printf("1815 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1200/next" { printf("1816 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1300/next" { printf("1817 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1500/next" { printf("1818 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2001/next" { printf("1819 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2003/next" { printf("1820 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2050a/next" { printf("1821 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp3020/next" { printf("1822 "); } REJECT
"/cgi-bin/icon/skipquestion" { printf("1835 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/skip" { printf("1834 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1006/skip" { printf("1825 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1030/skip" { printf("1826 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1200/skip" { printf("1827 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1300/skip" { printf("1828 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1500/skip" { printf("1829 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2001/skip" { printf("1830 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2003/skip" { printf("1831 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2050a/skip" { printf("1832 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp3020/skip" { printf("1833 "); } REJECT
"/cgi-bin/icon/stats" { printf("1846 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/stats" { printf("1845 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1006/stats" { printf("1836 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1030/stats" { printf("1837 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1200/stats" { printf("1838 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1300/stats" { printf("1839 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp1500/stats" { printf("1840 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2001/stats" { printf("1841 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2003/stats" { printf("1842 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp2050a/stats" { printf("1843 "); } REJECT
"/cgi-bin/""icon/"?"perl/tute""2"?"/cp3020/stats" { printf("1844 "); } REJECT
\n {printf("0\n");}
. {}
%%
yywrap()
{
return(1);
}
#undef input()
#define input() mlex_input()
#undef unput()
#define unput(c) mlex_unput(c)
