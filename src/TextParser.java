import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Utility class for parsing text in a consistent manner.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 * @version Spring 2019
 */
public class TextParser {

	/** Regular expression that matches any whitespace. **/
	public static final Pattern SPLIT_REGEX = Pattern.compile("(?U)\\p{Space}+");

	/** Regular expression that matches non-alphabetic characters. **/
	public static final Pattern CLEAN_REGEX = Pattern.compile("(?U)[^\\p{Alpha}\\p{Space}]+");

	/**
	 * Cleans the text by removing any non-alphabetic characters (e.g. non-letters
	 * like digits, punctuation, symbols, and diacritical marks like the umlaut) and
	 * converting the remaining characters to lowercase.
	 *
	 * @param text the text to clean
	 * @return cleaned text
	 */
	public static String clean(String text) {
		String cleaned = Normalizer.normalize(text, Normalizer.Form.NFD);
		cleaned = CLEAN_REGEX.matcher(cleaned).replaceAll("");
		return cleaned.toLowerCase();
	}

	/**
	 * Splits the supplied text by whitespaces.
	 *
	 * @param text the text to split
	 * @return an array of {@link String} objects
	 */
	public static String[] split(String text) {
		return text.isBlank() ? new String[0] : SPLIT_REGEX.split(text.strip());
	}

	/**
	 * Cleans the text and then splits it by whitespace.
	 *
	 * @param text the text to clean and split
	 * @return an array of {@link String} objects
	 *
	 * @see #clean(String)
	 * @see #parse(String)
	 */
	public static String[] parse(String text) {
		return split(clean(text));
	}

	/**
	 * A simple main method that demonstrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		String text = "Sally Sue...\t sells 76 sea-shells    at THE sEa_shorE soirée!";

		System.out.printf(" Text: %s%n", text);
		System.out.printf("Clean: %s%n%n", clean(text));

		String[] afterSplit = split(text);
		String[] afterParse = parse(text);

		String[] splitAfterClean = split(clean(text));
		String[] cleanAfterSplit = new String[afterSplit.length];

		for (int i = 0; i < afterSplit.length; i++) {
			cleanAfterSplit[i] = clean(afterSplit[i]);
		}

		System.out.printf("         Split (%d items): %s%n", afterSplit.length, Arrays.toString(afterSplit));
		System.out.printf("Clean -> Split (%d items): %s%n", cleanAfterSplit.length, Arrays.toString(cleanAfterSplit));
		System.out.printf("Split -> Clean (%d items): %s%n", splitAfterClean.length, Arrays.toString(splitAfterClean));
		System.out.printf("         Parse (%d items): %s%n", afterParse.length, Arrays.toString(afterParse));
	}
}
