package movierecommendation.movierunner;

import movierecommendation.ratings.Rating;
import movierecommendation.ratings.SecondRatings;

import java.util.*;

/**
 * Class that has functionalities of getting average ratings for movies.
 */
public class MovieRunnerAverage {
    
    private String movieFileName;
    private String ratingFileName;
    private SecondRatings sr;
    
    public MovieRunnerAverage() {
        movieFileName = "ratedmoviesfull.csv";
        ratingFileName = "ratings.csv";
        sr = new SecondRatings(movieFileName, ratingFileName);
    }

    /**
     * Prints the average ratings for the movies in the file.
     */
    public void printAverageRatings(){

        System.out.println("There are " + sr.getMovieSize() + " movies in the file.");
        System.out.println("There are " + sr.getRaterSize() + " raters in the file.");
        
        int numRating = 12;
        ArrayList<Rating> ratings = sr.getAverageRatings(numRating);
        Collections.sort(ratings);
        
        System.out.println("Rating values of Movies with at least " + numRating + " ratings:");
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                String currMovieID = currRating.getItem();
                System.out.println(currValue + "  " + sr.getTitle(currMovieID));
            }
        }
    }

    /**
     * Prints the average rating for a select Movie.
     * <p>
     * This method should first create a SecondRatings object, reading in data from the movie and ratings data files.
     * Then this method should print out the average ratings for a specific movie title
     */
    public void getAverageRatingOneMovie(){
        int numRating = 0;
        ArrayList<Rating> ratings = sr.getAverageRatings(numRating);
        
        String movieTitle = "Vacation";
        String movieID = sr.getID(movieTitle);
        double value = -1;
        for (Rating currRating: ratings){
            if (currRating.getItem().equals(movieID)){
                value = currRating.getValue();
            }
        }
        System.out.println("The average rating for " + movieTitle + " is " + value + "");
    }
}
