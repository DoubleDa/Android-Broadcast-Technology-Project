# Android直播技术研究与实践


## 基础知识

### 概述

* 视频播放	

视频的编解码

* 视频录制

借助于牛逼的硬件摄像头

### 知识概要

关于Android中的视频处理，这里主要包：Android中的摄像头技术，录制视频，视频播放器等知识点。这些技术有一个基本的要求就是字节操作，考虑很多字节流处理，这个在Android中有一个类ByteBuffer。

Android中视频的处理大纲图解：

![](http://img.blog.csdn.net/20160710143827946?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center =x320)


### 知识结构

* 视频编码

Android中视频编码有两种方式，主要是两个核心的类，一个是**MediaCodec**和**MediaRecorder**，这两个类有什么区别呢？其实很好理解，他们都可以对视频进行编码，但是唯一不同的是**MediaCodec更偏向原生**，而**MediaRecorder偏向的上层封装**。

1.MediaCodec

MediaCodec可以处理具体的视频流，主要有这几个方法：

	getInputBuffers：获取需要编码数据的输入流队列，返回的是一个ByteBuffer数组
	queueInputBuffer：输入流入队列
	dequeueInputBuffer：从输入流队列中取数据进行编码操作
	getOutputBuffers：获取编解码之后的数据输出流队列，返回的是一个ByteBuffer数组
	dequeueOutputBuffer：从输出队列中取出编码操作之后的数据
	releaseOutputBuffer：处理完成，释放ByteBuffer数据
	

![](http://img.blog.csdn.net/20160710153028900?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center =x320)

视频流有一个**输入队列**和**输出队列**，分别对应getInputBuffers和getOutputBuffers这两个方法获取这个队列，然后对于输入流这端有两个方法一个是queueInputBuffers是将视频流入队列，dequeueInputBuffer是从输入流队列中取出数据进行编解码操作，在输出端这边有一个dequeueOutputBuffer方法从输出队列中获取视频数据，releaseOutputBuffers方法将处理完的输出视频流数据ByteBuffer放回视频流输出队列中，再次循环使用。这样视频流输入端和输出端分别对应一个ByteBuffer队列，这些ByteBuffer可以重复使用，在处理完数据之后再放回去即可。

所以这里看到**MediaCodec类处理视频的时候可以接触到视频流数据**的，这里比如我们如果有一些特殊需求，比如视频的叠加技术，添加字幕等就可以在这里处理了。同时MediaCodec有一个方法：createInputSurface可以设置视频源输入Surface类型，同时也是可以通过configure方法设置视频输出Surface类型。

2.MediaRecorder

MediaRecorder这个类相对于MediaCodec简单，因为他封装的很好，直接就是几个接口来完成视频录制，比如视频的编码格式，视频的保存路劲，视频来源等，用法简单，但是有一个问题就是**不能接触到视频流数据**了，**处理不了原生的视频数据**了。这个也是他和MediaCodec最大的区别，他完成不了视频的叠加技术的。

注意：

关于MediaRecorder这个类，其实他和Android中的一个命令是相对应的，就是：adb screenrecord。这个类还有一个地方需要注意的就是他有一个方法：**setVideoSource**，**可以设置视频来源**，代码后续文章会介绍，主要就两个来源：**一个是来自于设备的摄像头Camera**，**一个是来自于Surface**，关于Surface这个类后面会介绍。

注意：

现在视频编码的格式都是**H264**的，关于H264格式说明如下：

H.264，MPEG-4,MPEG-2等这些都是压缩算法，毕竟带宽是有限的，为了获得更好的图像的传输和显示效果，就不断的想办法去掉一些信息，转换一些信息等等，这就是这些压缩算法的做的事情。H.264最大的优势是**具有很高的数据压缩比率**，在同等图像质量的条件下，H.264的压缩比是MPEG-2的2倍以上，是MPEG-4的1.5～2倍。举个例子，原始文件的大小如果为88GB，采用MPEG-2压缩标准压缩后变成3.5GB，压缩比为25∶1，而采用H.264压缩标准压缩后变为879MB，从88GB到879MB，H.264的压缩比达到惊人的102∶1！H.264为什么有那么高的压缩比？**低码率**（Low Bit Rate）起了重要的作用，和MPEG-2和MPEG-4 ASP等压缩技术相比，H.264压缩技术将**大大节省用户的下载时间和数据流量收费**。尤其值得一提的是，H.264在具有**高压缩比**的同时还拥有**高质量流畅的图像**。写了这么多，举个例来说下，比如移动电视，我们接收的到的图像信号一般是H.264格式的，移动设备接收到后，需要先解码成原始的YUV码流，然后又转换成RGB码流，将一帧一帧的RGB数据放到显存上才能显示出图像。虽然传输快了，得是增加了设备的解码成本，不过总体来讲肯定是值得的。现在PC上的显卡慢慢都要集成H.264的硬件解码，据说苹果的最新产品IPAD也是有了这个硬解码。而YUV到RGB的转换，很多ARM芯片上都有了。





