package io.github.edsonisaac.beemonitor.utils;

import io.github.edsonisaac.beemonitor.exceptions.OperationFailureException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public abstract class FileUtils {

    public static final Map<String, MultipartFile> FILES = new HashMap<>();

    public static final String IMAGES_DIRECTORY = File.separator + "data" + File.separator + "files" + File.separator + "images";

    public static File find(String filename, String path) throws FileNotFoundException {

        final var file = new File(System.getProperty("user.dir") + path + File.separator + filename);

        if (!file.exists()) {
            throw new FileNotFoundException("Arquivo não encontrado!");
        }

        return file;
    }

    public static File save(String filename, MultipartFile file, String path) throws IOException {


        if (checkPathDestination(path)) {
            final var filePath = Paths.get(System.getProperty("user.dir") + path, filename);
            Files.write(filePath, file.getBytes());

            return find(filename, path);
        }

        throw new OperationFailureException("Diretório não encontrado!");
    }

    public static boolean delete(String filename, String path) {

        final var file = new File(System.getProperty("user.dir") + path + "/" + filename);

        if (file.exists() && file.isFile()) {
            return file.delete();
        }

        return true;
    }

    public static String getExtension(Object object) throws FileNotFoundException {

        if (object instanceof File) {

            final var file = ((File) object);

            if (!file.exists()) {
                throw new FileNotFoundException("Arquivo não encontrado!");
            }

            return file.getName().replace(".", " ").split(" ")[1];
        }

        if (object instanceof MultipartFile) {
            final var file = ((MultipartFile) object);
            return file.getOriginalFilename().replace(".", " ").split(" ")[1];
        }

        throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
    }

    public static boolean checkPathDestination(String path) {

        final var directory = new File(System.getProperty("user.dir") + path);

        if (!directory.exists()) {
            return directory.mkdir();
        }

        return true;
    }
}
