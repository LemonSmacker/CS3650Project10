import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.Element;

//go to line 33 to choose which file is being read



public class Main {

  
  private static boolean checkInArrays(String[] array, String stringToCheck) {
      for (String x : array) {
          if (x.equals(stringToCheck)) {
              return true;
          }
      }
      return false;
  }

  
  public static void main(String[] args) {
  try {
    BufferedReader reader = new BufferedReader(new FileReader("")); //give file name
    BufferedWriter writer = new BufferedWriter(new FileWriter("out1.txt"));
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.contains("//")) {
        writer.write("\n" + line.substring(0,line.indexOf("//")));
        continue;
      }
      boolean inComment = false;
      if (line.contains("/*")) {
        inComment = true;}
      if (!inComment) {
        writer.write("\n" + line);}
      if (line.contains("*/") && inComment) {
        inComment = false;}
    }
    writer.close();
    reader.close();

    
    BufferedReader reader2 = new BufferedReader(new FileReader("out1.txt"));
    BufferedWriter writer2 = new BufferedWriter(new FileWriter("out2.txt"));
    String line2;
    while ((line2 = reader2.readLine()) != null) {
      if (line2.trim().length() == 0 ) {
        continue;}
      writer2.write(line2.trim() + "\n");
    }
    writer2.close();
    reader2.close();


    
    BufferedReader reader2h = new BufferedReader(new FileReader("out2.txt"));
    BufferedWriter writer2h = new BufferedWriter(new FileWriter("out2h.txt"));
    String line2h;
    while ((line2h = reader2h.readLine()) != null) {
      String pattern = "[a-zA-Z]+|\"([^\"]*)\"|[=;<>+\\-*/(),{}\\[\\]&.|]|//.*|\\b\\d+\\b";
      Pattern regex = Pattern.compile(pattern);
      Matcher matcher = regex.matcher(line2h);
      while (matcher.find()) {
          String quotes = matcher.group(1);
          if (quotes != null) {
              writer2h.write(quotes.trim() + "\n");
          } else {
              writer2h.write(matcher.group().trim() + "\n");
          }
      }
    }
    writer2h.close();
    reader2h.close();

    

    BufferedReader reader3 = new BufferedReader(new FileReader("out2h.txt"));
    BufferedWriter writer3 = new BufferedWriter(new FileWriter("MainT.xml"));
    String line3;
    int i = 0;
    writer3.write("<tokens>" + "\n");
    while ((line3 = reader3.readLine()) != null) {
      if (line3.trim().length() == 0 ) {
        continue;}
      i = i + 1;
      String[] keyword = {"class", "method", "function", "constructor", "int", "boolean", "char", "void", "var", "static", "field", "let", "do", "if", "else", "while", "return", "true", "false", "null", "this"};
      String[] symbol = {"`", "~", "!", "@", "#", "$", "%", "^", "*", "(", ")", "-", "_", "+", "=", "{", "[", "}", "]", "|", ";", ":", "'", ",", ".", "/", "?"};
      String[] symbolAlt = {"<", ">", "\"", "&"};
      for (String k : keyword) {
        if (k.equals(line3)) {
          writer3.write("<keyword> " + k + " </keyword>" + "\n");
          break;
        }
      }
      for (String s : symbol) {
        if (s.equals(line3)) {
          writer3.write("<symbol> " + s + " </symbol>" + "\n");
          break;
        }
      }
      if (line3.contains("<")) {
        writer3.write("<symbol> " + "&lt;" + " </symbol>" + "\n");}
      if (line3.contains(">")) {
        writer3.write("<symbol> " + "&gt;" + " </symbol>" + "\n");}

      if (line3.contains("\"")) {
        writer3.write("<symbol> " + "&quot;" + " </symbol>" + "\n");}
      if (line3.contains("&")) {
        writer3.write("<symbol> " + "&amp;" + " </symbol>" + "\n");}
      if (line3.contains(" ")) {
        writer3.write("<stringConstant> " + line3 + " </stringConstant>" + "\n");}
      if (line3.matches("\\d+")) {
        writer3.write("<integerConstant> " + line3 + " </integerConstant>" + "\n");}
      if (!checkInArrays(keyword, line3) && !checkInArrays(symbol, line3)
           && !checkInArrays(symbolAlt, line3) && !line3.matches("\\d+") && !line3.contains(" ")) {
        writer3.write("<identifier> " + line3 + " </identifier>" + "\n");
      }
    }
    writer3.write("</tokens>" + "\n");
    writer3.close();
    reader3.close();

  } catch (IOException e) {
    e.printStackTrace();
  }
  }
}