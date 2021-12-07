package br.com.gabrielferreira.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.gabrielferreira.entidade.ArquivoUpload;
import br.com.gabrielferreira.repositorio.ArquivoUploadRepositorio;

public class ArquivoUploadService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ArquivoUploadRepositorio arquivoUploadRepositorio;
	
	public void inserir(ArquivoUpload arquivoUpload) {
		arquivoUploadRepositorio.inserir(arquivoUpload);
	}
	
	public ArquivoUpload getArquivo(Long id) {
		return arquivoUploadRepositorio.pesquisarPorId(id, ArquivoUpload.class);
	}
	
	public List<ArquivoUpload> getArquivos(){
		return arquivoUploadRepositorio.listagem(ArquivoUpload.class);
	}

}
