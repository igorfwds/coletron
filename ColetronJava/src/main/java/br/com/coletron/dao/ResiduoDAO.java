package br.com.coletron.dao;

import br.com.coletron.model.Residuo;
import br.com.coletron.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResiduoDAO {

    /**
     * Busca um tipo de resíduo pelo seu nome (tipo).
     * @param tipo O nome do tipo de resíduo (ex: "Pequeno", "Médio")
     * @return O objeto Residuo se encontrado, caso contrário null.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public Residuo buscarResiduoPorTipo(String tipo) throws SQLException {
        String sql = "SELECT id_residuo, tipo, pontos_residuo FROM Residuo WHERE tipo = ?";
        Residuo residuo = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    residuo = new Residuo(
                            rs.getInt("id_residuo"),
                            rs.getString("tipo"),
                            rs.getInt("pontos_residuo")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar resíduo por tipo no banco de dados: " + e.getMessage());
            throw e;
        }
        return residuo;
    }
}