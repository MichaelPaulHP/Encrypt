package com.example.mrrobot.encrypt.Algorithm;


import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MrBot extends Rsa {

    /* encrypt : encripta el texto x
    * @param x:  String x  para encryptar
    * @param pk: Public Key para encrptar para RSA
    * return : x encriptado
    */
    @Override
    public String encrypt(String x ,PublicKey pk) {
        x = invert(x);// invertir x
        x = functions(x); // pares y impares
        x = super.encrypt(x,pk); // aplicar RSA
        return x; // x encriptado
    }


    @Override
    public String encrypt(String x) {

        x = invert(x);// invertir x
        x = functions(x); // pares, impares, primos
        x = super.encrypt(x); // aplicar RSA
        return x; // x encriptado
    }

    public PublicKey publicKeyReceptor;
    public MrBot(){
        super();

    }
    /* decrypt : descifrar el texto x
     *
     * @param x:  String x  para descifrar
     * return : x descifrado
     */
    @Override
    public String decrypt(String x) {

        x = super.decrypt(x); // descifrar con RSA
        x = functions(x); // pares, impares
        x = invert(x); // invertir
        return x;
    }



    public  void setPublicKeyReceptor(String x) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = android.util.Base64.decode(x,0);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        this.publicKeyReceptor=keyFromBytes;
    }
    private String functions(String x){
        return x;
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
