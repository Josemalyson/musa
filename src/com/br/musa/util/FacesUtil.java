package com.br.musa.util;



import java.io.File;
import java.text.MessageFormat;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class FacesUtil {

	public static ServletContext getServletContext() {
		return ((ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext());
	}

	public static Object getManagedBean(String beanName) {
		Object o = getValueBinding(getJsfEl(beanName)).getValue(
				FacesContext.getCurrentInstance());

		return o;
	}

	public static void resetManagedBean(String beanName) {
		getValueBinding(getJsfEl(beanName)).setValue(
				FacesContext.getCurrentInstance(), null);
	}

	public static void setManagedBeanInSession(String beanName,
			Object managedBean) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(beanName, managedBean);
	}

	public static Object getRequestParameter(Object name) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	public static void setRequestParameter(String key, Object value) {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		req.setAttribute(key, value);
	}

	public static Object setSessionAttribute(String key, Object value) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().put(key, value);
	}

	public static Object getSessionAttribute(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(key);
	}

	private static javax.faces.application.Application getApplication() {
		ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder
				.getFactory("javax.faces.application.ApplicationFactory");
		return appFactory.getApplication();
	}

	private static ValueBinding getValueBinding(String el) {
		return getApplication().createValueBinding(el);
	}

	public static HttpServletRequest getServletRequest() {
		return ((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest());
	}

	public static HttpServletResponse getServletResponse() {
		return ((HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse());
	}

	private static Object getElValue(String el) {
		return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
	}

	private static String getJsfEl(String value) {
		return "#{" + value + "}";
	}

	public static String getRealPath(String pasta) {
		return getServletContext().getRealPath(File.separator + pasta);
	}

	public static void adicionarAtributoFlash(String nome, Object objeto) {
		obterContexto().getExternalContext().getFlash().put(nome, objeto);
	}

	public String getContextPath() {
		return getServletContext().getContextPath();
	}

	public static Object obterAtributoFlash(String nome) {
		return obterContexto().getExternalContext().getFlash().get(nome);
	}

	public static void adicionarMensagem(String texto) {
		registrarFacesMessage(texto, FacesMessage.SEVERITY_INFO);
	}

	public static void adicionarMensagem(String texto, Object[] params) {
		registrarFacesMessage(texto, FacesMessage.SEVERITY_INFO);
	}

	public static void adicionarAviso(String texto) {
		registrarFacesMessage(texto, FacesMessage.SEVERITY_WARN);
	}

	public static void adicionarAviso(String texto, Object[] params) {
		registrarFacesMessage(texto, FacesMessage.SEVERITY_WARN);
	}

	public static void adicionarErro(String texto) {
		registrarFacesMessage(texto, FacesMessage.SEVERITY_ERROR);
	}

	public static void adicionarErro(String texto, Object[] params) {
		registrarFacesMessage(texto, FacesMessage.SEVERITY_ERROR);
	}

	public static void manterMensagensNoFlash() {
		obterContexto().getExternalContext().getFlash().setKeepMessages(true);
	}

	public static String obterTexto(String chave, Object[] params) {

		if (ObjetoUtil.isBlank(chave).booleanValue()) {
			return chave;
		}

		return MessageFormat.format(chave, params);
	}

	public static NavigationHandler obterNavigationHandler() {
		return obterContexto().getApplication().getNavigationHandler();
	}

	private static void registrarFacesMessage(String texto,
			FacesMessage.Severity severidade) {
		FacesMessage mensagem = new FacesMessage();

		mensagem.setSummary(texto);
		mensagem.setSeverity(severidade);

		obterContexto().addMessage(null, mensagem);
	}

	public static FacesContext obterContexto() {
		return FacesContext.getCurrentInstance();
	}

	public static String obterTextoMensagem(String chave, Object[] params) {
		if (ObjetoUtil.isBlank(chave).booleanValue()) {
			return chave;
		}

		return MessageFormat.format(chave, params);
	}
}