package com.evbx.resource.test.repository;

import com.evbx.resource.support.ProjectionVerificationService;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@ComponentScan("com.evbx.resource.layer")
@Sql(scripts = { "classpath:script/clean.sql", "classpath:script/data.sql" })
public abstract class BaseRepositoryTest {

    ProjectionVerificationService projectionVerificationService = new ProjectionVerificationService();
}
