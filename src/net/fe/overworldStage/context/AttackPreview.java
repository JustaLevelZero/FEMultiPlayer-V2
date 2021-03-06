package net.fe.overworldStage.context;

import java.util.ArrayList;

import chu.engine.anim.AudioPlayer;
import net.fe.overworldStage.*;
import net.fe.unit.*;

public class AttackPreview extends OverworldContext{
	private Unit attacker;
	private Unit defender;
	private BattlePreview preview;
	private ArrayList<Weapon> weapons;
	private int index;
	public AttackPreview(ClientOverworldStage s, OverworldContext prevContext, Unit a, Unit d) {
		super(s, prevContext);
		attacker = a;
		defender = d;
		preview = new BattlePreview(ClientOverworldStage.RIGHT_AXIS - 44,
				76, a, d, Grid.getDistance(a, d));
		
	}
	
	public void startContext(){
		super.startContext();
		stage.addEntity(preview);
		weapons = attacker.equippableWeapons(Grid.getDistance(attacker, defender));
	}

	@Override
	public void onSelect() {
		AudioPlayer.playAudio("select");
		stage.addCmd("ATTACK");
		stage.addCmd(new UnitIdentifier(defender));
		stage.send();
		attacker.setMoved(true);
		cursor.setXCoord(attacker.getXCoord());
		cursor.setYCoord(attacker.getYCoord());
		stage.reset();
	}
	
	public void cleanUp(){
		stage.removeEntity(preview);
	}

	@Override
	public void onUp() {
		
	}

	@Override
	public void onDown() {
		
	}

	@Override
	public void onLeft() {
		index--;
		if(index < 0){
			index += weapons.size();
		}
		equip();
	}

	@Override
	public void onRight() {
		index = (index+1)%weapons.size();
		equip();
	}
	
	public void equip(){
		attacker.equip(weapons.get(index));
	}

}
