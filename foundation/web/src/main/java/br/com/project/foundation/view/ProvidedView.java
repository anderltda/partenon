package br.com.project.foundation.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.project.commons.service.CommonsService;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.dto.SelectItemDTO;
import br.com.project.foundation.service.ServiceLocator;
import br.com.project.foundation.util.CompositeObjectHelper;


/**
 * @author anderson.nascimento
 *
 * @param <T>
 */
@ManagedBean
@SessionScoped
public class ProvidedView<T extends BaseObject> extends SearchView<T> {	

	private static final long serialVersionUID = 1L;
	
	public static final Map<String, String> INSCRICOES_ESTADUAIS = new HashMap<String, String>();

	protected String maskInsEstadual = "999.999.999.999";
	
	protected Set<Map<String, String>> abas = new LinkedHashSet<Map<String, String>>();

	private List<SelectItemDTO> estados;
	private List<SelectItemDTO> combustiveis;
	private List<SelectItemDTO> categoriasFiliais;
	private List<SelectItemDTO> categoriasVeiculos;
	
	static {	
		
		INSCRICOES_ESTADUAIS.put("RS", "999-9999999");
		INSCRICOES_ESTADUAIS.put("SC", "999.999.999");
		INSCRICOES_ESTADUAIS.put("PR", "99999999-99");
		INSCRICOES_ESTADUAIS.put("SP", "999.999.999.999");
		INSCRICOES_ESTADUAIS.put("MG", "999.999.999/9999");
		INSCRICOES_ESTADUAIS.put("RJ", "99.999.99-9");
		INSCRICOES_ESTADUAIS.put("ES", "999.999.99-9");
		INSCRICOES_ESTADUAIS.put("BA", "999.999.99-9");
		INSCRICOES_ESTADUAIS.put("SE", "999999999-9");
		INSCRICOES_ESTADUAIS.put("AL", "999999999");
		INSCRICOES_ESTADUAIS.put("PE", "99.9.999.9999999-9");
		INSCRICOES_ESTADUAIS.put("PB", "99999999-9");
		INSCRICOES_ESTADUAIS.put("RN", "99.999.999-9");
		INSCRICOES_ESTADUAIS.put("PI", "999999999");
		INSCRICOES_ESTADUAIS.put("MA", "999999999");
		INSCRICOES_ESTADUAIS.put("CE", "99999999-9");
		INSCRICOES_ESTADUAIS.put("GO", "99.999.999-9");
		INSCRICOES_ESTADUAIS.put("TO", "99999999999");
		INSCRICOES_ESTADUAIS.put("MT", "999999999");
		INSCRICOES_ESTADUAIS.put("MS", "999999999");
		INSCRICOES_ESTADUAIS.put("DF", "99999999999-99");
		INSCRICOES_ESTADUAIS.put("AM", "99.999.999-9");
		INSCRICOES_ESTADUAIS.put("AC", "99.999.999/999-99");
		INSCRICOES_ESTADUAIS.put("PA", "99-999999-9");
		INSCRICOES_ESTADUAIS.put("RO", "999.99999-9");
		INSCRICOES_ESTADUAIS.put("RR", "99999999-9");
		INSCRICOES_ESTADUAIS.put("AP", "999999999");
		
	}
	
	@ManagedProperty(value = "#{" + CommonsService.SERVICE + "}")
	protected CommonsService commomService;
	
	public CompositeObjectHelper getCompositeObjectHelper() {
		return new CompositeObjectHelper();
	}

	protected CommonsService getService() {
		return commomService;
	}

	protected CommonsService getService(String name) {
		return (CommonsService) ServiceLocator.getInstance().getBean(name);
	}

	public void setCommomService(CommonsService commomService) {
		this.commomService = commomService;
	}
	
	public String getMaskInsEstadual() {
		return maskInsEstadual;
	}	
	
