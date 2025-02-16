package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	
	private double G;
	private Vector2D F = new Vector2D(0,0);
	private Vector2D a = new Vector2D(1,1);
	
	public NewtonUniversalGravitation(double G) {
		
		if (G <= 0) 
			throw new IllegalArgumentException("Invalid arguments");
		this.G = G;
	}
	@Override
	public void apply(List<Body> bs) {
		double fij=0;
		double distancia = 0;
		Vector2D direccion;
		for(Body i:bs) {
			for (Body j:bs) {
				if(i!=j) {
					/*DISTANCIA en direccoin es un vector
					teng que hacer la suma de las F mayusc
					direccion = j.p.minus(i.p);
					direccion = direccion.direction();*/
					distancia = j.p.distanceTo(i.p);
					if(distancia >0) {
						/*fij =  G*i.m*j.m/(distancia*distancia);
						F= F.plus(direccion.scale(fij));
						F = F.plus(j.p.minus(i.p).scale(G*i.m*j.m/(j.p.distanceTo(i.p)*j.p.distanceTo(i.p))));*/
						
						F=F.plus(j.p.minus(i.p).direction().scale((G*i.m*j.m)/((j.p.distanceTo(i.p))*(j.p.distanceTo(i.p)))));
						
					}
					else {
						fij =  0;
	
					}
				}
			}
			i.addForce(F);
			//F = F.scale(0.0);
			F = new Vector2D();
			//System.out.println(fij + " fij");
			//System.out.println(distancia + " dist");
			//F = a.scale(Fij);
			//System.out.println(F + " F");
			//i.addForce(F);

		}
	}
	public String toString() {
		return "Newton’s Universal Gravitation with G= " + G;
	}

}
