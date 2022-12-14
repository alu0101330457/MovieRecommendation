package movierecommendation.movierunner;

import movierecommendation.movies.MovieDatabase;
import movierecommendation.ratings.Rating;
import movierecommendation.filters.*;
import movierecommendation.ratings.FourthRatings;

import java.lang.System.Logger;
import java.util.*;

/**
 * Has functionalities for getting MOVIES with similar ratings and multiple filters.
 */
public class MovieRunnerSimilarRatings {
    private FourthRatings fourth;
    private Logger log;
    static final String THEREARE = "There are ";
    static final  String INTHEFILE = "in the file";

    static final String MOVIES = " movies ";
    static final String RATINGS = " ratings ";

    static final String RATINGVALUES = "Rating values of movies with at least ";
    static final String RECOMMENDED = " recommended movies were found." ;
    
    public MovieRunnerSimilarRatings(){
        fourth = new FourthRatings("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
    }
    
    public void printAverageRatings(){
        log.log(Logger.Level.INFO, THEREARE + MovieDatabase.size() + MOVIES + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE + fourth.getRaterSize() + RATINGS + INTHEFILE);
        
        int numRating = 35;
        ArrayList<Rating> ratings = fourth.getAverageRatings(numRating);
        Collections.sort(ratings);
        
        int num = 0;
        log.log(Logger.Level.INFO, RATINGVALUES + numRating + RATINGS);
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                log.log(Logger.Level.INFO, currValue + "  " + MovieDatabase.getTitle(currMovieID));
            }
        }
        log.log(Logger.Level.INFO, THEREARE + num + " MOVIES have at least " + numRating + RATINGS);
    }

    /**
     * Prints the similar ratings for a select movie in the database.
     */
    public void printSimilarRatings(){
        log.log(Logger.Level.INFO,  THEREARE + MovieDatabase.size() + MOVIES + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE + fourth.getRaterSize() + RATINGS + INTHEFILE + "\n");
        ArrayList<Rating> list = fourth.getSimilarRatings("337", 10, 3);
        for (Rating r: list){
            log.log(Logger.Level.INFO, MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
        }
        log.log(Logger.Level.INFO, "\n" + THEREARE + list.size() + RECOMMENDED );
    }

    /**
     * Prints the MOVIES with similar ratings per genre for a select movie.
     */
    public void printSimilarRatingsByGenre(){
        log.log(Logger.Level.INFO, THEREARE + MovieDatabase.size() + MOVIES + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE + fourth.getRaterSize() + RATINGS+ INTHEFILE + "\n");
        Filter genreFilter = new GenreFilter("Mystery");
        ArrayList<String> movieIDs = MovieDatabase.filterBy(genreFilter);
        ArrayList<Rating> list = fourth.getSimilarRatings("964", 20, 5);
        int num = 0;
        for (Rating r: list){
            if (movieIDs.contains(r.getItem())){
                log.log(Logger.Level.INFO, MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
                log.log(Logger.Level.INFO, "    " + MovieDatabase.getGenres(r.getItem()));
                num += 1;
            }
        }
        log.log(Logger.Level.INFO, "\n" + THEREARE + num + RECOMMENDED );
    }

    /**
     * Prints the MOVIES with similar ratings per director for a select movie.
     */
    public void printSimilarRatingsByDirector(){
        log.log(Logger.Level.INFO, THEREARE + MovieDatabase.size() + MOVIES + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE + fourth.getRaterSize() + RATINGS + INTHEFILE);
        Filter directorFilter = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
        ArrayList<String> movieIDs = MovieDatabase.filterBy(directorFilter);
        ArrayList<Rating> list = fourth.getSimilarRatings("120", 10, 2);
        int num = 0;
        for (Rating r: list){
            if (movieIDs.contains(r.getItem())){
                log.log(Logger.Level.INFO, MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
                log.log(Logger.Level.INFO, "    " + MovieDatabase.getDirector(r.getItem()));
                num += 1;
            }
        }
        log.log(Logger.Level.INFO, "\n" + THEREARE + num + RECOMMENDED );
    }

    /**
     * Prints the MOVIES with similar rating per genre and duration for a select movie.
     */
    public void printSimilarRatingsByGenreAndMinutes(){
        log.log(Logger.Level.INFO, THEREARE + MovieDatabase.size() + MOVIES + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE + fourth.getRaterSize() + RATINGS + INTHEFILE);
        Filter genreFilter = new GenreFilter("Drama");
        Filter minutesFilter = new MinutesFilter(80, 160);
        AllFilters all = new AllFilters();
        all.addFilter(genreFilter);
        all.addFilter(minutesFilter);
        ArrayList<String> movieIDs = MovieDatabase.filterBy(all);
        
        ArrayList<Rating> list = fourth.getSimilarRatings("168", 10, 3);
        int num = 0;
        for (Rating r: list){
            if (movieIDs.contains(r.getItem())){
                log.log(Logger.Level.INFO, MovieDatabase.getTitle(r.getItem()) + " : " + "Duration-" + MovieDatabase.getMinutes(r.getItem()) 
                                   + " Rating-" + r.getValue());
                log.log(Logger.Level.INFO, "    " + MovieDatabase.getGenres(r.getItem()));
                num += 1;
            }
        }
        log.log(Logger.Level.INFO, THEREARE + num + RECOMMENDED );
    }

    /**
     * Prints the MOVIES with similar ratings by release year and duration.
     */
    public void printSimilarRatingsByYearAfterAndMinutes(){
        log.log(Logger.Level.INFO, THEREARE + MovieDatabase.size() + MOVIES + INTHEFILE);
        log.log(Logger.Level.INFO, THEREARE + fourth.getRaterSize() + RATINGS + INTHEFILE);
        Filter yearAfterFilter = new YearAfterFilter(1975);
        Filter minutesFilter = new MinutesFilter(70, 200);
        AllFilters all = new AllFilters();
        all.addFilter(yearAfterFilter);
        all.addFilter(minutesFilter);
        ArrayList<String> movieIDs = MovieDatabase.filterBy(all);
        
        ArrayList<Rating> list = fourth.getSimilarRatings("314", 10, 5);
        int num = 0;
        for (Rating r: list){
            if (movieIDs.contains(r.getItem())){
                log.log(Logger.Level.INFO, MovieDatabase.getTitle(r.getItem()) + " : " + "Duration-" + MovieDatabase.getMinutes(r.getItem()) 
                                   + " Rating-" + r.getValue());
                log.log(Logger.Level.INFO, "    " + MovieDatabase.getYear(r.getItem()));
                num += 1;
            }
        }
        log.log(Logger.Level.INFO, THEREARE + num + RECOMMENDED );
    }
}
