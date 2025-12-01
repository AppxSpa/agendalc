package com.agendalc.agendalc.utils;

public class RutUtils {

    private RutUtils() {
        // Private constructor to hide the implicit public one
    }

    public static Integer parse(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return null;
        }
        String digits = rut.replaceAll("[^0-9kK]", "");
        if (digits.isEmpty()) {
            return null;
        }
        // Remove verifier digit
        String numberPart = digits.substring(0, digits.length() - 1);
        try {
            return Integer.valueOf(numberPart);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
