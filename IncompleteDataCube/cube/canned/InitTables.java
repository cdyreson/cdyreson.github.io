package w3c.tools.dbm;
import java.util.*;
import cube.tools.*;
public class InitTables {
  public static Hashtable whichInit(String s) {

    if (s.compareTo("/filters") == 0) {return init1();}
    if (s.compareTo("/filterUnits") == 0) {return init2();}
    if (s.compareTo("/filterMeasures") == 0) {return init3();}
    if (s.compareTo("/count") == 0) {return init4();}
    if (s.compareTo("/ids") == 0) {return init5();}
    if (s.compareTo("/strings") == 0) {return init6();}
    if (s.compareTo("/MachinecoarserUnits") == 0) {return init7();}
    if (s.compareTo("/MachinefinerUnits") == 0) {return init8();}
    if (s.compareTo("/MachinecoarserMeasures") == 0) {return init9();}
    if (s.compareTo("/MachinefinerMeasures") == 0) {return init10();}
    if (s.compareTo("/MachineunitToMeasure") == 0) {return init11();}
    if (s.compareTo("/Machinemeasures") == 0) {return init12();}
    if (s.compareTo("/TimecoarserUnits") == 0) {return init13();}
    if (s.compareTo("/TimefinerUnits") == 0) {return init14();}
    if (s.compareTo("/TimecoarserMeasures") == 0) {return init15();}
    if (s.compareTo("/TimefinerMeasures") == 0) {return init16();}
    if (s.compareTo("/TimeunitToMeasure") == 0) {return init17();}
    if (s.compareTo("/Timemeasures") == 0) {return init18();}
    if (s.compareTo("/PagecoarserUnits") == 0) {return init19();}
    if (s.compareTo("/PagefinerUnits") == 0) {return init20();}
    if (s.compareTo("/PagecoarserMeasures") == 0) {return init21();}
    if (s.compareTo("/PagefinerMeasures") == 0) {return init22();}
    if (s.compareTo("/PageunitToMeasure") == 0) {return init23();}
    if (s.compareTo("/Pagemeasures") == 0) {return init24();}
   Internal.Error("Died opening " + s);
   return null;
   } // end of whichInit
  public static Hashtable init1() {
    Hashtable j = new Hashtable();
    String key; 
