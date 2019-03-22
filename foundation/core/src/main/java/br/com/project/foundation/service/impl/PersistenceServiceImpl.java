package br.com.project.foundation.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.project.commons.util.BaseObject;
import br.com.project.foundation.exception.ServiceException;
import br.com.project.foundation.service.PersistenceService;


/**
 * @author anderson.nascimento
 *
 */
@Service(value = PersistenceService.SERVICE)
@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
public class PersistenceServiceImpl implements PersistenceService {
	
	private static final Log LOG = LogFactory.getLog(PersistenceServiceImpl.class);

	@PersistenceContext(unitName = "entityManagerFactory")
	protected EntityManager em;

	@Resource(name = "dataSource")
	protected DataSource defaultDS;
	
	@Autowired
	private SqlSession sqlSessionTemplate;

	@Override
	public EntityManager getEM() {
		return em;
	}

	@Override
	public <T> EntityManager getEM(Class<T> clazz) {
		return getEM();
	}

	public DataSource getDefaultDS() {
		return defaultDS;
	}

	public void setSqlSessionTemplate(final SqlSession sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	public SqlSession getSqlSessionTemplate() {
		return this.sqlSessionTemplate;
	}

	public void setDefaultDS(DataSource defaultDS) {
		this.defaultDS = defaultDS;
	}


	// ----------------------------------------------------------------------------------------------------------------------------------
	// Secao de CRUD
	// ----------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.SUPPORTS)
	public <T, ID extends Serializable> T getByID(Class<T> persistenceClass, ID id) throws ServiceException {
		T value = getEM(persistenceClass).find(persistenceClass, id);
		return value;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.SUPPORTS)
	public <T, ID extends Serializable> List<T> getByAll(Class<T> persistenceClass) throws ServiceException {
		List<T> value = (List<T>) getEM(persistenceClass).createQuery("select c from " + persistenceClass.getName() + " c").getResultList();
		return value;
	}	

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.SUPPORTS)
	public <T, ID extends Serializable> List<T> getByCondition(Class<T> persistenceClass, Map<String, Object> conditions) throws ServiceException {
		
		List<T> value = null;
		
		StringBuilder query = new StringBuilder("select e from " + persistenceClass.getName() + " e where 1=1");
		
		if(conditions != null) {			
			for(Entry<String, Object> entry : conditions.entrySet()) {
				query.append(" and e." + entry.getKey() + entry.getValue());
			}						
		}
		
		LOG.debug("JQL - " + query.toString());		
		value = (List<T>) getEM(persistenceClass).createQuery(query.toString()).getResultList();		
		
		return value;
	}

	@Override
	public <T> int countEntity(Class<T> persistenceClass, Map<String, Object> conditions) throws ServiceException {
		
		StringBuilder query = new StringBuilder("select count(e) from " + persistenceClass.getName() + " e where 1=1");
		
		if(conditions != null) {			
			for(Entry<String, Object> entry : conditions.entrySet()) {
				query.append(" and e." + entry.getKey() + entry.getValue());
			}						
		}
		
		LOG.debug("JQL - " + query.toString());		
		Long value = (Long) getEM(persistenceClass).createQuery(query.toString()).getSingleResult();		
		
		return value.intValue();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.SUPPORTS)
	public <T, ID extends Serializable> List<T> getByCondition(Class<T> persistenceClass, Map<String, Object> conditions, int firstResult, int maxResult) throws ServiceException {

		List<T> value = null;
		
		StringBuilder querys = new StringBuilder("select e from " + persistenceClass.getName() + " e where 1=1");
		
		if(conditions != null) {			
			for(Entry<String, Object> entry : conditions.entrySet()) {
				querys.append(" and e." + entry.getKey() + entry.getValue());
			}						
		}
		
		LOG.debug("JQL - " + querys.toString());		
		Query query = getEM(persistenceClass).createQuery(querys.toString());		
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);		
		value = query.getResultList();		
		
		return value;
		
	}

	@Override
	public <T> T save(T instance) throws ServiceException {
		if (instance == null)
			return null;

		instance = getEM(instance.getClass()).merge(instance);

		return instance;
	}
	
	@Override
	public <T,ID extends Serializable> void delete(Class<T> clazz, ID id) throws ServiceException {
		Query del = getEM(clazz).createQuery("delete from " + clazz.getCanonicalName() + " c where c.id = :id");
		del.setParameter("id", id);
		del.executeUpdate();
	}
	
	@Override
	public <T> List<T> selectQuery(String query, T instance) {
		return getSqlSessionTemplate().selectList(query, instance);
	}
	
	@Override
	public <T> List<T> selectQuery(String query, Map<String, Object> conditions) {		
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("conditions", conditions);
		maps.put("instruction", "select *");		
		return getSqlSessionTemplate().selectList(query, maps);
	}

	@Override
	public <T> List<T> selectQuery(String query, Map<String, Object> conditions, int firstResult, int maxResult) throws ServiceException {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("conditions", conditions);
		maps.put("instruction", "select *");
		return getSqlSessionTemplate().selectList(query, maps, new RowBounds(firstResult, maxResult));
	}

	@Override
	public <T> int countQuery(String query, Map<String, Object> conditions) throws ServiceException {		
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("conditions", conditions);
		maps.put("instruction", "select count(*) TOTAL");		
		BaseObject baseObject = ((BaseObject)getSqlSessionTemplate().selectOne(query, maps));		
		Integer value = baseObject.size;
		return value;
	}	
}
