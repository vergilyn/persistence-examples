package com.vergilyn.examples;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class JanusDatasource implements DataSource {
	private final DataSource targetDataSource;

	public JanusDatasource(DataSource targetDataSource) {
		this.targetDataSource = targetDataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return targetDataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return targetDataSource.getConnection(username, password);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return targetDataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return targetDataSource.isWrapperFor(iface);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return targetDataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		targetDataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		targetDataSource.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return targetDataSource.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return targetDataSource.getParentLogger();
	}
}
