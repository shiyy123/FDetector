package clonedetection;

import java.io.*;

public class CodeRepresentation {
    static void processMessage(final InputStream inputStream, final boolean isDebuggable) {
        new Thread(() -> {
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(reader);
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    if (isDebuggable) {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    void processCode() {
        String sootPath = "bin/soot.jar";
//        SootMethod method = new SootMethod();
//        try {
//            File[] files = new File("/home/cary/Documents/Data/SourceCC/Clone/").listFiles();
//            for (File file : files) {
//                String filePath = file.getName();
//
//                String cmd = "java -cp /home/cary/Documents/Code/soot-3.0.0/bin/soot.jar soot.Main -cp .:/usr/lib/jdk/jdk1.8.0_161/jre/lib/rt.jar " + filePath;
//                Process process = Runtime.getRuntime().exec(cmd, null, new File("/home/cary/Documents/Data/SourceCC/Representation"));
//                processMessage(process.getInputStream(), true);
//                processMessage(process.getErrorStream(), true);
//                process.waitFor();
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
