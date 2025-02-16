package simulator.model;

import java.util.Map;

public interface SimulatorObserver {
	public void onAdvance(Map<String, BodiesGroup> groups, double time);
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt);
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt);
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g);
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b);
	public void onDeltaTimeChanged(double dt);
	public void onForceLawsChanged(BodiesGroup g);
}
