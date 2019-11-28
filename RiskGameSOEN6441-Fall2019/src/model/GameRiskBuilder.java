package model;

import java.io.Serializable;

import controller.Game.Phase;

public class GameRiskBuilder extends GameBuilder implements Serializable{
	//default serialVersion id
    private static final long serialVersionUID = 1L;
	private MapGeo mapGeo;
	private Player turnPlayer;
	private Phase phase;

	public MapGeo getMapGeo() {
		return mapGeo;
	}

	public void setMapGeo(MapGeo mapGeo) {
		this.mapGeo = mapGeo;
	}

	public Player getTurnPlayer() {
		return turnPlayer;
	}

	public void setTurnPlayer(Player turnPlayer) {
		this.turnPlayer = turnPlayer;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	@Override
	void buildTurnPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void buildPhase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void buildMapGeo() {
		game.setMapGeo(mapGeo);
		
	}

}
