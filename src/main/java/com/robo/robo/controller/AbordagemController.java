package com.robo.robo.controller;

import com.robo.robo.service.AbordagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("abordagem")
@RequiredArgsConstructor
public class AbordagemController {

    private final AbordagemService service;

    @GetMapping("importar")
    public String importar() {
        return service.importar();
    }

}
