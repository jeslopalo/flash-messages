package es.sandbox.ui.messages.resolver;


public class StringFormatMessageResolverStrategy
      implements MessageResolverStrategy {

   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.resolver.MessageResolverStrategy#resolve(java.lang.String, java.lang.Object[])
    */
   @Override
   public String resolve(String code, Object... arguments) {
      return String.format(code, arguments);
   }
}
