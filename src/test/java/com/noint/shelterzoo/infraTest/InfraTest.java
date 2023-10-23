package com.noint.shelterzoo.infraTest;

import com.noint.shelterzoo.service.infraTest.InfraTestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
public class InfraTest {
    @Autowired
    private InfraTestService testService;

    @Test
    @DisplayName("DB연결 테스트")
    void test(){
        testService.test();
    }
}
