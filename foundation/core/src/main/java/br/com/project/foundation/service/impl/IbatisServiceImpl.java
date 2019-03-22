package br.com.project.foundation.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibatis.sqlmap.client.SqlMapClient;

import br.com.project.foundation.exception.ServiceException;
import br.com.project.foundation.service.IbatisService;

/**
 * @author anderson.nascimento
 *
 */
public class IbatisServiceImpl implements IbatisService, Serializable {	

	private static final long serialVersionUID = 1L;

	@Autowired
	protected SqlMapClient sqlMapClient;
	
	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}
	
	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	/* (non-Javadoc)
	 * @see br.com.project.foundation.service.PersistenceService#executeQuery(java.lang.String, java.lang.Object, int, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> executeQuery(String queryName, T instance,  int firstResult, int maxResult) throws ServiceException {

		List<T> t = null;
		
		try {
			
			t = getSqlMapClient().queryForList(queryName, instance, firstResult, maxResult);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceException(ex);
		}
		
		return t;
	}

	/* (non-Javadoc)
	 * @see br.com.project.foundation.service.PersistenceService#executeQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> executeQuery(String queryName, T instance) throws ServiceException {

		List<T> t = null;
		
		try {
			
			t = getSqlMapClient().queryForList(queryName, instance);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceException(ex);
		}
		
		return t;
		
	}	
}
