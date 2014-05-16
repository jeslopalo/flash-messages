package es.sandbox.ui.messages.argument;


public interface Link extends Argument {

   /**
    * @param url
    * @return
    */
   Link url(String url);

   /**
    * @param code
    * @param arguments
    * @return
    */
   Link title(String code, Object... arguments);

   /**
    * @param text
    * @return
    */
   Link title(Text text);

   /**
    * @param cssClass
    * @return
    */
   Link cssClass(String cssClass);
}
