package pl.latusikl.client.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnector
        implements AutoCloseable
{
    private final Socket socket;

    private final PrintWriter output;

    private final BufferedReader input;


    public ServerConnector(final int portNumber, final String ipAddress) throws IOException
    {
        this.socket = new Socket(ipAddress, portNumber);
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String readMessage() throws IOException
    {
        return input.readLine();
    }


    public void sendMessage(final String message)
    {
        output.println(message);
    }

    public BufferedReader getInput()
    {
        return input;
    }

    @Override
    public void close() throws Exception
    {
        socket.close();
    }
}
