package es.sandbox.ui.messages;

import es.sandbox.ui.messages.resolver.MessageResolverStrategy;


/**
 * Allow controllers to publish flash messages.
 * 
 * @author jeslopalo
 * @since v0.1
 */
public interface Messages {

   /**
    * Publish a new success message. It will be resolve
    * using the configured {@link MessageResolverStrategy}
    * 
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addSuccess(String code, Object... arguments)
         throws NullPointerException, IllegalArgumentException;

   /**
    * Publish a new info message. It will be resolve
    * using the configured {@link MessageResolverStrategy}
    * 
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addInfo(String code, Object... arguments)
         throws NullPointerException, IllegalArgumentException;

   /**
    * Publish a new warning message. It will be resolve
    * using the configured {@link MessageResolverStrategy}
    * 
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addWarning(String code, Object... arguments)
         throws NullPointerException, IllegalArgumentException;

   /**
    * Publish a new error message. It will be resolve
    * using the configured {@link MessageResolverStrategy}
    * 
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void addError(String code, Object... arguments)
         throws NullPointerException, IllegalArgumentException;

   /**
    * Clear all messages from all {@link Level}s
    */
   void clear();

   /**
    * Check whether there are no messages
    * 
    * @return
    */
   boolean isEmpty();
}
