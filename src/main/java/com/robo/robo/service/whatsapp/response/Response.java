package com.robo.robo.service.whatsapp.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private Id id;
    private String ack;
    private boolean hasMedia;
    private String body;
    private String type;
    private Integer timestamp;
    private String from;
    private String to;
    private String deviceType;
    private boolean isForwarded;
    private Integer forwardingScore;
    private boolean isStarred;
    private boolean fromMe;
    private boolean hasQuotedMsg;
    private List<String> vCards;
    private List<String> mentionedIds;
}

