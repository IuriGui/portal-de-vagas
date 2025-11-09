package br.csi.oportunidades.model.recruiter;


import br.csi.oportunidades.model.appUser.AppUser;
import br.csi.oportunidades.model.company.Company;
import br.csi.oportunidades.model.company.CompanyRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "recruiter_profile")
@Entity
@NoArgsConstructor
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "job_title", length = 100)
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_role", nullable = false, length = 20)
    private CompanyRoles companyRole = CompanyRoles.MEMBER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RecruiterStatus status = RecruiterStatus.PENDING;


}
