import javafx.css.Match;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.PrintWriter;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class main {
    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);

        System.out.println("Enter textfile you want to parse without extensions:");
        String filename = input.nextLine();


        System.out.println("Enter the amount of columns in your table:");
        int columnAmount = parseInt(input.nextLine());
        try (PrintWriter writer = new PrintWriter(new File("data/" + filename + ".csv"))) {
            StringBuilder columname = new StringBuilder();
            for (int i = 1; i <= columnAmount; i++) {
                System.out.println("Enter the name of column " + i);
                if (i == columnAmount) {
                    columname.append(input.nextLine());
                } else {
                    columname.append(input.nextLine() + ",");
                }
            }
            columname.append("\n");
            writer.write(columname.toString());

            System.out.println("Now enter the regular expresion for the table");
            String pattern = input.nextLine();
            //String pattern = "(.*?)\\s\\(([0-9 ?\\/A-Z]*)\\)\\s\\{?\\{*([A-Za-z '?,\\&\\-\\>]*)?\\(?\\#?([0-9]*)\\.?([0-9]*)?\\)?\\}?\\}?\\s*?\\(?([A-Z]*)?\\)?\\s*\\t*([A-Za-z ]*)";
            Pattern r = Pattern.compile(pattern);

            File myObj = new File(String.format("data/%s.list", filename));
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Matcher m = r.matcher(data);
                if (m.find()) {
                    StringBuilder sb = new StringBuilder();
                    for(int j = 1; j <=columnAmount; j++){
                        if(j == columnAmount) {
                            System.out.println(m.group(j));
                            sb.append(m.group(j));
                        }
                        else{
                            System.out.println(m.group(j));
                            sb.append(m.group(j) + ",");
                        }

                    }

                    sb.append("\n");
                    writer.write(sb.toString());

                } else {
                    System.out.println("No match :(");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
