/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.monitoringcontrol.Bean;

/**
 *
 * @author noslineda
 */
import com.jcraft.jsch.*;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.nio.charset.StandardCharsets;

public class SSHLogReader {

    /**
     * Conecta via SSH e lê o conteúdo de um arquivo de log.
     */
    public String lerLogRemoto(String usuario, String senha, String host, String diretorioArquivo) {
        StringBuilder conteudoLog = new StringBuilder();

        JSch jsch = new JSch();
        Session session = null;
        ChannelExec channel = null;
        
        try {
            // 1. Configura a sessão
            session = jsch.getSession(usuario, host, 22);
            session.setPassword(senha);

            // Evita a verificação rigorosa de host key (útil para testes)
            session.setConfig("StrictHostKeyChecking", "no");
            
            System.out.println("Conectando a " + host + "...");
            session.connect();

            // 2. Abre o canal para executar o comando 'cat' no arquivo
            String comando = "cat " + diretorioArquivo;
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(comando);

            // 3. Obtém o fluxo de entrada para ler o log
            InputStream in = channel.getInputStream();
            channel.connect();

            // 4. Lê e imprime o conteúdo
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String linha;
            System.out.println("--- Início do Log ---");
            try {
                // ... conexão ...
                while ((linha = reader.readLine()) != null) {
                    conteudoLog.append(linha).append("\n");
                }
            } catch (Exception e) {
                return "Erro: " + e.getMessage();
            }
    

            System.out.println("--- Fim do Log ---");

        } catch (Exception e) {
            System.err.println("Erro ao acessar o log: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. Fecha as conexões
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
            System.out.println("Conexão encerrada.");
        }
        return  conteudoLog.toString();
    }

    public String lerUltimasLinhas(String usuario, String senha, String ip, String caminhoArquivo) {
        StringBuilder conteudo = new StringBuilder();
        Session session = null;
        ChannelExec channel = null;

        try {
            JSch jsch = new JSch();
            // Criar a sessão
            session = jsch.getSession(usuario, ip, 22);
            session.setPassword(senha);

            // Configuração para não pedir confirmação de chave (fingerprint)
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // Abrir o canal de execução
            channel = (ChannelExec) session.openChannel("exec");

            // O comando que será executado no Linux
            String comando = "tail -n 20000 " + caminhoArquivo;
            channel.setCommand(comando);

            // Ler a resposta do comando com BufferedReader
            InputStream in = channel.getInputStream();
            channel.connect();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    conteudo.append(line).append("\n");
                }
            }

             // Esperar o canal fechar
            while (!channel.isClosed()) {
                Thread.sleep(100);
            }

        } catch (Exception e) {
            return "Erro ao ler log: " + e.getMessage();
        } finally {
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }

        return conteudo.toString();
    }

}
