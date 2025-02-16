package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	private Vector2D c;
	private double g;
	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards fixed point");
		c = new Vector2D();
		g = 9.81;
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if(data.has("c")){
			JSONArray aux = data.getJSONArray("c");
			c = new Vector2D(aux.getDouble(0),aux.getDouble(1));
		}
		//else if(data.has("g"))
		if(data.has("g"))
			g = data.getDouble("g");
		MovingTowardsFixedPoint fl = new MovingTowardsFixedPoint(c,g);
		return fl;
	}
	public JSONObject getInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type", "mtfp");
		info.put("desc", "Moving towards a fixed point");
		data.put("c", "the point towards which bodies move "+c);
		data.put("g" ,"the length of the acceleration vector "+g);
		info.put("data",data);
		return info;
	}

}
