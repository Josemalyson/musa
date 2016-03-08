package com.br.musa.util;

import org.primefaces.context.RequestContext;

public abstract class JavaScriptUtil {

	public static void marcarCampoObrigatorio(String id) {
		RequestContext.getCurrentInstance().execute("marcarCampoObrigatorio('#" + id + "')");
	}

	// static ScriptEngine engine = new
	// ScriptEngineManager().getEngineByName("nashorn");
	//
	// public static void criandoEngineJavaScript() {
	//
	// try {
	// engine.eval(new
	// FileReader(FacesContext.getCurrentInstance().getExternalContext().getRealPath("")
	// + "\\recursos\\js\\meujs.js"));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (ScriptException e) {
	// e.printStackTrace();
	// }
	// }
	//
	//
	// public static void marcarCampoObrigatorio(String id) {
	// criandoEngineJavaScript();
	// Invocable invocable = (Invocable) engine;
	// try {
	// Object result = invocable.invokeFunction("marcarCampoObrigatorio", id);
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (ScriptException e) {
	// e.printStackTrace();
	// }
	//
	// }

}
