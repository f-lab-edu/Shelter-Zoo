package com.noint.shelterzoo.service.infraTest;

import com.noint.shelterzoo.repository.infraTest.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfraTestService {
    private final TestRepository repository;

    public void test(){
        System.out.println(repository.test());
    }

}
