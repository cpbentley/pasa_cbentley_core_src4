/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.io;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Same as {@link BADataIS} but with int inverted.
 * Wierd use case but that's how it is.
 * @author Charles Bentley
 *
 */
public class BADataISInverted extends BADataIS implements DataInput, IStringable {

   /**
    * Creates a DataInputStream that uses the specified
    * underlying InputStream.
    * @param uc 
    * @param  in   the specified input stream
    */
   public BADataISInverted(UCtx uc, BAByteIS in) {
      super(uc,in);
   }

   /**
    * Create a {@link BAByteIS} from {@link BADataOS}
    * @param uc 
    * @param out
    */
   public BADataISInverted(UCtx uc, BADataOS out) {
      super(uc,out);
   }

   /**
    * 
    * @throws IllegalStateException if called with not enough data
    */
   public int readInt() {
      int ch4 = in.read();
      int ch3 = in.read();
      int ch2 = in.read();
      int ch1 = in.read();
      return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
   }

   public int readInt24() {
      int ch3 = in.read();
      int ch2 = in.read();
      int ch1 = in.read();
      return ((ch1 << 16) + (ch2 << 8)) + (ch3 << 0);
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "BADataISInverted");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BADataISInverted");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
