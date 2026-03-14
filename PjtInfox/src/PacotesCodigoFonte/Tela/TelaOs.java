package PacotesCodigoFonte.Tela;

import PacotesCodigoFonte.Dal.ModuloConexao;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;


public class TelaOs extends JInternalFrame{
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    JPanel painel1;
    DefaultTableModel modeloTabela;
    JTable tabelacli;
    JTextField pesquisaTfPcli; 
    JTextField idclientTf;
    JLabel pesquisatfcliLabel;
    JRadioButton btnOrc;
    JRadioButton btnOs;
    ButtonGroup grupobtn;
    String tipo;
    JComboBox OsComboBox;
    JTextField equipamentocliTf;
    JTextField defeitocliTf;
    JTextField servicocliTf;
    JTextField tecnicocliTf;
    JTextField valortotcliTf;
    JTextField numOsTF;
    JTextField dataOsTF;
    JButton adicionarcliBtn;
    int osClienteSelecionado = -1;
    JButton imprimircliBtn;
    JButton removercliBtn;
    JButton alterarcliBtn;
    JButton consultarcliBtn;

    public boolean validacampos(JComboBox OsComboBox, JTextField idclientTf, String tipo, JTextField equipamentocliTf, JTextField defeitocliTf, JTextField servicocliTf, JTextField tecnicocliTf, JTextField valortotcliTf) {
        return idclientTf.getText().isEmpty() || tipo.isEmpty() || OsComboBox.getSelectedItem() == null || equipamentocliTf.getText().isEmpty() || defeitocliTf.getText().isEmpty() || 
        servicocliTf.getText().isEmpty() || tecnicocliTf.getText().isEmpty() || valortotcliTf.getText().isEmpty();

    }

    public void limpar () {
        OsComboBox.setSelectedItem(null);
        equipamentocliTf.setText(null);
        defeitocliTf.setText(null);
        servicocliTf.setText(null);
        tecnicocliTf.setText(null);
        valortotcliTf.setText(null);
        idclientTf.setText(null);

        adicionarcliBtn.setEnabled(true);
        consultarcliBtn.setEnabled(true);
        tabelacli.setVisible(true);

        alterarcliBtn.setEnabled(false);
        removercliBtn.setEnabled(false);
        imprimircliBtn.setEnabled(false);

    }

