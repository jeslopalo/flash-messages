package es.sandbox.ui.messages.spring.scope.flash;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import es.sandbox.ui.messages.store.MessagesStore;
import es.sandbox.ui.messages.store.MessagesStoreAccessor;
import es.sandbox.ui.messages.store.MessagesStoreNotFoundException;


final class MessagesStoreFlashScopeAccessor
      implements MessagesStoreAccessor {

   private static final Logger LOGGER= LoggerFactory.getLogger(MessagesStoreFlashScopeAccessor.class);

   private final HttpServletRequest request;
   private final String parameterName;


   /**
    * @param request
    * @param flashParameter
    * @throws NullPointerException
    * @throws IllegalArgumentException
    */
   MessagesStoreFlashScopeAccessor(HttpServletRequest request, String flashParameter)
         throws NullPointerException, IllegalArgumentException {

      assertThatRequestIsNotNull(request);
      assertThatFlashParameterIsValid(flashParameter);
      LOGGER.trace("The messages will be stored in flash scope param [{}]", flashParameter);

      this.request= request;
      this.parameterName= flashParameter;

      initialize();
   }

   private void assertThatRequestIsNotNull(HttpServletRequest request) throws NullPointerException {
      if (request == null) {
         throw new NullPointerException("HttpServletRequest can't be null.");
      }
   }

   private void assertThatFlashParameterIsValid(String parameter)
         throws NullPointerException, IllegalArgumentException {

      if (parameter == null) {
         throw new NullPointerException("Flash parameter name can't be null.");
      }

      if (StringUtils.isBlank(parameter)) {
         throw new IllegalArgumentException("Flash parameter name can't be blank.");
      }
   }

   private void initialize() {
      if (!existsStoreInCurrentRequest()) {
         copyFromPreviousToCurrentRequest();
      }
   }


   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.store.MessagesStoreAccessor#contains()
    */
   @Override
   public boolean contains() {
      return existsStoreInCurrentRequest() || existsStoreFromPreviousRequest();
   }

   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.store.MessagesStoreAccessor#get()
    */
   @Override
   public MessagesStore get() throws MessagesStoreNotFoundException {
      LOGGER.debug("Getting the current messsages store in use");
      return fromCurrentRequest();
   }

   /*
    * (non-Javadoc)
    * @see
    * es.sandbox.ui.messages.store.MessagesStoreAccessor#put(es.sandbox.ui.messages.store.MessagesStore)
    */
   @Override
   public void put(MessagesStore store) {
      LOGGER.debug("Setting [{}] as the store in use.", store);
      currentRequestFlashMap().put(this.parameterName, store);
   }


   private boolean existsStoreFromPreviousRequest() {
      return previousRequestFlashMap().containsKey(this.parameterName);
   }

   private boolean existsStoreInCurrentRequest() {
      return currentRequestFlashMap().containsKey(this.parameterName);
   }

   private void copyFromPreviousToCurrentRequest() {
      final MessagesStore previousStore= fromPreviousRequest();
      if (previousStore != null) {
         LOGGER.debug("There is a store from previous request in the input flash scope!");
         put(previousStore);
      }
   }

   private MessagesStore fromCurrentRequest() throws MessagesStoreNotFoundException {
      final MessagesStore store= (MessagesStore) currentRequestFlashMap().get(this.parameterName);
      if (store == null) {
         throw new MessagesStoreNotFoundException();
      }
      return store;
   }

   private MessagesStore fromPreviousRequest() {
      return (MessagesStore) previousRequestFlashMap().get(this.parameterName);
   }

   private Map<String, ?> previousRequestFlashMap() {
      final Map<String, ?> inputFlashMap= RequestContextUtils.getInputFlashMap(this.request);
      return inputFlashMap == null? new HashMap<String, Object>() : inputFlashMap;
   }

   private FlashMap currentRequestFlashMap() {
      return RequestContextUtils.getOutputFlashMap(this.request);
   }
}
