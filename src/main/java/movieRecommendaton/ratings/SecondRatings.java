package movieRecommendaton.ratings;

import movieRecommendaton.movies.Movie;
import movieRecommendaton.raters.Rater;

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings firstrating = new FirstRatings();
        myMovies = firstrating.loadMovies(moviefile);
        myRaters = firstrating.loadRaters(ratingsfile);
    }
    
    public int getMovieSize(){
        // return the number of movies.
        return myMovies.size();
    }
    
    public int getRaterSize(){
        // return the number of raters.
        return myRaters.size();
    }

    /**
     * @return A double representing the average movie rating for this ID
     * if there are at least minimalRaters ratings.
     * If there are not minimalRaters ratings, then it returns 0.0.
     */
    private double getAverageByID(String movieID, int minimalRaters){
        /* This method returns */
        int numRatings = 0;
        double totalScore = 0;
        for (Rater currRater: myRaters){
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
            return totalScore/numRatings;
        }
    }

    /**
     * This method finds the average rating for every movie that has been rated by at least
     *  minimalRaters raters.
     * @param minimalRaters
     * @return ArrayList of all the Rating objects for movies
     * that have at least the minimal number of raters supplying a rating.
     */
    public ArrayList<Rating> getAverageRatings(int minimalRaters){

        ArrayList<Rating> allAverageRatings = new ArrayList<Rating>();
        for (Movie currMovie: myMovies){
            String currMovieID = currMovie.getID();
            double averageRating = getAverageByID(currMovieID, minimalRaters);
            allAverageRatings.add(new Rating(currMovieID, averageRating));
        }
        return allAverageRatings;
    }

    /**
     * @return the title of the movie with that ID. If the movie ID does not exist,
     * then this method returns a String indicating the ID was not found
     */
    public String getTitle(String movieID){
        /*
         * This method returns .
         */
        for (Movie currMovie: myMovies){
            if (currMovie.getID().equals(movieID)){
                return currMovie.getTitle();
            }
        }
        return "N/A";
    }

    /**
     *
     * @param movieTitle
     * @return The movie ID of this movie. If the title is not found, returns "N/A".
     * Note that the movie title must be spelled exactly as it appears in the movie data files.
     */
    public String getID(String movieTitle){
        /*
         * This method returns
         */
        for (Movie currMovie: myMovies){
            if (currMovie.getTitle().equals(movieTitle)){
                return currMovie.getID();
            }
        }
        return "N/A";
    }
}
