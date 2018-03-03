
#import "RNReactNativeHumanApi.h"

@interface RNReactNativeHumanApi ()
@property (nonatomic, strong) RCTResponseSenderBlock callback;
@end

@implementation RNReactNativeHumanApi

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_REMAP_METHOD(onConnect, connectOptions:(NSDictionary *)options callback:(RCTResponseSenderBlock)callback) {
    self.callback = callback;
    
    NSString *clientId = options[@"client_id"];
    NSString *clientUserId = options[@"client_user_id"];
    NSString *authURL = options[@"auth_url"];
    NSString *language = options[@"language"];
    NSString *publicToken = options[@"public_token"];
    
    HumanConnectViewController *hcvc = [[HumanConnectViewController alloc] initWithClientID:clientId andAuthURL:authURL];
    
    UIViewController *root = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    while (root.presentedViewController != nil) {
        root = root.presentedViewController;
    }
    
    dispatch_async(dispatch_get_main_queue(), ^{
        hcvc.delegate = self;
        [root presentViewController:hcvc animated:YES completion:nil];
        
        if (publicToken && [publicToken length] > 0) {
            [hcvc startConnectFlowFor: clientUserId
                       andPublicToken: publicToken];
            
        } else {
            [hcvc startConnectFlowForNewUser:clientUserId];
        }
    });
}

/** Connect success handler */
- (void)onConnectSuccess:(NSDictionary *)data
{
    NSLog(@"Connect success! %@", data);
    self.callback(@[data]);
    
    //Notify user of success
    //Save publicToken with local user for subsequent Human Connect launches
}


/** Connect failure handler */
- (void)onConnectFailure:(NSString *)error
{
    NSLog(@"Connect failure: %@", error);
    self.callback(@[@{@"status": @"cancel"}]);
}

@end
  
