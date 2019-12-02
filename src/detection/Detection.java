package detection;

import config.ENV;
import embedding.HOPE;
import embedding.Word2Vec;
import feature.Feature;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cary.shi on 2019/11/29
 */
public class Detection {

    public static void singleFileCloneDetection(List<File> word2vecFeatureFileList1, List<File> hopeFeatureFileList1,
                                                List<File> word2vecFeatureFileList2, List<File> hopeFeatureFileList2) {
        int len1 = word2vecFeatureFileList1.size(), len2 = word2vecFeatureFileList2.size();
        for (int i = 0; i < len1; i++) {
            File word2vec1 = word2vecFeatureFileList1.get(i);
            File hope1 = hopeFeatureFileList1.get(i);

            List<Double> word2vecVec1 = Word2Vec.getVecFromEmbeddingFile(word2vec1);
            List<Double> hopeVec1 = HOPE.getVecFromEmbeddingFile(hope1);
            List<Double> feature1 = new ArrayList<>(word2vecVec1);
            feature1.addAll(hopeVec1);

            for (int j = 0; j < len2; j++) {
                File word2vec2 = word2vecFeatureFileList2.get(j);
                File hope2 = hopeFeatureFileList2.get(j);

                List<Double> word2vecVec2 = Word2Vec.getVecFromEmbeddingFile(word2vec2);
                List<Double> hopeVec2 = HOPE.getVecFromEmbeddingFile(hope2);

                List<Double> feature2 = new ArrayList<>(word2vecVec2);
                feature2.addAll(hopeVec2);

                if (isClone(feature1, feature2)) {
                    System.out.println("is clone");
                } else {
                    System.out.println("not clone");
                }

            }
        }
    }

    private static boolean isClone(List<Double> feature1, List<Double> feature2) {
        File tmpFeatureFile = new File(ENV.TMP_PATH + File.separator + "feature.txt");
        if (tmpFeatureFile.exists()) {
            tmpFeatureFile.delete();
        }

        StringBuilder sb = new StringBuilder();
        for (Double aDouble : feature1) {
            sb.append(String.format("%.6f", aDouble));
            sb.append(" ");
        }
        for (int i = 0; i < feature2.size() - 1; i++) {
            sb.append(String.format("%.6f", feature2.get(i)));
            sb.append(" ");
        }
        sb.append(String.format("%.6f", feature2.get(feature2.size() - 1)));
        System.out.println(feature1.size() + feature2.size());
        System.out.println(sb.toString());
        try {
            FileUtils.write(tmpFeatureFile, sb.toString(), StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Process process = Runtime.getRuntime().exec("python3 ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (feature1.size() == feature2.size()) {
            return true;
        }
        return false;
    }
}
