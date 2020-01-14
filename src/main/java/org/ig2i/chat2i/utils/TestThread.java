package org.ig2i.chat2i.utils;

public class TestThread extends Thread
{
    @Override
    public void run() {
        while(!isInterrupted())
        {
            System.out.println("Serveur en vie");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Le thread courant ne peut pas être interrompu.");
                Thread.currentThread().interrupt();
            }
        }
    }
}
