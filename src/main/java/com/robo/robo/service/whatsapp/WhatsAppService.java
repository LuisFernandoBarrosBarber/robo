package com.robo.robo.service.whatsapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robo.robo.configuration.WhatsAppProperties;
import com.robo.robo.service.whatsapp.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Slf4j
public class WhatsAppService {

    private final WhatsAppProperties whatsAppProperties;

    public ResponseBody send(String number, String message) throws IOException {

        URL url = new URL(whatsAppProperties.getHost() + "/send-message");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("number", number);
        params.put("message", message);
        params.put("token", "TOKEN_HARD_BARBER_2021");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), UTF_8));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), UTF_8));
        }

        byte[] postDataBytes = postData.toString().getBytes(UTF_8);
        connection.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
            writer.write(postDataBytes);

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(content.toString(), ResponseBody.class);
        } finally {
            connection.disconnect();
        }
    }
}
