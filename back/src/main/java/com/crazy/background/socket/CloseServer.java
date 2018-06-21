package com.crazy.background.socket;

import java.io.IOException;

public class CloseServer
{
    public static void main(String[] args)
    {
        SocketServer socketServer = new SocketServer();
        socketServer.running();

        String hostname = "127.0.0.1";
        int port = 2020;
        try
        {
            SocketClient socketClient = new SocketClient(hostname, port);
            socketClient.run("a");
            socketServer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
