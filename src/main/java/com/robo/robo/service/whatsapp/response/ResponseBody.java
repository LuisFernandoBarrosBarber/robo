package com.robo.robo.service.whatsapp.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseBody {
    private boolean status;
    private Response response;
}
