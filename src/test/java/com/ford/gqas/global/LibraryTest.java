package com.ford.gqas.global;
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.ford.gqas.global.Library;

import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;

public class LibraryTest {
	
	private Jedis jedis;

	//@Autowired
	private TestRestTemplate testRestTemplate;
	
	//@Autowired
	//private TestRestTemplate templateNoAuth;

	//private String host = "localhost";

	// @LocalServerPort ... same as below?
	@Value("${local.server.port}")
	private int port;

	// management.port
	@Value("${local.management.port}")
	private int mgt;

	@Before
	public void clearRedisData() {
		//testRestTemplate = new TestRestTemplate();
		testRestTemplate = new TestRestTemplate("admin", "password", null);
		// testRestTemplate.withBasicAuth( "admin", "password" );
		jedis = new Jedis("localhost", 6379);
		jedis.flushAll();
	}

    @Test
    public void testRedisIsEmpty() {
        Set<String> result = jedis.keys("*");
        assertEquals(0, result.size());
    }
   
    
    @Test 
    public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
    }
}