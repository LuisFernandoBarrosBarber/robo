package com.robo.robo.repository;

import com.robo.robo.entity.AbordagemEntity;
import com.robo.robo.enumerator.EtapaAbordagem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface AbordagemRepository extends JpaRepository<AbordagemEntity, Long> {

    Stream<AbordagemEntity> findAllByIsAtivoIsTrueAndEtapaAbordagemOrderByCriadoEm(
            EtapaAbordagem etapaAbordagem,
            Pageable pageable);

}
