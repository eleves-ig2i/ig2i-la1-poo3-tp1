package org.ig2i;

import org.ig2i.client.ApplicationClient;
import org.ig2i.client.ApplicationClientGUIForm;
import org.ig2i.serveur.Serveur;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Chat2i
{
    public static void tp1q11() throws IOException {
        Serveur serv = new Serveur();
        serv.start();
        //sleep(1000);
        for(int i =0; i < 5; i++)
        {
            InetAddress adresseIp = InetAddress.getByName("127.0.0.1");
            Socket socketFlux = new Socket(adresseIp, Serveur.PORT_ECOUTE);
            System.out.println("[Client] Connexion avec le serveur " + "127.0.0.1" + " réussie.");
        }
    }


    public static void tp1q12()
    {
        JFrame fenetre = new JFrame("Chat2i");
        ApplicationClientGUIForm client = new ApplicationClientGUIForm();
        fenetre.setContentPane( client.getRootPanel() );
        fenetre.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        fenetre.pack();
        fenetre.setVisible(true);
    }


    public static void tp1q13()
    {
        ApplicationClient a = new ApplicationClient();
    }
    public static void main(String[] args) {
        tp1q13();
    }
}
