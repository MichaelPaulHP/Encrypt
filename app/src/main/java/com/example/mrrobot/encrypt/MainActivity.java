package com.example.mrrobot.encrypt;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mrrobot.encrypt.Algorithm.PublicKeyMrBot;
import com.example.mrrobot.encrypt.Algorithm.Rsa;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class MainActivity extends AppCompatActivity {


    /*
    public static  void main(String[] args) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        String x="hola Mundo";
        final int keySize = 512;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair =  keyPairGenerator.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        //PrivateKey privateKey = keyPair.getPrivate();

        System.out.println("PUBLIC KEY");
        System.out.println(publicKey.getAlgorithm());
        System.out.println(publicKey.getFormat());
        byte[] encoded = publicKey.getEncoded();
        System.out.println(Base64.encodeBase64String(encoded));

        String enviar =Base64.encodeBase64String(encoded);// este viaja como string
        // probando key

        // ahora este recie para encryptar

        byte[] bytes =Base64.decodeBase64(enviar);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        System.out.println("PUBLIC KEY COPY");
        System.out.println(keyFromBytes.getAlgorithm());
        System.out.println(keyFromBytes.getFormat());
        byte[] encodedw = keyFromBytes.getEncoded();
        System.out.println(Base64.encodeBase64String(encodedw));



        // encriptar con las clave
        Cipher cipher= Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);// go to ENCRYPT_MODE
        byte[] xInBytes =cipher.doFinal(x.getBytes());
        //String result = android.util.Base64.encodeToString(xInBytes,0);
        String result= Base64.encodeBase64String(cipher.doFinal(x.getBytes()));//x ENCRYPTED to String
        System.out.println("---------");
        System.out.println(result);

        Cipher cipherb= Cipher.getInstance("RSA");
        cipherb.init(Cipher.ENCRYPT_MODE, keyFromBytes);// go to ENCRYPT_MODE
        byte[] xInBytesb =cipherb.doFinal(x.getBytes());
        //String result = android.util.Base64.encodeToString(xInBytes,0);
        String resultb= Base64.encodeBase64String(cipherb.doFinal(x.getBytes()));//x ENCRYPTED to String
        System.out.println("-----MR BOT ENCRYPt----");
        System.out.println(resultb);


        KeyPairGenerator keyPairGenerator2 = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator2.initialize(keySize);
        KeyPair keyPair2 =  keyPairGenerator.genKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publi = keyPair.getPublic()
        Cipher cipher2 = Cipher.getInstance("RSA");
        cipher2.init(Cipher.DECRYPT_MODE, privateKey);// go to ENCRYPT_MODE
        //byte[] xInBytes2 =cipher.doFinal(x.getBytes());
        //String result2= new String(xInBytes2);

        String result2=new String(cipher2.doFinal(Base64.decodeBase64(result)));//x ENCRYPTED to String
        System.out.println("DECRPT-----------");
        System.out.println(result2);
       // PublicKey pk = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec());
    }
    */

    private TextView mTextMessage;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView bottomNavigation;
    private FileFragment fileFragment;
    private TextFragment textFragment;
    private InfoFragment infoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fileFragment = new FileFragment();
        this.textFragment= new TextFragment();
        this.infoFragment = new InfoFragment();

        setFragment(this.textFragment);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //setFragment(textFragment);
                    return true;
                case R.id.navigation_dashboard:
                    //setFragment(fileFragment);
                    return true;
                case R.id.navigation_notifications:
                    //setFragment(infoFragment);
                    return true;
            }
            return false;
        }
    };


    private  void setFragment (Fragment fragment){

        this.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //this.fragmentTransaction.addToBackStack(null);
        this.fragmentTransaction.replace(R.id.ContentFragment,fragment);
        this.fragmentTransaction.commit();
    }

}
