package br.com.project.commons.seguranca.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.commons.seguranca.service.SegurancaCommonService;
import br.com.project.commons.service.impl.CommonsServiceImpl;

/**
 * @author anderson.nascimento
 *
 */
@Transactional
@Service(value = SegurancaCommonService.SERVICE)
public class SegurancaCommonServiceImpl extends CommonsServiceImpl implements SegurancaCommonService {
		
	private static final Log LOG = LogFactory.getLog(SegurancaCommonServiceImpl.class);	

}