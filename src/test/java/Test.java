package test.java;

import main.java.data.User;
import main.java.data.UserRepository;
import main.java.data.Warehouse;
import main.java.data.WarehouseRepository;

import static main.java.data.UserRepository.getAllEmployees;
import static main.java.data.WarehouseRepository.getWarehouseList;

public class Test {

  public static void main(String[] args) {
    //
      for (Warehouse warehouse : getWarehouseList()) {
          System.out.println(warehouse.toString());
      }

      for(User user : getAllEmployees()) {
      System.out.println(user.toString());
      }
  }
}
