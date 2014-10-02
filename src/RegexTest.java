
public class RegexTest {

	public static final String EXAMPLE_TEST = "This is my small example "
		      + "string which I'm going to " + "use for pattern matching.";
	public static String zahlen = "2794729";

		  public static void main(String[] args) {
			System.out.println(zahlen.matches("\\d+"));
		    System.out.println(EXAMPLE_TEST.matches("\\w.*"));
		    String[] splitString = (EXAMPLE_TEST.split("\\s+"));
		    System.out.println(splitString.length);// should be 14
		    for (String string : splitString) {
		      System.out.println(string);
		    }
		    // replace all whitespace with tabs
		    System.out.println(EXAMPLE_TEST.replaceAll("\\s+", "\t"));
		  }

}