	public List<SelectItemDTO> getEstados() {
		estados = new ArrayList<SelectItemDTO>();
		
		estados.add(new SelectItemDTO("AC", "Acre - AC"));
		estados.add(new SelectItemDTO("AL", "Alagoas - AL"));	
		estados.add(new SelectItemDTO("AP", "Amapá - AP"));
		estados.add(new SelectItemDTO("AM", "Amazonas - AM"));	
		estados.add(new SelectItemDTO("BA", "Bahia - BA"));
		estados.add(new SelectItemDTO("CE", "Ceará - CE"));	
		estados.add(new SelectItemDTO("DF", "Distrito Federal - DF"));
		estados.add(new SelectItemDTO("ES", "Espírito Santo - ES"));	
		estados.add(new SelectItemDTO("GO", "Goiás - GO"));
		estados.add(new SelectItemDTO("MA", "Maranhão - MA"));	
		estados.add(new SelectItemDTO("MT", "Mato Grosso - MT"));
		estados.add(new SelectItemDTO("MS", "Mato Grosso do Sul - MS"));	
		estados.add(new SelectItemDTO("MG", "Minas Gerais - MG"));
		estados.add(new SelectItemDTO("PA", "Pará - PA"));
		estados.add(new SelectItemDTO("PB", "Paraíba - PB"));	
		estados.add(new SelectItemDTO("PR", "Paraná - PR"));	
		estados.add(new SelectItemDTO("PE", "Pernambuco - PE"));	
		estados.add(new SelectItemDTO("PI", "Piauí - PI"));	
		estados.add(new SelectItemDTO("RJ", "Rio de Janeiro - RJ"));	
		estados.add(new SelectItemDTO("RN", "Rio Grande do Norte - RN"));	
		estados.add(new SelectItemDTO("RS", "Rio Grande do Sul - RS"));	
		estados.add(new SelectItemDTO("RO", "Rondônia - RO"));	
		estados.add(new SelectItemDTO("RR", "Roraima - RR"));	
		estados.add(new SelectItemDTO("SC", "Santa Catarina - SC"));	
		estados.add(new SelectItemDTO("SP", "São Paulo - SP"));	
		estados.add(new SelectItemDTO("SE", "Sergipe - SE"));	
		estados.add(new SelectItemDTO("TO", "Tocantins - TO"));
		
		return estados;
	}

	public List<SelectItemDTO> getCategoriasFiliais() {
		categoriasFiliais = new ArrayList<SelectItemDTO>();
		categoriasFiliais.add(new SelectItemDTO("M", "MATRIZ"));
		categoriasFiliais.add(new SelectItemDTO("F", "FILIAL"));	
		categoriasFiliais.add(new SelectItemDTO("P", "PARCEIRO"));
		categoriasFiliais.add(new SelectItemDTO("V", "VIRTUAL"));	
		return categoriasFiliais;
	}

	public List<SelectItemDTO> getCombustiveis() {
		combustiveis = new ArrayList<SelectItemDTO>();
		combustiveis.add(new SelectItemDTO("D", "DIESEL"));
		combustiveis.add(new SelectItemDTO("E", "ETANOL"));	
		combustiveis.add(new SelectItemDTO("G", "GASOLINA"));
		combustiveis.add(new SelectItemDTO("GN", "GÁS NATURAL"));
		combustiveis.add(new SelectItemDTO("F", "FLEX"));
		return combustiveis;
	}

	public List<SelectItemDTO> getCategoriasVeiculos() {
		categoriasVeiculos = new ArrayList<SelectItemDTO>();
		categoriasVeiculos.add(new SelectItemDTO("P", "PRÓPRIO"));
		categoriasVeiculos.add(new SelectItemDTO("T", "TERCEIRO"));	
		categoriasVeiculos.add(new SelectItemDTO("A", "AGREGADO"));
		return categoriasVeiculos;
	}

	public Set<Map<String, String>> getAbas() {
		return abas;
	}

	public void addAbas(Map<String, String> aba) {
		abas.add(aba);
	}
}