package es.tessier.procesos.runtime;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RuntimeExercise {

  public static void main(String[] args) {

    if (args.length < 1) {
      System.err.println("Se necesitan argumentos para ejecutar el programa");
      System.exit(-1);
    }

    Runtime runtime = Runtime.getRuntime();

    try {

      Process process = runtime.exec(args);
	
      BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

     	process.destroy();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
