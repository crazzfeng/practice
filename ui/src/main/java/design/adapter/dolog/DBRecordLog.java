package design.adapter.dolog;

public interface DBRecordLog
{
    /**
     * 添加日志
     * @param log
     */
    void insertLog(String log);

    /**
     * 删除日志
     */
    void deleteLog();

    /**
     * 更新日志
     * @param log
     */
    void update(String log);

    /**
     * 查询日志
     * @return
     */
    String getLog();

}
