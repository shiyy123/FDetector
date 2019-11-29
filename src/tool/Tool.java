package tool;

import java.io.File;

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

}
