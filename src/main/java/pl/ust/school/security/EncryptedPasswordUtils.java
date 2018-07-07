package pl.ust.school.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {
 
    // Encryte Password with BCryptPasswordEncoder
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
 
    public static void main(String[] args) {
        String password = "123";
        String encryptedPassword = encryptPassword(password);
 
        System.out.println("Password: " + password + " Encryted Password: " + encryptedPassword);
    }
 
}