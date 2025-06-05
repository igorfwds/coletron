package br.com.coletron.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Defina os parâmetros de conexão com o seu banco de dados MySQL
    // A URL aponta para o banco 'ColetronDB' que você criou
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ColetronDB";
    private static final String DATABASE_USER = "root"; // O usuário que você configurou no Docker e usou no DBeaver
    private static final String DATABASE_PASSWORD = "root"; // A senha que você configurou

    // Método estático para obter uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // O driver JDBC do MySQL (Connector/J) geralmente se registra automaticamente
            // desde a versão JDBC 4.0 se estiver no classpath.
            // Class.forName("com.mysql.cj.jdbc.Driver"); // Esta linha não é estritamente necessária com drivers modernos.

            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            if (connection != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
            // Re-lança a exceção para que a classe que chamou saiba do erro
            throw e;
        }
        return connection;
    }

    public static void main(String[] args) {
        // Um pequeno teste para verificar a conexão
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Teste de conexão bem-sucedido. A conexão será fechada automaticamente (try-with-resources).");
            } else {
                System.out.println("Falha no teste de conexão.");
            }
        } catch (SQLException e) {
            System.err.println("Falha no teste de conexão: " + e.getMessage());
        }
    }
}