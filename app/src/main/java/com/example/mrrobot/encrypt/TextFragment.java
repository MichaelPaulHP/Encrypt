package com.example.mrrobot.encrypt;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrrobot.encrypt.Algorithm.MrBot;
import com.example.mrrobot.encrypt.Algorithm.Rsa;
import com.example.mrrobot.encrypt.Models.User;
import com.example.mrrobot.encrypt.Services.SocketIO;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class TextFragment extends Fragment implements
        TextBoxFragment.OnFragmentInteractionListener
        ,View.OnClickListener{
    // attributes
    String socketId;
    private boolean encrypt;
    private String text;
    private MrBot mrBot;
    private io.socket.client.Socket socket;
    private TextView textView;
    private TextView cantUserTextView;
    private boolean goChat;

    //private List<User> amigos;
    private Map<String, String> amigos;
    private TextBoxFragment inputFragmentTexbox;
    private TextView receptorTextView;
    private TextView cantFriensTextView;
    private TextBoxFragment outputTextBoxFragment;
    //private TextView messageResult;


    // METHODS
    public TextFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.encrypt=false;
        this.goChat=false;
        this.socket= SocketIO.getSocket();
        this.amigos = new HashMap<String, String>();
        this.socket.connect();
        this.mrBot= new MrBot();
        this.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject thisUser = new JSONObject();
                        try{
                            socketId=socket.id();
                            // var data={"id":$socket.id,"publicKey":$myKeyPublic}
                            thisUser.put("id",socket.id());
                            thisUser.put("publicKey",mrBot.getPublicKey());
                            socket.emit("infoNewUser",thisUser);
                        }catch(JSONException e){
                            Log.d("JSONException",e.getMessage());
                        }

                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_text, container, false);
        // get RadioGroup
        /*RadioGroup radioGroup= (RadioGroup)view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);*/

        // Get button GO
        Button buttonGo=(Button)view.findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(this);

        // message Result:
        //this.messageResult=(TextView)view.findViewById(R.id.messageResult);

        // to temp show key
        this.textView =(TextView)view.findViewById(R.id.resultTextView);

        this.receptorTextView =(TextView)view.findViewById(R.id.receptorTextView);
        this.cantUserTextView= (TextView)view.findViewById(R.id.cantUserTextView);
        this.cantFriensTextView = (TextView)view.findViewById(R.id.cantFriendsTextView);
        // Image Button on click
        view.findViewById(R.id.showKeyPrivate).setOnClickListener(this);
        view.findViewById(R.id.showKeyPublic).setOnClickListener(this);
        view.findViewById(R.id.showKeyReceptor).setOnClickListener(this);
        view.findViewById(R.id.decrypt).setOnClickListener(this);

        // get Text of input TextBoxFragment
        this.inputFragmentTexbox = new TextBoxFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_text_box,inputFragmentTexbox);
        fragmentTransaction.commit();
        //this.text=inputFragmentTexbox.getText();
        /*inputFragmentTexbox =(TextBoxFragment) getFragmentManager().findFragmentById(R.id.fragment_text_box);
        this.text=inputFragmentTexbox.getText();*/



        // emit this

        // cantidad ed user
        if(this.socket.connected()){
            this.cantUserTextView.setText(this.socket.id());

        }

        // return cant of user conectados
        this.socket.on("newUser", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String cant = args[0].toString();
                        cantUserTextView.setText(socket.id());
                    }
                });
            }
        });

        // cuando se conecta un nuevo user and emit infoNewUser
        this.socket.on("infoNewUser", new Emitter.Listener() {
            @Override
            public void call( final  Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String id;
                        String publicKey;
                        try {
                            JSONObject data = (JSONObject) args[0];
                            id = data.getString("id");
                            publicKey = data.getString("publicKey");


                            amigos.put(id,publicKey);
                            cantFriensTextView.setText("Amigos Conectados: "+amigos.size());
                            if (!amigos.isEmpty()){
                                mrBot.setPublicKeyReceptor(publicKey);
                                receptorTextView.setText("A: "+id);
                                goChat=true;
                                //cantFriensTextView.setText("Amigo Listo");
                            }
                        } catch (JSONException e) {
                            Log.d("JSONException",e.getMessage());
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeySpecException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });

        this.socket.on("newMessage", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        //create bundle for TexBOxFragment
                        //showOutputTextBox(message);
                       try {
                           JSONObject data = (JSONObject)args[0];
                            String message = data.getString("message");
                           String from = data.getString("from");
                            //create bundle for TexBOxFragment
                            showOutputTextBox(message);

                        } catch (JSONException e) {
                            return;
                        }


                    }
                });
            }
        });
        this.socket.on("disconected", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String id = args[0].toString();
                        // if cant ==2
                        if (amigos.containsKey(id)){
                            amigos.remove(id);
                        }
                        int cant=amigos.size();
                        cantFriensTextView.setText("Amigos Conectados: "+cant);
                        goChat= cant>=1? true:false;
                        if (cant>=1){
                            goChat=true;

                        }
                        else{
                            goChat=false;
                            receptorTextView.setText("");

                        }

                    }
                });
            }
        });
        return view;
    }
    public void showOutputTextBox(String text){

        if(this.outputTextBoxFragment==null){
            Bundle args= new Bundle();
            args.putString("text",text);

            // create fragment TexBox output
            this.outputTextBoxFragment = new TextBoxFragment();
            outputTextBoxFragment.setArguments(args);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.outputFragment_text_box,outputTextBoxFragment);
            fragmentTransaction.commit();
        }
        else{
            this.outputTextBoxFragment.setText(text);
        }

    }


    public void decrypt(String text){
        // text is encrypted

        String result="";
        result=this.mrBot.decrypt(text);
        this.showOutputTextBox(result);

    }
    public void goEmitMessage() throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.text=this.inputFragmentTexbox.getText();

        if(this.text.isEmpty() || !this.goChat){
            String me=!this.goChat? "Nadie te Escucha":"ingrese Texto";
            // not found text
            Toast.makeText(getContext(),me,Toast.LENGTH_LONG).show();
        }
        else{
            // emit message encrypted
            // message encrypted con receptor publick key
            // buscar en amigos y obtener su publickKey
            String pk="";
            String id="";
            for (Map.Entry<String, String> entry : amigos.entrySet()) {
                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                pk=entry.getValue();
                id=entry.getKey();
            }
            this.mrBot.setPublicKeyReceptor(pk); // se crear la Public Key
            String message = this.mrBot.encrypt(this.text,this.mrBot.publicKeyReceptor);

            JSONObject jsonObject = new JSONObject();
            try{

                // var data={"id":$socket.id,"publicKey":$myKeyPublic}
                jsonObject.put("to",id);
                jsonObject.put("message",message);
                socket.emit("newMessage",jsonObject);
            }catch(JSONException e){
                Log.d("JSONException",e.getMessage());
            }
            /*// encrypt or decrypt text
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

            // set text to outputFragmentTextBox*/
        }
    }
    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.buttonGo:
                try {
                    goEmitMessage();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return;
            case R.id.decrypt:
                if(this.outputTextBoxFragment!=null) {
                    decrypt(this.outputTextBoxFragment.getText());
                }
                return ;
            case R.id.showKeyPublic:
                Toast.makeText(this.getContext(),mrBot.publicKeyToString(),Toast.LENGTH_LONG).show();
                return ;
            case R.id.showKeyPrivate:
                Toast.makeText(this.getContext(),mrBot.privateKeyToString(),Toast.LENGTH_LONG).show();
                return ;
            case R.id.showKeyReceptor:
                if (!amigos.isEmpty()){
                    Toast.makeText(this.getContext(),mrBot.publicKeyReceptorToString(),Toast.LENGTH_LONG).show();
                }
                return ;
        }
    }

    /*@Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radioEncrypt:
                this.encrypt=true;
            case R.id.radioDecrypt:
                this.encrypt=false;
        }
    }*/

    @Override
    public void onSetText(String text) {

    }

    @Override
    public void onDestroy() {
        this.socket.disconnect();
        super.onDestroy();
        //this.socket.disconnect();

    }
}
