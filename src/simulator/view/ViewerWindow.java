package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ViewerWindow extends JFrame implements SimulatorObserver{
	private Controller _ctrl;
	private SimulationViewer _viewer;
	private JFrame _parent;
	ViewerWindow(JFrame parent, Controller ctrl) {
		super("Simulation Viewer");
		_ctrl = ctrl;
		_parent = parent;
		intiGUI();
		// TODO registrar this como observador
		_ctrl.addObserver(this);
	}
	private void intiGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		// TODO poner contentPane como mainPanel con scrollbars (JScrollPane)
		//this.setContentPane(mainPanel);
		JScrollPane j = new JScrollPane(mainPanel);
		setContentPane(j);
		// TODO crear el viewer y añadirlo a mainPanel (en el centro)
		_viewer = new Viewer();
		mainPanel.add(_viewer, BorderLayout.CENTER);
		// TODO en el método windowClosing, eliminar ‘this’ de los observadores
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				_ctrl.removeObserver(ViewerWindow.this);
			}
		});

		pack();
	
		if (_parent != null)
		setLocation(
		_parent.getLocation().x + _parent.getWidth()/2 - getWidth()/2,
		_parent.getLocation().y + _parent.getHeight()/2 - getHeight()/2);
		setVisible(true);
	}
	 // TODO otros métodos van aquí….
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		_viewer.update();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_viewer.reset();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		/*for(BodiesGroup bs: groups.values()) {
			_viewer.addGroup(bs);
			for(Body b:bs) {
				_viewer.addBody(b);
			}
		}*/
		_viewer.reset();
		for(BodiesGroup bs: groups.values()) {
			_viewer.addGroup(bs);
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		_viewer.addGroup(g);
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		_viewer.addBody(b);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

}
