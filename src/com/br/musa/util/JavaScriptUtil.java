package com.br.musa.util;

import org.primefaces.context.RequestContext;

public abstract class JavaScriptUtil {

	public static void marcarCampoObrigatorio(String id) {
		RequestContext.getCurrentInstance().execute("marcarCampoObrigatorio('" + id + "')");
	}

}