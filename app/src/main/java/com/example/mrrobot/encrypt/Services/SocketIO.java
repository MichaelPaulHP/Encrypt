package com.example.mrrobot.encrypt.Services;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketIO {
    private static Socket mSocket;
    private static final String CHAT_SERVER_URL="https://ggprueba.herokuapp.com";
    //private static final String CHAT_SERVER_URL="http://localhost:1337/";
    public static Socket getSocket() {
        if (mSocket==null){
            try {
                mSocket = IO.socket(CHAT_SERVER_URL);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return mSocket;
    }
}
