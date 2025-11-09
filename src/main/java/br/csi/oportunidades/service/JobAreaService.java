package br.csi.oportunidades.service;


import br.csi.oportunidades.model.opportunity.JobArea;
import br.csi.oportunidades.repository.JobAreaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobAreaService {

    private final JobAreaRepository jobAreaRepository;

    public List<JobArea> findAll() {
        return jobAreaRepository.findAll();
    }

}
