package clonedetection;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Cary on 19-1-21
 * Email: yangyangshi@smail.nju.edu.cn
 */
public class Feature {

    Tool tool = new Tool();

    //根据函数调用关系构造函数调用图
    GraphNode generateGraphFromCallFile(File callFile) {
        JSONArray jsonArray = tool.call2JSON(callFile);

        try{
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject callJsonObject = jsonArray.getJSONObject(i);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GraphNode graphNode = new GraphNode("1");
        graphNode.setAttribute("1", "2");
        System.out.println(graphNode.getId());
        System.out.println(graphNode.getAttributes());
        GraphEdge graphEdge = new GraphEdge("1", graphNode, graphNode);
        return graphNode;
    }

    class MyGraph{
        int verticeNum;
        int edgeNum;

    }
}
