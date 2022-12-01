package movieRecommendaton.filters;

import movieRecommendaton.movies.MovieDatabase;

/**
 * Represents a Filter to check the release year of a movie.
 */
public class YearAfterFilter implements Filter {
	private int myYear;
	
	public YearAfterFilter(int year) {
		myYear = year;
	}
	
	@Override
	public boolean satisfies(String id) {
		return MovieDatabase.getYear(id) >= myYear;
	}

}
