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

    public Connexion(Socket socketFlux) throws IOException {
        Objects.requireNonNull(socketFlux, "socketFlux ne peut pas valoir null.");

        this.socketFlux = socketFlux;

        InputStreamReader isr = new InputStreamReader(socketFlux.getInputStream());
        in = new BufferedReader(isr);

        out = new PrintWriter(socketFlux.getOutputStream(),true);
        // autoFlush à true permet de vider le tampon à chaque appel de printIn().
        // Le message est envoyé au serveur que lorsque le tampon est vdé.
    }

    /**
     * Ecrit sur le flux de sortie de chacune des connexions de l'ensemble des connexions du serveur
     * @param message
     */
    private void envoyerMessage(String message)
    {
        out.write(message);
    }


}
