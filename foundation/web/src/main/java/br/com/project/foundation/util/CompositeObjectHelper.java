package br.com.project.foundation.util;

import br.com.project.commons.cadastro.entity.Endereco;
import br.com.project.commons.cadastro.entity.EnderecoDominio;
import br.com.project.foundation.Constants;

/**
 * Classe responsavel por ajudar a montar um objeto relacionado um com o outro. 
 * Isso muito utilizado para não ocorre NullPointerException
 * 
 * @author anderson.nascimento
 *
 */
public class CompositeObjectHelper {

	/**
	 * @param enderecoDominio - Classe a entity
	 * @param endereco - Classe com os dados do endereço encontrado
	 */
	public void createEnderecoDominio(EnderecoDominio enderecoDominio, Endereco endereco) {
		if(endereco != null) {
			enderecoDominio.setCep(endereco.getCep());
			enderecoDominio.setLogradouro(endereco.getLogradouro());
			enderecoDominio.setBairro(endereco.getBairro());
			enderecoDominio.setCidade(endereco.getCidade());
			enderecoDominio.setUf(endereco.getUf());	
			enderecoDominio.setPais(Constants.ENDERECO_PAIS_PADRAO);		
		}
	}
}
