
Concordia University
Department of Computer Science
and Software Engineering
Advanced Programming Practices
SOEN 6441 --- Fall 2019
<br>
Project Description
<br>

<b>Deadline: Intermediate delivery 1: October 16,17,18, 2019</b>
<br>
<b>Intermediate delivery 2: November 7,8,9, 2019</b>
<br>
<b>Final delivery: November 27,28, 29, 2019</b>
<br>
Evaluation: Intermediate delivery 1: 15% of final mark
<br>
Intermediate delivery 2: 15% of final mark
<br>
Final delivery: 20% of final mark</b>
<br>
Late submission: not accepted
<br>
Teams: the project is a team assignment
<br>
General description
<br>
<br>
The project is to be undertaken teams of 5 members and consists of the building of a challengingly large Java program. The completion of the project is divided into three separate components: (1) the First and Second Intermediate Project Delivery are intermediate operational build of the software, effectively demonstrating the full implementation of some important software features; (2) the Final Project Delivery is the demonstration of the finalized version of your software. During the final project delivery, you also have to demonstrate that your code includes many of the Java features presented in the lectures, and that you effectively use the tools presented in the lectures in your project. All project deliveries are to be undertaken in the laboratory where the team presents the implemented features to the instructor following a pre-circulated grading sheet. The individual assignments will also be related to the project, but graded individually and separately from the project.
It is important to realize that the project description is purposely incomplete, and that it is one of your duties in this project to: 1) elicit and formulate all the missing details before you start the implementation, 2) limit the scope of the project according to the time that is available, 3) determine what design decisions will be made, as well as 4) what tools will be used for the implementation. These activities require some investigations and discussions that are important aspects of software development and this project.
Problem statement
The specific project for this semester consists in building a simple “Risk” computer game. The developed program will have to be compatible with the rules and map files and the command-line play of the “Domination” version of Risk, which can be downloaded at: https://sourceforge.net/projects/domination/. A Risk game consists of a connected graph map representing a world map, where each node is a country and each edge represents adjacency between countries. Two or more players can play by placing armies on countries they own, from which they can attack adjacent countries to conquer them. The objective of the game is to conquer all countries on the map.
<br>
<br>
<b>Map:</b> The game map is a connected graph where each node represents a country owned by one of the players. Edges between the nodes represent adjacency between countries. The map is divided into subgraphs that represent continents. A continent is a connected subgraph of the map graph, and every country belongs to one and only one continent. Each continent is given a control value that determines the number of armies per turn that is given to a player that controls all of it. During game play, every country belongs to one and only one player and contains one or more armies that belong to the player owning the country. In your implementation, it will be expected that the game can be played on any connected subgraph that is defined by the user before play, saved as a text file representation, and loaded by the game during play.
2
<br>
<br>
<b>Game:</b> The game starts by the startup phase, where the number of players is determined, then all the countries are randomly assigned to the players. Then the turn-based main play phase begins, in which all players are given a turn in a round-robin fashion. Each player’s turn is itself divided into three phases: 1) reinforcement phase, 2) attack phase, 3) fortifications phase. Once a player is finished with these three phases, the next player’s turn starts. In the reinforcements phase, the player is given a number of armies that depends on the number of countries he owns (# of countries owned divided by 3, rounded down). If the player owns all the countries of an entire continent, the player is given an amount of armies corresponding to the continent’s control value. Finally, if the player owns three cards of different sorts or the same sorts, he can exchange them for armies. The number of armies a player will get for cards is first 5, then increases by 5 every time any player does so (i.e. 5, 10, 15, …). 
<br>
In any case, the minimal number of reinforcement armies is 3. Once the total number of reinforcements is determined for the player’s turn, the player may place the armies on any country he owns, divided as he wants. Once all the reinforcement armies have been placed by the player, the attacks phase begins. In the attack phase, the player may choose one of the countries he owns that contains two or more armies, and declare an attack on an adjacent country that is owned by another player. A battle is then simulated by the attacker rolling at most 3 dice (which should not be more than the number of armies contained in the attacking country) and the defender rolling at most 2 dice (which should not be more than the number of armies contained in the attacking country). The outcome of the attack is determined by comparing the defenders best dice roll with the attackers best dice roll. If the defender rolls greater or equal to the attacker then the attacker loses an army otherwise the defender loses an army. If the defender rolled two dice then his other dice roll is compared to the attacker's second best dice roll and a second army is lost by the attacker or defender in the same way. The attacker can choose to continue attacking until either all his armies or all the defending armies have been eliminated. If all the defender's armies are eliminated the attacker captures the territory. The attacking player must then place a number of armies in the conquered country which is greater or equal than the number of dice that was used in the attack that resulted in conquering the country. A player may do as any attacks as he wants during his turn. Once he declares that he will not attack anymore (or cannot attack because none of his countries that have an adjacent country controlled by another player is containing more than one army), the fortification phase begins. In the fortification phase, the player may move any number of armies from one of his owed countries to the other, provided that there is a path between these two countries that is composed of countries that he owns. Only one such move is allowed per fortification phase. Once the move is made or the player forfeits his fortification phase, the player’s turn ends and it is now the next player’s turn. Any player than does not control at least one country is removed from the game. The game ends at any time one of the players owns all the countries in the map.
<br>
<br>
<b>Cards:</b> A player receives a card at the end of his turn if he successfully conquered at least one country during his turn. Each card is either an infantry, cavalry, or artillery card. During a player’s reinforcement phase, a player can exchange a set of three cards of the same kind, or a set of three cards of all different kinds for a number of armies that increases every time any player does so. The number of armies a player will get for cards is first 5, then increases by 5 every time any player does so (i.e. 5, 10, 15, …). A player that conquers the last country owned by another player receives all the cards held by that player. If a player holds five cards during his reinforcement phase, he must exchange three of them for armies.
<br>
<br>
References
Main reference
<br>

Domination by Yura (game rules included, command-line game play to be emulated)
Other game rules resources
<br>
Official Risk game rules by Hasbro (1)
Official Risk game rules by Hasbro (2)
<br>

Risk game rules by ultraboardgames.com
<br>

How to play Risk by wikihow.com
<br>

Other similar implemented games
<br>

Conquest by Sean O’Connor
Online Risk by Pogo games
