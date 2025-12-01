package Dados;

import java.time.LocalDate;

public class PFisica extends Clientes {
    private String CPF;
    private String nome;
    private LocalDate dataNascimento;

    public PFisica(int codCliente, String email, String telefone, String endereco, String CPF, String nome, LocalDate dataNascimento) {
        super(codCliente, email, telefone, endereco, 1);
        this.CPF = CPF;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    // Getters e Setters
    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "Pessoa Física:\n" +
                "Código: " + getCodCliente() + "\n" +
                "Nome: " + nome + "\n" +
                "CPF: " + CPF + "\n" +
                "Data Nasc.: " + dataNascimento + "\n" +
                "E-mail: " + getEmail() + "\n" +
                "Telefone: " + getTelefone() + "\n" +
                "Endereço: " + getEndereco();
    }
    
}
