package com.example.app.ftpUpload;

import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * FTP操作辅助类.
 */
public class Ftpupload  extends Thread {

	String path;
	String fileName;
	InputStream stream;
	FTPUtil ftp;
	private String vandaAddress;
	private String user;
	private String pwd;
	private String port;
	public Ftpupload(String path, String fileName, InputStream stream,String vandaAddress,String user,String pwd,String port){
		this.path = path;
		this.fileName = fileName;
		this.stream = stream;
		this.vandaAddress=vandaAddress;
		this.user=user;
		this.pwd=pwd;
		this.port=port;
	}
	@Override
	public void run() {
		Lock lock = new ReentrantLock();
		try {
			lock.lock();
			ftp = new FTPUtil(vandaAddress,port,user,pwd);
			ftp.login();
            ftp.uploadFile(path,fileName,stream);
         }
		finally {
			ftp.loginout();
			lock.unlock();
		}
	}


}