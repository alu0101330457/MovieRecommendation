package movierecommendation.ratings;



import movierecommendation.movies.MovieDatabase;
import movierecommendation.raters.RaterDatabase;
import movierecommendation.filters.Filter;
import movierecommendation.filters.TrueFilter;
import movierecommendation.raters.Rater;

import java.util.*;

/**
 * Processes information in a CSV File about movies and ratings.
 */
public class FourthRatings {
    
    public FourthRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public FourthRatings(String ratingsfile) {
        RaterDatabase.initialize(ratingsfile);
    }

    /**
     * Returns the number of raters in the file.
     */
    public int getRaterSize(){
        // return the number of raters.
        return RaterDatabase.size();
    }

    /**
     * Returns the average movie rating for this ID if there are at least minimalRaters ratings.
     * @param movieID Movie to calculate the average rating of.
     * @param minimalRaters minimum number of raters necessary to return the average.
     * @return Average movie rating for movieID if there are at least minimalRater ratings, otherwise 0.0
     */
    private double getAverageByID(String movieID, int minimalRaters){
        int numRatings = 0;
        double totalScore = 0;
        for (Rater currRater: RaterDatabase.getRaters()){
            ArrayList<String> currMovies = currRater.getItemsRated();
            for (String s: currMovies){
                if (s.equals(movieID)){
                    numRatings += 1;
                    totalScore += currRater.getRating(movieID);
                }
            }
        }
        
        if (numRatings < minimalRaters){
            return 0.0;
        } else {
            numRatings = 1;
            return totalScore/numRatings;
        }
    }

    /**
     * Finds the average rating for every movie that has been rated by at least minimalRater
     * raters.
     * @param minimalRaters minimum number of raters to be considered.
     * @return ArrayList<Rating> containing all Rating objects for movies that have at least the
     * minimal number of raters supplying a rating.
     */
    public ArrayList<Rating> getAverageRatings(int minimalRaters){

        // Get the ArrayList of Movies from MovieDatabase.
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> allAverageRatings = new ArrayList<>();
        for (String currMovieID: movies){
            double averageRating = getAverageByID(currMovieID, minimalRaters);
            allAverageRatings.add(new Rating(currMovieID, averageRating));
        }
        return allAverageRatings;
    }

    /**
     * Creates and returns and ArrayList<Rating> of all the movies that have at
     * least minimalRaters ratings and satisfies the filter criteria.
     */
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<String> movieIDs = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> averageRatings = new ArrayList<>();
        for (String s: movieIDs){
            double ratingValue = getAverageByID(s, minimalRaters);
            averageRatings.add(new Rating(s, ratingValue));
        }
        return averageRatings;
    }

    /**
     * Translates a rating from 0-10 scale to -5 to 5 scale and returns the dot product
     * of the ratings of movies that they both rated.
     * <p>
     * This method is called by getSimilarities.
     */
    private double dotProduct(Rater me, Rater r){
        double result = 0.0;
        ArrayList<String> myMovieIDs = me.getItemsRated();
        ArrayList<String> otherMovieIDs = r.getItemsRated();
        for (String s: myMovieIDs){
            if (otherMovieIDs.contains(s)){
                double myValue = me.getRating(s) - 5;
                double otherValue = r.getRating(s) - 5;
                result += myValue*otherValue;
            }
        }
        return result;
    }

    /**
     * Computes a similarity rating for each rater in the RaterDatabase
     * (except the rater with the ID given by the parameter) to see how similar they are to the Rater
     * whose ID is the parameter to getSimilarities.
     * <p>
     * Note that in each Rating object the item field is a rater’s ID,
     * and the value field is the dot product comparison between that rater and the rater
     * whose ID is the parameter to getSimilarities.
     * @param id
     * @return ArrayList<Rating> sorted by ratings from highest to lowest rating with the highest rating first
     * and only including those raters who have a positive similarity rating since those with negative values
     * are not similar in any way.
     */
    private ArrayList<Rating> getSimilarities(String id){
        /*
         * this method computes a similarity rating for each rater in the RaterDatabase 
         *
         */
        ArrayList<Rating> list = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater r: RaterDatabase.getRaters()){
            String currOtherID = r.getID();
            if (!currOtherID.equals(id)){
                double currDotProduct = dotProduct(me, r);
                if (currDotProduct > 0.0){
                    list.add(new Rating(currOtherID, currDotProduct));
                }
            }
        }
        Collections.sort(list, Collections.reverseOrder());
        // The instance variables in every object of the arraylist are ID(String) and dotProduct(double).
        return list; 
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        /*
         * This method should return an ArrayList of type Rating, of movies and their weighted average ratings 
         * using only the top numSimilarRaters with positive ratings and including only those movies 
         * that have at least minimalRaters ratings from those most similar raters (not just minimalRaters ratings overall). 
         * For example, if minimalRaters is 3 and a movie has 4 ratings but only 2 of those ratings 
         * were made by raters in the top numSimilarRaters, that movie should not be included. 
         * These Rating objects should be returned in sorted order by weighted average rating from largest to smallest ratings. 
         * This method is very much like the getAverageRatings method you have written previously.
         */
        ArrayList<Rating> similarList = getSimilarities(id);
        // System.out.println("Similar raters list: " + similarList); // Test
        // Key: Movies' IDs.  Value: RaterID and ratring value.
        HashMap<String, HashMap<String, Double>> recMap = new HashMap<>();
        if (similarList.size() < numSimilarRaters){
            numSimilarRaters = similarList.size();
        }
        for (int k=0; k<numSimilarRaters; k++){
            String currRaterID = similarList.get(k).getItem();
            Rater currRater = RaterDatabase.getRater(currRaterID);
            ArrayList<String> ratedMovies = currRater.getItemsRated();
            for (String currMovie: ratedMovies){
                if (!recMap.containsKey(currMovie)){
                    HashMap<String, Double> first = new HashMap<>();
                    first.put(currRaterID, currRater.getRating(currMovie));
                    recMap.put(currMovie, first);
                } else {
                    recMap.get(currMovie).put(currRaterID, currRater.getRating(currMovie));
                }
            }
        }
        // System.out.println(recMap);
        
        ArrayList<Rating> result = new ArrayList<>();
        for (String currMovie : recMap.keySet()){
            HashMap<String, Double> currValueMap = recMap.get(currMovie);
            if (currValueMap.size() >= minimalRaters){
                double total = 0.0;
                for (String currRaterID: currValueMap.keySet()){
                    double currSimilarRating = 0.0;
                    // Find similar rating for the currRater.
                    for (Rating r: similarList){
                        if (r.getItem().equals(currRaterID)){
                            currSimilarRating = r.getValue();
                        }
                    }
                    total += currValueMap.get(currRaterID)*currSimilarRating;
                }
                double weightedAverage = total/currValueMap.size();
                result.add(new Rating(currMovie, weightedAverage));
            }
        }
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }
    
}
