package main.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static main.java.data.StockRepository.getAllItems;

public class Session {
    private static List<String> SESSION_ACTIONS = new LinkedList<>();



    public void addToSession(int x, String name){
        switch(x){
            case 1:
                SESSION_ACTIONS.add("Listed "+ getTotalListedItems() +" items.");
                break;
            case 2:
                SESSION_ACTIONS.add("Searched " + getAppropriateIndefiniteArticle(name) + name);
                break;
            case 3:
                SESSION_ACTIONS.add("Browsed the category " + name);
                break;
            default:
                break;
        }
    }

    public void listSessionActions(){
        int counter = 1;
        for(String x: getSessionActions()){
            System.out.printf("%d. %s", counter, x);
            counter++;
        }
    }

    public List<String> getSessionActions(){
        return SESSION_ACTIONS;
    }

    private String getAppropriateIndefiniteArticle(String x){
        ArrayList<String> vowels = new ArrayList<>(5);
        vowels.add("a");
        vowels.add("e");
        vowels.add("i");
        vowels.add("o");
        vowels.add("u");
        boolean startsWithVowels = false;
        for(String z : vowels){
            if (x.startsWith(z)) {
                startsWithVowels = true;
                break;
            }
        }
        if(startsWithVowels){
            return "an";
        }else{
            return "a";
        }

    }

    private int getTotalListedItems(){
        return getAllItems().size();
    }
}
