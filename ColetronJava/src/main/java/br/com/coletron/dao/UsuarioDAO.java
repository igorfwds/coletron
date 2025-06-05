package br.com.coletron.dao; // Ajuste o pacote se necessário

import br.com.coletron.model.Usuario;
import br.com.coletron.util.DatabaseConnection; // Importe sua classe de conexão

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// Remova os imports de java.io.* pois não serão mais usados aqui para o arquivo txt

public class UsuarioDAO {

    // O FILE_NAME não é mais necessário
    // private static final String FILE_NAME = "usuarios.txt";

    /**
     * Salva um novo usuário no banco de dados.
     * O ID do usuário é gerado automaticamente pelo banco (AUTO_INCREMENT).
     * Os pontos são inicializados com o valor padrão do banco ou 0 se não especificado.
     *
     * @param usuario O objeto Usuario a ser salvo.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public void salvarUsuario(Usuario usuario) throws SQLException {
        // SQL para inserir um novo usuário.
        // A coluna id_usuario é AUTO_INCREMENT e não precisa ser inserida explicitamente.
        // A coluna pontos_acum tem um DEFAULT 0 no script SQL [cite: 2]
        String sql = "INSERT INTO Usuario (nome, cpf, email, senha, pontos_acum) VALUES (?, ?, ?, ?, ?)";

        // Usando try-with-resources para garantir que a conexão e o statement sejam fechados
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getCpf());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setInt(5, usuario.getPontos()); // Assume que o usuário novo já pode ter pontos iniciais ou 0

            pstmt.executeUpdate();
            System.out.println("Usuário salvo com sucesso no banco de dados!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar usuário no banco de dados: " + e.getMessage());
            throw e; // Re-lança a exceção para a camada de serviço tratar
        }
    }

    /**
     * Busca um usuário no banco de dados pelo CPF.
     *
     * @param cpf O CPF do usuário a ser buscado.
     * @return O objeto Usuario se encontrado, caso contrário null.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public Usuario buscarUsuarioPorCpf(String cpf) throws SQLException {
        // SQL para selecionar um usuário pelo CPF
        String sql = "SELECT id_usuario, nome, cpf, email, senha, pontos_acum FROM Usuario WHERE cpf = ?";
        Usuario usuario = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Use o construtor que aceita nome, cpf, email, senha e pontos_acum
                    usuario = new Usuario(
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getInt("pontos_acum") // Adicione pontos_acum aqui
                    );
                    // Defina o id_usuario separadamente
                    usuario.setId_usuario(rs.getInt("id_usuario"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por CPF no banco de dados: " + e.getMessage());
            throw e;
        }
        return usuario;
    }

    /**
     * Atualiza os dados de um usuário existente no banco de dados, identificado pelo CPF.
     *
     * @param usuario O objeto Usuario com os dados atualizados.
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados.
     */
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        // SQL para atualizar um usuário existente, identificado pelo CPF
        String sql = "UPDATE Usuario SET nome = ?, email = ?, senha = ?, pontos_acum = ? WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setInt(4, usuario.getPontos());
            pstmt.setString(5, usuario.getCpf()); // CPF é usado na cláusula WHERE

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Usuário atualizado com sucesso no banco de dados!");
            } else {
                System.out.println("Nenhum usuário encontrado com o CPF fornecido para atualização ou os dados já eram os mesmos.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário no banco de dados: " + e.getMessage());
            throw e;
        }
    }
}