package config;

import java.io.File;

/**
 * @author cary.shi on 2019/11/28
 */
public class ENV {
    public static String base = "G:\\share\\CloneDetection\\data";

    public static String WORK_SPACE = "/workspace/";

    public static String SRC_PATH = base + File.separator + "src";
    public static String CALL_PATH = base + File.separator + "call";
    public static String CFG_PATH = base + File.separator + "cfg";
    public static String AST_PATH = base + File.separator + "ast";
    public static String FUNC_PATH = base + File.separator + "func";
    public static String SINGLE_FUNC_PATH = base + File.separator + "func.txt";
    public static String FEATURE_PATH = base + File.separator + "feature";
    public static String FUNC_EDGE_PATH = base + File.separator + "func_edge";
    public static String FEATURE_EDGE_PATH = base + File.separator + "feature_edge";
    public static String WORD2VEC_PATH = base + File.separator + "word2vec";
    public static String EMBEDDING_FUNC_WORD2VEC_PATH = base + File.separator + "embedding_func_word2vec";
    public static String EMBEDDING_FEATURE_WORD2VEC_PATH = base + File.separator + "embedding_feature_word2vec";
    public static String EMBEDDING_FUNC_HOPE_PATH = base + File.separator + "embedding_func_HOPE";
    public static String EMBEDDING_FEATURE_HOPE_PATH = base + File.separator + "embedding_feature_HOPE";
}
