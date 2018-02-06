package br.com.handson.service;

import br.com.handson.entity.CheckListEntity;
import br.com.handson.entity.PairValueEntity;
import br.com.handson.entity.ResultWithContentEntity;
import br.com.handson.repository.CheckListRepository;
import br.com.handson.util.Constants;

import java.util.List;

public class CheckListService {
    public ResultWithContentEntity<CheckListEntity> checkList(){
        CheckListRepository checkListRepository = new CheckListRepository();

        List<PairValueEntity> modalities = checkListRepository.get(Constants.MODALITY);
        List<PairValueEntity> stadiums = checkListRepository.get(Constants.STADIUM);
        List<PairValueEntity> steps = checkListRepository.get(Constants.STEP);
        List<PairValueEntity> federations = checkListRepository.get(Constants.FEDERATION);

        CheckListEntity checkListEntity = new CheckListEntity(modalities, stadiums, steps, federations);

        ResultWithContentEntity<CheckListEntity> resultWithContentEntity = new ResultWithContentEntity<>();
        resultWithContentEntity.setCode(0);
        resultWithContentEntity.setMessage("CheckList de Dados retornados com sucesso");
        resultWithContentEntity.setContent(checkListEntity);

        return resultWithContentEntity;
    }
}
