package mainui;

import android.app.Application;
import com.parse.Parse;


public class ViceAgentApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "tesGMXqwermSgT7dxZ12YyyyWaykwqFcEOiXpZUQ", "EHQkHZiEzpa6dRnJTYV238sDYRs9awZZO1vP8GaN");


    }

}
