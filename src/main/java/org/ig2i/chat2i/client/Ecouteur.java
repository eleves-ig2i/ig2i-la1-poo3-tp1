package org.ig2i.chat2i.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ig2i.chat2i.serveur.Serveur;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class Ecouteur extends Thread {

    Logger log = LogManager.getLogger(Ecouteur.class);

    private JTextArea conversation;

    private BufferedReader in;

    public Ecouteur(JTextArea conversation, BufferedReader in) {
        Objects.requireNonNull(conversation);
        Objects.requireNonNull(in);
        this.conversation = conversation;
        this.in = in;
    }

    /**
     * Ce thread ne gère pas la fermeture du flux d'entrée.
     */
    @Override
    public void run() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                log.debug("Reception du message '{}'", message);
                conversation.append(message);
            }
        } catch (IOException e)
        {
            log.error("Fin du flux d'entrée.");
        }
    }
}
