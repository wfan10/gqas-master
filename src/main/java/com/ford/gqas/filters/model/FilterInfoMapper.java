package com.ford.gqas.filters.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class FilterInfoMapper implements RowMapper<FilterInfoDTO>{
	public FilterInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		FilterInfoDTO filterRow = new FilterInfoDTO();
		filterRow.setFilterDesc(rs.getString("FILTER_DESC"));
		filterRow.setFilterOptionDflt(rs.getString("FILTER_OPTION_DFLT"));
		filterRow.setFilterOptionList(rs.getString("FILTER_OPTION_LIST"));
		filterRow.setFilterPointer(rs.getString("FILTER_POINTER"));
		filterRow.setFilterDescVar(rs.getString("FILTER_DESC_VAR"));
		filterRow.setOperGrpId(rs.getInt("OPER_GRP_ID"));
		filterRow.setFilterId(rs.getString("FILTER_ID"));
		filterRow.setFilterRowCount(rs.getInt("FILTER_ROW_COUNT"));
		filterRow.setFilterLoaded(rs.getString("FILTER_LOADED"));
		filterRow.setFilterUse(rs.getInt("FILTER_USE"));
		filterRow.setFilterAllowTypin(rs.getString("FILTER_ALLOW_TYPIN"));
		filterRow.setFilterColType(rs.getString("FILTER_COL_TYPE"));
		filterRow.setFilterValidTable(rs.getString("FILTER_VALID_TABLE"));
		filterRow.setFilterWhereStr(rs.getString("FILTER_WHERE_STR"));
		filterRow.setFilterSortBy(rs.getString("FILTER_SORT_BY"));
		filterRow.setFilterSingleSel(rs.getInt("FILTER_SINGLE_SEL"));
		filterRow.setFilterQasCol1(rs.getString("FILTER_QAS_COL1"));
		filterRow.setFilterQasCol2(rs.getString("FILTER_QAS_COL2"));
		filterRow.setFilterQasCol3(rs.getString("FILTER_QAS_COL3"));
		filterRow.setFilterQasCol4(rs.getString("FILTER_QAS_COL4"));
		filterRow.setFilterQasCol5(rs.getString("FILTER_QAS_COL5"));
		filterRow.setFilterOwner(rs.getString("FILTER_OWNER"));
		return filterRow;
	}
}
