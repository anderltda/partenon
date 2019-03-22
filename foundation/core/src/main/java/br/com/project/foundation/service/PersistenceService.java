package br.com.project.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 *
 */
public interface PersistenceService {
	
	public static final String SERVICE = "persistenceService";

	<T> T save(T instance) throws ServiceException;	
	<T, ID extends Serializable> void delete(Class<T> clazz, ID id) throws ServiceException;
	<T, ID extends Serializable> T getByID(Class<T> persistenceClass, ID id) throws ServiceException;
	<T, ID extends Serializable> List<T> getByAll(Class<T> persistenceClass) throws ServiceException;
	<T, ID extends Serializable> List<T> getByCondition(Class<T> persistenceClass, Map<String, Object> conditions) throws ServiceException;
	<T, ID extends Serializable> List<T> getByCondition(Class<T> persistenceClass, Map<String, Object> conditions, int firstResult, int maxResult) throws ServiceException;
	<T> int countEntity(Class<T> persistenceClass, Map<String, Object> conditions) throws ServiceException;
	<T> EntityManager getEM(Class<T> clazz);
	EntityManager getEM();
	DataSource getDefaultDS();

	/* METODOS P/ IBATIS */
	<T> List<T> selectQuery(String query, Map<String, Object> conditions) throws ServiceException;
	<T> List<T> selectQuery(String query, Map<String, Object> conditions, int firstResult, int maxResult) throws ServiceException;
	<T> int countQuery(String query, Map<String, Object> conditions) throws ServiceException;
	<T> List<T> selectQuery(String query, T instance) throws ServiceException;
}

