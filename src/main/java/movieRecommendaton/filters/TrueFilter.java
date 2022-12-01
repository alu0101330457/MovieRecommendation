package movieRecommendaton.filters;

import movieRecommendaton.filters.Filter;

public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}
