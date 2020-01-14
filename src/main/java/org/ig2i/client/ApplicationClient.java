package org.ig2i.client;

import javax.swing.*;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationClient extends JFrame
{

    private ApplicationClientGUIForm clientDesigner;

    private Socket socketFlux;

    private PrintWriter out;

    public ApplicationClient(String adresse){
       super("Chat2i");
       clientDesigner = new ApplicationClientGUIForm();
       this.setContentPane( clientDesigner.getRootPanel() );
       this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
       this.pack();
       this.setVisible(true);
    }
}
