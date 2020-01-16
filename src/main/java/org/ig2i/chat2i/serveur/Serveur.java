package org.ig2i.chat2i.serveur;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

public class Serveur extends Thread
{
    Logger log = LogManager.getLogger(Serveur.class);

    private List<Connexion> connexions;

    private ServerSocket socketEcoute;

    public static final int PORT_ECOUTE = 49152;

    public static final int TIMEOUT_MS = 100000;

    public Serveur() throws IOException {
        try {
            this.socketEcoute = new ServerSocket(PORT_ECOUTE);
            log.debug(TIMEOUT_MS / 1000 + " secondes d'attente de connexion avec le client.");
            socketEcoute.setSoTimeout(TIMEOUT_MS); // 100 secondes pour accepter une connexion
            log.info("Serveur ouvert.");
        } catch (IOException e) {
            log.error("Impossible de créer le socket d'écoute sur le port " + PORT_ECOUTE);
            e.printStackTrace();
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
                log.debug("En attente de connexion ..");
                Socket socketFlux = socketEcoute.accept();
                log.info("Connexion établie avec un client: " + socketFlux);
                connexions.add( new Connexion(socketFlux) );
            }
        }
        catch (IOException e){
            if( e instanceof SocketTimeoutException )
            {
                log.warn("Délai d'attente de connexion dépassé.");
            } else {
                log.error(e);
                e.printStackTrace();
            }
        } finally {
            fermerSocketEcoute();
        }
    }


    private void fermerSocketEcoute()
    {
        try {
            log.debug("Fermeture du serveur en cours.");
            socketEcoute.close();
            log.info("Serveur fermé.");
        } catch (IOException e) {
            log.error("Impossible de fermer le socket d'écoute.");
            e.printStackTrace();
        }
    }


    public int getNbConnexions()
    {
        return connexions.size();
    }

    /* package */ List<Connexion> getConnexions() {
        return connexions;
    }
}
