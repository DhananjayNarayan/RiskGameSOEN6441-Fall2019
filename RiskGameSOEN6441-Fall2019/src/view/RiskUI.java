package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.Game;
import controller.Game.Phase;
import model.Country;
import model.MapAdapter;
import model.GameBuilder;
import model.MapConquest;
import model.GameDirector;
import model.MapDomination;
import model.GameBuilder;
import model.MapGeo;
import model.Player;
import model.Tournament;

/**
 * This is a Risk UI class
 * 
 * @author f_yazdan
 * @author AdinAshby
 * @author s_shehna
 */

public class RiskUI {


	/**
	 * private mapView
	 */

	private MapView mapView = new MapView();
	/**
	 * private welcomeMessage
	 */

	private String welcomeMessage = "\t\t*****Risk Game*****";
	/**
	 * private editMapYesOrNoMessage
	 */

	private String editMapYesOrNoMessage = "Do you want to create/edit map? (Y/N)\n";
	/**
	 * private editMapRequestingMessage
	 */

	private String editMapRequestingMessage = "Enter corresponding commands for creating/editing a map.\n"
			+ "Whenever you are happy with the result, enter \"finishediting\".\n";
	/**
	 * private loadMapRequestingMessage
	 */

	private String loadMapRequestingMessage = "Load the map you with to play by using \"loadmap\" command or start the tournament command or load a game\n";
	/**
	 * private addOrRemovePlayersRequestingMessage
	 */
	private String addOrRemovePlayersRequestingMessage = "Add/remove players and at the end, enter \"populatecountries\":\n";
	/**
	 * private startupRequestingMessage
	 */
	private String startupRequestingMessage = "Place your armies on your selected country or use \"placeall\" command:\n";

	/**
	 * private scanner
	 */
	private transient Scanner scanner;
	/**
	 * private input
	 */
	private String input;
	/**
	 * private regex
	 */
	private String regex;
	/**
	 * private pattern
	 */
	private Pattern pattern;
	/**
	 * private matcher
	 */
	private Matcher matcher;
	/**
	 * private playerNames
	 */
	private ArrayList<String> playerNames = new ArrayList<String>();
	/**
	 * private playerStrategies
	 */
	private ArrayList<String> playerStrategies = new ArrayList<String>();

	private Tournament theTournament;

	private int counterForPhases;
	
	private MapDomination mapDomination = new MapDomination();
	private MapConquest mapConquest;


	/**
	 * This is RiskUI constructor
	 */
	public RiskUI() {
		scanner = new Scanner(System.in);
	}

