package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements SimulatorObserver {
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton;
	 // TODO añade más atributos aquí …
	private JButton _archivoButton;
	private JButton _leyButton;
	private JButton _visualButton;
	private JButton _runButton;
	private JButton _stopButton;
	private ForceLawsDialog fld = null;
	//private ViewerWindow vw;
	
	private JSpinner numeroPasosSpinner;
	private JTextField deltaTimeTextField;
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		// TODO registrar this como observador
		_ctrl.addObserver(this); 
	}
	private void initGUI() {
	setLayout(new BorderLayout());
	_toolaBar = new JToolBar();
	add(_toolaBar, BorderLayout.PAGE_START);
	// TODO crear los diferentes botones/atributos y añadirlos a _toolaBar.
	// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
	 // _toolaBar.addSeparator() para añadir la línea de separación vertical
	 // entre las componentes que lo necesiten
	_fc =new JFileChooser();
	
	//Archivo button
	//_toolaBar.addSeparator();
	_archivoButton = new JButton();
	_archivoButton.setToolTipText("Selector fichero");
	_archivoButton.setIcon(new ImageIcon("resources/icons/open.png"));
	_archivoButton.addActionListener((e) -> abrirSelector());
	_toolaBar.add(_archivoButton);

	//Ley button
	_toolaBar.addSeparator();
	_leyButton = new JButton();
	_leyButton.setToolTipText("cambia la ley de la fuerza");
	_leyButton.setIcon(new ImageIcon("resources/icons/physics.png"));
	_leyButton.addActionListener((e) -> cambioLeyFuerza());
	_toolaBar.add(_leyButton);
	
	//Visual button
	//_toolaBar.addSeparator();
	_visualButton = new JButton();
	_visualButton.setToolTipText("ver representacion visual de la simulacion");
	_visualButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
	_visualButton.addActionListener((e) -> new ViewerWindow ((JFrame) SwingUtilities.getWindowAncestor(this), _ctrl));
	_toolaBar.add(_visualButton);
	
	//Run button
	_toolaBar.addSeparator();
	_runButton = new JButton();
	_runButton.setToolTipText("deshabilita botones excepto stop y hace run");
	_runButton.setIcon(new ImageIcon("resources/icons/run.png"));
	_runButton.addActionListener((e) -> runBoton());
	_toolaBar.add(_runButton);
	
	//Stop Button
	//_toolaBar.addSeparator();
	_stopButton = new JButton();
	_stopButton.setToolTipText("detiene run");
	_stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
	_stopButton.addActionListener((e) -> _stopped=true);
	_toolaBar.add(_stopButton);
	
	numeroPasosSpinner = new JSpinner(new SpinnerNumberModel(10000,1,10000,100));
	deltaTimeTextField = new JTextField(6);
	//_toolaBar.add("Steps:", numeroPasosSpinner);
	numeroPasosSpinner.setMaximumSize(new Dimension(80, 40));
	numeroPasosSpinner.setMinimumSize(new Dimension(80, 40));
	numeroPasosSpinner.setPreferredSize(new Dimension(80, 40));
	deltaTimeTextField.setMaximumSize(new Dimension(70, 70));
	deltaTimeTextField.setMinimumSize(new Dimension(70, 70));
	_toolaBar.addSeparator();
	JLabel etiqueta = new JLabel("Steps: ");
	_toolaBar.add(etiqueta);
	_toolaBar.add(numeroPasosSpinner);//spinner
	_toolaBar.addSeparator();
	JLabel etiqueta2 = new JLabel("Delta-Time: ");
	_toolaBar.add(etiqueta2);
	deltaTimeTextField.setText("250");
	_toolaBar.add(deltaTimeTextField); //textfield
	
	// Quit Button
	_toolaBar.add(Box.createGlue()); // this aligns the button to the right
	_toolaBar.addSeparator();
	_quitButton = new JButton();
	_quitButton.setToolTipText("Quit");
	_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
	_quitButton.addActionListener((e) -> Utils.quit(this));
	_toolaBar.add(_quitButton);
	 // TODO crear el selector de ficheros
	}
	private void abrirSelector() {
		//fileinputstrem, get selected data
		int r =_fc.showOpenDialog(Utils.getWindow(this));
		if(r==JFileChooser.APPROVE_OPTION) {
			File f = _fc.getSelectedFile();
			try {
				_ctrl.reset();
				_ctrl.loadData(new FileInputStream (f));
			}
			catch (Exception e) {
				Utils.showErrorMsg("No se ha podido cargar");
			}
		}
		
	}
	private void cambioLeyFuerza() {
		if(fld == null) {
			//esto de utils lo he puesto yo, preguntar 
			fld = new ForceLawsDialog(Utils.getWindow(this), _ctrl);
		}		
		fld.open();
	}
	private void runBoton() {
		_archivoButton.setEnabled(false);
		_leyButton.setEnabled(false);
		_visualButton.setEnabled(false);
		_runButton.setEnabled(false);
		_stopped = false;
		
		double text = Double.parseDouble(deltaTimeTextField.getText());
		_ctrl.setDeltaTime(text);
		run_sim((Integer)numeroPasosSpinner.getValue());
	}
	private void run_sim(int n) {
		 if (n > 0 && !_stopped) {
			 try {
				 _ctrl.run(1);
			 } catch (Exception e) {
				 // TODO llamar a Utils.showErrorMsg con el mensaje de error que corresponda
				 Utils.showErrorMsg("No es posible hacer run");
				 // TODO activar todos los botones
				 _archivoButton.setEnabled(true);
				_leyButton.setEnabled(true);
				_visualButton.setEnabled(true);
				_runButton.setEnabled(true);
				_stopped = true;
				return;
			 }
			 SwingUtilities.invokeLater(() -> run_sim(n - 1));
		 } else {
			 // TODO llamar a Utils.showErrorMsg con el mensaje de error que corresponda
			// Utils.showErrorMsg("No es posible porque n<0 o stopped es falso");
			 // TODO activar todos los botones
			 _archivoButton.setEnabled(true);
			_leyButton.setEnabled(true);
			_visualButton.setEnabled(true);
			_runButton.setEnabled(true);
			_stopped = true;
		 }
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		String deltaTime = String.valueOf(dt);
		deltaTimeTextField.setText(deltaTime);
		
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	 // TODO el resto de métodos van aquí…
}
