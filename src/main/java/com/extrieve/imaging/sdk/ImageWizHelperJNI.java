
package com.extrieve.imaging.sdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.HashMap;

public class ImageWizHelperJNI {
    private final Object lock = new Object();
    private static long handle = 0L;
    private static long count = 0L;

    private static String readLicData(String licPath) throws Exception {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(licPath));

            String mLine;
            while((mLine = reader.readLine()) != null) {
                builder.append(mLine);
            }
        } catch (Exception var8) {
            throw new RuntimeException("Unable to Read Licence File");
        } finally {
            if (reader != null) {
                reader.close();
            }

        }

        return builder.length() > 1 ? builder.toString() : null;
    }

    public ImageWizHelperJNI() throws Exception {
        String DllName = "libImageWizHelperJNI";
        String LicName = "waves_imaging";
        String currentDir = System.getProperty("user.dir");
        String dllPath = currentDir + File.separator + DllName + ".so";
        String licPath = currentDir + File.separator + LicName + ".lic";
        if (!Files.exists(Paths.get(dllPath), new LinkOption[0])) {
            throw new RuntimeException("Shared library not found");
        } else {
            try {
                System.load(dllPath);
                StringBuffer error_info = new StringBuffer("");
                if (Files.exists(Paths.get(licPath), new LinkOption[0])) {
                    synchronized(this.lock) {
                        boolean unlock = unlockLibrary(readLicData(licPath), error_info);
                        if (!unlock && error_info.length() > 0) {
                            throw new Exception(error_info.toString());
                        }

                        handle = initialize();
                        if (handle == 0L) {
                            throw new RuntimeException("Unable to load imaging library");
                        }

                        ++count;
                    }

                    Runtime.getRuntime().addShutdownHook(new Thread() {
                        public void run() {
                            if (ImageWizHelperJNI.handle > 0L) {
                                synchronized(ImageWizHelperJNI.this.lock) {
                                    if (ImageWizHelperJNI.count == 1L) {
                                        ImageWizHelperJNI.terminate(ImageWizHelperJNI.handle);
                                    }

                                    ImageWizHelperJNI.count = ImageWizHelperJNI.count - 1L;
                                }
                            }

                        }
                    });
                } else {
                    throw new RuntimeException("License file not Found");
                }
            } catch (Exception var10) {
                System.out.println(var10.toString());
                throw new RuntimeException(var10);
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (handle > 0L) {
                synchronized(this.lock) {
                    if (count == 1L) {
                        terminate(handle);
                    }

                    --count;
                }
            }
        } finally {
            super.finalize();
        }

    }

    private static native boolean unlockLibrary(String var0, StringBuffer var1);

    private static native long initialize();

    private static native int terminate(long var0);

    private static native void resetToDefault(long var0);

    private static native int getErrorDescription(int var0, StringBuffer var1, int var2);

    private static native int setPageLayout(long var0, int var2);

    private static native int getPageLayout(long var0, IntPtr var2, IntPtr var3);

    private static native int setDPI(long var0, int var2);

    private static native int getDPI(long var0, IntPtr var2);

    private static native int setConversion(long var0, int var2);

    private static native int getConversion(long var0, IntPtr var2);

    private static native int setImageQuality(long var0, int var2);

    private static native int getImageQuality(long var0, IntPtr var2);

    private static native int setImageCustomWidth(long var0, int var2);

    private static native int getImageCustomWidth(long var0, IntPtr var2);

    private static native int setImageCustomHeigth(long var0, int var2);

    private static native int getImageCustomHeight(long var0, IntPtr var2);

    private static native int enableExifRotation(long var0);

    private static native int disableExifRotation(long var0);

    private static native int getPageCountInImageFile(long var0, String var2, String var3);

    private static native int compressToTIFF(long var0, String[] var2, String var3, int var4);

    private static native int compressToPDF(long var0, String[] var2, String var3, int var4);

    private static native int compressToJPEG(long var0, String[] var2, String var3, int var4);

    private static native int ocrBWConversionToTiff(long var0, String var2, int var3, String var4, int var5);

    private static native int appendToTIFF(long var0, String var2, String var3, int var4);

    private static native int getMaskImage(long var0, String var2, String var3, int var4, Rect[] var5, long var6);

    private static native int compressPagesToTiff_Array(long var0, String var2, String var3, int[] var4, boolean var5, int var6);

    private static native int compressPagesToTiff_Range(long var0, String var2, String var3, int var4, int var5, boolean var6, int var7);

    public String GetErrorDescription(int errorCode) {
        StringBuffer error_info = new StringBuffer("");
        getErrorDescription(errorCode, error_info, 255);
        return error_info.toString();
    }

    public void ResetToDefault() {
        resetToDefault(handle);
    }

    public int SetPageLayout(ImageWizHelperJNI.Layout layout) {
        return setPageLayout(handle, layout.getValue());
    }

    public ImageWizHelperJNI.Layout GetPageLayouWidth() {
        IntPtr objWidth = new IntPtr();
        IntPtr objHeight = new IntPtr();
        int ret = getPageLayout(handle, objHeight, objWidth);
        return ret != 0 ? ImageWizHelperJNI.Layout.Unknown : ImageWizHelperJNI.Layout.valueOf(objWidth.value);
    }

    public ImageWizHelperJNI.Layout GetPageLayouHeight() {
        IntPtr objWidth = new IntPtr();
        IntPtr objHeight = new IntPtr();
        int ret = getPageLayout(handle, objHeight, objWidth);
        return ret != 0 ? ImageWizHelperJNI.Layout.Unknown : ImageWizHelperJNI.Layout.valueOf(objHeight.value);
    }

    public int SetDPI(ImageWizHelperJNI.ImageDPI imageDPI) {
        return setDPI(handle, imageDPI.getValue());
    }

    public ImageWizHelperJNI.ImageDPI GetDPI() {
        IntPtr objDpi = new IntPtr();
        int ret = getDPI(handle, objDpi);
        return ret != 0 ? ImageWizHelperJNI.ImageDPI.Unknown : ImageWizHelperJNI.ImageDPI.valueOf(objDpi.value);
    }

    public int SetConversion(ImageWizHelperJNI.ConversionType convertionType) {
        return setConversion(handle, convertionType.getValue());
    }

    public ImageWizHelperJNI.ConversionType GetConversion() {
        IntPtr objConversionType = new IntPtr();
        int ret = getConversion(handle, objConversionType);
        return ret != 0 ? ImageWizHelperJNI.ConversionType.Unknown : ImageWizHelperJNI.ConversionType.valueOf(objConversionType.value);
    }

    public int SetImageQuality(ImageWizHelperJNI.ImageQuality imageQuality) {
        return setImageQuality(handle, imageQuality.getValue());
    }

    public ImageWizHelperJNI.ImageQuality GetImageQuality() {
        IntPtr objquality = new IntPtr();
        int ret = getImageQuality(handle, objquality);
        return ret != 0 ? ImageWizHelperJNI.ImageQuality.Unknown : ImageWizHelperJNI.ImageQuality.valueOf(objquality.value);
    }

    public int SetImageCustomWidth(int widthInInches) {
        return setImageCustomWidth(handle, widthInInches);
    }

    public int getImageCustomHeight() {
        IntPtr objHeightInInches = new IntPtr();
        int ret = getImageCustomHeight(handle, objHeightInInches);
        return ret != 0 ? ret : objHeightInInches.value;
    }

    public int SetImageCustomHeigth(int heightInInches) {
        return setImageCustomHeigth(handle, heightInInches);
    }

    public int GetImageCustomHeight() {
        IntPtr objHeightInInches = new IntPtr();
        int ret = getImageCustomHeight(handle, objHeightInInches);
        return ret != 0 ? ret : objHeightInInches.value;
    }

    public int EnableExifRotation() {
        return enableExifRotation(handle);
    }

    public int DisableExifRotation() {
        return disableExifRotation(handle);
    }

    public int GetPageCountInImageFile(String filename, String pwd) {
        return getPageCountInImageFile(handle, filename, pwd);
    }

    public int CompressToTIFF(String[] inputFiles, String outputFile, ImageWizHelperJNI.ResetOption resetOption) {
        return compressToTIFF(handle, inputFiles, outputFile, resetOption.getValue());
    }

    public int CompressToPDF(String[] inputFiles, String outputFile, ImageWizHelperJNI.ResetOption resetOption) {
        return compressToPDF(handle, inputFiles, outputFile, resetOption.getValue());
    }

    public int CompressToJPEG(String[] inputFile, String outPutFile, ImageWizHelperJNI.ResetOption resetOption) {
        return compressToJPEG(handle, inputFile, outPutFile, resetOption.getValue());
    }

    public int OcrBWConversionToTiff(String inputFile, int pageNumber, String outputFileName, ImageWizHelperJNI.ResetOption resetOption) {
        return ocrBWConversionToTiff(handle, inputFile, pageNumber, outputFileName, resetOption.getValue());
    }

    public int AppendToTIFF(String inputFile, String outputFile, ImageWizHelperJNI.ResetOption resetOption) {
        return appendToTIFF(handle, inputFile, outputFile, resetOption.getValue());
    }

    public int GetMaskImage(String fileName, String outputFileName, int PageNum, Rect[] MaskPortion, long MaskValue) {
        return getMaskImage(handle, fileName, outputFileName, PageNum, MaskPortion, MaskValue);
    }

    public int CompressPagesToTiff_Array(String inputFile, String outputFile, int[] pageArray, boolean append, ImageWizHelperJNI.ResetOption resetOption) {
        return compressPagesToTiff_Array(handle, inputFile, outputFile, pageArray, append, resetOption.getValue());
    }

    public int CompressPagesToTiff_Range(String inputFile, String outputFile, int startPage, int endPage, boolean append, ImageWizHelperJNI.ResetOption resetOption) {
        return compressPagesToTiff_Range(handle, inputFile, outputFile, startPage, endPage, append, resetOption.getValue());
    }

    public static enum ConversionType {
        Unknown(-1),
        No_Conversion(0),
        Convert_To_BW(1),
        Convert_To_Grey(2);

        private int value;
        private static HashMap<Object, Object> map = new HashMap();

        static {
            ImageWizHelperJNI.ConversionType[] var3;
            int var2 = (var3 = values()).length;

            for(int var1 = 0; var1 < var2; ++var1) {
                ImageWizHelperJNI.ConversionType conversiontype = var3[var1];
                map.put(conversiontype.value, conversiontype);
            }

        }

        private ConversionType(int value) {
            this.value = value;
        }

        public static ImageWizHelperJNI.ConversionType valueOf(int conversiontype) {
            return (ImageWizHelperJNI.ConversionType)map.get(conversiontype);
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum ImageDPI {
        Unknown(-1),
        DPI_100(0),
        DPI_150(1),
        DPI_200(2),
        DPI_300(3),
        DPI_500(4),
        DPI_600(5);

        private int value;
        private static HashMap<Object, Object> map = new HashMap();

        static {
            ImageWizHelperJNI.ImageDPI[] var3;
            int var2 = (var3 = values()).length;

            for(int var1 = 0; var1 < var2; ++var1) {
                ImageWizHelperJNI.ImageDPI imageDPI = var3[var1];
                map.put(imageDPI.value, imageDPI);
            }

        }

        private ImageDPI(int value) {
            this.value = value;
        }

        public static ImageWizHelperJNI.ImageDPI valueOf(int imageDPI) {
            return (ImageWizHelperJNI.ImageDPI)map.get(imageDPI);
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum ImageQuality {
        Unknown(-1),
        Photo_Quality(0),
        Document_Quality(1),
        Compressed_Document(2);

        private final int value;
        private static HashMap<Object, Object> map = new HashMap();

        static {
            ImageWizHelperJNI.ImageQuality[] var3;
            int var2 = (var3 = values()).length;

            for(int var1 = 0; var1 < var2; ++var1) {
                ImageWizHelperJNI.ImageQuality imageQuality = var3[var1];
                map.put(imageQuality.value, imageQuality);
            }

        }

        private ImageQuality(int value) {
            this.value = value;
        }

        public static ImageWizHelperJNI.ImageQuality valueOf(int imageQuality) {
            return (ImageWizHelperJNI.ImageQuality)map.get(imageQuality);
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum Layout {
        Unknown(-1),
        A0(0),
        A1(1),
        A2(2),
        A3(3),
        A4(4),
        A5(5),
        A6(6),
        A7(7);

        private int value;
        private static HashMap<Object, Object> map = new HashMap();

        static {
            ImageWizHelperJNI.Layout[] var3;
            int var2 = (var3 = values()).length;

            for(int var1 = 0; var1 < var2; ++var1) {
                ImageWizHelperJNI.Layout layout = var3[var1];
                map.put(layout.value, layout);
            }

        }

        private Layout(int value) {
            this.value = value;
        }

        public static ImageWizHelperJNI.Layout valueOf(int layout) {
            return (ImageWizHelperJNI.Layout)map.get(layout);
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum ResetOption {
        No_DPI_change(0),
        ResetAllDPI(1),
        ResetZeroDPI(2);

        private int value;
        private static HashMap<Object, Object> map = new HashMap();

        static {
            ImageWizHelperJNI.ResetOption[] var3;
            int var2 = (var3 = values()).length;

            for(int var1 = 0; var1 < var2; ++var1) {
                ImageWizHelperJNI.ResetOption resetOption = var3[var1];
                map.put(resetOption.value, resetOption);
            }

        }

        private ResetOption(int value) {
            this.value = value;
        }

        public static ImageWizHelperJNI.ResetOption valueOf(int resetOption) {
            return (ImageWizHelperJNI.ResetOption)map.get(resetOption);
        }

        public int getValue() {
            return this.value;
        }
    }
}