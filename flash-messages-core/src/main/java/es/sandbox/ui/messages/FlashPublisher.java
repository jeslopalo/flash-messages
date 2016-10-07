package es.sandbox.ui.messages;

import es.sandbox.ui.messages.resolver.MessageResolver;


class FlashPublisher implements Flash {

    private final MessageResolver messageResolver;
    private final Store store;


    /**
     * @param messageResolver
     * @param store
     */
    public FlashPublisher(MessageResolver messageResolver, Store store) {
        assertThatMessageResolverIsNotNull(messageResolver);
        assertThatMessagesStoreIsNotNull(store);

        this.messageResolver = messageResolver;
        this.store = store;
    }

    private void assertThatMessageResolverIsNotNull(MessageResolver messageResolver) {
        if (messageResolver == null) {
            throw new NullPointerException("MessageResolver can't be null");
        }
    }

    private void assertThatMessagesStoreIsNotNull(Store store) {
        if (store == null) {
            throw new NullPointerException("Store can't be null");
        }
    }

    private String resolveMessage(String code, Object... arguments) {
        return this.messageResolver.resolve(code, arguments);
    }

    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.Flash#success(java.lang.String, java.lang.Object[])
     */
    @Override
    public void success(String code, Object... arguments)
        throws NullPointerException, IllegalArgumentException {
        this.store.add(Level.SUCCESS, resolveMessage(code, arguments));
    }

    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.Flash#info(java.lang.String, java.lang.Object[])
     */
    @Override
    public void info(String code, Object... arguments)
        throws NullPointerException, IllegalArgumentException {

        this.store.add(Level.INFO, resolveMessage(code, arguments));
    }

    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.Flash#warning(java.lang.String, java.lang.Object[])
     */
    @Override
    public void warning(String code, Object... arguments)
        throws NullPointerException, IllegalArgumentException {

        this.store.add(Level.WARNING, resolveMessage(code, arguments));
    }

    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.Flash#error(java.lang.String, java.lang.Object[])
     */
    @Override
    public void error(String code, Object... arguments)
        throws NullPointerException, IllegalArgumentException {

        this.store.add(Level.ERROR, resolveMessage(code, arguments));
    }

    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.Flash#clear()
     */
    @Override
    public void clear() {
        this.store.clear();
    }

    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.Flash#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return this.store.isEmpty();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.store.toString();
    }
}
