package io.invertase.firebase.fiam;

import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.CampaignMetadata;
import com.google.firebase.inappmessaging.model.InAppMessage;

public class ReactNativeFirebaseFiamClickListener implements FirebaseInAppMessagingClickListener {
    ReactApplicationContext reactContext;
    String eventName;
    ReactNativeFirebaseFiamClickListener(ReactApplicationContext reactContext, String eventName) {
      this.reactContext = reactContext;
      this.eventName = eventName;
    }

    @Override
    public void messageClicked(InAppMessage inAppMessage, Action action) {
        String url = action.getActionUrl();

        CampaignMetadata metadata = inAppMessage.getCampaignMetadata();

        reactContext
          .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
          .emit(eventName, "FOO");
        reactContext
          .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
          .emit(eventName, url);
        reactContext
          .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
          .emit(eventName, "BAR");
        reactContext
          .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
          .emit(eventName, "SHO");
    }
}
