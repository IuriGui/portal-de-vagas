package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}
