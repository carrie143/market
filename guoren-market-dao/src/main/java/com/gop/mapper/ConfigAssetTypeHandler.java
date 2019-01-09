package com.gop.mapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.gop.domain.enums.ConfigAssetType;

public class ConfigAssetTypeHandler implements TypeHandler<ConfigAssetType> {

	@Override
	public void setParameter(PreparedStatement ps, int i, ConfigAssetType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public ConfigAssetType getResult(ResultSet rs, String columnName) throws SQLException {
		String string = rs.getString(columnName.toUpperCase());
		// return ConfigAssetType.valueOf(string);
		return ConfigAssetType.getType(string);
	}

	@Override
	public ConfigAssetType getResult(ResultSet rs, int columnIndex) throws SQLException {
		String string = rs.getString(columnIndex);
		// return ConfigAssetType.valueOf(string.toUpperCase());
		return ConfigAssetType.getType(string);
	}

	@Override
	public ConfigAssetType getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String string = cs.getString(columnIndex);
		// return ConfigAssetType.valueOf(string.toUpperCase());
		return ConfigAssetType.getType(string);
	}

}
