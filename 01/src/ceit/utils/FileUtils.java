package ceit.utils;
import ceit.model.Note;

import java.io.*;

/**
 * the FileUtils class connects the INote app to files and has some methods to write to file and  ,
 * read from it.
 */
public class FileUtils {

    //notes path directory.
    private static final String NOTES_PATH = "./notes/";

    //It's a static initializer. It's executed when the class is loaded.
    //It's similar to constructor
    static {
        boolean isSuccessful = new File(NOTES_PATH).mkdirs();
        System.out.println("Creating " + NOTES_PATH + " directory is successful: " + isSuccessful);
    }

    /**
     * make a file in the given directory.
     *
     * @return file.
     */
    public static File[] getFilesInDirectory() {

        return new File(NOTES_PATH).listFiles();
    }

    //TODO: Phase1: define method here for reading file with InputStream
    //TODO: Phase1: define method here for writing file with OutputStream

//    /**
//     * the fileReader reads some data from a file using fileInputStream.
//     *
//     * @param file the file want to read items from it.
//     * @return content of a file.
//     * @throws IOException when could not open the file.
//     */
//    public static String fileReader(File file) throws IOException {
//        //TODO: Phase1: read content from file
//        FileInputStream in = null;
//        char[] array = new char[1024];
//        String text;
//        try {
//            in = new FileInputStream(file);
//            int c;
//            int i = 0;
//            while ((c = in.read()) != -1) {
//                array[i] = (char) c;
//                i++;
//            }
//            text = new String(array);
//        } finally {
//            if (in != null) {
//                in.close();
//            }
//        }
//        return text;
//    }

    //    /**
//     * the fileWriter writes some data to a file using fileOutputStream.
//     *
//     * @param content the content want to write to the file.
//     * @throws IOException when could not make the file.
//     */
//    public static void fileWriter(String content) throws IOException {
//        //TODO: write content on file
//        FileOutputStream out = null;
//        String fileName = getProperFileName(content);
//        try {
//            byte[] data = content.getBytes();
//            out = new FileOutputStream(NOTES_PATH + fileName + ".txt");
//            out.write(data);
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//    }
//
//    /**
//     * the fileReaderWithBuffer reads some data from a file using BufferedReader.
//     *
//     * @param file the file want to read items from it.
//     * @return content of a file.
//     */
    public static String fileReaderWithBuffer(File file) {
        //TODO: Phase1: read content from file
        String text = " ";
        char[] array = new char[1024];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int c;
            int i = 0;
            while ((c = bufferedReader.read()) != -1) {
                array[i] = (char) c;
                i++;
            }
            text = new String(array);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("cannot open the file");
        }
        return text;
    }

    //    /**
//     * the fileWriterWithBuffer writes some data to a file using BufferedWriter.
//     *
//     * @param content the content want to write items to file..
//     */
    public static void fileWriterWithBuffer(String content) {
        //TODO: write content on file
        String fileName = getProperFileName(content);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NOTES_PATH + fileName + ".txt"))) {
            bufferedWriter.write(content);

        } catch (IOException e) {
            System.out.println("Failed to write on file");
        }
    }
//

    //TODO: Phase2: proper methods for handling serialization

    /**
     * the writeFileObject take an object and write it to the file.
     *
     //     * @param note the Note object want to write on a file.
     //     */
    public static void writeFileObject(Note note) {
        File file = new File(NOTES_PATH+note.getTitle()+note.getDate() + ".bin");
        try (FileOutputStream fs = new FileOutputStream(file)) {

            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(note);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException er) {
            er.printStackTrace();
        }

    }

    /**
     * the readFileObject takes a file and read the binary data from it.
     *
     * @param file the file want to read.
     * @return content of the read file.
     */
    public static String readFileObject(File file) {
        Note note;
        try (FileInputStream fi = new FileInputStream(file)) {

            ObjectInputStream oi = new ObjectInputStream(fi);

            note = (Note) oi.readObject();
            return note.toString();

        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException er) {
            er.printStackTrace();
        }
        return " ";
    }

    /**
     * get the name of the file.
     *
     * @param content data of the file.
     * @return name of the file.
     */
    private static String getProperFileName(String content) {
        int loc = content.indexOf("\n");
        if (loc != -1) {
            return content.substring(0, loc);
        }
        if (!content.isEmpty()) {
            return content;
        }
        return System.currentTimeMillis() + "_new file.txt";
    }
}

