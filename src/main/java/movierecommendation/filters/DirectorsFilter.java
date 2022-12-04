package movierecommendation.filters;

import movierecommendation.movies.MovieDatabase;

/**
 * Represents a Filter to check the Director of a movie.
 */
public class DirectorsFilter implements Filter {
    private String myDirectors;
    
    public DirectorsFilter(String directors) {
        myDirectors = directors;
    }
    
    @Override
    public boolean satisfies(String id) {
        String[] directors = myDirectors.split(",");
        
        for (int k=0; k < directors.length; k++){
            if (MovieDatabase.getDirector(id).indexOf(directors[k]) != -1){
                return true;
            }
        }
        return false;

    }
}