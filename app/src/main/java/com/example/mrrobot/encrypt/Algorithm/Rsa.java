package com.example.mrrobot.encrypt.Algorithm;

import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;



import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class Rsa {

    private PrivateKey privateKey;// private key
    private PublicKey publicKey;// public key
    //private Cipher cipher;

    public Rsa() {
        KeyPair keyPair= null;
        try {

            keyPair = new KeyPairGenerate().newInstance();
            this.privateKey=keyPair.getPrivate();
            this.publicKey=keyPair.getPublic();
            //cipher= Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public String encrypt(String x){
        String result="null";
        try {
            Cipher cipher= Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);// go to ENCRYPT_MODE
            byte[] xInBytes =cipher.doFinal(x.getBytes());
            result = android.util.Base64.encodeToString(xInBytes,0);
            //result=Base64.encodeBase64String(cipher.doFinal(x.getBytes()),getEncode());//x ENCRYPTED to String

        } catch (Exception e) {
            result= e.getMessage();
        }

        return result;

    }
    public String decrypt(String x){
        String result="null";

        try {
            Cipher cipher= Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);// go to DECRYPT_MODE

            result=new String(cipher.doFinal(android.util.Base64.decode(x,0)));
            //result=new String(cipher.doFinal(Base64.decodeBase64(x)));//x to bytes and DECRYPTED

        } catch (Exception e) {
            result= e.getMessage();
        }

        return result;

    }


}
