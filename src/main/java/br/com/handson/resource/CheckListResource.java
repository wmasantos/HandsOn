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
        //PRIMEIRA REGRA - verifyDistinctPlayers pois preciso verificar de os 2 players informados para o jogo estão jogando pela final ou semifinal

        //FALSE = JÁ EXISTE UMA PARTIDA OCORRENDO NO MESMO LOCAL, HORARIO E MODALIDADE, TRUE, PODE CADASTRAR
        boolean teste = new CompetitionRepository().verifyPlays(competitionEntity);

        //FALSE DEU ERRADO O CADASTRO, TRUE, DEU CERTO
        boolean res = new CompetitionRepository().add(competitionEntity);

        //FALSE = A PARTIDA TEM MENOS DE 30 MINUTOS, TRUE, A PARTIDA TEM 30 OU MAIS.
        boolean res2 = new CompetitionRepository().verifyTime(competitionEntity);

        //FALSE = EXISTE 4 EVENTOS NO MESMO DIA, TRUE, PODE CADASTRAR A COMPETIÇÃO.
        boolean res3 = new CompetitionRepository().verifyCount(competitionEntity);
        return "";
    }
}
