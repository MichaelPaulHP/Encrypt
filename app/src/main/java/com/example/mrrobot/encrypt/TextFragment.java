package com.example.mrrobot.encrypt;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mrrobot.encrypt.Algorithm.Rsa;




public class TextFragment extends Fragment implements
        RadioGroup.OnCheckedChangeListener,TextBoxFragment.OnFragmentInteractionListener
        ,View.OnClickListener{
    // attributes
    private boolean encrypt;
    private String text;
    private Rsa rsa;
    TextBoxFragment inputFragmentTexbox;


    // METHODS
    public TextFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.encrypt=true;
        this.rsa = new Rsa();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_text, container, false);
        // get RadioGroup
        RadioGroup radioGroup= (RadioGroup)view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        // Get button GO
        Button buttonGo=(Button)view.findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(this);

        // get Text of input TextBoxFragment
        this.inputFragmentTexbox = new TextBoxFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_text_box,inputFragmentTexbox);
        fragmentTransaction.commit();
        //this.text=inputFragmentTexbox.getText();
        /*inputFragmentTexbox =(TextBoxFragment) getFragmentManager().findFragmentById(R.id.fragment_text_box);
        this.text=inputFragmentTexbox.getText();*/


        return view;
    }

    public String encryptOrDecrypt(String text){
        String result="";
        if(this.encrypt){
            //encrypt text
            result=this.rsa.encrypt(text);

        }
        else{
            //decrypt text
            result=this.rsa.decrypt(text);
        }
        return result;
    }
    public void go(){
        this.text=this.inputFragmentTexbox.getText();

        if(this.text.isEmpty()){
            // not found text
            Toast.makeText(getContext(),"Ingrese Texto",Toast.LENGTH_LONG).show();
        }
        else{
            // encrypt or decrypt text
            // if decrypt
            String textChange = encryptOrDecrypt(this.text);
            // create outputFragmentTextBox


            //create bundle for TexBOxFragment
            Bundle args= new Bundle();
            args.putString("text",textChange);

            // create fragment TexBox output
            TextBoxFragment textBoxFragment = new TextBoxFragment();
            textBoxFragment.setArguments(args);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.outputFragment_text_box,textBoxFragment);
            fragmentTransaction.commit();

            // set text to outputFragmentTextBox
        }
    }
    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.buttonGo:
                go();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radioEncrypt:
                this.encrypt=true;
            case R.id.radioDecrypt:
                this.encrypt=false;
        }
    }

    @Override
    public void onSetText(String text) {

    }

}
