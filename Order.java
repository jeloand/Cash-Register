public class Order
{
    private Item item;
    private int quantity;
    private double cost;
    private int orderNumber;

    public Order(Item item, int quantity, int orderNumber)
    {
        this.item = item;
        this.quantity = quantity;
        this.orderNumber = orderNumber;
        cost = item.getPrice() * quantity;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public double getCost() {
    	return cost;
    }
    
    public int getOrderNumber() {
        return orderNumber;
    }

    public Item getItem() {
        return item;
    }
    
    public int getItemCode() {
    	return item.getItemCode();
    }
    
    public double getPrice() {
    	return item.getPrice();
    }
    
    public String getItemName() {
    	return item.getName();
    }

    public int getQuantity() {
        return quantity;
    }

    /* public int takeOrder()
    {
        if (item.getQuantity() > quantity)
        {
            item.setQuantity(item.getQuantity() - quantity);
            return 1;
        }

        else
            return 0;
    } */
}