package es.sandbox.ui.messages.tags;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.Message;
import es.sandbox.ui.messages.context.MessagesContext;


public class MessagesTaglibSupport {

   private static final Logger LOGGER= LoggerFactory.getLogger(MessagesTaglibSupport.class);


   /**
    * Private constructor to prevent instances
    * 
    * @throws UnsupportedOperationException
    */
   private MessagesTaglibSupport() throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   /**
    * @param level
    * @param request
    * @return
    */
   public static Collection<Message> levelMessages(Level level, HttpServletRequest request) {
      return levelMessagesFromContext(level, context(request), request);
   }

   private static Collection<Message> levelMessagesFromContext(Level level, MessagesContext context, HttpServletRequest request) {
      if ((context != null) && (level != null)) {
         return context.levelMessages(level, request);
      }

      LOGGER.warn("Level messages can't be accessed!");
      return new ArrayList<Message>();
   }

   private static MessagesContext context(HttpServletRequest request) {
      return request == null? null : (MessagesContext) request.getAttribute(MessagesContext.MESSAGES_CONTEXT_PARAMETER);
   }

   /**
    * @param request
    * @return
    */
   public static Level[] levels(HttpServletRequest request) {

      final MessagesContext context= context(request);
      if (context == null) {
         LOGGER.warn("MessageContext can't be accessed!");
         return new Level[0];
      }
      return context.levels();
   }

   /**
    * @param level
    * @param request
    * @return
    */
   public static String levelCssClass(Level level, HttpServletRequest request) {

      final MessagesContext context= context(request);
      if (context == null) {
         LOGGER.warn("MessageContext can't be accessed!");
         return "";
      }
      return context.getLevelCssClass(level);
   }
}
