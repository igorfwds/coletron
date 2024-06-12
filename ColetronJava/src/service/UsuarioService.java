package service;

import model.Usuario;
import model.Residuo;
import dao.UsuarioDAO;
import java.io.IOException;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrarUsuario(String nome, String cpf, String email, String senha) throws IOException {
        Usuario usuario = new Usuario(nome, cpf, email, senha);
        usuarioDAO.salvarUsuario(usuario);
    }

    public Usuario loginUsuario(String cpf) throws IOException {
        return usuarioDAO.buscarUsuarioPorCpf(cpf);
    }

    public void adicionarPontos(Usuario usuario, Residuo residuo) throws IOException {
        usuario.adicionarPontos(residuo.getPontos());
        usuarioDAO.atualizarUsuario(usuario);
    }
}