package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	PhysicsSimulator ph;
	Factory<Body> fb;
	Factory<ForceLaws> ffl;
	
	public Controller(PhysicsSimulator ph, Factory<Body> fb, Factory<ForceLaws> ffl) {
		this.ph = ph;
		this.fb = fb;
		this.ffl = ffl;
	}
	 
	public void loadData(InputStream in) {
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
		JSONArray aux = jsonInupt.getJSONArray("groups");
		//para cada identificador de grupo del JSONArray aux llamo a addGroup
		for(int i=0;i<aux.length();i++) {
			ph.addGroup(aux.getString(i));
		}
		if(jsonInupt.has("laws")) {
			//cojo los li
			JSONArray aux1 = jsonInupt.getJSONArray("laws");
			//cojo el id y laws dentro de cada li
			for(int i =0;i<aux1.length();i++) {
				JSONObject o = aux1.getJSONObject(i);
				ph.setForceLaws(o.getString("id"),ffl.createInstance(o.getJSONObject("laws")) );
			}		
		}
		
		JSONArray aux2 = jsonInupt.getJSONArray("bodies");
		for(int i =0;i<aux2.length();i++) {
			JSONObject o = aux2.getJSONObject(i);
			ph.addBody(fb.createInstance(o));
		}	
	}
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		p.println(ph.getState());
		// run the sumulation n steps, etc.
		for(int i=1;i<=n;i++) {
			ph.advance();
			p.print(",");
			JSONObject a = ph.getState();
			p.println(a);
		}
		p.println("]");
		p.println("}");
	}
	public void reset() {
		ph.reset();
	}
	public void setDeltaTime(double dt) {
		ph.setDeltaTime(dt);
	}
	public void addObserver(SimulatorObserver o) {
		ph.addObserver(o);
	}
	public void removeObserver(SimulatorObserver o) {
		ph.removeObserver(o);
	}
	public List<JSONObject> getForceLawsInfo(){
		return ffl.getInfo();
	}
	public void setForcesLaws(String gID, JSONObject info) {
		ph.setForceLaws(gID, ffl.createInstance(info));
	}
	public void run(int n) {
		for(int i=0;i<n;i++) {
			ph.advance();
		}
	}
	public double getDeltaTime() {
		return ph.getDeltaTime();
	 }
	 
	 public int getNumeroGrupos() {
		return ph.getNumeroGrupos();
	 }
}

//leo los inputStream que es un JSON y creo objetos
//el run ejecuta los mil pasos llamando a simulation, pone en un JSON object los estados
//el main crea el objeto controlador