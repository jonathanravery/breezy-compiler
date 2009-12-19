package src;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Hashtable;

/**
 * The BreezyDefault class contains all of the built in functions available in
 * Breezy.
 * 
 * @author javery
 * 
 */
public class BreezyDefault {
	// Readers for each file. This way, readers are created once per file during
	// execution, allowing reading to
	// pick up where it left off
	private Hashtable<String, BufferedReader> readers;

	// I thought we decided against doing this, in favor of some speciailzed code & a "super main" in the BreezyProg class
	// private static BreezyProg bp;
	// public static void main(String[] args){bp.main("", 5);}

	/**
	 * Read a single character from standard input
	 */
	public String ReadCharacterFromScreen() {
		int tmp;
		try {
			tmp = System.in.read();
		} catch (IOException ioe) {
			// TODO: Error handling
			return "";
		}
		return new String(new char[] { (char) tmp });
	}

	/**
	 * ReadLineFromScreen reads & returns a single line from standard input
	 */
	public String ReadLineFromScreen() {
		// Create a new reader (since we don't have to deal with the
		// "pick up where you left off problem"
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;

		try {
			// Read a line of input
			input = br.readLine();
		} catch (IOException ioe) {
			// TODO: Error handling
		}

		// Return the line of input we read. Simple.
		return input;
	}

	/**
	 * Continue reading input from standard in until the end of input control
	 * character is received, then return everything.
	 * 
	 * @return String A string containing all the input from stdin
	 */
	public String ReadAllFromScreen() {
		// Set up a new buffered reader
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		String input = "";

		// At least once...
		do {
			try {
				// Read a line of input
				line = br.readLine();
				// Attach the newline back onto it (because I believe readline
				// strips it off the end)
				input += line + "\n";
			} catch (IOException ioe) {
				// TODO: Error handling
			}
		}
		// ... until we've reached the end
		while (line != null);

		// return the string
		return input;
	}

	/**
	 * Read a single character of input from a file
	 * 
	 * @param filename
	 * @return
	 */
	public String ReadCharacterFromFile(String filename) {
		// Get the reader for this file
		BufferedReader br = this.getReader(filename);
		String character = null;

		try {
			// the br.read() will return an int, which we cast into a char,
			// which we put in an array,
			// which we pass as the parameter to the String constructor
			// String(char[])
			character = new String(new char[] { (char) br.read() });
		} catch (IOException ioe) {
			// TODO: Error handling
		}

		// Return the string
		return character;
	}

	/**
	 * Read a line of input from a file
	 * 
	 * @param filename
	 * @return
	 */
	public String ReadLineFromFile(String filename) {
		// Get the reader
		BufferedReader br = this.getReader(filename);
		String line = null;

		try {
			// Get the line from the file
			line = br.readLine();
		} catch (IOException ioe) {
			// TODO: Error handling
		}

		// Return it
		return line;
	}

	/**
	 * Read the entire text of a file into a string
	 * 
	 * @param filename
	 * @return
	 */
	public String ReadAllFromFile(String filename) {
		BufferedReader br = this.getReader(filename);
		String line = null;
		String all = "";

		// At least once...
		do {
			try {
				// Read a line from the file and add the \n back on the end
				line = br.readLine();
				all += line + "\n";
			} catch (IOException ioe) {
				// TODO: Error handling
			}
		}
		// ... and keep going until the end of the file
		while (line != null);

		// Return it
		return all;
	}

	/**
	 * Write a string of text to standard out. This method is used by the character, line, and all versions,
	 * which would not have been created in the first place, had we thought it through before promising
	 * them in the tutorial.
	 * 
	 * @param text The text to write to stdout
	 */
	public void WriteToScreen(String text) {
		System.out.print(text);
	}

	/**
	 * Write a string of text to a file. This method is used by the character, line, and all verions, which
	 * would not have been created in the first place, had we thought it through before promising them in
	 * the tutorial
	 * 
	 * @param text The text to write to a file
	 * @param filename The name of the file to write the text to
	 */
	public void WriteToFile(String text, String filename) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(filename));
		} catch (FileNotFoundException fnfe) {
			// TODO: Error handling
		}

		pw.write(text);
	}

	/**
	 * Write a single character to standard out.
	 * @param text
	 */
	public void WriteCharacterToScreen(String text)
	{
		this.WriteToScreen(text.substring(0, 1));
	}
	
	/**
	 * Write a line of text to standard out
	 * @param text
	 */
	public void WriteLineToScreen(String text)
	{
		String[] lines = text.split("\n");
		this.WriteToScreen(lines[0] + "\n");
	}
	
	/**
	 * Write some text to standard out
	 * @param text
	 */
	public void WriteAllToScreen(String text)
	{
		this.WriteToScreen(text);
	}
	
	/**
	 * Write a single character to a file
	 * @param text
	 * @param filename
	 */
	public void WriteCharacterToFile(String text, String filename)
	{
		this.WriteToFile(text.substring(0, 1), filename);
	}
	
	/**
	 * Write a line of text to a file
	 * @param text
	 * @param filename
	 */
	public void WriteLineToFile(String text, String filename)
	{
		String[] lines = text.split("\n");
		this.WriteToFile(lines[0] + "\n", filename);
	}
	
	/**
	 * Write some text to a file
	 * @param text
	 * @param filename
	 */
	public void WriteAllToFile(String text, String filename)
	{
		this.WriteToFile(text, filename);
	}
	
	/**
	 * Convert a string into a number
	 * @param numstring
	 * @return
	 */
	public double StringToNumber(String numstring) {
		return Double.parseDouble(numstring);
	}

	/**
	 * Convert a number into a string
	 * @param number
	 * @return
	 */
	public String NumberToString(double number) {
		return Double.toString(number);
	}

	/**
	 * Convert a number into a string, up to a certain number of digits
	 * @param number
	 * @param maxDigits
	 * @return
	 */
	public String NumberToString(double number, int maxDigits) {
		String num;

		num = Double.toString(number);

		return num.substring(0, maxDigits);
	}

	/**
	 * Simple function for getting a buffered reader so we can do this in a
	 * single line within the functions.
	 * 
	 * @param filename
	 * @return
	 */
	private BufferedReader getReader(String filename) {
		// Get the reader from the list of readers we have
		BufferedReader br = this.readers.get(filename);

		// If there isn't one there yet
		if (br == null) {
			try {
				// Make one
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(filename)));
				// Put it in the table
				this.readers.put(filename, br);
			} catch (FileNotFoundException fnfe) {
				// TODO: Error handling
			}
		}

		// Return it
		return br;
	}
}
