package br.com.gabrielferreira.enumeration;

public enum UploadArquivoEnum {

	TXT(1,"Arquivo Txt"),
	CSV(2,"Arquivo CSV");
	
	private Integer codigo;
	private String descricao;
	
	UploadArquivoEnum(Integer codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
