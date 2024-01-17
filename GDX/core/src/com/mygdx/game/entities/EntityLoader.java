/*package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.world.GameMap;

public class EntityLoader {

    private static Json json = new Json();

    public static ArrayList<Entity> loadEntities(String id, GameMap map) {
        FileHandle file = Gdx.files.internal(id + ".json");

        if (file.exists()) {
            System.out.println("A");
            try {
                EntitySnapshot[] snapshots = json.fromJson(EntitySnapshot[].class, file.readString());
                System.out.println("B");
                System.out.println(snapshots);
                ArrayList<Entity> entities = new ArrayList<Entity>();
                for (EntitySnapshot snapshot : snapshots) {
                    entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
                }
                return entities;
            } catch (Exception e) {
                Gdx.app.error("EntityLoader", "Failed to deserialize JSON data. Error: " + e.getMessage());
            }
            return null;
            
            
        } else {
            Gdx.app.error("EntityLoader", "Could not load entities.");
            return null;
        }
    }
}*/