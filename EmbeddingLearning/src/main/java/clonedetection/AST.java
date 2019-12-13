package clonedetection;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Cary on 19-1-17
 * Email: yangyangshi@smail.nju.edu.cn
 */

public class AST {
    /**
     * format:
     * childNum:0
     * code:main ()
     * type:FunctionDef
     * functionId:4098865
     *
     * @param s
     * @return
     */
    JSONObject getASTNode(String s) {
        JSONObject jsonObject = new JSONObject();
        String[] tmps = s.split("\n");
        for (int i = 0; i < tmps.length; i++) {
            try {
                if (tmps[i].startsWith("childNum:")) {
                    jsonObject.put("childNum", tmps[i].substring(tmps[i].indexOf(":") + 1));
                } else if (tmps[i].startsWith("code:")) {
                    jsonObject.put("code", tmps[i].substring(tmps[i].indexOf(":") + 1));
                } else if (tmps[i].startsWith("functionId:")) {
                    jsonObject.put("functionId", tmps[i].substring(tmps[i].indexOf(":") + 1));
                } else if (tmps[i].startsWith("type:")) {
                    jsonObject.put("type", tmps[i].substring(tmps[i].indexOf(":") + 1));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    void traverse(TreeNode root) {
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {
            TreeNode cur = nodeQueue.poll();
//            if (isLeaf(cur)) {
//                JSONObject object = getASTNode(cur.node.getAttribute("label").toString());
//                try {
//                    System.out.println(object.getString("code"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
            for (TreeNode node : cur.children) {
                nodeQueue.offer(node);
            }
        }
    }

    void midTraverse(TreeNode root, File output) {
        if (root.node != null) {
            root.children.forEach(x -> {
                if (isLeaf(x)) {
                    JSONObject jsonObject = getASTNode(x.node.getAttribute("label").toString());
                    try {
                        FileUtils.write(output, jsonObject.getString("code") + " ","utf-8", true);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            root.children.forEach(x -> midTraverse(x, output));
        }
    }

    boolean isLeaf(TreeNode node) {
        return node.children.isEmpty();
    }

    //生成AST，构造节点之间的关系
    TreeNode generateASTTree(File astFile) {
        GraphNode graphNode = getRoot(astFile);
        TreeNode root = new TreeNode();
        root.node = graphNode;
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {
            TreeNode cur = nodeQueue.poll();
            List<GraphNode> childrenList = getChildren(cur.node, astFile);

            List<TreeNode> treeNodeList = new ArrayList<>();
            childrenList.forEach(children -> {
                TreeNode node = new TreeNode();
                node.node = children;
                treeNodeList.add(node);
            });
            cur.children = treeNodeList;
            treeNodeList.forEach(nodeQueue::offer);
        }
        return root;
    }

    //从ast文件中获取根节点
    GraphNode getRoot(File astFile) {
        GraphNode rootNode = new GraphNode("-1");
        try {
            GraphParser parser = new GraphParser(new FileInputStream(astFile));
            Map<String, GraphNode> nodes = parser.getNodes();

            for (GraphNode node : nodes.values()) {
                String label = node.getAttribute("label").toString();
                JSONObject item = getASTNode(label);

                if (item.toString().contains("FunctionDef") && item.getString("type").equals("FunctionDef")) {
                    rootNode = node;
                }
            }
        } catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return rootNode;
    }

    //获取所有node的孩子节点
    List<GraphNode> getChildren(GraphNode node, File astFile) {
        List<GraphNode> children = new ArrayList<>();
        try {
            GraphParser parser = new GraphParser(new FileInputStream(astFile));
            Map<String, GraphEdge> edges = parser.getEdges();

            for (GraphEdge edge : edges.values()) {
                if (edge.getNode1().getId().equals(node.getId())) {
                    children.add(edge.getNode2());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return children;
    }

    public static void main(String[] args) {
        AST ast = new AST();
        TreeNode root = ast.generateASTTree(new File("/home/cary/Documents/Data/CloneData/ast/80/1376/10398065.dot"));
        System.out.println("generate success");
        ast.midTraverse(root, new File("/home/cary/Documents/Data/CloneData/ident/80/1376/10398065.dot"));
    }
}
