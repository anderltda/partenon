package br.com.project.monitoramento.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.commons.service.impl.CommonsServiceImpl;
import br.com.project.monitoramento.service.MonitoramentoService;


/**
 * @author anderson.nascimento
 *
 */
@Transactional
@Service(value = MonitoramentoService.SERVICE)
public class MonitoramentoServiceImpl extends CommonsServiceImpl implements MonitoramentoService {

	private static final Log LOG = LogFactory.getLog(MonitoramentoServiceImpl.class);
}
