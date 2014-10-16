package edu.rosehulman.johncena;

import java.io.IOException;

import java.io.OutputStream;

import java.net.URI;

import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.FSDataInputStream;

import org.apache.hadoop.fs.FileSystem;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.fs.ftp.FTPFileSystem;

import org.apache.hadoop.io.IOUtils;

public class FTPtoHDFS {
	public static void main(String[] args) throws IOException, URISyntaxException {
		if (args.length != 3) {
			System.out.println("Usage: FTPtoHDFS <ftp server connection string> <ftp location> <hdfs location>");
			return;
		}
		String serverString = args[0];
		String src = args[1];
		Configuration conf = new Configuration();
		FTPFileSystem ftpfs = new FTPFileSystem();
		ftpfs.setConf(conf);
		ftpfs.initialize(new URI("ftp://username:password@host"), conf);
		ftpfs.initialize(new URI(serverString), conf);
		FSDataInputStream fsdin = ftpfs.open(new Path(src), 1000);
		FileSystem fileSystem = FileSystem.get(conf);
		System.out.println(args[2]);
		OutputStream outputStream = fileSystem.create(new Path(args[2]));
		IOUtils.copyBytes(fsdin, outputStream, conf, true);
		ftpfs.close();
	}
}