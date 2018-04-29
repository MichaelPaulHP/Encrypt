package com.example.mrrobot.encrypt.Algorithm;

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

    private PrivateKey privateKey;
    private PublicKey publicKey;


    public Rsa() {
        KeyPair keyPair= null;
        try {
            keyPair = generateKeys();
            this.privateKey=keyPair.getPrivate();
            this.publicKey=keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
    public String encrypt(String x){
        String result="null";
        try {
            Cipher cipher= Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);// go to ENCRYPT_MODE
            result=Base64.encodeBase64String(cipher.doFinal(x.getBytes()));//x ENCRYPTED to String

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;

    }
    public String decrypt(String x){
        String result="null";
        try {
            Cipher cipher= Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);// go to DECRYPT_MODE
            result=new String(cipher.doFinal(Base64.decodeBase64(x)));//x to bytes and DECRYPTED

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;

    }
    private KeyPair generateKeys() throws NoSuchAlgorithmException {
        final int keySize = 512;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }

}
