//A rudimentary text-based file system created for a school project.

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Scanner;

public class FileManagementSystem {
    private static String copiedFilePath = null;

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        File d1 = new File(System.getProperty("user.dir"));
        String baseDirectory = d1.toString() + "\\";

        // Main loop to display menu and handle user choices
        while (true) {
            // Display menu options

            System.out.println("File Management System");
            System.out.println(" 1. Create a file");
            System.out.println(" 2. Read a file");
            System.out.println(" 3. Update a file");
            System.out.println(" 4. Delete a file");
            System.out.println(" 5. Create a directory");
            System.out.println(" 6. Delete a directory");
            System.out.println(" 7. Check directory");
            System.out.println(" 8. Search for files path");
            System.out.println(" 9. File Details");
            System.out.println("10. Change Permissions");
            System.out.println("11. Copy File");
            System.out.println("12. Paste File");
            System.out.println("13. Encrypt/Decrypt File");
            System.out.println("14. Change Directory");
            System.out.println("15. Go to Parent Directory");
            System.out.println("16. Exit");
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Switch statement to handle user choices
                switch (choice) {

                    case 1:
                        System.out.print("Enter the file name: ");
                        String createFileName = scanner.nextLine();
                        createFile(baseDirectory + createFileName);
                        break;
                    case 2:
                        System.out.print("Enter the file name: ");
                        String readFileName = scanner.nextLine();
                        readFile(baseDirectory + readFileName);
                        break;
                    case 3:
                        System.out.print("Enter the file name: ");
                        String updateFileName = scanner.nextLine();
                        updateFile(baseDirectory + updateFileName);
                        break;
                    case 4:
                        System.out.print("Enter the file name: ");
                        String deleteFileName = scanner.nextLine();
                        deleteFile(baseDirectory + deleteFileName);
                        break;
                    case 5:
                        System.out.print("Enter the directory name: ");
                        String createDirectoryName = scanner.nextLine();
                        createDirectory(baseDirectory + createDirectoryName);
                        break;
                    case 6:
                        System.out.print("Enter the directory name: ");
                        String deleteDirectoryName = scanner.nextLine();
                        deleteDirectory(baseDirectory + deleteDirectoryName);
                        break;
                    case 7:
                        System.out.print("Enter the directory name: ");
                        String viewDirectoryName = scanner.nextLine();
                        listFilesInDirectory(baseDirectory + viewDirectoryName);
                        break;
                    case 8:
                        System.out.print("Enter the search query: ");
                        String searchQuery = scanner.nextLine();
                        searchFilesByName(baseDirectory, searchQuery);
                        break;
                    case 9:
                        System.out.print("Enter the file name: ");
                        String fileDetail = scanner.nextLine();
                        getFileDetails(baseDirectory + fileDetail);
                        break;
                    case 10:
                        System.out.print("Enter the file name: ");
                        String permissionsFileName = scanner.nextLine();
                        changePermissions(baseDirectory + permissionsFileName);
                        break;
                    case 11:
                        System.out.print("Enter the source file name: ");
                        String sourceFileName = scanner.nextLine();
                        copyFile(baseDirectory + sourceFileName);
                        break;
                    case 12:
                        System.out.print("Enter the destination directory: ");
                        String destinationDirectory = scanner.nextLine();
                        pasteFile(destinationDirectory);
                        break;
                    case 13:
                        System.out.print("Enter the file name: ");
                        String encryptFileName = scanner.nextLine();
                        encryptFile(baseDirectory + encryptFileName);
                        break;
                    case 14:
                        System.out.print("Enter the directory path: ");
                        String newDirectory = scanner.nextLine();
                        baseDirectory = changeDirectory(baseDirectory, newDirectory);
                        break;
                    case 15:
                        baseDirectory = goToParentDirectory(baseDirectory);
                        break;
                    case 16:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Please enter a valid integer (1-16) for choice.");
                scanner.nextLine();
            }
            System.out.println();
        }
    }

    /**
     * Creates a new file with the specified file name.
     * If the parent directory of the file doesn't exist, it creates the directory
     * first.
     * If the file is created successfully, it prints a success message.
     * If the file already exists, it prints a message indicating that the file
     * exists.
     * If an error occurs during file creation, it prints an error message.
     *
     * @param fileName The name of the file to be created.
     */

    private static void createFile(String fileName) {
        try {
            File file = new File(fileName);
            File directory = file.getParentFile();
            if (directory != null && !directory.exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Directory created: " + directory.getAbsolutePath());
                } else {
                    System.out.println("Failed to create directory: " + directory.getAbsolutePath());
                    return;
                }
            }
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
        }
    }

    /**
     * Creates a new directory with the specified directory name.
     * If the directory is created successfully, it prints the absolute path of the
     * created directory.
     * If the directory creation fails, it prints an error message with the absolute
     * path of the failed directory.
     *
     * @param directoryName The name of the directory to be created.
     */

    private static void createDirectory(String directoryName) {
        File directory = new File(directoryName);
        if (directory.mkdirs()) {
            System.out.println("Directory created: " + directory.getAbsolutePath());
        } else {
            System.out.println("Failed to create directory: " + directory.getAbsolutePath());
        }
    }

    /**
     * Reads the contents of a file with the specified file name.
     * It uses a Scanner to read the file line by line and prints each line to the
     * console.
     * If the file is not found, it prints a "File not found" message.
     *
     * @param fileName The name of the file to be read.
     */

    private static void readFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                System.out.println(data);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    /**
     * Updates the contents of a file with the specified file name.
     * If the file exists, it prompts the user to enter new content for the file.
     * It then writes the new content to the file using a FileWriter and closes the
     * writer.
     * If the file is updated successfully, it prints a success message.
     * If the file does not exist, it prints a message indicating that the file does
     * not exist.
     * If the file does not have write permissions, it prints an error message.
     *
     * @param fileName The name of the file to be updated.
     */

    private static void updateFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                @SuppressWarnings("resource")
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter the new content for the file:");
                String content = scanner.nextLine();
                FileWriter writer = new FileWriter(file);
                writer.write(content);
                writer.close();
                System.out.println("File updated successfully.");
            } else {
                System.out.println("File does not exist.");
            }
        } catch (IOException e) {
            System.out.println("File does not have write permissions.");
        }
    }

    /**
     * Deletes a file with the specified file name.
     * If the file is deleted successfully, it prints a success message with the
     * deleted file name.
     * If the file does not exist, it prints a message indicating that the file does
     * not exist.
     *
     * @param fileName The name of the file to be deleted.
     */

    private static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("File deleted: " + file.getName());
        } else {
            System.out.println("File to delete does not exist.");
        }
    }

    /**
     * Deletes a directory with the specified directory name and all its contents
     * recursively.
     * If the directory exists and is a directory, it retrieves the list of files
     * and directories inside it.
     * For each subdirectory, it recursively calls the deleteDirectory method to
     * delete its contents.
     * For each file, it deletes the file using the delete method.
     * After deleting all the contents, it deletes the directory itself.
     * If the directory is deleted successfully, it prints a success message with
     * the deleted directory path.
     * If the directory deletion fails, it prints an error message with the failed
     * directory path.
     * If the specified directory does not exist, it prints a message indicating
     * that the directory does not exist.
     *
     * @param directoryName The name of the directory to be deleted.
     */

    private static void deleteDirectory(String directoryName) {
        File directory = new File(directoryName);
        if (directory.exists()) {
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            deleteDirectory(file.getAbsolutePath());
                        } else {
                            file.delete();
                        }
                    }
                }
            }
            if (directory.delete()) {
                System.out.println("Directory deleted: " + directory.getAbsolutePath());
            } else {
                System.out.println("Failed to delete the directory: " + directory.getAbsolutePath());
            }
        } else {
            System.out.println("Directory does not exist.");
        }
    }

    /**
     * Lists all the files and directories in the specified directory path.
     * If the directory exists and is a directory, it retrieves the list of files
     * and directories using listFiles().
     * For each file, it prints "File: " followed by the file name.
     * For each directory, it prints "Directory: " followed by the directory name.
     * If the directory is empty, it prints a message indicating that no files were
     * found.
     * If the specified path is not a directory or does not exist, it prints an
     * appropriate message.
     *
     * @param directoryPath The path of the directory to list files and directories.
     */

    private static void listFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                System.out.println("Files in directory " + directoryPath + ":");
                for (File file : files) {
                    if (file.isFile()) {
                        System.out.println("File: " + file.getName());
                    } else if (file.isDirectory()) {
                        System.out.println("Directory: " + file.getName());
                    }
                }
            } else {
                System.out.println("No files found in directory.");
            }
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }
    }

    /**
     * Searches for files with names containing the specified search query within
     * the directory and its subdirectories.
     * It calls the overloaded searchFilesByName method with the directory path and
     * search query.
     *
     * @param directoryPath The path of the directory to search for files.
     * @param searchQuery   The search query to match against file names.
     */

    private static void searchFilesByName(String directoryPath, String searchQuery) {
        searchFilesByName(new File(directoryPath), searchQuery);
    }

    private static void searchFilesByName(File directory, String searchQuery) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().contains(searchQuery)) {
                    System.out.println("Found matching file: " + file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    searchFilesByName(file, searchQuery);
                }
            }
        }
    }

    /**
     * Recursively searches for files with names containing the specified search
     * query within the directory and its subdirectories.
     * For each file in the directory, it checks if the file name contains the
     * search query.
     * If a match is found, it prints the absolute path of the file.
     * If a subdirectory is encountered, it recursively calls the searchFilesByName
     * method to search within that directory.
     *
     * @param directory   The directory to search for files.
     * @param searchQuery The search query to match against file names.
     */

    private static void getFileDetails(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("File details for " + fileName + ":");
            System.out.println("Name: " + file.getName());
            System.out.println("Size: " + file.length() + " bytes");
            System.out.println("Last modified: " + new Date(file.lastModified()));
        } else {
            System.out.println("File does not exist.");
        }
    }

    /**
     * Changes the read and write permissions of a file with the specified file
     * name.
     * If the file exists, it prompts the user to enter the new permissions as 'r'
     * for read and 'w' for write.
     * It then sets the read and write permissions of the file using setReadable()
     * and setWritable() based on the user's input.
     * If the permissions are changed successfully, it prints a success message.
     * If the file does not exist, it prints a message indicating that the file does
     * not exist.
     * If the user provides invalid input, it prints an appropriate message.
     *
     * @param fileName The name of the file to change permissions for.
     */

    private static void changePermissions(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the new permissions as ('r' or 'w'(read/write): ");
            String permissions = scanner.nextLine();
            boolean read = permissions.contains("r");
            boolean write = permissions.contains("w");
            if (read || write) {
                file.setReadable(read);
                file.setWritable(write);
                System.out.println("Permissions changed successfully.");
            } else {
                System.out.println("Invalid input provided.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    /**
     * Copies a file with the specified source file name to the clipboard.
     * If the source file exists, it stores the absolute path of the file in the
     * copiedFilePath variable.
     * It then prints a message indicating that the file is copied to the clipboard.
     * If the source file does not exist, it prints a message indicating that the
     * source file does not exist.
     *
     * @param sourceFileName The name of the file to be copied.
     */

    private static void copyFile(String sourceFileName) {
        File sourceFile = new File(sourceFileName);
        if (sourceFile.exists()) {
            copiedFilePath = sourceFile.getAbsolutePath();
            System.out.println("File copied to clipboard: " + copiedFilePath);
        } else {
            System.out.println("Source file does not exist.");
        }
    }

    /**
     * Encrypts a file with the specified file name using a simple encryption
     * algorithm.
     * If the file exists, it prompts the user to enter an initial value for
     * encryption.
     * It reads the file contents as bytes using Files.readAllBytes().
     * It then encrypts the file contents using the Crypt() function, which performs
     * a bitwise XOR operation
     * with a keystream generated based on the initial value and a feedback
     * polynomial.
     * The encrypted data is written back to the file using Files.write().
     * If the file is encrypted successfully, it prints a success message.
     * If the file does not exist, it prints a message indicating that the file does
     * not exist.
     * If an error occurs during encryption, it prints an error message and the
     * stack trace.
     *
     * @param fileName The name of the file to be encrypted.
     */

    private static void encryptFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                @SuppressWarnings("resource")
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the initial value for encryption: ");
                String initialValue = scanner.nextLine();
                byte[] fileData = Files.readAllBytes(file.toPath());
                byte[] encryptedData = Crypt(fileData, Long.parseUnsignedLong(initialValue.substring(2), 16));
                Files.write(file.toPath(), encryptedData);
                System.out.println("File encrypted successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while encrypting the file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    private static long feedbackPolynomial = 0x87654321L;

    /**
     * Performs encryption/decryption on the given data using a bitwise XOR
     * operation and a keystream.
     * The keystream is generated based on the initial value and the feedback
     * polynomial.
     * For each byte of data, it generates 8 bits of keystream by shifting the
     * initial value right by 1 bit.
     * If the least significant bit of the initial value is 0, it simply shifts the
     * value.
     * If the least significant bit is 1, it shifts the value and XORs it with the
     * feedback polynomial.
     * It then XORs each byte of data with the corresponding byte of the keystream.
     * The encrypted/decrypted data is returned as a byte array.
     *
     * @param data         The data to be encrypted/decrypted.
     * @param initialValue The initial value used to generate the keystream.
     * @return The encrypted/decrypted data as a byte array.
     */

    static byte[] Crypt(byte[] data, long initialValue) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 8; j++) {
                if ((initialValue & 1) == 0) {
                    initialValue = initialValue >>> 1;
                } else {
                    initialValue = (initialValue >>> 1) ^ feedbackPolynomial;
                }
            }
            byte keystream = (byte) (initialValue & 0xFF);
            result[i] = (byte) (data[i] ^ keystream);
        }
        return result;
    }

    /**
     * Changes the current directory to the specified new directory relative to the
     * current directory.
     * It creates a File object representing the new directory path.
     * If the new directory exists and is a directory, it updates the current
     * directory to the new directory path
     * and prints a message indicating the change.
     * If the new directory does not exist or is not a directory, it prints an
     * appropriate message
     * and returns the original current directory.
     *
     * @param currentDirectory The current directory path.
     * @param newDirectory     The new directory to change to, relative to the
     *                         current directory.
     * @return The updated current directory path.
     */

    private static String changeDirectory(String currentDirectory, String newDirectory) {
        File directory = new File(currentDirectory, newDirectory);
        if (directory.exists() && directory.isDirectory()) {
            System.out.println("Changed directory to: " + directory.getAbsolutePath());
            return directory.getAbsolutePath() + "/";
        } else {
            System.out.println("Directory does not exist or is not a directory.");
            return currentDirectory;
        }
    }

    /**
     * Pastes a previously copied file to the specified destination directory.
     * If a file path is currently stored in the copiedFilePath variable, it prompts
     * the user to enter a destination file name.
     * It creates a Path object representing the destination path by combining the
     * parent directory of the source file,
     * the destination directory, and the destination file name.
     * It copies the source file to the destination path using Files.copy() and
     * prints a success message.
     * If the destination path is not found, it prints an appropriate message.
     * If the source file no longer exists, it prints a message indicating that the
     * source file no longer exists.
     * If no file is currently copied to the clipboard, it prints a message
     * indicating that no file is copied.
     *
     * @param destinationDirectory The destination directory to paste the file.
     */

    private static void pasteFile(String destinationDirectory) {
        if (copiedFilePath != null) {
            File sourceFile = new File(copiedFilePath);
            if (sourceFile.exists()) {
                @SuppressWarnings("resource")
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the destination file name: ");
                String destinationFileName = scanner.nextLine();
                Path destinationPath = Paths.get(sourceFile.getParentFile().getAbsolutePath(), destinationDirectory,
                        destinationFileName);
                try {
                    Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("File pasted successfully.");
                } catch (IOException e) {
                    System.out.println("Destination path not found.");
                }
            } else {
                System.out.println("Source file no longer exists.");
            }
        } else {
            System.out.println("No file is currently copied to the clipboard.");
        }
    }

    /**
     * Navigates to the parent directory of the current directory.
     * It creates a File object representing the current directory.
     * It retrieves the parent directory using getParentFile().
     * If the parent directory exists, it updates the current directory to the
     * parent directory path
     * and prints a message indicating the change.
     * If the current directory is already at the root level, it prints an
     * appropriate message
     * and returns the original current directory.
     *
     * @param currentDirectory The current directory path.
     * @return The updated current directory path.
     */

    private static String goToParentDirectory(String currentDirectory) {
        File directory = new File(currentDirectory);
        File parentDirectory = directory.getParentFile();
        if (parentDirectory != null && parentDirectory.exists()) {
            System.out.println("Moved to parent directory: " + parentDirectory.getAbsolutePath());
            return parentDirectory.getAbsolutePath() + "/";
        } else {
            System.out.println("Already at the root directory.");
            return currentDirectory;
        }
    }
}