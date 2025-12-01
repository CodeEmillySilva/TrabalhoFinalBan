package persistenciaBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dados.Agencia;

public class AgenciaDAO {
    
	public boolean inserir(Agencia agencia) throws SQLException {
		
	    String sql = "INSERT INTO agencia (codagencia, endereco, telefone) VALUES (?, ?, ?)";

	    try (Connection c = ConexaoBD.conectar();
	         PreparedStatement ps = c.prepareStatement(sql)) {

	       ps.setInt(1, agencia.getCodAgencia());
	       ps.setString(2, agencia.getEndereco());
	       ps.setInt(3, Integer.parseInt(agencia.getTelefone()));
	
	       return ps.executeUpdate() == 1;

       } catch (SQLException e) {
    	   System.out.println("Problema de conexao com o banco");
           e.printStackTrace();
           return false;
       }
	
	}

	public boolean excluir(int codAgencia) throws SQLException {
		
		String sql = "DELETE FROM agencia WHERE codagencia = ?";
		
		try (Connection c = ConexaoBD.conectar();
			 PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setInt(1, codAgencia);
			
			return ps.executeUpdate() == 1;
			
		} catch (SQLException e) {
	                 System.out.println("Erro ao excluir agência");
	                 e.printStackTrace();
	                 return false;
	    }
		
    }

    public boolean excluirSemConta(int cod) throws SQLException {
    	
    	 String sqlCheck = "SELECT 1 FROM conta WHERE codagencia = ?";
         String sqlDelete = "DELETE FROM agencia WHERE codagencia = ?";
         
         try (Connection c = ConexaoBD.conectar();
              PreparedStatement psCheck = c.prepareStatement(sqlCheck);
        	  PreparedStatement psDelete = c.prepareStatement(sqlDelete)) {
        	 
        	 psCheck.setInt(1, cod);
        	 
        	 try (ResultSet rs = psCheck.executeQuery()) {
        		 
        		 if(!rs.next()) {
        			 psDelete.setInt(1, cod);
        			 int verificacao = psDelete.executeUpdate();
        			 return verificacao==1;
        		 } else {
        			 System.out.println("A agência possui contas vinculadas e não pode ser excluída.");
                     return false;
        		 }
        	 }
        	 
         } catch (SQLException e) {
             System.out.println("Erro ao excluir agência (verificação de contas)");
             e.printStackTrace();
             return false;
         }
         
    }

    public Agencia buscarPorCodigo(int codAgencia) throws SQLException {
    	
        String sql = "SELECT codagencia, endereco, telefone FROM agencia WHERE codagencia = ?";
        
        try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
        	
        	ps.setInt(1, codAgencia);
        	
        	try(ResultSet rs = ps.executeQuery();) {
        		
        		if (rs.next()) {
                    int cod = rs.getInt("codagencia");
                    String end = rs.getString("endereco");
                    String tel = rs.getBigDecimal("telefone").toPlainString();
                    return new Agencia(cod, end, tel);
                }
        		
        	}
        	
        	return null;
        	
	    } catch (SQLException e) {
	        System.out.println("Erro ao buscar agência");
	        e.printStackTrace();
	        return null;
	    }
        
    }

    public List<Agencia> listar() throws SQLException {
    	
    	List<Agencia> agencias = new ArrayList<>();
    	
    	String sql = "SELECT codagencia, endereco, telefone FROM agencia";

        try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        	
        	while (rs.next()) {
                int cod = rs.getInt("codagencia");
                String end = rs.getString("endereco");
                String telefone = rs.getBigDecimal("telefone").toPlainString();

                Agencia a = new Agencia(cod, end, telefone);
                agencias.add(a);
                
            }
        	
        } catch (SQLException e) {
        	
            System.out.println("Erro ao listar agências");
            e.printStackTrace();
            
        }

        return agencias;
    
    }
    
}
