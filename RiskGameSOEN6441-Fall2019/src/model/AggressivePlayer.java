package model;

import java.util.ArrayList;
/**
 * This class is made to implement Aggressive Player Strategy
 * 
 * @author f_yazdan
 * @author s_shehna
 * @author AdinAshby
 * 
 */
public class AggressivePlayer extends Player implements Strategy {
	/**
	 * This is  constructor for initializing AggressivePlayer
	 * 
	 * @param playerName
	 * @param countriesIDs
	 * @param mapBuild
	
	 */
	public AggressivePlayer(String playerName, ArrayList<Integer> countriesIDs, MapGeo mapBuild) {
		super(playerName, countriesIDs, mapBuild);
		setStrategy(this);
	}
	/**
	 * This is attack method for AggressivePlayer
	 * 
	 * @param attackerCountry
	 * @param attackingCountry
	 * @param attackerNumDice
	 * @param defendNumDice
	 * @param attackAllout
	 */
	@Override
	public void attack(Country attackerCountry, Country attackingCountry, int attackerNumDice, int defendNumDice, int attackAllout){
		Dice attackDice = new Dice(attackerNumDice);
		int[] attackDiceArray = attackDice.getDiceArray();
		

		Dice defendDice = new Dice(defendNumDice);
		int[] defendDiceArray = defendDice.getDiceArray();
		

		boolean[] winner = attackDice.isWinner(defendDice);
		

		for (int i = 0; i < winner.length; i++) {
			int diceNo = i + 1;
			if (winner[i]) {
				System.out.println("Attacker win dice " + diceNo );
				attackingCountry.setArmies(attackingCountry.getArmies() - 1);
				
				if (attackingCountry.getArmies() == 0) {
					System.out.println("\n*****"+
							attackingCountry.getCountryName() + " is conquered"+"*****\n");
					mapGeo.setContinentNamesOfPlayer(this);
					Card card = new Card();
					addCard(card);
					System.out.println("You have the following cards now :");
					System.out.println(getCardNames());
					attackingCountry.setPlayer(attackerCountry.getPlayerName());
					addCountryIdToPlayer(attackingCountry.getCountryId());
					removeCountryIdFromPlayer(attackerCountry.getCountryId());
					int NoOfContinentsControlled = getContinentsControlled().size();
				
					if(mapGeo.getAllCountries().size() == mapGeo.getPlayerByName(attackerCountry.getPlayerName()).getCountryIDs().size()) {
						System.out.println(attackerCountry.getPlayerName()
								+ " is winner. Game over!");
						this.setWon(true);
						
					}
					mapGeo.showMap();
					
					attackMoveCommand(attackerCountry, attackingCountry, attackerCountry.getArmies() / 2);
				}

			} else {
				System.out.println("Defender win dice " + diceNo );
				attackerCountry.setArmies(attackerCountry.getArmies() - 1);
				calculateWorldDominationView();
				if (attackerCountry.getArmies() == 0) {
					System.out.println(
							attackerCountry.getCountryName() + " is conquered");
					attackerCountry.setPlayer(attackingCountry.getPlayerName());
				}
			}
		}
	}
	/**
	 * This is reinforce method for AggressivePlayer
	 * 
	 * @param mapBuild
	 * @param countryName
	 * @param num
	 * @param finished
	 
	 */
	@Override
	public boolean reinforce(MapGeo mapBuild, String countryName, int num, boolean finished) {

		int oldArmies = mapBuild.getCountryByName(countryName).getArmies();
		mapBuild.getCountryByName(countryName)
		.setArmies(oldArmies + mapBuild.playerContinentValuesOwnership(this.getPlayerName()) + num);
		
		calculateWorldDominationView();

		return true;
	}
	/**
	 * This is fortify method for AggressivePlayer
	 * 
	 * @param fromCountry
	 * @param toCountry
	 * @param armiesToMove
	 * @param mapBuild
	 
	 */

	@Override
	public void fortify(String fromCountry, String toCountry, int armiesToMove, MapGeo mapBuild) {
			int oldArmiesFromCountry = mapBuild.getCountryByName(fromCountry).getArmies();
			mapBuild.getCountryByName(fromCountry).setArmies(oldArmiesFromCountry - armiesToMove);

			int oldArmiesToCountry = mapBuild.getCountryByName(toCountry).getArmies();
			mapBuild.getCountryByName(toCountry).setArmies(oldArmiesToCountry + armiesToMove);
			calculateWorldDominationView();
	}
}