package awsome.realistech.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.JSONUtils;

public class RenderUtil {
	
	public Vector3f arrayToVector(JsonObject json, String name) {
		JsonArray jsonArray = JSONUtils.getJsonArray(json, name);	
		if (jsonArray.size() > 3) {
			throw new JsonParseException("Expected 3 " + name + " values, found: " + jsonArray.size());
		}
		
		float[] vector = new float[3];
		
		for (int i = 0; i < vector.length; i++) {
			vector[i] = JSONUtils.getFloat(jsonArray.get(i), name + "[" + i + "]");
		}
		
		return new Vector3f(vector[0], vector[1], vector[2]);
	}
	
}
