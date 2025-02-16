package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{

	private Vector2D a = new Vector2D();
	
	public MovingBody(String id, String gid, Vector2D p, Vector2D v, double m){
		super(id, gid, p, v, m);
	}

	@Override
	void advance(double dt) {
		if(m != 0) {
			a = f.scale(1.0/m);
		}
		else {
			a = a.scale(0);
		}

		//Vector2D aux = v.scale(dt);
		
		//Vector2D aux1 = a.scale(dt*dt*0.5);
		p = p.plus(v.scale(dt).plus(a.scale(dt*dt*0.5)));
		//p = p.plus(aux1);
		
		//Vector2D aux3 = a.scale(dt);
		v = v.plus(a.scale(dt));
	}

}
