package br.com.handson.repository;

import br.com.handson.entity.CompetitionEntity;
import br.com.handson.entity.CompetitionResultEntity;
import br.com.handson.util.H2Connection;

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

    public boolean add(CompetitionEntity competitionEntity) throws SQLException {
        String sql = "INSERT INTO JOGO(IDFEDERACAO1, IDFEDERACAO2, IDETAPA, IDESTADIO, IDMODALIDADE, DATA_INICIO, DATA_TERMINO)VALUES(?, ?, ?, ?, ?, ?, ?);";

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

        return result >= 1;
    }

    //Validação para saber se um jogo que será cadastrado não está sendo inserido no mesmo período, no mesmo local, para a
    //mesma modalidade. Ex: Se eu tenho uma partida de futebol que com início às 18:00 e
    //término às 20:00 no Estádio 1, eu não poderia ter outra partida de futebol se iniciando
    //às 19:30 nesse mesmo estádio

    //OK
    public boolean verifyPlays(CompetitionEntity competitionEntity) throws SQLException {
        String sql = "SELECT * FROM JOGO j INNER JOIN MODALIDADE m ON m.ID = j.IDMODALIDADE INNER JOIN ESTADIO e ON " +
                "e.ID = j.IDESTADIO WHERE j.IDESTADIO = ? AND j.IDMODALIDADE = ? AND (((j.DATA_INICIO BETWEEN ? " +
                "AND ?) OR (j.DATA_TERMINO BETWEEN ? AND ?)) " +
                "OR ((? BETWEEN j.DATA_INICIO AND j.DATA_TERMINO) OR (? BETWEEN j.DATA_INICIO AND j.DATA_TERMINO)))";

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
        //con.closeConnection();
        return !rs.next();

    }

    /*
    O fluxo de cadastro deve permitir que se forneça o mesmo valor, para os 2 países
    envolvidos na disputa, apenas se a etapa for Final ou Semifinal. Para as demais etapas,
    não se deve permitir que se forneça o mesmo valor.
     */
    public int verifyDistinctPlayers(CompetitionEntity competitionEntity) throws SQLException {
        String sql = "SELECT COUNT(1) FROM JOGO j WHERE ((j.IDFEDERACAO1 = ? AND j.IDFEDERACAO2 = ?) OR (j.IDFEDERACAO1 = ? AND j.IDFEDERACAO2 = ?)) AND j.IDETAPA = ? AND j.IDMODALIDADE = ?;";

        PreparedStatement stm = con.getConnection().prepareCall(sql);
        stm.setInt(1, competitionEntity.getFedaration1Id());
        stm.setInt(2, competitionEntity.getFederation2Id());
        stm.setInt(3, competitionEntity.getFederation2Id());
        stm.setInt(4, competitionEntity.getFedaration1Id());
        stm.setInt(5, competitionEntity.getStepId());
        stm.setInt(6, competitionEntity.getModalityId());

        ResultSet rs = stm.executeQuery();
        //con.closeConnection();

        return rs.next() ? rs.getInt(1) : 0;
    }

    //A competição deve ter a duração de no mínimo 30 minutos.
    public boolean verifyTime(CompetitionEntity competitionEntity){
        Calendar checkAux = Calendar.getInstance();
        checkAux.setTime(competitionEntity.getStartDate());

        checkAux.add(Calendar.MINUTE, 29);
        checkAux.add(Calendar.SECOND, 59);

        return competitionEntity.getFinalDate().after(checkAux.getTime());
    }

    //Para evitar problemas, a organização das olimpíadas que limitar a no máximo 4
    //competições por dia num mesmo local
    public int verifyCount(CompetitionEntity competitionEntity) throws SQLException {
        String sql = "SELECT COUNT(1) FROM JOGO j INNER JOIN ESTADIO e ON e.ID = j.IDESTADIO WHERE FORMATDATETIME(j.DATA_INICIO, 'yyyy-MM-dd') = FORMATDATETIME(?, 'yyyy-MM-dd') AND j.IDESTADIO = ?";

        PreparedStatement stm = con.getConnection().prepareCall(sql);
        stm.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(competitionEntity.getStartDate()));
        stm.setInt(2, competitionEntity.getStadiumId());

        ResultSet rs = stm.executeQuery();

        //con.closeConnection();

        return rs.next() ? rs.getInt(1) : 0;
    }

    //Para situações de erro, é necessário que a resposta da requisição seja coerente em
    //exibir uma mensagem condizente com o erro.

    public List<CompetitionResultEntity> get (String modalidade){
        List<CompetitionResultEntity> competitionResultEntities = null;
        StringBuffer sql = new StringBuffer("SELECT j.ID, f1.NOME AS TIME_A, f2.NOME AS TIME_B, e.NOME AS ETAPA, es.NOME AS ESTADIO, m.NOME AS MODALIDADE, j.DATA_INICIO, j.DATA_TERMINO FROM JOGO j INNER JOIN FEDERACAO f1 ON f1.ID = j.IDFEDERACAO1 INNER JOIN FEDERACAO f2 ON f2.ID = j.IDFEDERACAO2 INNER JOIN MODALIDADE m ON m.ID = j.IDMODALIDADE INNER JOIN ETAPA e ON e.ID = j.IDETAPA INNER JOIN ESTADIO es ON es.ID = j.IDESTADIO");

        if(!modalidade.isEmpty())
            sql.append(" WHERE j.IDMODALIDADE = ?");

        sql.append(" ORDER BY j.DATA_INICIO");

        try {
            PreparedStatement stm = con.getConnection().prepareCall(sql.toString());
            if(!modalidade.isEmpty())
                stm.setString(1, modalidade);

            ResultSet rs = stm.executeQuery();

            competitionResultEntities = new ArrayList<>();

            while(rs.next()) {
                competitionResultEntities.add(
                        new CompetitionResultEntity(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return competitionResultEntities;
    }
}
