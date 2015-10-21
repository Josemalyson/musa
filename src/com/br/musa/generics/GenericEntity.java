package com.br.musa.generics;

import java.io.Serializable;

public abstract class GenericEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  public abstract Serializable obterIdentificador();
}