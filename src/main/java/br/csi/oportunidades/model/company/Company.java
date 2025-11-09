package br.csi.oportunidades.model.company;


import br.csi.oportunidades.model.Address;
import br.csi.oportunidades.model.opportunity.Opportunity;
import br.csi.oportunidades.model.recruiter.Recruiter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "company")
@Entity
@NoArgsConstructor
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "phone", length = 15)
    private String phone;



    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "main_address_id", referencedColumnName = "id")
    private Address mainAddress;

    @OneToMany(
            mappedBy = "company",
            fetch = FetchType.LAZY
    )
    private Set<Recruiter> recruiters = new HashSet<>();

    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Opportunity> opportunities = new HashSet<>();

}
