package persistenciaBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Dados.Clientes;
import Dados.PFisica;
import Dados.PJuridica;

import java.sql.Date;

public class ClienteDAO {

    public boolean inserirCliente(Clientes cliente) throws SQLException {
    	
    	String sql = "INSERT INTO cliente " +
                "(codcliente, tipocliente, nome, data_nascimento, "
                + "email, cpf, cnpj, razaosocial, endereco, "
                + "telefone) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	
    	try (Connection c = ConexaoBD.conectar();
    		PreparedStatement ps = c.prepareStatement(sql)) {
    		
    		ps.setInt(1,cliente.getCodCliente());
    		ps.setInt(2, cliente.getTipoCliente());
    		ps.setString(5, cliente.getEmail());
            ps.setString(9, cliente.getEndereco());
            ps.setLong(10, Long.parseLong(cliente.getTelefone()));
            
            if(cliente instanceof PFisica) {
            	
            	PFisica fisica = (PFisica) cliente;
            	
            	ps.setString(3,  fisica.getNome());
            	ps.setDate(4, Date.valueOf(fisica.getDataNascimento()));
            	ps.setLong(6,Long.parseLong(fisica.getCPF()));
            	
            	ps.setNull(7, Types.BIGINT); 
                ps.setNull(8, Types.VARCHAR);
                
            } else {
            	
            	PJuridica juridica = (PJuridica) cliente;
            	
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(4, Types.DATE);
                ps.setNull(6, Types.INTEGER);
                
                ps.setLong(7, Long.parseLong(juridica.getCNPJ()));
                ps.setString(8, juridica.getRazaoSocial());
                
            }
            
            return ps.executeUpdate()==1;
            
    	} catch (SQLException e) {
    	   System.out.println("Problema de conexao com o banco");
           e.printStackTrace();
           return false;
       }
    	
    }

    public Clientes buscarPorCodigo(int codCliente) throws SQLException {
    	
    	String sql = "SELECT * FROM cliente WHERE codcliente = ?";
    	
    	try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
    		
    		ps.setInt(1, codCliente);
    		
    		try (ResultSet rs = ps.executeQuery()) {
    			
    			if(!rs.next()) {
    				return null;
    			}
    			
    			int tipo = rs.getInt("tipocliente");
    			
    			if(tipo == 1) {
    				
    				return new PFisica(
                            rs.getInt("codcliente"),
                            rs.getString("email"),
                            String.valueOf(rs.getLong("telefone")),
                            rs.getString("endereco"),
                            String.valueOf(rs.getLong("cpf")),
                            rs.getString("nome"),
                            rs.getDate("data_nascimento").toLocalDate()
                    );
    			
    			} else {
    				
    				return new PJuridica(
                            rs.getInt("codcliente"),
                            rs.getString("email"),
                            String.valueOf(rs.getLong("telefone")),
                            rs.getString("endereco"),
                            String.valueOf(rs.getLong("cnpj")),
                            rs.getString("razaosocial")
                    );
    				
    			}
    		}
    		
    	} catch (SQLException e) {
            System.out.println("Erro ao buscar cliente.");
            e.printStackTrace();
            return null;
        }
    	
    }

    public boolean excluirSemConta(int cod) throws SQLException {
    	
    	String sqlCheck = "SELECT 1 FROM conta WHERE codcliente = ?";
    	String sqlDelete = "DELETE FROM cliente WHERE codcliente = ?";
	    
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
	   			 System.out.println("O cliente possui contas vinculadas e não pode ser excluída.");
	                return false;
	   		 }
	   	 }
	   	 
	    } catch (SQLException e) {
	        System.out.println("Erro ao excluir clientes (verificação de contas)");
	        e.printStackTrace();
	        return false;
	    }
	    
    }

    public boolean excluir(int codCliente) throws SQLException {
    	
    	String sql = "DELETE FROM cliente WHERE codcliente=?";
    	
    	try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
    		
    		ps.setInt(1, codCliente);
    		
    		return ps.executeUpdate()==1;
    		
    	} catch (SQLException e) {
            System.out.println("Erro ao deletar cliente.");
            e.printStackTrace();
            return false;
        }
    	
    }
    
    public List<Clientes> listarTodos() throws SQLException {
    	
        List<Clientes> clientes = new ArrayList<>();
        
        String sql = "SELECT * FROM cliente ORDER BY codcliente";

        
        try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        	
        	while(rs.next()) {
        		
        		int tipo = rs.getInt("tipocliente");
        		
        		if (tipo==1) {
	        		PFisica fisica = new PFisica(
	                        rs.getInt("codcliente"),
	                        rs.getString("email"),
	                        String.valueOf(rs.getLong("telefone")),
	                        rs.getString("endereco"),
	                        String.valueOf(rs.getLong("cpf")),
	                        rs.getString("nome"),
	                        rs.getDate("data_nascimento").toLocalDate()
	                );
	        		
	                clientes.add(fisica);
        		} else {
        			PJuridica juridica = new PJuridica (
        					rs.getInt("codcliente"),
                            rs.getString("email"),
                            String.valueOf(rs.getLong("telefone")),
                            rs.getString("endereco"),
                            String.valueOf(rs.getLong("cnpj")),
                            rs.getString("razaosocial"));
        			
        			clientes.add(juridica);
        		}
        	}
        	
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes.");
            e.printStackTrace();
        }
        
        return clientes;
    }
    
}
