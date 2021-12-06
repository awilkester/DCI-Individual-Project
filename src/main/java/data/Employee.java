package main.java.data;

import java.util.List;

import static main.java.TheWarehouseManager.session;

public class Employee extends User{

    private String password;
    List<Employee> headOf;

    public Employee(String userName, String password, List<Employee> headOf) {
        super(userName);
        this.password = password;
        this.headOf = headOf;
    }
    public Employee(String userName, String password) {
        super(userName);
        this.password = password;
    }

    //The authenticate method will also be overwritten, because in this case we need to check if the password is valid.
    // The method will return true if the argument password matches the property password of the object.
    @Override
    public boolean authenticate(String password){
        return this.password.equals(password);
    }

    //The order method will print the name of the item and amount ordered by the user when they place an order.
    public void order(String item, int amount){
    System.out.printf("You have ordered %d of %s.", amount, item);
    }

  // The greet method will also print a message, but the message to employees will be different:
  //
  // Hello, {name of the user}!
  // If you experience a problem with the system,
  // please contact technical support.

    @Override
    public void greet(){
        System.out.printf("Hello, %s !%n " +
                "If you experience a problem with the system,%n " +
                "please contact technical support.%n", getName());
    }
    //The bye method will also print a thank you message but, additionally, it will print the summary of actions
    // taken during the session. This method should call the parent method to print the message defined there and
    // then print the list of actions. It should not redefine the Thank you for your visit, {name}! message.

    @Override
    public void bye(String user){
        System.out.printf("\nThank you for your visit, %s!\n", this.name);
        if(session.getSessionActions().size() > 0){
            System.out.println("In this session you have :");
            session.listSessionActions();
        }else{
            System.out.println("In this session you have not done anything.");
        }
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

}
