package main.java.data;

public class Guest extends User{
    @Override
    public boolean authenticate(String username) {
        return false;
    }

    @Override
    public void greet() {
        System.out.printf("Hello, %s%n " +
                "Welcome to our Warehouse Database.%n " +
                "If you don't find what you are looking for,%n " +
                "please ask one of our staff members to assist you.%n", getName());
    }

    @Override
    public void bye(String user) {System.out.println("Goodbye, " + user);}
}
