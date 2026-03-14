package PacotesCodigoFonte.Tela;
import javax.swing.*;

public class TelaSobre extends JFrame {
    public TelaSobre() {
        setTitle("Sobre");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);

        JLabel sobre1 = new JLabel("Sistema de Controle Para Ordem e Serviços");
        sobre1.setBounds(10, 20, 300, 20);
        add(sobre1);

        JLabel sobre2 = new JLabel("Versao 1.0.1");
        sobre2.setBounds(10, 40, 300, 20);
        add(sobre2);

        JLabel sobre3 = new JLabel("@ 2026 - Desenvolvido por David Alex");
        sobre3.setBounds(10, 80, 300, 20);
        add(sobre3);

        JLabel sobre4 = new JLabel("<html>Este software utiliza componentes de terceiros licenciados<br>" + "sob licenças livres (MIT, Apache 2.0, GPL).</html>");
        sobre4.setBounds(10, 100, 400, 60);
        add(sobre4);
    }
   
}
