package com.br.musa.exeption;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ExceptionHandlerCustomFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory exceptionHandlerFactory;

	public ExceptionHandlerCustomFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
		this.exceptionHandlerFactory = exceptionHandlerFactory;
	}

	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler result = this.exceptionHandlerFactory.getExceptionHandler();
		result = new ExceptionHandlerCustom(result);
		return result;
	}

}
