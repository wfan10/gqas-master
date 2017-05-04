/*
 * Template SQL statement. 
 * 
SELECT GF.group_id, GF.group_name, 
F.filter_desc, F.filter_Option_Dflt, F.filter_Option_List, NVL( F.filter_pointer, 'null' ) filter_pointer, NVL( F.filter_desc_var, 'null' ) filter_desc_var, 
F.oper_grp_id, F.filter_ID, NVL( F.filter_Row_Count, 0 ) filter_row_count, F.filter_loaded, F.filter_use, 
NVL(F.filter_allow_typin,'null') filter_allow_typin, NVL(F.filter_col_type,'null' ) filter_col_type, NVL( F.filter_valid_table,'null' ) filter_valid_table, NVL( F.filter_where_str,'null' ) filter_where_str, NVL( F.filter_sort_by,'null' ) filter_sort_by, 
NVL(F.filter_single_sel,0 ) filter_single_sel, NVL(F.filter_qas_col1,'null' ) filter_qas_col1, NVL(F.filter_qas_col2,'null' ) filter_qas_col2, NVL( F.filter_qas_col3,'null' ) filter_qas_col3, NVL( F.filter_qas_col4,'null' ) filter_qas_col4, NVL( F.filter_qas_col5, 'null' ) filter_qas_col5
FROM FILTERS F, 
--GROUPS G, GROUP_VALUE V 
( 
SELECT DISTINCT G.group_id GROUP_ID, G.group_name GROUP_NAME, V.val_col1 FILTER_ID
FROM groups G, group_value V
WHERE 1=1
AND G.group_id = V.group_id
AND G.filter_id like 'F_FLT%' 
AND G.group_owner IN ( 'SYSTEM','WFAN10') 
AND ( G.group_status = 'VALID' or '|ER|QLS|ECB|EVB|PDI|GQR|' = 'ALL' or regexp_like('|ER|QLS|ECB|EVB|PDI|GQR|','|'||G.group_status||'|')) 
) GF
WHERE 1=1
--G.Group_Name = 'ALL VEHICLE FILTERS' 
--AND G.GROUP_OWNER IN ( 'SYSTEM','WFAN10' ) 
--AND G.Group_ID = V.Group_ID 
AND F.filter_id = GF.filter_id 
ORDER BY 1, F.filter_desc;
*
*
*/

package com.ford.gqas.filters.dao;

import java.sql.Types;
//import java.util.HashMap;

import java.util.List;
//import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
// import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;

import com.ford.gqas.filters.model.*;

@Repository("tblFiltersDao")
public class TblFiltersDAO {

	@Autowired
	private JdbcTemplate jdbcTemplateObj;
	
