package br.com.project.seguranca.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.commons.service.impl.CommonsServiceImpl;
import br.com.project.commons.util.CommonsMail;
import br.com.project.commons.util.HashSecurity;
import br.com.project.foundation.Constants;
import br.com.project.foundation.exception.ServiceException;
import br.com.project.seguranca.service.SegurancaService;

/**
 * @author anderson.nascimento
 *
 */
@Transactional
@Service(value = SegurancaService.SERVICE)
public class SegurancaServiceImpl extends CommonsServiceImpl implements SegurancaService {

	private static final Log LOG = LogFactory.getLog(SegurancaServiceImpl.class);
	
	/**
	 * Método responsável por efetuar o login no sistema
	 * @param login - Username do usuario
	 * @param password - Senha do usuario
	 * @return - Usuário
	 */
	@Override
	public Usuario login(String login, String password) throws ServiceException {
		
		Usuario usuario = null;
		
		try {

			CriteriaBuilder build = em.getCriteriaBuilder();
			
			CriteriaQuery<Usuario> criteria = build.createQuery(Usuario.class);
			
			Root<Usuario> usuarioRoot = criteria.from(Usuario.class);
			
			ParameterExpression<String> loginParam = build.parameter(String.class);
			
			ParameterExpression<String> passwordParam = build.parameter(String.class);
			
			criteria.select(usuarioRoot).where(
					build.equal(usuarioRoot.get("login"), loginParam),
					build.equal(usuarioRoot.get("password"), passwordParam));
			
			TypedQuery<Usuario> query = em.createQuery(criteria);
			
			query.setParameter(loginParam, login);
			
			query.setParameter(passwordParam, HashSecurity.criptografar(password));
			
			usuario = (Usuario) query.getSingleResult();
			
			if(usuario == null) {
				throw new ServiceException("Não foi possivel fazer login no sistema, possivelmente seu usuário ou senha estão incorretos. " +
						"Caso tenha esquecido sua senha entre em esqueci minha senha e o sistema irá enviar uma nova para você.");
			}
						 
			if(usuario.getAtivo().equals(Constants.NAO)) {
				throw new ServiceException("Não foi possivel fazer login no sistema com esse usuário, porque está inativo! " +
						"Por favor entre em contato com o Administrador do Sistema");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.debug(ex.getLocalizedMessage());
			throw new ServiceException(ex.getMessage());
		}

		return usuario;
	}
	
	/**
	 * @param login
	 * @param id
	 * @throws ServiceException
	 */
	@Override
	public void validarLogin(String login, Long id) throws ServiceException {


		try {

			Usuario usuario = null;

			CriteriaBuilder build = em.getCriteriaBuilder();

			CriteriaQuery<Usuario> criteria = build.createQuery(Usuario.class);

			Root<Usuario> usuarioRoot = criteria.from(Usuario.class);

			ParameterExpression<String> loginParam = build.parameter(String.class);

			criteria.select(usuarioRoot).where(build.equal(usuarioRoot.get("login"), loginParam));

			TypedQuery<Usuario> query = em.createQuery(criteria);

			query.setParameter(loginParam, login);

			usuario = (Usuario) query.getSingleResult();

			if(usuario != null && usuario.getId() != id) {
				throw new ServiceException("Nao possivel utilizar esse login, devido a existencia ha um usuario do sistema.");
			}

		} catch (NoResultException ex) {
			ex.printStackTrace();
			LOG.debug(ex.getLocalizedMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.debug(ex.getLocalizedMessage());
			throw new ServiceException(ex.getMessage());
		}
	}
		
	/**
	 * @param usuario
	 * @throws ServiceException
	 */
	@Override
	public void salvarUsuario(Usuario usuario) throws ServiceException {
		
		try {

			validarLogin(usuario.getLogin(), usuario.getId());
			
			if(usuario.getId() == null) {
				gerarSenhaUsuario(usuario, "Segue a senha para o acesso -");
			}
			
			save(usuario);		
			
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.debug(ex.getLocalizedMessage());
			throw new ServiceException(ex.getMessage());
		}

	}
	
	/**
	 * @param usuario
	 */
	private void gerarSenhaUsuario(Usuario usuario, String message) throws Exception {		
		
		List<String> emails = new ArrayList<String>();
		
		emails.add(usuario.getEmail());
		
		usuario.setPassword(HashSecurity.geradorNumerosAleatorios(1970062199));			
		
		CommonsMail.enviaEmailFormatoHtml(emails, "Solicitação de Senha", message + " Senha: "+ usuario.getPassword(), null, null);
		
		String password = HashSecurity.criptografar(usuario.getPassword());
		
		usuario.setPassword(password);		
	}

	/**
	 * @param usuario
	 * @throws ServiceException
	 */
	@Override
	public void trocarSenhaUsuario(Usuario usuario) throws ServiceException {

		try {
			
			String password = HashSecurity.criptografar(usuario.getPassword());
			usuario.setPassword(password);
			save(usuario);		
			
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.debug(ex.getLocalizedMessage());
			throw new ServiceException(ex.getMessage());
		}		
	}

	/**
	 * @param email
	 * @throws ServiceException
	 */
	@Override
	public void esqueciSenha(String email) throws ServiceException {
		
		try {

			Usuario usuario = null;

			CriteriaBuilder build = em.getCriteriaBuilder();

			CriteriaQuery<Usuario> criteria = build.createQuery(Usuario.class);

			Root<Usuario> usuarioRoot = criteria.from(Usuario.class);

			ParameterExpression<String> emailParam = build.parameter(String.class);

			criteria.select(usuarioRoot).where(build.equal(usuarioRoot.get("email"), emailParam));

			TypedQuery<Usuario> query = em.createQuery(criteria);

			query.setParameter(emailParam, email);

			usuario = (Usuario) query.getSingleResult();

			if(usuario != null) {				
				gerarSenhaUsuario(usuario, "Sua senha foi resetada, estamos enviamos uma nova senha para você. <br>");
			}

		} catch (NoResultException ex) {
			ex.printStackTrace();
			LOG.debug(ex.getLocalizedMessage());
			throw new ServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.debug(ex.getLocalizedMessage());
			throw new ServiceException(ex.getMessage());
		} 
		
	}	
	
}