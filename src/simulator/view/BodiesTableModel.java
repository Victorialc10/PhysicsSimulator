package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver{
	String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	List<Body> _bodies;
	private Controller _ctrl;
	
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		// TODO registrar this como observer
		_ctrl=ctrl;
		_ctrl.addObserver(this);
	}
	 // TODO el resto de métodos van aquí…

	@Override
	public String getColumnName(int col) {
		return _header[col];
	}
	public int getRowCount() {
		// TODO Auto-generated method stub
		//return _bodies.size();
		return _bodies == null ? 0 :_bodies.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex ==0) {
			Body bs = _bodies.get(rowIndex);
			return bs.getId();
		}
		else if(columnIndex == 1) {
			Body bs = _bodies.get(rowIndex);
			return bs.getgId();
		}
		else if(columnIndex == 2) {
			Body bs = _bodies.get(rowIndex);
			return bs.getMass();
		}
		if(columnIndex ==3) {
			Body bs = _bodies.get(rowIndex);
			return bs.getVelocity();
		}
		else if(columnIndex == 4) {
			Body bs = _bodies.get(rowIndex);
			return bs.getPosition();
		}
		else if(columnIndex == 5) {
			Body bs = _bodies.get(rowIndex);
			return bs.getForce();
		}
		
		return null;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		fireTableDataChanged() ;
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_bodies.clear();
		fireTableStructureChanged() ;//aa lo mejor es data
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_bodies = new ArrayList<>();
		for(BodiesGroup bs1: groups.values()) {
			for(Body body: bs1) {
				if(!_bodies.contains(body)) {
					_bodies.add(body);
				}
			}
		}
		fireTableStructureChanged() ;
		
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		fireTableStructureChanged() ;
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		_bodies.add(b);
		fireTableStructureChanged() ;
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		fireTableStructureChanged() ;
	}

}
