package es.sandbox.ui.messages;

import es.sandbox.ui.messages.resolver.MessageResolverStrategy;


/**
 * Allow controllers to publish flash messages.
 * 
 * @author jeslopalo
 * @since v0.1
 */
public interface Flash {

   /**
    * Publish a new success message. It will be resolved
    * using the configured {@link MessageResolverStrategy}
    * 
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void success(String code, Object... arguments)
         throws NullPointerException, IllegalArgumentException;

   /**
    * Publish a new info message. It will be resolved
    * using the configured {@link MessageResolverStrategy}
    * 
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void info(String code, Object... arguments)
         throws NullPointerException, IllegalArgumentException;

   /**
    * Publish a new warning message. It will be resolved
    * using the configured {@link MessageResolverStrategy}
    * 
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void warning(String code, Object... arguments)
         throws NullPointerException, IllegalArgumentException;

   /**
    * Publish a new error message. It will be resolved
    * using the configured {@link MessageResolverStrategy}
    * 
    * @param code
    * @param arguments
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   void error(String code, Object... arguments)
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
