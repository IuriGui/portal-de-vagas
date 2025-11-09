package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.candidate.AcademicBackgroundDTO;
import br.csi.oportunidades.dto.candidate.AcademicBackgroundUpdateDTO;
import br.csi.oportunidades.dto.candidate.ProfessionalExperienceDTO;
import br.csi.oportunidades.dto.candidate.ProfessionalExperienceUpdateDTO;
import br.csi.oportunidades.dto.candidate.CandidateProfileDTO;
import br.csi.oportunidades.dto.candidate.CandidateUpdateDTO;
import br.csi.oportunidades.model.Address;
import br.csi.oportunidades.model.Skill;
import br.csi.oportunidades.model.candidate.AcademicBackground;
import br.csi.oportunidades.model.candidate.Candidate;
import br.csi.oportunidades.model.candidate.ProfessionalExperience;
import br.csi.oportunidades.repository.AcademicBackgroundRepository;
import br.csi.oportunidades.repository.CandidateRepository;
import br.csi.oportunidades.repository.ProfessionalExperienceRepository;
import br.csi.oportunidades.repository.SkillRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@AllArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final ModelMapper modelMapper;
    private final SkillRepository skillRepository;
    private final ProfessionalExperienceRepository professionalExperienceRepository;
    private final AcademicBackgroundRepository academicBackgroundRepository;

    public CandidateProfileDTO getMyProfile() {
        Long profileId = UsuarioAutenticado.getProfileId();

        if (profileId == null) {
            throw new NoSuchElementException("Usuário não encontrado ou não é um candidato.");
        }

        Candidate candidate = candidateRepository.findProfileById(profileId)
                .orElseThrow(() -> new NoSuchElementException("Perfil de candidato não encontrado"));

        return modelMapper.map(candidate, CandidateProfileDTO.class);
    }


    @Transactional
    public CandidateProfileDTO updateMyProfile(CandidateUpdateDTO dto) {

        Long profileId = UsuarioAutenticado.getProfileId();
        if (profileId == null) {
            throw new NoSuchElementException("Usuário não autenticado.");
        }

        if (isDtoEmpty(dto)) {
            throw new IllegalArgumentException("Você deve fornecer pelo menos um campo para atualizar.");
        }


        Candidate candidate = candidateRepository.findById(profileId)
                .orElseThrow(() -> new NoSuchElementException("Perfil de candidato não encontrado"));

        if (dto.getFullName() != null) {
            candidate.setFullName(dto.getFullName());
        }
        if (dto.getPhone() != null) {
            candidate.setPhone(dto.getPhone());
        }
        if (dto.getBirthDate() != null) {
            candidate.setBirthDate(dto.getBirthDate());
        }
        if (dto.getResumeUrl() != null) {
            candidate.setResumeUrl(dto.getResumeUrl());
        }

        if (dto.getAddress() != null) {
            if (candidate.getAddress() == null) {
                candidate.setAddress(new Address());
            }

            Address ad = candidate.getAddress();
            ad.setZipCode(dto.getAddress().getZipCode());
            ad.setStateCode(dto.getAddress().getStateCode());
            ad.setCity(dto.getAddress().getCity());
            ad.setDistrict(dto.getAddress().getDistrict());
            ad.setStreet(dto.getAddress().getStreet());
            ad.setNumber(dto.getAddress().getNumber());
            ad.setComplement(dto.getAddress().getComplement());
        }

        if (dto.getSkillIds() != null) {
            Set<Skill> novasHabilidades = new HashSet<>(
                    skillRepository.findAllById(dto.getSkillIds())
            );
            candidate.setSkills(novasHabilidades);
        }

        Candidate savedCandidate = candidateRepository.save(candidate);

        return modelMapper.map(savedCandidate, CandidateProfileDTO.class);
    }


    @Transactional
    public ProfessionalExperience addExperienceToMe(ProfessionalExperienceDTO dto) {

        Long profileId = UsuarioAutenticado.getProfileId();
        Candidate candidate = candidateRepository.findById(profileId)
                .orElseThrow(() -> new NoSuchElementException("Candidato não encontrado"));

        ProfessionalExperience newExperience = new ProfessionalExperience();
        newExperience.setJobTitle(dto.getJobTitle());
        newExperience.setCompanyName(dto.getCompanyName());
        newExperience.setStartDate(dto.getStartDate());
        newExperience.setEndDate(dto.getEndDate());

        newExperience.setCandidate(candidate);
        return professionalExperienceRepository.save(newExperience);
    }




    private boolean isDtoEmpty(CandidateUpdateDTO dto) {
        return dto.getFullName() == null &&
                dto.getPhone() == null &&
                dto.getBirthDate() == null &&
                dto.getResumeUrl() == null &&
                dto.getAddress() == null &&
                (dto.getSkillIds() == null || dto.getSkillIds().isEmpty());
    }

    @Transactional
    public ProfessionalExperienceDTO updateMyExperience(ProfessionalExperienceUpdateDTO dto, Long experienceId) {

        Long profileId = UsuarioAutenticado.getProfileId();
        if (profileId == null) {
            throw new NoSuchElementException("Usuário não autenticado.");
        }

        ProfessionalExperience experience = professionalExperienceRepository.findById(experienceId)
                .orElseThrow(() -> new NoSuchElementException("Experiência profissional não encontrada."));

        if (!experience.getCandidate().getId().equals(profileId)) {
            throw new AccessDeniedException("Você não tem permissão para editar esta experiência.");
        }


        if (dto.getJobTitle() != null) {
            experience.setJobTitle(dto.getJobTitle());
        }

        if (dto.getCompanyName() != null) {
            experience.setCompanyName(dto.getCompanyName());
        }

        if (dto.getStartDate() != null) {
            experience.setStartDate(dto.getStartDate());
        }

        if (dto.getEndDate() != null) {
            experience.setEndDate(dto.getEndDate());
        }

        return modelMapper.map(professionalExperienceRepository.save(experience), ProfessionalExperienceDTO.class);
    }


    public void deleteMyExperience(Long experienceId) {
        Long profileId = UsuarioAutenticado.getProfileId();
        ProfessionalExperience experience = professionalExperienceRepository
                .findById(experienceId)
                .orElseThrow(() -> new NoSuchElementException("Experiência profissional não encontrada."));

        if (!experience.getCandidate().getId().equals(profileId)) {
            throw new AccessDeniedException("Você não tem permissão para editar esta experiência.");
        }

        professionalExperienceRepository.delete(experience);
    }

    public AcademicBackground addAcademicBackground(AcademicBackgroundDTO dto) {

        Long profileId = UsuarioAutenticado.getProfileId();
        Candidate candidate = candidateRepository.findById(profileId)
                .orElseThrow(() -> new NoSuchElementException("Candidato não encontrado"));

        if (dto.getEndDate() != null && dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("A data de término não pode ser anterior à data de início.");
        }

        AcademicBackground academic = new AcademicBackground();
        academic.setInstitutionName(dto.getInstitutionName());
        academic.setCourseName(dto.getCourseName());
        academic.setStartDate(dto.getStartDate());
        academic.setEndDate(dto.getEndDate());
        academic.setCandidate(candidate);
        return academicBackgroundRepository.save(academic);




    }

    public AcademicBackgroundDTO updateMyAcademicBackground(AcademicBackgroundUpdateDTO dto, Long acBk) {

        Long profileId = UsuarioAutenticado.getProfileId();
        if (profileId == null) {
            throw new NoSuchElementException("Usuário não autenticado.");
        }

        AcademicBackground academicBackground = academicBackgroundRepository.findById(acBk)
                .orElseThrow(() -> new NoSuchElementException("Formação acadêmica não encontrada"));

        if (!academicBackground.getCandidate().getId().equals(profileId)) {
            throw new AccessDeniedException("Você não tem permissão para editar esta formação.");
        }

        if (dto.getEndDate() != null && dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("A data de término não pode ser anterior à data de início.");
        }

        if(dto.getCourseName() != null) {
            academicBackground.setCourseName(dto.getCourseName());
        }
        if(dto.getInstitutionName() != null) {
            academicBackground.setInstitutionName(dto.getInstitutionName());
        }
        if(dto.getStartDate() != null) {
            academicBackground.setStartDate(dto.getStartDate());
        }
        if(dto.getEndDate() != null) {
            academicBackground.setEndDate(dto.getEndDate());
        }

        AcademicBackground savedEntity = academicBackgroundRepository.save(academicBackground);

        return modelMapper.map(savedEntity, AcademicBackgroundDTO.class);
    }

    public void deleteMyAcademicBackground(Long acBk) {
        Long profileId = UsuarioAutenticado.getProfileId();
        AcademicBackground academicBackground = academicBackgroundRepository
                .findById(acBk)
                .orElseThrow(() -> new NoSuchElementException("Formação não encontrada."));

        if (!academicBackground.getCandidate().getId().equals(profileId)) {
            throw new AccessDeniedException("Você não tem permissão para editar esta experiência.");
        }

        academicBackgroundRepository.delete(academicBackground);
    }
}
