package PacotesCodigoFonte.Tela;

import PacotesCodigoFonte.Dal.ModuloConexao;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class TelaUsuario extends JInternalFrame{
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    JTextArea idfT;
    JTextArea nomefT;
    JButton readBtn;
    JTextArea fonefT;
    JTextArea loginfT;
    JTextArea senhafT;
    JComboBox perfilF;
    public TelaUsuario() {
        conexao = ModuloConexao.conector();
        
        setTitle("Usuarios");
        setSize(780, 620);
        setLayout(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        JLabel idF = new JLabel("ID");
        idF.setBounds(10, 20, 20, 20);
        add(idF);
        idfT = new JTextArea();
        idfT.setBounds(65, 20, 50, 20);
        idfT.setBorder(border);
        add(idfT);

        JLabel nomeF = new JLabel("Nome");
        nomeF.setBounds(10, 60, 70, 20);
        add(nomeF);
        nomefT = new JTextArea();
        nomefT.setBounds(65, 60, 200, 20);
        nomefT.setBorder(border);
        add(nomefT);

        JLabel foneF = new JLabel("Telefone");
        foneF.setBounds(10, 100, 70, 20);
        add(foneF);
        fonefT = new JTextArea();
        fonefT.setBounds(65, 100, 100, 20);
        fonefT.setBorder(border);
        add(fonefT);

        JLabel loginF = new JLabel("Login");
        loginF.setBounds(250, 100, 70, 20);
        add(loginF);
        loginfT = new JTextArea();
        loginfT.setBounds(290, 100, 100, 20);
        loginfT.setBorder(border);
        add(loginfT);

        JLabel senhaF = new JLabel("Senha");
        senhaF.setBounds(10, 140, 70, 20);
        add(senhaF);
        senhafT = new JTextArea();
        senhafT.setBounds(65, 140, 100, 20);
        senhafT.setBorder(border);
        add(senhafT);

        String[] perfisCombo = {"Admin", "User"};
        perfilF = new JComboBox(perfisCombo);
        perfilF.setBounds(250, 140, 70, 20);
        perfilF.setBorder(null);
        perfilF.setBorder(border);
        add(perfilF);

        JButton createBtn = new JButton("Adicionar");
        createBtn.setBounds(50, 250, 70, 30);
        createBtn.setBorder(null);
        createBtn.setBorder(border);
        createBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createBtn.addActionListener(e -> adicionar());
        add(createBtn);

        readBtn = new JButton("Consultar");
        readBtn.setBounds(150, 250, 70, 30);
        readBtn.setBorder(null);
        readBtn.setBorder(border);
        readBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        readBtn.addActionListener(e -> consultar());
        add(readBtn);

        JButton updateBtn = new JButton("Alterar");
        updateBtn.setBounds(250, 250, 70, 30);
        updateBtn.setBorder(null);
        updateBtn.setBorder(border);
        updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateBtn.addActionListener(e -> alterar());
        add(updateBtn);

        JButton deleteBtn = new JButton("Remover");
        deleteBtn.setBounds(350, 250, 70, 30);
        deleteBtn.setBorder(null);
        deleteBtn.setBorder(border);
        deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> remover());
        add(deleteBtn);

        setVisible(true);

    }
    private void consultar() {
        String sql = "select * from dbusuarios where iduser=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idfT.getText());

            rs = pst.executeQuery();

            if(rs.next()) {
                nomefT.setText(rs.getString(2));
                fonefT.setText(rs.getString(3));
                loginfT.setText(rs.getString(4));
                senhafT.setText(rs.getString(5));

                // a Linha abaixo se refere ao combobox

                perfilF.setSelectedItem(rs.getString(6));

            } else {
                JOptionPane.showMessageDialog(null, "[ERRO] Id de usuario invalido");
                idfT.setText(null);
                nomefT.setText(null);
                fonefT.setText(null);
                loginfT.setText(null);
                senhafT.setText(null);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    } 
    private void adicionar() {
        String sql = "insert into dbusuarios(iduser,usuarios,fone,login,senha,perfil) values(?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idfT.getText());
            pst.setString(2, nomefT.getText());
            pst.setString(3, fonefT.getText());
            pst.setString(4, loginfT.getText());
            pst.setString(5, senhafT.getText());
            pst.setString(6, perfilF.getSelectedItem().toString());

            if (idfT.getText().isEmpty() || nomefT.getText().isEmpty() || loginfT.getText().isEmpty() || loginfT.getText().isEmpty() || senhafT.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {


                int dsada = pst.executeUpdate();

                if (dsada > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario adicionado com sucesso");
                } else {
                    idfT.setText(null);
                    nomefT.setText(null);
                    fonefT.setText(null);
                    loginfT.setText(null);
                    senhafT.setText(null);
                }
            }
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }  
    private void alterar() {
        String sql = "update dbusuarios set usuarios = ?, fone = ?, login = ?, senha = ?, perfil = ? where iduser = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomefT.getText());
            pst.setString(2, fonefT.getText());
            pst.setString(3, loginfT.getText());
            pst.setString(4, senhafT.getText());
            pst.setString(5, perfilF.getSelectedItem().toString());
            pst.setString(6, idfT.getText());

            if (idfT.getText().isEmpty() || nomefT.getText().isEmpty() || loginfT.getText().isEmpty() || senhafT.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {
                int sqlBase = pst.executeUpdate();

                if(sqlBase > 0) {
                    JOptionPane.showMessageDialog(null, "Update feito com sucesso");

                } else {
                    idfT.setText(null);
                    nomefT.setText(null);
                    fonefT.setText(null);
                    loginfT.setText(null);
                    senhafT.setText(null);
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() {
        int recusar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esse usuario?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (recusar==JOptionPane.YES_OPTION) {
            String sql = "delete from dbusuarios where iduser=?";
        
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, idfT.getText());

                int sqlBased = pst.executeUpdate();

                if (sqlBased > 0 ) {
                    JOptionPane.showMessageDialog(null, "Usuario apagado com sucesso");
                    idfT.setText(null);
                    nomefT.setText(null);
                    fonefT.setText(null);
                    loginfT.setText(null);
                    senhafT.setText(null);
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
}
