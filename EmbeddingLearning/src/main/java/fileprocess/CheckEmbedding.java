package fileprocess;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Cary on 19-4-12
 *
 * @email: yangyangshi@smail.nju.edu.cn
 * @time: 上午10:25
 */
public class CheckEmbedding {

    final static String base = "/home/cary/Documents/Data/CloneData/";

    /**
     * 因为文件中含有多个func，可能存在某个func的Embedding失败的情况
     * 这时需要删除存在问题的文件，因为Embedding失败了难以修复
     * src是完整的，Embedding可能缺少
     *
     * @param srcFolderPath
     * @param embeddingFolderPath
     */
    void checkSrcAndEmbeddingAndDelete(String srcFolderPath, String embeddingFolderPath) {
        File[] srcFolders = new File(srcFolderPath).listFiles();

        assert srcFolders != null;
        for (File srcFolder : srcFolders) {
            String folderName = srcFolder.getName();
            File embeddingFolder = new File(embeddingFolderPath.concat(folderName));
            // 当不存在整个Embedding文件夹时，跳过
            if (!embeddingFolder.exists()) {
                System.out.println("no folder:" + folderName);
                continue;
            }
            File[] srcFiles = srcFolder.listFiles();
            assert srcFiles != null;
            for (File srcFile : srcFiles) {
                String srcName = srcFile.getName();
                File embeddingSrcFile = new File(embeddingFolder.getAbsolutePath().concat(File.separator).concat(srcName));
                if (!embeddingSrcFile.exists()) {
                    System.out.println("no src:" + srcName);
                    continue;
                }
                File[] funcFile = srcFile.listFiles();
                assert funcFile != null;
                for (File file : funcFile) {
                    String funcName = file.getName().substring(0, file.getName().indexOf(".") + 1).concat("embedding");
                    File embeddingFuncFile = new File(embeddingSrcFile.getAbsolutePath().concat(File.separator).concat(funcName));
                    if (!embeddingFuncFile.exists()) {
                        try {
                            FileUtils.deleteDirectory(new File(embeddingFolderPath + folderName + File.separator + srcName));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("no func:" + folderName + "/" + srcName + "/" + funcName);
                    }
                }
            }
        }
    }

    void checkCfgEmbedAndIdentEmbed(String cfgEmbedFolderPath, String identEmbedFolderPath) {
        File[] cfgEmbedFolders = new File(cfgEmbedFolderPath).listFiles();
        File[] identEmbedFolders = new File(identEmbedFolderPath).listFiles();
        assert cfgEmbedFolders != null;
        assert identEmbedFolders != null;

        int length = identEmbedFolders.length;
        for (int i = 0; i < length; i++) {
            boolean find = false;
            String identName = identEmbedFolders[i].getName();

            for (int j = 0; j < cfgEmbedFolders.length; j++) {
                if (cfgEmbedFolders[j].getName().equals(identName)) {
                    find = true;
                }
            }

            if (!find) {
                try {
                    FileUtils.deleteDirectory(identEmbedFolders[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0; i < identEmbedFolders.length; i++) {
            String identName = identEmbedFolders[i].getName();

            File[] identFolders = identEmbedFolders[i].listFiles();
            File[] cfgFolders = new File(cfgEmbedFolderPath.concat(identName)).listFiles();
            assert identFolders != null;
            int len = identFolders.length;
            for (int j = 0; j < len; j++) {
                String name = identFolders[j].getName();
                File cfgFile = new File(cfgEmbedFolderPath.concat(identName).concat(File.separator).concat(name));
                if (!cfgFile.exists()) {
                    try {
                        FileUtils.deleteDirectory(identFolders[j]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("no:" + cfgFile.getAbsolutePath());
                }
            }
        }
    }

    public static void main(String[] args) {
        CheckEmbedding checkEmbedding = new CheckEmbedding();
//        checkEmbedding.checkSrcAndEmbeddingAndDelete(base + "func_edge/", base + "embedding_func_HOPE/");
        checkEmbedding.checkCfgEmbedAndIdentEmbed(base + "identEmbed/", base + "cfgEmbed/");
    }
}
