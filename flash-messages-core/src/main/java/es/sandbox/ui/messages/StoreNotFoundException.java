package es.sandbox.ui.messages;


public class StoreNotFoundException extends IllegalStateException {

    private static final long serialVersionUID = -1747140702489823659L;


    public StoreNotFoundException() {
        super("There is not Store. Probably Context is not initialized!");
    }
}
