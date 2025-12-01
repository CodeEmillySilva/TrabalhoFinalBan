package persistenciaBD;

import Dados.Agencia;
import Dados.Clientes;
import Dados.Conta;
import Dados.TipoConta;
//import Dados.Transacao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    public boolean inserir(Conta conta) {
    
    	String sql = "INSERT INTO conta (codconta,idtipoconta,"
    			+ "codcliente,nmconta,codagencia,dtabertura) "
    			+ "VALUES (?, ?, ?, ?, ?, ?);";

	    try (Connection c = ConexaoBD.conectar();
	         PreparedStatement ps = c.prepareStatement(sql)) {

	       ps.setInt(1, conta.getCodConta());
	       ps.setInt(2, conta.getTipoConta().getId());
	       ps.setInt(3, conta.getCliente().getCodCliente());
	       ps.setInt(4, conta.getNumConta());
	       ps.setInt(5, conta.getAgencia().getCodAgencia());
	       ps.setDate(6, Date.valueOf(conta.getDataAbertura()));
	
	       return ps.executeUpdate() == 1;

       } catch (SQLException e) {
    	   System.out.println("Problema de conexao com o banco");
           e.printStackTrace();
           return false;
       }
	    
    }
    
    public boolean atualizarSaldo(int numConta, double novoSaldo) {
        String sql = "UPDATE conta SET saldo = ? WHERE nmconta = ?";

        try (Connection c = ConexaoBD.conectar();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, novoSaldo);
            ps.setInt(2, numConta);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    
    public boolean existeContaParaCliente(int codCliente) {
    String sql = "SELECT 1 FROM conta WHERE codcliente = ?";

    try (Connection c = ConexaoBD.conectar();
         PreparedStatement ps = c.prepareStatement(sql)) {

        ps.setInt(1, codCliente);
        ResultSet rs = ps.executeQuery();
        return rs.next();

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    public List<Conta> listar() {
    	
    	 List<Conta> contas = new ArrayList<>();
         
         String sql = "SELECT * FROM conta";

         
         try (Connection c = ConexaoBD.conectar();
              PreparedStatement ps = c.prepareStatement(sql);
              ResultSet rs = ps.executeQuery()) {
        	 
             TipoContaDAO tipoDAO = new TipoContaDAO();
             AgenciaDAO agenciaDAO = new AgenciaDAO();
             ClienteDAO clienteDAO = new ClienteDAO();
             //TransacaoDAO transacaoDAO = new TransacaoDAO();
         	
         	while(rs.next()) {
         		
                int codConta = rs.getInt("codconta");
                int idTipoConta = rs.getInt("idtipoconta");
                int codCliente = rs.getInt("codcliente");
                int numeroConta = rs.getInt("nmconta");
                int codAgencia = rs.getInt("codagencia");
                LocalDate dataAbertura = rs.getDate("dtabertura").toLocalDate();
         		
                TipoConta tipoConta = tipoDAO.buscarPorCodigo(idTipoConta);
                Agencia agencia = (Agencia) agenciaDAO.buscarPorCodigo(codAgencia);
                Clientes cliente = clienteDAO.buscarPorCodigo(codCliente);
                
         	Conta conta = new Conta(
                codConta,
                numeroConta,
                dataAbertura,
                tipoConta,
                agencia);
         	conta.setCliente(cliente);
         	//List<Transacao> transacoes = transacaoDAO.listar(codConta);
            //conta.setTransacoes(transacoes);
         	contas.add(conta);
         		
         	}
         	
         } catch (SQLException e) {
             System.out.println("Erro ao listar clientes.");
             e.printStackTrace();
         }
         
         return contas;
         
    }

    public boolean existeContaParaAgencia(int codAgencia) {
    	
    	String sql = "SELECT 1 FROM conta WHERE codAgencia = ?";
    	
	   	 try (Connection c = ConexaoBD.conectar();
	   	      PreparedStatement ps = c.prepareStatement(sql)) {
	   		 
	   		 ps.setInt(1, codAgencia);
	   
	   		 try (ResultSet rs = ps.executeQuery()) {
	   			 return rs.next();
	   		 }
	   		 
		   	 } catch (SQLException e) {
		   	        e.printStackTrace();
		   	        return false;
		   	 }
   	 
    }

    public Conta buscarPorNumero(int numeroConta){
        String sql = """
            SELECT * FROM conta c
            JOIN tipoconta t ON c.idtipoconta = t.idtipoconta
            JOIN agencia a ON c.codagencia = a.codagencia
            WHERE c.nmconta = ?
        """;

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numeroConta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TipoConta tipo = new TipoConta(
                        rs.getInt("idtipoconta"),
                        rs.getString("tipoconta")
                );

                Agencia ag = new Agencia(
                        rs.getInt("codagencia"),
                        rs.getString("endereco"),
                        rs.getString("telefone")
                );

                return new Conta(
                        rs.getInt("codconta"),
                        rs.getInt("nmconta"),
                        rs.getDate("dtabertura").toLocalDate(),
                        tipo,
                        ag
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
    public boolean excluir(int codConta) {
        String sql = "DELETE FROM conta WHERE codconta = ?";

        try (Connection con = ConexaoBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codConta);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir conta: " + e.getMessage());
            return false;
        }
    }

    public int contarPorAgencia(int codAgencia) {
        String sql = "SELECT COUNT(*) FROM conta WHERE codagencia = ?";

        try (Connection con = ConexaoBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codAgencia);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            System.out.println("Erro ao contar a quant de contas por agencia: " + e.getMessage());
        }

        return 0;
    }

}
