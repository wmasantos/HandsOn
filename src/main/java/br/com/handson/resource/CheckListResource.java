package br.com.handson.resource;

import br.com.handson.entity.CheckListEntity;
import br.com.handson.entity.CompetitionEntity;
import br.com.handson.entity.ResultWithContentEntity;
import br.com.handson.repository.CompetitionRepository;
import br.com.handson.service.CheckListService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/checkList")
public class CheckListResource {
    @GetMapping
    public ResultWithContentEntity<CheckListEntity> check(){
        CheckListService checkListService = new CheckListService();

        return checkListService.checkList();
    }

    @PostMapping
    public String test(@RequestBody CompetitionEntity competitionEntity){
        return String.valueOf(new CompetitionRepository().verifyPlays(competitionEntity));
    }
}
