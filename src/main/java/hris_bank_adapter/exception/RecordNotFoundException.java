package hris_bank_adapter.exception;

public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6638849627494498004L;

    public RecordNotFoundException(String message) {
        super(message);
    }
}

