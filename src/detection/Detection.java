package detection;

import embedding.HOPE;
import embedding.Word2Vec;
import feature.Feature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cary.shi on 2019/11/29
 */
public class Detection {

    public static void cloneDetection(List<File> word2vecFeatureFileList1, List<File> hopeFeatureFileList1,
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

            }
        }
    }

    private static boolean isClone(List<Double> feature1, List<Double> feature2) {
        return true;
    }
}
