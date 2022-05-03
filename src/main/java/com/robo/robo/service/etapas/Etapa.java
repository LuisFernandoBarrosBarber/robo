package com.robo.robo.service.etapas;

import com.robo.robo.enumerator.EtapaAbordagem;

public interface Etapa {
    boolean matches(EtapaAbordagem e);

    EtapaAbordagem nextEtapa();

    String getTextToAbordar();
}
