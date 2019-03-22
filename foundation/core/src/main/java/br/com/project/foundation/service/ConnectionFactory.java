package br.com.project.foundation.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.util.ClassUtils;

import br.com.project.commons.exception.BaseException;
import br.com.project.commons.exception.ExceptionFactory;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.Constants;
import br.com.project.foundation.configuration.BPConfiguration;

public class ConnectionFactory {
	
	private static final String DEFAULT_DATASOURCE = Constants.DEFAULT_DATA_SOURCE;

	private static final String DEFAULT_URL = BPConfiguration.getString(Constants.KEY_CONNECTIONFACTORY_DEFAULTURL);
	private static final String DEFAULT_USER = BPConfiguration.getString(Constants.KEY_CONNECTIONFACTORY_DEFAULTUSER);
	private static final String DEFAULT_PASSWD = BPConfiguration.getString(Constants.KEY_CONNECTIONFACTORY_DEFAULTPASSWD);
	protected static int queryTimeout = 300;

	public static final Connection getConnection() throws BaseException {
		return getConnection(null);
	}

	public static final Connection getConnection(String dataSourceName) throws BaseException {
		Connection conn = null;
		try {
			String dsName = StringUtil.isEmptyTrim(dataSourceName) ? DEFAULT_DATASOURCE : dataSourceName;
			DataSource ds = (DataSource) ServiceLocator.getInstance().getBean(dsName);
			conn = ds.getConnection();
		} catch (Exception e) {
			try {
				conn = DriverManager.getConnection(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWD);
				return proxyWithQueryTimeout(conn);
			} catch (Exception e1) {
				throw ExceptionFactory.createException(BaseException.class, e1);
			}
		}
		return conn;
	}

	private static Connection proxyWithQueryTimeout(final Connection connection) {
		return proxy(connection, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object object = method.invoke(connection, args);
				if (object instanceof Statement) {
					Statement st = ((Statement) object);
					st.setQueryTimeout(queryTimeout);
				}
				return object;
			}
		});
	}

	private static Connection proxy(Connection connection, InvocationHandler invocationHandler) {
		return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(), ClassUtils.getAllInterfaces(connection), invocationHandler);
	}

	public static void setQueryTimeout(int time) {
		queryTimeout = time;
	}

}
