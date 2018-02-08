package br.com.handson.resource;

import br.com.handson.entity.CompetitionEntity;
import br.com.handson.entity.CompetitionResultEntity;
import br.com.handson.entity.ResultEntity;
import br.com.handson.entity.ResultWithContentArrayEntity;
import br.com.handson.repository.CompetitionRepository;
import br.com.handson.service.CompetitionService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competition")
public class CompetitionResource {

    @PostMapping
    public ResponseEntity<ResultEntity> add(@RequestBody CompetitionEntity competitionEntity){
        CompetitionService competitionService = new CompetitionService();
        ResultEntity resultEntity = competitionService.validadeCompetition(competitionEntity);
        if(resultEntity.getCode() == 0)
            return ResponseEntity.status(HttpStatus.OK).body(resultEntity);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultEntity);
    }

    @GetMapping
    public ResponseEntity<ResultWithContentArrayEntity<CompetitionResultEntity>> get(@RequestParam(value = "modality", defaultValue = "") String modality){
        CompetitionRepository competitionRepository = new CompetitionRepository();

        ResultWithContentArrayEntity<CompetitionResultEntity> result = new ResultWithContentArrayEntity<>();

        List<CompetitionResultEntity> competitionResultEntityList = competitionRepository.get(modality);

        if(competitionResultEntityList == null){
            result.setCode(1);
            result.setMessage("Erro ao listar dados de competições.");
            return ResponseEntity.status(400).body(result);
        }
        else {
            result.setCode(0);
            result.setMessage("Dados de competição listados com sucesso.");
            result.setContent(competitionResultEntityList);
            return ResponseEntity.status(200).body(result);
        }
    }
}
