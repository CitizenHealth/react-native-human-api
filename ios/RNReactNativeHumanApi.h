
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif
#import "HumanConnectViewController.h"

@interface RNReactNativeHumanApi : NSObject <HumanAPINotifications, RCTBridgeModule>

@end
  
