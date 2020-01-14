package org.ig2i.chat2i.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TestServeur extends Thread {

    public static final int PORT_ECOUTE = 49152;
    public static final int TIMEOUT_MS = 5000;

    private ServerSocket socketEcoute;

    @Override
    public void run() {
        try {
            socketEcoute = new ServerSocket(PORT_ECOUTE);
            System.out.println(TIMEOUT_MS / 1000 + " secondes d'attente de connexion avec le client.");
            socketEcoute.setSoTimeout(TIMEOUT_MS); // 5 secondes pour accepter une connexion
        } catch (IOException e) {
            System.err.println("Impossible de créer le socket d'écoute sur le port " + PORT_ECOUTE);
            System.exit(1);
        }

        while( !isInterrupted()) {
            Socket socketService = null;
            try {
                socketService = socketEcoute.accept(); // création de la connexion avec un client.
                System.out.println("Connexion avec le client établie.");




                System.out.println("Message envoyé par le client : " + lireMessage( socketService ));

                System.out.println("Fermeture du socket de service.");
                socketService.close();
            } catch (IOException e) {
                if (e instanceof SocketTimeoutException) {
                    System.err.println("Délai d'attente de connexion dépassé.");
                } else {
                    e.printStackTrace();
                }
                Thread.currentThread().interrupt();
            }
        }

        try {
            socketEcoute.close();
            System.out.println("Fermeture du socket d'écoute.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String lireMessage(Socket socketFlux) throws IOException {
        InputStreamReader isr = new InputStreamReader(socketFlux.getInputStream());
        BufferedReader in = new BufferedReader(isr);
        String message = in.readLine();
        in.close();
        return message;
    }
}
