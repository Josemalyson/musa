package com.br.musa.controlador;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.jboss.logging.Logger;

import com.br.musa.constantes.MsgConstantes;

@ManagedBean
public class LoginController extends CoreControlador{

	private String username;
	private String password;
	private Subject currentUser;

	JdbcRealm realm = new JdbcRealm();
    DefaultSecurityManager sm = new DefaultSecurityManager(realm);
    
    private static final long serialVersionUID = 7412780138266810968L;
    private static final Logger logger = Logger.getLogger(LoginController.class);

	@PostConstruct
	public void init() {
		username = new String();
		password = new String();
		currentUser = SecurityUtils.getSubject();
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
			
		} catch ( UnknownAccountException uae ) { 
			 logger.info(uae.getMessage(), uae);
				adicionarErro(MsgConstantes.USUARIO_OU_SENHA_INVALIDO);
		} catch ( IncorrectCredentialsException ice ) {
			 logger.info(ice.getMessage(), ice);
			 adicionarErro(MsgConstantes.USUARIO_OU_SENHA_INVALIDO);
		} catch ( LockedAccountException lae ) {
			 logger.info(lae.getMessage(), lae);
			 adicionarErro(MsgConstantes.USUARIO_OU_SENHA_INVALIDO);
		} catch ( ExcessiveAttemptsException eae ) {
			 logger.info(eae.getMessage(), eae);
			 adicionarErro(MsgConstantes.USUARIO_OU_SENHA_INVALIDO);
		} catch ( AuthenticationException ae ) {
		    logger.info(ae.getMessage(), ae);
		    adicionarErro(MsgConstantes.USUARIO_OU_SENHA_INVALIDO);
		}
		return retorno;
	}
	
	public String logout(){
		SecurityUtils.getSubject().logout();
        return "/login.jsf?faces-redirect=true";
	}

	public String nomeUsuarioLogado(){
		return " Bem vindo (a), "+ currentUser.getPrincipal().toString().toUpperCase();
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
