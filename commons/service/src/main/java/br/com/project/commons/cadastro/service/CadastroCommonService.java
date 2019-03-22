package br.com.project.commons.cadastro.service;

import br.com.project.commons.cadastro.entity.ContaBancaria;
import br.com.project.commons.service.CommonsService;
import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 *
 */
public interface CadastroCommonService extends CommonsService {
	
	public static final String SERVICE = "cadastroCommonService";
	
	
	/**
	 * Método responsável por trazer a conta bancaria informada pelo id
	 * @param idContaBancaria - Id da conta bancaria
	 * @return ContaBancaria
	 * @throws ServiceException
	 */
	public ContaBancaria buscarContaBancaria(Long idContaBancaria) throws ServiceException; 
	
}