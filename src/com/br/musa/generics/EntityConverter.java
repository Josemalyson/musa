package com.br.musa.generics;

import java.util.Map;
import java.util.WeakHashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.musa.entidades.Cliente;
import com.br.musa.util.ObjetoUtil;

@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

//    @Override
//    public String getAsString(FacesContext context, UIComponent component, Object entity) {
//        synchronized (entities) {
//            if (!entities.containsKey(entity)) {
//                String uuid = UUID.randomUUID().toString();
//                entities.put(entity, uuid);
//                return uuid;
//            } else {
//                return entities.get(entity);
//            }
//        }
//    }
//
//    @Override
//    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
//        for (Entry<Object, String> entry : entities.entrySet()) {
//            if (entry.getValue().equals(uuid)) {
//                return entry.getKey();
//            }
//        }
//        return null;
//    }

    public Object getAsObject(FacesContext facesContext, UIComponent component,
    	      String submittedValue) {
    	    if (ObjetoUtil.isBlank(submittedValue) || "".equals(submittedValue.trim())
    	        || "null".equals(submittedValue)) {
    	      return null;
    	    } else {
    	    Cliente cliente = new Cliente();
    	    cliente.setId(Long.parseLong(submittedValue));
    	      return cliente;

    	    }
    	  }

    	  public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	    if (value == null || "".equals(value)) {
    	      return "";
    	    } else {
    	      return String.valueOf(((Cliente) value).getId());
    	    }
    	  }
}
