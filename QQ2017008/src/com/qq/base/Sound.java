package com.qq.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

public class Sound {
	@SuppressWarnings({ "resource"})
	public Sound(int cmd){
		String filename = "sound/system.wav";
		switch(cmd){
			// ����֪ͨ
			case Cmd.CMD_ONLINE:
				filename = "sound/system.wav";
				break;
			// ��Ϣ֪ͨ
			case Cmd.CMD_SEND:
				filename = "sound/Global.wav";
				break;
			// ����֪ͨ
			case Cmd.CMD_OFFLINE:
				filename = "sound/system.wav";
				break;
		}
		File file = new File(filename);
		try {
			InputStream is = new FileInputStream(file);
			int size = is.available();
			byte b[] = new byte[size];
			is.read(b, 0, size);
			// ׼�����ŵ���Դ
			AudioData ad = new AudioData(b);
			// ׼�����ŵ��豸
			AudioDataStream ds = new AudioDataStream(ad);
			// ���豸����׼���õ���Դ
			AudioPlayer.player.start(ds);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		
	}
}
