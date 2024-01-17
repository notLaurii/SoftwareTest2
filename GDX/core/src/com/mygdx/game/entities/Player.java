package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.world.GameMap;

public class Player extends Entity {
	
	private static final int SPEED = 80;
	private static final int JUMP_VELOCITY = 6;
	private static final float FRAME_DURATION = 0.1f;
	private boolean UP = false;
	private float rangeX = 20;
	private float rangeY = 10;
	private float attackCooldown = 0;
	private boolean canAttack = true;
	private String Skin="Panda";
	
	 private Texture[] animationFrames;
	    private int currentFrame = 0;
	    private float stateTime = 0;
	
	Texture image;
	
	public Player(float x, float y, GameMap map, float health, float attackDamage) {
		super(x, y, EntityType.PLAYER, map, health, attackDamage);
		loadAnimationFrames(Skin, "StandStill");
	}
	
	 private void loadAnimationFrames(String playerSkin, String animation) {
	        // Lade die einzelnen Frames der Animation hier, z.B., indem du ein Texture-Array erstellst.
	        // Ersetze dies durch deine eigene Implementierung entsprechend der Anzahl der Frames in deiner GIF-Datei.
		 if(playerSkin=="Panda") {
			 if(animation=="GoRight") {
	        animationFrames = new Texture[]{
	        		
	                new Texture("PlayerAnimations/PridePanda/WalkRight/PridePandaWalkRight1.png"),
	                new Texture("PlayerAnimations/PridePanda/WalkRight/PridePandaWalkRight2.png"),
	                new Texture("PlayerAnimations/PridePanda/WalkRight/PridePandaWalkRight3.png"),
	                new Texture("PlayerAnimations/PridePanda/WalkRight/PridePandaWalkRight4.png"),
	                new Texture("PlayerAnimations/PridePanda/WalkRight/PridePandaWalkRight5.png"),
	                new Texture("PlayerAnimations/PridePanda/WalkRight/PridePandaWalkRight6.png"),
	                new Texture("PlayerAnimations/PridePanda/WalkRight/PridePandaWalkRight7.png"),
	                new Texture("PlayerAnimations/PridePanda/WalkRight/PridePandaWalkRight8.png"),
	        };
			 }
			 if(animation=="GoLeft") {
				 animationFrames = new Texture[]{
			        		
			                new Texture("PlayerAnimations/PridePanda/WalkLeft/PridePandaWalkLeft1.png"),
			                new Texture("PlayerAnimations/PridePanda/WalkLeft/PridePandaWalkLeft2.png"),
			                new Texture("PlayerAnimations/PridePanda/WalkLeft/PridePandaWalkLeft3.png"),
			                new Texture("PlayerAnimations/PridePanda/WalkLeft/PridePandaWalkLeft4.png"),
			                new Texture("PlayerAnimations/PridePanda/WalkLeft/PridePandaWalkLeft5.png"),
			                new Texture("PlayerAnimations/PridePanda/WalkLeft/PridePandaWalkLeft6.png"),
			                new Texture("PlayerAnimations/PridePanda/WalkLeft/PridePandaWalkLeft7.png"),
			                new Texture("PlayerAnimations/PridePanda/WalkLeft/PridePandaWalkLeft8.png"),
				};
			 }
			 if(animation=="StandStill") {
				 animationFrames = new Texture[]{
			        		
			                new Texture("PlayerAnimations/PridePanda/PridePandaFront.png"),
				 };
			 }
		 	}
	    }

	
	/*@Override
	public void create (EntitySnapshot snapshot, EntityType type, GameMap map) {
		super.create(snapshot, type, map);
		image = new Texture("player.png");
		//otherData = snapshot.getFloat("otherData", defaultValue) 
	}*/
	
	@Override
	public void update(float deltaTime, float gravity) {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)&&attackCooldown==0) {
			attack(this.rangeX, this.rangeY);
			startAttackCooldown();
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded)
			this.velocityY += JUMP_VELOCITY * getWeight();
		else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded &&this.velocityY > 0)
			this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
		
		super.update(deltaTime, gravity);
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			moveCamX(-SPEED * deltaTime);
			moveX(-SPEED * deltaTime);
			loadAnimationFrames(Skin, "GoLeft");
		}
			
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			moveCamX(SPEED * deltaTime);
			moveX(SPEED * deltaTime);
			loadAnimationFrames(Skin, "GoRight");
		}
		moveCamY(pos.y);
		updateAnimation(deltaTime);
	}	
	
	public void moveCamX(float amount) {
		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.A)) {
		Vector2 translation = new Vector2(getDeltaX(amount), 0f);
		MyGdxGame.cam.translate(translation);
		MyGdxGame.cam.update();	
		//System.out.println(amount);
		}
	}

	public float getDeltaX(float amount) {
		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.A)) {
		float newX = pos.x + amount;
		if (!map.doesRectCollideWithMap(newX, pos.y, getWidth(), getHeight()))
			return amount;
		}
		return 0f;
	}
	
	public void moveCamY(float y) {
		if(pos.y>MyGdxGame.gameMap.getPixelHeight()/2&&UP==false) {
		Vector2 translation = new Vector2(0f, MyGdxGame.gameMap.getPixelHeight()-Gdx.graphics.getHeight());
		UP=true;
		MyGdxGame.cam.translate(translation);
		}
		else if(pos.y<=MyGdxGame.gameMap.getPixelHeight()/2&&UP==true) {
		Vector2 translation = new Vector2(0f, -(MyGdxGame.gameMap.getPixelHeight()-Gdx.graphics.getHeight()));
		MyGdxGame.cam.translate(translation);
		UP=false;
		}
		MyGdxGame.cam.update();	
		//System.out.println(y);
	}
	
	public float getY(float y) {
		if (y>MyGdxGame.gameMap.getPixelHeight()/2)
			return MyGdxGame.gameMap.getPixelHeight()/2;
			return -MyGdxGame.gameMap.getPixelHeight()/2;
	}
	
	 private void updateAnimation(float deltaTime) {
	        stateTime += deltaTime;
	        currentFrame = (int) (stateTime / FRAME_DURATION) % animationFrames.length;
	    }
		
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(animationFrames[currentFrame], pos.x, pos.y, getWidth(), getHeight());

	}

	
	public void attack(float attackRangeX, float attackRangeY) {
		ArrayList<Entity> entities = GameMap.entities;
		for(Entity entity : entities) 
			if(entity != this && entity.isInPlayerRange(this)) 
				entity.takeDamage(attackDamage);
		startAttackCooldown();
	}
	
	private void startAttackCooldown() {
        canAttack = false;
        Timer.schedule(new Task() {
            @Override
            public void run() {
            	canAttack = true;
            }
        }, 1f); // 1 Sekunde Cooldown
    }
	public float getRangeX() {
		return rangeX;
	}
	
	public float getRangeY() {
		return rangeY;
	}

	
	/*public EntitySnapshot getSaveSnapshot() {
		EntitySnapshot snapshot = super.getSaveSnapshot();
		//snapshot.putFloat(otherData, defaultValue);
		return snapshot; 
	} */

}
