package com.glomozda.machinerepair.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/persistenceContextTest.xml"})
public abstract class DAOTestsTemplate {

    @Autowired
    protected JdbcTemplate jdbcTemplate; 

}
