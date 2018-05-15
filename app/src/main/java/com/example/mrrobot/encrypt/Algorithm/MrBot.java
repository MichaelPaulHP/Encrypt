package com.example.mrrobot.encrypt.Algorithm;



public class MrBot extends Rsa {


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

    // add algorithms to encrypt or de decrypt
    private String invert(String x){

        String result="";

        for(int i=0;i<x.length();i++)
            result= x.charAt(i) + result;
        return result;

    }

}
