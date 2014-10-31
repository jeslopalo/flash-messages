package es.sandbox.ui.messages.argument;

/**
 * {@link Argument} to render an html link.
 * 
 * @author jeslopalo
 * @since v1.0
 */
public interface Link extends Argument {

   /**
    * Url to complete the <code>href</code> link attribute
    * 
    * @param url
    * @return
    */
   Link url(String url);

   /**
    * i18n code and arguments to complete the text link
    * 
    * @param code
    * @param arguments
    * @return
    */
   Link title(String code, Object... arguments);

   /**
    * {@link Text} to complete the text link
    * 
    * @param text
    * @return
    */
   Link title(Text text);

   /**
    * Optional cssClass to complete the <code>class</code> link attribute
    * 
    * @param cssClass
    * @return
    */
   Link cssClass(String cssClass);
}
