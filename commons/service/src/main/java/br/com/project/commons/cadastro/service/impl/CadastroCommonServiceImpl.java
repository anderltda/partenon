package br.com.project.commons.cadastro.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.commons.cadastro.entity.Agencia;
import br.com.project.commons.cadastro.entity.Banco;
import br.com.project.commons.cadastro.entity.ContaBancaria;
import br.com.project.commons.cadastro.service.CadastroCommonService;
import br.com.project.commons.service.impl.CommonsServiceImpl;
import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 *
 */
@Transactional
@Service(value = CadastroCommonService.SERVICE)
public class CadastroCommonServiceImpl extends CommonsServiceImpl implements CadastroCommonService {

	private static final Log LOG = LogFactory.getLog(CadastroCommonServiceImpl.class);

	/**
	 * Método responsável por trazer a conta bancaria informada pelo id
	 * @param idContaBancaria - Id da conta bancaria
	 * @return ContaBancaria
	 * @throws ServiceException
	 */
	@Override
	public ContaBancaria buscarContaBancaria(Long idContaBancaria) throws ServiceException {		

		ContaBancaria contaBancaria = null;
		
		try {
			
			if(idContaBancaria != null) {
				contaBancaria = getByID(ContaBancaria.class, idContaBancaria);
				Agencia agencia = getByID(Agencia.class, contaBancaria.getAgencia().getId());
				Banco banco = getByID(Banco.class, agencia.getBanco().getId());
				agencia.setBanco(banco);			
				contaBancaria.setAgencia(agencia);
			}

		} catch (ServiceException ex) {
			LOG.error(ex);
			throw new ServiceException(ex);
		}

		return contaBancaria;
	}

}