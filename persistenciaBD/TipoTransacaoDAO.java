package persistenciaBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dados.TipoTransacao;

public class TipoTransacaoDAO {
    
	public boolean inserir(TipoTransacao tt) throws SQLException {
		
        String sql = "INSERT INTO tipotransacao (idtipotransacao, tipotransacao) VALUES (?, ?)";
        
	    try (Connection c = ConexaoBD.conectar();
	         PreparedStatement ps = c.prepareStatement(sql)) {

	       ps.setInt(1, tt.getIdTipoTransacao());
	       ps.setString(2, tt.getTipoTransacao());
	
	       return ps.executeUpdate() == 1;

       } catch (SQLException e) {
    	   System.out.println("Problema de conexao com o banco");
           e.printStackTrace();
           return false;
       }
	
	}

	public boolean excluir(int idTtransacao) throws SQLException {
		
		String sql = "DELETE FROM tipotransacao WHERE idtipotransacao = ?";
		
		try (Connection c = ConexaoBD.conectar();
			 PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setInt(1, idTtransacao);
			
			return ps.executeUpdate() == 1;
			
		} catch (SQLException e) {
	                 System.out.println("Erro ao excluir tipo de transação");
	                 e.printStackTrace();
	                 return false;
	    }
		
    }

    public TipoTransacao buscarPorCodigo(int idTipo) throws SQLException {
    	
        String sql = "SELECT idtipotransacao, tipotransacao FROM tipotransacao WHERE idtipotransacao = ?";
        
        try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
        	
        	ps.setInt(1, idTipo);
        	
        	try(ResultSet rs = ps.executeQuery();) {
        		
        		if (rs.next()) {
                    int cod = rs.getInt("idtipotransacao");
                    String tipo = rs.getString("tipotransacao");

                    return new TipoTransacao(cod, tipo);
                }
        		
        	}
        	
        	return null;
        	
	    } catch (SQLException e) {
	        System.out.println("Erro ao buscar tipo de transação");
	        e.printStackTrace();
	        return null;
	    }
        
    }

    public List<TipoTransacao> listar() throws SQLException {
    	
    	List<TipoTransacao> tipoTransacoes = new ArrayList<>();
    	
    	String sql = "SELECT idtipotransacao, tipotransacao FROM tipotransacao";
 
        try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        	
        	while (rs.next()) {
                int cod = rs.getInt("idtipotransacao");
                String tipo = rs.getString("tipotransacao");
                TipoTransacao t = new TipoTransacao(cod, tipo);
                
                tipoTransacoes.add(t);
                
            }
        	
        } catch (SQLException e) {
        	
            System.out.println("Erro ao listar agências");
            e.printStackTrace();
            
        }

        return tipoTransacoes;
    
    }
    
}
