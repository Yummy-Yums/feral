package feral.examples.util

import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord

class TestLogHandler extends Handler { 

    setLevel(Level.ALL)
    private val log_store: collection.mutable.ListBuffer[LogRecord] = scala.collection.mutable.ListBuffer.empty

    def getLog = log_store.result()
    def clear(): Unit = log_store.clear()
    def close(): Unit = () 
    def flush(): Unit = ()
    def publish(record: LogRecord): Unit = log_store += record
}

object TestLogHandler {
  def apply(): TestLogHandler = new TestLogHandler()
}
 