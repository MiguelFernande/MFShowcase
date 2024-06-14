//To run the program simply run and pick a txt file

import java.io.*;
import java.util.Map;

import javax.swing.JFileChooser;

public class Project2 {
  // Main Method to read and display contents

  public static void main(String[] args) {
    // using java swing since it convenient and easy.
    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(null); // Show the open file dialog

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filePath = selectedFile.getAbsolutePath(); // Get the path of the selected file

      System.out.println("Trying to access: " + filePath);

      // main loop for reading files read file -> lexer read -> make token -> if its
      // not EOT make a token -> errors/EOT
      try {
        FileReader fileReader = new FileReader(filePath);
        Lexer lexer = new Lexer(fileReader);
        Parser parser = new Parser(lexer);

        Map<String, Object> astRoot = parser.parse();
        parser.printAST(astRoot);

        System.out.println("Finished Syntax analysis on " + filePath);

      } catch (FileNotFoundException e) {
        System.out.println("Error: file not found");
      } catch (IOException e) {
        System.out.println("Error reading file");
      } catch (LexicalException e) {
        System.out.println(e.getMessage());
      } catch (ParseException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}