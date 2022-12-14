package edu.duke;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.nio.charset.StandardCharsets;


/**
 * The <code>URLResource</code> class opens a connection to a URL and allows access to the contents
 * of the web page a line at a time, using the method <code>lines</code>, or a word at a time, using
 * the method <code>words</code>. These strings can then be iterated over using a <code>for</code>
 * loop.
 * 
 * <P>
 * Example usage:
 * 
 * <PRE>
 * URLResource ur = new URLResource("http://www.dukelearntoprogram.com/");
 * for (String s : ur.lines()) {
 *     // print or process s
 * }
 * </PRE>
 *
 * <P>
 * If each line of the web page represents separated data values, because its a CSV file, then the
 * user can get a <code>getCSVParser</code> object to access that data more directly, using one of the
 * <code>getCSVParser</code> methods.
 * 
 * <P>
 * Example CSV usage:
 * 
 * <PRE>
 * URLResource ur = new URLResource("http://www.dukelearntoprogram.com/course2/java/food.csv");
 * for (CSVRecord record : ur.getCSVParser()) {
 *     // print or process fields in record
 *     String name = record.get("Name");
 *     // other processing
 * }
 * </PRE>
 * 
 * <P>
 * This software is licensed with an Apache 2 license, see
 * http://www.apache.org/licenses/LICENSE-2.0 for details.
 * 
 * @author Duke Software Team
 */
public class URLResource {
    private String myPath;
    private String mySource;

    /**
     * Allow access to open web page one line at a time.
     * 
     * @return an <code>Iterable</code> that allows access one line at a time
     */
    public Iterable<String> lines () {
        return new TextIterable(mySource, "\\n");
    }

    /**
     * Allow access to this open web page one word at a time, where words are separated by
     * white-space. This means any form of spaces, like tabs or newlines, can delimit words.
     * 
     * @return an <code>Iterable</code> that allows access one word at a time
     */
    public Iterable<String> words () {
        return new TextIterable(mySource, "\\s+");
    }

    /**
     * Returns a <code>CSVParser</code> object to access the contents of an open web page.
     * 
     * Each line of the web page should be formatted as data separated by commas and with a header
     * row to describe the column names.
     * 
     * @return a <code>CSVParser</code> that can provide access to the records in the web page one
     *         at a time
     * @throws exception if this web page does not represent a CSV formatted data
     */
    public CSVParser getCSVParser () {
        return getCSVParser(true);
    }

    /**
     * Returns a <code>CSVParser</code> object to access the contents of an open web page, possibly
     * without a header row.
     * 
     * Each line of the web page should be formatted as data separated by commas and with/without a
     * header row to describe the column names.
     * 
     * @param withHeader uses first row of data as a header row only if true
     * @return a <code>CSVParser</code> that can provide access to the records in the web page one
     *         at a time
     * @throws exception if this web page does not represent a CSV formatted data
     */
    public CSVParser getCSVParser (boolean withHeader) {
        return getCSVParser(withHeader, ",");
    }

    /**
     * Returns a <code>CSVParser</code> object to access the contents of an open web page, possibly
     * without a header row and a different data delimiter than a comma.
     * 
     * Each line of the web page should be formatted as data separated by the delimiter passed as a
     * parameter and with/without a header row to describe the column names. This is useful if the
     * data is separated by some character other than a comma.
     * 
     * @param withHeader uses first row of data as a header row only if true
     * @param delimiter a single character that separates one field of data from another
     * @return a <code>CSVParser</code> that can provide access to the records in the web page one
     *         at a time
     * @throws exception if this web page does not represent a CSV formatted data
     * @throws exception if <code>delimiter.length() != 1</code>
     */
    public CSVParser getCSVParser (boolean withHeader, String delimiter) {
        if (delimiter == null || delimiter.length() != 1) {
            throw new ResourceException("URLResource: CSV delimiter must be a single character: " + delimiter);
        }
        try {
            char delim = delimiter.charAt(0);
            Reader input = new StringReader(mySource);
            if (withHeader) {
                return new CSVParser(input, CSVFormat.EXCEL.withHeader().withDelimiter(delim));
            }
            else {
                return new CSVParser(input, CSVFormat.EXCEL.withDelimiter(delim));
            }
        }
        catch (Exception e) {
            throw new ResourceException("URLResource: cannot read " + myPath + " as a CSV file.");
        }
    }

}
