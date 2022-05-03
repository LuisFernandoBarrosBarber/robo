package com.robo.robo.service;

import com.robo.robo.entity.AbordagemEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbordagemHelpService {

    public void tryAbordar(AbordagemEntity a) {
        if (a.getId() % 2 == 0) {
            throw new RuntimeException("ERRO AO ABORDAR");
        }
        // TODO: MANDAR MENSAGENS
    }
}
