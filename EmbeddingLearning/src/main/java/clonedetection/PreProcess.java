package clonedetection;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PreProcess {
    void process() {
        String split1 = "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$";
        String split2 = "----------------------------------------";
        int count = 0;
        Set<String> set = new HashSet<>();
        try {
            String lines = FileUtils.readFileToString(new File("/home/cary/Documents/Data/SourceCC/TP-3.txt"), "utf-8");
            String[] ss = lines.split(split2);
            String clone1 = "";
            String clone2 = "";
            String code1 = "";
            String code2 = "";
            for (int i = 0; i < ss.length - 1; i++) {
                String s = ss[i];
                if (count == 0) {
                    s = s.substring(s.indexOf(split1));
                    clone1 = s.split("\\n")[1];
                    clone2 = s.split("\\n")[2];

                    String s1 = clone1.split("\\s")[1];
                    String e1 = clone1.split("\\s")[2];

                    String s2 = clone2.split("\\s")[1];
                    String e2 = clone2.split("\\s")[2];

                    clone1 = clone1.split("\\s")[0];
                    clone2 = clone2.split("\\s")[0];

                    clone1 = clone1.substring(clone1.lastIndexOf("/") + 1);
                    clone2 = clone2.substring(clone2.lastIndexOf("/") + 1);

                    clone1 = clone1.substring(0, clone1.indexOf(".")) + "_" + s1 + "_" + e1 + ".java";
                    clone2 = clone2.substring(0, clone2.indexOf(".")) + "_" + s2 + "_" + e2 + ".java";

                    BufferedWriter writer = new BufferedWriter(new FileWriter("/home/cary/Documents/Data/SourceCC/clone.txt", true));
                    writer.write(clone1+","+clone2);
                    writer.newLine();
                    writer.flush();
                    writer.close();

                    if(set.contains(clone1) ) {
                        System.out.println(clone1);

                    }
                    if(set.contains(clone2))
                        System.out.println(clone2);

                    set.add(clone1);
                    set.add(clone2);

                    count++;
                } else if (count == 1) {
                    code1 = s;
                    BufferedWriter writer = new BufferedWriter(new FileWriter("/home/cary/Documents/Data/SourceCC/Clone/" + clone1));
                    writer.write(code1);
                    writer.flush();
                    writer.close();
                    count++;
                } else if (count == 2) {
                    code2 = s;
                    BufferedWriter writer = new BufferedWriter(new FileWriter("/home/cary/Documents/Data/SourceCC/Clone/" + clone2));
                    writer.write(code2);
                    writer.flush();
                    writer.close();
                    count = 0;
                }
            }

//            List<String> lines = FileUtils.readLines(new File("/home/cary/Documents/Data/SourceCC/TP-1.txt"), "utf-8");
//            String clone1="";
//            String clone2="";
//            for (int i = 0; i < lines.size(); i++) {
//                if (lines.process(i).equals(split1)) {
//                    clone1 = lines.process(i + 1);
//                    clone2 = lines.process(i + 2);
//                    clone1 = clone1.substring(0, clone1.indexOf(" "));
//                    clone2 = clone2.substring(0, clone2.indexOf(" "));
//                    clone1 = clone1.substring(clone1.lastIndexOf("/") + 1);
//                    clone2 = clone2.substring(clone2.lastIndexOf("/") + 1);
//                    count = 0;
//                }
//                if (count < 2 && lines.process(i).equals(split2)) {
//                    count++;
//                    List<String> codes = new ArrayList<>();
//                    while (!lines.process(++i).equals(split2)) {
//                        codes.add(lines.process(i));
//                    }
//                    i--;
//                    String path = "";
//                    System.out.println(clone1);
//                    if(count == 0) {
//                        path = clone1;
//                    } else if(count == 1) {
//                        path = clone2;
//                    }
//                    if (!path.isEmpty()) {
////                        System.out.println(path);
//                        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/cary/Documents/Data/SourceCC/Clone/" + path));
//                        for (String code : codes) {
//                            writer.write(code);
//                            writer.newLine();
//                            writer.flush();
//                        }
//                        writer.close();
//                    }
//                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
