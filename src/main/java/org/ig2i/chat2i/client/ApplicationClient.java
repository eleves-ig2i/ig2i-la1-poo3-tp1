package org.ig2i.chat2i.client;


import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class ApplicationClient extends JFrame
{
    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ApplicationClient.class);

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
            System.err.println(e.getMessage());
            if( e instanceof UnknownHostException)
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
            //log.error(e.getMessage());
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Impossible d'envoyer un message au serveur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(3);
        }
    }


    private void fermerSocketFlux()
    {
        try {
            socketFlux.close();
            //log.info("Connexion avec le serveur close.");
            System.out.println("Connexion avec le serveur close.");
        } catch (IOException e) {
            //log.error("Impossible de fermer le socket d'écoute.");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(4);
        }
    }

    @Override
    public void dispose() {
        out.close();
        fermerSocketFlux();
        System.exit(0);
    }
}
