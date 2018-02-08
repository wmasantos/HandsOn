package br.com.handson.service;

import br.com.handson.entity.CompetitionEntity;
import br.com.handson.entity.ResultEntity;
import br.com.handson.repository.CompetitionRepository;

import java.sql.SQLException;

public class CompetitionService {
    public ResultEntity validadeCompetition(CompetitionEntity competitionEntity){
        ResultEntity resultEntity = new ResultEntity();

        //VERIFICA SE AS FEDERACOES PASSADAS SAO IGUAIS
        if(competitionEntity.getFedaration1Id() == competitionEntity.getFederation2Id())
            resultEntity.getErrors().add("É permitido apenas jogos entre times diferentes.");

        try {
            //VERIFICA SE CADA DUPLA DE PAÍSES ESTÃO JOGANDO PELAS SEMIFINAIS OU FINAIS
            int res4 = new CompetitionRepository().verifyDistinctPlayers(competitionEntity);

            //VERIFICA SE É UMA COMPETIÇÃO NA FINAL OU SEMIFINAL
            if(competitionEntity.getStepId() == 4 || competitionEntity.getStepId() == 5){
                if(res4 >= 2)
                    resultEntity.getErrors().add("Apenas 2 entradas iguais para finais ou semifinais.");
            }
            else{
                if(res4 >= 1)
                    resultEntity.getErrors().add("Apenas 1 entrada para jogos que não sejam finais ou semifinais.");
            }

            //FALSE = JÁ EXISTE UMA PARTIDA OCORRENDO NO MESMO LOCAL, HORARIO E MODALIDADE, TRUE, PODE CADASTRAR
            boolean rn2 = new CompetitionRepository().verifyPlays(competitionEntity);
            if(!rn2)
                resultEntity.getErrors().add("Já existe uma competição acontecendo neste horário e/ou local informado.");

            //FALSE = A PARTIDA TEM MENOS DE 30 MINUTOS, TRUE, A PARTIDA TEM 30 OU MAIS.
            boolean res2 = new CompetitionRepository().verifyTime(competitionEntity);
            if(!res2)
                resultEntity.getErrors().add("Cada competição deve ter 30 minutos ou mais de duração.");

            //EXISTE 4 EVENTOS NO MESMO DIA OU PODE CADASTRAR A COMPETIÇÃO.
            int res3 = new CompetitionRepository().verifyCount(competitionEntity);
            if(res3 >= 4)
                resultEntity.getErrors().add("Já existem 4 competições agendadas para o dia e local informado.");

            if(resultEntity.getErrors().isEmpty()){
                //FALSE DEU ERRADO O CADASTRO, TRUE, DEU CERTO
                boolean res = new CompetitionRepository().add(competitionEntity);
                if(!res)
                    resultEntity.getErrors().add("Erro ao cadastrar competição.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultEntity.getErrors().add("Erro - " + e.getMessage());
        } finally {
            if(resultEntity.getErrors().isEmpty()) {
                resultEntity.setCode(0);
                resultEntity.setMessage("Competição cadastrada com sucesso.");
            }
            else{
                resultEntity.setCode(1);
                resultEntity.setMessage("Erro ao cadastrar competição, confira a lista de erros para maiores informações.");
            }

            return resultEntity;
        }
    }
}
