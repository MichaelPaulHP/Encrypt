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

import com.example.mrrobot.encrypt.Algorithm.Rsa;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {


    /*public static  void main(String[] args){
        String input="hola Mundo";
        Rsa rsa = new  Rsa();

        String textEncrypt=rsa.encrypt(input);
        System.out.println(textEncrypt);
        System.out.println("---------");
        System.out.println(rsa.decrypt(textEncrypt));
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
                    setFragment(textFragment);
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(fileFragment);
                    return true;
                case R.id.navigation_notifications:
                    setFragment(infoFragment);
                    return true;
            }
            return false;
        }
    };


    private  void setFragment (Fragment fragment){

        this.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        this.fragmentTransaction.addToBackStack(null);
        this.fragmentTransaction.replace(R.id.ContentFragment,fragment);
        this.fragmentTransaction.commit();
    }

}
