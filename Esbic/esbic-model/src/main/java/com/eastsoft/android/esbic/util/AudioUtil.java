package com.eastsoft.android.esbic.util;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import com.eastsoft.android.esbic.jni.JniUtil;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AudioUtil
{
	private static AudioUtil instance = new AudioUtil();
	private int SEND_DATA_LEN = 0;
	private List<byte[]> sendQueue = new ArrayList<>();
	private ByteBuffer playBuffer;
	private int playBufferLen;
	
	private AudioUtil()
	{
		init();
	}
	
	public static AudioUtil getInstance()
	{
		if (instance == null)
		{
			synchronized (AudioUtil.class)
			{
				if (instance == null)
				{
					instance = new AudioUtil();
				}
			}
		}
		return instance;
	}
	
	private AudioRecord audioRecord;
	private AudioTrack audioTrack;
	private int recBufSize = 0;
	private int playBufSize = 0;
	private int sampleRateInHz = SampleRateEnum.Hz_16000.getValue();
	private int channelConfig = ChannelEnum.SINGLE.getValue();
	private int encodingBitrate = EncodingBitrateEnum.PCM_16BIT.getValue();
	private boolean isRecording, isSending;
	
	private void init()
	{
		recBufSize = AudioRecord.getMinBufferSize(sampleRateInHz, 
				channelConfig, encodingBitrate);
		playBufSize = AudioTrack.getMinBufferSize(sampleRateInHz,
				channelConfig, encodingBitrate);
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 
				sampleRateInHz, channelConfig, encodingBitrate, recBufSize);
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRateInHz, 
				channelConfig, encodingBitrate, playBufSize, AudioTrack.MODE_STREAM);
		SEND_DATA_LEN = 180;
		playBufferLen = playBufSize + (SEND_DATA_LEN-(playBufSize%SEND_DATA_LEN));
	}
	
	public void release()
	{
		audioRecord.release();
		audioTrack.release();
	}
	
	public void startRecordAudio()
	{
//		isRecording = true;
//		isSending = true;
//		new AudioRecordThread().start();
//		new SendAudioData().start();
	}
	
	public void stopRecordAudio()
	{
		isRecording = false;
		isSending = false;
	}
	
	public void startPlayAudio(byte[] data)
	{
		LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_DEBUG, "Recv audio data size : " + data.length);
		if(playBuffer == null)
		{
			playBuffer = ByteBuffer.allocate(playBufferLen);
		}
		playBuffer.put(data);
		if(playBuffer.position() == playBuffer.limit())
		{
			LogUtil.print("play audio data");
			byte[] tmp = playBuffer.array();
			audioTrack.play();
			audioTrack.write(tmp, 0, tmp.length);
			audioTrack.stop();
			playBuffer = null;
		}
	}
	
	class AudioRecordThread extends Thread
    {
    	@Override
    	public void run()
    	{
			int len = recBufSize + (SEND_DATA_LEN-(recBufSize%SEND_DATA_LEN));
    		byte[] bsBuffer = new byte[len];
    		audioRecord.startRecording();
    		while(isRecording) 
    		{
    			int line = audioRecord.read(bsBuffer, 0, len);
				for(int i = 0; i<(len/180); i++)
				{
					byte[] tmpBuf = new byte[SEND_DATA_LEN];
					System.arraycopy(bsBuffer, i*SEND_DATA_LEN, tmpBuf, 0, SEND_DATA_LEN);
					sendQueue.add(tmpBuf);
				}
			}
    		audioRecord.stop();
    	}
    }

	class SendAudioData extends Thread
	{
		@Override
		public void run()
		{
			while(isSending)
			{
				if(sendQueue.size() == 0)
				{
					continue;
				}
				byte[] data = sendQueue.get(0);
				JniUtil.getInstance().push_audio_data(data);
				LogUtil.print("SEND audio data , len = " + data.length + " " + Arrays.toString(data));
				if(sendQueue.size() > 0)
				{
					sendQueue.remove(0);
				}
			}
			audioRecord.stop();
		}
	}
	
	public enum SampleRateEnum
	{
		Hz_11025  (11025),
		Hz_16000  (16000),
		Hz_22050  (22050),
		Hz_44100  (44100),
		;
		private int value;
		SampleRateEnum(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
	}
	
	@SuppressWarnings("deprecation")
	public enum ChannelEnum
	{
		SINGLE  (AudioFormat.CHANNEL_CONFIGURATION_MONO),
		DOUBLE  (AudioFormat.CHANNEL_CONFIGURATION_STEREO),
		;
		private int value;
		ChannelEnum(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
	}
	
	public enum EncodingBitrateEnum
	{
		PCM_8BIT   (AudioFormat.ENCODING_PCM_8BIT),
		PCM_16BIT  (AudioFormat.ENCODING_PCM_16BIT),
		;
		private int value;
		EncodingBitrateEnum(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
	}
}
