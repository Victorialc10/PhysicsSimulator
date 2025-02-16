package simulator.factories;


import org.json.JSONObject;


import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	public NoForceBuilder() {
		super("nf", "No force");
		
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		NoForce b = new NoForce();
		return b;
	}
	public JSONObject getInfo() {
		JSONObject info = new JSONObject();
		info.put("type", "nf");
		info.put("desc", "No force");
		info.put("data",new JSONObject());
		return info;
	}

}
 