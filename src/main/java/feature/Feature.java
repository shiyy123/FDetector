package feature;

import config.ENV;
import org.apache.commons.io.FileUtils;
import tool.Tool;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author cary.shi on 2019/11/29
 */
public class Feature {
    private String folderAndFilePath;
    private List<Long> funcIdList;

    Feature(String folderAndFilePath, List<Long> funcIdList) {
        this.folderAndFilePath = folderAndFilePath;
        this.funcIdList = funcIdList;
    }

    public static List<Feature> getFeatureListBySourceFile(File sourceFile) {
        List<Feature> featureList = new ArrayList<>();

        String folderAndFilePath = Tool.getFolderAndFilePath(sourceFile);
        File featureFile = new File(ENV.FEATURE_PATH + File.separator + folderAndFilePath + File.separator + "feature.txt");
        List<String> lines = null;
        try {
            lines = FileUtils.readLines(featureFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert lines != null;
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            line = line.substring(1, line.length() - 1);
            String[] cols = line.split(",");
            List<Long> funcIds = new ArrayList<>();
            for (String col : cols) {
                funcIds.add(Long.parseLong(col.trim()));
            }
            Feature feature = new Feature(folderAndFilePath, funcIds);
            featureList.add(feature);
        }
        return featureList;
    }

    List<File> getFeatureEdgeFileListBySourceFile(File sourceFile) {
        List<File> resList = new ArrayList<>();

        String folderAndFilePath = Tool.getFolderAndFilePath(sourceFile);

        File[] files = new File(ENV.FEATURE_EDGE_PATH + folderAndFilePath).listFiles();

        assert files != null;
        Collections.addAll(resList, files);

        return resList;
    }


    public List<Long> getFuncIdList() {
        return funcIdList;
    }

    public void setFuncIdList(List<Long> funcIdList) {
        this.funcIdList = funcIdList;
    }
}
