package br.com.coletron.service;

import br.com.coletron.dao.DescarteDAO;
import br.com.coletron.model.Descarte;
import br.com.coletron.model.Residuo;
import br.com.coletron.model.Usuario;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class DescarteService {
    private DescarteDAO descarteDAO = new DescarteDAO();

    /**
     * Registra um novo descarte.
     * @param usuario O usuário que realizou o descarte (pode ser null se não logado, mas o DAO não salvará).
     * @param residuo O resíduo que foi descartado (deve ter ID).
     * @throws SQLException Se ocorrer um erro de banco de dados.
     */
    public void registrarDescarte(Usuario usuario, Residuo residuo) throws SQLException {
        if (usuario == null || residuo == null) {
            // Não registra o descarte se não houver usuário ou resíduo válido.
            // Isso cobre o caso de "Somente Descartar" se não quisermos logar o descarte.
            System.out.println("Descarte não registrado: usuário não logado ou resíduo inválido.");
            return;
        }

        if (usuario.getId_usuario() == 0 || residuo.getId_residuo() == 0) {
            // Garante que temos IDs válidos (id 0 pode significar não persistido)
            // Embora o auto_increment comece em 1.
            System.err.println("Tentativa de registrar descarte com ID de usuário ou resíduo inválido.");
            // Poderia lançar uma exceção aqui também.
            return;
        }

        Descarte novoDescarte = new Descarte();
        novoDescarte.setId_usuario(usuario.getId_usuario());
        novoDescarte.setId_residuo(residuo.getId_residuo());
        novoDescarte.setData_hora(LocalDateTime.now()); // Define a data/hora atual

        descarteDAO.salvarDescarte(novoDescarte);
    }
}