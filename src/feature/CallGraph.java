package feature;

import config.ENV;
import tool.Tool;

import java.io.File;

/**
 * @author cary.shi on 2019/11/28
 */
public class CallGraph {
    File sourceCodeFile2CallGraphFile(File sourceCodeFile) {
        String folderAndFilePath = Tool.getFolderAndFilePath(sourceCodeFile);
        return new File(ENV.CALL_PATH + File.separator + folderAndFilePath + File.separator + "call.txt");
    }

}
