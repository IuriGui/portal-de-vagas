package br.csi.oportunidades.service;

import br.csi.oportunidades.dto.usuario.UserCandidateRequestDTO;
import br.csi.oportunidades.dto.usuario.UserCreateRequest;
import br.csi.oportunidades.dto.usuario.UserRecruiterRequestDTO;
import br.csi.oportunidades.model.appUser.AppUser;
import br.csi.oportunidades.model.appUser.UserRoles;
import br.csi.oportunidades.model.candidate.Candidate;
import br.csi.oportunidades.model.company.Company;
import br.csi.oportunidades.model.company.CompanyRoles;
import br.csi.oportunidades.model.recruiter.Recruiter;
import br.csi.oportunidades.model.recruiter.RecruiterStatus;
import br.csi.oportunidades.repository.CompanyRepository;
import br.csi.oportunidades.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UsersRepository usersRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AppUser registerCandidate(UserCandidateRequestDTO request) {

        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRoles.CANDIDATE);

        Candidate candidate = new Candidate();
        candidate.setFullName(request.getCandidateFullName());
        candidate.setPhone(request.getCandidatePhone());
        candidate.setBirthDate(request.getBirthDate());
        candidate.setResumeUrl(request.getResumeUrl());

        candidate.setUser(user);
        user.setCandidateProfile(candidate);

        return usersRepository.save(user);
    }

    @Transactional
    public AppUser registerRecruiter(UserRecruiterRequestDTO request) {

        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRoles.RECRUITER);

        Company company = new Company();
        company.setCompanyName(request.getCompanyName());
        company.setPhone(request.getCompanyPhone());
        company.setDescription(request.getCompanyDescription());
        Company savedCompany = companyRepository.save(company);

        Recruiter recruiter = new Recruiter();
        recruiter.setFullName(request.getRecruiterFullName());
        recruiter.setCompanyRole(CompanyRoles.ADMIN);
        recruiter.setStatus(RecruiterStatus.ACTIVE);

        recruiter.setUser(user);
        recruiter.setCompany(savedCompany);
        user.setRecruiterProfile(recruiter);

        return usersRepository.save(user);
    }

    @Transactional
    public AppUser register(UserCreateRequest request) {

        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));


        if (request.getRole() == UserRoles.CANDIDATE) {

            user.setRole(UserRoles.CANDIDATE);

            Candidate candidate = new Candidate();
            candidate.setFullName(request.getCandidateFullName());
            candidate.setPhone(request.getCandidatePhone());
            candidate.setBirthDate(request.getBirthDate());
            candidate.setResumeUrl(request.getResumeUrl());




            candidate.setUser(user);
            user.setCandidateProfile(candidate);

        } else if (request.getRole() == UserRoles.RECRUITER) {


            user.setRole(UserRoles.RECRUITER);

            Company company = new Company();
            company.setCompanyName(request.getCompanyName());
            company.setPhone(request.getCompanyPhone());
            company.setDescription(request.getCompanyDescription());

            Company savedCompany = companyRepository.save(company);


            Recruiter recruiter = new Recruiter();
            recruiter.setFullName(request.getRecruiterFullName());
            recruiter.setCompanyRole(CompanyRoles.ADMIN);
            recruiter.setStatus(RecruiterStatus.ACTIVE);
            recruiter.setUser(user);
            recruiter.setCompany(savedCompany);
            user.setRecruiterProfile(recruiter);

        } else {

            throw new IllegalArgumentException("Role de usuário inválida: " + request.getRole());
        }



        return usersRepository.save(user);
    }


}