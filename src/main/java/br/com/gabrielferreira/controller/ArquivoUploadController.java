package br.com.gabrielferreira.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import br.com.gabrielferreira.entidade.ArquivoUpload;
import br.com.gabrielferreira.entidade.Usuario;
import br.com.gabrielferreira.enumeration.UploadArquivoEnum;
import br.com.gabrielferreira.service.ArquivoUploadService;
import br.com.gabrielferreira.service.UsuarioService;
import br.com.gabrielferreira.utils.JSFUtil;

@Named
@ViewScoped
public class ArquivoUploadController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ArquivoUploadService service;
	
	@Inject
	private UsuarioService usuarioService;
	
	private List<Usuario> usuarios;
	
	private List<Usuario> listagemUsuarios;
	
	private List<ArquivoUpload> arquivoUploads;
	
	private ArquivoUpload arquivoUpload;
	
	private Part uploadArquivoDados;
	
	private Integer arquivoEscolhido;

	@PostConstruct
	public void init() {
		arquivoEscolhido = UploadArquivoEnum.TXT.getCodigo();
		arquivoUpload = new ArquivoUpload();
		usuarios = new ArrayList<>();
		listagemUsuarios = usuarioService.getUsuarios();
		arquivoUploads = service.getArquivos();
	}
	
	public void upload() throws IOException {
		if(arquivoEscolhido.equals(UploadArquivoEnum.TXT.getCodigo())) {
			inserirArquivoTxt();
		} else if(arquivoEscolhido.equals(UploadArquivoEnum.CSV.getCodigo())) {
			inserirArquivoCsv();
		}
	}
	
	private void inserirArquivoTxt() throws IOException {
		if (arquivoEscolhido.equals(UploadArquivoEnum.TXT.getCodigo())
				&& uploadArquivoDados.getContentType().equals("text/plain")) {

			Scanner sc = new Scanner(uploadArquivoDados.getInputStream(), "UTF-8");

			while (sc.hasNext()) {
				Usuario usuario = new Usuario();
				
				String linha = sc.nextLine();
				
				if(!linha.trim().isEmpty() && linha != null) {
					if(linha.contains(";")) {
						String[] delimitador = linha.split("\\;");
						usuario.setNome(delimitador[0]);
						usuario.setEmail(delimitador[1]);
						usuarios.add(usuario);
					}
				}
			}
			
			usuarioService.inserir(usuarios);
			inserirArquivo();

			sc.close();

			arquivoUpload = new ArquivoUpload();
			
			JSFUtil.enviarMensagemSucesso("Enviado com sucesso !", "frmUpload:msg");
		}
	}
	
	private void inserirArquivoCsv() throws IOException {
		if(uploadArquivoDados.getContentType()
				.equals("application/vnd.ms-excel") 
				&& arquivoEscolhido.equals(UploadArquivoEnum.CSV.getCodigo())) {
			
			Scanner scanner = new Scanner(uploadArquivoDados.getInputStream(),"UTF-8");
			scanner.useDelimiter(",");
			
			List<Usuario> usuarios = new ArrayList<>();
			
			while(scanner.hasNext()) {
				
				Usuario usuario = new Usuario();
				
				String linha = scanner.nextLine();
				
				if(!linha.trim().isEmpty() && linha != null) {
					linha = linha.replaceAll("\"", "");
					
					String[] dados = linha.split(",");
					usuario.setNome(dados[0]);
					usuario.setEmail(dados[1]);
					usuarios.add(usuario);
				}
				
				
			}
			
			
			usuarioService.inserir(usuarios);
			inserirArquivo();
			
			scanner.close();
			
			arquivoUpload = new ArquivoUpload();
			
			JSFUtil.enviarMensagemSucesso("Enviado com sucesso !", "frmUpload:msg");
		}
	}
	
	public void download() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idPessoaImagem = params.get("downloadArquivo");
		
		if(idPessoaImagem != null) {
			ArquivoUpload arquivoUpload = service.getArquivo(Long.parseLong(idPessoaImagem));
		
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			
			response.setHeader("Content-disposition","attachment; filename=download.csv");
			response.setContentType("application/octet-stream");  // Midia arquivo, imagem, fotos, videos
			response.setContentLength(arquivoUpload.getArquivoBytes().length);
			
			response.getOutputStream().write(arquivoUpload.getArquivoBytes());
			response.getOutputStream().flush();
			FacesContext.getCurrentInstance().responseComplete();
			
			
		}
		
	}
	
	private void inserirArquivo() throws IOException {
		byte[] bytesArquivo = toByteArray(uploadArquivoDados.getInputStream());
		arquivoUpload.setArquivoBytes(bytesArquivo);
		service.inserir(arquivoUpload);
	}
	
	public List<SelectItem> getArquivosSelecionados(){
		List<SelectItem> items = new ArrayList<SelectItem>();
		for(UploadArquivoEnum up : UploadArquivoEnum.values()) {
			items.add(new SelectItem(up.getCodigo(), up.getDescricao()));
		}
		return items;
	}
	
	public UploadArquivoEnum[] getArquivos() {
		return UploadArquivoEnum.values();
	}

	public ArquivoUpload getArquivoUpload() {
		return arquivoUpload;
	}

	public void setArquivoUpload(ArquivoUpload arquivoUpload) {
		this.arquivoUpload = arquivoUpload;
	}

	public Part getUploadArquivoDados() {
		return uploadArquivoDados;
	}

	public void setUploadArquivoDados(Part uploadArquivoDados) {
		this.uploadArquivoDados = uploadArquivoDados;
	}
	
	private byte[] toByteArray(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int reads = inputStream.read();
		while (reads != -1) {
			byteArrayOutputStream.write(reads);
			reads = inputStream.read();
		}
		return byteArrayOutputStream.toByteArray();
	}

	public Integer getArquivoEscolhido() {
		return arquivoEscolhido;
	}

	public void setArquivoEscolhido(Integer arquivoEscolhido) {
		this.arquivoEscolhido = arquivoEscolhido;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Usuario> getListagemUsuarios() {
		return listagemUsuarios;
	}

	public void setListagemUsuarios(List<Usuario> listagemUsuarios) {
		this.listagemUsuarios = listagemUsuarios;
	}

	public List<ArquivoUpload> getArquivoUploads() {
		return arquivoUploads;
	}

	public void setArquivoUploads(List<ArquivoUpload> arquivoUploads) {
		this.arquivoUploads = arquivoUploads;
	}
	
	
	
	
}
