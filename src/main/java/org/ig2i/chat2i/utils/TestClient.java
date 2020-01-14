package org.ig2i.chat2i.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient extends Thread
{
    private String adresseIpServeur;

    private int portEcouteServeur;

    public TestClient(String adresseIpServeur, int portEcouteServeur) {
        this.adresseIpServeur = adresseIpServeur;
        this.portEcouteServeur = portEcouteServeur;
    }

    public String getAdresseIpServeur() {
        return adresseIpServeur;
    }

    public int getPortEcouteServeur() {
        return portEcouteServeur;
    }

    @Override
    public void run() {
        connect();
    }

    public void connect()
    {
        try {
            InetAddress adresseIp = InetAddress.getByName( adresseIpServeur);
            System.out.println(adresseIp);
            Socket socketFlux = new Socket(adresseIp, portEcouteServeur);
            System.out.println("Connexion avec le serveur " + adresseIpServeur + " réussie.");


            PrintWriter out = new PrintWriter(socketFlux.getOutputStream(),true);
            // autoFlush à true permet de vider le tampon à chaque appel de printIn().
            // Le message est envoyé au serveur que lorsque le tampon est vdé.
            out.println("bla bla bla");
            out.close();
            socketFlux.close();
        } catch (UnknownHostException e) {
            System.err.println("Hôte " + adresseIpServeur + " non connu.");
        } catch ( ConnectException e) {
            System.err.println("Connexion refusée au port " + portEcouteServeur + " du serveur " + adresseIpServeur + ".");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
