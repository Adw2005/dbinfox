package PacotesCodigoFonte.Dal; // AJUSTE conforme sua pasta

import java.sql.Connection;
import java.sql.DriverManager;

public class ModuloConexao {

    public static Connection conector() {
        // modulo para conectar ao banco de dados
        Connection conexao = null;

        // chamar o driver
        String driver = "com.mysql.cj.jdbc.Driver";

        // Armazenar informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbinfox?characterEncoding=utf-8";
        String user = "dba";
        String password = "infox@12345";


        // estabelecer a conexao dos bancos

        try{
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado com sucesso");
            return conexao;
        } catch(Exception e) {
            // a linha abaixo serve para identificar o erro
            e.printStackTrace();
            return null;
        }
    }
}
