package es.sandbox.ui.messages;

import java.io.Serializable;


public interface Messages {

   /**
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addSuccess(String code, Serializable... arguments);

   /**
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addInfo(String code, Serializable... arguments);

   /**
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addWarning(String code, Serializable... arguments);

   /**
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addError(String code, Serializable... arguments);

   /**
    * Clear all {@link Level} messages
    */
   void clear();

   /**
    * Return true if there are not messages in any level
    * 
    * @return
    */
   boolean isEmpty();
}
