package simulator.factories;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;




public class BuilderBasedFactory<T> implements Factory<T> {

	private Map<String,Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;
	
	
	public BuilderBasedFactory() {
		// Create a HashMap for _builders, a LinkedList _buildersInfo
		_builders = new HashMap<String,Builder<T>>();
		_buildersInfo = new LinkedList<JSONObject>();
		
		
	} 
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		// call addBuilder(b) for each builder b in builder
		for(Builder<T> b: builders) {
			this.addBuilder(b);
		}
	}
	public void addBuilder(Builder<T> b) {
		// add and entry ‘‘ b.getTag() −> b’’ to _builders.
		_builders.put(b.getTypeTag(), b);
		// add b.getInfo () to _buildersInfo
		_buildersInfo.add(b.getInfo());
		}

	public T createInstance(JSONObject info) {
		T objeto = null;
		if (info == null) {
			throw new IllegalArgumentException("Invalid value for createInstance: null");
		}
		// Search for a builder with a tag equals to info . getString ("type"), call its
		// createInstance method and return the result if it is not null . The value you
		// pass to createInstance is :
		for(Builder<T> b:_builders.values()) {
			if(b.getTypeTag().equals(info.getString("type"))){
				if(info.has("data")) {
					objeto = b.createInstance(info.getJSONObject("data"));
				}
				else
					objeto = b.createInstance(info);
					
					//throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString());
			}
		}
		// info . has("data") ? info . getJSONObject("data") : new getJSONObject()
		// If no builder is found or thr result is null ...
		if(objeto == null)throw new IllegalArgumentException();
		
		return objeto;
	}

	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}

}
