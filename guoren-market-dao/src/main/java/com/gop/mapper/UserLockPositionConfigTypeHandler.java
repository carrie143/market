package com.gop.mapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.gop.domain.UserLockPositionConfigType;

public class UserLockPositionConfigTypeHandler implements TypeHandler<UserLockPositionConfigType>{

	@Override
	public void setParameter(PreparedStatement ps, int i, UserLockPositionConfigType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public UserLockPositionConfigType getResult(ResultSet rs, String columnName) throws SQLException {
		String result = rs.getString(columnName);
		return  UserLockPositionConfigType.getType(result);
		
	}

	@Override
	public UserLockPositionConfigType getResult(ResultSet rs, int columnIndex) throws SQLException {
		String result = rs.getString(columnIndex);
		return  UserLockPositionConfigType.getType(result);
	}

	@Override
	public UserLockPositionConfigType getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String result = cs.getString(columnIndex);
		return UserLockPositionConfigType.getType(result);
	}


}
