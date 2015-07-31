package frames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamRender extends Thread {

public static final String STDOUT = "STDOUT";
public static final String STDERR = "STDERR";

private InputStream inputStream;
private String inputType;

public StreamRender(InputStream inputStream, String inputType) {
  this.inputStream = inputStream;
  this.inputType = inputType;
}

public void run() {
  try {
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader br = new BufferedReader(inputStreamReader);
    String line = null;
    while ((line = br.readLine()) != null) {
      System.out.println(this.inputType + " > " + line);
    }
  } catch (IOException ioe) {
    ioe.printStackTrace();
  }
}
}