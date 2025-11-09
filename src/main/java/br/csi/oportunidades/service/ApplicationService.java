package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.application.ApplicationForRecruiterDTO;
import br.csi.oportunidades.dto.application.ApplicationResponseDTO;
import br.csi.oportunidades.dto.application.ApplicationStatusUpdateDTO;
import br.csi.oportunidades.dto.candidate.CandidateSimpleDTO;
import br.csi.oportunidades.model.application.Application;
import br.csi.oportunidades.model.application.ApplicationStatus;
import br.csi.oportunidades.model.candidate.Candidate;
import br.csi.oportunidades.model.opportunity.Opportunity;
import br.csi.oportunidades.repository.ApplicationRepository;
import br.csi.oportunidades.repository.CandidateRepository;
import br.csi.oportunidades.repository.OpportunityRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplicationService {


    private final ApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final OpportunityRepository opportunityRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ApplicationResponseDTO applyToOpportunity(Long opportunityId) {

        Long candidateProfileId = UsuarioAutenticado.getProfileId();
        if (candidateProfileId == null) {
            throw new NoSuchElementException("Usuário não autenticado ou não é um candidato.");
        }

        Candidate candidate = candidateRepository.getReferenceById(candidateProfileId);

        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new NoSuchElementException("Vaga não encontrada."));

        Application newApplication = new Application();
        newApplication.setCandidate(candidate);
        newApplication.setOpportunity(opportunity);
        newApplication.setStatus(ApplicationStatus.APPLIED);

        Application savedApplication = applicationRepository.save(newApplication);

        Application loadedApp = applicationRepository.findById(savedApplication.getId()).get();
        return modelMapper.map(loadedApp, ApplicationResponseDTO.class);
    }

    public List<ApplicationForRecruiterDTO> getApplicationsForOpportunity(Long opportunityId) {

        Long loggedCompanyId = UsuarioAutenticado.getCompanyId();
        if (loggedCompanyId == null) {
            throw new AccessDeniedException("Acesso negado.");
        }

        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new NoSuchElementException("Vaga não encontrada."));

        if (!opportunity.getCompany().getId().equals(loggedCompanyId)) {
            throw new AccessDeniedException("Você não tem permissão para ver as inscrições desta vaga.");
        }

        Set<Application> applications = opportunity.getApplications();

        return applications.stream()
                .map(app -> {
                    ApplicationForRecruiterDTO dto = new ApplicationForRecruiterDTO();
                    dto.setId(app.getId());
                    dto.setStatus(app.getStatus());
                    dto.setAppliedAt(app.getAppliedAt());

                    CandidateSimpleDTO cDto = modelMapper.map(app.getCandidate(), CandidateSimpleDTO.class);
                    dto.setCandidate(cDto);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ApplicationResponseDTO updateApplicationStatus(Long applicationId, ApplicationStatusUpdateDTO dto) {

        Long loggedCompanyId = UsuarioAutenticado.getCompanyId();
        if (loggedCompanyId == null) {
            throw new AccessDeniedException("Acesso negado. ID da empresa não encontrado no token.");
        }

        Application application = applicationRepository.findByIdWithOpportunityAndCompany(applicationId)
                .orElseThrow(() -> new NoSuchElementException("Inscrição não encontrada."));

        if (!application.getOpportunity().getCompany().getId().equals(loggedCompanyId)) {
            throw new AccessDeniedException("Você não tem permissão para alterar o status desta inscrição.");
        }

        application.setStatus(dto.getStatus());
        Application savedApplication = applicationRepository.save(application);

        return modelMapper.map(savedApplication, ApplicationResponseDTO.class);
    }

    public List<ApplicationResponseDTO> getMyApplications() {
        Long candidateProfileId = UsuarioAutenticado.getProfileId();
        if (candidateProfileId == null) {
            throw new NoSuchElementException("Usuário não autenticado ou não é um candidato.");
        }

        List<Application> applications = applicationRepository.findAllByCandidateIdWithDetails(candidateProfileId);

        return applications.stream()
                .map(app -> modelMapper.map(app, ApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void withdrawApplication(Long applicationId) {
        Long candidateProfileId = UsuarioAutenticado.getProfileId();
        if (candidateProfileId == null) {
            throw new NoSuchElementException("Usuário não autenticado.");
        }

        Application application = applicationRepository.findByIdAndCandidateId(applicationId, candidateProfileId)
                .orElseThrow(() -> new NoSuchElementException("Inscrição não encontrada ou não pertence a este usuário."));

        application.setStatus(ApplicationStatus.WITHDRAWN);

        applicationRepository.save(application);
    }

}
