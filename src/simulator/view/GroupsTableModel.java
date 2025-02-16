package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver{
	String[] _header = { "Id", "Force Laws", "Bodies" };
	List<BodiesGroup> _groups;
	private Controller _ctrl;
	
	GroupsTableModel(Controller ctrl) {
		_groups = new ArrayList<>();
		this._ctrl=ctrl;
		// TODO registrar this como observador;
		_ctrl.addObserver(this);
	}
	 // TODO el resto de métodos van aquí …
	@Override
	public String getColumnName(int col) {
		return _header[col];
	}
	public int getRowCount() {
		// TODO Auto-generated method stub
		//return _groups.size();
		return _groups == null ? 0: _groups.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		String valor=" ";
		if(columnIndex ==0) {
			BodiesGroup bs = _groups.get(rowIndex);
			valor= bs.getId();
		}
		else if(columnIndex == 1) {
			BodiesGroup bs = _groups.get(rowIndex);
			valor = bs.getForceLawsInfo();
		}
		else if(columnIndex == 2) {
			
			BodiesGroup bs = _groups.get(rowIndex);
			for (Body b: bs) {
				valor += b.getId() + " ";
			}
		}
		return valor;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_groups.clear();
		fireTableStructureChanged();
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_groups = new ArrayList<>(groups.values());
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		_groups.add(g);
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
		fireTableDataChanged();
	}

}
