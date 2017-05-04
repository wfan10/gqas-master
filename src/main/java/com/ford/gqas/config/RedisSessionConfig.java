package com.ford.gqas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.session.ExpiringSession;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

/*
 * For information on why this class is important see link:
 *  https://sdqali.in/blog/2016/07/16/controlling-redis-auto-configuration-for-spring-boot-session/
 */
// Timeout can be configured in properties or as: EnableRedisHttpSession(maxInactiveIntervalInSeconds=3600)
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {

	/* For information on session timeout  
	 *  https://github.com/spring-projects/spring-session/issues/110
	 * @Value("${server.session-timeout}") private int
	 * maxInactiveIntervalInSeconds;
	 * 
	 * @Primary
	 * 
	 * @Bean public RedisOperationsSessionRepository
	 * sessionRepository(RedisTemplate<String, ExpiringSession>
	 * sessionRedisTemplate) { RedisOperationsSessionRepository
	 * sessionRepository = new
	 * RedisOperationsSessionRepository(sessionRedisTemplate);
	 * sessionRepository.setDefaultMaxInactiveInterval(
	 * maxInactiveIntervalInSeconds); return sessionRepository; }
	 * 
	 * 
	 * @Bean public JedisConnectionFactory connectionFactory() { return new
	 * JedisConnectionFactory(); }
	 * 
	 * @Bean public RedisTemplate<String, Customer> name( RedisConnectionFactory
	 * connectionFactory) { RedisTemplate<String, Customer> redisTemplate = new
	 * RedisTemplate<String, Customer>();
	 * redisTemplate.setConnectionFactory(connectionFactory); return
	 * redisTemplate; }
	 */
}
