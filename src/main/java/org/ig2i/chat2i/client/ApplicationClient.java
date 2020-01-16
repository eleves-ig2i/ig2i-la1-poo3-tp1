package org.ig2i.chat2i.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class ApplicationClient extends JFrame
{
    Logger log = LogManager.getLogger(ApplicationClient.class);

    private ApplicationClientGUI clientDesigner;

    private Socket socketFlux;

    private PrintWriter out;


    public ApplicationClient(String adresse, int port){
        super("Chat2i");
        clientDesigner = new ApplicationClientGUI();
        this.setContentPane( clientDesigner.getRootPanel() );
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        connecterServeur(adresse,port);

        creerBufferServeur();
    }


    private void connecterServeur(String adresseIP, int port)
    {
        try {
            socketFlux = new Socket( InetAddress.getByName(adresseIP), port);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            if( e instanceof UnknownHostException || e instanceof ConnectException)
            {
                JOptionPane.showMessageDialog(null, "Impossible de se connecter au serveur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(2);
        }
    }


    private void creerBufferServeur()
    {
        try {
            out = new PrintWriter(socketFlux.getOutputStream(),true);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Impossible d'envoyer un message au serveur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(3);
        }
    }


    private void fermerSocketFlux()
    {
        try {
            socketFlux.close();
            log.info("Connexion avec le serveur close.");
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            System.exit(4);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        out.close();
        fermerSocketFlux();
        //System.exit(0);
    }
}
