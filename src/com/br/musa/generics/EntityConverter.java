package com.br.musa.generics;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.musa.entidades.Cliente;

@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		if (submittedValue == null || "".equals(submittedValue.trim()) || "null".equals(submittedValue)) {
			return null;
		} else {
			Cliente cliente = new Cliente();
			cliente.setId(Long.parseLong(submittedValue));
			return cliente;

		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null || "".equals(value)) {
			return "";
		} else {
			return String.valueOf(((Cliente) value).getId());
		}
	}
}
