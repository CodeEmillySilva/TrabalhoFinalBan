package Dados;

import java.util.ArrayList;
import java.util.List;

public abstract class Clientes {
	
    private int codCliente;
    private int tipoCliente; // 1 para PF, 2 para PJ
    private String email;
    private String telefone;
    private String endereco;
    private List<Conta> contas;

    public Clientes(int codCliente, String email, String telefone, String endereco, int tipoCliente) {
        this.codCliente = codCliente;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
        this.contas = new ArrayList<>();
    }

    // Getters e Setters
    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void adicionarConta(Conta conta) {
        this.contas.add(conta);
    }

    public int getTipoCliente() {
        return tipoCliente;
    }

    public void setTipo(int tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código do Cliente: ").append(this.codCliente).append("\n");
        sb.append("E-mail: ").append(this.email).append("\n");
        sb.append("Telefone: ").append(this.telefone).append("\n");
        sb.append("Endereço: ").append(this.endereco).append("\n");
        sb.append("Tipo: ").append(this.tipoCliente).append("\n");
        sb.append("Contas: ").append(this.contas);
        return sb.toString();
    }

}
