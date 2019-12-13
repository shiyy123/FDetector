package clonedetection;

import java.io.File;

/**
 * Created by Cary on 19-5-1
 *
 * @email: yangyangshi@smail.nju.edu.cn
 * @time: 下午5:42
 */
public class Experiment {
    static String base = "/home/cary/Documents/Data/CloneData/";

    static void chooseSuitableSizeofCode() {
        GenerateData generateData = new GenerateData();
        generateData.deleteFolders(200, base.concat("emdtest").concat(File.separator).concat("ident_word2vec"));
    }

    static void generateCSV() {
        GenerateData generateData = new GenerateData();
        generateData.generateWord2VecDataCSV(true);
    }

    static void balanceCSV() {
        GenerateData generateData = new GenerateData();
        generateData.balanceDataLabel(base.concat("csv").concat(File.separator).
                concat("3,12,7,6,5,9,10,13,2,1_train_ident_word2vec.csv"));
        generateData.balanceDataLabel(base.concat("csv").concat(File.separator).
                concat("17,19,14,15,16_test_ident_word2vec.csv"));
    }



    public static void main(String[] args) {
//        chooseSuitableSizeofCode();
//        generateCSV();
        balanceCSV();
    }
}
