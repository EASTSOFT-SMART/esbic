package com.eastsoft.android.esbic.util;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import com.eastsoft.android.esbic.jni.JniUtil;

public class AudioUtil
{
	private static AudioUtil instance = new AudioUtil();
	
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
	private int sampleRateInHz = SampleRateEnum.Hz_44100.getValue();
	private int channelConfig = ChannelEnum.SINGLE.getValue();
	private int encodingBitrate = EncodingBitrateEnum.PCM_16BIT.getValue();
	private boolean isRecording;
	
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
	}
	
	public void release()
	{
		audioRecord.release();
		audioTrack.release();
	}
	
	public void startRecordAudio()
	{
		isRecording = true;
		new AudioRecordThread().start();
	}
	
	public void stopRecordAudio()
	{
		isRecording = false;
	}
	
	public void startPlayAudio(byte[] data)
	{
		audioTrack.play();
		audioTrack.write(data, 0, data.length);
		audioTrack.stop();
	}
	
	class AudioRecordThread extends Thread
    {
    	@Override
    	public void run()
    	{
    		byte[] bsBuffer = new byte[recBufSize];
    		audioRecord.startRecording();
    		while(isRecording) 
    		{
    			int line = audioRecord.read(bsBuffer, 0, recBufSize);
    			byte[] tmpBuf = new byte[line];
    			System.arraycopy(bsBuffer, 0, tmpBuf, 0, line);
				JniUtil.getInstance().push_audio_data(tmpBuf);
			}
    		audioRecord.stop();
    	}
    }
	
	public enum SampleRateEnum
	{
		Hz_11025  (11025),
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
