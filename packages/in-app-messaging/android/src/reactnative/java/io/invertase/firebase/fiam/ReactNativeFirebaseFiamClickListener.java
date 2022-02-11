package io.invertase.firebase.fiam;

import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.CampaignMetadata;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import java.util.Map;

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

        WritableMap campaignData = Arguments.createMap();
        CampaignMetadata metadata = inAppMessage.getCampaignMetadata();
        campaignData.putBoolean("isTestMessage", metadata.getIsTestMessage());
        campaignData.putString("campaignName", metadata.getCampaignName());	
        campaignData.putString("campaignId", metadata.getCampaignId());	

        WritableMap messageData = Arguments.createMap();
        Map<String, String> dataMap = inAppMessage.getData();
        for (String key : dataMap.keySet()) {
           messageData.putString(key, dataMap.get(key));
        }

        WritableMap out = Arguments.createMap();
        out.putString("url", url);
        out.putMap("campaignData", campaignData);
        out.putMap("messageData", messageData);
        reactContext
          .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
          .emit(eventName, out);
    }
}
