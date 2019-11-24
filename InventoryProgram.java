import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class InventoryProgram {
	private JFrame frmInventory;
	private JTable table;
	private JTextField textField;
	private DefaultTableModel model;

    private JButton btnEditEntry;
    private JButton btnDelete;
	private JButton btnAddStock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryProgram window = new InventoryProgram();
					window.frmInventory.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InventoryProgram() {
        int loadInventorySuccesful = Inventory.loadInventory();
        if (loadInventorySuccesful == 1) {
            JOptionPane.showMessageDialog(frmInventory, "Inventory file not found.");
            System.exit(1);
        } else if (loadInventorySuccesful == 2) {
            JOptionPane.showMessageDialog(frmInventory, "Error in loading inventory file.");
            System.exit(1);
		}
        initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInventory = new JFrame();
		frmInventory.setTitle("Inventory");
		frmInventory.setBounds(150, 50, 700, 600);
		frmInventory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInventory.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 660, 30);
		frmInventory.getContentPane().add(panel);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(0, 0, 350, 30);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                loadTable();
			}
		});
		// btnRefresh.setEnabled(false);
		btnRefresh.setBounds(460, 0, 200, 30);
		panel.add(btnRefresh);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                search();
			}
		});
		btnSearch.setBounds(350, 0, 100, 30);
		panel.add(btnSearch);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 51, 660, 400);
		frmInventory.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 660, 400);
		panel_1.add(scrollPane);

		String[] colHeadings = {"Item", "Price", "Item Code", "Quantity"};
		String[][] data = {};
		DefaultTableModel model = new DefaultTableModel(data, colHeadings) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
		table = new JTable(model);
		loadTable();
		scrollPane.setViewportView(table);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 460, 660, 100);
		frmInventory.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JButton btnAddNewItem = new JButton("Add New Item");
		btnAddNewItem.setBounds(0, 0, 220, 40);
		panel_2.add(btnAddNewItem);
		btnAddNewItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewItem();
			}
		});

		btnDelete = new JButton("Delete");
		btnDelete.setBounds(0, 50, 220, 40);
		panel_2.add(btnDelete);
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEntry();
			}
		});

		btnEditEntry = new JButton("Edit Entry");
		btnEditEntry.setEnabled(false);
		btnEditEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editEntry();
			}
		});
		btnEditEntry.setBounds(230, 0, 220, 40);
		panel_2.add(btnEditEntry);

		btnAddStock = new JButton("Add Stock");
		btnAddStock.setBounds(230, 50, 220, 40);
		panel_2.add(btnAddStock);
		btnAddStock.setEnabled(false);
		btnAddStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStock();
			}
		});

		JButton btnOpenCashRegister = new JButton("Cash Register");
        btnOpenCashRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                CashRegister.main(null);
                btnOpenCashRegister.setEnabled(false);
            }
		});
		btnOpenCashRegister.setBounds(462, 0, 198, 88);
		panel_2.add(btnOpenCashRegister);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				btnEditEntry.setEnabled(true);
				btnDelete.setEnabled(true);
				btnAddStock.setEnabled(true);
        	}
		});
	}

	public void loadTable() {
		model = (DefaultTableModel) table.getModel();
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();

        ArrayList<Item> inventoryList = Inventory.getInventory();
		for (Item temp: inventoryList) {
			model.addRow(new Object[]
				{
					temp.getName(),
					Double.toString(temp.getPrice()),
					Integer.toString(temp.getItemCode()),
					Integer.toString(temp.getQuantity())
				}
			);

		}
	}

    public void loadTable(ArrayList<Item> searchResult) {
		model = (DefaultTableModel) table.getModel();
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();

		for (Item temp: searchResult) {
			model.addRow(new Object[]
				{
					temp.getName(),
					Double.toString(temp.getPrice()),
					Integer.toString(temp.getItemCode()),
					Integer.toString(temp.getQuantity())
				}
			);

		}
	}

	public void addNewItem() {
        btnDelete.setEnabled(false);
        btnEditEntry.setEnabled(false);
		btnAddStock.setEnabled(false);
		JTextField fieldItem = new JTextField();
		JTextField fieldPrice = new JTextField();
		JTextField fieldItemCode = new JTextField();
		JTextField fieldQuantity = new JTextField();
		Object[] message = {
		    "Item Name", fieldItem,
		    "Price", fieldPrice,
		    "Item Code", fieldItemCode,
		    "Quantity", fieldQuantity
		};

		int option = JOptionPane.showConfirmDialog(frmInventory, message, "Enter new item", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION)
		{
            try {
                Inventory.addItem(new Item(
    					fieldItem.getText().toUpperCase(),
    					Double.parseDouble(fieldPrice.getText()),
    					Integer.parseInt(fieldItemCode.getText()),
    					Integer.parseInt(fieldQuantity.getText())
    				)
    			);
                if (!Inventory.updateFile())
                    JOptionPane.showMessageDialog(frmInventory, "Error saving to file.");
    			loadTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frmInventory, "Error.");
            }
		}
	}

	public void editEntry() {
		int row = table.getSelectedRow();
		JTextField fieldItem = new JTextField(table.getModel().getValueAt(row, 0).toString());
		JTextField fieldPrice = new JTextField(table.getModel().getValueAt(row, 1).toString());
		JTextField fieldItemCode = new JTextField(table.getModel().getValueAt(row, 2).toString());
		JTextField fieldQuantity = new JTextField(table.getModel().getValueAt(row, 3).toString());
		Object[] message = {
		    "Item Name", fieldItem,
		    "Price", fieldPrice,
		    "Item Code", fieldItemCode,
		    "Quantity", fieldQuantity
		};

		int option = JOptionPane.showConfirmDialog(frmInventory, message, "Edit Entry", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION)
		{
            try {
    			Item temp = new Item(
    				fieldItem.getText().toUpperCase(),
    				Double.parseDouble(fieldPrice.getText()),
    				Integer.parseInt(fieldItemCode.getText()),
    				Integer.parseInt(fieldQuantity.getText())
    			);

    			Inventory.editItem(row, temp);
    			loadTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frmInventory, "Error.");
            }
        }
        btnEditEntry.setEnabled(false);
        btnDelete.setEnabled(false);
		btnAddStock.setEnabled(false);
	}

    public void deleteEntry() {
        Inventory.removeItem(table.getSelectedRow());
        loadTable();
        btnDelete.setEnabled(false);
        btnEditEntry.setEnabled(false);
		btnAddStock.setEnabled(false);
    }

    public void search() {
        loadTable(Inventory.search(textField.getText()));
    }

	public void addStock() {
		int toAdd = Integer.parseInt(JOptionPane.showInputDialog(frmInventory, "Input value of additional stock.", 0));
		Inventory.addStock(table.getSelectedRow(), toAdd);
		btnAddStock.setEnabled(false);
		loadTable();
	}
}
