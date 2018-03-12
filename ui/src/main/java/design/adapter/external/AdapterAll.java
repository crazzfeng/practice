package design.adapter.external;

import design.adapter.dolog.DBRecordLog;
import design.adapter.dolog.ReadAndWriteLog;

public class AdapterAll implements DBRecordLog, ReadAndWriteLog
{

    public ReadAndWriteLog readAndWriteLog;

    public DBRecordLog dbRecordLog;

    public AdapterAll(ReadAndWriteLog readAndWriteLog, DBRecordLog dbRecordLog)
    {
        this.readAndWriteLog = readAndWriteLog;
        this.dbRecordLog = dbRecordLog;
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
        System.err.print("适配器文件记录日志：" + "\n");
    }

    /**
     * 更新日志
     *
     * @param log
     */
    public void update(String log)
    {
        System.err.print("适配器文件记录日志：" + log + "\n");
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

    /**
     * 记录日志
     *
     * @param log
     */
    public void writeLog(String log)
    {
        dbRecordLog.insertLog(log);
    }

    /**
     * 读取日志
     *
     * @return
     */
    public String readLog()
    {
        return dbRecordLog.getLog();
    }
}
