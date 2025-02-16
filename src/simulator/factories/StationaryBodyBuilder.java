package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body>{

	public StationaryBodyBuilder() {
		super("st_body", "Stationary body");
		
	}

	@Override
	protected Body createInstance(JSONObject data) {
		String id,gid;
		JSONArray aux;
		Vector2D p;
		double m;
		if(data.has("id"))
			 id = data.getString("id");
		else throw new IllegalArgumentException("Must have id");
		
		if(data.has("gid"))
			gid = data.getString("gid");
		else throw new IllegalArgumentException("Must have gid");
		
		if(data.has("p")) {
			 aux = data.getJSONArray("p");
			 if(aux.length()>2) throw new IllegalArgumentException("p must be 2D");
			 p = new Vector2D(aux.getDouble(0),aux.getDouble(1));
		}
		else throw new IllegalArgumentException("Must have p");
		
		
		if(data.has("m"))
			m = data.getDouble("m");
		else throw new IllegalArgumentException("Must have m");
		
		StationaryBody b = new StationaryBody(id, gid, p, m);
		return b;
	}

}
