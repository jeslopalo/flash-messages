package es.sandbox.ui.messages;



public interface Messages {

   /**
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addSuccess(String code, Object... arguments);

   /**
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addInfo(String code, Object... arguments);

   /**
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addWarning(String code, Object... arguments);

   /**
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addError(String code, Object... arguments);

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
