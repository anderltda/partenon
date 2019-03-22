package br.com.project.cadastro.service.impl;

import java.util.Date;
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

import br.com.project.cadastro.service.CadastroService;
import br.com.project.commons.cadastro.entity.Endereco;
import br.com.project.commons.cadastro.entity.Filial;
import br.com.project.commons.cadastro.entity.Motorista;
import br.com.project.commons.cadastro.entity.Veiculo;
import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.commons.service.impl.CommonsServiceImpl;
import br.com.project.commons.util.BuscaCepUtil;
import br.com.project.commons.util.HashSecurity;
import br.com.project.commons.util.SetUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.exception.ServiceException;


/**
 * @author anderson.nascimento
 *
 */
@Transactional
@Service(value = CadastroService.SERVICE)
public class CadastroServiceImpl extends CommonsServiceImpl implements CadastroService {

	private static final Log LOG = LogFactory.getLog(CadastroServiceImpl.class);

	/**
	 * Método responsável por validar o login do usuario
	 * @param login - Instance a ser procurado
	 * @param id - Instance a ser verificado se é o proprio objeto existente
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
	 * Método responsável por salvar o usuario
	 * @param usuario - Instance a ser persistido
	 * @throws ServiceException
	 */
	@Override
	public void salvarUsuario(Usuario usuario) throws ServiceException {

		validarLogin(usuario.getLogin(), usuario.getId());

		String password = HashSecurity.criptografar(usuario.getPassword());

		usuario.setPassword(password);

		save(usuario);		
	}
	
	/**
	 * Método responsável por salvar a Filial/Unidade
	 * @param filial - Instance a ser persistido
	 * @throws ServiceException
	 */
	@Override
	public void salvarFilial(Filial filial) throws ServiceException {
		
		if(StringUtil.isEmpty(filial.getCnpj()) && StringUtil.isDifferent(filial.getCategoria(), Filial.CATEGORIA_VIRTUAL)) {
			throw new ServiceException("Cnpj não pode ser vazio.");
		}
		
		if(StringUtil.isEmpty(filial.getInscricaoMunicipal()) && StringUtil.isDifferent(filial.getCategoria(), Filial.CATEGORIA_VIRTUAL)) {
			throw new ServiceException("Inscrição Municipal não pode ser vazio.");
		}
		
		if(StringUtil.isEmpty(filial.getInscricaoEstadual()) && StringUtil.isDifferent(filial.getCategoria(), Filial.CATEGORIA_VIRTUAL)) {
			throw new ServiceException("Inscrição Estadual não pode ser vazio.");
		}
		
		if(StringUtil.isEmpty(filial.getEndereco().getCep()) && StringUtil.isDifferent(filial.getCategoria(), Filial.CATEGORIA_VIRTUAL)) {
			throw new ServiceException("Cep não pode ser vazio.");
		}
		
		if(StringUtil.isEmpty(filial.getEndereco().getLogradouro()) && StringUtil.isDifferent(filial.getCategoria(), Filial.CATEGORIA_VIRTUAL)) {
			throw new ServiceException("Logradouro não pode ser vazio.");
		}
		
		if(filial.getEndereco().getNumero() == null && StringUtil.isDifferent(filial.getCategoria(), Filial.CATEGORIA_VIRTUAL)) {
			throw new ServiceException("Nº não pode ser vazio.");
		}
		
		if(StringUtil.isEmpty(filial.getEndereco().getBairro()) && StringUtil.isDifferent(filial.getCategoria(), Filial.CATEGORIA_VIRTUAL)) {
			throw new ServiceException("Bairro não pode ser vazio.");
		}
		
		if(StringUtil.isEmpty(filial.getEndereco().getCidade()) && StringUtil.isDifferent(filial.getCategoria(), Filial.CATEGORIA_VIRTUAL)) {
			throw new ServiceException("Cidade não pode ser vazio.");
		}
		
		if(StringUtil.isEmpty(filial.getEndereco().getUf()) && StringUtil.isDifferent(filial.getCategoria(), Filial.CATEGORIA_VIRTUAL)) {
			throw new ServiceException("Estado não pode ser vazio.");
		}

		filial.setSigla(filial.getSigla().toUpperCase());

		save(filial);
	}
	
	/**
	 * Método responsável por salvar Proprietário/Motorista/Ajudante
	 * @param motorista - Instance a ser persistido
	 * @throws ServiceException
	 */
	@Override
	public void salvarMotorista(Motorista motorista) throws ServiceException {
		
		if(StringUtil.isEmpty(motorista.getNumeroCnh()) && StringUtil.isDifferent(motorista.getCategoria(), Motorista.CATEGORIA_AJUDANTE)) {
			throw new ServiceException("Nº CNH não pode ser vazio.");
		}
		
		if(StringUtil.isNotEmpty(motorista.getNumeroCnh()) && StringUtil.isEmpty(motorista.getCategoriaCnh())) {
			throw new ServiceException("Categoria da CNH não pode ser vazio.");
		}
		
		if(StringUtil.isNotEmpty(motorista.getNumeroCnh()) && motorista.getDataCnhEmissao() == null) {
			throw new ServiceException("Emissão da CNH não pode ser vazio.");
		}
		
		if(StringUtil.isNotEmpty(motorista.getNumeroCnh()) && motorista.getDataCnhValidade() == null) {
			throw new ServiceException("Validade da CNH não pode ser vazio.");
		}
		
		if(StringUtil.isNotEmpty(motorista.getRg()) && motorista.getDataRgEmissao() == null) {
			throw new ServiceException("Data de Emissão do RG não pode ser vazio.");
		}
		
		if(StringUtil.isNotEmpty(motorista.getRg()) && StringUtil.isEmpty(motorista.getRgEmissor())) {
			throw new ServiceException("Emissor do RG não pode ser vazio.");
		}

		motorista.setDataAlteracao(new Date());		

		save(motorista);
		
	}
	
	/**
	 * Método responsável por salvar Veiculo
	 * @param veiculo - Instance a ser persistido
	 * @throws ServiceException
	 */
	public void salvarVeiculo(Veiculo veiculo) throws ServiceException {
		save(veiculo);
	}
	
	/**
	 * Método responsável por buscar um endereço pelo cep.
	 * @param cep - Codigo Postal
	 * @return - Retorna o objeto endereco
	 * @throws ServiceException
	 */
	@Override
	public Endereco buscaEndereco(String cep) throws ServiceException {
		
		String codigoPostal = cep.replaceAll("[^a-zA-Z0-9]+", "");
		
		Endereco endereco = new Endereco();
		
		if(StringUtil.isNotEmpty(codigoPostal)) {
			
			endereco = new Endereco(codigoPostal);
			
			List<Endereco> enderecos = selectQuery("cadastro.buscarEndereco", endereco);
			
			if(SetUtil.nonEmpty(enderecos)) {
				return enderecos.get(0);
			}
			
			try {
				
				endereco = BuscaCepUtil.buscaEndereco2(codigoPostal);
				
				if(endereco.isEnderecoCompleto()) {
					save(endereco);
					return endereco;
				}			
				
				endereco = BuscaCepUtil.buscaEndereco1(codigoPostal);
				
				if(endereco.isEnderecoCompleto()) {
					save(endereco);
					return endereco;
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
				LOG.debug(ex.getLocalizedMessage());
			}
			
		}
		
		return endereco;
	}
}
