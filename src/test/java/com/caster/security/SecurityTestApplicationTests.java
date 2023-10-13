package com.caster.security;

import com.caster.security.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityTestApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }


    @Test
    public void sqlTest() {



    }


}
