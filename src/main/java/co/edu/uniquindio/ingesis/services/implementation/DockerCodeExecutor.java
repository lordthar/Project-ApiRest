package co.edu.uniquindio.ingesis.services.implementation;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class DockerCodeExecutor {

    private static final long TIMEOUT_SECONDS = 30;

    public static String ejecutarCodigoEnContenedor(String codigoJava) throws IOException, InterruptedException {
        String className = extraerNombreClase(codigoJava);
        if (className == null) {
            throw new IllegalArgumentException("No se encontró una clase pública en el código.");
        }

        // Archivo fuente
        File dir = new File(System.getProperty("java.io.tmpdir"));
        File sourceFile = new File(dir, className + ".java");
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write(codigoJava);
        }

        // Nombre aleatorio de contenedor
        String containerName = "code_executor_" + UUID.randomUUID();

        // Comando Docker
        List<String> command = Arrays.asList(
                "docker", "run", "--name", containerName,
                "-v", dir.getAbsolutePath() + ":/app",
                "-w", "/app",
                "openjdk:17-jdk",
                "bash", "-c",
                "javac " + className + ".java && java " + className
        );

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> outputReader = executor.submit(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            } catch (IOException e) {
                output.append("Error leyendo la salida: ").append(e.getMessage()).append("\n");
            }
        });

        boolean finishedInTime = process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        if (!finishedInTime) {
            process.destroyForcibly();
            outputReader.cancel(true);
            executor.shutdownNow();
            eliminarContenedor(containerName);
            throw new RuntimeException("Tiempo de ejecución excedido. El contenedor fue detenido.");
        }

        executor.shutdown();
        eliminarContenedor(containerName);
        return output.toString();
    }

    private static void eliminarContenedor(String containerName) {
        try {
            new ProcessBuilder("docker", "rm", "-f", containerName)
                    .start()
                    .waitFor(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("No se pudo eliminar el contenedor: " + containerName);
        }
    }

    private static String extraerNombreClase(String codigo) {
        Matcher matcher = Pattern.compile("public\\s+class\\s+(\\w+)").matcher(codigo);
        return matcher.find() ? matcher.group(1) : null;
    }
}
