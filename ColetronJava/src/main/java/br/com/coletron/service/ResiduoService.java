package br.com.coletron.service;

import br.com.coletron.dao.ResiduoDAO;
import br.com.coletron.model.Residuo;

import java.sql.SQLException;

public class ResiduoService {
    private ResiduoDAO residuoDAO = new ResiduoDAO();

    /**
     * Obtém um tipo de resíduo pelo seu nome.
     * @param tipo O nome do tipo de resíduo.
     * @return O objeto Residuo.
     * @throws SQLException Se ocorrer um erro no banco ou se o resíduo não for encontrado.
     */
    public Residuo obterResiduoPorTipo(String tipo) throws SQLException {
        Residuo residuo = residuoDAO.buscarResiduoPorTipo(tipo);
        if (residuo == null) {
            // Decide o que fazer se o tipo de resíduo não estiver no banco.
            // Por agora, vamos lançar uma exceção para indicar que é uma configuração faltando.
            throw new SQLException("Tipo de resíduo '" + tipo + "' não encontrado no banco de dados. Verifique a configuração.");
        }
        return residuo;
    }
}