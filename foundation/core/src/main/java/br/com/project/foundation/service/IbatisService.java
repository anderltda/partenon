package br.com.project.foundation.service;

import java.util.List;

import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 *
 */
public interface IbatisService {

	/* METODOS P/ IBATIS */
	<T> List<T> executeQuery(String queryName, T instance, int firstResult, int maxResult) throws ServiceException;
	<T> List<T> executeQuery(String queryName, T instance) throws ServiceException;
}
