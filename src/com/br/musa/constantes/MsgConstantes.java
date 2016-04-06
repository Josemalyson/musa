package com.br.musa.constantes;

public abstract class MsgConstantes {

	/* MENSSAGEM DA CLASSES DE NEGOCIO */

	public static final String MSG_SUCESSO = "Operação realizada com sucesso.";
	public static final String MSG_ERRO = "Não foi possível realizar a operação.";
	public static final String MSG_ALTERACAO_SUCESSO = "Alteração realizada com sucesso.";
	public static final String CPF_INVALIDO = "CPF inválido";
	public static final String MSG_EXCLUSAO_SUCESSO = "Exclusão realizada com sucesso.";
	public static final String ERRO_QUANTIDADE_ZERO = "O valor do campor quantidade não pode ter valor zero. (0)";
	public static final String NAO_HA_PEDIDOS_PARA_CLIENTE = "Não existe pedidos cadastrados para esse cliente.";
	public static final String NAO_EXISTE_PRODUTOS_CADASTRADOS = "Não é possível efetuar um pedido sem produtos cadastrados, por favor efetue o cadastrao de pelo menos um produto.";
	public static final String PEDIDO_COM_VALOR_ZER0 = "Não se pode aplicar desconto a pedido com valor total zero";
	public static final String DESCONTO_JA_FOI_APLICADO = "Esse pedido já possui um valor de desconto.";

	/* MENSSAGEM DA CLASSE CALCULADORA */
	public static final String VALOR_ZERO_NAO_PODE_SER_DESCONTADO = "Total com valor zero (0) não pode ser calculado.";
	public static final String NAO_PODE_EXCLUIR_PEDIDO_NAO_PAGO = "Você não pode excluir um pedido com status Não Pago.";
	public static final String VALOR_PAGO_DIFERENTE_ZERO = "Valor do pagamento não pode ter valor zero (0)";
	public static final String PAGAMENTO_MAIOR_QUE_VALOR_RESTANTE = "Você não pode aplicar um pagamento maior que o valor restante do Pedido.";
	public static final String PAGAMENTO_COM_VALOR_IGUAL_OU_MENOR_QUE_ZERO = "Você não pode aplicar um valor igual ou menor que zero (0) ao pagamento.";
	public static final String PAGAMENTO_MAIOR_QUE_VALOR_TOTAL = "Você não pode aplicar um pagamento maior que o valor Total do Pedido.";
	public static final String NAO_HA_PEDIDOS_PESQUISADOS = "Não existe pagamentos para o filtro aplicado.";
	public static final String USUARIO_OU_SENHA_INVALIDO = "Usu�rio ou senha inv�lidos";

	private MsgConstantes() {
	}
}
