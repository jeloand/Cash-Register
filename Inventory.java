import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Inventory {
    private static ArrayList<Item> inventory;

    public static ArrayList<Item> getInventory() {
        return inventory;
    }

    public static int getInventorySize() {
        // int size = 0;
        // for (Item temp: inventory)
        //     size++;
        return inventory.size();
    }


    // public static String[] getItemNames() {
    //     String[] menuList = new String[];
    //     int i = 0;
    //     for (Item temp: inventory) {
    //         menuList[i] = inventory.get(i).getName();
    //     }
    //     return menuList;
    // }

    public static String getItemName(int index) {
        return inventory.get(index).getName();
    }

    public static boolean isAvailable(String orderName, int orderQuantity) {
        for (Item temp: inventory) {
            if (orderName.equals(temp.getName())) {
                if (temp.getQuantity() >= orderQuantity)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    public static Item getItem(String orderName) {
        for (Item temp: inventory) {
            if (orderName.equals(temp.getName()))
                return temp;
        }
        return null;
    }

    public static void addStock(int index, int toAdd) {
        inventory.get(index).addQuantity(toAdd);
        updateFile();
    }

    public static void transact(String orderName, int orderQuantity) {
        int i = 0;
        for (Item temp: inventory) {
            if (orderName.equals(temp.getName()))
                inventory.get(i).deductQuantity(orderQuantity);
            i++;
        }
        updateFile();
    }

    public static void addItem(Item newItem) {
        inventory.add(newItem);
    }

    public static boolean updateFile() {
        try {
            FileWriter inventoryFile = new FileWriter("inventory.txt");
            String write = "";
            for (Item temp: inventory) {
                write += temp.getName() + "," + temp.getPrice() + "," + temp.getItemCode() + "," + temp.getQuantity() + "\n";
            }
            inventoryFile.write(write);
            inventoryFile.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void editItem(int index, Item editedItem) {
        inventory.set(index, editedItem);
        updateFile();
    }

    public static void removeItem(int index) {
        inventory.remove(index);
        updateFile();
    }

    public static ArrayList<Item> search(String searchTerm) {
        ArrayList<Item> result = new ArrayList<Item>();
        for (Item temp: inventory) {
            if (temp.getName().contains(searchTerm.toUpperCase()))
                result.add(temp);
        }
        return result;
    }

    public static int loadInventory() {
		inventory = new ArrayList<Item>();
    	try {
			Scanner inventoryFile = new Scanner(new File("inventory.txt"));
			int i = 0;

			while (inventoryFile.hasNextLine()) {
				String temp = inventoryFile.nextLine();
				String line[] = temp.split(",");

                inventory.add(i, new Item(
				               line[0],
					           Double.parseDouble(line[1]),
					           Integer.parseInt(line[2]),
					           Integer.parseInt(line[3])
				    )
				);
				i++;
			}
            inventoryFile.close();
            return 0;
		}
		catch (FileNotFoundException e) { return 1; }
        catch (Exception f) { return 2; }
	}
}
