package karaokesonglist.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SplashScreen extends JWindow {

    Container panel;
    ImageIcon imagem;
    JLabel jLabel;
    JProgressBar barra;
    JLabel texto;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension dimension = toolkit.getScreenSize();
    Image bi = Toolkit.getDefaultToolkit().getImage("resources/images/sunfly.jpg");

    public SplashScreen() {
        barra = new JProgressBar();
        barra.setPreferredSize(new Dimension(200, 5));
        texto = new JLabel();
        texto.setFont(new Font("Serif", Font.PLAIN, 10));
        texto.setText("Verificando chave");
        jLabel = new JLabel();
        imagem = new ImageIcon(bi);
        jLabel.setIcon(imagem);
        panel = new Container();
        this.setContentPane(panel);
        panel.setLayout(null);
        panel.add(jLabel);
        jLabel.setBounds(0, 0, jLabel.getPreferredSize().width, jLabel.getPreferredSize().height);
        barra.setBounds(100, 200, barra.getPreferredSize().width, barra.getPreferredSize().height);
        texto.setBounds(100, 230, 300, texto.getPreferredSize().height);
        jLabel.setLayout(null);
        jLabel.add(barra);
        barra.setIndeterminate(true);
        jLabel.add(texto);
        System.out.println("opaco: " + texto.isOpaque());
        //panel.add(jLabel, abcImage);
        //panel.add(barra, abcBarra);
        //this.setLocation(new Point(((int) dimension.getWidth() / 2) - (imagem.getIconWidth() / 2), ((int) dimension.getHeight() / 2) - (imagem.getIconHeight() / 2)));
        this.setLocationRelativeTo(null);
        this.getContentPane().setSize(imagem.getIconWidth(), imagem.getIconHeight());
        this.getContentPane().revalidate();
        System.out.println("Content pane: " + this.getContentPane().toString());
        this.setSize(imagem.getIconWidth(), imagem.getIconHeight());
    }

    public void carregouChave() {
        texto.setText("Verificando versão");
    }

    public void baixandoNovaVersao() {
        texto.setText("Baixando nova versão");
    }

    public void processarVerificarVersao() {
        texto.setText("Verificando versão");
    }

    public void processar() {
        texto.setText("Atualizando para nova versão");
    }

    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);
    }
}
