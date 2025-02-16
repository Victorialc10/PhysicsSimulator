package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class BodiesGroup implements Iterable<Body>{
	private String id;
	private ForceLaws leyesFuerza;
	private List<Body> bs;
	
	private List<Body>_bodiesRO; //version no modificable
	
	public BodiesGroup(String id,ForceLaws leyesFuerza) {
		
		if(id == null || leyesFuerza == null || id == "  " || id.isEmpty())
			throw new IllegalArgumentException();
		
		this.id = id;
		this.leyesFuerza = leyesFuerza;
		this.bs = new ArrayList <Body>();
		
		this._bodiesRO = Collections.unmodifiableList(bs);
	}
	public String getId() {
		return this.id;
	}
	void setForceLaws(ForceLaws fl) {
		if(fl == null)
			throw new IllegalArgumentException();
		this.leyesFuerza = fl;
	}
	void addBody (Body b) {
		if(b == null)
			throw new IllegalArgumentException();
		boolean encontrado = false;
		int i = 0;
		while(encontrado==false && i<bs.size()) {
			if(bs.get(i).id ==b.id) {
				encontrado = true;
			}
			else i++;
		}
		if(encontrado == true)
			throw new IllegalArgumentException();
		else 
			bs.add(b);
	}
	void advance(double dt) {
		if(dt<=0) {
			throw new IllegalArgumentException();
		}
		for(Body i:bs) {
			i.resetForce();
		}
		leyesFuerza.apply(bs);
		for(Body i:bs) {
			i.advance(dt);
		}
	}
	public JSONObject getState() {
		JSONObject state = new JSONObject();
		//state.put("id", id);
		JSONArray aux = new JSONArray();
		for(Body i:bs) {
			aux.put(i.getState());
		}
		state.put("bodies", aux);
		state.put("id", id);
		return state;
	}
	public String toString() {
		return getState().toString();
	}
	public String getForceLawsInfo() {
		return leyesFuerza.toString();
	}
	
	
	
	public boolean equals(Object obj) {

		if (this == obj)

			return true;

		if (obj == null)

			return false;

		if (getClass() != obj.getClass())

			return false;

		BodiesGroup other = (BodiesGroup) obj;

		if (id == null) {

			if (other.id != null)

				return false;

		} else if (!id.equals(other.id))

			return false;

		return true;

	}

       @Override

	public int hashCode() {

		final int prime = 31;

		int result = 1;

		result = prime * result + ((id == null) ? 0 : id.hashCode());

		return result;

	}
	@Override
	public Iterator<Body> iterator() {
		// TODO Auto-generated method stub
		return _bodiesRO.iterator();
	}
}
