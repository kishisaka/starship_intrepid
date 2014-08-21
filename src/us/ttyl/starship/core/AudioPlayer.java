package us.ttyl.starship.core;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer 
{
	private Clip mClip;

	public AudioPlayer(String samplePath)
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File(samplePath));
			mClip = AudioSystem.getClip();
			mClip.open(audioInputStream);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void play() 
	{
		if (mClip != null)
		{
			mClip.stop();
			mClip.setFramePosition(0);
			mClip.start();
		}
	}
}
