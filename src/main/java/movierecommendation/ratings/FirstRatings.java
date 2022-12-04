package movierecommendation.ratings;

import edu.duke.*;
import java.util.*;

import movierecommendation.movies.Movie;
import movierecommendation.raters.EfficientRater;
import movierecommendation.raters.Rater;
import org.apache.commons.csv.*;



import java.io.*;
import java.lang.System.Logger;

/**
 * Process information in a CSV File about movie information.
 */
public class FirstRatings {

    private Logger log;

    /**
     * This method processes movie records from a CSV File
     * @param filename CSV File containing movie information.
     * @return ArrayList<Movie> with all the Movie data from the file.
     */
    public ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> movies = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        CSVParser movieParser = fr.getCSVParser();
        for (CSVRecord currentRow: movieParser){
            String currId = currentRow.get("id");
            String currTitle = currentRow.get("title");
            String currYear = currentRow.get("year");
            String currGenres = currentRow.get("genre");
            String currDirector = currentRow.get("director");
            String currCountry = currentRow.get("country");
            String currPoster = currentRow.get("poster");
            int currMinutes = Integer.parseInt(currentRow.get("minutes"));
            
            movies.add(new Movie(currId, currTitle, currYear, currGenres, 
                                currDirector, currCountry, currPoster, currMinutes));
        }
        return movies;
    }

    /**
     * This method processes rater records from a CSV File.
     * @param filename CSV File containing rater information.
     * @return ArrayList<Rater> with all the Rater data from the file.
     */
    public ArrayList<Rater> loadRaters(String filename){
        ArrayList<Rater> raters = new ArrayList<>();
        FileResource fr = new FileResource("data/" + filename);
        CSVParser raterParser = fr.getCSVParser();
        // Initial the String to check if the rater is existed or not. 
        String existed = "";
        for (CSVRecord currentRow: raterParser){
            String raterId = currentRow.get("rater_id");
            String movieId = currentRow.get("movie_id");
            double rating = Double.parseDouble(currentRow.get("rating"));
            
            // Ckeck if the current rater is existed in the ArrayList "raters" or not.
            boolean exist = false;
            /*for (Rater r: raters){
                if (r.getID().equals(raterId)){
                    exist = true;
                }
            }*/
            if (existed.indexOf(raterId) != -1){
                exist = true;
            }
            
            if (exist == false){
                //Rater currRater = new PlainRater(raterId);
                Rater currRater = new EfficientRater(raterId);
                currRater.addRating(movieId, rating);
                raters.add(currRater);
                // Add the rater's ID to the "existed" String.
                existed += raterId + " ";
            } else {
                for (Rater r: raters){
                    if (r.getID().equals(raterId)){
                        r.addRating(movieId, rating);
                    }
                }
            }
        }
        return raters;
    }

    /**
     * Returns the number of records of the Rater with the selected raterID.
     */
    public void findNumOfRater(String filename, String RaterID){
        ArrayList<Rater> raters = loadRaters(filename);
        Rater result = null;
        for (Rater currRater: raters){
            if (currRater.getID().equals(RaterID)){
                result = currRater;
            }
        }

        int num = 0;
        if (result.numRatings() != 0) {
            num = result.numRatings();
        }
        log.log(Logger.Level.INFO, "There are " + num + " ratings of " + "ID " + RaterID);
    }

    /**
     * Finds the rater with the highest number of records inside the selected file.
     */
    public void findMaxNumOfRatingsByRater(String filename){
        ArrayList<Rater> raters = loadRaters(filename);
        int max = 0;
        for (Rater currRater: raters){
            if (currRater.numRatings() > max){
                max = currRater.numRatings();
            }
        }
        log.log(Logger.Level.INFO, "The maximum number of ratings of the rater(s) is " + max + ". Their IDs are:");
        String s = "";
        for (Rater currRater: raters){
            if (currRater.numRatings() == max){
                s += currRater.getID() + ", ";
            }
        }
        log.log(Logger.Level.INFO, s.substring(0, s.length()-2));
    }

    /**
     * Returns the number of ratings a selected movie has, inside a selected file.
     */
    public void findRatingsOfMovie(String filename, String movieID){
        ArrayList<Rater> raters = loadRaters(filename);
        int num = 0;
        for (Rater currRater: raters){
            ArrayList<String> movies = currRater.getItemsRated();
            if (movies.contains(movieID)){
                num += 1;
            }
        }
        log.log(Logger.Level.INFO, "Movie with ID " + movieID + " was rated by " + num + " raters.");
    }

    /**
     * Counts the unique number of movies inside the selected file.
     */
    public void countRatedMovies(String filename){
        ArrayList<Rater> raters = loadRaters(filename);
        ArrayList<String> movies = new ArrayList<>();
        for (Rater currRater: raters){
            ArrayList<String> currMovies = currRater.getItemsRated();
            for (String s: currMovies){
                if (!movies.contains(s)){
                    movies.add(s);
                }
            }
        }
        log.log(Logger.Level.INFO, "There are " + movies.size() + " movies rated.");
    }


    public void test(){
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            String filename = f.getName();
            log.log(Logger.Level.INFO, "Processing file: " + filename);
            log.log(Logger.Level.INFO, " ");

            findNumOfRater(filename, "193");
        }
    }
    
    public void testLoadMovies(String filename){
        ArrayList<Movie> movies = loadMovies(filename);
        log.log(Logger.Level.INFO, "There are " + movies.size() + " records.");

            
        int numComedy = 0;
        for (Movie currMovie: movies){
            if (currMovie.getGenres().indexOf("Comedy") != -1){
                numComedy += 1;
            }
        }
        log.log(Logger.Level.INFO, "There are " + numComedy + " comedy movies in the file.");
            
        int numLength150 = 0;
        for (Movie currMovie: movies){
            if (currMovie.getMinutes() > 150){
                numLength150 += 1;
            }
        }
        log.log(Logger.Level.INFO, "There are " + numLength150 + " movies which their lengths are more than 150 min.\n");

        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (Movie currMovie: movies){
            String director = currMovie.getDirector().trim();
            if (director.indexOf(",") == -1){
                if (!map.containsKey(director)){
                    map.put(director, new ArrayList<>());
                }
                String title = currMovie.getTitle();
                map.get(director).add(title);
                  
            } else {
                while (director.indexOf(",") != -1){
                    int idxComma = director.indexOf(",");
                    String currDirector = director.substring(0, idxComma);
                        
                    if (!map.containsKey(currDirector)){
                        map.put(currDirector, new ArrayList<>());
                    }
                    String title = currMovie.getTitle();
                    map.get(currDirector).add(title);

                    director = director.substring(idxComma+1).trim();
                }
            }
        }
            
        int maxNumOfMoviesByDirector = 0;
        for (String s: map.keySet()){
            if (map.get(s).size() > maxNumOfMoviesByDirector){
                maxNumOfMoviesByDirector = map.get(s).size();
            }
        }
        log.log(Logger.Level.INFO, "The maximum number of films directed by one director is " + maxNumOfMoviesByDirector);
            
        String directorWithMaxMovies = "";
        for (String s: map.keySet()){
            if (map.get(s).size() == maxNumOfMoviesByDirector){
                directorWithMaxMovies += s + ", ";
            }
        }
        log.log(Logger.Level.INFO, "Names of the directors who directed the maximum number of movies " +
                                directorWithMaxMovies.substring(0, directorWithMaxMovies.length()-2));
    }
    
    public void testLoadRaters(String filename){
        ArrayList<Rater> raters = loadRaters(filename);
        log.log(Logger.Level.INFO, "There are " + raters.size() + " raters.");
        log.log(Logger.Level.INFO, " ");
    }
    
    
}
