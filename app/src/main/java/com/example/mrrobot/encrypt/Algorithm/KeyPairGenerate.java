package com.example.mrrobot.encrypt.Algorithm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyPairGenerate  {

    private KeyPair keyPair;
    public KeyPairGenerate(){}
    public KeyPair newInstance() throws NoSuchAlgorithmException {
        if(this.keyPair==null){
            final int keySize = 512;
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
            this.keyPair =  keyPairGenerator.genKeyPair();
            return this.keyPair;
        }
        else{
            return this.keyPair;
        }
    }

}
