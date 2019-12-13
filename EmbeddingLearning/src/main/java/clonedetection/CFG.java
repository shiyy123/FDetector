package clonedetection;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Cary on 19-2-15
 *
 * @email: yangyangshi@smail.nju.edu.cn
 * @time: 上午10:54
 */
public class CFG {
    Tool tool = new Tool();

    //判断当前节点是否存在自环
    boolean hasCircle(String node, List<Edge> edges) {
        boolean res = false;
        for (Edge edge : edges) {
            if (edge.start.equals(node) && edge.end.equals(node)) {
                res = true;
            }
        }
        return res;
    }

    List<Edge> simplifyCFGEdge(File edgeFile) {
        System.out.println(edgeFile);
        List<Edge> res = new ArrayList<>();
        try {
            List<String> edgeStringList = FileUtils.readLines(edgeFile, "utf-8");
            List<Edge> edges = new ArrayList<>();
            edgeStringList.forEach(s -> {
                Edge edge = new Edge(s.split(" ")[0], s.split(" ")[1]);
                edges.add(edge);
            });

            List<Edge> canDelete = new ArrayList<>();

            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                //该边属于自环，跳过
                if (edge.start.equals(edge.end)) {
                    continue;
                }
                //该边两端的节点存在自环，跳过
                if (hasCircle(edge.start, edges) || hasCircle(edge.end, edges)) {
                    continue;
                }

                List<String> toStartNodeList = tool.getCallStatementIdList(edges, edge.start);
                List<String> startToNodeList = tool.getBeCalledStatementIdList(edges, edge.start);

                List<String> toEndNodeList = tool.getCallStatementIdList(edges, edge.end);
                List<String> endNodeToList = tool.getBeCalledStatementIdList(edges, edge.end);
                if (toStartNodeList.size() <= 1 && startToNodeList.size() <= 1 &&
                        toEndNodeList.size() <= 1 && endNodeToList.size() <= 1) {
                    canDelete.add(edge);
                }
            }

            canDelete.forEach(System.out::println);

            for (Edge edge : edges) {
                for (Edge canDelEdge : canDelete) {
                    if (edge.start.equals(canDelEdge.start) && edge.end.equals(canDelEdge.end)) {
                        //找到了需要删除的边

                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    void simplifyFeatureEdgeAndFuncEdge() {
        String featureEdgePath = Config.basePath.concat("feature_edge");
        String funcEdgePath = Config.basePath.concat("func_edge");

        String simpleEdgePath = Config.basePath.concat("simplifyEdge");

        List<List<File>> funcEdgeFileList = tool.getFile(funcEdgePath);
        for (List<File> x : funcEdgeFileList) {
            for (File y : x) {
                String dir = tool.getLastTwo(y.getAbsolutePath());
                File edge;
                if (new File(featureEdgePath.concat(File.separator).concat(dir)).exists()) { //去feature里面找
                    edge = new File(featureEdgePath.concat(File.separator).concat(dir).concat(File.separator).concat("0.edgelist"));
                } else {//在func里找即可
                    edge = Objects.requireNonNull(y.listFiles())[0];
                }
                simplifyCFGEdge(edge);
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        CFG cfg = new CFG();
        cfg.simplifyCFGEdge(new File("/home/cary/Documents/Data/CloneData/func_edge/3/4/3320433.edgelist"));
    }
}
