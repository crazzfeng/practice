package com.crazy.background.socket;

import java.io.IOException;

public class Client
{
    public static void main(String[] args)
    {

        String hostname = "127.0.0.1";
        int port = 2020;
        try
        {
            SocketClient socketClient = new SocketClient(hostname, port);
            socketClient.run("a");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
