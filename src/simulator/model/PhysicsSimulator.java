package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver>{
	
	private double tiempoRealPorPaso;//delta time
	private ForceLaws leyesFuerza;
	
	HashMap<String, BodiesGroup> mapa ;//_groups
	private double tiempoActual;
	private List<String> lista;
	
	private List<SimulatorObserver> listaObservadores;
	//nuevo mapa que contiene lo mismo que mapa pero que va a ser no modificable
	private Map<String, BodiesGroup> _groupsRO; //a los observadores pasamos este
	
	public PhysicsSimulator(ForceLaws leyesFuerza,double tiempoRealPorPaso){
		if(tiempoRealPorPaso <=0 || leyesFuerza == null)
			throw new IllegalArgumentException();
		
		this.tiempoRealPorPaso = tiempoRealPorPaso;
		this.leyesFuerza = leyesFuerza;
		mapa = new HashMap<String,BodiesGroup>();
		this.tiempoActual = 0.0;
		this.lista = new ArrayList<String>();
		
		this.listaObservadores=new ArrayList<>();
		this._groupsRO = Collections.unmodifiableMap(mapa);
		
	}
	
	public void advance() {
		for(BodiesGroup b: mapa.values()) {//recorro las valores
			b.advance(tiempoRealPorPaso);
		}
		tiempoActual = tiempoActual+tiempoRealPorPaso;
		
		for(SimulatorObserver so:listaObservadores) {
			so.onAdvance(_groupsRO, tiempoActual);
		}
	}
	public void addGroup(String id) {
		for(BodiesGroup b: mapa.values()) {
			if(b.getId().equals(id)) {
				throw new IllegalArgumentException();
			}
		}
		BodiesGroup b = new BodiesGroup(id,leyesFuerza);
		mapa.put(id, b);
		lista.add(b.getId());
		
		for(SimulatorObserver so:listaObservadores) {
			so.onGroupAdded(_groupsRO, b);
		}
		
		
	}
	public void addBody(Body b) {
		BodiesGroup bs = mapa.get(b.gid);
		if(bs == null) {
			throw new IllegalArgumentException();
		}
		bs.addBody(b);
		for(SimulatorObserver so:listaObservadores) {
			so.onBodyAdded(_groupsRO, b);
		}
	}
	public void setForceLaws(String id, ForceLaws fl) {
		BodiesGroup bs = mapa.get(id);
		if(bs == null) {
			throw new IllegalArgumentException();
		}
		bs.setForceLaws(fl);
		
		for(SimulatorObserver so:listaObservadores) {
			so.onForceLawsChanged(bs);
		}
	}
	public JSONObject getState() {
		JSONObject state = new JSONObject();
		JSONArray aux = new JSONArray();
		
		for(String i: lista) {
			aux.put(mapa.get(i).getState());				
		}
		
		state.put("groups", aux);
		state.put("time", tiempoActual);
		
		return state;
	}
	public String toString() {
		return getState().toString();
	}

	@Override
	public void addObserver(SimulatorObserver o) {
		// busco que no este ya, si esta no lo añado
		boolean estaYaElObservador = false;
		for(SimulatorObserver so:listaObservadores) {
			if(so.equals(o)) {
				estaYaElObservador = true;
			}
		}
		if(estaYaElObservador == false) {
			listaObservadores.add(o);
			o.onRegister(_groupsRO, tiempoActual, tiempoRealPorPaso);
		}
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		// TODO Auto-generated method stub
		listaObservadores.remove(o);
	}
	public void reset() {
		mapa.clear();
		lista.clear();
		this.tiempoActual = 0.0;
		for(SimulatorObserver so:listaObservadores) {
			so.onReset(_groupsRO, tiempoActual, tiempoRealPorPaso);
		}
	}
	public void setDeltaTime(double dt) {
		if(dt<0)
			throw new IllegalArgumentException();
		this.tiempoRealPorPaso =dt;
		
		for(SimulatorObserver so:listaObservadores) {
			so.onDeltaTimeChanged(tiempoRealPorPaso);
		}
	}
	public int getNumeroGrupos() {
		return mapa.size();
	}

	public double getDeltaTime() {
		// TODO Auto-generated method stub
		return this.tiempoActual;
	}
}
