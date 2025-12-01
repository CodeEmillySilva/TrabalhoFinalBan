package persistenciaBD;

import java.sql.*;
import java.time.*;
import java.util.*;

import Dados.Conta;
import Dados.TipoTransacao;
import Dados.Transacao;

import java.sql.Date;

@SuppressWarnings("unused")
public class TransacaoDAO {

    public boolean inserir(Transacao t) {
        String sql = "INSERT INTO transacao(codconta, idtransacao, dttransacao, hora, valor, idtipotransacao, tarifa, descricao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)       ";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, t.getCodConta().getCodConta());
            ps.setInt(2, t.getIdTransacao());
            ps.setDate(3, Date.valueOf(t.getDataTransacao()));
            ps.setTime(4, Time.valueOf(t.getHora()));
            ps.setDouble(5, t.getValor());
            ps.setInt(6, t.getTipoTransacao().getIdTipoTransacao());
            ps.setDouble(7, t.getTarifa());
            ps.setString(8, t.getDescricao());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transacao> extrato(int codConta) {
        List<Transacao> lista = new ArrayList<>();

        String sql = """
            SELECT t.*, tp.tipotransacao
            FROM transacao t
            JOIN tipotransacao tp ON t.idtipotransacao = tp.idtipotransacao
            WHERE t.codconta = ?
            ORDER BY t.dttransacao, t.hora
        """;

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codConta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TipoTransacao tipo = new TipoTransacao(
                        rs.getInt("idtipotransacao"),
                        rs.getString("tipotransacao")
                );

                Transacao t = new Transacao(
                        rs.getInt("idtransacao"),
                        rs.getDate("dttransacao").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        rs.getDouble("valor"),
                        rs.getDouble("tarifa"),
                        tipo,
                        null,
                        rs.getString("descricao")
                );

                lista.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public double calcularSaldo(int codConta) {
        String sql = """
            SELECT 
                SUM(CASE WHEN idtipotransacao = 1 THEN valor ELSE -valor END) AS saldo
            FROM transacao
            WHERE codconta = ?
        """;

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codConta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getDouble("saldo");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    List<Transacao> listar(int codConta) {
    	throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public int obterProximoIdTransacao() {
        String sql = "SELECT COALESCE(MAX(idtransacao), 0) + 1 FROM transacao";

        try (Connection c = ConexaoBD.conectar();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    public List<Transacao> extratoPorConta(int codConta) {
        List<Transacao> transacoes = new ArrayList<>();

        String sql = """
            SELECT t.*, tt.tipotransacao 
            FROM transacao t
            JOIN tipotransacao tt ON t.idtipotransacao = tt.idtipotransacao
            WHERE codconta = ?
            ORDER BY dttransacao, hora
        """;

        try (Connection c = ConexaoBD.conectar();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, codConta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TipoTransacao tipo = new TipoTransacao(
                rs.getInt("idtipotransacao"),
                rs.getString("tipotransacao")
                );

                Transacao t = new Transacao(
                rs.getInt("idtransacao"),
                rs.getDate("dttransacao").toLocalDate(),
                rs.getTime("hora").toLocalTime(),
                rs.getDouble("valor"),
                rs.getDouble("tarifa"),
                tipo,
                null,
                rs.getString("descricao")
                );

                transacoes.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transacoes;
    }


}
