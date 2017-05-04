package com.ford.gqas.filters.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FilterValueMapper implements RowMapper<FilterValueDTO> {

		public FilterValueDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

			FilterValueDTO filterValue = new FilterValueDTO();
			filterValue.setGroupId(rs.getLong("group_id"));
			filterValue.setGroupOwner(rs.getString("group_owner"));
			filterValue.setGroupName(rs.getString("group_name"));
			filterValue.setFdesc(rs.getString("fdesc"));
			filterValue.setValCol1( rs.getString("val_col1"));
			filterValue.setValCol2( rs.getString("val_col2"));
			filterValue.setValCol3( rs.getString("val_col3"));
			filterValue.setValCol4( rs.getString("val_col4"));
			filterValue.setValCol5( rs.getString("val_col5"));
			return filterValue;
		}
}
