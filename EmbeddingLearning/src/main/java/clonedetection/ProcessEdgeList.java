package clonedetection;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProcessEdgeList {
    void get() {
        String baseDir = "/home/cary/data/";
//        String baseDir = "/home/cary/Documents/Data/CloneData";

        String dotDir = baseDir + File.separator + "dot";
        File[] subFiles = new File(dotDir).listFiles();
        try {
            for (int i = 0; i < subFiles.length; i++) {
                File[] dots = subFiles[i].listFiles();

                String edgePath = baseDir + File.separator + "edge" + File.separator + subFiles[i].getName();
//                System.out.println(edgePath);
                if(!new File(edgePath).exists()) {
                    new File(edgePath).mkdir();
                }

                for (int j = 0; j < dots.length; j++) {
//                    System.out.println(dots[j].getAbsolutePath());
                    String name = dots[j].getName();
                    name = name.substring(0, name.lastIndexOf("."));

                    BufferedWriter writer = new BufferedWriter(new FileWriter( edgePath + File.separator + name + ".edgelist"));

                    List<String> lines = FileUtils.readLines(dots[j], "utf-8");
                    for (String line : lines) {
//                        System.out.println(line);

                        if(line.contains("->") && line.contains("[color")) {
                            String edge = line.substring(0, line.indexOf("\t [color"));
                            edge = edge.substring("\t".length());
                            String start = edge.substring(0, edge.indexOf("->") - 1);
                            String end = edge.substring(edge.indexOf("->") + 3);
                            writer.write(start);
                            writer.write(" ");
                            writer.write(end);
                            writer.newLine();
                            writer.flush();
                        }
                    }
                    writer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProcessEdgeList processEdgeList = new ProcessEdgeList();
        processEdgeList.get();
    }
}
