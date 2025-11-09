package br.csi.oportunidades.service;

import br.csi.oportunidades.dto.*;
import br.csi.oportunidades.dto.opportunity.OpportunityRequestDTO;
import br.csi.oportunidades.dto.opportunity.OpportunityResponseDTO;
import br.csi.oportunidades.dto.recruiter.CompanySimpleDTO;
import br.csi.oportunidades.dto.recruiter.RecruiterSimpleDTO;
import br.csi.oportunidades.model.Address;
import br.csi.oportunidades.model.company.Company;
import br.csi.oportunidades.model.opportunity.JobArea;
import br.csi.oportunidades.model.opportunity.Opportunity;
import br.csi.oportunidades.model.recruiter.Recruiter;
import br.csi.oportunidades.repository.CompanyRepository;
import br.csi.oportunidades.repository.JobAreaRepository;
import br.csi.oportunidades.repository.OpportunityRepository;
import br.csi.oportunidades.repository.RecruiterRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OpportunityService {


    private final RecruiterRepository recruiterRepository;
    private final CompanyRepository companyRepository;
    private final OpportunityRepository opportunityRepository;
    private final JobAreaRepository jobAreaRepository;
    private final ModelMapper modelMapper;

    private Recruiter getLoggedRecruiter() {
        Long profileId = UsuarioAutenticado.getProfileId();
        if (profileId == null) {
            throw new AccessDeniedException("Acesso negado. Perfil de recrutador não encontrado no token.");
        }
        return recruiterRepository.findById(profileId)
                .orElseThrow(() -> new NoSuchElementException("Recrutador não encontrado no banco."));
    }

    private Company getLoggedCompany() {
        Long companyId = UsuarioAutenticado.getCompanyId();
        if (companyId == null) {
            throw new AccessDeniedException("Acesso negado. ID da empresa não encontrado no token.");
        }
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada no banco."));
    }

    @Transactional
    public OpportunityResponseDTO createOpportunity(OpportunityRequestDTO dto) {

        Recruiter loggedRecruiter = getLoggedRecruiter();
        Company loggedCompany = getLoggedCompany();

        JobArea jobArea = jobAreaRepository.findById(dto.getJobAreaId())
                .orElseThrow(() -> new NoSuchElementException("Área de Atuação não encontrada."));

        if (!dto.getIsRemote() && dto.getAddress() == null) {
            throw new IllegalArgumentException("Vagas presenciais devem conter um endereço.");
        }

        Opportunity opportunity = new Opportunity();
        opportunity.setTitle(dto.getTitle());
        opportunity.setDescription(dto.getDescription());
        opportunity.setExpiresAt(dto.getExpiresAt());
        opportunity.setRemote(dto.getIsRemote());
        opportunity.setWorkloadHours(dto.getWorkloadHours());
        opportunity.setSalary(dto.getSalary());
        opportunity.setBenefits(dto.getBenefits());
        opportunity.setRequirements(dto.getRequirements());

        opportunity.setRecruiter(loggedRecruiter);
        opportunity.setCompany(loggedCompany);
        opportunity.setJobArea(jobArea);
        opportunity.setPublishedAt(LocalDateTime.now());

        if (dto.getAddress() != null) {
            Address ad = new Address();
            ad.setZipCode(dto.getAddress().getZipCode());
            ad.setStateCode(dto.getAddress().getStateCode());
            ad.setCity(dto.getAddress().getCity());
            ad.setDistrict(dto.getAddress().getDistrict());
            ad.setStreet(dto.getAddress().getStreet());
            ad.setNumber(dto.getAddress().getNumber());
            ad.setComplement(dto.getAddress().getComplement());

            opportunity.setAddress(ad);
        }

        Opportunity savedOpportunity = opportunityRepository.save(opportunity);

        OpportunityResponseDTO dtoResponse = modelMapper.map(savedOpportunity, OpportunityResponseDTO.class);

        dtoResponse.setRecruiter(modelMapper.map(loggedRecruiter, RecruiterSimpleDTO.class));


        dtoResponse.setCompany(modelMapper.map(loggedCompany, CompanySimpleDTO.class));


        return dtoResponse;
    }


    @Transactional
    public OpportunityResponseDTO updateOpportunity(Long opportunityId, OpportunityRequestDTO dto) {

        Long loggedCompanyId = UsuarioAutenticado.getCompanyId();
        Recruiter loggedRecruiter = getLoggedRecruiter();
        Company loggedCompany = getLoggedCompany();

        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new NoSuchElementException("Vaga (ID: " + opportunityId + ") não encontrada."));

        if (!opportunity.getCompany().getId().equals(loggedCompanyId)) {
            throw new AccessDeniedException("Você não tem permissão para editar a vaga de outra empresa.");
        }

        JobArea jobArea = opportunity.getJobArea();
        if (dto.getJobAreaId() != null && !opportunity.getJobArea().getId().equals(dto.getJobAreaId())) {
            jobArea = jobAreaRepository.findById(dto.getJobAreaId())
                    .orElseThrow(() -> new NoSuchElementException("Área de Atuação (ID: " + dto.getJobAreaId() + ") não encontrada."));
            opportunity.setJobArea(jobArea);
        }


        if(dto.getTitle() != null) opportunity.setTitle(dto.getTitle());
        if(dto.getDescription() != null) opportunity.setDescription(dto.getDescription());
        if(dto.getExpiresAt() != null) opportunity.setExpiresAt(dto.getExpiresAt());
        if(dto.getIsRemote() != null) opportunity.setRemote(dto.getIsRemote());
        if(dto.getWorkloadHours() != null) opportunity.setWorkloadHours(dto.getWorkloadHours());

        opportunity.setSalary(dto.getSalary());
        opportunity.setBenefits(dto.getBenefits());
        opportunity.setRequirements(dto.getRequirements());

        if (dto.getAddress() != null) {
            if (opportunity.getAddress() == null) {
                opportunity.setAddress(new Address());
            }
            Address ad = opportunity.getAddress();
            ad.setZipCode(dto.getAddress().getZipCode());
            ad.setStateCode(dto.getAddress().getStateCode());
            ad.setCity(dto.getAddress().getCity());
            ad.setDistrict(dto.getAddress().getDistrict());
            ad.setStreet(dto.getAddress().getStreet());
            ad.setNumber(dto.getAddress().getNumber());
            ad.setComplement(dto.getAddress().getComplement());
        } else {
            opportunity.setAddress(null);
        }

        Opportunity updatedOpportunity = opportunityRepository.save(opportunity);

        OpportunityResponseDTO dtoResponse = modelMapper.map(updatedOpportunity, OpportunityResponseDTO.class);

        dtoResponse.setJobArea(modelMapper.map(jobArea, AreaAtuacaoResponseDTO.class));

        dtoResponse.setRecruiter(modelMapper.map(loggedRecruiter, RecruiterSimpleDTO.class));
        dtoResponse.setCompany(modelMapper.map(loggedCompany, CompanySimpleDTO.class));

        return dtoResponse;
    }


    public void deleteOpportunity(Long opportunityId) {
        Long loggedCompanyId = UsuarioAutenticado.getCompanyId();

        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new NoSuchElementException("Vaga (ID: " + opportunityId + ") não encontrada."));

        if (!opportunity.getCompany().getId().equals(loggedCompanyId)) {
            throw new AccessDeniedException("Você não tem permissão para deletar a vaga de outra empresa.");
        }

        opportunityRepository.delete(opportunity);
    }


    public List<OpportunityResponseDTO> getMyCompanyOpportunities() {
        Long loggedCompanyId = UsuarioAutenticado.getCompanyId();

        List<Opportunity> opportunities = opportunityRepository.findByCompanyId(loggedCompanyId);

        return opportunities.stream()
                .map(opp -> modelMapper.map(opp, OpportunityResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<OpportunityResponseDTO> getAll() {
        List<Opportunity> opportunities = opportunityRepository.findAll();
        List<OpportunityResponseDTO> opportunitiesResponse = new ArrayList<>();
        for (Opportunity opportunity : opportunities) {
            opportunitiesResponse.add(modelMapper.map(opportunity, OpportunityResponseDTO.class));
        }
        return opportunitiesResponse;
    }

    public OpportunityResponseDTO getOpportunity(Long opportunityId) {
        Opportunity o = opportunityRepository.findById(opportunityId).orElseThrow(() -> new NoSuchElementException("Oportunidade não existe"));
        return modelMapper.map(o, OpportunityResponseDTO.class);
    }

}
