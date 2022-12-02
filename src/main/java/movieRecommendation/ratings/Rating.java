package movieRecommendation.ratings;


/**
 * An immutable passive data object to represent the rating data.
 */
public class Rating implements Comparable<Rating> {
    private String item; //!< movieID, a number but not a title.
    private double value;

    public Rating (String anItem, double aValue) {
        item = anItem;
        value = aValue;
    }

    /**
     *
     * @return The item being rated.
     */
    public String getItem () {
        return item;
    }

    /**
     *
     * @return the value of this rating (as a number so it can be used in calculations)
     */
    public double getValue () {
        return value;
    }

    /**
     *
     * @return a string of all the rating information
     */
    public String toString () {
        return "[" + getItem() + ", " + getValue() + "]";
    }

    /**
     * Compares two ratings.
     * @param other the other Rating to be compared.
     * @return -1 if this object is less than the parameter. 1 if it is greater.
     */
    public int compareTo(Rating other) {
        if (value < other.value) return -1;
        if (value > other.value) return 1;
        
        return 0;
    }
}
