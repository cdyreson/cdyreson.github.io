%{
/* flex source for lexical analyzer, automatically generated */
%}
%%
"/Jan/1997" { printf("265 "); } REJECT
"/Feb/1997" { printf("266 "); } REJECT
"/Mar/1997" { printf("267 "); } REJECT
"/Apr/1997" { printf("268 "); } REJECT
"/May/1997" { printf("269 "); } REJECT
"/Jun/1997" { printf("270 "); } REJECT
"/Jul/1997" { printf("271 "); } REJECT
"/Aug/1997" { printf("272 "); } REJECT
"/Sep/1997" { printf("273 "); } REJECT
"/Oct/1997" { printf("274 "); } REJECT
"/Nov/1997" { printf("275 "); } REJECT
"/Dec/1997" { printf("276 "); } REJECT
"/Jan/1996" { printf("277 "); } REJECT
"/Feb/1996" { printf("278 "); } REJECT
"/Mar/1996" { printf("279 "); } REJECT
"/Apr/1996" { printf("280 "); } REJECT
"/May/1996" { printf("281 "); } REJECT
"/Jun/1996" { printf("282 "); } REJECT
"/Jul/1996" { printf("283 "); } REJECT
"/Aug/1996" { printf("284 "); } REJECT
"/Sep/1996" { printf("285 "); } REJECT
"/Oct/1996" { printf("286 "); } REJECT
"/Nov/1996" { printf("287 "); } REJECT
"/Dec/1996" { printf("288 "); } REJECT
"/Jan/1995" { printf("289 "); } REJECT
"/Feb/1995" { printf("290 "); } REJECT
"/Mar/1995" { printf("291 "); } REJECT
"/Apr/1995" { printf("292 "); } REJECT
"/May/1995" { printf("293 "); } REJECT
"/Jun/1995" { printf("294 "); } REJECT
"/Jul/1995" { printf("295 "); } REJECT
"/Aug/1995" { printf("296 "); } REJECT
"/Sep/1995" { printf("297 "); } REJECT
"/Oct/1995" { printf("298 "); } REJECT
"/Nov/1995" { printf("299 "); } REJECT
"/Dec/1995" { printf("300 "); } REJECT
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
