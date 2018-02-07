package br.com.handson.repository;

import br.com.handson.entity.CompetitionEntity;
import br.com.handson.entity.DatasEntity;
import br.com.handson.util.H2Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CompetitionRepository {
    H2Connection con;

    public CompetitionRepository() {
        con = new H2Connection();
    }

    public boolean add(CompetitionEntity competitionEntity){
        String sql = "INSERT INTO JOGO(IDFEDERACAO1, IDFEDERACAO2, IDETAPA, IDESTADIO, IDMODALIDADE, DATA_INICIO, DATA_TERMINO)VALUES(?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement stm = con.getConnection().prepareCall(sql);
            stm.setInt(1, competitionEntity.getFedaration1Id());
            stm.setInt(2, competitionEntity.getFederation2Id());
            stm.setInt(3, competitionEntity.getStepId());
            stm.setInt(4, competitionEntity.getStadiumId());
            stm.setInt(5, competitionEntity.getModalityId());
            stm.setString(6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(competitionEntity.getStartDate().getTime()));
            stm.setString(7,  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(competitionEntity.getFinalDate().getTime()));

            int result = stm.executeUpdate();
            con.getConnection().commit();
            con.closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //Validação para saber se um jogo que será cadastrado não está sendo inserido no mesmo período, no mesmo local, para a
    //mesma modalidade. Ex: Se eu tenho uma partida de futebol que com início às 18:00 e
    //término às 20:00 no Estádio 1, eu não poderia ter outra partida de futebol se iniciando
    //às 19:30 nesse mesmo estádio

    //OK
    public boolean verifyPlays(CompetitionEntity competitionEntity){
        String sql = "SELECT * FROM JOGO j INNER JOIN MODALIDADE m ON m.ID = j.IDMODALIDADE INNER JOIN ESTADIO e ON " +
                "e.ID = j.IDESTADIO WHERE j.IDESTADIO = ? AND j.IDMODALIDADE = ? AND ((j.DATA_INICIO BETWEEN ? " +
                "AND ?) OR (j.DATA_TERMINO BETWEEN ? AND ?)) " +
                "OR ((? BETWEEN j.DATA_INICIO AND j.DATA_TERMINO) OR (? BETWEEN j.DATA_INICIO AND j.DATA_TERMINO))";

        try {
            PreparedStatement stm = con.getConnection().prepareCall(sql);
            stm.setInt(1, competitionEntity.getStadiumId());
            stm.setInt(2, competitionEntity.getModalityId());
            stm.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(competitionEntity.getStartDate()));
            stm.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(competitionEntity.getFinalDate().getTime()));
            stm.setString(5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(competitionEntity.getStartDate().getTime()));
            stm.setString(6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(competitionEntity.getFinalDate().getTime()));
            stm.setString(7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(competitionEntity.getStartDate().getTime()));
            stm.setString(8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(competitionEntity.getFinalDate().getTime()));

            ResultSet rs = stm.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<DatasEntity> getDados(){
        String sql = "SELECT j.DATA_INICIO, j.DATA_TERMINO FROM JOGO j";

        try {
            PreparedStatement stm = con.getConnection().prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            List<DatasEntity> datasEntities = new ArrayList<>();

            while(rs.next())
                datasEntities.add(new DatasEntity(rs.getString(1), rs.getString(2)));

            return datasEntities;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
    public boolean verifyTime(CompetitionEntity competitionEntity){
        Calendar checkAux = Calendar.getInstance();
        checkAux.setTime(competitionEntity.getStartDate());

        checkAux.add(Calendar.MINUTE, 30);

        return competitionEntity.getFinalDate().after(checkAux.getTime());
    }

    //Para evitar problemas, a organização das olimpíadas que limitar a no máximo 4
    //competições por dia num mesmo local
    public String verifyCount(){
        return null;
    }

    //Para situações de erro, é necessário que a resposta da requisição seja coerente em
    //exibir uma mensagem condizente com o erro.
}
