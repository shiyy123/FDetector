package clonedetection;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProcessSingleJoern {
    private void RunCMD(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            CodeRepresentation.processMessage(process.getErrorStream(), false);
            CodeRepresentation.processMessage(process.getInputStream(), false);

            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void RunCMDWorkPath(String cmd, String workPath) {
        try {
            System.out.println(workPath);
            Process process = Runtime.getRuntime().exec(cmd, null, new File(workPath));
            CodeRepresentation.processMessage(process.getErrorStream(), false);
            CodeRepresentation.processMessage(process.getInputStream(), false);

            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void RunSH(String shPath, String workPath) {
        String cmd = "sh " + shPath;
        try {
            Process process = Runtime.getRuntime().exec(cmd, null, new File(workPath));
            CodeRepresentation.processMessage(process.getErrorStream(), false);
            CodeRepresentation.processMessage(process.getInputStream(), false);

            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stopNeo4j(String neo4jPath) {
        String cmd = neo4jPath + "/bin/neo4j stop";
        RunCMD(cmd);
    }

    private void startNeo4j(String neo4jPath) {
        String cmd = neo4jPath + "/bin/neo4j start";
        RunCMD(cmd);
    }

    private void generateFuncList(String workPath) {
        String cmd = "joern-list-funcs > func.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(workPath + "/generateFuncList.sh"));
            writer.write(cmd);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RunSH(workPath + "/generateFuncList.sh", workPath);
        new File(workPath + "/generateFuncList.sh").delete();
    }

    private void generateCallList(String workPath) {
        try {
            List<String> funcList = FileUtils.readLines(new File(workPath + "/func.txt"), "utf-8");
            new File(workPath + "/call").mkdir();
            for (String func : funcList) {
                String[] data = func.split("\t");
                String funcName = data[0];
                String funcId = data[1];

                // echo 'getCallsTo(f)' | joern-lookup -g > call/10.txt
                String cmd = "echo 'getCallsTo(\"" + funcName + "\")' | joern-lookup -g > call/" + funcId + ".txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(workPath + "/generateCallList.sh"));
                writer.write(cmd);
                writer.flush();
                writer.close();
                RunSH(workPath + "/generateCallList.sh", workPath);
            }
            new File(workPath + "/generateCallList.sh").delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifyNeo4jConf(String neo4jPath, String codeDir) {
        String confCodeDir = "org.neo4j.server.database.location=";
        try {
            List<String> confs = FileUtils.readLines(new File(neo4jPath + "/conf/neo4j-server.properties"), "utf-8");
            BufferedWriter writer = new BufferedWriter(new FileWriter(neo4jPath + "/conf/tmp.properties"));
            for (String conf : confs) {
                if(conf.startsWith(confCodeDir)) {
                    writer.write(confCodeDir + codeDir);
                } else {
                    writer.write(conf);
                }
                writer.newLine();
                writer.flush();
            }
            writer.close();
            new File(neo4jPath + "/conf/neo4j-server.properties").delete();
            new File(neo4jPath + "/conf/tmp.properties").renameTo(new File(neo4jPath + "/conf/neo4j-server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void process() {
//        String base = "/home/cary/Documents/Data/CloneData/";
//        String neo4jPath = "/usr/software/neo4j-community-2.1.8/";

        String base = "/home/cary/data/";
        String neo4jPath = "/home/cary/software/neo4j-community-2.1.8/";


        String joernPath = base + "cfg/joern.jar";
        File[] srcFolders = new File(base + "/src").listFiles();
        for (File srcFolder : srcFolders) {
//            if(!srcFolder.getName().equals("1")) {
//                continue;
//            }

            new File(base + "/singlejoern/" + srcFolder.getName()).mkdir();
            File[] srcFiles = srcFolder.listFiles();
            for (File srcFile : srcFiles) {
                String srcPath = base + "/singlejoern/" + srcFolder.getName() + "/"
                        + srcFile.getName().substring(0, srcFile.getName().indexOf("."));
                if(new File(srcPath).exists()) {
                    continue;
                }
                new File(srcPath).mkdir();// singlejoern/1/13
                try {
                    FileUtils.copyFile(srcFile, new File(srcPath + File.separator + srcFile.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String cmd = "java -jar " + joernPath + " " + srcPath;
                try {
                    Process p = Runtime.getRuntime().exec(cmd, null, new File(srcPath));
                    p.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                //echo 'getAllCalls()' | joern-lookup -g > call.txt
                //joern-list-funcs > func.txt
                stopNeo4j(neo4jPath);

                modifyNeo4jConf(neo4jPath, srcPath + "/.joernIndex");

                startNeo4j(neo4jPath);

                generateFuncList(srcPath);
                generateCallList(srcPath);

                stopNeo4j(neo4jPath);

            }
        }
    }

    public static void main(String[] args) {
        ProcessSingleJoern processSingleJoern = new ProcessSingleJoern();
        processSingleJoern.process();
    }
}
