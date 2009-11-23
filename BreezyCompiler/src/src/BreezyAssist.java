/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leighton Minor
 */
public class BreezyAssist {

    public BreezyAssist() {
    }

    public void DumpFile(String s){
        DumpFile(s,"BreezyProg");
    }

    public void DumpFile(String s, String fileName){
        try {
            FileWriter fw = new FileWriter(fileName + ".java");

            fw.write("//import Brezzy_Classes_Here.*;\n");
            fw.write("public class " + fileName + "{\n");
            fw.write(s);
            System.out.println("}");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(BreezyAssist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
