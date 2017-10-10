package srt.inz.connnectors;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import srt.inz.mytripuser.Chatroom;

public class MyAppController extends Application {
	
	public static synchronized MyAppController getInstance() {
	    return getInstance();
	  }
	

	  public void stopService(View view) {
	    stopService(new Intent(getBaseContext(), Chatroom.MessageRecApiTask.class));
	  }

}
