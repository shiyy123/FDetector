package representation;

import config.ENV;
import tool.Tool;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author cary.shi on 2019/11/28
 */
public class AST {
    public static List<File> sourceFile2ASTFileList(File sourceFile) {
        String folderAndFilePath = Tool.getFolderAndFilePath(sourceFile);

        File[] files = new File(ENV.AST_PATH + File.separator + folderAndFilePath).listFiles();
        List<File> fileList = new ArrayList<>();
        assert files != null;
        Collections.addAll(fileList, files);
        return fileList;
    }
}
