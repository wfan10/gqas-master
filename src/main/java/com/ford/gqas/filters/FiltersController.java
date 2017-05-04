package com.ford.gqas.filters;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.ui.ModelMap;

import com.ford.gqas.filters.service.FiltersService;
import com.ford.gqas.filters.model.*;

//This way works to allow query from any domain origin. 
// CrossOrigin annotations can be replaced by global CORS filters. 
// @CrossOrigin
// @CrossOrigin(origins = "http://localhost:8081")
@RestController
// @RequestMapping("/filters")
public class FiltersController {

	@Autowired
	FiltersService filtersService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public Map<String, String> getResource() {
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "here is some resource");
		return resource;
	}

	// Just login and return greeting
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<String> login(HttpSession session) {
		//
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		// save user into session variable. 
		session.setAttribute("authuser", userName );
		String greeting = "Hello " + userName;
		return new ResponseEntity<String>(greeting, HttpStatus.OK);
	}

	// invalidate session
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<Void> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// get all tblFiltersRows
	@RequestMapping(value = "/filterlist", method = RequestMethod.GET)
	public ResponseEntity<List<TblFiltersDTO>> listTblFilters(HttpSession session) {
		// String userName = (String)session.getAttribute("authuser");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		//if ( userName )
		logger.info("FiltersController.listTblFilters entered. Found userName in session as " + userName);

		List<TblFiltersDTO> tblFiltersRows = filtersService.listTblFiltersRows(userName);

		if (tblFiltersRows.isEmpty()) {
			// You many decide to return HttpStatus.NOT_FOUND
			return new ResponseEntity<List<TblFiltersDTO>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<TblFiltersDTO>>(tblFiltersRows, HttpStatus.OK);
	}
	
	// This controller function fetches ALL values. No request id or VETA checking is involved. 
	// GET filter values for filterid. Options are the following: V,G,VG
	// URL sample: http://localhost:9000/filtervalues/F_ENG?options=VG+
	@RequestMapping( value = "/filtervalues/{filterId}", method = RequestMethod.GET)
	public ResponseEntity<List<FilterValueDTO>> getFilterValues(
			HttpSession session,
			@PathVariable(value="filterId") String filterId,
			@RequestParam(value="options", required=true) String options ) {
		
		// String userName = (String)session.getAttribute("authuser");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		List<FilterValueDTO> listFilterValues = Collections.emptyList();
		
		logger.info("FiltersController.getFilterValues entered. Parameters are " + userName + ":" + filterId + ":" + options);
		
		// Values only indicated if there is V or G is in the list. 
		if ( ( options.indexOf('G') < 0 ) && ( options.indexOf('V') < 0 ) ){
			return new ResponseEntity<List<FilterValueDTO>>(HttpStatus.NO_CONTENT);
		}
		
		listFilterValues = filtersService.listFilterValues( filterId, options, userName );
				
		if ( listFilterValues.isEmpty() ){
			return new ResponseEntity<List<FilterValueDTO>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<FilterValueDTO>>(listFilterValues, HttpStatus.OK);
	}

}
