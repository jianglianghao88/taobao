package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	@Value("IMAGE_IP_URL")
	private String IMAGE_IP_URL;
	
	@Override
	public PictureResult uploadPicture(MultipartFile uploadFile) {
		PictureResult pictureResult = new PictureResult();
		
		// TODO Auto-generated method stub
		if(uploadFile.isEmpty()){
			pictureResult.setError(1);
			pictureResult.setMessage("图片不能为空");
		}
		try {
			FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
			String url = client.uploadFile(uploadFile.getBytes());
			url = IMAGE_IP_URL + url;
			
			pictureResult.setError(0);
			pictureResult.setUrl(url);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pictureResult.setError(1);
			pictureResult.setMessage("图片上传失败");
		}
		
		return null;
	}

}
