package br.com.handson.resource;

import br.com.handson.entity.CompetitionEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/competition")
public class TokyoResource {

    @PostMapping
    public CompetitionEntity add(@RequestBody CompetitionEntity competitionEntity){
        return competitionEntity;
    }
}
