package com.br.musa.enums;

public enum EstadoEnum {
		ACRE(1L,"Acre","AC"),
		ALAGOAS(2L,"Alagoas","AL"),
		AMAPA(3L,"Amapá","AP"),
		AMAZONAS(4L,"Amazonas","AM"),
		BAHIA(5L,"Bahia","BA"),
		CEARA(6L,"Ceara","CE"),
		DISTRITO_FEDERAL(7L,"Distrito Federal","DF"),
		ESPIRITO_SANTO(8L,"Espirito Santo","ES"),
		RORAIMA(9L,"Roraima","RR"),
		GOIAS(10L,"Goias","GO"),
		MARANHAO(11L,"Maranhão","MA"),
		MATO_GROSSO(12L,"Matogrosso","MT"),
		MATO_GROSSO_DO_SUL(13L,"Matogrosso do Sul","MS"),
		MINAS_GERAIS(14L,"Minas Gerais","MG"),
		PARA(15L,"Para","PA"),
		PARAIBA(16L,"Paraiba","PB"),
		PARANA(17L,"Parana","PR"),
		PERNAMBUCO(18L,"Pernambuco","PE"),
		PIAUI(19L,"Piaui","PI"),
		RIO_DE_JANEIRO(20L,"Rio de Janeiro","RJ"),
		RIO_GRANDE_DO_NORTE(21L,"Rio Grande do Norte","RN"),
		RIO_GRANDE_DO_SUL(22L,"Rio Grande do Sul","RS"),
		RONDONIA(23L,"Rondonia","RO"),
		TOCANTINS(24L,"Tocantins","TO"),
		SANTA_CATARINA(25L,"Santa Catarina","SC"),
		SAO_PAULO(26L,"Sao Paulo","SP"),
		SERGIPE(27L,"Sergipe","SE");

		private Long codigo;
		private String descricao;
		private String sigla;
		
		EstadoEnum(Long codigo, String descricao, String sigla) {
			this.codigo = codigo;
			this.descricao = descricao;
			this.sigla = sigla;
		}
		
		public Long getCodigo() {
			return codigo;
		}

		public String getDescricao() {
			return descricao;
		}
		public String getSigla() {
			return sigla;
		}
		
}