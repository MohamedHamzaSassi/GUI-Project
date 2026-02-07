package org.example.teleporti.Utils;

public class EmailVerification {

    public static boolean isValid(String email) {
        String reg = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+.$";
        return email.matches(reg);
    }
}
