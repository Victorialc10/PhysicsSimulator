package simulator.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class InfoTable extends JPanel{
	String _title;
	TableModel _tableModel;
	JTable tabla;
	JScrollPane scroll;
	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}
	private void initGUI() {
		// TODO cambiar el layout del panel a BorderLayout()
		this.setLayout(new BorderLayout());
		// TODO añadir un borde con título al JPanel, con el texto _title
		Border borde = BorderFactory.createTitledBorder(_title);
		this.setBorder(borde);
		// TODO añadir un JTable (con barra de desplazamiento vertical) que use_tableModel
		tabla = new JTable(_tableModel);
		scroll = new JScrollPane(tabla);
		this.add(scroll);
		
	}
}
