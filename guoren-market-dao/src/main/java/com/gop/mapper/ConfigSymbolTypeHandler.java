package com.gop.mapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.gop.domain.enums.ConfigSymbolType;

public class ConfigSymbolTypeHandler implements TypeHandler<ConfigSymbolType> {

	@Override
	public void setParameter(PreparedStatement ps, int i, ConfigSymbolType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public ConfigSymbolType getResult(ResultSet rs, String columnName) throws SQLException {
		String string = rs.getString(columnName);
		return ConfigSymbolType.getType(string);
	}

	@Override
	public ConfigSymbolType getResult(ResultSet rs, int columnIndex) throws SQLException {
		String string = rs.getString(columnIndex);

		return ConfigSymbolType.getType(string);
	}

	@Override
	public ConfigSymbolType getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String string = cs.getString(columnIndex);

		return ConfigSymbolType.getType(string);
	}

}
