package br.csi.oportunidades.controller;


import br.csi.oportunidades.model.Skill;
import br.csi.oportunidades.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/skills")
@AllArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    @Operation(summary = "Lista Habilidades", description = "Retorna lista com todas as habilidades cadastradas no sistema.")
    public Set<Skill> getSkills() {
        return skillService.findAll();
    }



}
