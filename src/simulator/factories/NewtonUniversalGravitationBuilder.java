package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	private double G;
	
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton Universal Gravitation");
		G = 6.67E-11;
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if(data.has("G"))
			G = data.getDouble("G");
		NewtonUniversalGravitation fl = new NewtonUniversalGravitation(G);
		return fl;
	}
	public JSONObject getInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type", "nlug");
		info.put("desc", "Newton Universal Gravitation");
		data.put("G","the gravitational constant "+G);
		info.put("data", data);
		return info;
	}

}
