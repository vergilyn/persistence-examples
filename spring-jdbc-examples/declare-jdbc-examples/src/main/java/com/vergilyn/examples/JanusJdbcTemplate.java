package com.vergilyn.examples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.SneakyThrows;

public class JanusJdbcTemplate implements JdbcOperations {
	private final JanusDatasource datasource;

	public JanusJdbcTemplate(JanusDatasource datasource) {
		this.datasource = datasource;
	}

	@Override
	public int update(String sql, Object... args) {
		return 0;
	}

	@SneakyThrows
	private Object execute(String sql, Object... args){
		Connection connection = null;
		try {
			connection = datasource.getConnection();
			connection.setAutoCommit(false);

			PreparedStatement ps = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				ps.setObject(i, args[i]);
			}
			connection.commit();
		} catch (SQLException throwables) {
		} finally {
			if (connection != null){
				connection.close();
			}
		}

		return null;
	}
}
