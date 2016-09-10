import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String NEWLINE = "\n";

    public static void main(String[] args) throws Exception {

        // int num = countNumByte();
        // RandomAccessFile randomAccessFile = null;
        //
        // String line1 = "line\n";
        // String line2 = "asdf1234\n";
        //
        // // read / write permissions
        // randomAccessFile = new RandomAccessFile("test.txt", "r");
        //
        // // randomAccessFile.writeBytes(line1);
        // // randomAccessFile.writeBytes(line2);
        //
        // // Place the file pointer at the end of the first line
        // randomAccessFile.seek(0);
        //
        // byte[] buffer = new byte[num];
        // randomAccessFile.read(buffer);
        // System.out.println(new String(buffer));
        //
        // randomAccessFile.close();

        // readLine2(0);

        String str = extractLineFromFile("test.txt", 1, 9003);
        System.out.println(str);
        System.out.println("--");
    }

    private static String extractLineFromFile(String filePath, int from, int to) throws Exception {
        if (from < 1) {
            throw new Exception("The lowest line number is 1!");
        }

        // note this line only needs to be built once
        String[] strArr = readFile2StrArr(filePath);

        if (to > strArr.length) {
            throw new Exception("to exceeding the number of lines of the file!");
        }

        if (from > to) {
            throw new Exception("From is greater than To!");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = from - 1; i <= to - 2; i++) {
            sb.append(strArr[i] + NEWLINE);
        }

        sb.append(strArr[to - 1]);

        return sb.toString();
    }

    // THIS METHOD WILL ONLY NEED TO BE RUN ONCE
    // CONSTRUCT A GLOBAL PRIVAT FINAL STRING ARR TO GET THE VALUE
    private static String[] readFile2StrArr(String filePath) throws IOException {

        RandomAccessFile raf = new RandomAccessFile(filePath, "r");
        int line = 0;
        String lineContent = null;
        List<String> list = new ArrayList<String>();
        String[] strArr = null;
        while (true) {
            line++;
            lineContent = raf.readLine();

            if (lineContent == null) {
                break;
            }
            list.add(lineContent);
        }

        strArr = new String[list.size()];
        strArr = list.toArray(strArr);
        return strArr;
    }

    private static int countNumByte() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("test.txt", "r");
        randomAccessFile.seek(0);
        // randomAccessFile.readl
        int count = 0;
        // boolean eof = false;
        while (true) {
            count++;
            int byteValue = randomAccessFile.read();
            if (byteValue == -1) {
                // System.out.println(count);
                break;
            }

        }
        randomAccessFile.close();
        return count;
    }

}