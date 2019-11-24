public class Item
{
	private String name;
    private double price;
    private int itemCode;
    private int quantity;

    public Item(String name, double price, int itemCode, int quantity)
    {
        this.name = name;
        this.price = price;
        this.itemCode = itemCode;
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	public void deductQuantity(int value) {
		quantity -= value;
	}

	public void addQuantity(int value) {
		quantity += value;
	}

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getItemCode() {
        return itemCode;
    }

    public int getQuantity() {
        return quantity;
    }
}
