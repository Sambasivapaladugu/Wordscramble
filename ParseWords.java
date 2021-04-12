import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;

/**
 * This class returns a meaningful word/words out
 * of the word passed in with letters scrambled
 */
public class ParseWords {

    static List<String> inMemoryList;
    static Object[] objArray;
    private static InputStream stream;
   // public String perm_string = "nisk";

    public ParseWords() {

    }

    public ParseWords(String scrambled) {
        inMemoryList = new ArrayList<String>();
	getDictionaryPath("2of12.txt");
        OpenAndParseFile();
        StringPerm perm = new StringPerm(scrambled);
        String[] list = perm.makePermutations(2);
        List<String> found = this.permuteAndFindWord(list);
        this.display(found);
    }

    /*Parse the dictionary and convert that into an inmemory
    array of words.*/

    public static void OpenAndParseFile() {
        BufferedReader bis = null;

        try {
            //File fili = new File(fileName);
            //bis = new BufferedReader(new FileReader(fili));
	    bis = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = bis.readLine()) != null) {
                inMemoryList.add(line);
            }
            objArray = inMemoryList.toArray();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /* This function is to rewrite the originally parsed dictionary file to a
     * neater format... something like "abc",
     *                                 "def",
     */
    public void printWords() {
        try {
            PrintWriter writer = new PrintWriter(new File("/tmp/words.txt"));

            int len = inMemoryList.size();
            for (int i = 0; i < len; i++) {
                String string = inMemoryList.get(i);
                writer.println("\"" + string + "\"" + ",");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParseWords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /* Get the array with all permutations of the word
    iterate thru this array and do a binary search of each
    word in the array.*/

    public List<String> permuteAndFindWord(String[] input) {
        int lenInput = input.length;
        List<String> found = new ArrayList<String>();
        for (int i = 0; i < lenInput; i++) {
            String word = input[i];

            int index = Arrays.binarySearch(objArray, word);
            if (index >= 0) {
                found.add((String) objArray[index]);
            }

        }
        return found;
    }

    public void display(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    /* Get the dictionary file from within the jar and convert
	it to an input stream */
    public static void getDictionaryPath(String dictionaryFile) {

        if (dictionaryFile != null) {
            ClassLoader contextClassLoader =
                    Thread.currentThread().getContextClassLoader();
            if (contextClassLoader == null) {
                contextClassLoader = ParseWords.class.getClassLoader();
            }

            InputStream is = contextClassLoader.getResourceAsStream(dictionaryFile);
            if (is != null) {
		stream = is;
            }
        }
    }
}
