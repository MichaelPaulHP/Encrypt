package com.example.mrrobot.encrypt.Algorithm;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.KeyGenerator;


public class PublicKeyMrBot  implements PublicKey {

    java.security.KeyPairGenerator keyPairGenerate;
    private final int keySize = 512;
    private  PublicKey publicKey;
    String algorithm;
    String format;
    byte[] encoded;
    public PublicKeyMrBot() throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator =KeyGenerator.getInstance("AES");
        keyGenerator.init(keySize);
        this.algorithm= keyGenerator.generateKey().getAlgorithm();
        this.encoded = keyGenerator.generateKey().getEncoded();
        this.format = keyGenerator.generateKey().getFormat();
        /*keyPairGenerate = KeyPairGenerator.getInstance("RSA");
        keyPairGenerate.initialize(keySize);
        KeyPair keyPair =  keyPairGenerate.genKeyPair();
        publicKey = keyPair.getPublic();
        this.algorithm=publicKey.getAlgorithm();
        this.encoded=publicKey.getEncoded();
        this.format=publicKey.getFormat();*/
    }

    @Override
    public String getAlgorithm() {

        return this.algorithm;
    }

    @Override
    public String getFormat() {
        return this.format;
    }

    @Override
    public byte[] getEncoded() {
        return this.encoded;
    }
    public void setEncoded(byte[] encoded){
        this.encoded=encoded;
    }
}