	/**
	 * This method is to start the game
	 * 
	 * @throws Exception
	 */
	public void RiskUIStartTheGame() throws Exception {

		/**
		 * boolean isValidCommand
		 */
		boolean isValidCommand = false;
		/**
		 * String mapFileName
		 */
		String mapFileName = null;

		/**
		 * String editMapAnswer
		 */
		String editMapAnswer = null;
		/**
		 * boolean finished
		 */

		boolean finished = false;
		/**
		 * boolean placeAllFlag
		 */
		boolean placeAllFlag = false;
		/**
		 * boolean editMapWhile
		 */

		boolean editMapWhile = false;
		/**
		 * boolean finishedEditing
		 */
		boolean finishedEditing = false;

		/**
		 * print welcome message
		 */
		System.out.println(welcomeMessage);
		/**
		 * print edit map
		 */
		System.out.println(editMapYesOrNoMessage);

		/**
		 * String addText
		 */

		String addText = "";

/**
 * object of game class
 */
		Game game=new Game();
		/**
		 * boolean finishedADD_PLAYERS
		 */
		boolean finishedADD_PLAYERS=false;
		/**
		 * boolean finishedMAP_LOAD
		 */
		boolean finishedMAP_LOAD = false;
		/**
		 * boolean finishedPLACE_ARMIES
		 */
		boolean finishedPLACE_ARMIES = false;
		/**
		 * intializes turnPlayer 
		 */
		Player turnPlayer = null;
		/**
		 * This part is hard code to test the project defined by the boolean debug
		 * attribute
		 */
 

		boolean debug = false;
		if (debug == true) {
			mapFileName="test"; //Aden Africa
			
			mapDomination.isDominationMap(mapFileName);
			game.setMapGeo(mapDomination);



			playerNames.add("Aval");
			playerNames.add("Dovom");

			playerStrategies.add("human");
			playerStrategies.add("human");

			mapDomination.assigningPlayersToCountries(playerNames, playerStrategies);
			mapDomination.showMap();
			mapDomination.placeAllArmies();

			mapDomination.showMap();
			
			
			Player[] players = mapDomination.getPlayers();
			Player player1 = players[0];
			Player player2 = players[1];
			player1.calculateNumberOfArmiesEachPlayerGets();
			player2.calculateNumberOfArmiesEachPlayerGets();
			System.out.println(player1.getPlayerName() + " is your turn to reinforce");
			player1.getNumberOfArmiesEachPlayerGets();
			player2.getNumberOfArmiesEachPlayerGets();
			System.out.println("Player 1=" + player1.getPlayerName() + " Player2=" + player2.getPlayerName());

			game.saveGame("AGameSave", mapDomination, player1, Phase.REINFORCEMENT);
			game.loadGame("AGameSave");
			
			player1.attackAllout();
			
			int attackerCountryId = player1.getCountryIDs().get(0);
			int fortifyCountryId = player1.getCountryIDs().get(1);
			int attackingCountryId = player2.getCountryIDs().get(0);
			String attackerCountryName = mapDomination.getCountryNameById(attackerCountryId);
			String fortifyCountryName = mapDomination.getCountryNameById(fortifyCountryId);
			String attackingCountryName = mapDomination.getCountryNameById(attackingCountryId);
			Country attackerCountry = mapDomination.getCountryById(attackerCountryId);
			Country fortifyCountry = mapDomination.getCountryById(fortifyCountryId);
			Country attackingCountry = mapDomination.getCountryById(attackingCountryId);
			isValidCommand = player1.reinforce(mapDomination, attackerCountryName, 3, false);
			mapDomination.showMap();
			System.out.println("\n-------------------\nAttack Scenario from " + attackerCountryName + " to "
					+ attackingCountryName);
			player1.attack(attackerCountry, attackingCountry, 3, 2, 0);

			mapDomination.showMap();
			System.out.println("\n-------------------\nFortify Scenario from " + attackerCountryName + " to "
					+ fortifyCountryName);
			player1.fortify(attackerCountryName, fortifyCountryName, 5, mapDomination);
			mapDomination.showMap();
		
			editMapAnswer = "N";
		}

		editMapAnswer = scanner.nextLine();

		while (!editMapWhile) {

			if (editMapAnswer.equalsIgnoreCase("Y")) {
				System.out.println(editMapRequestingMessage);
				editMapWhile = true;
				while (!finished) {
				
					isValidCommand = false;
					readInput();

					// editmap filename
					regex = "editmap ([\\w*\\_\\-]*)";
					setPattern(regex);
					setMatcher(input);

					if (getMatcher().find()) {
						mapFileName = getMatcher().group(1);
						try {

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						isValidCommand = true;
					}

					// add continent
					regex = "(?<=editcontinent)(.*)";
					setPattern(regex);
					setMatcher(input);
					addText = "";
					if (matcher.find()) {
						addText = matcher.group(1);
					}
					regex = "(-add ([\\w*\\_\\-]*) (\\d*))+";
					setPattern(regex);
					setMatcher(addText);
					while (matcher.find()) {
						String continentName = matcher.group(2);
						int continentValue = Integer.parseInt(matcher.group(3));
						mapDomination.addContinent(continentName, continentValue);
						isValidCommand = true;

					}

					// remove continent
					regex = "(?<=editcontinent)(.*)";
					setPattern(regex);
					setMatcher(input);
					addText = "";
					if (matcher.find()) {
						addText = matcher.group(1);
					}
					regex = "(-remove ([\\w*\\_\\-]*))+";
					setPattern(regex);
					setMatcher(addText);
					while (matcher.find()) {
						String continentName = matcher.group(2);
						mapDomination.removeContinent(continentName);
						isValidCommand = true;

					}

					// add country
					regex = "(?<=editcountry)(.*)";
					setPattern(regex);
					setMatcher(input);
					addText = "";
					if (matcher.find()) {
						addText = matcher.group(1);
					}
					regex = "(-add ([\\w*\\_\\-]*) ([\\w*\\_\\-]*))+";
					setPattern(regex);
					setMatcher(addText);
					while (matcher.find()) {
						String countryName = matcher.group(2);
						String continentName = matcher.group(3);
						mapDomination.addCountry(countryName, continentName);
						mapDomination.getCountryAdjacency().addVertex(mapDomination.getCountryIdByName(countryName));
						isValidCommand = true;

					}

					// remove country
					regex = "(?<=editcountry)(.*)";
					setPattern(regex);
					setMatcher(input);
					addText = "";
					if (matcher.find()) {
						addText = matcher.group(1);
					}
					regex = "(-remove ([\\w*\\_\\-]*))+";
					pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(addText);
					while (matcher.find()) {
						String countryName = matcher.group(2);
						mapDomination.removeCountry(countryName);
						isValidCommand = true;

					}

					// add neighbor
					regex = "(?<=editneighbor)(.*)";
					setPattern(regex);
					setMatcher(input);
					addText = "";
					if (matcher.find()) {
						addText = matcher.group(1);
					}
					regex = "(-add ([\\w*\\_\\-]*) ([\\w*\\_\\-]*))+";
					setPattern(regex);
					setMatcher(addText);
					while (matcher.find()) {
						String countryName = matcher.group(2);
						String neighborCountryName = matcher.group(3);
						mapDomination.addCountryAdjacency(countryName, neighborCountryName);
						
						isValidCommand = true;

					}

					// remove neighbor

					regex = "(?<=editneighbor)(.*)";
					setPattern(regex);
					setMatcher(input);
					addText = "";
					if (matcher.find()) {
						addText = matcher.group(1);
					}
					regex = "(-remove ([\\w*\\_\\-]*) ([\\w*\\_\\-]*))+";
					setPattern(regex);
					setMatcher(addText);
					while (matcher.find()) {
						String countryName = matcher.group(2);
						String neighborCountryName = matcher.group(3);
						mapDomination.removeCountryAdjacency(countryName, neighborCountryName);
						isValidCommand = true;

					}

					// showmap
					regex = "showmap";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						mapDomination.showMap();
						isValidCommand = true;
					}

					// savemap filename
					regex = "savemap ([\\w*\\_\\-]*)";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						mapFileName = matcher.group(1);
						isValidCommand = true;

						try {
							mapDomination.write(mapFileName);
							mapConquest=new MapConquest(mapDomination);
							MapDomination mapAdapterFromDomination = new MapAdapter(mapConquest);
							mapAdapterFromDomination.write(mapFileName+"Conquest");
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}

					// savegame filename
					regex = "savegame ([\\w*\\_\\-]*)";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						mapFileName = matcher.group(1);
						isValidCommand = true;

						game.saveGame(mapFileName, mapDomination, null, Phase.GAME_START);
					}
					
					
					// added multiple time editcontinent -add aa 1 -add bb 2
					// add continent done
					regex = "(?<=editcontinent)(.*)";
					setPattern(regex);
					setMatcher(input);
					addText = "";
					if (matcher.find()) {
						addText = matcher.group(1);
					}

					// validatemap
					regex = "validatemap";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						isValidCommand = true;
						mapDomination.validateMap();

					}

					// showadjacencymap countryname
					regex = "showadjacencymap ([\\w*\\_\\-]*)";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						mapFileName = matcher.group(1);
						// call adjacency
						isValidCommand = true;
					}

					// finishediting
					regex = "finishediting";
					if (input.equalsIgnoreCase(regex)) {
						finished = true;
						finishedEditing = true;
						isValidCommand = true;
					}

					if (!isValidCommand) {
						System.out.println("Please follow the correct command rules");
					
					}
				}

			}

			
			if (editMapAnswer.equalsIgnoreCase("N") || finishedEditing == true) {
				editMapWhile = true;

				counterForPhases = 0;
				mapView.showPhaseView(counterForPhases, "");

				

				while (!finishedMAP_LOAD && finishedEditing == false && debug == false) {
					System.out.println(loadMapRequestingMessage);
					isValidCommand = false;
					readInput();

					// loadmap filename done
					regex = "loadmap ([\\w*\\_\\-]*)";
					setPattern(regex);
					setMatcher(input);

					if (matcher.find()) {
						mapFileName = matcher.group(1);
						isValidCommand = true;
						boolean isLoaded = false;
						try {
							isLoaded = mapDomination.isDominationMap(mapFileName);
						
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						if (isLoaded == true) {
							finishedMAP_LOAD = true;
						}

					}
					
					// savegame filename
					regex = "savegame ([\\w*\\_\\-]*)";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						mapFileName = matcher.group(1);
						isValidCommand = true;

						game.saveGame(mapFileName, mapDomination, null, Phase.MAP_LOAD);
					}

					// loadgame filename
					regex = "loadgame ([\\w*\\_\\-]*)";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						mapFileName = matcher.group(1);
						isValidCommand = true;

						boolean isLoaded=game.loadGame(mapFileName);
						if(isLoaded) {
						mapDomination=(MapDomination) game.getMapGeo();
						turnPlayer=game.getTurnPlayer();
						String playerName;
						Phase phase = game.getPhase();
						if (turnPlayer!=null) {playerName=turnPlayer.getPlayerName();}else {playerName="-";}
						System.out.println("Game Loaded, Let's countinue Player "+playerName+" at the stage of "+phase);
						mapDomination.showMap();

							switch (phase) {
//GAME_START, MAP_LOAD, ADD_PLAYERS, PLACE_ARMIES, REINFORCEMENT, ATTACK, FORTIFICATION, GAME_OVER, TOURNAMENT
							case ADD_PLAYERS:
								finishedMAP_LOAD = true;
								break;

							case PLACE_ARMIES:
								finishedMAP_LOAD = true;
								finishedADD_PLAYERS = true;
								break;
							case REINFORCEMENT:
								finishedMAP_LOAD = true;
								finishedADD_PLAYERS = true;
								finishedPLACE_ARMIES = true;
								break;

							}
						
						
						finishedMAP_LOAD = true;
						}
					}
					
					
					// tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns

					ArrayList<String> listOfMapFiles = new ArrayList<String>();
					ArrayList<String> listOfPlayerStrategies = new ArrayList<String>();
					int numberOfGames = 0;
					int maxNumberOfTurns = 0;

					String[] maps;
					String[] strategy;

					regex = "(?<=tournament )(.*)";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						addText = getMatcher().group(1);


						regex = "(?=(-M )(?<maps>([\\w\\s]+)))";
						setPattern(regex);
						setMatcher(addText);

						if (matcher.find()) {
							maps = getMatcher().group("maps").split(" ");
							System.out.println("Maps are selected :"+Arrays.toString(maps));
							if(maps.length>5 || maps.length<1) {
								System.out.println("Maps should be between 1 to 5");
								isValidCommand = false;
							}else {
								for(int i=0;i<maps.length;i++) {
									listOfMapFiles.add(maps[i]);
								}
							}

						}

						regex = "(?=(-P )(?<strategy>([\\w\\s]+)))";
						setPattern(regex);
						setMatcher(addText);
						if (matcher.find()) {

							strategy = getMatcher().group("strategy").split(" ");
							System.out.println("Strategies are selected :"+Arrays.toString(strategy));
							if(strategy.length>4 || strategy.length<2) {
								System.out.println("Strategy should be between 2 to 4");
								isValidCommand = false;
							}else {
								for(int i=0;i<strategy.length;i++) {
									listOfPlayerStrategies.add(strategy[i]);
								}
							}
						}

						regex = "(?=(-G )(?<numberOfGames>\\d+))";
						setPattern(regex);
						setMatcher(addText);
						if (matcher.find()) {
							try {
								numberOfGames = Integer.parseInt(getMatcher().group("numberOfGames"));
								System.out.println("Number Of games are selected :"+numberOfGames);
								if(numberOfGames>5 || numberOfGames<1) {
									System.out.println("Number of games should be between 1 to 5");
									isValidCommand = false;
								}
							}catch (Exception e){
								System.out.println("Enter number for the number of games");
								isValidCommand = false;
							}
						}

						regex = "(?=(-D )(?<maxNumberOfTurns>\\d+))";
						setPattern(regex);
						setMatcher(addText);
						if (matcher.find()) {
							try {
								maxNumberOfTurns = Integer.parseInt(getMatcher().group("maxNumberOfTurns"));
								System.out.println("Max number Of turns are selected :"+maxNumberOfTurns);
								if(maxNumberOfTurns>50 || maxNumberOfTurns<10) {
									System.out.println("Number of turns should be between 10 to 50");
									isValidCommand = false;
								}
							}catch (Exception e){
								System.out.println("Enter number for the number of turns");
								isValidCommand = false;
							}

						}







						if((listOfMapFiles.size() > 0 && listOfMapFiles.size() < 6) && (listOfPlayerStrategies.size() > 1 && listOfPlayerStrategies.size() < 5) && (numberOfGames > 0 && numberOfGames < 6) && (maxNumberOfTurns > 9 && maxNumberOfTurns < 51)) {

							isValidCommand = true;

							for(String each : listOfMapFiles) {
								if( mapDomination.isDominationMap(each) == false) {
						//			isValidCommand = false;
						//			break;
								}
							}

							for(String each : listOfPlayerStrategies) {
								if(!(each.equalsIgnoreCase("aggressive") || each.equalsIgnoreCase("benevolent") || each.equalsIgnoreCase("random") || each.equalsIgnoreCase("cheater"))) {
						//			isValidCommand = false;
						//			break;
								}
							}

							if(isValidCommand) {
								theTournament = new Tournament(listOfMapFiles, listOfPlayerStrategies, numberOfGames, maxNumberOfTurns);
								theTournament.startTheTournament();
								System.exit(0);
							}
						}

						if (!isValidCommand) {
							System.out.println("Please follow the correct command rules");
						}
					}
				}

				
				
				
				

				while (!finishedADD_PLAYERS && debug == false) {
					System.out.println(addOrRemovePlayersRequestingMessage);
					isValidCommand = false;
					readInput();
					// savemap filename done
					regex = "savemap ([\\w*\\_\\-]*)";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						mapFileName = matcher.group(1);
						isValidCommand = true;

						try {
							mapDomination.write(mapFileName);
							mapConquest=new MapConquest(mapDomination);
							MapDomination mapAdapterFromDomination = new MapAdapter(mapConquest);
							mapAdapterFromDomination.write(mapFileName+"Conquest");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					
					
					// savegame filename
					regex = "savegame ([\\w*\\_\\-]*)";
					setPattern(regex);
					setMatcher(input);
					if (matcher.find()) {
						mapFileName = matcher.group(1);
						isValidCommand = true;

						game.saveGame(mapFileName, mapDomination, null, Phase.MAP_LOAD);
					}
					
					
					
					
					
					
					
					// gameplayer -add
					regex = "(?<=gameplayer)(.*)";
					setPattern(regex);
					setMatcher(input);
					addText = "";
					if (matcher.find()) {
						addText = matcher.group(1);
					}
					regex = "(-add ([\\w*\\_\\-]*) ([\\w*\\_\\-]*))+";
					setPattern(regex);
					setMatcher(addText);

					while (matcher.find()) {
						String playerName = getMatcher().group(2);
						String playerStrategy = getMatcher().group(3);

						playerNames.add(playerName);
						playerStrategies.add(playerStrategy);
						System.out.println("Player "+playerName+" by "+playerStrategy+" behaviour is added");						
						isValidCommand = true;
					}

					// gameplayer -remove
					regex = "(?<=gameplayer)(.*)";
					setPattern(regex);
					setMatcher(input);
					addText = "";
					if (matcher.find()) {
						addText = matcher.group(1);
					}
					regex = "(-remove ([\\w*\\_\\-]*))+";
					setPattern(regex);
					setMatcher(addText);
					while (matcher.find()) {
						String playerName = getMatcher().group(2);
						int indexOfPlayerName = playerNames.indexOf(playerName);
						playerNames.remove(playerName);
						playerStrategies.remove(indexOfPlayerName);
						System.out.println(playerName+" is removed");
						isValidCommand = true;
					}

					// populatecountries
					regex = "populatecountries";
					setPattern(regex);
					setMatcher(input);
					if (getMatcher().find()) {
						isValidCommand = true;
						finishedADD_PLAYERS = true;
						mapDomination.assigningPlayersToCountries(playerNames, playerStrategies);

					}

					// showmap
					regex = "showmap";
					setPattern(regex);
					setMatcher(input);
					if (getMatcher().find()) {
						isValidCommand = true;
						mapView.showMap(mapDomination);

					}

					if (!isValidCommand) {
						System.out.println("Please follow the correct command rules");
					}
				}
				
				
//				finishedPLACE_ARMIES = false;
				
				
				
				

				if (placeAllFlag == true) {
					break;
				}

				/******** START GAME PHASE **********************/

				

				for (Player player : mapDomination.getPlayers()) {
					
					
					if(turnPlayer!=null && turnPlayer!=player) {
						break;
					}
					
					
					if (placeAllFlag == true) {
						break;
					}
				

					player.calculateNumberOfArmiesEachPlayerGets();
			

				
					

					while (!finishedPLACE_ARMIES && debug == false) {
						System.out.println(startupRequestingMessage);
						isValidCommand = false;

						System.out.println("Player " + player.getPlayerName() + ":");
						System.out.println("You get -" + mapDomination.calculateNumberOfInitialArmies() + "- armies.");
						readInput();

						// savegame filename
						regex = "savegame ([\\w*\\_\\-]*)";
						setPattern(regex);
						setMatcher(input);
						if (matcher.find()) {
							mapFileName = matcher.group(1);
							isValidCommand = true;

							game.saveGame(mapFileName, mapDomination, player, Phase.PLACE_ARMIES);
						}
						
						
						// placeall
						regex = "placeall";
						setPattern(regex);
						setMatcher(input);
						if (matcher.find()) {
							isValidCommand = true;
							mapDomination.placeAllArmies();
							finishedPLACE_ARMIES = true;
							placeAllFlag = true;
							break;

						}

						// showmap
						regex = "showmap";
						setPattern(regex);
						setMatcher(input);
						if (getMatcher().find()) {
							isValidCommand = true;
							mapView.showMap(mapDomination);
						}

						// placearmy countryname

						regex = "(?<=placearmy )(.*)";
						setPattern(regex);
						setMatcher(input);
						addText = "";

						if (getMatcher().find()) {
							String countryName = matcher.group(1);

							if (mapDomination.placearmyIsValid(player, countryName) == true) {
								mapDomination.assignInitialsArmiesToSpecificCountry(countryName,
										mapDomination.calculateNumberOfInitialArmies());
								finishedPLACE_ARMIES = true;
							} else {
								System.out.println("placearmy is not valid");
							}

							isValidCommand = true;
						}

						if (!isValidCommand) {
							System.out.println("Please follow the correct command rules");
						}
					

						if (placeAllFlag == true) {
							break;
						}
					}
				}
				
				

				/******** START GAME PHASE **********************/

				whileLoop:
					while(true) {

						for (Player player : mapDomination.getPlayers()) {
							if(turnPlayer!=null && turnPlayer!=player) {
								break;
							}
							player.setCounterForPhases(1);

							
							finished = false;

							boolean finishedReinforce=false;
							if(finishedPLACE_ARMIES==true) {
							System.out.println(player.getPlayerName() + " is your turn to reinforce");
							isValidCommand = player.reinforceCommand(game, mapDomination, mapView);
							if(isValidCommand==true) {finishedReinforce=true;}
							}

							player.setCounterForPhases(2);
							isValidCommand = player.attackCommand(game, mapView);

							if(player.getWon()) {
								break whileLoop;
							}

							player.setCounterForPhases(3);
							isValidCommand = player.fortifyCommand(game, mapDomination, mapView);

							if (!isValidCommand) {
								System.out.println("Please follow the correct command rules");
							}
						}// Endof For Player
					}

				mapDomination.showMap();

			} else {
				System.out.println("Please answer by Y or N");
				editMapAnswer = scanner.nextLine();
			}
		}
	}




	/**
	 * This method is for reading the input from console
	 */
	public void readInput() {
		this.input = scanner.nextLine();
	}

	/**
	 * This method is for setting the pattern
	 * 
	 * @param regex
	 */
	public void setPattern(String regex) {
		this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	}

	/**
	 * This method is for setting the matcher
	 * 
	 * @param input
	 */

	public void setMatcher(String input) {
		this.matcher = pattern.matcher(input);
	}

	/**
	 * This method is for fetching the matcher
	 * 
	 * @return Matcher
	 */
	public Matcher getMatcher() {
		return this.matcher;
	}

	/**
	 * This method is for getting the counter for phases
	 * 
	 * @return counterForPhases
	 */
	public int getCounterForPhases() {
		return counterForPhases;
	}

}
