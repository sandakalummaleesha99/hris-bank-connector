package hris_bank_adapter.util;

public class CommonValidation {

    public static boolean stringNullValidation(String inputString) {
        return inputString == null || inputString.isEmpty();
    }

    public static boolean integerNullValidation(Integer inputValue) {
        return inputValue == null;
    }
}
