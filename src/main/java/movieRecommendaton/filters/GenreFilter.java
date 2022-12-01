package movieRecommendaton.filters;

import movieRecommendaton.movies.MovieDatabase;

/**
 * Represents a Filter to check the Genre of a Movie.
 */
public class GenreFilter implements Filter {
    private String myGenre;
    
    public GenreFilter(String genre) {
        myGenre = genre;
    }
    
    @Override
    public boolean satisfies(String id) {
        int idx = MovieDatabase.getGenres(id).indexOf(myGenre);
        if (idx != -1){
            return true;
        } else {
            return false;
        }
    }
}
