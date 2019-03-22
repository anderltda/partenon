package br.com.project.foundation.persistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;

import org.springframework.util.ClassUtils;

import br.com.project.foundation.service.ConnectionFactory;

public class OracleDataSourceWrapper extends OracleDataSource {
	
	private static final long serialVersionUID = 1L;
	
	private boolean autoCommit = true;
	
	private int queryTimeout = 300;

	public OracleDataSourceWrapper() throws SQLException {
		super();
	}

	public Connection getConnection() throws SQLException {
		Connection connection = proxyWithQueryTimeout(super.getConnection());
		if (connection == null) {
			throw new SQLException("Erro ao busca conexão com Banco de Dados");
		}
		connection.setAutoCommit(false);
		return connection;
	}

	@SuppressWarnings("deprecation")
	public synchronized void setConnectionCacheProperties(Properties props) throws SQLException {
		if (props == null) {
			throw new IllegalArgumentException("Properties are null");
		}
		super.setConnectionCachingEnabled(true);
		super.setFastConnectionFailoverEnabled(false);
		super.setConnectionCacheProperties(props);
	}

	public void afterPropertiesSet() throws Exception {
	}

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public void setQueryTimeout(int queryTimeout) {
		this.queryTimeout = queryTimeout;
		// hook caso usem a conexão direta
		ConnectionFactory.setQueryTimeout(queryTimeout);
	}

	public int getQueryTimeout() {
		return queryTimeout;
	}

	private Connection proxyWithQueryTimeout(final Connection connection) {
		return proxy(connection, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object object = method.invoke(connection, args);
				if (object instanceof Statement) {
					Statement st = ((Statement) object);
					st.setQueryTimeout(getQueryTimeout());
				}
				return object;
			}
		});
	}

	private Connection proxy(Connection connection, InvocationHandler invocationHandler) {
		return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(), ClassUtils.getAllInterfaces(connection), invocationHandler);
	}

}
// http://www.idevelopment.info/data/Programming/java/jdbc/High_Availability/FastConnectionFailoverExampleThin.java