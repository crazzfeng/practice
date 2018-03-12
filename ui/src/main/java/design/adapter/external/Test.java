package design.adapter.external;

import design.adapter.dolog.DBRecordLog;
import design.adapter.dolog.DBRecordLogImpl;
import design.adapter.dolog.ReadAndWriteLog;
import design.adapter.dolog.ReadAndWriteLogImpl;

public class Test
{
    public static void main(String[] args)
    {
        ReadAndWriteLog readAndWriteLog = new ReadAndWriteLogImpl();

        DBRecordLog dbRecordLog = new DBRecordLogImpl();

        DBRecordLog test1 = new AdapterAll(readAndWriteLog,dbRecordLog);

        ReadAndWriteLog test2 = new AdapterAll(readAndWriteLog,dbRecordLog);

        test1.insertLog("rizhiasdf");

        test2.writeLog("asd");


        test1.getLog();

        test2.readLog();

    }
}
