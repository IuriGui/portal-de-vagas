package br.csi.oportunidades.model;


import jakarta.persistence.*;

@Entity
@Table(name = "area_atuacao")
public class AreaAtuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String area;
}
