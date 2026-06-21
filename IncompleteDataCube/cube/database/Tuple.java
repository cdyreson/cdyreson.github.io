package cube.database;

import java.io.*;
import cube.*;

/**
*  A Tuple is a binary relationship between a key column and a data column.
*  The key and data must be valid column types.
* <P>
* For more information on the cube see the cube
* <A HREF="cube.Overview.html">Overview</A>.
* <BR>
* Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
* Please be aware of the
* <A HREF="cube.Licence.html">Licence</A>
* and
* <A HREF="cube.Version.html">Version</A>.
*
* @see cube.database.Table
* @author Curtis Dyreson and Jason Pountney
**/
public class Tuple {
  private byte[] key;
  private byte[] value;

  /**
  * Create a tuple with the given key/data values.
  */
  public Tuple(byte[] key, byte[] value) {
    this.key = key;
    this.value = value;
    }

  /**
  * Create a tuple with the given key/data values.
  */
  public Tuple(IdList key, IntegerCol value) {
    this.key = key.toBytes();
    this.value = value.toBytes();
    }

  /**
  * Create a tuple with the given key/data values.
  */
  public Tuple(Id key, Id value) {
    this.key = key.toBytes();
    this.value = value.toBytes();
    }

  /**
  * Create a tuple with the given key/data values.
  */
  public Tuple(Id key, IdList value) {
    this.key = key.toBytes();
    this.value = value.toBytes();
    }

  /**
  * Create a tuple with the given key/data values.
  */
  public Tuple(StringCol key, Id value) {
    this.key = key.toBytes();
    this.value = value.toBytes();
    }

  /**
  * Create a tuple with the given key/data values.
  */
  public Tuple(Id key, StringCol value) {
    this.key = key.toBytes();
    this.value = value.toBytes();
    }

  /**
  * Create a tuple with the given key/data values.
  */
  public Tuple(Id key, IdSet value) {
    this.key = key.toBytes();
    this.value = value.toBytes();
    }

  /**
  * Create a tuple with the given key, data is null.
  */
  public Tuple(Id key) {
    this.key = key.toBytes();
    this.value = null;
    }

  /**
  * Create a tuple with the given key, data is null.
  */
  public Tuple(StringCol key) {
    this.key = key.toBytes();
    this.value = null;
    }

  /**
  * Create a tuple with the given key, data is null.
  */
  public Tuple(IdSet key) {
    this.key = key.toBytes();
    this.value = null;
    }

  /**
  * Create a tuple with the given key, data is null.
  */
  public Tuple(IdList key) {
    this.key = key.toBytes();
    this.value = null;
    }

  /**
  * Update the value field in a Tuple.
  */
  public void updateValue(byte[] value) {
    this.value = value;
    }

  /**
  * Retrieve the key as a byte image.
  */
  public byte[] getKeyAsBytes() { return key; }
  /**
  * Retrieve the value as a byte image.
  */
  public byte[] getValueAsBytes() { return value; }
  /**
  * Retrieve the key as an Id.
  */
  public Id getKeyAsId() { return new Id(key); }
  /**
  * Retrieve the key as an IntegerCol.
  */
  public IntegerCol getKeyAsIntegerCol() { return new IntegerCol(key); }
  /**
  * Retrieve the key as a StringCol.
  */
  public StringCol getKeyAsStringCol() { return new StringCol(key); }
  /**
  * Retrieve the key as a String.
  */
  public String getKeyAsString() { StringCol temp = new StringCol(key); 
                                   return temp.toRealString(); }
  /**
  * Retrieve the key as an IdList.
  */
  public IdList getKeyAsIdList() { return new IdList(key); }
  /**
  * Retrieve the key as an IdSet.
  */
  public IdSet getKeyAsIdSet() { return new IdSet(key); }
  /**
  * Retrieve the value as an Id.
  */
  public Id getValueAsId() { return new Id(value);}
  /**
  * Retrieve the value as an IntegerCol.
  */
  public IntegerCol getValueAsIntegerCol() { return new IntegerCol(value); }
  /**
  * Retrieve the value as an StringCol.
  */
  public StringCol getValueAsStringCol() { return new StringCol(value); }
  /**
  * Retrieve the value as a String.
  */
  public String getValueAsString() { StringCol temp = new StringCol(value); 
                                     return temp.toRealString(); }
  /**
  * Retrieve the value as an IdSet.
  */
  public IdSet getValueAsIdSet() { return new IdSet(value); }
  /**
  * Retrieve the value as an IdList.
  */
  public IdList getValueAsIdList() { return new IdList(value);}

}
