package br.com.project.commons.monitoramento.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.commons.monitoramento.MonitoramentoCommonService;
import br.com.project.commons.monitoramento.entity.PosicaoVeiculo;
import br.com.project.commons.service.impl.CommonsServiceImpl;
import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 *
 */
@Transactional
@Service(value = MonitoramentoCommonService.SERVICE)
public class MonitoramentoCommonServiceImpl extends CommonsServiceImpl implements MonitoramentoCommonService {
		
	private static final Log LOG = LogFactory.getLog(MonitoramentoCommonServiceImpl.class);	

	/**
	 * Método responsável por buscar as posições dos veiculos
	 * @param posicaoVeiculo
	 * @param ultimaLocalizacao
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<PosicaoVeiculo> buscarPosicoesVeiculos(PosicaoVeiculo posicaoVeiculo, boolean ultimaLocalizacao) throws ServiceException {
		
		List<PosicaoVeiculo> veiculos = null;
		
		try {
			
			veiculos = (List<PosicaoVeiculo>) selectQuery("monitoramento.buscarPosicaoVeiculos", posicaoVeiculo);
			
			if(ultimaLocalizacao) {			
				
				Map<String, PosicaoVeiculo> maps = new HashMap<String, PosicaoVeiculo>();			
				
				for (PosicaoVeiculo veiculo : veiculos) {				
					if(maps.get(veiculo.getVeiculo().getId()) == null) {
						maps.put(veiculo.getVeiculo().getPlaca(), veiculo);					
					}				
				}
				
				veiculos.clear();
				
				for(Entry<String, PosicaoVeiculo> entry : maps.entrySet()) {
					veiculos.add(entry.getValue());
				}
			} 	
			
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error(ex.getLocalizedMessage(), ex.fillInStackTrace());
			throw new ServiceException(ex.getLocalizedMessage());
		}		
		
		return veiculos;
	}
}