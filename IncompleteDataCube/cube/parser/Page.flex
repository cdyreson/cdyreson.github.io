%{
/* flex source for lexical analyzer, automatically generated */
%}
%%
^.*$ { printf("301 "); } REJECT
"/cgi-bin/" { printf("309 "); } REJECT
"/cgi-bin/perl/""icon/"?"tute".*"/starter" { printf("304 "); } REJECT
"/cgi-bin/perl/""icon/"?"tute".*"/marker" { printf("305 "); } REJECT
"/cgi-bin/""icon/"?"tute".*"/stats" { printf("306 "); } REJECT
"/cgi-bin/""icon/"?"tute".*"/next" { printf("307 "); } REJECT
"/cgi-bin/""icon/"?"tute".*"/skip" { printf("308 "); } REJECT
"/cp1500/1995" { printf("313 "); } REJECT
"/cp1500/1996" { printf("314 "); } REJECT
"/cp1500/1997" { printf("315 "); } REJECT
"/cp2000/1995" { printf("317 "); } REJECT
"/cp2001/1996" { printf("318 "); } REJECT
"/cp2001/1997" { printf("319 "); } REJECT
"/cp2003/1996" { printf("320 "); } REJECT
"/cp2003/1997" { printf("321 "); } REJECT
"/cp1010/1997" { printf("322 "); } REJECT
" / HT" { printf("332 "); } REJECT
" /~curtis"(\/|"/index.html")?" HT" { printf("327 "); } REJECT
"curtis/""htmls/"?"Incomplete" { printf("326 "); } REJECT
" /~olivier"(\/|"/index.html")?" HT" { printf("329 "); } REJECT
" /~tony"(\/|"/index.html")?" HT" { printf("331 "); } REJECT
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
