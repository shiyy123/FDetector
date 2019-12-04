package tool;

import java.io.File;
import java.util.HashSet;

/**
 * @author cary.shi on 2019/11/29
 */
public class Tool {
    public static String getFolderAndFilePath(File sourceFile) {
        String name = sourceFile.getName();
        name = name.substring(0, name.indexOf("."));
        String folderName = sourceFile.getParentFile().getName();
        return folderName + File.separator + name;
    }

    static void deleteUseless() {
        File[] files = new File("G:\\share\\CloneData\\data\\identEmbed\\2").listFiles();
        HashSet<String> set = new HashSet<>();
        for (File file : files) {
            set.add(file.getName());
        }
        File[] sources = new File("G:\\share\\CloneData\\data\\src\\2").listFiles();
        for (File source : sources) {
            String name = source.getName();
            if (!set.contains(name.substring(0, name.indexOf(".")))) {
                source.delete();
            }
        }
    }

    public static void main(String[] args) {
        deleteUseless();
    }

}
