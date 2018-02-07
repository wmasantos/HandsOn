package br.com.handson.repository;

import br.com.handson.entity.CompetitionEntity;
import br.com.handson.util.H2Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompetitionRepository {
    H2Connection con;

    public CompetitionRepository() {
        con = new H2Connection();
    }

    //Validação para saber se um jogo que será cadastrado não está sendo inserido no mesmo período, no mesmo local, para a
    //mesma modalidade. Ex: Se eu tenho uma partida de futebol que com início às 18:00 e
    //término às 20:00 no Estádio 1, eu não poderia ter outra partida de futebol se iniciando
    //às 19:30 nesse mesmo estádio
    //
    public boolean verifyPlays(CompetitionEntity competitionEntity){
        String sql = "SELECT * FROM JOGO j INNER JOIN MODALIDADE m ON m.ID = j.IDMODALIDADE INNER JOIN ESTADIO e ON " +
                "e.ID = j.IDESTADIO WHERE j.IDESTADIO = ? AND j.IDMODALIDADE = ? AND ((j.DATA_INICIO BETWEEN ? " +
                "AND ?) OR (j.DATA_TERMINO BETWEEN ? AND ?)) " +
                "OR ((? BETWEEN j.DATA_INICIO AND j.DATA_TERMINO) OR (? BETWEEN j.DATA_INICIO AND j.DATA_TERMINO))";

        try {
            PreparedStatement stm = con.getConnection().prepareCall(sql);
            stm.setInt(1, competitionEntity.getStadiumId());
            stm.setInt(2, competitionEntity.getModalityId());
            stm.setDate(3, new Date(competitionEntity.getStartDate().getTime()));
            stm.setDate(4, new Date(competitionEntity.getFinalDate().getTime()));
            stm.setDate(5, new Date(competitionEntity.getStartDate().getTime()));
            stm.setDate(6, new Date(competitionEntity.getFinalDate().getTime()));
            stm.setDate(7, new Date(competitionEntity.getStartDate().getTime()));
            stm.setDate(8, new Date(competitionEntity.getFinalDate().getTime()));

            ResultSet rs = stm.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
    O fluxo de cadastro deve permitir que se forneça o mesmo valor, para os 2 países
    envolvidos na disputa, apenas se a etapa for Final ou Semifinal. Para as demais etapas,
    não se deve permitir que se forneça o mesmo valor.
     */
    public String verifyDistinctPlayers(){
        return null;
    }

    //A competição deve ter a duração de no mínimo 30 minutos.
    public String verifyTime(){
        return null;
    }

    //Para evitar problemas, a organização das olimpíadas que limitar a no máximo 4
    //competições por dia num mesmo local
    public String verifyCount(){
        return null;
    }

    //Para situações de erro, é necessário que a resposta da requisição seja coerente em
    //exibir uma mensagem condizente com o erro.
}
