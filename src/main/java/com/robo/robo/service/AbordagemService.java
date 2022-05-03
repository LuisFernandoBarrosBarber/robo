package com.robo.robo.service;

import com.robo.robo.entity.AbordagemEntity;
import com.robo.robo.enumerator.EtapaAbordagem;
import com.robo.robo.repository.AbordagemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbordagemService {

    private final AbordagemRepository repository;
    private final ImportarService importarService;
    private final AbordagemHelpService helpService;
    private static final String FINISH = "OPERAÇÃO CONCLUÍDA: ";

    @Transactional
    public String importar() {
        String operacao = "IMPORTAÇÃO";
        log.info(operacao + " INICIADA");
        List<String> alreadyInBase =
                repository.findAll()
                        .stream()
                        .map(AbordagemEntity::getTelefone)
                        .collect(Collectors.toList());

        repository.saveAll(importarService.getAbordagensFromFile(alreadyInBase));
        log.info(operacao + " CONCLUÍDA");
        return FINISH + operacao;
    }

    @Transactional
    public String abordar(int qtd, EtapaAbordagem etapa) {
        String operacao = "ABORDAGEM. ETAPA: " + etapa;
        log.info(operacao + " INICIADA");
        List<AbordagemEntity> abordagens =
                repository.findAllByIsAtivoIsTrueAndEtapaAbordagemOrderByCriadoEm(etapa, PageRequest.of(0, qtd))
                        .collect(Collectors.toList());
        log.info("ENCONTRADOS " + abordagens.size() + " ABORDAGENS PARA FAZER.");
        abordagens.forEach(it -> {
            it.setUltimaTentativa(now());
            try {
                helpService.tryAbordar(it);
                it.setSucesso(true);
            } catch (Exception e) {
                log.info("ERRO AO ABORDAR NÚMERO " + it.getTelefone());
                it.setErro(e.getMessage());
                it.setAtivo(false);
                it.setSucesso(false);
            }
        });
        log.info(operacao + " CONLUÍDA");
        return FINISH + operacao;
    }
}
