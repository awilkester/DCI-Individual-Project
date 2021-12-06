package main.java.data;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private int id;
    List<Item> stock;
    //The constructor method will have an input argument that will be the id of the warehouse as an integer,
// and it will store this value in the object's id property. It does not require a stock argument because the
// stock will be managed with the addItem method. Instead, it must simply initialize the stock attribute as an empty list.
    public Warehouse(int id){
        this.id = id;
        this.stock = new ArrayList<>();
    }
    //The occupancy method will not take any argument and will return an integer representing the number of items in stock.
    public int occupancy(){
        return stock.size();
    }

    //The addItem method will take an instance of Item as an argument. This method should simply add the given object to the stock property.
    public void addItem(Item item){
        stock.add(item);
    }
    //The search method will search the items in the current warehouse and will return the list of matching items according
// to the searchTerm passed.

    public List<String> search(String searchTerm){
        List<String> result = new ArrayList<>();
        for(Item item : getStock()){
            if(searchTerm.equals(item.toString())){
                result.add(item.toString());
            }
        }
        return result;
    }


    public int getId() {return id;}

    public List<Item> getStock(){
        return stock;
    }

}
