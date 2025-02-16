package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {

	private Vector2D c;
	private double g;
	private Vector2D F = new Vector2D(0,0);
	
	public MovingTowardsFixedPoint(Vector2D c,double g) {
		if(c==null || g <= 0 )
			throw new IllegalArgumentException("Invalid arguments");
	
		this.c = c;
		this.g = g;
	}
	public void apply(List<Body> bs) {
		for(Body i:bs) {
			/*Vector2D distancia = c.minus(i.p);
			distancia = distancia.direction();
			F = distancia.scale(i.m*g);
			i.addForce(F);*/
			i.addForce(c.minus(i.p).direction().scale(i.m*g));
		}
		
		
	}
	public String toString() {
		return "Moving towards "+c+" with constant acceleration "+g; 
	}

}
