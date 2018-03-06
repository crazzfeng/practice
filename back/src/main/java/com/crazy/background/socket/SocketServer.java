package com.crazy.background.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketServer
{

    private Socket socket;
    private SocketAddress socketAddress;

    public SocketServer(String ip, int port) throws IOException
    {
        socketAddress = new InetSocketAddress(8080);
        socket = new Socket("www.baidu.com", 8080);
    }

    private void connect() throws IOException
    {
        ServerSocket serverSocket = new ServerSocket();
        socket.connect(socketAddress);
    }

}
