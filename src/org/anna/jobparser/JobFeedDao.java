package org.anna.jobparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JobFeedDao {
	 
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insertJason(String boxName, String jobName, String jobOwner){
		System.out.println(jdbcTemplate);
		this.jdbcTemplate.update(
		        "insert into job_feed (box_name, job_name,job_owner) values (?, ?, ?)",boxName,jobName,jobOwner);
		
	}
}
