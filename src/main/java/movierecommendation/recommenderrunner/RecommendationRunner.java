package movierecommendation.recommenderrunner;

import movierecommendation.movies.MovieDatabase;
import movierecommendation.raters.RaterDatabase;
import movierecommendation.ratings.Rating;
import movierecommendation.filters.Filter;
import movierecommendation.filters.TrueFilter;
import movierecommendation.ratings.FourthRatings;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.lang.System.Logger;
import java.util.*;


public class RecommendationRunner implements Recommender {
    private Random myRandom;
    private int toRateNum;
    private int numSimilarRaters;
    private int minimalRaters;
    private int maxRecNum;
    private Logger log = System.getLogger("RecommendationRunner");
    
    public RecommendationRunner(){
        myRandom = new Random();
        toRateNum = 10;
        numSimilarRaters = 10;
        minimalRaters = 3;
        maxRecNum = 20;
    }
    
    public ArrayList<String> getItemsToRate(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        ArrayList<String> toRate = new ArrayList<>();
        Filter f = new TrueFilter();
        ArrayList<String> allMovies = MovieDatabase.filterBy(f);
        for (int k=0; k<toRateNum; k++){
            int currIdx = myRandom.nextInt(MovieDatabase.size());;
            String currMovieID = allMovies.get(currIdx);
            toRate.add(currMovieID);
        }
        return toRate;
    }

    public static void main(String[] args) throws PrinterException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Películas a calificar:");
        RecommendationRunner rec = new RecommendationRunner();
        ArrayList<String> films = rec.getItemsToRate();
        int rate = -1;
        ArrayList<Integer> rates = new ArrayList<Integer>();
        for (String film : films) {
            System.out.print(MovieDatabase.getMovie(film) + ": ");
            rate = scanner.nextInt();
            while ((rate < 0) || (rate > 10)) {
                System.out.print(MovieDatabase.getMovie(film) + ": ");
                rate = scanner.nextInt();
            }
            rates.add(rate);
            RaterDatabase.addRaterRating("0", film, rate);
            rate = -1;
        }

        JTextPane jtp = new JTextPane();
        jtp.setContentType("text/html");
        jtp.setText("<html>" + rec.printRecommendationsFor("0") + "</html>"); //Your whole html here..
        JFrame frame = new JFrame();
        frame.add(jtp);
        frame.pack();
        frame.setVisible(true);

    }
    
    public String printRecommendationsFor (String webRaterID){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        FourthRatings fourth = new FourthRatings();
        ArrayList<Rating> result = fourth.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);
        int num = result.size();
        if (num == 0){
            return "No movies found";
        } else {
            if (num > maxRecNum){
                num = maxRecNum;
            }
            String header = ("<table> <tr> <th>Title</th> <th>Rating Value</th> </tr>");
            String body = "";
            for (int k=0; k<num; k++){
                Rating currRating = result.get(k);
                String currMovieID = currRating.getItem();
                String currMovieTitle = MovieDatabase.getTitle(currMovieID);
                double currRatingValue = currRating.getValue();
                body += printOut(currMovieTitle, currRatingValue);
            }
            return header + body + "</table>";
        }
    }
    
    public String printOut(String title, double value){
        return ("<tr> <td>" + title + "</td> <td>" + value + "</td> </tr>");
    }
}
