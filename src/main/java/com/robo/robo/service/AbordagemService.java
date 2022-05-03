package com.robo.robo.service;

import com.robo.robo.entity.AbordagemEntity;
import com.robo.robo.repository.AbordagemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbordagemService {

    private final AbordagemRepository repository;
    private final ImportarService importarService;

    @Transactional
    public String importar() {
        List<String> alreadyInBase =
                repository.findAll()
                        .stream()
                        .map(AbordagemEntity::getTelefone)
                        .collect(Collectors.toList());

        log.info("IMPORTACAO INICIADA");
        repository.saveAll(importarService.getAbordagensFromFile(alreadyInBase));
        log.info("IMPORTACAO CONCLUIDA");
        return "IMPORTACAO CONCLUIDA";
    }
}
