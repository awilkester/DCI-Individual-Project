package main.java.data;

public abstract class User {
    protected String name;
    private boolean isAuthenticated = false;

    public User(String userName){
        this.name = userName;
    }
    public User(){}
    public abstract boolean authenticate(String username);

    //The isNamed method will return true if the name passed to the method equals the this.name property.
// Since name is protected we will need a way to check if the user is the one we want.
    public boolean isNamed(String name){
        if(name.equals(this.name)){
            return true;
        } else{
            return false;
        }
    }

    public abstract void greet();

    public abstract void bye(String user);

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
