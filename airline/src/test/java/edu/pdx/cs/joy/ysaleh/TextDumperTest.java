package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

  @Test
  void airlineNameIsDumpedInTextFormat() throws IOException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);
    String testFile = "saleh_project2_test_file.txt";

    TextDumper dumper = new TextDumper(testFile);
    dumper.dump(airline);

    // inspired by example from Google AI after searching "how to extract first line of a file in java"
    BufferedReader br = new BufferedReader(new FileReader(testFile));
    String text = br.readLine();
    assertThat(text, containsString(airlineName));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);
    String testFile = "saleh_project2_test_file.txt";

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(testFile);
    dumper.dump(airline);

    TextParser parser = new TextParser(testFile);
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
  }
}
