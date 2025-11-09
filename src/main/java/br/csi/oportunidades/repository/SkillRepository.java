package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
