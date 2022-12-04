package movierecommendation.filters;

/**
 * Filter that always returns "true", it's always satisfied.
 */
public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}
