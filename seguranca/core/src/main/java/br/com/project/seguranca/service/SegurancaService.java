
package br.com.project.seguranca.service;

import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.commons.service.CommonsService;
import br.com.project.foundation.exception.ServiceException;


/**
 * @author anderson.nascimento
 *
 */
public interface SegurancaService extends CommonsService {
	
	public static final String SERVICE = "segurancaService";

	/**
	 * Método responsável por efetuar o login no sistema
	 * @param login - Username do usuario
	 * @param password - Senha do usuario
	 * @return - Usuário
	 */
	public Usuario login(String login, String password) throws ServiceException;
		
	/**
	 * @param login
	 * @param id
	 * @throws ServiceException
	 */
	public void validarLogin(String login, Long id) throws ServiceException;

	/**
	 * @param usuario
	 * @throws ServiceException
	 */
	public void salvarUsuario(Usuario usuario) throws ServiceException;

	/**
	 * @param usuario
	 * @throws ServiceException
	 */
	public void trocarSenhaUsuario(Usuario usuario) throws ServiceException;
	
	/**
	 * @param email
	 * @throws ServiceException
	 */
	public void esqueciSenha(String email) throws ServiceException;
}