package com.tloj.game.utilities;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.Socket;


public class NetworkUtils {
    public static boolean isInternetAvailable() {
        try (Socket socket = new Socket()) {
            int timeout = 2000;  // Timeout in milliseconds
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);

            socket.connect(socketAddress, timeout);
            return true;
        } catch (IOException e) {
            return false;  // Either we have a timeout or unreachable host or failed DNS lookup
        }
    }
}
