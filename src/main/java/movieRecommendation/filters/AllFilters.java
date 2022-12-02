package movieRecommendation.filters;

import java.util.ArrayList;

/**
 * Class that contains multiple Filters.
 */
public class AllFilters implements Filter {
    ArrayList<Filter> filters;

    /**
     * Initializes the object without any Filter.
     */
    public AllFilters() {
        filters = new ArrayList<Filter>();
    }

    /**
     * Adds a Filter.
     */
    public void addFilter(Filter f) {
        filters.add(f);
    }

    @Override
    public boolean satisfies(String id) {
        for(Filter f : filters) {
            if (! f.satisfies(id)) {
                return false;
            }
        }
        
        return true;
    }

}
