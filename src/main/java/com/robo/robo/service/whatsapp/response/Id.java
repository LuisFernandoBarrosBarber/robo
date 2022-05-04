package com.robo.robo.service.whatsapp.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Id {
    private boolean fromMe;
    private Remote remote;
    private String id;
    private String _serialized;
}
