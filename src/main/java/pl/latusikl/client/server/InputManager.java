package pl.latusikl.client.server;

import pl.latusikl.client.server.exceptions.InputException;

public class InputManager
{
    public static int parsePort(final String port) throws InputException
    {
        final int portNumber;

        try {
            portNumber = Integer.parseInt(port);
        } catch (final NumberFormatException e) {
            throw new InputException("Unable to read port number");
        }

        if (portNumber < 0 || portNumber > 65535) {
            throw new InputException("Unable to read port number");
        }

        return portNumber;
    }

    private static boolean isIPByteGood(final String byteValue)
    {

        try {
            final int byteValueInt = Integer.parseInt(byteValue);
            if (byteValueInt < 0 || byteValueInt > 255) {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String checkIP(final String ipAddress) throws InputException
    {
        final String[] splitIPAddress = ipAddress.split("[.]");
        if (splitIPAddress.length != 4) {
            throw new InputException("Unable to process IP address");
        } else {
            for (final String elem : splitIPAddress) {
                if (!isIPByteGood(elem)) {
                    throw new InputException("Unable to process IP address");
                }
            }
        }
        return ipAddress;
    }
}