    public TelaOs() {
        conexao = ModuloConexao.conector();


        Border border = BorderFactory.createLineBorder(Color.black,1);

        setTitle("OS");
        setSize(780,620);
        setLayout(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent e) {
                listarTodosClientes();
                grupobtn = new ButtonGroup();
                grupobtn.add(btnOrc);
                grupobtn.add(btnOs);
                btnOrc.setSelected(true);

                limpar();
                tipo = "Orçamento";
            }
        });

        

        painel1 = new JPanel();
        painel1.setSize(250, 100);
        painel1.setBounds(10, 10, 250, 100);
        painel1.setBorder(BorderFactory.createEtchedBorder());
        painel1.setLayout(null);
        add(painel1);

        JLabel numOS = new JLabel("N° OS");
        numOS.setBounds(10, 10, 40, 20);
        painel1.add(numOS);

        numOsTF = new JTextField();
        numOsTF.setBounds(10, 40, 70, 22);
        numOsTF.setBorder(border);
        numOsTF.setEditable(false);
        painel1.add(numOsTF);

        JLabel dataOS = new JLabel("Data");
        dataOS.setBounds(100, 10, 40, 20);
        painel1.add(dataOS);

        dataOsTF = new JTextField();
        dataOsTF.setBounds(100, 40, 130, 22);
        dataOsTF.setBorder(border);
        dataOsTF.setEditable(false);
        painel1.add(dataOsTF);

        btnOrc = new JRadioButton();
        btnOrc.setBounds(7, 70, 20, 20);
        painel1.add(btnOrc);

        JLabel orcLabel = new JLabel("Orçamento");
        orcLabel.setBounds(27, 70, 100, 20);
        painel1.add(orcLabel);


        btnOs = new JRadioButton();
        btnOs.setBounds(97, 70, 20, 20);
        btnOs.addActionListener(e ->{
            tipo = "Os";
        });
        painel1.add(btnOs);

        JLabel osLabel = new JLabel("Ordem e serviço");
        osLabel.setBounds(117, 70, 100, 20);
        painel1.add(osLabel);


        JLabel situacLabel = new JLabel("Situação");
        situacLabel.setBounds(10, 120, 70, 20);
        add(situacLabel);

        String[] ComboxOs = {"Na Bancada", "Entrega Ok", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado Pelo Cliente", "Retornou"};
        OsComboBox = new JComboBox(ComboxOs);
        OsComboBox.setBounds(70, 120, 200, 20);
        add(OsComboBox);

        JPanel painelClientes =  new JPanel();
        painelClientes.setLayout(null);
        painelClientes.setBounds(300, 10, 450, 200);
        painelClientes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Clientes"));
        add(painelClientes);

        pesquisaTfPcli = new JTextField();
        pesquisaTfPcli.setBounds(10, 20, 200, 22);
        pesquisaTfPcli.setBorder(border);
        pesquisaTfPcli.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            pesquisarCliente();
            }
        });
        painelClientes.add(pesquisaTfPcli);

        pesquisatfcliLabel = new JLabel("Pesquisa");
        pesquisatfcliLabel.setBounds(220, 20, 70, 20);
        painelClientes.add(pesquisatfcliLabel);

        JLabel idclientLabel = new JLabel("*Id");
        idclientLabel.setBounds(310, 20, 40, 20);
        painelClientes.add(idclientLabel);

        idclientTf = new JTextField();
        idclientTf.setBounds(350, 20, 60, 22);
        idclientTf.setBorder(border);
        idclientTf.setEditable(false);
        painelClientes.add(idclientTf);

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Telefone");

        tabelacli = new JTable(modeloTabela);
        tabelacli.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setar_campos();
            }
        });

        JScrollPane scroll = new JScrollPane(tabelacli);
        scroll.setBounds(10, 60, 425, 130);
        scroll.setBorder(border);
        painelClientes.add(scroll);

        JLabel equipamentocliLabel = new JLabel("*Equipamento");
        equipamentocliLabel.setBounds(10, 240, 100, 20);
        add(equipamentocliLabel);

        equipamentocliTf = new JTextField();
        equipamentocliTf.setBounds(100, 240, 500, 22);
        equipamentocliTf.setBorder(border);
        add(equipamentocliTf);

        JLabel defeitocliLabel = new JLabel("*Defeito");
        defeitocliLabel.setBounds(10, 280, 100, 20);
        add(defeitocliLabel);

        defeitocliTf = new JTextField();
        defeitocliTf.setBounds(100, 280, 500, 22);
        defeitocliTf.setBorder(border);
        add(defeitocliTf);

        JLabel servicocliLabel = new JLabel("Serviço");
        servicocliLabel.setBounds(10, 320, 100, 20);
        add(servicocliLabel);

        servicocliTf = new JTextField();
        servicocliTf.setBounds(100, 320, 500, 22);
        servicocliTf.setBorder(border);
        add(servicocliTf);

        JLabel tecnicocliLabel = new JLabel("Tecnico");
        tecnicocliLabel.setBounds(10, 360, 100, 20);
        add(tecnicocliLabel);

        tecnicocliTf = new JTextField();
        tecnicocliTf.setBounds(100, 360, 500, 22);
        tecnicocliTf.setBorder(border);
        add(tecnicocliTf);

        JLabel valortotcliLabel = new JLabel("Valor Total");
        valortotcliLabel.setBounds(10, 400, 100, 20);
        add(valortotcliLabel);

        valortotcliTf = new JTextField("0");
        valortotcliTf .setBounds(100, 400, 300, 22);
        valortotcliTf .setBorder(border);
        add(valortotcliTf );

        adicionarcliBtn = new JButton("Adicionar");
        adicionarcliBtn.setBounds(50,470,100,30);
        adicionarcliBtn.setBorder(border);
        adicionarcliBtn.addActionListener(e -> cadastrarCli());
        add(adicionarcliBtn);

        consultarcliBtn = new JButton("Consultar");
        consultarcliBtn.setBounds(170,470,100,30);
        consultarcliBtn.setBorder(border);
        consultarcliBtn.addActionListener(e -> consultarOs());
        add(consultarcliBtn);

        alterarcliBtn = new JButton("Alterar");
        alterarcliBtn.setBounds(290,470,100,30);
        alterarcliBtn.setBorder(border);
        alterarcliBtn.addActionListener(e -> alterar());
        add(alterarcliBtn);

        removercliBtn = new JButton("Remover");
        removercliBtn.setBounds(410,470,100,30);
        removercliBtn.setBorder(border);
        removercliBtn.addActionListener(e -> remover());
        add(removercliBtn);

        imprimircliBtn = new JButton("Imprimir");
        imprimircliBtn.setBounds(530,470,100,30);
        imprimircliBtn.setBorder(border);
        add(imprimircliBtn);

        setVisible(true);
        

    }   
    public void listarTodosClientes() {
    String sql = "select idclient as id, nomecli as nome, telefonecli as telefone " + 
    "from tbclientes";

    try {
        pst = conexao.prepareStatement(sql);
        rs = pst.executeQuery();

        modeloTabela.setRowCount(0); // limpa tabela

        while (rs.next()) {
            modeloTabela.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
            });
        }

        // 🔥 força atualização visual
        modeloTabela.fireTableDataChanged();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
    public void pesquisarCliente() {
    if (pesquisaTfPcli.getText().trim().isEmpty()) {
        listarTodosClientes();
        return;
    }
    
    String sql = "select idclient as id, nomecli as nome, telefonecli as telefone "
               + "from tbclientes where nomecli like ?";

    try {
        pst = conexao.prepareStatement(sql);
        pst.setString(1, pesquisaTfPcli.getText() + "%");
        rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tabelacli.getModel();

        model.setRowCount(0); // limpa tabela

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
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

        idclientTf.setText(tabelacli.getModel().getValueAt(linhaModel, 0).toString());
        pesquisaTfPcli.setText(tabelacli.getModel().getValueAt(linhaModel, 1).toString());

    }

    // metodo para dcadastrar uma os

    public void cadastrarCli() {
        if (this.validacampos(OsComboBox, idclientTf, tipo, equipamentocliTf, defeitocliTf, servicocliTf, tecnicocliTf, valortotcliTf)) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");           
        }
        String sql = "insert into tbos (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idclient) values (?,?,?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, tipo);
            pst.setString(2, OsComboBox.getSelectedItem().toString());
            pst.setString(3, equipamentocliTf.getText());
            pst.setString(4, defeitocliTf.getText());
            pst.setString(5, servicocliTf.getText());
            pst.setString(6, tecnicocliTf.getText());
            pst.setString(7, valortotcliTf.getText().replace(",", "."));
            pst.setString(8, idclientTf.getText());

            int sqlbased = pst.executeUpdate();

            if (sqlbased > 0) {
                JOptionPane.showMessageDialog(null, "Cliente Adicionado com Sucesso");
                limpar();
            }
            
        } catch (java.lang.NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Informe a situação da OS");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);       
        }
    }

    public void consultarOs() {
        String numOs = JOptionPane.showInputDialog("Numero da OS");
        String sql = "select os, date_format(data_os, '%d/%m/%Y - %Y : %i'), tipo, situacao, equipamento, defeito, servico, tecnico, valor, idclient from tbos where os = " + numOs;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                numOsTF.setText(rs.getString(1));
                dataOsTF.setText(rs.getString(2));

                String rbtipo =  rs.getString(3);
                if (rbtipo.equals("Os")) {
                    btnOs.setSelected(true);
                    tipo = "Os";
                } else {
                    btnOrc.setSelected(true);
                    tipo = "Orçamento";
                }

                OsComboBox.setSelectedItem(rs.getString(4));
                equipamentocliTf.setText(rs.getString(5));
                defeitocliTf.setText(rs.getString(6));
                servicocliTf.setText(rs.getString(7));
                tecnicocliTf.setText(rs.getString(8));
                valortotcliTf.setText(rs.getString(9));
                idclientTf.setText(rs.getString(10));

                adicionarcliBtn.setEnabled(false);
                consultarcliBtn.setEnabled(false);
                alterarcliBtn.setEnabled(true);
                removercliBtn.setEnabled(true);
                imprimircliBtn.setEnabled(true);

                pesquisaTfPcli.setBackground(new Color(238, 238, 238));
                tabelacli.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(null, "OS Não Cadastrada");
                OsComboBox.setSelectedItem(" ");
                equipamentocliTf.setText(null);
                defeitocliTf.setText(null);
                servicocliTf.setText(null);
                tecnicocliTf.setText(null);
                valortotcliTf.setText(null);
                idclientTf.setText(null);

                adicionarcliBtn.setEnabled(true);
                pesquisaTfPcli.setEnabled(true);
                tabelacli.setVisible(true);
            }
            
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS Invalida");
            limpar();
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }
        private void alterar() {
        String sql = "update tbos set tipo = ?, situacao = ?, equipamento = ?, defeito = ?, servico = ?, tecnico = ?, valor = ? where os = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, OsComboBox.getSelectedItem().toString());
            pst.setString(3, equipamentocliTf.getText());
            pst.setString(4, defeitocliTf.getText());
            pst.setString(5, servicocliTf.getText());
            pst.setString(6, tecnicocliTf.getText());
            pst.setString(7, valortotcliTf.getText().replace(",", "."));
            pst.setString(8, numOsTF.getText());

            if (this.validacampos(OsComboBox, idclientTf, tipo, equipamentocliTf, defeitocliTf, servicocliTf, tecnicocliTf, valortotcliTf)) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {
                int sqlBase = pst.executeUpdate();

                if(sqlBase > 0) {
                    JOptionPane.showMessageDialog(null, "OS Alterada com sucesso");
                    limpar();
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
     private void remover() {
        int recusar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover essa OS?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (recusar==JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os=?";
        
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, numOsTF.getText());

                int sqlBased = pst.executeUpdate();

                if (sqlBased > 0 ) {
                    JOptionPane.showMessageDialog(null, "OS Removida com sucesso");
                    dataOsTF.setText(null);
                    numOsTF.setText(null);
                    limpar();
                } else {
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
}
