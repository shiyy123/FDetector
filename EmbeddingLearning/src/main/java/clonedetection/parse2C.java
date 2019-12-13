package clonedetection;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class parse2C {
    public static void main(String[] args) throws IOException {
        String path = "/home/cary/Documents/Data/CloneData/CloneData";
        File[] files = new File(path).listFiles();
        int cnt1 = 0, cnt2 = 0;
        for (File file : files) {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                String name = subFile.getAbsolutePath();
                name = name.substring(0, name.lastIndexOf("."));
                String content = FileUtils.readFileToString(subFile, "utf-8");
                if(content.contains("printf") || content.contains("scanf") ||
                        content.contains("gets") || content.contains("puts") ||
                        content.contains("getchar") || content.contains("putchar") ) {
                    subFile.renameTo(new File(name + ".c"));
                    cnt1++;
                }
                else if(content.contains("cout")) {
                    subFile.renameTo(new File(name + ".cpp"));
                    cnt2++;
                } else {
                    System.out.println(subFile.getAbsolutePath());
                }
            }
        }
        System.out.println(cnt1);
        System.out.println(cnt2);

        System.out.println(cnt1 + cnt2);
        System.out.println(files.length);
//        Set<String> set = new HashSet<>();
//        for (int i = 0; i < files.length; i++) {
//            File[] subFiles = files[i].listFiles();
//            for (int j = 0; j < subFiles.length; j++) {
//                String p = subFiles[j].getAbsolutePath();
//                String name = subFiles[j].getName();
//                String parent = subFiles[j].getParentFile().getName();
//                String newName = parent + "_" + name;
//
//                String a = "/media/cary/DATA/NJU/data/sourcecode/";
//                subFiles[j].renameTo(new File(a + newName));
//
//                set.add(newName);
//            }
//        }
//        System.out.println(set.size());
    }
}
