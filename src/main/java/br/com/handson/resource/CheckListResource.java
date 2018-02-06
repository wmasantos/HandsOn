package br.com.handson.resource;

import br.com.handson.entity.CheckListEntity;
import br.com.handson.entity.ResultWithContentEntity;
import br.com.handson.service.CheckListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/checkList")
public class CheckListResource {
    @GetMapping
    public ResultWithContentEntity<CheckListEntity> check(){
        CheckListService checkListService = new CheckListService();

        return checkListService.checkList();
    }
}
