/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SplashWindow.java
 *
 * Created on Jun 11, 2013, 10:53:37 PM
 */
package com.rodcastro.karaokesonglist.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import com.rodcastro.karaokesonglist.Main;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author rodcastro
 */
public class SplashWindow extends javax.swing.JFrame {

    private ImagePanel background;
    private JProgressBar progressBar;
    private LoadingBarListener listener;
    private JLabel lblMessage;
    private MainWindow mainFrame;

    public SplashWindow(MainWindow frame) {
        initComponents();
        try {
            mainFrame = frame;
            background = new ImagePanel("resources/images/sunfly.jpg");
            background.setBounds(0, 0, 480, 360);
            Container panel = new Container();
            this.setContentPane(panel);
            this.getContentPane().add(background);
            JLabel lblTitle1 = new JLabel("Karaokê Sunfly");
            JLabel lblTitle2 = new JLabel("Lista de Músicas");
            lblMessage = new JLabel("Preparando...");
            Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
            lblTitle1.setFont(titleFont);
            lblTitle2.setFont(titleFont);
            background.setLayout(null);
            background.add(lblTitle1);
            background.add(lblTitle2);
            background.add(lblMessage);
            lblTitle1.setBounds(250, 160, 250, 30);
            lblTitle2.setBounds(240, 195, 250, 30);
            lblMessage.setBounds(240, 250, 200, 25);
            progressBar = new JProgressBar();
            progressBar.setPreferredSize(new Dimension(210, 10));
            background.add(progressBar);
            progressBar.setBounds(240, 280, 210, 10);
            setIconImage(ImageIO.read(new File("resources/images/icon.png")));
            listener = new LoadingBarListener() {

                @Override
                public void onUpdateBar(int value, String message) {
                    progressBar.setValue(value);
                    lblMessage.setText(message);
                }

                @Override
                public void onChangeMaxValue(int maxValue) {
                    progressBar.setMaximum(maxValue);
                }

                @Override
                public void onFinish() {
                    mainFrame.searchSongs("");
                    mainFrame.setVisible(true);
                    SplashWindow.this.dispose();
                }
            };
            Main.getRepository().setListener(listener);
        } catch (Exception ex) {
        }
        setSize(480, 370);
        setLocationRelativeTo(null);
    }

    public void loadSongs() {
        Main.getRepository().loadSongs();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sunfly Song Browser");
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
