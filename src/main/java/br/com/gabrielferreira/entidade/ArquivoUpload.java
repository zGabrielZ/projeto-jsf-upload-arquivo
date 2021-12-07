package br.com.gabrielferreira.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ARQUIVO_UPLOAD")
public class ArquivoUpload implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "ARQUIVO_SEQ", sequenceName = "SEQ_ARQUIVO", allocationSize = 1, initialValue = 1 )
	@GeneratedValue(generator = "ARQUIVO_SEQ",strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String descricao;
	
	@Lob
	private byte[] arquivoBytes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public byte[] getArquivoBytes() {
		return arquivoBytes;
	}

	public void setArquivoBytes(byte[] arquivoBytes) {
		this.arquivoBytes = arquivoBytes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArquivoUpload other = (ArquivoUpload) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
	
	
	
	

}
