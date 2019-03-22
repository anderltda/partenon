package br.com.project.foundation;

import br.com.project.foundation.configuration.BPConfiguration;


public class Constants {

	public static final String DEFAULT_DATA_SOURCE = BPConfiguration.getString(Constants.KEY_CONNECTIONFACTORY_DEFAULTDS, "oracleDataSource");
	public static final String DEFAULT_TRANSACTION_MANAGER = BPConfiguration.getString(Constants.KEY_CONNECTIONFACTORY_DEFAULTTRANSACTIONMANAGER, "transactionManager");
	public static final String KEY_CONNECTIONFACTORY_DEFAULTTRANSACTIONMANAGER = "connectionFactory.default.TransactionManager";
	public static final String KEY_CONNECTIONFACTORY_DEFAULTDS = "connectionFactory.default.DataSource";
	public static final String KEY_CONNECTIONFACTORY_DEFAULTURL = "connectionFactory.default.URL";
	public static final String KEY_CONNECTIONFACTORY_DEFAULTUSER = "connectionFactory.default.User";
	public static final String KEY_CONNECTIONFACTORY_DEFAULTPASSWD = "connectionFactory.default.Password";

	public static final String SPACE = " ";
	public static final String OPEN_P = "(";
	public static final String CLOSE_P = ")";
	public static final String SIM = "S";
	public static final String NAO = "N";
		
	public static final String ENDERECO_PAIS_PADRAO = "BRASIL";

	public static final String LOGGED_USER = "LOGGED_USER";
	public static final String LOGGED_DOMAIN = "LOGGED_DOMAIN";
}
