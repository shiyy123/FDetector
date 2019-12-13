package clonedetection;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Merge {
    public static void main(String[] args) throws IOException {
        String p = "/home/cary/Documents/Data/CloneData/embedding";
        File[] fs = new File(p).listFiles();
        for (File f : fs) {
            File[] embeddings = f.listFiles();
            String ep = "/home/cary/Documents/Data/CloneData/merge/" + f.getName() + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(ep));
            for (File embedding : embeddings) {
                String content = FileUtils.readFileToString(embedding, "utf-8");
                content = content.replace("  ", ",");
                content = content.replace(' ', ',');
                content = content.replace("[,", "[");
                content = content.replace(",,", ",");
                content = content.replace("[", "");
                content = content.replace("]", "");

                writer.write(embedding.getName().substring(0, embedding.getName().indexOf(".")) + ",");
                writer.write(content);
                writer.newLine();
                writer.flush();
            }
            writer.close();
        }
    }
}
