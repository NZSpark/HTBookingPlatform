package com.seclib.htbp.oss.service.impl

import java.lang.Exception
import com.seclib.htbp.oss.utils.ConstantOssPropertiesUtils
import com.seclib.htbp.oss.service.FileService
import org.springframework.web.multipart.MultipartFile

import com.aliyun.oss.OSSClientBuilder
import org.joda.time.DateTime
import java.util.UUID
import org.springframework.stereotype.Service

@Service
open class FileServiceImpl : FileService {
    override fun upload(file: MultipartFile): String {
        val endpoint: String = ConstantOssPropertiesUtils.ENDPOINT
        val accessKeyId: String = ConstantOssPropertiesUtils.ACCESS_KEY_ID
        val accessKeySecret: String = ConstantOssPropertiesUtils.SECRET
        val bucketName: String = ConstantOssPropertiesUtils.BUCKET
        return try {
            // 创建OSSClient实例。
            val ossClient = OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret)
            // 上传文件流。
            val inputStream = file.inputStream
            var fileName = file.originalFilename
            //生成随机唯一值，使用uuid，添加到文件名称里面
            val uuid = UUID.randomUUID().toString().replace("-".toRegex(), "")
            fileName = uuid + fileName
            //按照当前日期，创建文件夹，上传到创建文件夹里面
            //  2021/02/02/01.jpg
            val timeUrl = DateTime().toString("yyyy/MM/dd")
            fileName = "$timeUrl/$fileName"
            //调用方法实现上传
            ossClient.putObject(bucketName, fileName, inputStream)
            // 关闭OSSClient。
            ossClient.shutdown()
            //上传之后文件路径
            // https://yygh-atguigu.oss-cn-beijing.aliyuncs.com/01.jpg
            //返回
            "https://$bucketName.$endpoint/$fileName"
        } catch (e: Exception) {
            e.printStackTrace()
            //            return null;
            "http://localhost:3000/_nuxt/assets/images/logo.png" //for test
        }
    }
}