package design.adapter.dolog;

public interface ReadAndWriteLog
{
    /**
     * 记录日志
     *
     * @param log
     */
    void writeLog(String log);

    /**
     * 读取日志
     * @return
     */
    String readLog();
}
