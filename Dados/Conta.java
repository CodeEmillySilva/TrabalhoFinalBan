package Dados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Conta {
	
    private Integer codConta;
    private int nmConta;
    private LocalDate dataAbertura;
    private TipoConta tipoConta;
    private Agencia agencia;
    private double saldo;
    private Clientes cliente;
    private List<Transacao> transacoes;

    public Conta(Integer codConta, int nmConta, LocalDate dataAbertura, TipoConta tipoConta, Agencia agencia) {
        this.codConta = codConta;
        this.nmConta = nmConta;
        this.dataAbertura = dataAbertura;
        this.tipoConta = tipoConta;
        this.agencia = agencia;
        this.saldo = 0.0;
        this.transacoes = new ArrayList<>();
    }

	public Integer getCodConta() {
		return codConta;
	}

	public void setCodConta(Integer codConta) {
		this.codConta = codConta;
	}

	public int getNumConta() {
		return nmConta;
	}

	public void setNumConta(int nmConta) {
		this.nmConta = nmConta;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}
    
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Código da conta: ").append(this.codConta).append("\n");
	    sb.append("Número da conta: ").append(this.nmConta).append("\n");
	    sb.append("Data de abertura: ").append(this.dataAbertura).append("\n");
	    sb.append("Tipo de conta: ").append(this.tipoConta).append("\n");
	    sb.append("Agência: ").append(this.agencia).append("\n");
	    sb.append("Saldo: ").append(this.saldo).append("\n");
	    sb.append("Cliente: ").append(this.cliente).append("\n");
	    sb.append("Transações:\n");
	    for (int i = 0; i < this.transacoes.size(); i++) {
	        sb.append("  Transação ").append(i + 1).append(": ").append(this.transacoes.get(i)).append("\n");
	    }

	    return sb.toString();
	}
    
}
