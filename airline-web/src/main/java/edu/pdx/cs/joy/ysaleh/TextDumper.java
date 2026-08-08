package edu.pdx.cs.joy.ysaleh;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;

public class TextDumper {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  public void dump(Airline airline, String src, String dest) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ){
      if (src != null && dest != null) {
        pw.println(airline.getName());
        for (Flight f : airline.getFlights()) {
          if (f.getSource().equals(src) && f.getDestination().equals(dest)) {
            pw.println(f.getNumber());
          }
        }
      }
      else {
        pw.println(airline.getName());
        for (Flight f : airline.getFlights()) {
          pw.println(f.getNumber());
        }
      }

      pw.flush();
    }
  }
}
