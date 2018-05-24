package com.example.mrrobot.encrypt;

import android.content.ClipData;
import android.content.ClipboardManager;
//import android.text.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;


public class TextBoxFragment extends Fragment implements
        BottomNavigationView.OnNavigationItemSelectedListener
        , TextToSpeech.OnInitListener
{

    private EditText editText;
    private BottomNavigationView options;
    private TextToSpeech textToSpeech;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String text = "";


    // TODO: Rename and change types of parameters
    private String text;


    private OnFragmentInteractionListener mListener;

    public TextBoxFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters
    /*public static TextBoxFragment newInstance(String param1, String param2) {
        TextBoxFragment fragment = new TextBoxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.textToSpeech = new TextToSpeech(getContext(),this );
        if (getArguments() != null) {
            text = getArguments().getString("text");
        }
        else{
            text="";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_box, container, false);
        this.editText = (EditText) view.findViewById(R.id.editText);
        this.editText.setText(text);
        this.options = (BottomNavigationView)view.findViewById(R.id.options);
        this.options.setOnNavigationItemSelectedListener(this);
        // button to talk
        /*ImageButton imageButton= (ImageButton)view.findViewById(R.id.talk);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                talk();
            }
        });
        */
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/
    public String getText(){
        return this.editText.getText().toString();
    }
    public void setText(String text){this.editText.setText(text);}
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.textToSpeech != null) {
            this.textToSpeech.stop();
            this.textToSpeech.shutdown();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
                case R.id.listen:
                    // listen text
                    listen(getText());
                    return true;
                case R.id.talk:
                    talk();
                    return true;

            }
            return false;
    }
    /*
    case R.id.clear:
                    // clear text of editText
                    this.editText.setText("");
                    return true;
                case R.id.copy:
                    toClipBoar(getText());
                    return true;
    */
    private void talk(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        startActivityForResult(intent, 5);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 5:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    this.editText.setText(result.get(0));
                }
        }
    }

    private void toClipBoar(String x ){

        if(!x.isEmpty()){
            ClipData clipData = ClipData.newPlainText("Text",x);
            ClipboardManager clipboardManager= (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(clipData);

        }
    }

    @Override
    public void onInit(int status) {
        try{
            if (status == TextToSpeech.SUCCESS) {
                int result = this.textToSpeech.setLanguage(Locale.getDefault());
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    throw new Exception("no support language");
                }
            } else {
                throw new Exception("Initilization Failed!");
            }
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG);
        }

    }
    public void listen (String x){
        if(this.textToSpeech.isSpeaking()){
            this.textToSpeech.stop();

        }
        else {
            this.textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
        void onSetText(String text);
        //void getText();
    }
}
