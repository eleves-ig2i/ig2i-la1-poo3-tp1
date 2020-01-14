package org.ig2i.chat2i.client;

import javax.swing.*;

public class ApplicationClientGUI {
    private JTextArea conversation;
    private JLabel labelEnvoiMessage;
    private JTextField messageInput;
    private JButton envoyerButton;
    private JPanel rootPanel;


    public JPanel getRootPanel() {
        return rootPanel;
    }


    public JTextArea getConversation() {
        return conversation;
    }

    public JTextField getMessageInput() {
        return messageInput;
    }

    public JButton getEnvoyerButton() {
        return envoyerButton;
    }

    public ApplicationClientGUI()
    {
        // Avec Intellij Idea, les champs sont tous initialisés avant le constructeur appelé.
        // Pour afficher une fenêtre : cf https://tutorials.tinyappco.com/Java/SwingGUI

    }
}
