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
