package com.android.smsutil;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/2.
 */

public class StringUtils {
    public static void main(String[] args) {
    }

    public static byte[] getBytes(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            while (true) {
                int n = fis.read(b);
                if (n == -1) {
                    fis.close();
                    bos.close();
                    return bos.toByteArray();
                }
                bos.write(b, 0, n);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void getFile(byte[] bfile, String filePath, String fileName) throws Throwable {
        Exception e;
        Throwable th;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            File dir = new File(filePath);
            if (!(dir.exists() || dir.isDirectory())) {
                dir.mkdirs();
            }
            File file = new File(new StringBuilder(String.valueOf(filePath)).append("\\").append(fileName).toString());
            File file2;
            try {
                FileOutputStream fos2 = new FileOutputStream(file);
                try {
                    BufferedOutputStream bos2 = new BufferedOutputStream(fos2);
                    try {
                        bos2.write(bfile);
                        if (bos2 != null) {
                            try {
                                bos2.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        if (fos2 != null) {
                            try {
                                fos2.close();
                                file2 = file;
                                fos = fos2;
                                bos = bos2;
                                return;
                            } catch (IOException e12) {
                                e12.printStackTrace();
                            }
                        }
                        file2 = file;
                        fos = fos2;
                        bos = bos2;
                    } catch (Exception e2) {
                        e = e2;
                        file2 = file;
                        fos = fos2;
                        bos = bos2;
                        try {
                            e.printStackTrace();
                            if (bos != null) {
                                try {
                                    bos.close();
                                } catch (IOException e122) {
                                    e122.printStackTrace();
                                }
                            }
                            if (fos == null) {
                                try {
                                    fos.close();
                                } catch (IOException e1222) {
                                    e1222.printStackTrace();
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (bos != null) {
                                try {
                                    bos.close();
                                } catch (IOException e12222) {
                                    e12222.printStackTrace();
                                }
                            }
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e122222) {
                                    e122222.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        file2 = file;
                        fos = fos2;
                        bos = bos2;
                        if (bos != null) {
                            bos.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    file2 = file;
                    fos = fos2;
                    e.printStackTrace();
                    if (bos != null) {
                        bos.close();
                    }
                    if (fos == null) {
                        fos.close();
                    }
                } catch (Throwable th4) {
                    th = th4;
                    file2 = file;
                    fos = fos2;
                    if (bos != null) {
                        bos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                file2 = file;
                e.printStackTrace();
                if (bos != null) {
                    bos.close();
                }
                if (fos == null) {
                    fos.close();
                }
            } catch (Throwable th5) {
                th = th5;
                file2 = file;
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos == null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static String getimei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        if (tm.getDeviceId() == null) {
            return "00000000000000000";
        }
        return tm.getDeviceId();
    }

    public static String getTextCenter(String text, String begin, String end) {
        try {
            int b = text.indexOf(begin) + begin.length();
            return text.substring(b, text.indexOf(end));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("string","e------->"+e.getMessage());
            return "error";
        }
    }

    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyyMMddHHmmss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }
}
