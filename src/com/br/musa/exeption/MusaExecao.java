package com.br.musa.exeption;

import java.util.ArrayList;
import java.util.List;

import com.br.musa.constantes.Constantes;

public class MusaExecao extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final List<String> mensagemList;
	private final String message;

	public MusaExecao() {
		super();
		this.mensagemList = new ArrayList<String>();
		this.message = new String();
		
	}
	
	public MusaExecao(String mensagem){
		this.message = mensagem;
		this.mensagemList = new ArrayList<String>();
	}
	
	public MusaExecao(List<String> mensagemList) {
		StringBuilder sb = new StringBuilder();
		for (String msg : mensagemList) {
			sb.append(msg+Constantes.PONTO_E_VIRGULA+Constantes.ESPACO_EM_BRANCO);
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
