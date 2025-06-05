package br.com.coletron.dao;

import br.com.coletron.model.Descarte;
import br.com.coletron.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp; // Para converter LocalDateTime para SQL Timestamp

public class DescarteDAO {

    /**
     * Salva um novo registro de descarte no banco de dados.
     * @param descarte O objeto Descarte a ser salvo.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public void salvarDescarte(Descarte descarte) throws SQLException {
        // O id_descarte é AUTO_INCREMENT.
        // data_hora tem DEFAULT CURRENT_TIMESTAMP, mas vamos enviar explicitamente.
        String sql = "INSERT INTO Descarte (id_usuario, id_residuo, data_hora) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, descarte.getId_usuario());
            pstmt.setInt(2, descarte.getId_residuo());
            pstmt.setTimestamp(3, Timestamp.valueOf(descarte.getData_hora()));

            pstmt.executeUpdate();
            System.out.println("Descarte registrado com sucesso no banco de dados!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar descarte no banco de dados: " + e.getMessage());
            throw e;
        }
    }
}