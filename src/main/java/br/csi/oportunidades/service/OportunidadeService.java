package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.OportunidadeRequestDTO;
import br.csi.oportunidades.dto.OportunidadeResponseDT0;
import br.csi.oportunidades.dto.OportunidadeUpdateDTO;
import br.csi.oportunidades.model.Endereco;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.oportunidade.AreaAtuacao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import br.csi.oportunidades.repository.OportunidadeRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OportunidadeService {


    private ModelMapper mp;

    private final InstituicaoService instituicaoService;
    private OportunidadeRepository or;
    private AreaAtuacaoService areaAtuacaoService;


    //LEITURA

    public List<OportunidadeResponseDT0> listarOportunidades(Long id){
        List<OportunidadeResponseDT0> listaOportunidades = new ArrayList<>();

        if(!instituicaoService.existsById(id)){
            throw new NoSuchElementException("Instituicao nao encontrada");
        }
        for(Oportunidade o : or.findAllByInstituicaoId(id)){
            listaOportunidades.add(mp.map(o, OportunidadeResponseDT0.class));
        }
        return  listaOportunidades;
    }

    public OportunidadeResponseDT0 listarOportunidade(Long id){

        Optional<Oportunidade> o = or.findById(id);

        if(o.isPresent()){
            return  mp.map(o.get(), OportunidadeResponseDT0.class);
        } else{
            throw new NoSuchElementException("Oportunidade nao encontrada");
        }


    }

    public Oportunidade recuperarPorId(Long id){

        Optional<Oportunidade> c = or.findById(id);

        if(c.isPresent()){
            return c.get();
        } else{
            throw new NoSuchElementException("Candidato nao encontrada");
        }

    }




    //EDICAO
    public OportunidadeResponseDT0 inserirOportunidade(OportunidadeRequestDTO oportunidade){

        System.out.println("[OportunidadeService.inserirOportunidade] ");
        Oportunidade n = mp.map(oportunidade, Oportunidade.class);



        AreaAtuacao a = areaAtuacaoService.findById(oportunidade.getAreaAtuacao_id());
        n.setAreaAtuacao(a);

        Instituicao in = new Instituicao();
        in.setId(oportunidade.getInstituicao_id());
        n.setInstituicao(in);

        System.out.println("[DADOS DA OPORTUNIDADE MAPEADA]");
        System.out.println(n);

        if(!UsuarioAutenticado.isUsuarioLogado(UsuarioAutenticado.getUserId())){
            System.out.println("[ERRO] USUARIO TENTANDO ALTERAR RECURSO DE OUTRO");
            throw new AuthorizationDeniedException("Voce nao pode modificar este recurso");
        }
        if(!instituicaoService.existsById(n.getInstituicao().getId())){
            throw new NoSuchElementException("Not Found");
        }
        Oportunidade savedOP =  or.save(n);
        return mp.map(savedOP, OportunidadeResponseDT0.class);

    }

    public void deletarOportunidade(Long id) {
        System.out.println("[OportunidadeService.deletarOportunidade]");

        Oportunidade o = or.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Oportunidade com id " + id + " nao encontrada"));

        if (o.getInstituicao().getId().equals(UsuarioAutenticado.getUserId())) {
            or.deleteById(id);
        } else {
            throw new AuthorizationDeniedException("Voce nao pode remover este recurso");
        }
    }

    public OportunidadeResponseDT0 atualizarOportunidade(Long id, OportunidadeUpdateDTO dto){
        System.out.println("[OportunidadeService.atualizarOportunidade] ");

        Oportunidade o = or.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Oportunidade com id " + id + " nao encontrada"));

        if(o.getInstituicao().getId().equals(UsuarioAutenticado.getUserId())) {


            if (dto.getTitulo() != null) o.setTitulo(dto.getTitulo());
            if (dto.getDescricao() != null) o.setDescricao(dto.getDescricao());
            if (dto.getDataPublicacao() != null) o.setDataPublicacao(dto.getDataPublicacao());
            if (dto.getDataValidade() != null) o.setDataValidade(dto.getDataValidade());
            if (dto.getRemoto() != null) o.setRemoto(dto.getRemoto());
            if (dto.getCargaHoraria() != null) o.setCargaHoraria(dto.getCargaHoraria());
            if (dto.getRemuneracao() != null) o.setRemuneracao(dto.getRemuneracao());
            if (dto.getBeneficios() != null) o.setBeneficios(dto.getBeneficios());
            if (dto.getRequisitos() != null) o.setRequisitos(dto.getRequisitos());


            if (dto.getEndereco() != null) {
                Endereco end = o.getEndereco();
                if (end == null) {
                    end = new Endereco();
                }

                if (dto.getEndereco().getCep() != null) end.setCep(dto.getEndereco().getCep());
                if (dto.getEndereco().getRua() != null) end.setRua(dto.getEndereco().getRua());
                if (dto.getEndereco().getNumero() != null) end.setNumero(dto.getEndereco().getNumero());
                if (dto.getEndereco().getBairro() != null) end.setBairro(dto.getEndereco().getBairro());
                if (dto.getEndereco().getCidade() != null) end.setCidade(dto.getEndereco().getCidade());
                if (dto.getEndereco().getUf() != null) end.setUf(dto.getEndereco().getUf());
                if (dto.getEndereco().getComplemento() != null) end.setComplemento(dto.getEndereco().getComplemento());
                if (dto.getEndereco().getLatitude() != null) end.setLatitude(dto.getEndereco().getLatitude());
                if (dto.getEndereco().getLongitude() != null) end.setLongitude(dto.getEndereco().getLongitude());

            }

            Oportunidade savedOp = or.save(o);
            return mp.map(savedOp, OportunidadeResponseDT0.class);

        } else{
            throw new AuthorizationDeniedException("Voce nao pode acessar este recurso");
            //Seria melhor retornar not found? pq assim a pessoa sabe que existe esse registro no banco'
            // e pode imaginar que eh sequencial
        }

    }


    public boolean exists(Long id){
        return or.existsById(id);
    }

}
