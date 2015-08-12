package com.gottibujiku.httpjsonexchange.example;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Sha2Crypt;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by newton on 5/13/15.
 */
public class Main {

    public static void main(String[] argv){
        String pass = "newton";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(pass.getBytes());
        byte[] digest = md.digest();
        System.out.println(DigestUtils.sha256Hex(pass.getBytes()));
        System.out.println(DatatypeConverter.printHexBinary(digest).toLowerCase() );
        System.out.println(hashPassword(pass) );


    }

    public static String hashPassword(String password){
        byte[] digest = null;
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            digest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        for(byte character : digest){
            //convert to hex string
            builder.append(Integer.toString((character & 0xff)+0x100,16).substring(1));
        }
        //DigestUtils.sha256Hex(password.getBytes());
        return builder.toString();
    }
}
