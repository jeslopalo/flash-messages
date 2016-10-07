package es.sandbox.ui.messages.spring.scope.flash;

import es.sandbox.ui.messages.StoreAccessor;
import es.sandbox.ui.messages.StoreAccessorFactory;

import javax.servlet.http.HttpServletRequest;


public class FlashScopeStoreAccessorFactory
    implements StoreAccessorFactory {

    public static final String FLASH_MESSAGES_PARAMETER = FlashScopeStoreAccessorFactory.class.getName() + ".FLASH_MESSAGES";


    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.store.StoreAccessorFactory#create(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public StoreAccessor create(HttpServletRequest request) {
        return new FlashScopeStoreAccessor(request, FLASH_MESSAGES_PARAMETER);
    }
}
