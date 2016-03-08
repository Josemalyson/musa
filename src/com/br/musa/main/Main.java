package com.br.musa.main;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<BigDecimal> produtoList = Arrays.asList(new BigDecimal(100),new BigDecimal(200));
		System.out.println(produtoList.stream().mapToDouble(c -> c.longValueExact()).sum());
		
	}

}
