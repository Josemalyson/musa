package com.br.musa.exeption;

import java.util.ArrayList;
import java.util.List;

import com.br.musa.constantes.MsgConstantes;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final List<String> mensagemList;
	private final String message;

	public BusinessException() {
		super();
		this.mensagemList = new ArrayList<String>();
		this.message = new String();
		
	}
	
	public BusinessException(String mensagem){
		this.message = mensagem;
		this.mensagemList = new ArrayList<String>();
	}
	
	public BusinessException(List<String> mensagemList) {
		StringBuilder sb = new StringBuilder();
		for (String msg : mensagemList) {
			sb.append(msg+MsgConstantes.PONTO_E_VIRGULA+MsgConstantes.ESPACO_EM_BRANCO);
		}
		this.mensagemList = mensagemList;
		this.message = sb.toString();
	}
	
	public List<String> getMensagemList() {
		return mensagemList;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
