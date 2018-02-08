package br.com.handson;

import br.com.handson.entity.CompetitionEntity;
import br.com.handson.entity.CompetitionResultEntity;
import br.com.handson.entity.ResultEntity;
import br.com.handson.repository.CompetitionRepository;
import br.com.handson.service.CompetitionService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HandsOnApplicationTests {

	private Calendar start = Calendar.getInstance();
	Calendar end = Calendar.getInstance();

	CompetitionEntity competitionEntity;


	public HandsOnApplicationTests() {
		start.add(Calendar.DAY_OF_MONTH, 4);
		end.add(Calendar.DAY_OF_MONTH, 4);
		end.add(Calendar.MINUTE, 30);
		end.add(Calendar.SECOND, 0);

		competitionEntity = new CompetitionEntity(2, 5, 1, 1, 1, start.getTime(), end.getTime());
	}

	@Test
	public void $1verifyEqualPlayersTest() {
		CompetitionService competitionService = new CompetitionService();

		Assert.assertFalse(competitionService.checkPlayers(competitionEntity));
	}

	@Test
	public void $2verifyDistinctPlayersTest(){
		try {

			int res4 = new CompetitionRepository().verifyDistinctPlayers(competitionEntity);

			//VERIFICA SE É UMA COMPETIÇÃO NA FINAL OU SEMIFINAL
			if(competitionEntity.getStepId() == 4 || competitionEntity.getStepId() == 5){
				if(res4 >= 2)
					Assert.assertTrue(false);
			}
			else{
				if(res4 >= 1)
					Assert.assertTrue(false);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void $3verifyPlayersTest(){
		try {
			boolean rn2 = new CompetitionRepository().verifyPlays(competitionEntity);
			Assert.assertTrue(rn2);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void $4verifyTimeTest(){
		boolean res2 = new CompetitionService().verifyTime(competitionEntity);
		Assert.assertTrue(res2);
	}

	@Test
	public void $5verifyCountTest(){
		try {
			int res3 = new CompetitionRepository().verifyCount(competitionEntity);
			Assert.assertFalse(res3 >= 4);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void $6verifyInsertCompetitionTest(){
		CompetitionService competitionService = new CompetitionService();

		ResultEntity resultEntity = competitionService.validadeCompetition(competitionEntity);
		Assert.assertTrue(resultEntity.getErrors().isEmpty());
	}

	@Test
	public void $7verifyReturn(){
		List<CompetitionResultEntity> competitionResultEntityList = new CompetitionRepository().get("");
		Assert.assertNotNull(competitionResultEntityList);
	}

	@Test
	public void $7verifyReturnWithParam(){
		List<CompetitionResultEntity> competitionResultEntityList = new CompetitionRepository().get(String.valueOf(competitionEntity.getModalityId()));
		Assert.assertNotNull(competitionResultEntityList);
	}
}
