package com.lgmshare.component.logger;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Logger is a wrapper for logging utils
 * But more pretty, simple and powerful
 */
final class LoggerPrinter implements Printer {

    private static final int VERBOSE = 2;
    private static final int DEBUG = 3;
    private static final int INFO = 4;
    private static final int WARN = 5;
    private static final int ERROR = 6;
    private static final int ASSERT = 7;

    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 4;

    /**
     * The minimum stack trace index, starts at this class after two native calls.
     */
    private static final int MIN_STACK_OFFSET = 3;

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

    /**
     * tag is used for the Log, the name is a little different
     * in order to differentiate the logs easily with the filter
     */
    private String tag;

    /**
     * Localize single tag and method count for each thread
     */
    private final ThreadLocal<String> localTag = new ThreadLocal<>();
    private final ThreadLocal<Integer> localMethodCount = new ThreadLocal<>();

    /**
     * It is used to determine log loggerConfiguration such as method count, thread info visibility
     */
    private LoggerConfiguration loggerConfiguration;

    private BufferedWriter mOutputFile;
    private long fileSize = 0L;
    private long maxFileSize = 500000L;
    private int maxNumFiles = 5;

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger
     */
    @Override
    public LoggerConfiguration init(String tag) {
        if (tag == null) {
            throw new NullPointerException("tag may not be null");
        }
        if (tag.trim().length() == 0) {
            throw new IllegalStateException("tag may not be empty");
        }
        this.tag = tag;
        this.loggerConfiguration = new LoggerConfiguration();
        return loggerConfiguration;
    }

    public LoggerConfiguration getLoggerConfiguration() {
        return loggerConfiguration;
    }

    @Override
    public Printer t(String tag, int methodCount) {
        if (tag != null) {
            localTag.set(tag);
        }
        localMethodCount.set(methodCount);
        return this;
    }

    @Override
    public void d(String message, Object... args) {
        log(DEBUG, message, args);
    }

    @Override
    public void e(String message, Object... args) {
        e(message, null, args);
    }

    @Override
    public void e(String message, Throwable throwable, Object... args) {
        if (!loggerConfiguration.isPrint()) {
            return;
        }
        if (throwable != null && message != null) {
            message += " : " + throwable.toString();
        }
        if (throwable != null && message == null) {
            message = throwable.toString();
        }
        if (message == null) {
            message = "No message/exception is set";
        }
        log(ERROR, message, args);
    }

    @Override
    public void w(String message, Object... args) {
        log(WARN, message, args);
    }

    @Override
    public void i(String message, Object... args) {
        log(INFO, message, args);
    }

    @Override
    public void v(String message, Object... args) {
        log(VERBOSE, message, args);
    }

