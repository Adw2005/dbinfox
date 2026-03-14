package PacotesCodigoFonte.Tela;

import PacotesCodigoFonte.Dal.ModuloConexao;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;
public class TelaLogin extends JFrame{
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    JTextArea usu;
    JPasswordField senha;


    public TelaLogin(){
        conexao = ModuloConexao.conector();
        
        JFrame tela = new JFrame("Tela teste");
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setSize(400, 250);
        tela.setLocationRelativeTo(null);
        tela.setLayout(null);
        tela.setResizable(false);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        JLabel label1 = new JLabel("Usuario: ");
        label1.setBounds(20, 20, 100, 20);
        tela.add(label1);

        JLabel label2 = new JLabel("Senha: ");
        label2.setBounds(20, 70, 100, 20); tela.add(label2);

        usu = new JTextArea();
        usu.setBounds(80, 20, 150, 20);
        usu.setBorder(border);
        tela.add(usu);

        senha = new JPasswordField();
        senha.setBounds(80, 70, 150, 20);
        senha.setBorder(border);
        tela.add(senha);

        JButton butao = new JButton("Login");
        butao.setBounds(250, 150, 100, 30);
        butao.setBorder(border);
        butao.addActionListener(e -> {
        logar();
        SwingUtilities.getWindowAncestor(butao).dispose();
        }); 
        tela.add(butao);

        JLabel status = new JLabel();
        status.setBounds(40, 125, 100, 30);
        tela.add(status);

        tela.setVisible(true); 

        // A linha abaixo serve de apoio para o status
        // System.out.println(conexao);

        if (conexao != null) {
            status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PacotesCodigoFonte/Icons/db_ok_icon1.png")));
        
        } else {
            status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PacotesCodigoFonte/Icons/db_error_icon1.png")));
        }
        
    }
    public void logar() {
        
        String sql = "select * from dbusuarios where login=? and senha=?";
        try {
            // as linhas abaixo pegam os valores da caixa de texto, e o valor dessas informação são atriubuidas ao ?
            pst = conexao.prepareStatement(sql);
            pst.setString(1, usu.getText());
            pst.setString(2, String.valueOf(senha.getPassword()));

            // a linha abaixo executa a query

            rs = pst.executeQuery();

            if (rs.next()) {

                // a linha a baixo obetem as informa~çoes da coluna
                String perfil = rs.getString(6);

                if(perfil.equals("Admin")) {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    TelaPrincipal.relatorios.setEnabled(true);
                    TelaPrincipal.usuario.setEnabled(true);
                    TelaPrincipal.mUsuario.setText(rs.getString(2));
                    TelaPrincipal.mUsuario.setForeground(Color.RED);
                    this.dispose();
                    conexao.close();
                } else {
                    TelaPrincipal principal = new TelaPrincipal();
                    TelaPrincipal.mUsuario.setText(rs.getString(2));
                    principal.setVisible(true);
                    this.dispose();
                    conexao.close();
                }

            } else {
                JOptionPane.showMessageDialog(null, "usuario e/ou senhas invalidos");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void btnlogin(java.awt.event.ActionEvent evt) {
        logar();
    }
}

