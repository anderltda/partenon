package br.com.project.cadastro.service;

import br.com.project.commons.cadastro.entity.Endereco;
import br.com.project.commons.cadastro.entity.Filial;
import br.com.project.commons.cadastro.entity.Motorista;
import br.com.project.commons.cadastro.entity.Veiculo;
import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.commons.service.CommonsService;
import br.com.project.foundation.exception.ServiceException;


/**
 * @author anderson.nascimento
 *
 */
public interface CadastroService extends CommonsService {
	
	public static final String SERVICE = "cadastroService";
		
	/**
	 * Método responsável por validar o login do usuario
	 * @param login - Instance a ser procurado
	 * @param id - Instance a ser verificado se é o proprio objeto existente
	 * @throws ServiceException
	 */
	public void validarLogin(String login, Long id) throws ServiceException;

	/**
	 * Método responsável por salvar o usuario
	 * @param usuario - Instance a ser persistido
	 * @throws ServiceException
	 */
	public void salvarUsuario(Usuario usuario) throws ServiceException;
	
	/**
	 * Método responsável por salvar a Filial/Unidade
	 * @param filial - Instance a ser persistido
	 * @throws ServiceException
	 */
	public void salvarFilial(Filial filial) throws ServiceException;	

	/**
	 * Método responsável por salvar Proprietário/Motorista/Ajudante
	 * @param motorista - Instance a ser persistido
	 * @throws ServiceException
	 */
	public void salvarMotorista(Motorista motorista) throws ServiceException;
	
	/**
	 * Método responsável por salvar Veiculo
	 * @param veiculo - Instance a ser persistido
	 * @throws ServiceException
	 */
	public void salvarVeiculo(Veiculo veiculo) throws ServiceException;	
		
	/**
	 * Método responsável por buscar um endereço pelo cep.
	 * @param cep - Codigo Postal
	 * @return - Retorna o objeto endereco
	 * @throws ServiceException
	 */
	public Endereco buscaEndereco(String cep) throws ServiceException; 
}
