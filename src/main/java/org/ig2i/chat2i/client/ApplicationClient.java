package org.ig2i.chat2i.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ig2i.chat2i.serveur.Serveur;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private String nom;


    public ApplicationClient(String nom, String adresse, int port){
        super(nom + " on Chat2i");
        this.nom = nom;

        clientDesigner = new ApplicationClientGUI();
        this.setContentPane( clientDesigner.getRootPanel() );
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        connecterServeur(adresse,port);

        creerBufferServeur();

        clientDesigner.getEnvoyerButton().addActionListener(actionEvent -> {
            String message = clientDesigner.getMessageInput().getText();
            log.info("L'utilisateur {} veut envoyer :'{}'",nom, message);
            if( message != null && !message.isEmpty() )
            {
                String body = nom + ": "+ message;
                out.println(body);
                log.debug("Message envoyé au serveur.");
            } else
            {
                log.warn("Message vide");
                JOptionPane.showMessageDialog(null, "Message vide !", "Attention", JOptionPane.WARNING_MESSAGE);
            }
        });

        try {
            creerEcouteurServeur();
        } catch (IOException e) {
            log.error("Echec de création de l'écouteur du serveur");
            JOptionPane.showMessageDialog(null, "Impossible de se connecter au serveur !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        log.info("Application client initialisée, en attente de message !");
    }


    private void connecterServeur(String adresseIP, int port)
    {
        try {
            socketFlux = new Socket( InetAddress.getByName(adresseIP), port);
            log.debug("Connexion avec le serveur réussie.");
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


    private void creerEcouteurServeur() throws IOException {
        InputStreamReader isr = new InputStreamReader(socketFlux.getInputStream());
        Ecouteur e = new Ecouteur(clientDesigner.getConversation(), new BufferedReader(new BufferedReader(isr)) );
        e.start();
    }

    @Override
    public void dispose() {
        log.info("Fermeture de l'application client " + nom);
        super.dispose();
        out.close();
        fermerSocketFlux();
        //System.exit(0);
    }


    public static void main(String[] args) {
        //ApplicationClient a = new ApplicationClient("Nathan","127.0.0.1", Serveur.PORT_ECOUTE);
        ApplicationClient a = new ApplicationClient("Nathan","192.168.1.102", 55555);
    }
}
