package com.meb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.base.service.BaseService;
import com.base.utils.FileRoot;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.base.utils.StreamUtils;
import com.base.web.AppConfig;

/**
 * 上传图片公开类
 * @author YXD
 *
 */
public class FileService extends BaseService {

	private static final String HEAD = "head";
	/**
     * 写入文件
     * @param inMap
     * @return
     */
    public ParaMap upFile(ParaMap inMap){
        ParaMap outMap = new ParaMap();
        String suf = inMap.getString("suf");// 后缀名
        String uid = inMap.getString("uid");// 会员uid
        String type= inMap.getString("type");//head 头像文件夹

        String fileName = uid + HEAD;
        if(StrUtils.isNotNull(suf)){
            fileName = fileName + "." + suf;
        }
        byte[] content= inMap.getBytes("file");
        if(content != null){
            try{
                File file = getAppImgFile(type, fileName);
                FileUtils.forceMkdir(file.getParentFile());
                checkFile(file);
                RandomAccessFile raf= new RandomAccessFile(file, "rw");
                raf.seek(0);
                raf.write(content, 0, content.length);
                raf.close();
                outMap.put("len", content.length);
                outMap.put("fileName", fileName);
                outMap.put("state", 1);
            }   
            catch(Exception ex){
                outMap.put("state", 0);
            }
        }else{
            outMap.put("state", 0);
        }
        return outMap;
    }
    
    
    /**
     * 下载所有文件入口
     * @param inMap
     * @return
     * @throws Exception
     * @author YXD
     */
    public byte[] downFile(ParaMap inMap) throws Exception{
        HttpServletResponse resp= getResponse();
        String suf = inMap.getString("suf");// 后缀名
        String uid = inMap.getString("uid");// 会员uid
        String type= inMap.getString("type");//head 头像文件夹
        String fileName = uid + HEAD;
        if(StrUtils.isNotNull(suf)){
            fileName = fileName + "." + suf;
        }
        File file = null;
        FileInputStream fin = null;
        byte[] buf = null ;
        switch(type)
        {
        case HEAD:
            file = AppConfig.getFile(FileRoot.imgs+ "/"+HEAD+"/"+ fileName.trim());
            fin = new FileInputStream(file);
            buf = StreamUtils.InputStreamToByte(fin);
            break;
        default:
            break;
        }
        fin.close();
        resp.setContentType("application/vnd.android.package-archive"+ ";charset="+ "UTF-8");
        resp.setHeader("Content-disposition", "attachment;filename="+ file.getName());
        resp.setHeader("Accept-Ranges", "bytes");
        log.info("####"+file.getName());
        return buf;
    }
    
    
    private File getAppImgFile(String type, String fileName){
        String fileId= FileRoot.imgs+ "/"+ type+ "/"+ fileName;
        File file= AppConfig.getFile(fileId);
        return file;
    }
    
    private void checkFile(File file) throws Exception{
        String path= file.getCanonicalPath();
        String fileRoot= AppConfig.getStringPro("fileRoot");
        if(!path.startsWith(fileRoot)) throw new Exception("文件路径非法:"+ path);
    }
}