	//@Autowired
	//private NamedParameterJdbcTemplate namedTemplateObj;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Cacheable(cacheNames = "tblfilters", unless = "#result == null")
	public List<TblFiltersDTO> listTblFiltersRows(String sGrpOwner) {

		logger.info("TblFiltersDAO.listTblFiltersRows entered with parameter " + sGrpOwner);

		String sql = "SELECT GF.group_id, GF.group_name, "
				+ "F.filter_desc, F.filter_Option_Dflt, F.filter_Option_List, NVL( F.filter_pointer, 'null' ) filter_pointer, NVL( F.filter_desc_var, 'null' ) filter_desc_var, "
				+ "F.oper_grp_id, F.filter_ID, NVL( F.filter_Row_Count, 0 ) filter_row_count, F.filter_loaded, F.filter_use, "
				+ "NVL(F.filter_allow_typin,'null') filter_allow_typin, NVL(F.filter_col_type,'null' ) filter_col_type, NVL( F.filter_valid_table,'null' ) filter_valid_table, NVL( F.filter_where_str,'null' ) filter_where_str, NVL( F.filter_sort_by,'null' ) filter_sort_by, "
				+ "NVL(F.filter_single_sel,0 ) filter_single_sel, NVL(F.filter_qas_col1,'null' ) filter_qas_col1, NVL(F.filter_qas_col2,'null' ) filter_qas_col2, NVL( F.filter_qas_col3,'null' ) filter_qas_col3, NVL( F.filter_qas_col4,'null' ) filter_qas_col4, NVL( F.filter_qas_col5, 'null' ) filter_qas_col5 "
				+ "FROM FILTERS F, ( SELECT DISTINCT G.group_id GROUP_ID, G.group_name GROUP_NAME, V.val_col1 FILTER_ID FROM groups G, group_value V "
				+ "WHERE G.group_id = V.group_id AND G.filter_id like 'F_FLT%' AND G.group_owner IN ( 'SYSTEM', ?) "
				+ "AND ( G.group_status = 'VALID' or '|ER|QLS|ECB|EVB|PDI|GQR|' = 'ALL' or regexp_like('|ER|QLS|ECB|EVB|PDI|GQR|','|'||G.group_status||'|')) ) GF "
				+ "WHERE F.filter_id = GF.filter_id ORDER BY 1, F.filter_desc";

		try {
			List<TblFiltersDTO> tblFiltersRows = jdbcTemplateObj.query(sql, new Object[] { sGrpOwner },
					new TblFiltersMapper());
			return tblFiltersRows;

		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * Cache only if return is not null. Reference:
	 * http://stackoverflow.com/questions/12113725/how-do-i-tell-spring-cache-
	 * not-to-cache-null-value-in-cacheable-annotation
	 */
	@Cacheable(cacheNames = "filterinfo", unless = "#result == null")
	public FilterInfoDTO getFilterInfo(String filterId) {

		logger.info("TblFiltersDAO.getFilterInfo entered with parameter " + filterId);

		String sql = "SELECT F.filter_desc, F.filter_Option_Dflt, F.filter_Option_List, NVL( F.filter_pointer, 'null' ) filter_pointer,"
				+ "NVL( F.filter_desc_var, 'null' ) filter_desc_var, F.oper_grp_id, F.filter_id, NVL( F.filter_Row_Count, 0 ) filter_row_count,"
				+ "F.filter_loaded, F.filter_use, NVL(F.filter_allow_typin,'null') filter_allow_typin, NVL(F.filter_col_type,'null' ) filter_col_type,"
				+ "NVL( F.filter_valid_table,'null' ) filter_valid_table, NVL( F.filter_where_str,'null' ) filter_where_str, NVL( F.filter_sort_by,'null' ) filter_sort_by, NVL(F.filter_single_sel,0 ) filter_single_sel,"
				+ "NVL(F.filter_qas_col1,'null' ) filter_qas_col1, NVL(F.filter_qas_col2,'null' ) filter_qas_col2, NVL( F.filter_qas_col3,'null' ) filter_qas_col3, NVL( F.filter_qas_col4,'null' ) filter_qas_col4,"
				+ "NVL( F.filter_qas_col5, 'null' ) filter_qas_col5, F.filter_owner FROM FILTERS F WHERE F.filter_id = ?";
		/*
		 * Must wrap in try / catch to return null. Reference:
		 * https://www.mkyong.com/spring/queryforobject-throws-
		 * emptyresultdataaccessexception-when-record-not-found/
		 */
		try {
			FilterInfoDTO filterInfo = jdbcTemplateObj.queryForObject(sql, new Object[] { filterId.toUpperCase() },
					new FilterInfoMapper());
			return filterInfo;
		} catch (Exception e) {
			// Most likely this EmptyResultDataAccessException
			return null;
		}
	}

	@Cacheable(cacheNames = "filtervalues", unless = "#result == null")
	public List<FilterValueDTO> listFilterValues(String filterId) {
		/*
		 * This function returns ALL of the values in description table. VETA
		 * checking is done in another function call. Returns only SYSTEM filter
		 * values. USER defined filters do not have values.
		 */
		logger.info("TblFiltersDAO.listFilterValues entered with parameter " + filterId);

		List<FilterValueDTO> filterValues = null;

		// Return filter information ... if null then return the empty list.
		FilterInfoDTO filterDTO = this.getFilterInfo(filterId);
		if (filterDTO == null) {
			
			return null;
		}

		if (filterDTO.getFilterPointer().equalsIgnoreCase("FAKE_TBL")) {
			
			return null;
		}

		if (filterDTO.getFilterPointer().equalsIgnoreCase("NULL")) {
			
			return null;
		}

		String sql = "SELECT group_id, group_owner, group_name, fdesc, val_col1, NVL(val_col2,'null') val_col2"
				+ ",NVL(val_col3,'null') val_col3, NVL(val_col4,'null') val_col4, NVL(val_col5,'null') val_col5"
				+ " FROM ( SELECT 0 AS group_id," + "'" + filterDTO.getFilterOwner() + "' AS group_owner, "
				+ "'*' AS group_name," + filterDTO.getFilterDescVar() + " AS fdesc," + filterDTO.getFilterQasCol1()
				+ " AS val_col1," + filterDTO.getFilterQasCol2() + " AS val_col2," + filterDTO.getFilterQasCol3()
				+ " AS val_col3," + filterDTO.getFilterQasCol4() + " AS val_col4," + filterDTO.getFilterQasCol5()
				+ " AS val_col5 FROM " + filterDTO.getFilterPointer() + ") ORDER BY fdesc";

		try {
			filterValues = jdbcTemplateObj.query(sql, new FilterValueMapper());
			return filterValues;
		} catch (Exception e) {
			logger.debug(e.toString());
			return null;
		}

	}

	/*
	public List<FilterValueDTO> listFilterValuesVeta(long reqId, String filterId) {
	
		 * TODO task details: Put together a sql statement from fields from
		 * filterDTO. This function returns ALL of the values in description
		 * table. VETA checking is done in this function call by fetching values
		 * from REQ_VALUE table. Returns only SYSTEM filter values. USER defined
		 * filters do not have values.
		 
		logger.info("TblFiltersDAO.listFilterValuesVeta entered with parameters " + reqId + ":" + filterId);

		List<FilterValueDTO> filterValues = null;

		// Return filter information ... if null then return the empty list.
		FilterInfoDTO filterDTO = this.getFilterInfo(filterId);
		if (filterDTO == null) {
			// return Collections.emptyList();
			return null;
		}

		if (filterDTO.getFilterPointer().equalsIgnoreCase("FAKE_TBL")) {
			// return Collections.emptyList();
			return null;
		}

		if (filterDTO.getFilterPointer().equalsIgnoreCase("NULL")) {
			// return Collections.emptyList();
			return null;
		}

		String sql = "SELECT group_id, group_owner, group_name, fdesc, val_col1" + ",NVL(val_col2,'null') val_col2"
				+ ",NVL(val_col3,'null') val_col3" + ",NVL(val_col4,'null') val_col4" + ",NVL(val_col5,'null') val_col5"
				+ " FROM ( SELECT 0 AS group_id," + "'" + filterDTO.getFilterOwner() + "' AS group_owner, "
				+ "'*' AS group_name," + filterDTO.getFilterDescVar() + " AS fdesc," + filterDTO.getFilterQasCol1()
				+ " AS val_col1," + filterDTO.getFilterQasCol2() + " AS val_col2," + filterDTO.getFilterQasCol3()
				+ " AS val_col3," + filterDTO.getFilterQasCol4() + " AS val_col4," + filterDTO.getFilterQasCol5()
				+ " AS val_col5" + " FROM " + filterDTO.getFilterPointer() + ") ORDER BY fdesc";

		try {
			filterValues = jdbcTemplateObj.query(sql, new FilterValueMapper());
			return filterValues;
		} catch (Exception e) {
			return null;
		}

	}

	*/
	
	public List<FilterValueDTO> listFilterGroups(String filterId, String sGrpOwner) {
		/*
		 * TODO task details: This function returns USER or SYSTEM groups. VETA
		 * checking is implemented in another function.
		 */
		logger.info("TblFiltersDAO.listFilterGroups entered with parameters " + filterId + ":" + sGrpOwner);

		List<FilterValueDTO> filterValues = null;

		String sql = "SELECT group_id, group_owner, group_name, fdesc, val_col1, val_col2, val_col3, val_col4, val_col5 FROM ( "
				+ "SELECT g.group_id, g.group_owner, g.group_name, g.group_name AS fdesc, "
				+ "'null' AS val_col1, 'null' AS val_col2, 'null' AS val_col3, 'null' AS val_col4, 'null' AS val_col5 "
				+ " FROM groups g WHERE g.group_status = 'VALID' AND g.filter_id= ? AND g.group_owner IN ('SYSTEM', ?))"
				+ " ORDER BY group_owner, fdesc";
		
		try {
			
			/*
			 * Alternate way to do SQL 
			Map<String, String> namedParam = new HashMap<String,String>();
			namedParam.put("filter_id", filterId);
			namedParam.put("group_owner", sGrpOwner);
			filterValues = this.namedTemplateObj.query(sql, namedParam, new FilterValueMapper());
			*/
			// int[] params = { Types.VARCHAR, Types.VARCHAR };
		
			filterValues = jdbcTemplateObj.query(sql, new Object[] { filterId.toUpperCase(), sGrpOwner.toUpperCase() }, 
												new int[]{ Types.VARCHAR, Types.VARCHAR }, new FilterValueMapper());
			
			return filterValues;

		} catch (Exception e) {
			logger.debug(e.toString());
			return null;
		}

	}

	/*
	public List<FilterValueDTO> listFilterGroupsVeta(long reqId, String filterId, String sGrpOwner) {
		/*
		 * TODO task details: This function returns user or SYSTEM groups. VETA
		 * checking is implemented in this function. First find the filter and
		 * request information.
		 
		logger.info(
				"TblFiltersDAO.listFilterGroups entered with parameters " + reqId + ":" + filterId + ":" + sGrpOwner);

		String sql = "SELECT g.group_id, g.group_owner, g.group_name, " + "g.group_name AS fdesc, "
				+ "'null' AS val_col1, " + "'null' AS val_col2, " + "'null' AS val_col3, " + "'null' AS val_col4, "
				+ "'null' AS val_col5, decode(g.group_owner,'SYSTEM','z','a') owner_ob "
				+ "FROM groups g WHERE g.group_status = 'VALID'"
				+ " AND g.filter_id = ? AND g.group_owner IN ('SYSTEM',?)" + " ORDER BY owner_ob, fdesc";

		try {
			List<FilterValueDTO> filterValues = jdbcTemplateObj.query(sql, new Object[] { filterId, sGrpOwner },
					new FilterValueMapper());
			return filterValues;
		} catch (Exception e) {
			return null;
		}

	}
	*/
}
