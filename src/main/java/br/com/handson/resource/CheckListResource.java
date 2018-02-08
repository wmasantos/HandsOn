package br.com.handson.resource;

import br.com.handson.entity.*;
import br.com.handson.repository.CompetitionRepository;
import br.com.handson.service.CheckListService;
import br.com.handson.service.CompetitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/checkList")
public class CheckListResource {
    @GetMapping
    public ResultWithContentEntity<CheckListEntity> check(){
        CheckListService checkListService = new CheckListService();

        return checkListService.checkList();
    }
}
