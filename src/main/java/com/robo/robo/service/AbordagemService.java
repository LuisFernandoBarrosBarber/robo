package com.robo.robo.service;

import com.robo.robo.entity.AbordagemEntity;
import com.robo.robo.entity.IgnorarEntity;
import com.robo.robo.enumerator.EtapaAbordagem;
import com.robo.robo.repository.AbordagemRepository;
import com.robo.robo.repository.IgnorarRepository;
import com.robo.robo.service.etapas.Etapa;
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
    private final IgnorarRepository ignorarRepository;
    private final ImportarService importarService;
    private final AbordagemHelpService helpService;
    private final List<Etapa> etapas;
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

        List<String> ignorarList =
                ignorarRepository.findAll()
                        .stream()
                        .map(IgnorarEntity::getTelefone)
                        .collect(Collectors.toList());

        repository.saveAll(importarService.getAbordagensFromFile(alreadyInBase, ignorarList));
        log.info(operacao + " CONCLUÍDA");
        return FINISH + operacao;
    }

    @Transactional
    public String abordar(int qtd, EtapaAbordagem e) {
        String operacao = "ABORDAGEM. ETAPA: " + e;
        log.info(operacao + " INICIADA");
        Etapa etapa =
                etapas.stream().filter(it -> it.matches(e)).findFirst()
                        .orElseThrow(() -> {
                            throw new RuntimeException("ETAPA NAO ENCONTRADA");
                        });

        List<AbordagemEntity> abordagens =
                repository.findAllByIsAtivoIsTrueAndEtapaAbordagemOrderByCriadoEm(e, PageRequest.of(0, qtd))
                        .collect(Collectors.toList());
        log.info("ENCONTRADAS " + abordagens.size() + " ABORDAGENS PARA FAZER.");
        abordagens.forEach(it -> {
            it.setUltimaTentativa(now());
            try {
                helpService.tryAbordar(it, etapa.getTextToAbordar());
                it.setSucesso(true);
                it.setEtapaAbordagem(etapa.nextEtapa());
                it.setErro(null);
            } catch (Exception ex) {
                log.info("ERRO AO ABORDAR NÚMERO " + it.getTelefone());
                it.setErro(ex.getMessage());
                it.setAtivo(false);
                it.setSucesso(false);
            }
        });
        log.info(operacao + " CONLUÍDA");
        return FINISH + operacao;
    }
}
