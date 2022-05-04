package com.robo.robo.service.whatsapp.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Remote {
    private String server;
    private String user;
    private String _serialized;
}
