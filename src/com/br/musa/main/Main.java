package com.br.musa.main;

import org.apache.shiro.authc.credential.DefaultPasswordService;

public class Main {
	public static void main(String[] args) {

		DefaultPasswordService t = new DefaultPasswordService();
		t.encryptPassword("1234");
		System.out.println(t.encryptPassword("1234"));
	}

}
