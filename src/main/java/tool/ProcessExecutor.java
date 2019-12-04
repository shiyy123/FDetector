package tool;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cary.shi on 2019/10/29
 */
public class ProcessExecutor {
    private Process process;
    private List<String> outputList;
    private List<String> errorList;

    public ProcessExecutor(Process process) {
        this.process = process;
    }

    public List<String> getOutputList() {
        return this.outputList;
    }

    public List<String> getErrorList() {
        return this.errorList;
    }

    public int execute() {
        int res = 0;
        ProcessOutputThread outputThread = new ProcessOutputThread(this.process.getInputStream());
        ProcessOutputThread errorThread = new ProcessOutputThread(this.process.getErrorStream());
        outputThread.start();
        errorThread.start();
        try {
            res = this.process.waitFor();
            outputThread.join();
            errorThread.join();
            this.outputList = outputThread.getOutputList();
            this.errorList = errorThread.getOutputList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

}

class ProcessOutputThread extends Thread {
    private InputStream is;
    private List<String> outputList;

    ProcessOutputThread(InputStream is) {
        this.is = is;
        this.outputList = new ArrayList<>();
    }

    List<String> getOutputList() {
        return this.outputList;
    }

    @Override
    public void run() {
        InputStreamReader isr = null;
        BufferedReader br = null;
        isr = new InputStreamReader(this.is);
        br = new BufferedReader(isr);
        String output = null;
        try {
            while (null != (output = br.readLine())) {
//                DebugUtils.debug(output);
                this.outputList.add(output);
            }
        } catch (IOException e) {
//            System.err.println("Process run error");
        } finally {
            try {
                br.close();
                isr.close();
                if (null != this.is) {
                    this.is.close();
                }
            } catch (IOException e) {
//                System.err.println("Process close stream error");
            }
        }
    }
}
