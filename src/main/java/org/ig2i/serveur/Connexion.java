package org.ig2i.serveur;


import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexion extends Thread
{
    private Socket socketFlux;

    private BufferedReader in;

    private PrintWriter out;

    public Connexion(@NotNull Socket socketFlux) throws IOException {
        this.socketFlux = socketFlux;

        InputStreamReader isr = new InputStreamReader(socketFlux.getInputStream());
        in = new BufferedReader(isr);

        out = new PrintWriter(socketFlux.getOutputStream(),true);
        // autoFlush à true permet de vider le tampon à chaque appel de printIn().
        // Le message est envoyé au serveur que lorsque le tampon est vdé.
    }


}
