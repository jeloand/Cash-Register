import java.util.ArrayList;

public class Transaction
{
    private String cashier;
    private String customer;
    private int pwdScId;
    public ArrayList<Order> orders;
    private int totalItems;
    private double totalDue;
    private double subtotal;
    private double cash;
    public boolean discounted;
    private double lessVAT;
    private double salesNoVAT;
    private double lessDiscount;
    private double vatableSales;
    private double vatAmount;
    private double vatExemptSale;
    private static int transactionNumber = 0;
    private static final double VAT_RATE[] = {1.12, .12};
    private static final double DISCOUNT = .20;

    public Transaction() {
        orders = new ArrayList<Order>();
        totalDue = 0;
        totalItems = 0;
        subtotal = 0;
        discounted = false;
        transactionNumber++;
    }

    public Transaction(String cashier) {
        this();
        this.cashier = cashier;
    }

    public void addOrder(Order newOrder) {
        orders.add(newOrder);
        subtotal += newOrder.getPrice() * newOrder.getQuantity();
        totalItems += newOrder.getQuantity();
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    public void setCustomerName(String customer) {
    	this.customer = customer;
    }

    public void setPwdScID(int pwdScId) {
    	this.pwdScId = pwdScId;
    }

    public int getPwdScID() {
    	return pwdScId;
    }

    public String getCustomerName() {
    	return customer;
    }

    public String getCashier() {
        return cashier;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public double getCash() {
        return cash;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public double getChange() {
        return cash - totalDue;
    }

    public double getSubtotal() {
    	return subtotal;
    }

    public void addToSubtotal(double addCost) {
    	subtotal += addCost;
    }

    public void calculateTotal() {
        if (discounted)
        {
            lessVAT = subtotal - (subtotal / VAT_RATE[0]);
            salesNoVAT = subtotal / VAT_RATE[0];
            lessDiscount = (subtotal / VAT_RATE[0]) * DISCOUNT;
            totalDue = salesNoVAT - lessDiscount;
            vatableSales = subtotal - salesNoVAT;
            vatExemptSale = salesNoVAT;
        }

        else
        {
            totalDue = subtotal;
            vatableSales = totalDue / VAT_RATE[0];
            vatExemptSale = 0.00;
        }

        vatAmount = vatableSales * VAT_RATE[1];
    }

    public double getLessVAT() {
        return lessVAT;
    }

    public double getSalesNoVAT() {
        return salesNoVAT;
    }

    public double getVatExemptSale() {
    	return vatExemptSale;
    }

    public double getLessDiscount() {
        return lessDiscount;
    }

    public double getVatableSales() {
        return vatableSales;
    }

    public double getVatAmount() {
        return vatAmount;
    }
}
