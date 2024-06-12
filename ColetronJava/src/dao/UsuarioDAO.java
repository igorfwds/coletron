package dao;

import model.Usuario;
import java.io.*;
import java.util.*;

public class UsuarioDAO {
    private static final String FILE_NAME = "usuarios.txt";

    public void salvarUsuario(Usuario usuario) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        writer.write(usuario.getNome() + "," + usuario.getCpf() + "," + usuario.getEmail() + "," + usuario.getSenha() + "," + usuario.getPontos());
        writer.newLine();
        writer.close();
    }

    public Usuario buscarUsuarioPorCpf(String cpf) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(",");
            if (partes[1].equals(cpf)) {
                reader.close();
                Usuario usuario = new Usuario(partes[0], partes[1], partes[2], partes[3]);
                usuario.setPontos(Integer.parseInt(partes[4]));
                return usuario;
            }
        }
        reader.close();
        return null;
    }

    public void atualizarUsuario(Usuario usuario) throws IOException {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("usuarios_temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(",");
            if (partes[1].equals(usuario.getCpf())) {
                writer.write(usuario.getNome() + "," + usuario.getCpf() + "," + usuario.getEmail() + "," + usuario.getSenha() + "," + usuario.getPontos());
            } else {
                writer.write(linha);
            }
            writer.newLine();
        }
        writer.close();
        reader.close();

        if (!inputFile.delete()) {
            System.out.println("Não foi possível deletar o arquivo original.");
            return;
        }

        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Não foi possível renomear o arquivo temporário.");
        }
    }
}