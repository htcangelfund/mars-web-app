package com.itcd.angelfund.mars.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NotificationController {
	
	private static int count;
	private static String filePath = "./notdelete.txt";
	
	public NotificationController() {
		super();
		count = readCount();
	}

	@CrossOrigin(origins = "http://139.224.70.36:8080")
	@GetMapping("/click")
	public int click() {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
		}
		if (file.canWrite()) {
			try {
				file.setWritable(true);
				FileWriter fileWriter = new FileWriter(file);
				count = count+1;
				fileWriter.write(String.valueOf(count));
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			} 
		}
		return count;
	}

	private int readCount() {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
		}
		if (file.canRead()) {
			try {
				file.setReadable(true);
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String readLine = bufferedReader.readLine();
				if(readLine != null) {
					return Integer.parseInt(readLine);
				}else {
					return 0;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
		}
		return 0;
	}
}
