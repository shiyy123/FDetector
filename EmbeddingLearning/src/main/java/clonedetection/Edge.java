package clonedetection;

/**
 * Created by Cary on 19-2-15
 *
 * @email: yangyangshi@smail.nju.edu.cn
 * @time: 上午11:22
 */
public class Edge {
    Edge(String start, String end) {
        this.start = start;
        this.end = end;
    }

    String start;
    String end;

    @Override
    public String toString() {
        return start + " " + end;
    }
}
