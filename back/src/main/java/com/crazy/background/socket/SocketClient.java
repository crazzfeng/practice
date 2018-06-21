package com.crazy.background.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 客户端
 */
public class SocketClient
{

    private Socket m_socket;
    private SocketAddress m_socketAddress;
    private String m_hostname;
    private int m_port;

    public SocketClient(String hostname, int port) throws IOException
    {
        m_hostname = hostname;
        m_port = port;
        connect();
    }

    private void connect() throws IOException
    {
        m_socketAddress = new InetSocketAddress(m_hostname, m_port);
        m_socket = new Socket();
        m_socket.connect(m_socketAddress);
    }

    public void run(Object param)
    {
        if (param == null)
        {
            throw new NullPointerException("输入参数不能为空");
        }
        try
        {
            //输入
            DataOutputStream dataOutputStream = new DataOutputStream(m_socket.getOutputStream());
            dataOutputStream.writeUTF(param.toString());

            //输出
            DataInputStream dataInputStream = new DataInputStream(m_socket.getInputStream());
            String s = dataInputStream.readUTF();
            System.err.print(s);

            dataInputStream.close();
            m_socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
