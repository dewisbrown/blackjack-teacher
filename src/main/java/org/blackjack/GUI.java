package org.blackjack;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JLabel dealerLabel;
    private JLabel playerLabel;
    private JLabel stats;

    private BlackjackGUI bj;

    public GUI() {
        setTitle("Blackjack Practicer");
        setPreferredSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContentPane());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        bj = new BlackjackGUI();
    }

    private Container createContentPane() {
        JPanel main = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(10, 10, 10, 10);

        JPanel labelPanel = getLabelPanel();
        JPanel buttonPanel = getButtonPanel();

        main.add(labelPanel, gc);
        gc.gridx++;
        main.add(buttonPanel, gc);

        return main;
    }

    private JPanel getLabelPanel() {
        JPanel labelPanel = new JPanel(new GridLayout(4, 1));

        dealerLabel = new JLabel("dealer");
        playerLabel = new JLabel("player");
        stats = new JLabel("stats");

        labelPanel.add(dealerLabel);
        labelPanel.add(playerLabel);
        labelPanel.add(stats);

        return labelPanel;
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        JButton hit = new JButton("HIT");
        hit.setOpaque(true);
        hit.addActionListener(e -> {
            if (bj.canHit()) {
                if (bj.isCorrectAction(Action.HIT)) {
                    hit.setBackground(new Color(0, 255, 0));
                    System.out.println("Correct!");
                } else {
                    hit.setBackground(new Color(255, 0, 0));
                    System.out.println("Wrong! You should have " + bj.getCorrectAction().toString());
                }

                bj.hitPlayer();
                updateLabels();

                if (bj.checkBust()) {
                    updateLabels();
                }
            } else {
                System.out.println("Cannot hit, player at 21 or more.");
            }
        });

        JButton stand = new JButton("STAND");
        stand.setOpaque(true);
        stand.addActionListener(e -> {
            if (bj.isCorrectAction(Action.STAY)) {
                stand.setBackground(new Color(0, 255, 0));
                System.out.println("Correct!");
            } else {
                stand.setBackground(new Color(255, 0, 0));
                System.out.println("Wrong! You should have " + bj.getCorrectAction().toString());
            }

            bj.hitDealer();
            updateLabels();
        });

        JButton doubleDown = new JButton("DOUBLE");
        doubleDown.setOpaque(true);
        doubleDown.addActionListener(e -> {
            if (bj.canDouble()) {
                if (bj.isCorrectAction(Action.DOUBLE)) {
                    doubleDown.setBackground(new Color(0, 255, 0));
                    System.out.println("Correct!");
                } else {
                    doubleDown.setBackground(new Color(255, 0, 0));
                    System.out.println("Wrong! You should have " + bj.getCorrectAction().toString());
                }

                bj.hitPlayer();
                bj.hitDealer();
                updateLabels();
            } else {
                System.out.println("You cannot double!");
            }
        });

        JButton split = new JButton("SPLIT");
        split.setOpaque(true);
        split.addActionListener(e -> {
            bj.split();
        });

        JButton start = new JButton("NEW HAND");
        start.addActionListener(e -> {
            hit.setBackground(null);
            stand.setBackground(null);
            doubleDown.setBackground(null);

            bj.dealCards();
            updateLabels();
        });

        gc.gridx = 0;
        gc.gridy = GridBagConstraints.RELATIVE;
        gc.fill = GridBagConstraints.BOTH;
        gc.ipadx = 10;
        gc.ipady = 10;

        buttonPanel.add(start, gc);
        buttonPanel.add(hit, gc);
        buttonPanel.add(stand, gc);
        buttonPanel.add(doubleDown, gc);
        buttonPanel.add(split, gc);

        return buttonPanel;
    }

    private void updateLabels() {
        dealerLabel.setText(bj.getDealerHand());
        playerLabel.setText(bj.getPlayerHand());
        stats.setText(bj.getStats());
    }
}
