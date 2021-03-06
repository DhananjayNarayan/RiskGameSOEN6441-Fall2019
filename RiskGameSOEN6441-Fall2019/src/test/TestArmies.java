package test;
import java.util.*;
import org.junit.Assert.*;
import org.junit.Before;

import model.*;

import org.junit.Assert;
import org.junit.Test;
/**
 * This testcase class test the calculation of armies given to players
 * @author s_shehna
 *
 */
public class TestArmies {

	ArrayList<String> players;
	ArrayList<String> strategy;
	MapGeo mapBuild;
	MapDomination mapDomination;
	MapConquest mapConquest;
	MapAdapter mapAdapter;
	ArrayList<Integer> countryListOne;
	ArrayList<Integer> countryListTwo;
	ArrayList<Integer> countryListThree;
	ArrayList<Integer> countryListfour;
	/**
	 * initializes data before testcases
	 */
	@Before
	public void setup()
	{
		mapBuild = MapGeo.getInstance();
		mapDomination = new MapDomination();
		mapConquest = new MapConquest(mapDomination);
		mapAdapter = new MapAdapter(mapConquest);
		players = new ArrayList<String>();
		strategy = new ArrayList<String>();
		countryListOne = new ArrayList<>();
		countryListTwo = new ArrayList<>();
		countryListThree = new ArrayList<>();
		countryListfour = new ArrayList<>();
	}
/**
 * This testcase tests the number of armies given to only player in game
 *
 */
	
	
	@Test
	public void testArmiesOwnedByPlayerOne() throws Exception {
		mapDomination.read("test");
		players.add("a");
		strategy.add("human");
		mapBuild.assigningPlayersToCountries(players, strategy);
		Player[] myPlayers = mapBuild.getPlayers();
			
		myPlayers[0].calculateNumberOfArmiesEachPlayerGets();
		int armies_owned_by_playerOne = myPlayers[0].getNumberOfArmiesEachPlayerGets();
	
		Assert.assertEquals(3, armies_owned_by_playerOne);	 
}
	/**
	 * This testcase tests the number of armies given to two players in game
	 * 
	 */
	@Test
	public void testArmiesOwnedByPlayerTwo() throws Exception {
		mapDomination.read("ameroki"); 
		players.add("a");
		players.add("b");
		strategy.add("human");
		strategy.add("human");
		mapBuild.assigningPlayersToCountries(players, strategy);
		Player[] myPlayers = mapBuild.getPlayers();
		ArrayList<Integer> countryOne = myPlayers[0].getCountryIDs();
		ArrayList<Integer> countryTwo = myPlayers[1].getCountryIDs();
		Player playerOne = new Player("a", countryOne , mapBuild);
		Player playerTwo = new Player("b", countryTwo , mapBuild);
		
	    playerOne.calculateNumberOfArmiesEachPlayerGets();
		playerTwo.calculateNumberOfArmiesEachPlayerGets();
		int armiesOwnedByPlayerOne = playerOne.getNumberOfArmiesEachPlayerGets();
		int armiesOwnedByPlayerTwo = playerTwo.getNumberOfArmiesEachPlayerGets();
		
		Assert.assertEquals(3, armiesOwnedByPlayerOne);	
		Assert.assertEquals(3, armiesOwnedByPlayerTwo);	
	}

}

