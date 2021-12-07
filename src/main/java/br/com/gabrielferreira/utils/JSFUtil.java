package br.com.gabrielferreira.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JSFUtil {

	public static void enviarMensagemSucesso(String msg, String formulario) {
		FacesContext.getCurrentInstance().addMessage(formulario, new FacesMessage(FacesMessage.SEVERITY_INFO,
				msg, null));
	}
	
	public static void enviarMensagemErro(String msg, String formulario) {
		FacesContext.getCurrentInstance().addMessage(formulario, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, null));
	}
	
	public static void enviarMensagemAtencao(String msg, String formulario) {
		FacesContext.getCurrentInstance().addMessage(formulario, new FacesMessage(FacesMessage.SEVERITY_WARN,
				msg, null));
	}
}
