/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

/**
 * Fine grained definition of log entry
 * @author Charles Bentley
 *
 */
public class LogEntryType {

   private int    tagID;

   private int    level;

   private String className;

   private String methodName;

   public int getTagID() {
      return tagID;
   }

   public void setTagID(int tagID) {
      this.tagID = tagID;
   }

   public int getLevel() {
      return level;
   }

   public void setLevel(int level) {
      this.level = level;
   }

   public String getClassName() {
      return className;
   }

   public void setClassName(String className) {
      this.className = className;
   }

   public String getMethodName() {
      return methodName;
   }

   public void setMethodName(String methodName) {
      this.methodName = methodName;
   }
}
