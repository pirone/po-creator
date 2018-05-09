package br.com.pirone.pocreator;

public enum TipoElemento {
	BUTTON("button","Button", "input[type='submit']"), COMBO("select","Combo"), INPUT("input","Input", "input[type!='hidden'][type!='submit']");
	
	private String valor;
	private String valorComParametros;
	private String classe;
	
	public String getValor() {
		return valor;
	}
	
	public String getValorComParametros() {
		return valorComParametros;
	}
	
	public String getClasse() {
		return classe;
	}
	
	TipoElemento(String tipo, String classe, String valorComParametros) {
		this.valor = tipo;
		this.classe = classe;
		this.valorComParametros = valorComParametros;
	}
	
	TipoElemento(String tipo, String classe) {
		this.valor = tipo;
		this.classe = classe;
	}
	
	
	
}
