package simulator.view;

import javax.swing.JComponent;

import simulator.model.BodiesGroup;
import simulator.model.Body;

@SuppressWarnings("serial")
abstract class SimulationViewer extends JComponent {
	abstract void addGroup(BodiesGroup g);

	abstract void addBody(Body b);

	abstract void update();

	abstract void reset();
}
