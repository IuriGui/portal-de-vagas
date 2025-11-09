package br.csi.oportunidades.controller;


import br.csi.oportunidades.model.opportunity.JobArea;
import br.csi.oportunidades.service.JobAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/job-areas")
@AllArgsConstructor
public class JobAreaController {


    private final JobAreaService jobAreaService;


    @GetMapping
    @Operation(
            summary = "Listar áreas de atuação",
            description = "Retorna a lista de áreas de atuação"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = JobArea.class))))
    })
    public List<JobArea> getAll() {
        return jobAreaService.findAll();
    }

}
