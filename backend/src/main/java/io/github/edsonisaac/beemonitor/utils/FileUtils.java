package io.github.edsonisaac.beemonitor.utils;

import io.github.edsonisaac.beemonitor.exceptions.OperationFailureException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type File utils.
 *
 * @author Edson Isaac
 */
public abstract class FileUtils {

    /**
     * The constant FILES.
     */
    public static final Map<String, MultipartFile> FILES = new HashMap<>();

    /**
     * The constant IMAGES_DIRECTORY.
     */
    public static final String IMAGES_DIRECTORY = File.separator + "files" + File.separator + "images";

    /**
     * Find.
     *
     * @param filename the filename
     * @param path     the path
     * @return the file
     * @throws FileNotFoundException the file not found exception
     */
    public static File find(String filename, String path) throws FileNotFoundException {

        File file = new File(System.getProperty("user.dir") + path + File.separator + filename);

        if (!file.exists()) {
            throw new FileNotFoundException("Arquivo não encontrado!");
        }

        return file;
    }

    /**
     * Save.
     *
     * @param filename the filename
     * @param file     the file
     * @param path     the path
     * @return the file
     * @throws IOException the io exception
     */
    public static File save(String filename, MultipartFile file, String path) throws IOException {


        if (checkPathDestination(path)) {
            Path filePath = Paths.get(System.getProperty("user.dir") + path, filename);
            Files.write(filePath, file.getBytes());

            return find(filename, path);
        }

        throw new OperationFailureException("Diretório não encontrado!");
    }

    /**
     * Delete.
     *
     * @param filename the filename
     * @param path     the path
     * @return the boolean
     */
    public static boolean delete(String filename, String path) {
        File file = new File(System.getProperty("user.dir") + path + "/" + filename);

        if (file.exists() && file.isFile()) {
            return file.delete();
        }

        return true;
    }

    /**
     * Get extension.
     *
     * @param object the object
     * @return the extension
     * @throws FileNotFoundException the file not found exception
     */
    public static String getExtension(Object object) throws FileNotFoundException {

        if (object instanceof File) {

            var file = ((File) object);

            if (!file.exists()) {
                throw new FileNotFoundException("Arquivo não encontrado!");
            }

            return file.getName().replace(".", " ").split(" ")[1];
        }

        if (object instanceof MultipartFile) {

            var file = ((MultipartFile) object);

            return file.getOriginalFilename().replace(".", " ").split(" ")[1];
        }

        throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
    }

    /**
     * Check path destination.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean checkPathDestination(String path) {

        File directory = new File(System.getProperty("user.dir") + path);

        if (!directory.exists()) {
            return directory.mkdir();
        }

        return true;
    }
}
