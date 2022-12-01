package movieRecommendaton.filters;

import movieRecommendaton.filters.Filter;

/**
 * Filter that always returns "true", it's always satisfied.
 */
public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}
