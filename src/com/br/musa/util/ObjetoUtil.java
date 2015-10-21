package com.br.musa.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;

import com.br.musa.reflexao.Reflexao;

@SuppressWarnings("rawtypes")
public class ObjetoUtil {

	public static Boolean isBlank(Object obj) {
		if (obj == null)
			return Boolean.valueOf(true);

		if ((((obj instanceof Collection) || (obj instanceof Collections)))
				&& (Colecoes.isEmpty((Collection) obj)))
			return Boolean.valueOf(true);

		if ((obj instanceof String) && (obj.toString().trim().equals("")))
			return Boolean.valueOf(true);

		if (((obj instanceof Number) && (obj == null))
				|| (obj.equals(Integer.valueOf(0))))
			return Boolean.valueOf(true);

		Boolean existGetter = Boolean.valueOf(false);
		Boolean gettersNulo = Boolean.valueOf(true);
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (Reflexao.existeGet(obj, field.getName())) {
				existGetter = Boolean.valueOf(true);
				if (Reflexao.getValorDoAtributo(obj, field.getName()) != null) {
					gettersNulo = Boolean.valueOf(false);
				}
			}
		}
		if ((existGetter.booleanValue()) && (gettersNulo.booleanValue()))
			return Boolean.valueOf(true);

		return Boolean.valueOf(false);
	}

	public static Boolean notBlank(Object obj) {
		return Boolean.valueOf(!(isBlank(obj).booleanValue()));
	}

}