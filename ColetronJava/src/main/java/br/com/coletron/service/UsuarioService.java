package br.com.coletron.service;

import br.com.coletron.model.Usuario;
import br.com.coletron.model.Residuo;
import br.com.coletron.dao.UsuarioDAO;

// Importar SQLException, pois os métodos do DAO agora podem lançar essa exceção
import java.sql.SQLException;
// O import java.io.IOException pode não ser mais necessário aqui,
// a menos que outras partes do serviço realizem operações de I/O de arquivo.
// Se não for usado, pode ser removido.

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Cadastra um novo usuário.
     *
     * @param nome O nome do usuário.
     * @param cpf O CPF do usuário.
     * @param email O email do usuário.
     * @param senha A senha do usuário.
     * @throws SQLException Se ocorrer um erro durante o acesso ao banco de dados.
     */
    public void cadastrarUsuario(String nome, String cpf, String email, String senha) throws SQLException {
        // A lógica de criação do objeto Usuario permanece a mesma.
        // A pontuação inicial será tratada pelo DAO ou pelo default do banco de dados.
        Usuario usuario = new Usuario(nome, cpf, email, senha, 0);
        // A chamada ao DAO agora pode lançar SQLException.
        usuarioDAO.salvarUsuario(usuario);
    }

    /**
     * Realiza o login de um usuário buscando-o pelo CPF.
     *
     * @param cpf O CPF do usuário.
     * @return O objeto Usuario se o login for bem-sucedido (usuário encontrado), caso contrário null.
     * @throws SQLException Se ocorrer um erro durante o acesso ao banco de dados.
     */
    public Usuario loginUsuario(String cpf) throws SQLException {
        // A chamada ao DAO agora pode lançar SQLException.
        return usuarioDAO.buscarUsuarioPorCpf(cpf);
    }

    /**
     * Adiciona pontos a um usuário com base no resíduo descartado.
     *
     * @param usuario O usuário que receberá os pontos.
     * @param residuo O resíduo descartado que define a quantidade de pontos.
     * @throws SQLException Se ocorrer um erro durante a atualização dos dados do usuário no banco.
     */
    public void adicionarPontos(Usuario usuario, Residuo residuo) throws SQLException {
        // A lógica de adicionar pontos ao objeto Usuario em memória permanece a mesma.
        usuario.adicionarPontos(residuo.getPontos());
        // A chamada ao DAO para persistir a atualização agora pode lançar SQLException.
        usuarioDAO.atualizarUsuario(usuario);
    }
}