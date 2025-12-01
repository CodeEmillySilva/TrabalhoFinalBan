package Dados;

public class TipoTransacao {

    private int idTipoTransacao;
    private String tipoTransacao;

    public TipoTransacao(int idTipoTransacao, String tipoTransacao) {
        this.idTipoTransacao = idTipoTransacao;
        this.tipoTransacao = tipoTransacao;
    }

	public int getIdTipoTransacao() {
		return idTipoTransacao;
	}

	public void setIdTipoTransacao(int idTipoTransacao) {
		this.idTipoTransacao = idTipoTransacao;
	}

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	@Override
	public String toString() {
	    return "Transação: " + this.idTipoTransacao + ", " + this.tipoTransacao + "\n";
	}
    
}
