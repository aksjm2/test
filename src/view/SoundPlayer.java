package view;

import java.io.File;
import java.io.PrintStream;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class SoundPlayer implements BasicPlayerListener {
	private PrintStream out = null;

	public SoundPlayer() {
		out = System.out;
	}

	public synchronized void play(String filename) {
		BasicPlayer player = new BasicPlayer();
		BasicController control = (BasicController) player;
		player.addBasicPlayerListener(this);

		try {
			control.open(new File(filename));
			control.play();
			control.setGain(0.85);
			control.setPan(1.0);
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	public void opened(Object stream, Map properties) {
		display("opened : " + properties.toString());
	}

	public void progress(int bytesread, long microseconds, byte[] pcmdata,
			Map properties) {
		display("progress : " + properties.toString());
	}

	public void stateUpdated(BasicPlayerEvent event) {
		display("stateUpdated : " + event.toString());
	}

	public void setController(BasicController controller) {
		display("setController : " + controller);
	}

	public void display(String msg) {

	}
}