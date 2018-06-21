package com.crazy.background.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 服务端server
 */
public class SocketServer
{

    private ServerSocket serverSocket;
    private int m_port;

    public SocketServer()
    {
    }

    public SocketServer(int port) throws IOException
    {
        m_port = port;

    }

    public void running()
    {
        try
        {
            serverSocket = new ServerSocket(m_port);
            while (true)
            {
                program(serverSocket);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void close()
    {
        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void program(ServerSocket serverSocket) throws IOException
    {
        OutputStream outputStream = null;
        try
        {
            Socket socket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String input = dataInputStream.readUTF();

            if (!"a".equals(input))
            {
                dos.writeUTF("请求错误");
            }
            else
            {
                dos.writeUTF("This is my first java socket program!");
            }

            dos.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public int getPort()
    {
        return m_port;
    }

    public void setPort(int port)
    {
        this.m_port = port;
    }
}
