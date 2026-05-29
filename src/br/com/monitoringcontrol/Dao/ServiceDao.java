/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.monitoringcontrol.Dao;

import br.com.monitoringcontrol.Bean.Clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.monitoringcontrol.Bean.Coordenador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author noslineda
 */
public class ServiceDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    /**
     * Construtor
     * @throws Exception .
     */
    public ServiceDao() throws Exception {
        
        try {
            this.conn = MySqlConfig.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro: " + e.getMessage());
        }
    }

    public Coordenador buscaCoordenadorPorPorta() throws Exception {
     
        String SQL = "SELECT * FROM fairtek.tb_cordenador";
        Coordenador c = new Coordenador();
        try {
            this.ps = conn.prepareStatement(SQL);
            this.rs = this.ps.executeQuery();
            while (rs.next()) {
                c.setId(this.rs.getInt("id"));
                c.setNome(this.rs.getString("nome"));
                c.setExcluido(this.rs.getInt("excluido"));
                c.setAtivo(this.rs.getInt("ativo"));
            }
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        } finally {
            MySqlConfig.closeConnection(this.conn, this.ps, this.rs);
        }
        return c;
    }

    public List<Coordenador> buscarTodosCoordenadores() throws Exception {
        List<Coordenador> lista = new ArrayList<>();
        String SQL = "SELECT * FROM fairtek.tb_cordenador where ativo = 1";
        try {
            this.ps = conn.prepareStatement(SQL);
            this.rs = this.ps.executeQuery();
            while (rs.next()) {
                Coordenador c = new Coordenador();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setExcluido(rs.getInt("excluido"));
                c.setAtivo(rs.getInt("ativo"));
                lista.add(c);
            }
            return lista;
        }catch(SQLException sqle){
            throw new Exception(sqle);
        }
    }
    public List<Clientes> buscarTodosServidores() throws Exception {
        List<Clientes> clientes = new ArrayList<>();
        String SQL = "SELECT * FROM  fairtek.tb_servidores";
        try {
            this.ps = conn.prepareStatement(SQL);
            this.rs = this.ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("nome"));
                Clientes c = new Clientes();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setIp(rs.getString("ip"));
                c.setAtivo(rs.getInt("ativo"));
                c.setPrincipal(rs.getInt("principal"));
                clientes.add(c);
            }
            //System.out.println("Lista de cliente: "+clientes);
            return clientes;
        }catch(SQLException sqle){
            throw new Exception(sqle);
        }
    }    
}
