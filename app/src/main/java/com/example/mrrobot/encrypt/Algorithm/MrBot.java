package com.example.mrrobot.encrypt.Algorithm;


import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MrBot extends Rsa {

    public PublicKey publicKeyReceptor;
    public MrBot(){
        super();

    }
    @Override
    public String encrypt(String x) {
        // 1 invert text
        x=invert(x);
        // encrypt text inverted
        return super.encrypt(x);
    }
    @Override
    public String decrypt(String x) {
        // first decrypt
        // invert text
        x=invert(super.decrypt(x));
        return x;
    }
    @Override
    public String encrypt(String x ,PublicKey pk) {
        // first decrypt
        // invert text
        x=invert(x);
        return super.encrypt(x,pk);
    }
    public  void setPublicKeyReceptor(String x) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = android.util.Base64.decode(x,0);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        this.publicKeyReceptor=keyFromBytes;
    }
    // add algorithms to encrypt or de decrypt
    private String invert(String x){

        String result="";

        for(int i=0;i<x.length();i++)
            result= x.charAt(i) + result;
        return result;

    }
    public String publicKeyToString(){

        String str=android.util.Base64.encodeToString(  this.publicKey.getEncoded(),0);
        return str;
    }
    public String privateKeyToString(){
        String str=android.util.Base64.encodeToString(  this.privateKey.getEncoded(),0);
        return str;
    }
    public String publicKeyReceptorToString(){
        String str=android.util.Base64.encodeToString(  this.publicKeyReceptor.getEncoded(),0);
        return str;
    }

}
