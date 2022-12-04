package movierecommendation.movierunner;

import movierecommendation.movies.MovieDatabase;
import movierecommendation.ratings.Rating;
import movierecommendation.filters.*;
import movierecommendation.ratings.ThirdRatings;

import java.lang.System.Logger;
import java.util.*;

/**
 * Class that has functionalities of retrieving similar movies using Filters.
 */
public class MovieRunnerWithFilters {
    private String ratingFileName;
    private ThirdRatings tr;
    private Logger log;

    static final String THEREARE = "There are ";
    static final  String INTHEFILE = "in the file";

    static final String MOVIES = " movies ";
    static final String RATINGS = " ratings ";

    static final String RATINGVALUES = "Rating values of movies with at least ";

    static final String MOVIESHAVE = " movies have at least ";
    
    public MovieRunnerWithFilters (){
        ratingFileName = "ratings.csv";
        tr = new ThirdRatings(ratingFileName);
    }

    /**
     * Prints the average ratings of a number of movies.
     */
    public void printAverageRatings(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        log.log(Logger.Level.INFO, THEREARE+ MovieDatabase.size() + MOVIES + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE+ tr.getRaterSize() + RATINGS + INTHEFILE);
        
        int numRating = 35;
        ArrayList<Rating> ratings = tr.getAverageRatings(numRating);
        Collections.sort(ratings);
        
        int num = 0;
        log.log(Logger.Level.INFO, RATINGVALUES + numRating + " ratings:");
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                log.log(Logger.Level.INFO, currValue + "  " + MovieDatabase.getTitle(currMovieID));
            }
        }
        log.log(Logger.Level.INFO, THEREARE+ num + MOVIESHAVE + numRating + " ratings.");
    }

    /**
     * Prints the average ratings of movies released in a select year.
     */
    public void printAverageRatingsByYearAfter(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        log.log(Logger.Level.INFO, THEREARE+ MovieDatabase.size() + " movies " + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE+ tr.getRaterSize() + RATINGS + INTHEFILE);
        
        int numRating = 20;
        Filter filterYear = new YearAfterFilter(2000);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filterYear);
        Collections.sort(ratings);
        
        int num = 0;
        log.log(Logger.Level.INFO, RATINGVALUES + numRating + " ratings:");
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                log.log(Logger.Level.INFO, currValue + "  " + MovieDatabase.getYear(currMovieID) + "  " +MovieDatabase.getTitle(currMovieID));
            }
        }
        log.log(Logger.Level.INFO, THEREARE+ num + MOVIESHAVE + numRating + " ratings and satisfy the filter.");
    }

    /**
     * Prints the average ratings of movies of a select genre.
     */
    public void printAverageRatingsByGenre(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        log.log(Logger.Level.INFO, THEREARE+ MovieDatabase.size() + " movies " + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE+ tr.getRaterSize() + RATINGS + INTHEFILE);
        
        int numRating = 20;
        Filter filterGenre = new GenreFilter("Comedy");
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filterGenre);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                log.log(Logger.Level.INFO, currValue + "  " + MovieDatabase.getTitle(currMovieID));
                log.log(Logger.Level.INFO, "     " + MovieDatabase.getGenres(currMovieID));
            }
        }
        log.log(Logger.Level.INFO, THEREARE+ num + MOVIESHAVE + numRating + " ratings and satisfy the filter.");
    }

    /**
     * Prints the average ratings of movies of a certain duration.
     */
    public void printAverageRatingsByMinutes(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        log.log(Logger.Level.INFO, THEREARE+ MovieDatabase.size() + " movies " + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE+ tr.getRaterSize() + RATINGS + INTHEFILE);
        
        int numRating = 5;
        int minMinutes = 105;
        int maxMinutes = 135;
        Filter filterMinutes = new MinutesFilter(minMinutes, maxMinutes);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filterMinutes);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                log.log(Logger.Level.INFO, currValue + "  " + "Time: " + MovieDatabase.getMinutes(currMovieID)
                                   + " " + MovieDatabase.getTitle(currMovieID));
            }
        }
        log.log(Logger.Level.INFO, THEREARE+ num + MOVIESHAVE + numRating + " ratings and satisfy the filter.");
    }

    /**
     * Prints the average ratings of movies with certain Directors.
     */
    public void printAverageRatingsByDirectors(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        log.log(Logger.Level.INFO, THEREARE+ MovieDatabase.size() + " movies " + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE+ tr.getRaterSize() + RATINGS + INTHEFILE);
        
        int numRating = 4;
        String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        Filter filterDirectors = new DirectorsFilter(directors);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filterDirectors);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                log.log(Logger.Level.INFO, currValue + "  " + MovieDatabase.getTitle(currMovieID));
                log.log(Logger.Level.INFO, "    " + MovieDatabase.getDirector(currMovieID));
            }
        }
        log.log(Logger.Level.INFO, THEREARE+ num + MOVIESHAVE + numRating + " ratings and satisfy the filter.");
    }

    /**
     * Prints the average ratings of movies by genre and release date.
     */
    public void printAverageRatingsByYearAfterAndGenre(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        log.log(Logger.Level.INFO, THEREARE+ MovieDatabase.size() + " movies " + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE+ tr.getRaterSize() + RATINGS + INTHEFILE);
        
        int numRating = 8;
        Filter filterYear = new YearAfterFilter(1990);
        Filter filterGenre = new GenreFilter("Drama");
        
        AllFilters filters = new AllFilters();
        filters.addFilter(filterYear);
        filters.addFilter(filterGenre);
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filters);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                log.log(Logger.Level.INFO, currValue + " " + MovieDatabase.getYear(currMovieID) + " "+MovieDatabase.getTitle(currMovieID));
                log.log(Logger.Level.INFO, "    " + MovieDatabase.getGenres(currMovieID));
            }
        }
        log.log(Logger.Level.INFO, THEREARE+ num + MOVIESHAVE + numRating + " ratings and satisfy the filters.");
    }

    /**
     * Prints the average ratings of movies by directors and duration.
     */
    public void printAverageRatingsByDirectorsAndMinutes (){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        log.log(Logger.Level.INFO, THEREARE+ MovieDatabase.size() + " movies " + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE+ tr.getRaterSize() + RATINGS + INTHEFILE);
        
        int numRating = 3;
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        Filter filterDirectors = new DirectorsFilter(directors);
        int minMinutes = 90;
        int maxMinutes = 180;
        Filter filterMinutes = new MinutesFilter(minMinutes, maxMinutes);
        
        AllFilters filters = new AllFilters();
        filters.addFilter(filterDirectors);
        filters.addFilter(filterMinutes);
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filters);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                log.log(Logger.Level.INFO, currValue + " Time: " + MovieDatabase.getMinutes(currMovieID) + " "+ MovieDatabase.getTitle(currMovieID));
                log.log(Logger.Level.INFO, "    " + MovieDatabase.getDirector(currMovieID));
            }
        }
        log.log(Logger.Level.INFO, THEREARE+ num + MOVIESHAVE + numRating + " ratings and satisfy the filters.");
    }
    
}
