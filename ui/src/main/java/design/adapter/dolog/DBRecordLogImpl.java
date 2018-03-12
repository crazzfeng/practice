package design.adapter.dolog;

public class DBRecordLogImpl implements DBRecordLog
{
    /**
     * 添加日志
     *
     * @param log
     */
    public void insertLog(String log)
    {
        System.err.print("数据库记录日志：" + log + "\n");
    }

    /**
     * 删除日志
     */
    public void deleteLog()
    {
        System.err.print("删除数据库日志" + "\n");
    }

    /**
     * 更新日志
     *
     * @param log
     */
    public void update(String log)
    {
        System.err.print("更新数据库记录日志" + log + "\n");
    }

    /**
     * 查询日志
     *
     * @return
     */
    public String getLog()
    {
        System.err.print("查询数据库记录日志" + "\n");
        return "查询数据库日志";
    }
}
