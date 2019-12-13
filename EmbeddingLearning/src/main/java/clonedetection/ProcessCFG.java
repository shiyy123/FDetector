package clonedetection;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProcessCFG {
    void get() {

        String baseDir = "/home/cary/data/";
//        String baseDir = "/home/cary/Documents/Data/CloneData/";

        String funPath = baseDir + File.separator + "cfg/func2.txt";
        try {
            List<String> lines = FileUtils.readLines(new File(funPath), "utf-8");

            for (int i = 0; i < lines.size(); i++) {
                if(i % 500 == 0) {
                    Process p1 = Runtime.getRuntime().exec("/home/cary/software/neo4j-community-2.1.8/bin/neo4j stop");
                    CodeRepresentation.processMessage(p1.getInputStream(), true);
                    CodeRepresentation.processMessage(p1.getErrorStream(), true);
                    p1.waitFor();

                    Process p2 = Runtime.getRuntime().exec("/home/cary/software/neo4j-community-2.1.8/bin/neo4j start");
                    CodeRepresentation.processMessage(p2.getInputStream(), true);
                    CodeRepresentation.processMessage(p2.getErrorStream(), true);
                    p2.waitFor();

                    System.out.println(i / 500);
                }

                String shDir = baseDir + File.separator + "sh" + File.separator + (i / 500) + "/";
                if(!new File(shDir).exists()) {
                    new File(shDir).mkdir();
                }

                String dotDir = baseDir + File.separator + "pdg" + File.separator + (i / 500) + "/";
                if(!new File(dotDir).exists()) {
                    new File(dotDir).mkdir();
                }


                String line = lines.get(i);
                String[] strings = line.split("\t");
                int func_id = Integer.parseInt(strings[1]);

                String dotRes = dotDir + File.separator + func_id + ".dot";
                if(new File(dotRes).exists() && new File(dotRes).length() > 0) {
                    continue;
                }

                String shPath = shDir + func_id + ".sh";
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(shPath));
                bufferedWriter.write("#!/bin/sh");
                bufferedWriter.newLine();
                bufferedWriter.write("echo " + func_id + " | joern-plot-proggraph -ddg -cfg > " + dotDir + File.separator + func_id + ".dot;");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                bufferedWriter.close();

                Process process = Runtime.getRuntime().exec("sh " + shPath);
                CodeRepresentation.processMessage(process.getErrorStream(), true);
                CodeRepresentation.processMessage(process.getInputStream(), true);

                process.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProcessCFG processCFG = new ProcessCFG();
        processCFG.get();
    }
}
