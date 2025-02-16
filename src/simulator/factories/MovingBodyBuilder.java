package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body>{

	String typeTag;
	String desc;
	public MovingBodyBuilder() {
		super("mv_body","Moving body");
		
	}

	@Override
	protected Body createInstance(JSONObject data) {
		//if(data.get(getTypeTag()) == "mv_body") {
		String id,gid;
		JSONArray aux,aux1;
		Vector2D p,v;
		double m;
		if(data.has("id"))
			 id = data.getString("id");
		else throw new IllegalArgumentException("Must have id");
		
		if(data.has("gid"))
			gid = data.getString("gid");
		else throw new IllegalArgumentException("Must have gid");
		
		if(data.has("p")) {
			 aux = data.getJSONArray("p");
			 if(aux.length()!=2) throw new IllegalArgumentException("p must be 2D");
			 p = new Vector2D(aux.getDouble(0),aux.getDouble(1));
		}
		else throw new IllegalArgumentException("Must have p");
		
		
		if(data.has("v")) {
			aux1 = data.getJSONArray("v");
			if(aux1.length()!=2) throw new IllegalArgumentException("v must be 2D");
			v = new Vector2D(aux1.getDouble(0),aux1.getDouble(1));
		}
		else throw new IllegalArgumentException("Must have v");
		
		
		if(data.has("m"))
			m = data.getDouble("m");
		else throw new IllegalArgumentException("Must have m");
		
		MovingBody b = new MovingBody(id, gid, p, v, m);
		return b;
	
	}
	

}
