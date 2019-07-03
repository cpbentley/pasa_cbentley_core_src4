package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.structs.IntToStrings;

public class URLUtils {

   private UCtx uc;

   public URLUtils(UCtx uc) {
      this.uc = uc;

   }

   /**
    * http://www.something.com/images/01.jpg will return 
    * http://www.something.biz/images/
    * @param url
    * @return
    */
   public String getURLRootFrom(String url) {
      int indexLastSlash = url.lastIndexOf('/');
      if (indexLastSlash != -1) {
         return url.substring(0, indexLastSlash + 1);
      }
      return url;
   }

   /**
    * extract all urls starting with href=
    * @param line
    * @return
    */
   public String[] extractHREFURL(String line) {
      int start = 0;
      int index = -1;
      IntToStrings ito = new IntToStrings(uc);
      while ((index = line.indexOf("href=", start)) != -1) {
         int indexDelim = index + 5;
         char delim = line.charAt(indexDelim);
         int endOfURL = line.indexOf(delim, indexDelim + 1);
         if (endOfURL != -1) {
            String url = line.substring(indexDelim + 1, endOfURL);
            ito.add(url);
            start = endOfURL + 1;
         } else {
            break;
         }
      }
      return ito.getStrings();
   }

   /**
    * <li>src="http://image"
    * <li>src='http://image'
    * 
    * @param line
    * @return
    */
   public String[] extractHttpURL(String line) {
      int start = 0;
      int index = -1;
      IntToStrings ito = new IntToStrings(uc);
      while ((index = line.indexOf("http://", start)) != -1) {
         char delim = line.charAt(index - 1);
         int endOfURL = line.indexOf(delim, index + 1);
         if (endOfURL != -1) {
            String url = line.substring(index, endOfURL);
            ito.add(url);
            start = endOfURL + 1;
         } else {
            break;
         }
      }
      return ito.getStrings();
   }

   /**
    * URL extraction in an HTML document with http and .jpg type at the end
    * Must be able to deal with 
    * <li>src="http://image.png"
    * <li>src='http://image.png'
    * <li>src=http://image.png
    * 
    * <br>
    * The Url must start with http://
    * <br>
    * @param line
    * @param type
    * @return
    */
   public String[] extractHttpURL(String line, String type) {
      int start = 0;
      int index = -1;
      IntToStrings ito = new IntToStrings(uc);
      int typeLen = type.length();
      while ((index = line.indexOf("http://", start)) != -1) {
         int endOfURL = line.indexOf(type, index + 1);
         if (endOfURL != -1) {
            String url = line.substring(index, endOfURL + typeLen);
            ito.add(url);
            start = endOfURL + typeLen;
         } else {
            break;
         }
      }
      return ito.getStrings();
   }

   /**
    * Extract all Strings inside the line String which are surrounded
    * by the String around.
    * <br>
    * 
    * @param line
    * @param around
    * @return
    */
   public String[] extractURL(String line, String around) {
      int start = 0;
      int index = -1;
      IntToStrings ito = new IntToStrings(uc);
      while ((index = line.indexOf(around, start)) != -1) {
         int indexDelim = index + around.length();
         char delim = line.charAt(indexDelim); //usually the " after href=
         int endOfURL = line.indexOf(delim, indexDelim + 1);
         if (endOfURL != -1) {
            String url = line.substring(indexDelim + 1, endOfURL);
            ito.add(url);
            start = endOfURL + 1;
         } else {
            break;
         }
         //read the title
      }
      return ito.getStrings();
   }

   public String[] extractURLWithTitle(String line, String around) {
      int start = 0;
      int index = -1;
      IntToStrings ito = new IntToStrings(uc);
      while ((index = line.indexOf(around, start)) != -1) {
         int indexDelim = index + around.length();
         char delim = line.charAt(indexDelim); //usually the " after href=
         int endOfURL = line.indexOf(delim, indexDelim + 1);
         if (endOfURL != -1) {
            String url = line.substring(indexDelim + 1, endOfURL);
            ito.add(url);
            start = endOfURL + 1;
            String title = "";
            //read the title
            int indexEnd = line.indexOf('>', endOfURL);
            if (indexEnd != -1) {
               int indexEnd2 = line.indexOf('<', indexEnd);
               if (indexEnd2 != -1) {
                  title = line.substring(indexEnd + 1, indexEnd2);
               }
            }
            ito.add(title);
         } else {
            break;
         }
      }
      return ito.getStrings();
   }
}
