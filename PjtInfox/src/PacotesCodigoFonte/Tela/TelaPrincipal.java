package PacotesCodigoFonte.Tela;

import PacotesCodigoFonte.Dal.ModuloConexao;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;

public class TelaPrincipal extends JFrame{
    public static JMenuItem usuario;
    public static JMenu relatorios;
    public static JLabel mUsuario;
    private JDesktopPane painel;
    Connection conexao = null;
    JMenuItem clientes;

    public TelaPrincipal() {
        conexao = ModuloConexao.conector();
        
        setTitle("X - Sistema de Controle Para Ordem de Serviço");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JMenuBar barramenu = new JMenuBar();

        JMenu cadastro = new JMenu("Cadastro");
        barramenu.add(cadastro);

        relatorios = new JMenu("Relatorio");
        relatorios.setEnabled(false);
        barramenu.add(relatorios);

        JMenu ajuda = new JMenu("Ajuda");
        barramenu.add(ajuda);

        JMenu opcao = new JMenu("Opções");
        barramenu.add(opcao);

        JMenuItem cliente = new JMenuItem("Cliente");
        cliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
        cliente.addActionListener(e -> {
            TelaCliente telacli = new TelaCliente();
            painel.add(telacli);
        });
        cadastro.add(cliente);

        JMenuItem os = new JMenuItem("OS");
        os.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
        os.addActionListener(e -> {
            TelaOs telaOs = new TelaOs();
            painel.add(telaOs);
        });
        cadastro.add(os);
        
        usuario = new JMenuItem("Usuarios");
        usuario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
        usuario.setEnabled(false);
        cadastro.add(usuario);
        usuario.addActionListener(e ->{
            TelaUsuario telaUsu = new TelaUsuario();
            painel.add(telaUsu);
        });

        clientes = new JMenuItem("Clientes");
        clientes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK));
        clientes.addActionListener(e -> {
            int confimarcli = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatorio? ", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confimarcli == JOptionPane.YES_OPTION) {
                // Imprimindo o relatorio com o framework JarperReports

                try {
                    // Usando a clase JasperPrint

                    JasperPrint print = JasperFillManager.fillReport("D:/Report/clientes.jasper", null, conexao);

                    // A linha abaixo exibe o relatorio atraves da classe

                    JasperViewer.viewReport(print, false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        relatorios.add(clientes);

        JMenuItem servicos = new JMenuItem("Serviços");
        servicos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
        relatorios.add(servicos);

        JMenuItem sobre = new JMenuItem("Sobre");
        sobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.ALT_DOWN_MASK));
        ajuda.add(sobre);
        sobre.addActionListener(e -> {
            TelaSobre tela = new TelaSobre();
        });

        JMenuItem sair = new JMenuItem("Sair");
        sair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        opcao.add(sair);
        sair.addActionListener(e -> {
            int sairP = JOptionPane.showConfirmDialog(null, "Tem Certeza Que Deseja Sair?", "Ajuda", JOptionPane.YES_NO_OPTION);
            if (sairP == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setJMenuBar(barramenu);

        painel = new JDesktopPane();
        painel.setVisible(true);
        painel.setBackground(Color.LIGHT_GRAY);
        painel.setBounds(0, 0, 780, 620);
        painel.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.WHITE));
        add(painel);



        mUsuario = new JLabel("Usuario");
        mUsuario.setBounds(850, 20, 200, 40);
        mUsuario.setFont(new Font("Arial", Font.BOLD, 23));
        add(mUsuario);

        JLabel mData = new JLabel("Data");
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        mData.setText(formatador.format(data));
        mData.setBounds(850, 80, 140, 40);
        mData.setFont(new Font("Arial", Font.BOLD, 23));
        add(mData);



          
    }   
}
