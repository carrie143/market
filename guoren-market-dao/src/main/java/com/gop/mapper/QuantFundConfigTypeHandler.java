package com.gop.mapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.gop.domain.enums.QuantFundConfigType;

public class QuantFundConfigTypeHandler implements TypeHandler<QuantFundConfigType> {

	@Override
	public void setParameter(PreparedStatement ps, int i, QuantFundConfigType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public QuantFundConfigType getResult(ResultSet rs, String columnName) throws SQLException {
		return QuantFundConfigType.getType(rs.getString(columnName));
	}

	@Override
	public QuantFundConfigType getResult(ResultSet rs, int columnIndex) throws SQLException {
		return QuantFundConfigType.getType(rs.getString(columnIndex));
	}

	@Override
	public QuantFundConfigType getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return QuantFundConfigType.getType(cs.getString(columnIndex));
	}

}
