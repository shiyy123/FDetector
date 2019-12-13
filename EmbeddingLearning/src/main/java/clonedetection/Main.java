package clonedetection;

import java.io.File;

public class Main {
    public static void processCode() {
        CodeRepresentation codeRepresentation = new CodeRepresentation();
        codeRepresentation.processCode();
    }

    /**
     * 80/1376
     *
     * @param args
     */
    public static void main(String[] args) {
//        Feature feature = new Feature();
//        feature.generateGraphFromCallFile(new File("/home/cary/Documents/Data/CloneData/call/1/13/call.txt"));

        Tool tool = new Tool();
//        tool.testCFGDistance("3", "13");
//        tool.testNode2VecDistance("3", "13");
//        tool.testIdentDistance("3", "13");
//        tool.printDistance("/home/cary/Documents/Data/CloneData/identDistance/3/18/distance.txt");
        tool.generateFeatureEdge();
    }
}
