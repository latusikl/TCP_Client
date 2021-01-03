package pl.latusikl.client.server;

import java.io.IOException;

public class ServerResponseThread
        extends Thread
{

    private final ServerConnector serverConnector;

    private final OutputSender outputSender;

    private volatile boolean end = false;

    public ServerResponseThread(final ServerConnector serverConnector, final OutputSender outputSender)
    {
        super("Server response thread");
        this.serverConnector = serverConnector;
        this.outputSender = outputSender;
    }

    public void end()
    {
        this.end = true;
    }

    private void disconnectedMessage()
    {
        this.outputSender.manageOutput("Server disconnected");
    }

    @Override
    public void run()
    {

        Boolean internalExit = false;
        while (!end && !internalExit) {

            if (serverConnector == null) {
                disconnectedMessage();
                internalExit = true;
            } else {
                final String serverMessage;
                try {
                    serverMessage = serverConnector.readMessage();
                    outputSender.manageOutput(serverMessage);
                    if (serverMessage == null) {
                        outputSender.manageOutput("Server disconnected.");

                        internalExit = true;
                    }
                } catch (final IOException e) {
                    outputSender.manageOutput("Server disconnected.");
                    internalExit = true;
                }
            }
        }
    }
}