    @Override
    public void wtf(String message, Object... args) {
        log(ASSERT, message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    @Override
    public void json(String json) {
        if (!loggerConfiguration.isPrint()) {
            return;
        }
        if (TextUtils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(message);
            }
        } catch (JSONException e) {
            e(e.getCause().getMessage() + "\n" + json);
        }
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    @Override
    public void xml(String xml) {
        if (!loggerConfiguration.isPrint()) {
            return;
        }
        if (TextUtils.isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e(e.getCause().getMessage() + "\n" + xml);
        }
    }

    @Override
    public void clear() {
        loggerConfiguration = null;
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private synchronized void log(int logType, String msg, Object... args) {
        if (!loggerConfiguration.isPrint() || logType < loggerConfiguration.getLevel()) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            msg = "[Error Log Message is Empty]";
        }
        String tag = getTag();
        String message = createMessage(msg, args);

        if (loggerConfiguration.isFormat()) {
            int methodCount = getMethodCount();
            logTopBorder(logType, tag);
            logHeaderContent(logType, tag, methodCount);

            //get bytes of message with system's default charset (which is UTF-8 for Android)
            byte[] bytes = message.getBytes();
            int length = bytes.length;
            if (length <= CHUNK_SIZE) {
                if (methodCount > 0) {
                    logDivider(logType, tag);
                }
                logContent(logType, tag, message);
                logBottomBorder(logType, tag);
            } else {
                if (methodCount > 0) {
                    logDivider(logType, tag);
                }
                for (int i = 0; i < length; i += CHUNK_SIZE) {
                    int count = Math.min(length - i, CHUNK_SIZE);
                    //create a new String with system's default charset (which is UTF-8 for Android)
                    logContent(logType, tag, new String(bytes, i, count));
                }
                logBottomBorder(logType, tag);
            }
        } else {
            logChunk(logType, tag, message);
        }

        //////////////////save log to file/////////////
        if (!loggerConfiguration.isWriteLogFile() || TextUtils.isEmpty(loggerConfiguration.getLogFilePath())) {
            return;
        }

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + loggerConfiguration.getLogFilePath();
        String datetime = mDateFormat.format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(getLevelString(logType)).append(": ").append(datetime).append(" ");
        sb.append(tag).append(" ").append(message).append("\n");

        logToFile(sb.toString(), fileDir, loggerConfiguration.getLogFileName());
    }

    private void logTopBorder(int logType, String tag) {
        logChunk(logType, tag, TOP_BORDER);
    }

    private void logHeaderContent(int logType, String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (loggerConfiguration.isShowThreadInfo()) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().getName());
            logDivider(logType, tag);
        }
        String level = "";

        int stackOffset = getStackOffset(trace) + loggerConfiguration.getMethodOffset();

        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("║ ")
                    .append(level)
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            level += "   ";
            logChunk(logType, tag, builder.toString());
        }
    }

    private void logBottomBorder(int logType, String tag) {
        logChunk(logType, tag, BOTTOM_BORDER);
    }

    private void logDivider(int logType, String tag) {
        logChunk(logType, tag, MIDDLE_BORDER);
    }

    private void logContent(int logType, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }

    private void logChunk(int logType, String tag, String chunk) {
        String finalTag = formatTag(tag);
        switch (logType) {
            case ERROR:
                Log.e(finalTag, chunk);
                break;
            case INFO:
                Log.i(finalTag, chunk);
                break;
            case VERBOSE:
                Log.v(finalTag, chunk);
                break;
            case WARN:
                Log.w(finalTag, chunk);
                break;
            case ASSERT:
                Log.wtf(finalTag, chunk);
                break;
            case DEBUG:
                // Fall through, log debug by default
            default:
                Log.d(finalTag, chunk);
                break;
        }
    }

    /**
     * save log to file
     *
     * @param msg
     * @param fileName
     */
    private synchronized void logToFile(final String msg, final String filePath, final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logEnsureFile(filePath, fileName);
                    mOutputFile.write(msg);
                    mOutputFile.newLine();
                    mOutputFile.flush();
                    fileSize += (msg.length());
                } catch (IOException e) {
                    e(e.getMessage(), e);
                } catch (RuntimeException e) {
                    e(e.getMessage(), e);
                } finally {
                }
            }
        }).start();
    }

    private void logEnsureFile(String filePath, String fileName) throws IOException {
        if (TextUtils.isEmpty(fileName))
            fileName = "logger";

        logCreateFileDir(filePath);

        if (fileSize > maxFileSize) {
            logRotateFiles(filePath, fileName);
        }

        if ((mOutputFile == null)) {
            String str = logMakeFilePath(1, filePath, fileName);
            File logFile = new File(str);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            mOutputFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true)));
            fileSize = logFile.length();
        }
    }

    private void logRotateFiles(String filePath, String fileName) throws IOException {
        if (mOutputFile != null) {
            mOutputFile.close();
            mOutputFile = null;
        }
        new File(logMakeFilePath(maxNumFiles, filePath, fileName)).delete();
        for (int i = -1 + maxNumFiles; i >= 1; i--) {
            new File(logMakeFilePath(i, filePath, fileName)).renameTo(new File(logMakeFilePath(i + 1, filePath, fileName)));
        }
    }

    private String logMakeFilePath(int count, String filePath, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(filePath).append("/");
        sb.append(fileName + "-" + count).append(".txt");
        return sb.toString();
    }

    /**
     * 创建日志文件保存目录
     *
     * @param filePath
     */
    private void logCreateFileDir(String filePath) {
        try {
            File files = new File(filePath);
            if (!files.exists()) {
                files.mkdirs();
            }
        } catch (RuntimeException e) {
            e(e.getMessage(), e);
        }
    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private String formatTag(String tag) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }

    /**
     * @return the appropriate tag based on local or global
     */
    private String getTag() {
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
            return tag;
        }
        return this.tag;
    }

    private String createMessage(String message, Object... args) {
        return args.length == 0 ? message : String.format(message, args);
    }

    private int getMethodCount() {
        Integer count = localMethodCount.get();
        int result = loggerConfiguration.getMethodCount();
        if (count != null) {
            localMethodCount.remove();
            result = count;
        }
        if (result < 0) {
            throw new IllegalStateException("methodCount cannot be negative");
        }
        return result;
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private String getLevelString(int level) {
        switch (level) {
            case VERBOSE:
                return "VERBOSE ";
            case DEBUG:
                return "DEBUG ";
            case INFO:
                return "INFO  ";
            case WARN:
                return "WARN  ";
            case ERROR:
                return "ERROR ";
            case ASSERT:
                return "ASSERT ";
            default:
                return "BADPRI ";
        }
    }
}
