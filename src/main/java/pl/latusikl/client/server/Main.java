package pl.latusikl.client.server;

import pl.latusikl.client.server.exceptions.InputException;

import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(final String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        println("Type port to listen.");

        final String port = scanner.next();

        println("Type ip to listen. 'l' to use localhost");

        final String defaultIp = "127.0.0.1";
        String ip = scanner.next();

        try {
            InputManager.parsePort(port);
            if (ip.equals("l")) {
                ip = defaultIp;
            }
            InputManager.checkIP(ip);
            final ServerConnector serverConnector = new ServerConnector(InputManager.parsePort(port), ip);
            final ServerResponseThread serverResponseThread = new ServerResponseThread(serverConnector,
                                                                                       Main::printFromServer);
            serverResponseThread.start();


            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                if (line.equals("quit")) {
                    scanner.close();
                    serverResponseThread.end();
                    break;
                } else {
                    serverConnector.sendMessage(line);
                }
            }

        } catch (final InputException | IOException e) {
            println(e.getMessage());
            scanner.close();
        }
        System.exit(0);
    }

    private static void println(final String line)
    {
        System.out.println(line);
    }

    private static void printFromServer(final String line)
    {
        System.err.print(line);
    }
}
