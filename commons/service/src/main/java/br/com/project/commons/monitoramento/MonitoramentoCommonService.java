package br.com.project.commons.monitoramento;

import java.util.List;

import br.com.project.commons.monitoramento.entity.PosicaoVeiculo;
import br.com.project.commons.service.CommonsService;
import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 *
 */
public interface MonitoramentoCommonService extends CommonsService {
	
	public static final String SERVICE = "monitoramentoCommonService";
	
	
	/**
	 * Método responsável por buscar as posições dos veiculos
	 * @param posicaoVeiculo
	 * @param ultimaLocalizacao
	 * @return
	 * @throws ServiceException
	 */
	public List<PosicaoVeiculo> buscarPosicoesVeiculos(PosicaoVeiculo posicaoVeiculo, boolean ultimaLocalizacao) throws ServiceException;
	
}