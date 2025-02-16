package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class StatusBar  extends JPanel implements SimulatorObserver {
	 // TODO Añadir los atributos necesarios, si hace falta …
	private Controller _ctrl;
	private double dt;
	private int numGrupos;
	private JLabel _etiquetaTiempo;
	private JLabel _etiquetaGrupos;
	StatusBar(Controller ctrl) {
		initGUI();
		// TODO registrar this como observador
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		numGrupos = ctrl.getNumeroGrupos();
		dt = ctrl.getDeltaTime();
	}
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		// TODO Crear una etiqueta de tiempo y añadirla al panel
		_etiquetaTiempo = new JLabel("Time: " + dt, JLabel.CENTER);
		this.add(_etiquetaTiempo);
		// TODO Crear la etiqueta de número de grupos y añadirla al panel
		_etiquetaGrupos = new JLabel ("Groups" + numGrupos,JLabel.CENTER);
		this.add(_etiquetaGrupos);
		// TODO Utilizar el siguiente código para añadir un separador vertical
	
		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);
	}
	 // TODO el resto de métodos van aquí…
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		dt +=time;
		_etiquetaTiempo.setText("Time: " + dt );
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		numGrupos = groups.size();
		_etiquetaGrupos.setText("Groups: "+numGrupos);
		this.dt = dt;
		_etiquetaTiempo.setText("Time: "+this.dt);
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		numGrupos = groups.size();
		_etiquetaGrupos.setText("Groups: "+numGrupos);
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		this.dt = dt;
		_etiquetaTiempo.setText("Time: "+this.dt);
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
}
