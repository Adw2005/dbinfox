package PacotesCodigoFonte.Tela;

import PacotesCodigoFonte.Dal.ModuloConexao;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;



public class TelaCliente extends JInternalFrame{
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultTableModel modeloTabela;
    JTable tabelacli;
    JTextField nomecliTf;
    JTextField enderecocliTf;
    JTextField fonecliTf;
    JTextField emailcliTf;
    JButton createBtnCli;
    JTextField cepcliTf;
    JLabel preenchaCli;
    JTextField pesquisaCli;
    JButton updateBtnCli;
    JButton deleteBtnCli;
    int idClienteSelecionado = -1;

    public boolean validiaCampos(JTextField nomecliTf, JTextField fonecliTf, JTextField enderecocliTf, JTextField emailcliTf, JTextField cepcliTf) {
        return nomecliTf.getText().isEmpty() || fonecliTf.getText().isEmpty() || enderecocliTf.getText().isEmpty() || emailcliTf.getText().isEmpty() || cepcliTf.getText().isEmpty();
    }
  
    
    public TelaCliente() {
        conexao = ModuloConexao.conector();
        
        setTitle("Cadastro de Clientes");
        setSize(780, 620);
        setLayout(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent e) {
                listarTodosClientes();
            }
        });


        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        pesquisaCli = new JTextField();
        pesquisaCli.setBounds(10, 20, 200, 22);
        pesquisaCli.setBorder(border);
        pesquisaCli.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            pesquisarCliente();
            }
        });

        add(pesquisaCli);

        JLabel pesquisaLabel = new JLabel("Pesquisa");
        pesquisaLabel.setBounds(220, 20, 70, 20);
        add(pesquisaLabel);

        preenchaCli = new JLabel("* Preencha os campos");
        preenchaCli.setBounds(320, 5, 150, 20);
        add(preenchaCli);


        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Endereço");
        modeloTabela.addColumn("Telefone");
        modeloTabela.addColumn("Email");
        modeloTabela.addColumn("CEP");

        tabelacli = new JTable(modeloTabela);
        tabelacli.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setar_campos();
            }
        });

        JScrollPane scroll = new JScrollPane(tabelacli);
        scroll.setBounds(10, 60, 445, 70);
        scroll.setBorder(border);
        add(scroll);


        JLabel nomecliLabel = new JLabel("Nome");
        nomecliLabel.setBounds(10, 140, 70, 20);
        add(nomecliLabel);

        nomecliTf = new JTextField();
        nomecliTf.setBounds(70, 140, 300, 22);
        nomecliTf.setBorder(border);
        add(nomecliTf);

        JLabel enderecocliLabel = new JLabel("Endereço");
        enderecocliLabel.setBounds(10, 180, 70, 20);
        add(enderecocliLabel);

        enderecocliTf = new JTextField();
        enderecocliTf.setBounds(70, 180, 300, 22);
        enderecocliTf.setBorder(border);
        add(enderecocliTf);

        JLabel fonecliLabel = new JLabel("Telefone");
        fonecliLabel.setBounds(10, 220, 70, 20);
        add(fonecliLabel);

        fonecliTf = new JTextField();
        fonecliTf.setBounds(70, 220, 300, 22);
        fonecliTf.setBorder(border);
        add(fonecliTf);

        JLabel emailcliLabel = new JLabel("Email");
        emailcliLabel.setBounds(10, 260, 70, 20);
        add(emailcliLabel);

        emailcliTf = new JTextField();
        emailcliTf.setBounds(70, 260, 300, 22);
        emailcliTf.setBorder(border);
        add(emailcliTf);

        JLabel cepcliLabel = new JLabel("CEP");
        cepcliLabel.setBounds(10, 300, 70, 20);
        add(cepcliLabel);

        cepcliTf = new JTextField();
        cepcliTf.setBounds(70, 300, 300, 22);
        cepcliTf.setBorder(border);
        add(cepcliTf);

        createBtnCli = new JButton("Adicionar");
        createBtnCli.setBounds(90, 340, 70, 30);
        createBtnCli.setBorder(null);
        createBtnCli.setBorder(border);
        createBtnCli.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createBtnCli.addActionListener(e -> adicionar());
        add(createBtnCli);

        updateBtnCli = new JButton("Alterar");
        updateBtnCli.setBounds(180, 340, 70, 30);
        updateBtnCli.setBorder(null);
        updateBtnCli.setBorder(border);
        updateBtnCli.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateBtnCli.addActionListener(e -> alterar());
        add(updateBtnCli);

        deleteBtnCli = new JButton("Remover");
        deleteBtnCli.setBounds(270, 340, 70, 30);
        deleteBtnCli.setBorder(null);
        deleteBtnCli.setBorder(border);
        deleteBtnCli.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteBtnCli.addActionListener(e -> remover());
        add(deleteBtnCli);

        setVisible(true);
    }

    private void adicionar() {
        String sql = "Insert into tbclientes(nomecli, telefonecli, enderecocli, emailcli, cepcli) values(?, ?, ?, ?, ?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomecliTf.getText());
            pst.setString(2, fonecliTf.getText());
            pst.setString(3, enderecocliTf.getText());
            pst.setString(4, emailcliTf.getText());
            pst.setString(5, cepcliTf.getText());

            if (this.validiaCampos(nomecliTf, fonecliTf, enderecocliTf, emailcliTf, cepcliTf)) {

                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {
                int sqlBased =  pst.executeUpdate();

                if (sqlBased > 0 ) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");
                    listarTodosClientes();
                } else {
                    nomecliTf.setText(null);
                    fonecliTf.setText(null);
                    enderecocliTf.setText(null);
                    emailcliTf.setText(null);
                    cepcliTf.setText(null);                  
                }
                
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void alterar() {
        if (idClienteSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente na tabela");
            return;
        }

        String sql = "update tbclientes set nomecli = ?, telefonecli = ?, enderecocli = ?, emailcli = ?,  cepcli = ? where idclient = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomecliTf.getText());
            pst.setString(2, fonecliTf.getText());
            pst.setString(3, enderecocliTf.getText());
            pst.setString(4, emailcliTf.getText());
            pst.setString(5, cepcliTf.getText());
            pst.setInt(6, idClienteSelecionado);

            if(nomecliTf.getText().isEmpty() || fonecliTf.getText().isEmpty() || enderecocliTf.getText().isEmpty() || emailcliTf.getText().isEmpty() || cepcliTf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos Obrigatorios");
            } else {
                int sqlBased = pst.executeUpdate();

                if(sqlBased > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso");
                    listarTodosClientes();
                } else {
                    nomecliTf.setText(null);
                    fonecliTf.setText(null);
                    enderecocliTf.setText(null);
                    emailcliTf.setText(null);
                    cepcliTf.setText(null);
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     private void remover() {
        if (idClienteSelecionado == -1) {
        JOptionPane.showMessageDialog(null, "Selecione um cliente na tabela");
        return;
    }
        int recusar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esse usuario?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (recusar==JOptionPane.YES_OPTION) {
            
            String sql = "delete from tbclientes where idclient=?";
        
            try {
                pst = conexao.prepareStatement(sql);
                pst.setInt(1, idClienteSelecionado);

                int sqlBased = pst.executeUpdate();

                if (sqlBased > 0 ) {
                    JOptionPane.showMessageDialog(null, "Usuario apagado com sucesso");
                    listarTodosClientes();
                    nomecliTf.setText(null);
                    fonecliTf.setText(null);
                    enderecocliTf.setText(null);
                    emailcliTf.setText(null);
                    cepcliTf.setText(null);
                    idClienteSelecionado = -1;
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    public void listarTodosClientes() {
    String sql = "select idclient, nomecli, enderecocli, telefonecli, emailcli, cepcli from tbclientes";

    try {
        pst = conexao.prepareStatement(sql);
        rs = pst.executeQuery();

        modeloTabela.setRowCount(0); // limpa tabela

        while (rs.next()) {
            modeloTabela.addRow(new Object[]{
                rs.getInt("idclient"),
                rs.getString("nomecli"),
                rs.getString("enderecocli"),
                rs.getString("telefonecli"),
                rs.getString("emailcli"),
                rs.getString("cepcli")
            });
        }

        // 🔥 força atualização visual
        modeloTabela.fireTableDataChanged();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}

    private void pesquisarCliente() {
    if (pesquisaCli.getText().trim().isEmpty()) {
        listarTodosClientes();
        return;
    }
    
    String sql = "select idclient, nomecli, enderecocli, telefonecli, emailcli, cepcli "
               + "from tbclientes where nomecli like ?";

    try {
        pst = conexao.prepareStatement(sql);
        pst.setString(1, pesquisaCli.getText() + "%");
        rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tabelacli.getModel();

        model.setRowCount(0); // limpa tabela

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("idclient"),
                rs.getString("nomecli"),
                rs.getString("enderecocli"),
                rs.getString("telefonecli"),
                rs.getString("emailcli"),
                rs.getString("cepcli")
            });
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
    public void setar_campos() {
        int setar = tabelacli.getSelectedRow();
        if(setar == -1 ) return;
        
        int linhaModel = tabelacli.convertRowIndexToModel(setar);

        idClienteSelecionado = Integer.parseInt(tabelacli.getModel().getValueAt(linhaModel, 0).toString());

        nomecliTf.setText(tabelacli.getModel().getValueAt(linhaModel, 1).toString());
        enderecocliTf.setText(tabelacli.getModel().getValueAt(linhaModel, 2).toString());
        fonecliTf.setText(tabelacli.getModel().getValueAt(linhaModel, 3).toString());
        emailcliTf.setText(tabelacli.getModel().getValueAt(linhaModel, 4).toString());
        cepcliTf.setText(tabelacli.getModel().getValueAt(linhaModel, 5).toString());
    }
    
}
