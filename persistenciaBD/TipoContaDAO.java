package persistenciaBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dados.TipoConta;

public class TipoContaDAO {
    
	public boolean inserir(TipoConta tc) throws SQLException {
		
        String sql = "INSERT INTO tipoconta (idtipoconta, tipoconta) VALUES (?, ?)";
        
	    try (Connection c = ConexaoBD.conectar();
	         PreparedStatement ps = c.prepareStatement(sql)) {

	       ps.setInt(1, tc.getId());
	       ps.setString(2, tc.getDescricaoConta());
	
	       return ps.executeUpdate() == 1;

       } catch (SQLException e) {
    	   System.out.println("Problema de conexao com o banco");
           e.printStackTrace();
           return false;
       }
	
	}

	public boolean excluir(int idtipo) throws SQLException {
		
		String sql = "DELETE FROM tipoconta WHERE idtipoconta = ?";
		
		try (Connection c = ConexaoBD.conectar();
			 PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setInt(1, idtipo);
			
			return ps.executeUpdate() == 1;
			
		} catch (SQLException e) {
	                 System.out.println("Erro ao excluir tipo de conta");
	                 e.printStackTrace();
	                 return false;
	    }
		
    }

    public TipoConta buscarPorCodigo(int idTipo) throws SQLException {
    	
        String sql = "SELECT idtipoconta, tipoconta FROM tipoconta WHERE idtipoconta = ?";
        
        try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
        	
        	ps.setInt(1, idTipo);
        	
        	try(ResultSet rs = ps.executeQuery();) {
        		
        		if (rs.next()) {
                    int cod = rs.getInt("idtipoconta");
                    String tipo = rs.getString("tipoconta");

                    return new TipoConta(cod, tipo);
                }
        		
        	}
        	
        	return null;
        	
	    } catch (SQLException e) {
	        System.out.println("Erro ao buscar tipo conta");
	        e.printStackTrace();
	        return null;
	    }
        
    }

    public List<TipoConta> listar() throws SQLException {
    	
    	List<TipoConta> tipoContas = new ArrayList<>();
    	
    	String sql = "SELECT idtipoconta, tipoconta FROM tipoconta";
 
        try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        	
        	while (rs.next()) {
                int cod = rs.getInt("idtipoconta");
                String tipo = rs.getString("tipoconta");
                TipoConta t = new TipoConta(cod, tipo);
                
                tipoContas.add(t);
                
            }
        	
        } catch (SQLException e) {
        	
            System.out.println("Erro ao listar tipos de contas");
            e.printStackTrace();
            
        }

        return tipoContas;
    
    }
    
}
