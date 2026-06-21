/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xmorph.edu.usu.evaluation.xquery;

/**
 *
 * @author Kiran
 */
public class XQueryQuery {
public String forStat,retStat,hiddenChildPath;
public boolean hidden;
public XQueryQuery()
{
  forStat = "";
  retStat = "";
  hiddenChildPath = "";
}
public void extendQuery(XQueryQuery q)
{
    forStat = forStat+"\n"+q.forStat;
    retStat = retStat+"\n"+q.retStat;
    if(hiddenChildPath.contentEquals(""))
      hiddenChildPath = q.hiddenChildPath;
    else
      hiddenChildPath = hiddenChildPath+" and "+q.hiddenChildPath;
}
public void extendHiddenPath(String path)
{
    if(hiddenChildPath.contentEquals(""))
      hiddenChildPath = path;
    else
      hiddenChildPath   = hiddenChildPath+" and "+path;
}
public void extendHiddenPathWithWhere(String path,String whereValue)
{
    if(hiddenChildPath.contentEquals(""))
      hiddenChildPath = path+"/text() ="+whereValue;
    else
      hiddenChildPath   = hiddenChildPath+" and "+path+"/text() ="+whereValue;
}
public void extendGroupReturn(String qName,String pvar)
{
     retStat = "<"+qName+"> {$"+pvar+"/text()}"+"\n {"+forStat+"\n return"+retStat+"} \n </"+qName+">";
}
public void extendGroupFor(String presVar,String prevVar,String path)
{
  if(prevVar != null)
  { forStat = "for $"+presVar+"G in distinct-values ( $"+prevVar+"/"+path+") \n"+
               "let $"+presVar+" := "+path+"[.=$"+presVar+"G]";
  }
  else
    forStat = "for $"+presVar+" in distinct-values ("+path+") \n"+
               "let $"+presVar+" := "+path+"[.=$"+presVar+"G]";
}
public void extendGroupForWithPredicate(String presVar,String prevVar,String path,String predicate)
{
     if(prevVar != null)
        forStat = "for $"+presVar+"G in distinct-values ( $"+prevVar+"/"+path+"["+predicate+"]) \n"+
                  "let $"+presVar+" := "+path+"[.=$"+presVar+"G]";
     else
        forStat = "for $"+presVar+"G in distinct-values ("+path+"["+predicate+"])  \n"+
                   "let $"+presVar+" := "+path+"[.=$"+presVar+"G]";
}
public void extendFor(String presVar,String prevVar,String path)
{
    if(prevVar != null)
      forStat = "for $"+presVar+" in $"+prevVar+"/"+path+" \n"+forStat;
           else
      forStat = "for $"+presVar+" in "+path+" \n"+forStat;
}
public void extendForWithPredicate(String presVar,String prevVar,String path,String predicate)
{
   if(prevVar != null)
      forStat = "for $"+presVar+" in $"+prevVar+"/"+path+"["+predicate+"] \n"+forStat;
   else
      forStat = "for $"+presVar+" in "+path+"["+predicate+"] \n"+forStat;
}
public void extendReturn(String qName,String presVar)
{
    retStat = "<"+qName+"> {$"+presVar+"/text()} \n"+retStat+" \n <"+qName+">";
}
public String returnQuery()
{
    String query = forStat+"\n return \n"+retStat;
    return query;
}
}