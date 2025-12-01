package Main;

import persistenciaBD.ClienteDAO;
import persistenciaBD.ContaDAO;
import persistenciaBD.TransacaoDAO;
import persistenciaBD.AgenciaDAO;
import persistenciaBD.TipoContaDAO;
import persistenciaBD.TipoTransacaoDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;

import Dados.Agencia;
import Dados.Clientes;
import Dados.Conta;
import Dados.TipoConta;
import Dados.TipoTransacao;
import Dados.Transacao;

public class SistemaBD {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private ContaDAO contaDAO = new ContaDAO();
    private AgenciaDAO agenciaDAO = new AgenciaDAO();
    private TransacaoDAO transacaoDAO = new TransacaoDAO();
    private TipoContaDAO tipoContaDAO = new TipoContaDAO();
    private TipoTransacaoDAO tipoTransacaoDAO = new TipoTransacaoDAO();


    // CLIENTE


    public boolean cadastrarCliente(Clientes cliente) throws SQLException {
        if (clienteDAO.buscarPorCodigo(cliente.getCodCliente()) != null) {
            System.out.println("Cliente já cadastrado!");
            return false;
        }
        return clienteDAO.inserirCliente(cliente);
    }

    public Clientes buscarClientePorCodigo(int codCliente) throws SQLException {
        return clienteDAO.buscarPorCodigo(codCliente);
    }

    public boolean excluirClienteSemConta(int codCliente) throws SQLException {
        if (contaDAO.existeContaParaCliente(codCliente)) {
            System.out.println("Cliente possui conta vinculada!");
            return false;
        }
        return clienteDAO.excluirSemConta(codCliente);
    }

    public List<Clientes> listarClientes() throws SQLException {
        return clienteDAO.listarTodos();
    }


    // CONTA
    
    public boolean cadastrarConta(
        int codConta,
        int numConta,
        int codCliente,
        int codAgencia,
        int idTipoConta) throws SQLException {

        //Soh cadastra se a conta do cliente ja foi adicionada
        if (contaDAO.buscarPorNumero(numConta) != null) {
            System.out.println("Conta já cadastrada!");
            return false;
        }

        //Buscar o cliente no banco de dados
        Clientes cliente = clienteDAO.buscarPorCodigo(codCliente);
        if (cliente == null) {
            System.out.println("Cliente não existe!");
            return false;
        }

        //Busca agência no banco
        Agencia agencia = (Agencia) agenciaDAO.buscarPorCodigo(codAgencia);
        if (agencia == null) {
            System.out.println("Agência não existe!");
            return false;
        }


        TipoConta tipoConta = tipoContaDAO.buscarPorCodigo(idTipoConta);
        if (tipoConta == null) {
            System.out.println("Tipo de conta inválido!");
            return false;
        }

        //Vincula os dados 
        Conta novaConta = new Conta(
            codConta,
            numConta,
            LocalDate.now(),
            tipoConta,
            agencia
        );

        novaConta.setCliente(cliente);

        return contaDAO.inserir(novaConta);
        
    }
    
    public boolean excluirConta(int codConta) {
        return contaDAO.excluir(codConta);
    }
    
    //Funcao de agregacao conta a quantide de contas estao vinculadas a cada agencia
    public int quantidadeContasPorAgencia(int codAgencia) {
        return contaDAO.contarPorAgencia(codAgencia);
    }

    
    // TIPO DE CONTA
    
    public boolean excluirTipoConta(int idTipo) throws SQLException {
        return tipoContaDAO.excluir(idTipo);
    }

    public TipoConta buscarTipoContaPorCodigo(int idTipo) throws SQLException {
        return tipoContaDAO.buscarPorCodigo(idTipo);
    }
    
    public boolean cadastrarTipoConta(TipoConta tipoConta) throws SQLException {

        if (tipoContaDAO.buscarPorCodigo(tipoConta.getId()) != null) {
            System.out.println("Tipo de conta já cadastrado!");
            return false;
        }

        return tipoContaDAO.inserir(tipoConta);
    }


    public boolean cadastrarConta(Conta conta, Clientes cliente) throws SQLException {

        if (contaDAO.buscarPorNumero(conta.getNumConta()) != null) {
            System.out.println("Conta já cadastrada!");
            return false;
        }

        if (clienteDAO.buscarPorCodigo(cliente.getCodCliente()) == null) {
            System.out.println("Cliente não existe!");
            return false;
        }

        if (agenciaDAO.buscarPorCodigo(conta.getAgencia().getCodAgencia()) == null) {
            System.out.println("Agência não existe!");
            return false;
        }

        conta.setCliente(cliente);
        return contaDAO.inserir(conta);
    }

