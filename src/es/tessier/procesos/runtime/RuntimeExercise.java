package es.tessier.procesos.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RuntimeExercise {

	public static void main(String[] args) {

	  if (args.length < 2) {
	      System.err.println("Se necesitan dos argumentos, un shell, un directorio y un comando a ejecutar");
	      System.exit(-1);
	    }

	    Path file = Paths.get(args[0]);

	    if (!Files.exists(file) || !Files.isDirectory(file)) {
	      System.err.println("Se necesita un directorio valido para a ejecutar");
	      System.exit(-1);
	    }

    Runtime runtime = Runtime.getRuntime();

    try {

      Process process = runtime.exec(new String[]{"cmd", "/c", args[1]}, null, file.toFile());
	
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
