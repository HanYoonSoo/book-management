package com.hanyoonsoo.bookmanagement;

import com.hanyoonsoo.bookmanagement.global.config.JpaConfig;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class IntegrationSupporter {

    @Autowired
    private CleanUp cleanUp;

    @AfterEach
    void tearDown() {
        cleanUp.all();
    }
}
