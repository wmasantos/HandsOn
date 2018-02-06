package br.com.handson.repository;

import br.com.handson.entity.CheckListEntity;
import br.com.handson.entity.PairValueEntity;
import br.com.handson.entity.ResultWithContentEntity;
import br.com.handson.util.Constants;
import br.com.handson.util.H2Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckListRepository {
    private H2Connection con;

    public CheckListRepository() {
        this.con = new H2Connection();
    }

    public List<PairValueEntity> get(int type){
        String query = "SELECT * FROM ";

        switch(type){
            case Constants.MODALITY:{
                query += "MODALIDADE";
                break;
            }

            case Constants.STADIUM:{
                query += "ESTADIO";
                break;
            }

            case Constants.STEP:{
                query += "ETAPA";
                break;
            }

            default:{
                query += "FEDERACAO";
            }
        }

        try {
            PreparedStatement stm = con.getConnection().prepareCall(query);
            ResultSet rs = stm.executeQuery();
            List<PairValueEntity> pairValueEntities = new ArrayList<>();

            while(rs.next())
                pairValueEntities.add(new PairValueEntity(rs.getInt(1), rs.getString(2)));

            con.closeConnection();

            return pairValueEntities;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
