package design.adapter.external;

import design.adapter.dolog.DBRecordLog;
import design.adapter.dolog.ReadAndWriteLog;

/**
 * 单向适配
 */
public class Adapter implements DBRecordLog
{
    public ReadAndWriteLog readAndWriteLog;

    public Adapter(ReadAndWriteLog readAndWriteLog)
    {
        this.readAndWriteLog = readAndWriteLog;
    }

    /**
     * 添加日志
     *
     * @param log
     */
    public void insertLog(String log)
    {
        readAndWriteLog.writeLog(log);
    }

    /**
     * 删除日志
     */
    public void deleteLog()
    {
        System.err.print("适配器删除" + "\n");
    }

    /**
     * 更新日志
     *
     * @param log
     */
    public void update(String log)
    {
        System.err.print("适配器更新" + "\n");
    }

    /**
     * 查询日志
     *
     * @return
     */
    public String getLog()
    {
        return readAndWriteLog.readLog();
    }
}
