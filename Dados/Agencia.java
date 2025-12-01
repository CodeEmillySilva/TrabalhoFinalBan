package Dados;

public class Agencia {
    private int codAgencia;
    private String endereco;
    private String telefone;

    public Agencia(int codAgencia, String endereco, String telefone) {
        this.codAgencia = codAgencia;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    // Getters e Setters
    public int getCodAgencia() {
        return codAgencia;
    }

    public void setCodAgencia(int codAgencia) {
        this.codAgencia = codAgencia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Código da agência: ").append(this.codAgencia).append("\n");
	    sb.append("Endereço: ").append(this.endereco).append("\n");
	    sb.append("Telefone: ").append(this.telefone);
	    return sb.toString();
	}

}
