package br.com.project.commons.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.commons.service.CommonsService;
import br.com.project.foundation.service.impl.PersistenceServiceImpl;

/**
 * @author anderson.nascimento
 *
 */
@Transactional
@Service(value = CommonsService.SERVICE)
public class CommonsServiceImpl extends PersistenceServiceImpl implements CommonsService {

	private static final Log LOG = LogFactory.getLog(CommonsServiceImpl.class);

}