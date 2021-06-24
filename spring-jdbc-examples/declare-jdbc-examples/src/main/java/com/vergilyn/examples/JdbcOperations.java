package com.vergilyn.examples;

public interface JdbcOperations {

	int update(String sql, Object... args);
}
