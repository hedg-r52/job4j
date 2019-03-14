package netmanager;

import java.io.*;

public class SocketHelper {
    private InputStream in;
    private OutputStream out;

    public SocketHelper(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    /**
     * Send file from disk to socket
     *
     * @param filename full path to file
     * @param size     size of sending file
     * @throws IOException
     */
    public void sendFileToSocket(String filename, long size) throws IOException {
        int count;
        int totalRead = 0;
        long remaining = size;
        byte[] buffer = new byte[4096];
        try (FileInputStream fis = new FileInputStream(filename)) {
            while ((count = fis.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0) {
                totalRead += count;
                remaining -= count;
                out.write(buffer, 0, count);
                out.flush();
            }
            System.out.println("send " + totalRead + " bytes.");
        }
    }

    /**
     * Save file from socket
     *
     * @param filename full path to file
     * @param size     size of sending file
     */
    public void saveFileFromSocket(String filename, long size) {
        int count;
        int totalWrite = 0;
        long remaining = size;
        byte[] buffer = new byte[4096];
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            out.write("OK".getBytes());
            out.flush();
            while ((count = in.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0) {
                totalWrite += count;
                remaining -= count;
                fos.write(buffer, 0, count);
            }
            System.out.println("write " + totalWrite + " bytes.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
