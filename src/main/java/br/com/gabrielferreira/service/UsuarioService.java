package br.com.gabrielferreira.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.gabrielferreira.entidade.Usuario;
import br.com.gabrielferreira.repositorio.UsuarioRepositorio;

public class UsuarioService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepositorio usuarioRepositorio;
	
	public void inserir(Usuario usuario) {
		usuarioRepositorio.inserir(usuario);
	}
	
	public void inserir(List<Usuario> usuarios) {
		for(Usuario u : usuarios) {
			usuarioRepositorio.inserir(u);
		}
	}
	
	public List<Usuario> getUsuarios(){
		return usuarioRepositorio.listagem(Usuario.class);
	}

}
