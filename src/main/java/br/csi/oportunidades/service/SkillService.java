package br.csi.oportunidades.service;


import br.csi.oportunidades.model.Skill;
import br.csi.oportunidades.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public Set<Skill> findAll() {
        return new HashSet<>(skillRepository.findAll());
    }

}
