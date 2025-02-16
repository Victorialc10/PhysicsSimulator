package extra.dialog.ex2;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class JSONBuilderDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private int _status;
	private JSONTableModel _dataTableModel;
	private String[][] _jsonSrc = { { "name", "age", "weight" }, //
			{ "city", "country" }, //
			{ "team", "points", "game", "value" } //
	};

	// This table model stores internally the content of the table. Use
	// getData() to get the content as JSON.
	//
	private class JSONTableModel extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] _header = { "Key", "Value" };
		String[][] _data;

		JSONTableModel() {
			initData(0);
		}

		public void initData(int dataIdx) {
			String[] keys = _jsonSrc[dataIdx];
			_data = new String[keys.length][2];
			for (int i = 0; i < keys.length; i++) {
				_data[i][0] = keys[i];
				_data[i][1] = "";
			}
			fireTableStructureChanged();
		}

		@Override
		public String getColumnName(int column) {
			return _header[column];
		}

		@Override
		public int getRowCount() {
			return _data.length;
		}

		@Override
		public int getColumnCount() {
			return _header.length;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex != 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return _data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object o, int rowIndex, int columnIndex) {
			_data[rowIndex][columnIndex] = o.toString();
		}

		// Method getData() returns a String corresponding to a JSON structure
		// with column 1 as keys and column 2 as values.

		// This method return the coIt is important to build it as a string, if
		// we create a corresponding JSONObject and use put(key,value), all values
		// will be added as string. This also means that if users want to add a
		// string value they should add the quotes as well as part of the
		// value (2nd column).
		//
		public String getData() {
			StringBuilder s = new StringBuilder();
			s.append('{');
			for (int i = 0; i < _data.length; i++) {
				if (!_data[i][1].isEmpty()) {
					s.append('"');
					s.append(_data[i][0]);
					s.append('"');
					s.append(':');
					s.append(_data[i][1]);
					s.append(',');
				}
			}

			if (s.length() > 1)
				s.deleteCharAt(s.length() - 1);
			s.append('}');

			return s.toString();
		}
	}

	JSONBuilderDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}

	private void initGUI() {

		_status = 0;

		setTitle("Build JSON from Table");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		// help
		JLabel help = new JLabel("<html><p>Fill in the table and click OK</p></html>");

		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// data table
		_dataTableModel = new JSONTableModel();
		JTable dataTable = new JTable(_dataTableModel) {
			private static final long serialVersionUID = 1L;

			// we override prepareRenderer to resize columns to fit to content
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tabelScroll);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// buttons
		JPanel ctrlPanel = new JPanel();
		ctrlPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(ctrlPanel);

		// combox box for selecting the data src
		JComboBox<String> dataSelector = new JComboBox<String>();
		for (int i = 0; i < _jsonSrc.length; i++)
			dataSelector.addItem("DATA-" + i);
		ctrlPanel.add(dataSelector);
		dataSelector.addActionListener((e) -> {
			_dataTableModel.initData(dataSelector.getSelectedIndex());
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				JSONBuilderDialog.this.setVisible(false);
			}
		});
		ctrlPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				JSONBuilderDialog.this.setVisible(false);
			}
		});
		ctrlPanel.add(okButton);

		setPreferredSize(new Dimension(400, 400));

		pack();
		setResizable(false); // change to 'true' if you want to allow resizing
		setVisible(false); // we will show it only whe open is called
	}

	public int open() {

		if (getParent() != null)
			setLocation(//
					getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2, //
					getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
		return _status;
	}

	public String getJSON() {
		return _dataTableModel.getData();
	}

}
