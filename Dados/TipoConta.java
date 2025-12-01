package Dados;

public class TipoConta {
	
    private int id;
    private String descricaoConta;

    public TipoConta(int id, String descricaoConta) {
        this.id = id;
        this.descricaoConta = descricaoConta;
    }

    public int getId() { 
    	return id; 
    }
    
    public String getDescricaoConta() { 
    	return descricaoConta; 
    }

	public void setId(int id) {
		this.id = id;
	}

	public void setDescricaoConta(String descricaoConta) {
		this.descricaoConta = descricaoConta;
	}
	
	@Override
	public String toString() {
	    return "Conta: " + this.id + ", " + this.descricaoConta + "\n";
	}

    
}
