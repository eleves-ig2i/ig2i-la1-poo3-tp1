package org.ig2i.chat2i.serveur;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class Connexion extends Thread
{
    Logger log = LogManager.getLogger(Connexion.class);

    private Socket socketFlux;

    private BufferedReader in;

    private PrintWriter out;

    private Serveur instanceServeur;

    public Connexion(Socket socketFlux, Serveur instanceServeur) throws IOException {
        Objects.requireNonNull(socketFlux, "socketFlux ne peut pas valoir null.");
        Objects.requireNonNull(instanceServeur, "instanceServeur ne peut pas valoir null.");

        this.socketFlux = socketFlux;
        this.instanceServeur = instanceServeur;

        InputStreamReader isr = new InputStreamReader(socketFlux.getInputStream());
        in = new BufferedReader(isr);

        out = new PrintWriter(socketFlux.getOutputStream(),true);
        // autoFlush à true permet de vider le tampon à chaque appel de printIn().
        // Le message est envoyé au serveur que lorsque le tampon est vdé.
    }

    /**
     * Ecrit sur le flux de sortie de chacune des connexions de l'ensemble des connexions du serveur
     * Le mot clé synchronized signifie que seul 1 thread peut accéder à cette méthode.
     * @param message
     */
    private synchronized void envoyerMessage(String message)
    {
        for( Connexion c : instanceServeur.getConnexions() )
        {
            log.debug("Envoi du message {} à la connexion {}",message,c);
            c.out.write(message);
        }
    }


    /**
     * Tant qu'il y a un message à lire sur le flux d'entrée,
     * on envoie ce message à toutes les connexions.
     * Lorsque la fin du flux d'entrée est atteinte, la méthode ferme toutes les ressources et termine l'exécution du thread courant.
     */
    @Override
    public void run() {
        String message = null;
        try {
            log.debug("Connexion initialisée, en attente de message..");
            System.out.println(socketFlux);
            while ((message = in.readLine()) != null) {
                log.info("Envoi du message '{}' à toutes les connexions.",message);
                envoyerMessage(message);
                log.debug("Envoi du message réussi.");
            }
        } catch( IOException e)
        {
            log.warn("fin du flux d'entrée.");
        } finally {
            fermerRessources();
        }
    }


    private void fermerRessources()
    {
        try {
            log.debug("Fermeture de la connexion {} en cours..", this);
            in.close();
            out.close();
            socketFlux.close();
            if( instanceServeur.supprimerConnexion(this) )
                log.info("Fermeture de la connexion {} réussie.", this);
            else
                log.warn("Connexion non existante dans le serveur");
        } catch( IOException e)
        {
            log.error("Echec de la fermeture de la connexion.");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Connexion{" +
                "socketFlux=" + socketFlux +
                '}';
    }
}
