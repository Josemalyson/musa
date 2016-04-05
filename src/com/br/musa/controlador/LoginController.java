package com.br.musa.controlador;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;

@ManagedBean
public class LoginController {

	private String username;
	private String password;
	JdbcRealm realm = new JdbcRealm();
    DefaultSecurityManager sm = new DefaultSecurityManager(realm);

	@PostConstruct
	public void init() {
		username = new String();
		password = new String();
	}

	public String login() {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		 SecurityUtils.setSecurityManager(sm);
		Subject currentUser = SecurityUtils.getSubject();
		String retorno = null;
		try {
			currentUser.login(token);
			
			if (currentUser.hasRole("ADMIN")) {
				retorno = "index.jsp?faces-redirect=true";
			}else {
				retorno = "404.jsf?faces-redirect=true";
			}
			
			return retorno; 
			
		} catch (AuthenticationException e) {
			System.out.println("Erro");
			e.printStackTrace();
			return retorno;
		}
	}
	
	public String logout(){
		SecurityUtils.getSubject().logout();
        return "/login.jsf?faces-redirect=true";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
}
