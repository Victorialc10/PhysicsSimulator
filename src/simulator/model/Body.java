package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {
	
	protected String id;
	protected String gid;
	protected Vector2D v,p;
	protected Vector2D f = new Vector2D(0,0);
	protected double m;
	
	public Body(String id,String gid, Vector2D p, Vector2D v, double m) {	
		if (id == null|| gid == null|| v == null || p == null || f == null || m <= 0.0 || id.isEmpty()||gid.isEmpty() || id.contains(" ") || gid.contains(" ")) 
			throw new IllegalArgumentException("Invalid arguments");
//trim().length()>0 
		this.id = id;
		this.gid = gid;
		this.v = v;
		this.p = p;
		this.m = m;
		
		
	}
	
	public String getId() {
		return this.id;
	}
	public String getgId() {
		return this.gid;
	}
	public Vector2D getVelocity() {
		return this.v;
	}
	public Vector2D getForce() {
		return this.f;
	}
	public Vector2D getPosition() {
		return this.p;
	}
	public double getMass() {
		return this.m;
	}
	void addForce(Vector2D f) {
		this.f = this.f.plus(f);
	}
	void resetForce() {
		this.f = new Vector2D();
	}
	abstract void advance(double dt);
	
	public JSONObject getState() {
		JSONObject state = new JSONObject();
		state.put("p", p.asJSONArray());
		state.put("v", v.asJSONArray());
		state.put("f", f.asJSONArray());
		state.put("id", id);
		state.put("m", m);
		return state;
	}
	public String toString() {
		return getState().toString();
	}
	public boolean equals(Object obj) {

		if (this == obj)

			return true;

		if (obj == null)

			return false;

		if (!(obj instanceof Body ))

			return false;

		Body other = (Body) obj;

		if (id == null || other.id == null) {

				return false;

		} else if (!id.equals(other.id))

			return false;

		return true;

	}
}
