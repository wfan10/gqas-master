package com.ford.gqas.filters.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ford.gqas.filters.dao.TblFiltersDAO;
import com.ford.gqas.filters.model.*;

@Service("filtersService")
public class FiltersService {

	@Autowired
	TblFiltersDAO tblFiltersDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<TblFiltersDTO> listTblFiltersRows(String sGrpOwner) {

		logger.info("FiltersService.listTblFiltersRows entered with parameter " + sGrpOwner);
		return tblFiltersDao.listTblFiltersRows(sGrpOwner);

	}

	/*
	 * Implement this from DAO ... much better idea.
	 * 
	 * @Cacheable(cacheNames = "filterinfo", key = "#filterId") public
	 * TblFiltersDTO getFilterById(String filterId, String sGrpOwner) {
	 * 
	 * logger.
	 * info("FiltersService.getFilterById entered with filterId and sGrpOwner "
	 * + filterId + "; " + sGrpOwner);
	 * 
	 * // listTblFilterRows should get the list from the cache. Look at the log
	 * // trail. List<TblFiltersDTO> listFiltersDTO =
	 * this.listTblFiltersRows(sGrpOwner); TblFiltersDTO filterDTO = null; //
	 * loop through the list to find the filter info. for (TblFiltersDTO filter
	 * : listFiltersDTO) { if (filter.getFilterId().equalsIgnoreCase(filterId))
	 * { filterDTO = filter; break; } }
	 * 
	 * return filterDTO; }
	 */

	public List<FilterValueDTO> listFilterValues(String filterId, String options, String sGrpOwner) {
		/*
		 * TODO task details: idea is this 1. get a list for values 2. get a
		 * list for groups 3. if options are VG ... then concatenate the list.
		 * List<MyClass> union = new ArrayList<MyClass>(); union.addAll( listA
		 * ); union.addAll( listB );
		 */
		// Either V or G is in options. 
		logger.info("FiltersService.listFilterValues entered with parameters " + filterId + ":" + options + ":"
				+ sGrpOwner);
			
		// instantiate  empty lists.
		List<FilterValueDTO> listValues = null;
		List<FilterValueDTO> listGroups = null;
		List<FilterValueDTO> listFinal = null;
		
		// Get the groups list
		if ( options.indexOf('G') >= 0 ) {
			listGroups = tblFiltersDao.listFilterGroups(filterId, sGrpOwner);		
		}
		// Get the values list
		if ( options.indexOf('V') >= 0 ) {
			listValues = tblFiltersDao.listFilterValues(filterId);
		}		
		
		if ( listGroups == null && listValues == null )
			return Collections.emptyList();
		// Return the final concatenated list. 
		if ( listGroups != null ) 
			listFinal = listGroups;
		if ( listValues != null ){
			if ( listFinal != null )
				listFinal.addAll(listValues);
			else
				listFinal = listValues;
		}
		return listFinal;
	}
	
}
