/*****************************************************************************

 File        : Main.java

 Date        : 19/01/2020

 Description : Java class for running the other code in this package

 Author      : 100239986
 ******************************************************************************/
package AutoComplete;

import AutoComplete.Part3.AutoCompletion;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        AutoCompletion ac = new AutoCompletion("csvFiles\\lotr.csv","csvFiles\\lotrQueries.csv");
        ac.printQueriesToConsole();
        ac.writeQueriesToFile("matches.csv");
    }
}
