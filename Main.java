/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;

/**
 *
 * @author pankaj
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String perm_string ="anagram";
        try {
            if (args[0] != null) {
                perm_string = args[0];
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
		
        }

        new ParseWords(perm_string);

    }
}

