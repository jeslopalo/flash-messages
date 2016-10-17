package es.sandbox.ui.messages.spring.scope.memory;

import es.sandbox.ui.messages.Store;
import es.sandbox.ui.messages.StoreAccessor;
import es.sandbox.ui.messages.StoreAccessorFactory;
import es.sandbox.ui.messages.StoreNotFoundException;

import javax.servlet.http.HttpServletRequest;


public class InMemoryStoreAccessorFactory
    implements StoreAccessorFactory {

    @Override
    public StoreAccessor create(HttpServletRequest request) {
        return new StoreAccessor() {

            private Store store = new Store();


            @Override
            public void put(Store store) {
                this.store = store;
            }

            @Override
            public Store get() throws StoreNotFoundException {
                if (this.store == null) {
                    throw new StoreNotFoundException();
                }
                return this.store;
            }

            @Override
            public boolean contains() {
                return this.store != null;
            }
        };
    }
}
