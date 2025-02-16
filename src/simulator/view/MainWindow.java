package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import simulator.control.Controller;


@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	private Controller _ctrl;
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
		//no faltaria un setSize???
	}
	private void initGUI() {
	JPanel mainPanel = new JPanel(new BorderLayout());//el borderLayout divide en cuatro zonas el panel
	setContentPane(mainPanel);
	// TODO crear ControlPanel y añadirlo en PAGE_START de mainPanel
	ControlPanel cPanel = new ControlPanel(_ctrl);
	mainPanel.add(cPanel, BorderLayout.PAGE_START);//colocar el cPanel en la zona norte del mainPanel
	
	// TODO crear StatusBar y añadirlo en PAGE_END de mainPanel
	StatusBar sBar = new StatusBar(_ctrl);
	mainPanel.add(sBar, BorderLayout.PAGE_END);
	
	// Definición del panel de tablas (usa un BoxLayout vertical)
	JPanel contentPanel = new JPanel();
	contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	mainPanel.add(contentPanel, BorderLayout.CENTER);
	
	// TODO crear la tabla de grupos y añadirla a contentPanel.
	// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tamaño
	//JTable tablaDeGrupos = new JTable();
	//tablaDeGrupos.setPreferredSize(new Dimension(500,250));
	//contentPanel.add(tablaDeGrupos);
	// TODO crear la tabla de cuerpos y añadirla a contentPanel.
	// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tamaño
	//JTable tablaDeCuerpos = new JTable();
	//tablaDeCuerpos.setPreferredSize(new Dimension(500,250));
	//contentPanel.add(tablaDeCuerpos);
	
	InfoTable tabladeGrupos = new InfoTable("Groups", new GroupsTableModel(_ctrl));
	tabladeGrupos.setPreferredSize(new Dimension(500,250));
	contentPanel.add(tabladeGrupos);
	
	InfoTable tabladeCuerpos = new InfoTable("Bodies", new BodiesTableModel(_ctrl));
	tabladeCuerpos.setPreferredSize(new Dimension(500,250));
	contentPanel.add(tabladeCuerpos);
	// TODO llama a Utils.quit(MainWindow.this) en el método windowClosing
	this.addWindowListener(
		new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Utils.quit(MainWindow.this);
			}
		}
	);
	
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//que debe hacer el programa si cierra la ventana
	pack();
	setVisible(true);
	}
	
	
}
