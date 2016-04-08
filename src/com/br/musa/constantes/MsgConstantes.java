package com.br.musa.constantes;

public abstract class MsgConstantes {

	/* MENSSAGEM DA CLASSES DE NEGOCIO */

	public static final String MSG_SUCESSO = "Opera��o realizada com sucesso.";
	public static final String MSG_ERRO = "N�o foi poss�vel realizar a opera��o.";
	public static final String MSG_ALTERACAO_SUCESSO = "Altera��o realizada com sucesso.";
	public static final String CPF_INVALIDO = "CPF inv�lido";
	public static final String MSG_EXCLUSAO_SUCESSO = "Exclu��o realizada com sucesso.";
	public static final String ERRO_QUANTIDADE_ZERO = "O valor do campor quantidade n�o pode ter valor zero. (0)";
	public static final String NAO_HA_PEDIDOS_PARA_CLIENTE = "N�o existe pedidos cadastrados para esse cliente.";
	public static final String NAO_EXISTE_PRODUTOS_CADASTRADOS = "N�o � poss�vel efetuar um pedido sem produtos cadastrados, por favor efetue o cadastrao de pelo menos um produto.";
	public static final String PEDIDO_COM_VALOR_ZER0 = "N�o se pode aplicar desconto a pedido com valor total zero";
	public static final String DESCONTO_JA_FOI_APLICADO = "Esse pedido j� possui um valor de desconto.";

	/* MENSSAGEM DA CLASSE CALCULADORA */
	public static final String VALOR_ZERO_NAO_PODE_SER_DESCONTADO = "Total com valor zero (0) n�o pode ser calculado.";
	public static final String NAO_PODE_EXCLUIR_PEDIDO_NAO_PAGO = "Voc� n�o pode excluir um pedido com status N�o Pago.";
	public static final String VALOR_PAGO_DIFERENTE_ZERO = "Valor do pagamento n�o pode ter valor zero (0)";
	public static final String PAGAMENTO_MAIOR_QUE_VALOR_RESTANTE = "Voc� n�o pode aplicar um pagamento maior que o valor restante do Pedido.";
	public static final String PAGAMENTO_COM_VALOR_IGUAL_OU_MENOR_QUE_ZERO = "Voc� n�o pode aplicar um valor igual ou menor que zero (0) ao pagamento.";
	public static final String PAGAMENTO_MAIOR_QUE_VALOR_TOTAL = "Voc� n�o pode aplicar um pagamento maior que o valor Total do Pedido.";
	public static final String NAO_HA_PEDIDOS_PESQUISADOS = "N�o existe pagamentos para o filtro aplicado.";
	public static final String USUARIO_OU_SENHA_INVALIDO = "Usu�rio ou senha inv�lidos";
	public static final String LOGIN_DUPLICADO = "Campo login n�o d�sponovel, por favor tente outro nome.";
	public static final String ERRO_NO_PROCESSAMENTO = "Erro no processamento da requisi��o, tente novamente!";

	private MsgConstantes() {
	}
}
