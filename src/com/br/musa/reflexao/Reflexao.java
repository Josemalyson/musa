package com.br.musa.reflexao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Reflexao {

	private static Map<String, Class> mapaDeClasses;
	private static Map<String, Map<String, Method>> mapaDeSets;
	private static Map<String, Map<String, Method>> mapaDeGets;

	private static final Map<String, Map<String, Method>> getMapaDeSets() {
		if (mapaDeSets == null) {
			mapaDeSets = new HashMap();
		}
		return mapaDeSets;
	}

	public static Object getValorDoAtributoComposto(Object objeto,
			String atributo) {
		Object valorDoAtributo = objeto;
		StringTokenizer token = new StringTokenizer(atributo, ".");
		while (token.hasMoreTokens()) {
			if (valorDoAtributo == null) {
				return null;
			}
			valorDoAtributo = getValorDoAtributo(valorDoAtributo,
					token.nextToken());
		}
		return valorDoAtributo;
	}

	private static final Map<String, Class> getMapaDeClasses() {
		if (mapaDeClasses == null) {
			mapaDeClasses = new HashMap();
		}
		return mapaDeClasses;
	}

	private static final String getNomeBaseDoMetodo(String nomeDoAtributo) {
		String nomeBaseDoMetodo = nomeDoAtributo.substring(0, 1).toUpperCase()
				+ nomeDoAtributo.substring(1, nomeDoAtributo.length());
		return nomeBaseDoMetodo;
	}

	private static final String getNomeDoMetodoGet(String nomeDoAtributo) {
		return "get" + getNomeBaseDoMetodo(nomeDoAtributo);
	}

	private static final String getNomeDoMetodoSet(String nomeDoAtributo) {
		return "set" + getNomeBaseDoMetodo(nomeDoAtributo);
	}

	public static final Object criaInstancia(String nomeDaClasse) {
		return criaInstancia(getClasse(nomeDaClasse));
	}

	public static final <T> T criaInstancia(Class<T> classe) {
		try {
			return classe.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final <T> T criaInstancia(Class<T> classe, Object[] parametros) {
		try {
			Class[] tipos = new Class[parametros.length];
			for (int i = 0; i < parametros.length; ++i) {
				tipos[i] = parametros[i].getClass();
			}
			Constructor construtor = classe.getConstructor(tipos);
			return (T) construtor.newInstance(parametros);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final Class getClasse(String nomeDaClasse) {
		return consultaCacheDeClasses(nomeDaClasse);
	}

	private static final Class consultaCacheDeClasses(String nomeDaClasse) {
		Class classe = (Class) getMapaDeClasses().get(nomeDaClasse);
		if (classe == null) {
			classe = carregaCacheDeClasses(nomeDaClasse);
		}
		return classe;
	}

	private static final Class carregaCacheDeClasses(String nomeDaClasse) {
		try {
			Class classe = Class.forName(nomeDaClasse);
			getMapaDeClasses().put(nomeDaClasse, classe);
			return classe;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static final Class[] getClassesDosParametros(Object[] parametros) {
		if (parametros == null) {
			return null;
		}
		Class[] classes = new Class[parametros.length];
		for (int i = 0; i < classes.length; ++i) {
			classes[i] = parametros[i].getClass();
		}
		return classes;
	}

	public static final Object executaMetodoEstatico(String nomeDaClasse,
			String nomeDoMetodo, Object[] parametros) {
		Class classe = getClasse(nomeDaClasse);
		try {
			Method metodo = classe.getMethod(nomeDoMetodo,
					getClassesDosParametros(parametros));

			return metodo.invoke(classe, parametros);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final Object executaMetodo(String nomeDaClasse,
			String nomeDoMetodo, Object[] parametros) {
		Object objeto = criaInstancia(nomeDaClasse);
		try {
			Method metodo = objeto.getClass().getMethod(nomeDoMetodo,
					getClassesDosParametros(parametros));

			return metodo.invoke(objeto, parametros);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final Object executaMetodo(Object objeto,
			String nomeDoMetodo, Object[] parametros)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Method metodo = getMetodo(objeto, nomeDoMetodo, parametros);
		return metodo.invoke(metodo, parametros);
	}

	private static final Method getMetodo(Object objeto, String nomeDoMetodo,
			Object[] parametros) throws NoSuchMethodException,
			SecurityException {
		Class[] tiposDosParametros = getTiposDosParametros(parametros);
		return objeto.getClass().getMethod(nomeDoMetodo, tiposDosParametros);
	}

	private static Class[] getTiposDosParametros(Object[] parametros) {
		Class[] tiposDosParametros = new Class[parametros.length];
		for (int i = 0; i < parametros.length; ++i) {
			tiposDosParametros[i] = parametros[i].getClass();
		}
		return tiposDosParametros;
	}

	public static final void setValorDoAtributo(Object objetoDestino,
			String nomeDoCampo, Object valor) {
		Method metodo = getMetodoSet(objetoDestino, nomeDoCampo);
		Object[] parametros = { valor };
		executaMetodo(objetoDestino, metodo, parametros);
	}

	public static final Object getValorDoAtributo(Object objeto,
			String nomeDoCampo) {
		Method metodo = getMetodoGet(objeto, nomeDoCampo);
		return executaMetodo(objeto, metodo, null);
	}

	private static final Map<String, Map<String, Method>> getMapaDeGets() {
		if (mapaDeGets == null) {
			mapaDeGets = new HashMap();
		}
		return mapaDeGets;
	}

	private static final Method getMetodoGet(Object objeto, String nomeDoCampo) {
		Map getsDaClasse = getGetsDaClasse(objeto.getClass().getName());

		Method metodoSet = (Method) getsDaClasse.get(nomeDoCampo);
		if (metodoSet == null) {
			metodoSet = criaMetodoGetDoAtributo(objeto, nomeDoCampo);
			getsDaClasse.put(nomeDoCampo, metodoSet);
		}
		return metodoSet;
	}

	private static final Method criaMetodoGetDoAtributo(Object objeto,
			String nomeDoCampo) {
		return criaMetodo(objeto.getClass(), getNomeDoMetodoGet(nomeDoCampo),
				null);
	}

	private static final Map<String, Method> getGetsDaClasse(String nomeDoCampo) {
		Map getsDaClasse = (Map) getMapaDeGets().get(nomeDoCampo);
		if (getsDaClasse == null) {
			getsDaClasse = new HashMap();
			getMapaDeGets().put(nomeDoCampo, getsDaClasse);
		}
		return getsDaClasse;
	}

	private static final Object executaMetodo(Object objetoDestino,
			Method metodo, Object[] parametros) {
		try {
			return metodo.invoke(objetoDestino, parametros);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final Method getMetodoSet(Object objetoDestino,
			String nomeDoCampo) {
		Map setsDaClasse = getSetsDaClasse(objetoDestino.getClass());

		Method metodo = (Method) setsDaClasse.get(nomeDoCampo);
		if (metodo == null) {
			Class classeDoParametro = descobreClasseDoParametro(objetoDestino,
					nomeDoCampo);

			metodo = criaMetodoSetDoAtributo(objetoDestino, nomeDoCampo,
					classeDoParametro);

			setsDaClasse.put(nomeDoCampo, metodo);
		}
		return metodo;
	}

	private static Class descobreClasseDoParametro(Object objetoDestino,
			String nomeDoCampo) {
		Class classeDoParametro = getMetodoGet(objetoDestino, nomeDoCampo)
				.getReturnType();

		if (classeDoParametro == null) {
			classeDoParametro = objetoDestino.getClass();
		}
		return classeDoParametro;
	}

	private static final Method criaMetodoSetDoAtributo(Object objetoDestino,
			String nomeDoCampo, Class classeDoParametro) {
		Class[] classesDosParametros = { classeDoParametro };
		Method metodo = criaMetodo(objetoDestino.getClass(),
				getNomeDoMetodoSet(nomeDoCampo), classesDosParametros);

		return metodo;
	}

	private static final Method criaMetodo(Class<?> classeDoObjetoDestino,
			String nomeDoMetodo, Class[] classesDosParametros) {
		Method metodo;
		try {
			metodo = classeDoObjetoDestino.getMethod(nomeDoMetodo,
					classesDosParametros);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return metodo;
	}

	private static final Map<String, Method> getSetsDaClasse(Class classe) {
		Map setsDaClasse = (Map) getMapaDeSets().get(classe);
		if (setsDaClasse == null) {
			setsDaClasse = new HashMap();
			getMapaDeSets().put(classe.getName(), setsDaClasse);
		}
		return setsDaClasse;
	}

	public static boolean existeGet(Object objeto, String nomeDoAtributo) {
		if (nomeDoAtributo != null) {
			try {
				objeto.getClass().getMethod(getNomeDoMetodoGet(nomeDoAtributo),
						new Class[0]);
				return true;
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				return false;
			}
		}
		return false;
	}

	public static boolean existeSet(Object objeto, String nomeDoCampo) {
		if (nomeDoCampo != null) {
			try {
				getMetodoSet(objeto, nomeDoCampo);
				return true;
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (RuntimeException e) {
				return false;
			}
		}
		return false;
	}

	public static void copyProperties(Object dest, Object orig) {
		for (Field field : getInheritedFields(orig))
			if (existeGet(orig, field.getName())) {
				Object obj = getValorDoAtributo(orig, field.getName());
				setValorDoAtributo(dest, field.getName(), obj);
			}
	}

	public static List<Field> getInheritedFields(Object objeto) {
		List fields = new ArrayList();

		for (Field f : objeto.getClass().getDeclaredFields()) {
			fields.add(f);
		}

		for (Class klass : getAllSuperclasses(objeto.getClass())) {
			if (!("java.lang.Object".equalsIgnoreCase(klass.getName()))) {
				for (Field f : klass.getDeclaredFields()) {
					fields.add(f);
				}
			}
		}

		return fields;
	}

	public static List<Class<?>> getAllSuperclasses(Class<?> clazz) {
		List classes = new ArrayList();

		Class superclass = clazz.getSuperclass();
		while (superclass != null) {
			classes.add(superclass);
			superclass = superclass.getSuperclass();
		}

		return classes;
	}

	public static Boolean ehAnnotationPresenteNoCampo(Field campo,
			Class<? extends Annotation> annotationClass) {
		return Boolean.valueOf(campo.isAnnotationPresent(annotationClass));
	}

	public static Boolean ehAnnotationPresenteNoMetodoDoCampo(Object objeto,
			Field campo, Class<? extends Annotation> annotationClass) {
		Boolean resultado = Boolean.valueOf(false);

		for (Method metodo : objeto.getClass().getMethods()) {
			if (ehGetterCampo(metodo, campo).booleanValue()) {
				resultado = Boolean.valueOf(metodo
						.isAnnotationPresent(annotationClass));
			}
		}

		return resultado;
	}

	private static Boolean ehGetterCampo(Method metodo, Field campo) {
		String nomeMetodo = metodo.getName();
		String nomeCampo = campo.getName();
		Boolean resultado = Boolean.valueOf(false);

		if (nomeMetodo.startsWith("get")) {
			nomeMetodo = nomeMetodo.replaceFirst("get", "").toLowerCase();

			if (nomeMetodo.equals(nomeCampo)) {
				resultado = Boolean.valueOf(true);
			}
		}
		return resultado;
	}

	public static Boolean ehCampoVazio(Field campo, Object objeto) {
		Boolean resultado = Boolean.valueOf(false);
		try {
			campo.setAccessible(true);

			Object conteudoCampo = campo.get(objeto);

			resultado = Boolean.valueOf(conteudoCampo == null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return resultado;
	}

	public static Collection<Field> getFieldsObjeto(Object objeto) {
		Class clazz = objeto.getClass();
		Class superClazz = clazz.getSuperclass();

		Collection fields = new ArrayList();

		for (Field field : superClazz.getDeclaredFields()) {
			if (existeGet(objeto, field.getName())) {
				fields.add(field);
			}
		}

		for (Field field : clazz.getDeclaredFields()) {
			if (existeGet(objeto, field.getName())) {
				fields.add(field);
			}
		}

		return fields;
	}

	/**
	 * Constrói o nome do método get, de acordo com o nome do atributo
	 * 
	 * @param fieldName
	 * @return Método get do atributo
	 */
	public static String buildGetMethodName(String fieldName) {
		StringBuilder methodName = new StringBuilder("get");
		methodName.append(fieldName.substring(0, 1).toUpperCase());
		methodName.append(fieldName.substring(1, fieldName.length()));
		return methodName.toString();
	}

	/**
	 * Constrói o nome do método set, de acordo com o nome do atributo
	 * 
	 * @param fieldName
	 * @return Método set do atributo
	 */
	public static String buildSetMethodName(String fieldName) {
		StringBuilder methodName = new StringBuilder("set");
		methodName.append(fieldName.substring(0, 1).toUpperCase());
		methodName.append(fieldName.substring(1, fieldName.length()));
		return methodName.toString();
	}
}