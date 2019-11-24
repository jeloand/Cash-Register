import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class CashRegister {

	private JFrame frame;
	private JTextField textCashierName;
	private JTextField quantity;
	private JTable table;
	private JLabel total;
	private JLabel tax;
	private JLabel subtotal;
	private JTextArea receipt;
	private JButton btnCalculate;
	private JComboBox<String> menuList;

	String []menu;

	Transaction tr;
	int orderNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CashRegister window = new CashRegister();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CashRegister() {
		loadItems();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 780, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 326, 51);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblCashier = new JLabel("Cashier:");
		lblCashier.setBounds(10, 0, 65, 14);
		panel.add(lblCashier);

		textCashierName = new JTextField();
		textCashierName.setBounds(10, 22, 209, 24);
		panel.add(textCashierName);
		textCashierName.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(412, 11, 342, 100);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblSubtotal = new JLabel("Subtotal");
		lblSubtotal.setForeground(Color.YELLOW);
		lblSubtotal.setBounds(31, 11, 88, 14);
		panel_1.add(lblSubtotal);

		JLabel lblTax = new JLabel("Tax");
		lblTax.setForeground(Color.YELLOW);
		lblTax.setBounds(31, 36, 46, 14);
		panel_1.add(lblTax);

		JLabel lblGrandTotal = new JLabel("Grand total");
		lblGrandTotal.setForeground(Color.YELLOW);
		lblGrandTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGrandTotal.setBounds(31, 70, 119, 14);
		panel_1.add(lblGrandTotal);

		subtotal = new JLabel("0.00");
		subtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		subtotal.setForeground(Color.YELLOW);
		subtotal.setBounds(256, 11, 46, 14);
		panel_1.add(subtotal);

		total = new JLabel("0.00");
		total.setForeground(Color.YELLOW);
		total.setHorizontalAlignment(SwingConstants.RIGHT);
		total.setFont(new Font("Tahoma", Font.BOLD, 13));
		total.setBounds(221, 70, 81, 14);
		panel_1.add(total);

		tax = new JLabel("0.00");
		tax.setHorizontalAlignment(SwingConstants.RIGHT);
		tax.setForeground(Color.YELLOW);
		tax.setBounds(256, 40, 46, 14);
		panel_1.add(tax);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 65, 392, 51);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JLabel lblItemCode = new JLabel("Item:");
		lblItemCode.setBounds(10, 0, 81, 14);
		lblItemCode.setEnabled(false);
		panel_2.add(lblItemCode);

		JLabel lblQuantity = new JLabel("Qty:");
		lblQuantity.setBounds(173, 0, 46, 14);
		lblQuantity.setEnabled(false);
		panel_2.add(lblQuantity);

		quantity = new JTextField();
		quantity.setBounds(173, 18, 46, 22);
		quantity.setEnabled(false);
		panel_2.add(quantity);
		quantity.setColumns(10);

		JButton btnAddItem = new JButton("Add item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// try {

					String orderName = (String)menuList.getSelectedItem();
					int newQuantity = Integer.parseInt(quantity.getText());

					Item newItem = null;

					if (quantity.getText().equals(""))
						JOptionPane.showMessageDialog(frame, "Error.");

					else {
						if (newQuantity > 0) {
							if (Inventory.isAvailable(orderName, newQuantity)) {
								newItem = Inventory.getItem(orderName);

								orderNumber++;
								tr.addOrder(new Order(newItem, newQuantity, orderNumber));
								Inventory.transact(orderName, newQuantity);
								DefaultTableModel model = (DefaultTableModel) table.getModel();

								model.addRow(new Object[]
								{
									Integer.toString(newQuantity),
									tr.orders.get(orderNumber - 1).getItemName(),
									Integer.toString(tr.orders.get(orderNumber - 1).getItemCode()),
									Double.toString(tr.orders.get(orderNumber - 1).getCost())
								});
								subtotal.setText(Double.toString(tr.getSubtotal()));
								updateDisplay();
								btnCalculate.setEnabled(true);
							} else {
								JOptionPane.showMessageDialog(frame, "There are not enough " + orderName + " in inventory.");
							}
						}

						else
							JOptionPane.showMessageDialog(frame, "Quantity should be more than 0.");
					}

				// } catch (Exception f) {
				// 	JOptionPane.showMessageDialog(frame, "Error.");
				// }
			}
		});
		btnAddItem.setBounds(227, 1, 155, 39);
		btnAddItem.setEnabled(false);
		panel_2.add(btnAddItem);

		menuList = new JComboBox<>(menu);
		menuList.setBounds(10, 18, 153, 22);
		menuList.setEnabled(false);
		AutoCompletion.enable(menuList);
		panel_2.add(menuList);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 122, 392, 318);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 392, 318);
		panel_3.add(scrollPane_1);

		String[] colHeadings = {"Qty", "Item", "Item Code", "Cost"};
		String[][] data = {};
		DefaultTableModel model = new DefaultTableModel(data, colHeadings);
		table = new JTable(model);
		table.setEnabled(false);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(1).setPreferredWidth(220);
		columnModel.getColumn(2).setPreferredWidth(70);
		columnModel.getColumn(3).setPreferredWidth(62);
		scrollPane_1.setViewportView(table);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 451, 392, 79);
		frame.getContentPane().add(panel_4);
		panel_4.setLayout(null);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(10, 45, 182, 23);
		btnDelete.setEnabled(false);
		panel_4.add(btnDelete);

		JButton btnAddDiscount = new JButton("Add Discount");
		btnAddDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tr.setDiscounted(true);
				tr.setCustomerName(JOptionPane.showInputDialog(frame, "Senior Citizen/PWD Name:"));
				tr.setPwdScID(Integer.parseInt(JOptionPane.showInputDialog(frame, "OSCA/PWD ID:")));
				btnAddDiscount.setEnabled(false);
				updateDisplay();
			}
		});
		btnAddDiscount.setBounds(202, 45, 183, 23);
		btnAddDiscount.setEnabled(false);
		panel_4.add(btnAddDiscount);

		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!tr.orders.isEmpty())
					try {
						updateDisplay();
						tr.setCash(Double.parseDouble(JOptionPane.showInputDialog(frame, "Cash:")));

						if (tr.getChange() > 0) {
							generateReceipt();
							btnCalculate.setEnabled(false);
							btnAddDiscount.setEnabled(false);
							btnAddItem.setEnabled(false);
							menuList.setEnabled(false);
							quantity.setEnabled(false);
							JOptionPane.showMessageDialog(frame, "Change: " + round(tr.getChange(), 2));
							textCashierName.setEnabled(true);
						}

						else {
							JOptionPane.showMessageDialog(frame, "Cash is insufficient.");
						}
					}
					catch (Exception f) {
						JOptionPane.showMessageDialog(frame, "Error.");
					}

			}

		});
		btnCalculate.setBounds(202, 11, 183, 23);
		btnCalculate.setEnabled(false);
		panel_4.add(btnCalculate);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(412, 122, 342, 408);
		frame.getContentPane().add(panel_5);
		panel_5.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 0, 342, 408);
		panel_5.add(scrollPane_2);

		receipt = new JTextArea();
		receipt.setFont(new Font("Monospaced", Font.PLAIN, 11));
		receipt.setEditable(false);
		scrollPane_2.setViewportView(receipt);

		JButton btnCreateNew = new JButton("New");
		btnCreateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddDiscount.setEnabled(true);
				menuList.setEnabled(true);
				quantity.setEnabled(true);
				btnAddItem.setEnabled(true);
				table.setEnabled(true);
				lblItemCode.setEnabled(true);
				lblQuantity.setEnabled(true);
				textCashierName.setEnabled(false);
				receipt.setText(null);
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				clearDisplay();

				orderNumber = 0;
				tr = new Transaction(textCashierName.getText());
			}
		});
		btnCreateNew.setBounds(10, 11, 182, 23);
		panel_4.add(btnCreateNew);
	}

	private void updateDisplay() {
		tr.calculateTotal();
		tax.setText(Double.toString(round(tr.getVatAmount(), 2)));
		total.setText(Double.toString(round(tr.getTotalDue(), 2)));
	}

	private void loadItems()
	{
		Inventory.loadInventory();
		// menu = Inventory.getItemNames();
		int inventorySize = Inventory.getInventorySize();
		menu = new String[inventorySize];

		for (int i = 0; i < inventorySize; i++)
			menu[i] = Inventory.getItemName(i);
		Arrays.sort(menu);
	}

	public static double round(double value, int places)
	{
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	private void clearDisplay() {
		subtotal.setText("0.00");
		tax.setText("0.00");
		total.setText("0.00");
	}

	void generateReceipt() {
		SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		DecimalFormat money = new DecimalFormat("###,##0.00");
		receipt.setText(
			"              JOLLIBEE DARAGA              \r\n" +
			"         Freemont Foods Corporation        \r\n" +
			"  Rizal St. Market Site POB. Daraga Albay  \r\n" +
			"        VAT Reg TIN: 003-460-168-112       \r\n" +
			"\n" +
			" Cashier: " + tr.getCashier() + "\n" +
			" ==========================================\r\n " +
			date.format(new Date(System.currentTimeMillis())) +
			"             OR#" + String.format("%010d", tr.getTransactionNumber()) +
			"\n ==========================================\r\n"
		);

		for (Order temp: tr.orders) {
			receipt.append(
				"  " + String.format("%3d", temp.getQuantity()) + " " + String.format("%1$-" + 26 + "s", temp.getItemName()) +
				String.format("%10s", money.format(temp.getCost())) + "\n"
			);
		}

		receipt.append(
			" ******                          **********\n  " +
			String.format("%3d", tr.getTotalItems()) + " Item(s)" + String.format("%29s", money.format(tr.getSubtotal())) + "\n\n"
		);

		if (tr.discounted) {
			receipt.append(
				"     " + String.format("%-17s", "Less: VAT") + String.format("%20s", money.format(tr.getLessVAT())) + "\n" +
				"     " + String.format("%-17s", "Sales without VAT") + String.format("%20s", money.format(tr.getSalesNoVAT())) + "\n" +
				"     " + String.format("%-17s", "Less: PWD DISC") + String.format("%20s", money.format(tr.getLessDiscount())) + "\n\n"
			);
		}

		receipt.append(
			"     " + String.format("%-17s", "TOTAL DUE") + String.format("%20s", money.format(tr.getTotalDue())) + "\n\n" +
			"     " + String.format("%-17s", "CASH") + String.format("%20s", money.format(tr.getCash())) + "\n" +
			"     " + String.format("%-17s", "CHANGE") + String.format("%20s", money.format(tr.getChange())) + "\n\n" +
			"     " + String.format("%-17s", "VATable Sales") + String.format("%20s", money.format(tr.getVatableSales())) + "\n" +
			"     " + String.format("%-17s", "VAT-Exempt Sales") + String.format("%20s", money.format(tr.getVatExemptSale())) + "\n" +
			"     " + String.format("%-17s", "VAT Amount") + String.format("%20s", money.format(tr.getVatAmount())) + "\n\n"
		);

		if (tr.discounted)
			receipt.append(
				" " + String.format("%-12s", "SC/PWD Name:") + tr.getCustomerName() + "\n" +
				" " +String.format("%-12s", "OSCA/PWD ID:") + tr.getPwdScID() + "\n\n"
			);

		receipt.append(
			"      We love to hear your feedback...     \r\n" +
			"           Call: (02) 8-898-7777           \r\n" +
			"         Text Only: (0917) 131-8000        \r\n" +
			"      Email: feedback@jollibee.com.ph      \r\n" +
			"        Website: www.jollibee.com.ph       \r\n" +
			"                                           \r\n" +
			"     This serves as an OFFICIAL RECEIPT    \r\n" +
			"  THIS INVOICE/RECEIPT SHALL BE VALID FOR  \r\n" +
			"    FIVE (5) YEARS FROM THE DATE OF THE    \r\n" +
			"               PERMIT TO USE.              \r\n"
		);
	}
}
