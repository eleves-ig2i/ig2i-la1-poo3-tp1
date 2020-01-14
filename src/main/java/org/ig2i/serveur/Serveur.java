package org.ig2i.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

public class Serveur extends Thread
{
    private List<Connexion> connexions;

    private ServerSocket socketEcoute;

    public static final int PORT_ECOUTE = 49152;

    public static final int TIMEOUT_MS = 5000;

    public Serveur() throws IOException {
        try {
            this.socketEcoute = new ServerSocket(PORT_ECOUTE);
            System.out.println("[Serveur] TIMEOUT_MS / 1000" + " secondes d'attente de connexion avec le client.");
            socketEcoute.setSoTimeout(TIMEOUT_MS); // 5 secondes pour accepter une connexion
            System.out.println("[Serveur] Serveur ouvert.");
        } catch (IOException e) {
            System.err.println("[Serveur] Impossible de créer le socket d'écoute sur le port " + PORT_ECOUTE);
            System.exit(1);
        }

        connexions = new LinkedList<>();
    }

    /**
     * A chaque fois qu'un client se connecte, une nouvelle connexion est ajoutée à l'ensemble des connexions.
     */
    @Override
    public void run() {

        try {
            while (!isInterrupted()) {
                System.out.println("[Serveur] En attente de connexion ..");
                Socket socketFlux = socketEcoute.accept();
                System.out.println("[Serveur] Connexion établie avec un client: " + socketFlux);
                connexions.add( new Connexion(socketFlux) );
            }
        }
        catch (IOException e){
            if( e instanceof SocketTimeoutException )
            {
                System.out.println("[Serveur] Délai d'attente de connexion dépassé.");
            } else {
                e.printStackTrace();
            }
        } finally {
            fermerSocketEcoute();
        }
    }


    private void fermerSocketEcoute()
    {
        try {
            System.out.println("Fermeture du serveur en cours.");
            socketEcoute.close();
            System.out.println("Serveur fermé.");
        } catch (IOException e) {
            System.err.println("Impossible de fermer le socket d'écoute.");
            e.printStackTrace();
        }
    }
}
