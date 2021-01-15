package es.tessier.procesos.processBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class ProcessBuilderExercise {

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

    ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe","/c",args[1],file.toString());
    
    // can also run the java file like this
    //  processBuilder.command(args[1]);
    // processBuilder.directory(file.toFile());

    try {

      Process process = processBuilder.start();

      BufferedReader reader =
        new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }

      int exitCode = process.waitFor();
      System.out.println("\nExited with error code : " + exitCode);

    } catch (IOException e) {
    	e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
