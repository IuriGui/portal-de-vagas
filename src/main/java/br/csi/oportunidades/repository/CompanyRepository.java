package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
