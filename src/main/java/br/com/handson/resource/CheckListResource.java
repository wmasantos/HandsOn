package br.com.handson.resource;

import br.com.handson.entity.CheckListEntity;
import br.com.handson.entity.CompetitionEntity;
import br.com.handson.entity.DatasEntity;
import br.com.handson.entity.ResultWithContentEntity;
import br.com.handson.repository.CompetitionRepository;
import br.com.handson.service.CheckListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/checkList")
public class CheckListResource {
    /*@GetMapping
    public ResultWithContentEntity<CheckListEntity> check(){
        CheckListService checkListService = new CheckListService();

        return checkListService.checkList();
    }*/

    @GetMapping
    public List<DatasEntity> getDados(){
        return new CompetitionRepository().getDados();
    }

    @PostMapping
    public String test(@RequestBody CompetitionEntity competitionEntity){
        boolean teste = new CompetitionRepository().verifyPlays(competitionEntity);
        boolean res = new CompetitionRepository().add(competitionEntity);
        boolean res2 = new CompetitionRepository().verifyTime(competitionEntity);
        return "";
    }
}
