package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ForceLawsDialog extends JDialog implements SimulatorObserver{
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	 // TODO en caso de ser necesario, añadir los atributos aquí…
	private int _selectedLawsIndex;
	private int _status;
	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		_forceLawsInfo = _ctrl.getForceLawsInfo();// _forceLawsInfo se usará para establecer la información en la tabla
		initGUI();
		// TODO registrar this como observer;
		_ctrl.addObserver(this);
	}
	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		// help
		JLabel help = new JLabel("<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	
		// TODO crear un JTable que use _dataTableModel, y añadirla al panel
		_dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO hacer editable solo la columna 1
				return column !=0;
			}
		};
	//_dataTableModel.setColumnIdentifiers(_headers);	
	
	//esto lo creo yo
		
		JTable dataTabla = new JTable(_dataTableModel);
		JScrollPane scroll= new JScrollPane(dataTabla);
		dataTabla.setPreferredSize(new Dimension(200,500));
		_dataTableModel.setColumnIdentifiers(_headers);	
		mainPanel.add(scroll);
			
	_lawsModel = new DefaultComboBoxModel<>();
	
	
	// TODO añadir la descripción de todas las leyes de fuerza a _lawsModel
	for(JSONObject j: _forceLawsInfo) {
		_lawsModel.addElement(j.getString("desc"));
	}
	
	
	// TODO crear un combobox que use _lawsModel y añadirlo al panel
	JComboBox <String>comboLeyes = new JComboBox<String>(_lawsModel);
	comboLeyes.setPreferredSize(new Dimension(200,20));
	JLabel ForceLawsEtiqueta = new JLabel("Force Law: ");
	//mainPanel.add(ForceLawsEtiqueta);
	//mainPanel.add(comboLeyes);
	//esto lo hago yo
		comboLeyes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_selectedLawsIndex = comboLeyes.getSelectedIndex();
				JSONObject info = _forceLawsInfo.get(_selectedLawsIndex);
				JSONObject data = info.getJSONObject("data");
				_dataTableModel.setNumRows(data.keySet().size());
				int i=0;
				for(String s : data.keySet()) {
					_dataTableModel.setValueAt(s, i, 0);
					_dataTableModel.setValueAt("", i, 1);
					_dataTableModel.setValueAt(data.getString(s), i, 2);
					i++;
				}
				
			}
		});
	_groupsModel = new DefaultComboBoxModel<>();
	// TODO crear un combobox que use _groupsModel y añadirlo al panel
	JComboBox <String>comboGrupos = new JComboBox<String>(_groupsModel);
	comboGrupos.setPreferredSize(new Dimension(50,20));
	JLabel GroupEtiqueta = new JLabel("Group: ");
	//mainPanel.add(GroupEtiqueta);
	//mainPanel.add(comboGrupos);
	JPanel combo = new JPanel();
	combo.setAlignmentX(CENTER_ALIGNMENT);
	mainPanel.add(combo);
	combo.add(ForceLawsEtiqueta);
	combo.add(comboLeyes);
	combo.add(GroupEtiqueta);
	combo.add(comboGrupos);
	
	// TODO crear los botones OK y Cancel y añadirlos al panel
	JButton okButton = new JButton("ok");
	JButton cancelButton = new JButton ("cancel");
	okButton.setPreferredSize(new Dimension(100,30));
	cancelButton.setPreferredSize(new Dimension(100,30));
	
	JPanel botones = new JPanel();
	botones.setAlignmentX(CENTER_ALIGNMENT);
	mainPanel.add(botones);
	botones.add(okButton);
	botones.add(cancelButton);
	
	//esto lo hago yo
	cancelButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			_status =0;
			setVisible(false);
		}	
	});
	
	okButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try{
				StringBuilder s = new StringBuilder();
				s.append('{');
				for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
					String k = _dataTableModel.getValueAt(i, 0).toString();
					String v = _dataTableModel.getValueAt(i, 1).toString();
					if (!v.isEmpty()) {
						s.append('"');
						s.append(k);
						s.append('"');
						s.append(':');
						s.append(v);
						s.append(',');
					}
				}

				if (s.length() > 1)
					s.deleteCharAt(s.length() - 1);
				s.append('}');

				JSONObject fl = new JSONObject("{\"type\":" + _forceLawsInfo.get(_selectedLawsIndex).getString("type")
						+ ", \"data\":" + s.toString() + "}");
				
				_ctrl.setForcesLaws(_groupsModel.getSelectedItem().toString(),fl);
				_status =1;
				setVisible(false);
			}catch(Exception e1) {
				Utils.showErrorMsg("No ha sido posible hacer todos los pasos del forcedialog");
			}
			
		}
	});
	
	setPreferredSize(new Dimension(700, 400));
	pack();
	setResizable(false);
	setVisible(false);
	}
	//public void open() {
	public int open() {
	if (_groupsModel.getSize() == 0)
	return _status;
	// TODO Establecer la posición de la ventana de diálogo de tal manera que se abra en el centro de la ventana principal
	//setLocation(_selectedLawsIndex, _selectedLawsIndex);
	Dimension tamano = this.getParent().getSize();
	int altura = tamano.height;
	int ancho = tamano.width;
	setSize(ancho/2,altura/2);
	setLocation(ancho/4,altura/4);
	//this.setMaximumSize(new Dimension(10,10));
	/*if (this.getParent() != null) {
		setLocation(
				this.getParent() .getLocation().x + this.getParent() .getWidth()/4 - getWidth()/4,
				this.getParent() .getLocation().y + this.getParent() .getHeight()/4 - getHeight()/4);
		setSize(this.getParent().getWidth()/2,this.getParent().getHeight()/2);
	}*/
		
	pack();
	setVisible(true);
	return _status;
	}
	 // TODO el resto de métodos van aquí…
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_groupsModel.removeAllElements();
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_groupsModel.addAll(groups.keySet());
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		_groupsModel.addElement(g.getId());
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
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
