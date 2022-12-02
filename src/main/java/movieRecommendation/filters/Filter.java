package movieRecommendation.filters;

/**
 * Interface that represents a Filter.
 */
public interface Filter {

	/**
	 * Returns true if the ID passes the filter.
	 * @param id A generic ID for an object.
	 */
	public boolean satisfies(String id);
}
