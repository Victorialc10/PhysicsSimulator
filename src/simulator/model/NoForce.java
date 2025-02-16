package simulator.model;

import java.util.List;

public class NoForce implements ForceLaws {

	@Override
	public void apply(List<Body> bs) {
		//no hace nada porque los cuerpos se mueven con aceleracion nula
		
	}
	
	public String toString() {
		return "No Force";
	}
}
