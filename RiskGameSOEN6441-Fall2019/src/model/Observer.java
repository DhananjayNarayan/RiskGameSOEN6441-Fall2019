package model;

import java.util.ArrayList;
/**
 * This is interface for the Observer
 * @author f_yazdan
 * @author s_shehna
 * @author AdinAshby
 */
public interface Observer {
	public void update(int counterForPhases, String playerName);
	public void update(double percentageControlled, int totalNumberOfArmies, ArrayList<String> continentsControlled, MapGeo mapBuild);
}
