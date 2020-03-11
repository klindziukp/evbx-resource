package com.evbx.resource.test.service;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@ComponentScan("com.evbx.resource.layer")
@Sql(scripts = { "classpath:script/clean.sql", "classpath:script/data.sql" })
abstract class BaseServiceTest {

    void mockSecurityContext() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("script");
        SecurityContextHolder.setContext(securityContext);
    }
}
