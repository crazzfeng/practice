package design.adapter.dolog;

public class ReadAndWriteLogImpl implements ReadAndWriteLog
{
    /**
     * 记录日志
     *
     * @param log
     */
    public void writeLog(String log)
    {
        System.err.print("文件记录日志：" + log + "\n");
    }


    /**
     * 读取日志
     * @return
     */
    public String readLog()
    {
        System.err.print("读取文件记录日志" + "\n");
        return "读取文件日志";
    }
}
