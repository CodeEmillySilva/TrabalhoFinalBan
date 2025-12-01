package Main;


import Dados.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class PrincipalBD {

    private static SistemaBD sistema = new SistemaBD();
    private static Scanner teclado = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws SQLException {

        int opcao;

        do {
            opcao = imprimeMenu();

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarAgencia();
                case 3 -> listarClientes();
                case 4 -> listarAgencias();
                case 5 -> excluirCliente();
                case 6 -> excluirAgencia();
                case 7 -> cadastrarTipoConta();
                case 8 -> listarTiposConta();
                case 9 -> cadastrarTipoTransacao();
                case 10 -> listarTiposTransacao();
                case 11 -> excluirTipoConta();
                case 12 -> excluirTipoTransacao();
                case 13 -> cadastrarConta();
                case 14 -> listarContas();
                case 15 -> excluirConta();
                case 16 -> quantidadeContasPorAgencia();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida!");
                
            }

        } while (opcao != 0);
    }

    private static int imprimeMenu() {
        System.out.println("\n=== SISTEMA BANCARIO (BD) ===");
        System.out.println("0  Sair");
        System.out.println("1  Cadastrar Cliente");
        System.out.println("2  Cadastrar Agencia");
        System.out.println("3  Listar Clientes");
        System.out.println("4  Listar Agencias");
        System.out.println("5  Excluir Cliente sem conta");
        System.out.println("6  Excluir Agencia sem conta");
        System.out.println("7  Cadastrar Tipo de Conta");
        System.out.println("8  Listar Tipos de Conta");
        System.out.println("9  Cadastrar Tipo de Transacao");
        System.out.println("10 Listar Tipos de Transacao");
        System.out.println("11 Excluir Tipo de Conta");
        System.out.println("12 Excluir Tipo de Transacao");
        System.out.println("13 Cadastrar Conta");
        System.out.println("14 Listar Contas");
        System.out.println("15 Excluir Conta");
        System.out.println("16 Quantidade de Contas por Agência");

        return teclado.nextInt();
    }

    // ================= CLIENTE =================

    public static void cadastrarCliente() throws SQLException {

        System.out.println("\n1 - Pessoa Fisica | 2 - Pessoa Juridica");
        int tipo = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Codigo: ");
        int cod = teclado.nextInt();
        teclado.nextLine();

        if (sistema.buscarClientePorCodigo(cod) != null) {
            System.out.println("Cliente ja existe!");
            return;
        }

        System.out.print("Email: ");
        String email = teclado.nextLine();

        System.out.print("Telefone: ");
        String telefone = teclado.nextLine();

        System.out.print("Endereco: ");
        String endereco = teclado.nextLine();

        if (tipo == 1) {
            System.out.print("CPF: ");
            String cpf = teclado.nextLine();

            System.out.print("Nome: ");
            String nome = teclado.nextLine();

            System.out.print("Data nascimento (dd/MM/yyyy): ");
            LocalDate data = LocalDate.parse(teclado.nextLine(), dateFormatter);

            PFisica pf = new PFisica(cod, email, telefone, endereco, cpf, nome, data);
            sistema.cadastrarCliente(pf);

        } else {
            System.out.print("CNPJ: ");
            String cnpj = teclado.nextLine();

            System.out.print("Razao Social: ");
            String razao = teclado.nextLine();

            PJuridica pj = new PJuridica(cod, email, telefone, endereco, cnpj, razao);
            sistema.cadastrarCliente(pj);
        }

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public static void listarClientes() throws SQLException {

        List<Clientes> lista = sistema.listarClientes();

        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (Clientes c : lista) {
            System.out.println(c);
            System.out.println("---------------------------");
        }
    }

    public static void excluirCliente() throws SQLException {

        System.out.print("Codigo do cliente: ");
        int cod = teclado.nextInt();

        if (sistema.excluirClienteSemConta(cod)) {
            System.out.println("Cliente excluido!");
        } else {
            System.out.println("Erro ao excluir cliente.");
        }
    }

    // ================= AGÊNCIA =================

    public static void cadastrarAgencia() throws SQLException {

        System.out.print("Codigo: ");
        int cod = teclado.nextInt();
        teclado.nextLine();

        if (sistema.buscarAgenciaPorCodigo(cod) != null) {
            System.out.println("Agencia ja existe!");
            return;
        }

        System.out.print("Endereço: ");
        String end = teclado.nextLine();

        System.out.print("Telefone: ");
        String tel = teclado.nextLine();

        sistema.cadastrarAgencia(new Agencia(cod, end, tel));
        System.out.println("Agencia cadastrada!");
    }

    public static void listarAgencias() throws SQLException {

        List<Agencia> lista = sistema.listarAgencias();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma agencia cadastrada.");
            return;
        }

        for (Agencia a : lista) {
            System.out.println(a);
            System.out.println("---------------------------");
        }
    }

    public static void excluirAgencia() throws SQLException {

        System.out.print("Codigo da agencia: ");
        int cod = teclado.nextInt();

        if (sistema.excluirAgenciaSemConta(cod)) {
            System.out.println("Agencia excluida!");
        } else {
            System.out.println("Erro ao excluir agencia.");
        }
    }
    
    // ================= CONTA =================
    
    public static void cadastrarConta() throws SQLException {

        System.out.println("\n=== CADASTRO DE CONTA ===");

        System.out.print("Codigo da Conta: ");
        int codConta = teclado.nextInt();

        System.out.print("Numero da Conta: ");
        int numConta = teclado.nextInt();

        System.out.print("Codigo do Cliente: ");
        int codCliente = teclado.nextInt();

        System.out.print("Codigo da Agencia: ");
        int codAgencia = teclado.nextInt();

        System.out.print("ID do Tipo de Conta: ");
        int idTipoConta = teclado.nextInt();

        boolean sucesso = sistema.cadastrarConta(
            codConta, numConta, codCliente, codAgencia, idTipoConta
        );

        if (sucesso) {
            System.out.println("Conta cadastrada com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar conta.");
        }
    }
    
    public static void listarContas() {

        List<Conta> lista = sistema.listarContas();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }

        for (Conta c : lista) {
            System.out.println(c);
            System.out.println("---------------------------");
        }
    }

    public static void excluirConta() {

        System.out.print("Codigo da Conta: ");
        int cod = teclado.nextInt();

        if (sistema.excluirConta(cod)) {
            System.out.println("Conta excluida!");
        } else {
            System.out.println("Erro ao excluir conta.");
        }
    }
    
    
    public static void quantidadeContasPorAgencia() {

        System.out.print("Codigo da Agencia: ");
        int codAgencia = teclado.nextInt();

        int qtd = sistema.quantidadeContasPorAgencia(codAgencia);

        System.out.println("Quantidade de contas nesta agencia: " + qtd);
    }



    // ================= TIPO CONTA =================

    public static void cadastrarTipoConta() throws SQLException {

        System.out.print("Codigo: ");
        int id = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Descricao: ");
        String desc = teclado.nextLine();

        sistema.cadastrarTipoConta(new TipoConta(id, desc));
        System.out.println("Tipo de conta cadastrado!");
    }
    
    public static void excluirTipoConta() throws SQLException {

        System.out.print("ID do Tipo de Conta: ");
        int id = teclado.nextInt();

        if (sistema.excluirTipoConta(id)) {
            System.out.println("Tipo de Conta excluído!");
        } else {
            System.out.println("Erro ao excluir Tipo de Conta.");
        }
    }

    public static void listarTiposConta() throws SQLException {

        List<TipoConta> lista = sistema.listarTiposConta();

        if (lista.isEmpty()) {
            System.out.println("Nenhum tipo de conta.");
            return;
        }

        for (TipoConta t : lista) {
            System.out.println(t);
        }
    }

    // ================= TIPO TRANSAÇÃO =================

    public static void cadastrarTipoTransacao() throws SQLException {

        System.out.print("Codigo: ");
        int id = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Descriçcao: ");
        String desc = teclado.nextLine();

        sistema.cadastrarTipoTransacao(new TipoTransacao(id, desc));
        System.out.println("Tipo de transacao cadastrado!");
    }
    
    public static void excluirTipoTransacao() throws SQLException {

        System.out.print("ID do Tipo de Transação: ");
        int id = teclado.nextInt();

        if (sistema.excluirTipoTransacao(id)) {
            System.out.println("Tipo de Transação excluído!");
        } else {
            System.out.println("Erro ao excluir Tipo de Transação.");
        }
    }

    public static void listarTiposTransacao() throws SQLException {

        List<TipoTransacao> lista = sistema.listarTiposTransacao();

        if (lista.isEmpty()) {
            System.out.println("Nenhum tipo de transacao.");
            return;
        }

        for (TipoTransacao t : lista) {
            System.out.println(t);
        }
    }
}
