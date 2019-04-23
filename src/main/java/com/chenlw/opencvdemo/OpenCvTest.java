package com.chenlw.opencvdemo;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * 描述:
 *
 * @author chenlw
 * @create 2019-04-22 10:35
 */
public class OpenCvTest {

    static{
        // 载入opencv的库
        //String opencvpath = System.getProperty("user.dir") + "\\opencv\\x64\\";
        String opencvpath = System.getProperty("user.dir") + "\\opencv\\x86\\";
        System.out.println(opencvpath);
        String opencvDllName = opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll";
        System.load(opencvDllName);
    }


    public static void main(String[] args){
        //System.out.println("hello world");
        try{
            //人脸识别
            detectFace("E:\\OpenCV3.4.5\\opencvTest\\face.jpg", "E:\\OpenCV3.4.5\\opencvTest\\detectFace.jpg");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    /**
     * opencv实现人脸识别
     * @param imagePath
     * @param outFile
     * @throws Exception
     */
    public static void detectFace(String imagePath,  String outFile) throws Exception
    {

        System.out.println("Running DetectFace ... ");
        // 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
        CascadeClassifier faceDetector = new CascadeClassifier(
                "E:\\OpenCV3.4.5\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");

        Mat image = Imgcodecs.imread(imagePath);

        // 在图片中检测人脸
        MatOfRect faceDetections = new MatOfRect();

        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        Rect[] rects = faceDetections.toArray();
        if(rects != null && rects.length > 1){
            throw new RuntimeException("超过一个脸");
        }
        // 在每一个识别出来的人脸周围画出一个方框
        Rect rect = rects[0];

        Imgproc.rectangle(image, new Point(rect.x-2, rect.y-2),
                new Point(rect.x + rect.width, rect.y + rect.height),
                new Scalar(0, 255, 0));

        Imgcodecs.imwrite(outFile, image);
        System.out.println(String.format("人脸识别成功，人脸图片文件为： %s", outFile));


    }



}
