
import { NativeModules } from 'react-native';

const { RNReactNativeHumanApi } = NativeModules;

class RNHumanAPI {
    onConnect(options) {
      const onCallback = (data) => {
        const status = data.status
        switch(status) {
          case 'auth':
            if (options.auth)
              options.auth(data)
            break
          case 'success':
            if (options.success)
              options.success(data)
            break
          default:
            if (options.cancel)
              options.cancel()
            break
        }
      }
      RNReactNativeHumanApi.onConnect(options, onCallback)
    }
}

export default RNHumanAPI;