    public Conta buscarContaPorNumero(int numConta) {
        return contaDAO.buscarPorNumero(numConta);
    }

    public List<Conta> listarContas() {
        return contaDAO.listar();
    }

    public List<TipoConta> listarTiposConta() throws SQLException {
        return tipoContaDAO.listar();
    }

    public List<TipoTransacao> listarTiposTransacao() throws SQLException {
        return tipoTransacaoDAO.listar();
    }


    // AGENCIA


    public boolean cadastrarAgencia(Agencia agencia) throws SQLException {
        if (agenciaDAO.buscarPorCodigo(agencia.getCodAgencia()) != null) {
            System.out.println("Agência já cadastrada!");
            return false;
        }
        return agenciaDAO.inserir(agencia);
    }

    public List<Agencia> listarAgencias() throws SQLException {
        return agenciaDAO.listar();
    }

    public Agencia buscarAgenciaPorCodigo(int codAgencia) throws SQLException {
        return (Agencia) agenciaDAO.buscarPorCodigo(codAgencia);
    }

    public boolean excluirAgenciaSemConta(int codAgencia) throws SQLException {
        if (contaDAO.existeContaParaAgencia(codAgencia)) {
            System.out.println("Agência possui conta vinculada!");
            return false;
        }
        return agenciaDAO.excluirSemConta(codAgencia);
    }


    // TIPO DE TRANSACOES 
    
    public boolean excluirTipoTransacao(int idTipoTransacao) throws SQLException {
        return tipoTransacaoDAO.excluir(idTipoTransacao);
    }
    
    public boolean cadastrarTipoTransacao(TipoTransacao tipoTransacao) throws SQLException {

        if (tipoTransacaoDAO.buscarPorCodigo(tipoTransacao.getIdTipoTransacao()) != null) {
            System.out.println("Tipo de transação já cadastrada!");
            return false;
        }

        return tipoTransacaoDAO.inserir(tipoTransacao);
    }

    // TRANSACOES (DEPÓSITO, SAQUE, EXTRATO)
    
    public boolean realizarDeposito(Conta conta, double valor) throws SQLException {

        if (valor <= 0) return false;

        conta.setSaldo(conta.getSaldo() + valor);
        contaDAO.atualizarSaldo(conta.getNumConta(), conta.getSaldo());

        TipoTransacao tipo = tipoTransacaoDAO.buscarPorCodigo(1); // 1 = DEPÓSITO

        Transacao t = new Transacao(
                transacaoDAO.obterProximoIdTransacao(),
                LocalDate.now(),
                LocalTime.now(),
                valor,
                0.0,
                tipo,
                conta,
                "Depósito"
        );

        transacaoDAO.inserir(t);
        return true;
    }

    public boolean realizarTransferencia(Conta conta, double valor, TipoTransacao tipo) {

        if (valor <= 0 || conta.getSaldo() < valor) return false;

        conta.setSaldo(conta.getSaldo() - valor);
        contaDAO.atualizarSaldo(conta.getNumConta(), conta.getSaldo());

        Transacao t = new Transacao(
                transacaoDAO.obterProximoIdTransacao(),
                LocalDate.now(),
                LocalTime.now(),
                valor,
                0.0,
                tipo,
                conta,
                "Débito"
        );

        transacaoDAO.inserir(t);
        return true;
    }

    public void exibirExtrato(int numConta) {

        Conta conta = contaDAO.buscarPorNumero(numConta);

        if (conta == null) {
            System.out.println("Conta não encontrada!");
            return;
        }

        List<Transacao> transacoes = transacaoDAO.extratoPorConta(conta.getCodConta());

        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada.");
            return;
        }

        System.out.println("\n=== EXTRATO DA CONTA ===");
        for (Transacao t : transacoes) {
            System.out.println(t);
            System.out.println("-------------------------");
        }
    }

    public Map<Integer, Clientes> getClientes() throws SQLException {
        Map<Integer, Clientes> map = new HashMap<>();
        for (Clientes c : clienteDAO.listarTodos()) {
            map.put(c.getCodCliente(), c);
        }
        return map;
    }

    public Map<Integer, Conta> getContas() {
        Map<Integer, Conta> map = new HashMap<>();
        for (Conta c : contaDAO.listar()) {
            map.put(c.getNumConta(), c);
        }
        return map;
    }

    public int getProximoIdTransacao() {
        return transacaoDAO.obterProximoIdTransacao();
    }
}
