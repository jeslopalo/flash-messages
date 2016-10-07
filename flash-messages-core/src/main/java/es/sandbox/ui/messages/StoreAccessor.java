package es.sandbox.ui.messages;


public interface StoreAccessor {

    /**
     * @return
     */
    boolean contains();

    /**
     * @return
     * @throws StoreNotFoundException
     */
    Store get()
        throws StoreNotFoundException;

    /**
     * @param store
     */
    void put(Store store);
}
