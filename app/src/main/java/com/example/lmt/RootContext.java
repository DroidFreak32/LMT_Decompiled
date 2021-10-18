package com.example.lmt;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class RootContext {
    private static boolean DEBUG = true;
    private static final String PREFIX = "";
    private static final String TAG = "LMT::RootContext";
    private static RootContext instance = null;
    private static int returnBufferSize = 512;
    private static byte[] returnBuffer = new byte[returnBufferSize];
    private String mAbsolutePath;
    private Context mContext;
    private boolean mInitialized = false;
    private DataInputStream mRemoteInputStream;
    private DataOutputStream mRemoteOutputStream;
    private Process mRemoteProcess;
    private DataInputStream mRootInputStream;
    private DataOutputStream mRootOutputStream;
    private Process mRootProcess;
    private String mShell;

    private RootContext(Context context) {
        try {
            this.mContext = context;
            this.mShell = "su";
            this.mAbsolutePath = this.mContext.getFilesDir().getAbsolutePath();
            initRemote();
            initRoot();
            this.mInitialized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!this.mInitialized) {
            Toast.makeText(context, "Failed to get root permissions!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Failed to get root permissions!");
        }
    }

    public static RootContext getInstance(Context context) {
        if (instance == null) {
            if (DEBUG) {
                Log.d(TAG, "getInstance()");
            }
            instance = new RootContext(context);
        }
        return instance;
    }

    boolean isRootAvailable(boolean trace) {
        if (trace && !this.mInitialized) {
            Toast.makeText(this.mContext, "This feature needs root permissions!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "This feature needs root permissions!");
        }
        return this.mInitialized;
    }

    void setDebug(int value) {
        boolean z = true;
        if (value != 1) {
            z = false;
        }
        DEBUG = z;
    }

    void setInitialized(boolean enabled) {
        this.mInitialized = enabled;
    }

    private void initRemote() throws Exception {
        if (DEBUG) {
            Log.d(TAG, "initRemote()");
        }
        initRemoteStreams();
        if (Build.VERSION.SDK_INT >= 23) {
            initRemoteProcess23();
        } else if (Build.VERSION.SDK_INT >= 20) {
            initRemoteProcess21();
        } else {
            initRemoteProcess20();
        }
    }

    private void copyRemote() throws Exception {
        String inputContext;
        if (DEBUG) {
            Log.d(TAG, "copyRemote()");
        }
        if (Build.VERSION.SDK_INT >= 29) {
            inputContext = "InputContext29.jar";
        } else if (Build.VERSION.SDK_INT >= 28) {
            inputContext = "InputContext28.jar";
        } else if (Build.VERSION.SDK_INT >= 24) {
            inputContext = "InputContext24.jar";
        } else if (Build.VERSION.SDK_INT >= 20) {
            inputContext = "InputContext21.jar";
        } else {
            inputContext = "InputContext20.jar";
        }
        InputStream inputStream = this.mContext.getAssets().open(inputContext);
        FileOutputStream outputStream = new FileOutputStream(new File(this.mAbsolutePath + "/InputContext.jar"));
        byte[] b = new byte[4096];
        while (true) {
            int read = inputStream.read(b);
            if (read != -1) {
                outputStream.write(b, 0, read);
            } else {
                outputStream.close();
                inputStream.close();
                return;
            }
        }
    }

    private void initRemoteStreams() throws Exception {
        if (DEBUG) {
            Log.d(TAG, "initRemoteStreams()");
        }
        try {
            if (this.mRemoteOutputStream != null) {
                this.mRemoteOutputStream.close();
            }
            if (this.mRemoteInputStream != null) {
                this.mRemoteInputStream.close();
            }
            if (this.mRemoteProcess != null) {
                this.mRemoteProcess.destroy();
            }
        } catch (Exception e) {
            this.mRemoteOutputStream = null;
            this.mRemoteInputStream = null;
            this.mRemoteProcess = null;
        }
        copyRemote();
        this.mRemoteProcess = new ProcessBuilder(this.mShell).redirectErrorStream(true).start();
        this.mRemoteOutputStream = new DataOutputStream(this.mRemoteProcess.getOutputStream());
        this.mRemoteInputStream = new DataInputStream(this.mRemoteProcess.getInputStream());
    }

    private void initRemoteProcess20() throws Exception {
        if (DEBUG) {
            Log.d(TAG, "initRemoteProcess20()");
        }
        if (Build.VERSION.SDK_INT >= 19) {
            this.mRemoteOutputStream.write("sh\n".getBytes("ASCII"));
        }
        DataOutputStream dataOutputStream = this.mRemoteOutputStream;
        dataOutputStream.write(("export CLASSPATH=" + this.mAbsolutePath + "/InputContext.jar\n").getBytes("ASCII"));
        DataOutputStream dataOutputStream2 = this.mRemoteOutputStream;
        dataOutputStream2.write(("exec app_process " + this.mAbsolutePath + " com.noname81.lmt.InputContext \"$@\"\n").getBytes("ASCII"));
    }

    private void initRemoteProcess21() throws Exception {
        if (DEBUG) {
            Log.d(TAG, "initRemoteProcess21()");
        }
        DataOutputStream dataOutputStream = this.mRemoteOutputStream;
        dataOutputStream.write(("export CLASSPATH=" + this.mAbsolutePath + "/InputContext.jar\n").getBytes("ASCII"));
        DataOutputStream dataOutputStream2 = this.mRemoteOutputStream;
        dataOutputStream2.write(("exec app_process " + this.mAbsolutePath + " com.noname81.lmt.InputContext\n").getBytes("ASCII"));
        this.mRemoteOutputStream.flush();
    }

    private void initRemoteProcess23() throws Exception {
        if (DEBUG) {
            Log.d(TAG, "initRemoteProcess23()");
        }
        this.mRemoteOutputStream.write("supolicy --live 'allow qti_init_shell zygote_exec file execute'\n".getBytes("ASCII"));
        DataOutputStream dataOutputStream = this.mRemoteOutputStream;
        dataOutputStream.write(("export CLASSPATH=" + this.mAbsolutePath + "/InputContext.jar\n").getBytes("ASCII"));
        DataOutputStream dataOutputStream2 = this.mRemoteOutputStream;
        dataOutputStream2.write(("exec app_process " + this.mAbsolutePath + " com.noname81.lmt.InputContext\n").getBytes("ASCII"));
        this.mRemoteOutputStream.flush();
    }

    private void initRoot() throws Exception {
        if (DEBUG) {
            Log.d(TAG, "initRoot()");
        }
        DataOutputStream dataOutputStream = this.mRootOutputStream;
        if (dataOutputStream != null) {
            dataOutputStream.close();
        }
        DataInputStream dataInputStream = this.mRootInputStream;
        if (dataInputStream != null) {
            dataInputStream.close();
        }
        Process process = this.mRootProcess;
        if (process != null) {
            process.destroy();
        }
        this.mRootProcess = new ProcessBuilder(this.mShell).redirectErrorStream(true).start();
        this.mRootOutputStream = new DataOutputStream(this.mRootProcess.getOutputStream());
        this.mRootInputStream = new DataInputStream(this.mRootProcess.getInputStream());
    }

    boolean runCommandRemote(String cmd, boolean trace) {
        if (DEBUG) {
            Log.d(TAG, "runCommandRemote(" + cmd + ")");
        }
        if (!isRootAvailable(trace) || this.mRemoteOutputStream == null) {
            return false;
        }
        if (getReturnString(this.mRemoteInputStream).contains("quit")) {
            try {
                initRemote();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            DataOutputStream dataOutputStream = this.mRemoteOutputStream;
            dataOutputStream.write((cmd + "\n").getBytes("ASCII"));
            this.mRemoteOutputStream.flush();
            return true;
        } catch (Exception e2) {
            try {
                initRemote();
                return true;
            } catch (Exception e22) {
                e22.printStackTrace();
                Log.e(TAG, "Failed to write remote command!");
                return true;
            }
        }
    }

    String runCommandRemoteResult(String cmd, boolean trace) {
        if (!runCommandRemote(cmd, trace)) {
            return null;
        }
        try {
            String result = this.mRemoteInputStream.readLine();
            if (DEBUG) {
                Log.d(TAG, "Command returned: " + result);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to read  from remote!");
            return null;
        }
    }

    private String getReturnString(DataInputStream dataInputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int availableBytes = dataInputStream.available();
            while (true) {
                if (availableBytes <= 0) {
                    break;
                }
                int readBytes = dataInputStream.read(returnBuffer, 0, Math.min(availableBytes, returnBufferSize));
                if (readBytes == -1) {
                    break;
                }
                stringBuilder.append(new String(returnBuffer, 0, readBytes));
                availableBytes -= readBytes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = stringBuilder.toString();
        if (DEBUG) {
            Log.d(TAG, "Command returned: " + result);
        }
        return result;
    }

    void runCommandRoot(String cmd, boolean trace) {
        DataOutputStream dataOutputStream;
        if (DEBUG) {
            Log.d(TAG, "runCommandRoot(" + cmd + ")");
        }
        if (isRootAvailable(trace) && (dataOutputStream = this.mRootOutputStream) != null) {
            try {
                dataOutputStream.write(("" + cmd + "\n").getBytes("ASCII"));
                this.mRootOutputStream.flush();
            } catch (Exception e) {
                try {
                    initRoot();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    Log.e(TAG, "Failed to write root command!");
                }
            }
        }
    }

    String runCommandResult(String cmd, boolean root) {
        if (DEBUG) {
            Log.d(TAG, "runCommandResult(" + cmd + ", " + root + ")");
        }
        try {
            String[] strArr = new String[1];
            strArr[0] = root ? this.mShell : "sh";
            Process shellProc = new ProcessBuilder(strArr).redirectErrorStream(true).start();
            DataOutputStream dataOutputStream = new DataOutputStream(shellProc.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(shellProc.getInputStream());
            dataOutputStream.write((cmd + "\n").getBytes("ASCII"));
            dataOutputStream.close();
            shellProc.waitFor();
            String result = getReturnString(dataInputStream);
            if (DEBUG) {
                Log.d(TAG, "Command returned: " + result);
            }
            dataInputStream.close();
            shellProc.destroy();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to write command!");
            return null;
        }
    }
}