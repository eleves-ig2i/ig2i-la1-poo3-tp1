package org.ig2i.client;

import javax.swing.*;

public class ApplicationClientGUIForm {
    private JTextArea conversation;
    private JLabel labelEnvoiMessage;
    private JTextField messageInput;
    private JButton envoyerButton;
    private JPanel rootPanel;


    public JPanel getRootPanel() {
        return rootPanel;
    }

    public ApplicationClientGUIForm()
    {
        // Avec Intellij Idea, les champs sont tous initialisés avant le constructeur appelé.
        // Pour afficher une fenêtre : cf https://tutorials.tinyappco.com/Java/SwingGUI

    }
}
