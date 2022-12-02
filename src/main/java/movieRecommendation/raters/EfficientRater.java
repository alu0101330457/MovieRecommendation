package movieRecommendation.raters;

import movieRecommendation.ratings.Rating;

import java.util.*;

/**
 * Implements a more efficient Rater for movies using a HashMap.
 */
public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String, Rating>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        if (myRatings.containsKey(item)){
            return true;
        }
        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        
        for (String s: myRatings.keySet()){
            if (s.equals(item)){
                return myRatings.get(s).getValue();
            }
        }
        
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for (String s: myRatings.keySet()){
            list.add(s);
        }
        return list;
    }
}

