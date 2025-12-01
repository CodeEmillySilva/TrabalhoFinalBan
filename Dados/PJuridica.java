package Dados;

public class PJuridica extends Clientes {
    private String CNPJ;
    private String razaoSocial;

    public PJuridica(int codCliente, String email, String telefone, String endereco, 
                     String CNPJ, String razaoSocial) {
        super(codCliente, email, telefone, endereco, 2);
        this.CNPJ = CNPJ;
        this.razaoSocial = razaoSocial;
    }

    // Getters e Setters
    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String toString() {
        return "Pessoa Jurídica:\n" +
                "Código: " + getCodCliente() + "\n" +
                "Razão Social: " + razaoSocial + "\n" +
                "CNPJ: " + CNPJ + "\n" +
                "E-mail: " + getEmail() + "\n" +
                "Telefone: " + getTelefone() + "\n" +
                "Endereço: " + getEndereco();
    }
    
}
