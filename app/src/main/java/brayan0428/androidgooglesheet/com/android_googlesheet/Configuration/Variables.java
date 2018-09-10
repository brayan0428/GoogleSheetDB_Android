package brayan0428.androidgooglesheet.com.android_googlesheet.Configuration;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Variables {
    public static String URL_API = "https://script.google.com/macros/s/AKfycbw9mUO6ZZ03HVdoRbqOSPhUuD4nVJ377gQvn19y6kQfAnvF6c7z/exec";
    public static String ID = "iduser";
    public static String NAMES = "names";
    public static String LASTNAMES = "lastnames";
    public static String ADDRESS = "address";
    public static String EMAIL = "email";
    public static String IMAGE = "image";

    public static String stringToMD5(String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(string.getBytes(Charset.forName("UTF-8")),0,string.length());
        return new BigInteger(1,messageDigest.digest()).toString(16);
    }
}

