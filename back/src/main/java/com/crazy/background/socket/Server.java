package com.crazy.background.socket;

import java.io.IOException;

public class Server
{
    public static void main(String[] args)
    {
        SocketServer socketServer = null;
        int port = 2020;
        try
        {
            socketServer = new SocketServer(port);
            socketServer.running();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
