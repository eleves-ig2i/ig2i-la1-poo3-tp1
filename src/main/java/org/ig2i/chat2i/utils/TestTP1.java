package org.ig2i.chat2i.utils;

import static java.lang.Thread.sleep;

public class TestTP1 {

    public static void question1A3() throws InterruptedException {
        TestThread testThread = new TestThread();
        testThread.start();
        sleep(1500);
        testThread.interrupt();
    }

    public static void question4()
    {
        TestServeur testServeur = new TestServeur();
        testServeur.start();
    }

    public static void question5() throws InterruptedException {
        TestServeur testServeur = new TestServeur();
        TestClient testClient1 = new TestClient("127.0.0.1",49152);
        TestClient testClient2 = new TestClient("127.0.0.1",49152);
        TestClient testClient3 = new TestClient("172.22.70.31",50001); // bloque l'appel
        TestClient testClient4 = new TestClient("127.0.0.1",49153); // Connexion refusée
        testServeur.start();
        sleep(1000);
        System.out.println("Main - tentative de connexion du client 1");
        testClient1.start();
        sleep(1000);
        System.out.println("Main - tentative de connexion du client 1");
        testClient2.start();
        sleep(1000);
        testClient3.start();
        sleep(1000);
        testClient4.start();
    }

    public static void test_voisin()
    {
        TestClient testClient1 = new TestClient("192.168.43.249",55555);
        testClient1.start();
    }

    public static void main(String[] args) throws InterruptedException {
        //question1A3();
        //question4();
        question5();
        //test_voisin();
    }
}
